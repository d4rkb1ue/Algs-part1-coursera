/*
- How to run:
javac-algs4 percolation/*.java
java-algs4 percolation/PercolationStats

*/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private double mean_data, stddev_data;
    private double[] datas;
    private int Trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException(" n ≤ 0 or trials ≤ 0");
        Trials = trials;

        mean_data = stddev_data = -1;
        datas = new double[trials];
        int res;
        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            res = testGrid(percolation, n);
            datas[i] = (double)res / (n * n);
        }

        StdOut.println("mean = " + mean());
        StdOut.println("stddev = " + stddev());
        StdOut.println("95% confidence interval = " + confidenceLo() + ", " + confidenceHi());

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
        int trials = Integer.parseInt(args[1]);

        new PercolationStats(n, trials);
    }
}