import argparse
from datetime import datetime
import numpy as np
import pandas as pd
import yfinance as yf
from scipy.signal import correlate
from statsmodels.tsa.stattools import grangercausalitytests
import matplotlib.pyplot as plt

def fetch_data(ticker, start, end):
    df = yf.download(ticker, start=start, end=end, progress=False)
    if df.empty:
        raise ValueError(f"No data for {ticker}")
    df = df[['Adj Close', 'Volume']].rename(columns={'Adj Close': 'AdjClose'})
    return df

def align_and_preprocess(a, b):
    df = pd.concat([a[['AdjClose','Volume']], b[['AdjClose','Volume']]], axis=1, keys=['A','B']).dropna()
    # price returns and log volume change
    ret = np.log(df.xs('AdjClose', level=1, axis=1)).diff().dropna()
    vol_change = np.log(df.xs('Volume', level=1, axis=1) + 1).diff().dropna()
    ret.columns = ['A','B']; vol_change.columns = ['A','B']
    return ret, vol_change

def max_crosscorr_lag(x, y, maxlag=30):
    # use zero-mean series
    xz = (x - x.mean()).to_numpy()
    yz = (y - y.mean()).to_numpy()
    # limit maxlag to length
    maxlag = min(maxlag, len(xz)-1)
    corr = correlate(yz, xz, mode='full')  # corr[k] corresponds to lag = k - (N-1)
    lags = np.arange(-len(xz)+1, len(xz))
    center = len(xz) - 1
    sel = (lags >= -maxlag) & (lags <= maxlag)
    corr_sel = corr[sel]
    lags_sel = lags[sel]
    # normalize to get correlation coefficient
    denom = np.std(xz) * np.std(yz) * len(xz)
    if denom == 0:
        return None, None
    corr_coeffs = corr_sel / denom
    idx = np.nanargmax(np.abs(corr_coeffs))
    best_lag = lags_sel[idx]
    best_corr = corr_coeffs[idx]
    # Interpretation: lag > 0 => x leads y by 'lag' steps (x at t correlates with y at t+lag)
    return best_lag, best_corr

def granger_summary(x, y, maxlag=10):
    # Granger: test if x causes y and y causes x
    df_xy = pd.concat([y, x], axis=1).dropna()  # test whether second column (x) Granger-causes first (y)
    df_yx = pd.concat([x, y], axis=1).dropna()
    def run_tests(data, direction_name):
        try:
            res = grangercausalitytests(data, maxlag=maxlag, verbose=False)
            pvals = [res[lag][0]['ssr_chi2test'][1] for lag in res]
            min_p = float(np.min(pvals))
            best_lag = int(np.argmin(pvals) + 1)
            return {'min_p': min_p, 'best_lag': best_lag}
        except Exception:
            return {'min_p': 1.0, 'best_lag': None}
    # x -> y:
    x_causes_y = run_tests(df_xy, "x->y")
    y_causes_x = run_tests(df_yx, "y->x")
    return x_causes_y, y_causes_x

def summary_report(t1, t2, price_ret, vol_change, price_lag, price_corr, vol_lag, vol_corr, gr_x_y, gr_y_x):
    def interpret(lag, corr, name1, name2):
        if lag is None:
            return f"{name1} vs {name2}: insufficient data"
        if lag > 0:
            return f"{name1} leads {name2} by {lag} days (cross-corr={corr:.3f})"
        elif lag < 0:
            return f"{name2} leads {name1} by {abs(lag)} days (cross-corr={corr:.3f})"
        else:
            return f"No lead/lag detected between {name1} and {name2} (lag=0, corr={corr:.3f})"
    print("=== Cross-correlation lead/lag (based on log returns / volume changes) ===")
    print("Price:", interpret(price_lag, price_corr, t1, t2))
    print("Volume:", interpret(vol_lag, vol_corr, t1, t2))
    print("\n=== Granger causality summary (min p-value across lags up to tested maxlag) ===")
    print(f"{t1} -> {t2}: p={gr_x_y['min_p']:.4f} (best lag={gr_x_y['best_lag']})")
    print(f"{t2} -> {t1}: p={gr_y_x['min_p']:.4f} (best lag={gr_y_x['best_lag']})")
    # short verdict
    def verdict():
        price_lead = "none"
        if price_lag is not None:
            if price_lag > 0: price_lead = t1
            elif price_lag < 0: price_lead = t2
            else: price_lead = "none"
        gr_lead = "none"
        if gr_x_y['min_p'] < 0.05 and gr_x_y['min_p'] < gr_y_x['min_p']:
            gr_lead = t1
        elif gr_y_x['min_p'] < 0.05 and gr_y_x['min_p'] < gr_x_y['min_p']:
            gr_lead = t2
        return f"Cross-corr suggests price lead: {price_lead}. Granger suggests causal lead: {gr_lead}."
    print("\nVerdict:", verdict())

def main():
    p = argparse.ArgumentParser(description="ETF lead/lag analysis (price & volume).")
    p.add_argument("--tickers", nargs=2, default=["SOXL","VGT"], help="Two tickers to compare.")
    p.add_argument("--start", default="2020-01-01")
    p.add_argument("--end", default=datetime.today().strftime("%Y-%m-%d"))
    p.add_argument("--maxlag", type=int, default=30, help="max lag for cross-corr (days).")
    p.add_argument("--gr-maxlag", type=int, default=10, help="max lag for Granger tests (days).")
    args = p.parse_args()

    t1, t2 = args.tickers
    a = fetch_data(t1, args.start, args.end)
    b = fetch_data(t2, args.start, args.end)
    price_ret, vol_change = align_and_preprocess(a, b)

    # cross-corr (price returns): treat series A as x, B as y
    price_lag, price_corr = max_crosscorr_lag(price_ret['A'], price_ret['B'], maxlag=args.maxlag)
    vol_lag, vol_corr = max_crosscorr_lag(vol_change['A'], vol_change['B'], maxlag=args.maxlag)

    gr_x_y_price, gr_y_x_price = granger_summary(price_ret['A'], price_ret['B'], maxlag=args.gr_maxlag)
    # For simplicity show Granger for price only; can run for volume similarly
    summary_report(t1, t2, price_ret, vol_change, price_lag, price_corr, vol_lag, vol_corr, gr_x_y_price, gr_y_x_price)

if __name__ == "__main__":
    main()