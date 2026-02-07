# ETF Analysis Tool

This project provides a set of Python tools for analyzing Exchange Traded Funds (ETFs). It allows for scaping holdings, fetching historical price data, and performing correlation and lead-lag analysis between pairs of ETFs.

## Features

-   **Holdings Analysis**: Scrapes ETF holdings from Yahoo Finance to compare the overlap in assets between two ETFs.
-   **Price Analysis**: Fetches historical adjusted close prices for specified ETFs using `yfinance`.
-   **Correlation Analysis**: Calculates both static and rolling correlations between ETF returns to understand how they move together over time.
-   **Lead-Lag Analysis**: Performs cross-correlation analysis to identify if price movements in one ETF lead or lag another.
-   **Visualization**: Generates plots for cumulative returns, rolling correlation, and lead-lag cross-correlation.

## Installation

This project is managed with `uv`. To install dependencies:

```bash
uv sync
```

Alternatively, you can install the required packages using pip:

```bash
pip install pandas numpy yfinance requests beautifulsoup4 matplotlib seaborn
```

## Usage

The main analysis logic is contained in `etf_analyzer.py`. You can run it directly to see an example analysis between `QQQ` (Nasdaq-100) and `SPY` (S&P 500).

```bash
python etf_analyzer.py
```

### Key Functions

-   `get_etf_holdings(ticker)`: Scrapes the holdings of a given ETF ticker.
-   `get_price_data(tickers, start_date, end_date)`: Fetches historical price data and calculates daily returns.
-   `analyze_correlation(returns_df, ticker1, ticker2, window)`: Computes overall and rolling correlations.
-   `analyze_lead_lag(returns_df, ticker1, ticker2, max_lag)`: Performs cross-correlation analysis to detect lead-lag relationships.
-   `compare_holdings(holdings1, holdings2, ticker1, ticker2)`: Compares the holdings of two ETFs and reports overlap.


> [!WARNING]
> The `get_etf_holdings` function relies on web scraping Yahoo Finance. This method is fragile and may fail (returning 404 errors) if Yahoo changes their website structure or blocks automated requests. The price analysis features (using `yfinance` API) are robust.

## Configuration

You can modify the `__main__` block in `etf_analyzer.py` to analyze different ETF pairs or change the time range and analysis parameters.

```python
# Configuration in etf_analyzer.py
TICKER_1 = 'GLD'
TICKER_2 = 'GDX'
START_DATE = '2023-01-01'
...
```
