package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class PercolationStats {
	// perform trials independent experiments on an n-by-n grid
	double mean_data, stddev_data, confidenceLo_data, confidenceHi_data;
	double datas[];
	int N;
	int Trials;

	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException(" n ≤ 0 or trials ≤ 0");
		N = n;
		Trials = trials;

		mean_data = stddev_data = confidenceLo_data = confidenceHi_data = -1;
		datas = new double[trials];
		int res;
		Percolation percolation;
		for (int i = 0; i < trials; i++) {
			percolation = new Percolation(n);
			res = testGrid(percolation, n);
			datas[i] = (double)res / (n * n);
		}

		System.out.println("mean = " + mean());
		System.out.println("stddev = " + stddev());
		System.out.println("95% confidence interval = " + confidenceLo() + ", " + confidenceHi());

	}
	// sample mean of percolation threshold
	public double mean() {
		if (mean_data < 0) {
			mean_data = StdStats.mean(datas);
		}
		return mean_data;
	}
	// sample standard deviation of percolation threshold
	public double stddev() {
		if (stddev_data < 0) {
			stddev_data = StdStats.stddev(datas);
		}
		return stddev_data;
	}
	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(Trials);
	}
	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(Trials);
	}
	// return percolate time
	private int testGrid(Percolation per, int n) {
		int i = 0;
		int p, q;
		while (!per.percolates()) {
			p = StdRandom.uniform(n) + 1;
			q = StdRandom.uniform(n) + 1;
			// only count when not repeat
			if (!per.isOpen(p, q)) {
				per.open(p, q);
				i++;
			}
		}
		return i;
	}

	// test client (described below)
	public static void main(String[] args) {

		int n = Integer.parseInt(args[0]);
		int times = Integer.parseInt(args[1]);

		PercolationStats stat = new PercolationStats(n, times);
	}
}