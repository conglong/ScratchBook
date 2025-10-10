import pandas as pd
import numpy as np
import yfinance as yf
import requests
from bs4 import BeautifulSoup
import matplotlib.pyplot as plt
import seaborn as sns
from typing import List, Dict, Tuple

# Set plot style for better aesthetics
sns.set_style("whitegrid")
plt.rcParams['figure.figsize'] = (15, 7)

def get_etf_holdings(ticker: str) -> pd.DataFrame:
    """
    Scrapes the holdings of a given ETF ticker from Yahoo Finance.
    
    Args:
        ticker (str): The ETF ticker symbol.
        
    Returns:
        pd.DataFrame: A DataFrame containing the ETF's holdings, or an empty DataFrame if failed.
    """
    url = f"https://finance.yahoo.com/quote/{ticker}/holdings?p={ticker}"
    headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'}
    
    try:
        print(f" scraping holdings for {ticker}...")
        response = requests.get(url, headers=headers)
        response.raise_for_status() # Raise an exception for bad status codes
        
        soup = BeautifulSoup(response.text, 'html.parser')
        
        # Find the holdings table
        tables = soup.find_all('table')
        if not tables:
            print(f"Warning: Could not find holdings table for {ticker}.")
            return pd.DataFrame()

        holdings_df = pd.read_html(str(tables[0]))[0]
        
        # Clean up the DataFrame
        holdings_df = holdings_df[['Symbol', 'Name', '% Assets']]
        holdings_df['% Assets'] = holdings_df['% Assets'].str.rstrip('%').astype(float)
        holdings_df.set_index('Symbol', inplace=True)
        
        print(f"Successfully scraped {len(holdings_df)} holdings for {ticker}.")
        return holdings_df

    except Exception as e:
        print(f"Error scraping holdings for {ticker}: {e}")
        return pd.DataFrame()

def get_price_data(tickers: List[str], start_date: str, end_date: str) -> pd.DataFrame:
    """
    Fetches historical adjusted close prices and calculates daily returns.
    
    Args:
        tickers (List[str]): A list of ticker symbols.
        start_date (str): The start date in 'YYYY-MM-DD' format.
        end_date (str): The end date in 'YYYY-MM-DD' format.
        
    Returns:
        pd.DataFrame: A DataFrame containing daily returns for each ticker.
    """
    print(f"\nFetching price data for {tickers} from {start_date} to {end_date}...")
    try:
        prices = yf.download(tickers, start=start_date, end=end_date)['Adj Close']
        daily_returns = prices.pct_change().dropna()
        daily_returns.columns = [f"{ticker}_Return" for ticker in tickers]
        print("Price data fetched successfully.")
        return daily_returns
    except Exception as e:
        print(f"Error fetching price data: {e}")
        return pd.DataFrame()

def analyze_correlation(returns_df: pd.DataFrame, ticker1: str, ticker2: str, window: int = 30) -> Tuple[float, pd.Series]:
    """
    Calculates static and rolling correlation between two ETF return series.
    
    Args:
        returns_df (pd.DataFrame): DataFrame with daily returns.
        ticker1 (str): The first ticker.
        ticker2 (str): The second ticker.
        window (int): The window size for rolling correlation.
        
    Returns:
        Tuple[float, pd.Series]: A tuple of the overall correlation and the rolling correlation series.
    """
    col1 = f"{ticker1}_Return"
    col2 = f"{ticker2}_Return"
    
    # Static correlation
    overall_corr = returns_df[col1].corr(returns_df[col2])
    
    # Rolling correlation
    rolling_corr = returns_df[col1].rolling(window=window).corr(returns_df[col2])
    
    return overall_corr, rolling_corr.dropna()

def analyze_lead_lag(returns_df: pd.DataFrame, ticker1: str, ticker2: str, max_lag: int = 10) -> Dict[int, float]:
    """
    Performs a cross-correlation analysis to find lead-lag relationships.
    
    Args:
        returns_df (pd.DataFrame): DataFrame with daily returns.
        ticker1 (str): The first ticker symbol.
        ticker2 (str): The second ticker symbol.
        max_lag (int): The maximum number of days to shift for lag analysis.
        
    Returns:
        Dict[int, float]: A dictionary mapping lag days to correlation values.
    """
    col1 = f"{ticker1}_Return"
    col2 = f"{ticker2}_Return"
    
    cross_corrs = {}
    for i in range(-max_lag, max_lag + 1):
        # Shift ticker2's returns and calculate correlation with ticker1
        # A positive lag 'i' means we're correlating Ticker1(t) with Ticker2(t-i)
        # If this correlation is high, it suggests Ticker2 leads Ticker1
        cross_corrs[i] = returns_df[col1].corr(returns_df[col2].shift(i))
        
    return cross_corrs

def compare_holdings(holdings1: pd.DataFrame, holdings2: pd.DataFrame, ticker1: str, ticker2: str) -> None:
    """
    Compares the holdings of two ETFs and prints the overlap.
    
    Args:
        holdings1 (pd.DataFrame): Holdings DataFrame for the first ETF.
        holdings2 (pd.DataFrame): Holdings DataFrame for the second ETF.
        ticker1 (str): The first ticker symbol.
        ticker2 (str): The second ticker symbol.
    """
    if holdings1.empty or holdings2.empty:
        print("\nSkipping holdings comparison due to scraping error.")
        return
        
    # Merge holdings on the symbol index
    merged_holdings = holdings1.merge(holdings2, left_index=True, right_index=True, suffixes=(f'_{ticker1}', f'_{ticker2}'))
    
    # Calculate overlap
    common_tickers = len(merged_holdings)
    overlap_weight_ticker1 = merged_holdings[f'% Assets_{ticker1}'].sum()
    overlap_weight_ticker2 = merged_holdings[f'% Assets_{ticker2}'].sum()

    print("\n--- Constituency Analysis ---")
    print(f"Total holdings for {ticker1}: {len(holdings1)}")
    print(f"Total holdings for {ticker2}: {len(holdings2)}")
    print(f"Number of common holdings: {common_tickers}")
    print(f"Weight of common holdings in {ticker1}: {overlap_weight_ticker1:.2f}%")
    print(f"Weight of common holdings in {ticker2}: {overlap_weight_ticker2:.2f}%")
    
    # Display top 10 common holdings
    print("\nTop 10 Common Holdings:")
    top_10 = merged_holdings.sort_values(by=f'% Assets_{ticker1}', ascending=False).head(10)
    print(top_10[[f'Name_{ticker1}', f'% Assets_{ticker1}', f'% Assets_{ticker2}']])
    print("--------------------------")
    
def plot_results(returns_df: pd.DataFrame, rolling_corr: pd.Series, cross_corrs: Dict[int, float], ticker1: str, ticker2: str):
    """
    Generates and displays plots for the analysis.
    """
    print("\nGenerating plots...")
    
    # Plot 1: Cumulative (Normalized) Returns
    plt.figure()
    normalized_prices = (1 + returns_df).cumprod()
    normalized_prices.plot()
    plt.title(f'Normalized Price Performance: {ticker1} vs. {ticker2}')
    plt.ylabel('Cumulative Return')
    plt.xlabel('Date')
    plt.legend([ticker1, ticker2])
    plt.tight_layout()
    plt.show()

    # Plot 2: Rolling Correlation
    plt.figure()
    rolling_corr.plot()
    plt.title(f'{len(rolling_corr.index)}-Day Rolling Correlation: {ticker1} vs. {ticker2}')
    plt.ylabel('Correlation Coefficient')
    plt.xlabel('Date')
    plt.axhline(rolling_corr.mean(), color='red', linestyle='--', label=f'Average: {rolling_corr.mean():.2f}')
    plt.legend()
    plt.tight_layout()
    plt.show()

    # Plot 3: Lead-Lag Cross-Correlation
    plt.figure()
    lag_df = pd.Series(cross_corrs).sort_index()
    lag_df.plot(kind='bar', color='skyblue')
    
    # Find and highlight the max correlation lag
    max_corr_lag = lag_df.idxmax()
    max_corr_val = lag_df.max()
    plt.axvline(x=max_corr_lag + 10, color='red', linestyle='--', label=f'Max Corr at lag {max_corr_lag} ({max_corr_val:.2f})')
    
    plt.title(f'Cross-Correlation (Lead/Lag): {ticker1} vs. {ticker2}')
    plt.xlabel(f'Lag (Days) of {ticker2} relative to {ticker1}')
    plt.ylabel('Correlation')
    plt.legend()
    plt.tight_layout()
    plt.show()
    
    # Interpretation of lead-lag plot
    if max_corr_lag > 0:
        print(f"\nLead/Lag Insight: The strongest correlation occurs when {ticker2}'s returns are shifted FORWARD by {max_corr_lag} day(s).")
        print(f"This suggests that movements in **{ticker1} may lead** movements in {ticker2} by approximately {max_corr_lag} day(s).")
    elif max_corr_lag < 0:
        print(f"\nLead/Lag Insight: The strongest correlation occurs when {ticker2}'s returns are shifted BACKWARD by {-max_corr_lag} day(s).")
        print(f"This suggests that movements in **{ticker2} may lead** movements in {ticker1} by approximately {-max_corr_lag} day(s).")
    else:
        print("\nLead/Lag Insight: The strongest correlation is at a lag of 0, suggesting the price movements are largely contemporaneous.")


if __name__ == "__main__":
    # --- Configuration ---
    # Example 1: Tech vs. Broader Market (QQQ vs. SPY)
    TICKER_1 = 'QQQ'
    TICKER_2 = 'SPY'
    
    # Example 2: Gold vs. Gold Miners (GLD vs. GDX)
    # TICKER_1 = 'GLD'
    # TICKER_2 = 'GDX'

    START_DATE = '2023-01-01'
    END_DATE = '2025-10-03'
    ROLLING_WINDOW = 30 # For rolling correlation, in days
    MAX_LAG = 10         # For lead-lag analysis, in days

    # 1. Scrape Constituency Data
    holdings_t1 = get_etf_holdings(TICKER_1)
    holdings_t2 = get_etf_holdings(TICKER_2)
    compare_holdings(holdings_t1, holdings_t2, TICKER_1, TICKER_2)
    
    # 2. Get Price Data
    returns_data = get_price_data([TICKER_1, TICKER_2], START_DATE, END_DATE)

    if not returns_data.empty:
        # 3. Analyze Correlation
        overall_correlation, rolling_correlation = analyze_correlation(returns_data, TICKER_1, TICKER_2, window=ROLLING_WINDOW)
        
        print("\n--- Correlation Analysis ---")
        print(f"Overall correlation between {TICKER_1} and {TICKER_2}: {overall_correlation:.4f}")
        print("----------------------------")
        
        # 4. Analyze Lead-Lag Relationship
        cross_correlation_results = analyze_lead_lag(returns_data, TICKER_1, TICKER_2, max_lag=MAX_LAG)
        
        # 5. Visualize Results
        plot_results(returns_data, rolling_correlation, cross_correlation_results, TICKER_1, TICKER_2)
    else:
        print("\nCould not perform analysis due to data fetching errors.")
        