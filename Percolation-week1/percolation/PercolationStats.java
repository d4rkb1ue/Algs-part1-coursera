package percolation;

import java.util.Random;
import java.util.*;

public class PercolationStats {
	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		System.out.println("Stat!");
	}
	// sample mean of percolation threshold
	public double mean() {
		return 1.0;
	}
	// sample standard deviation of percolation threshold
	public double stddev() {
		return 1.0;
	}
	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return 1.0;
	}
	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return 1.0;
	}

	public static int testGrid(Percolation per, int n) {
		Random random = new Random();
		int i = 0;
		int p, q;
		while (!per.percolates()) {
			p = random.nextInt(n);
			q = random.nextInt(n);
			// only count when not repeat
			if (per.open(p, q)) {
				i++;
			}
			// System.out.println("open " + p + "," + q + ", i: " + i);
		}
		return i;
	}

	public static void drawGraphic() {
		Percolation percolation = new Percolation(10);
		Scanner sc = new Scanner(System.in);
		String str;
		int i, j;
		while (true) {
			str = sc.nextLine();
			i = Integer.parseInt("" + str.charAt(0));
			j = Integer.parseInt("" + str.charAt(2));
			// System.out.println(i + "#" + j);
			percolation.open(i,j);
			percolation.draw();
		}
	}

	// test client (described below)
	public static void main(String[] args) {
		// drawGraphic();

		int n = 100;
		int times = 200;

		double avg = 0;
		Percolation percolation;
		for (int i = 0; i < times; i++) {
			percolation = new Percolation(n);
			int res = testGrid(percolation, n);
			System.out.println("Res: " + res + ", process: " + (i + 1) + "/" + times);
			avg += res;
		}

		System.out.println("Avg: " + avg / times / (n * n) );
		System.out.println("Correct: 0.593");
	}
}