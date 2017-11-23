using System;
using System.Diagnostics;

namespace FractionFormatter
{
	class MainClass
	{
		private  const int max = 1000;
		public static void Main (string[] args)
		{
			double[] numbers = new double[max];
			var random = new Random();
	        for (int n = 0; n < max; n++) {
				numbers[n] = Double.Parse(String.Format("{0:.#########}", random.NextDouble()* 100d));
	        }

	        rounders(numbers);
		}

		static void rounders(double[] numbers) {
	        int count = 10000;
	        DoubleFractionRounder dRounder = new DoubleFractionRounder(32);
	        for (int n = 1; n < 101; n *= 10) {
	            count *= n;
	            Stopwatch stopWatch = new Stopwatch();
	            stopWatch.Start();
	            for (int i = 0; i < count; i++) {
	                dRounder.round(numbers[i % max]);
	            }
	            stopWatch.Stop();
	            Console.WriteLine(String.Format("Rounder, time taken uncached ({0}) {1}", new Object[]{count, stopWatch.Elapsed}));
	        }
	        count = 10000;
	        CachingFractionRounder<Double> cRounder = new CachingFractionRounder<Double>(
	                new DoubleFractionRounder(32));
	        for (int n = 1; n < 101; n *= 10) {
	            count *= n;
	            Stopwatch stopWatch = new Stopwatch();
	            stopWatch.Start();
	            for (int i = 0; i < count; i++) {
	                cRounder.round(numbers[i % max]);
	            }
	            stopWatch.Stop();
	            Console.WriteLine(String.Format("Rounder, time taken cached ({0}: {1}) {2}", new Object[]{count, cRounder.getCallHitRatio(), stopWatch.Elapsed}));
	        }
		}
	}
}
