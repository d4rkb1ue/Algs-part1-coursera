package percolation;

/**
问题：渗透问题。将一个不透水的均质方块分割为矩阵N*N，最上方为水源。随机打开矩阵中任意格子，重复此项操作多次，直到产生一条路径使水能穿过这个方块到达最下方。

思路3：
采用weighted-quick-union算法（with Path compression）。

数据结构：和课上所用的weighted-quick-union算法一致，多添加2个boolean[N][N]: isOpen, isFull(有水)。初始状态，isFull[0][x] == true;

算法：和思路1一致，每次open调用4次union（上下左右连接，如果isOpen）。如果open的这个isFull，就设定其根isFull = true。于是就解决了思路1存在的问题。

这个算法在修改isFull的时候一开始写错了，详见下面的解释。

**/
public class Percolation {

	private int[] father_of;
	private int[] size;
	private boolean[] is_full;
	private boolean[] is_open;
	private int N;

	public Percolation(int n) {

		N = n;
		father_of = new int[N * N];
		size = new int[N * N];
		is_full = new boolean[N * N];
		is_open = new boolean[N * N];

		for (int i = 0; i < N * N; i++) {
			father_of[i] = i;
			size[i] = 1;
			is_full[i] = false;
			is_open[i] = false;
		}

		for (int i = 0; i < N; i++) {
			is_full[i] = true;
		}
	}

	private int root(int i) {
		while (father_of[i] != i) {

			// path compression
			father_of[i] = father_of[father_of[i]];
			i = father_of[i];
		}
		return i;
	}


	private void union (int i, int j) {

		int ri = root(i);
		int rj = root(j);

		if (ri == rj) return;

		if (size[rj] > size[ri]) {
			father_of[ri] = rj;
			size[rj] += size[ri];
			// new!
			if (is_full[ri]) is_full[rj] = true;
			// ---
		} else {
			father_of[rj] = ri;
			size[ri] += size[rj];
			// new!
			if (is_full[rj]) is_full[ri] = true;
			// ---
		}
	}

	// if is already open, return false; else return true
	public boolean open(int i, int j) {
		int self = i * N + j;
		if (is_open[self]) return false;
		is_open[self] = true;

		int x[] = new int[4];

		x[0] = i > 0 ? ((i - 1) * N + j) : -1;
		x[1] = i < N - 1 ? (i + 1) * N + j : -1;
		x[2] = j > 0 ? i * N + j - 1 : -1;
		x[3] = j < N - 1 ? i * N + j + 1 : -1;

		for (int k = 0; k < 4; k++) {
			if (x[k] >= 0 && is_open[x[k]]) {
				union(x[k], self);
				/*
				对is_full的修改放在此处（下面的三行代码）是错误的。放在此处的结果是：

				N-Result
				2 0.66
				10 死循环
				20 0.64 
				100 0.611
				200 0.605
				500 0.599
				1000 0.597
				2000 0.597

				结果很奇怪，N=10在重复100次时经常发生死循环，其他数值也偏大，但是越来越接近0.597。
				
				正确的替换是 new! 的部分。
				如果放在此处，考虑一种情况，open 处在中心的某一格 把上下两半连起来，但是上半部size小，故成为下半部的儿子。因为中心格是没水的，因此产生的新树root为没水。

				如此可以解释结果。发生死循环的时候正是全部底部格子处在一个没水的大树中，之后无论怎么连接，大根保持不变。这课大树的根永远不会修改is_full。而数值偏大是因为部分连接并未产生改变，之后的才有效。

				*/
				// if (is_full[self]) {
				// 	is_full[root(self)] = true;
				// }

			}
		}
		return true;
	}

	public boolean isOpen(int i, int j) {
		return is_open[i * N + j];
	}
	public boolean isFull(int i, int j) {
		// if his root is full, he is full.
		return is_full[root(i * N + j)];
	}
	public boolean percolates() {
		for (int i = 0; i < N; i++) {
			if (isFull(N - 1, i))
				return true;
		}
		return false;
	}

	public void draw() {
		for (int k = 0; k < 10; k++) {
			for (int m = 0; m < 10; m++) {
				if (isOpen(k, m)) System.out.print("#");
				else System.out.print("-");
			}
			System.out.println();
		}
		System.out.println();
	}
}
