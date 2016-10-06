package percolation;

/**
问题：渗透问题。将一个不透水的均质方块分割为矩阵N*N，最上方为水源。随机打开矩阵中任意格子，重复此项操作多次，直到产生一条路径使水能穿过这个方块到达最下方。

思路4：
采用weighted-quick-union算法（with Path compression）。

数据结构：和课上所用的weighted-quick-union算法一致，采用PPT的写法，模拟出2个虚拟点，分别是top和bottom。isFull就是判断这个点是否 is_connected(self,top)
**/
public class Percolation {

	private int[] father_of;
	private int[] size;
	private boolean[] is_open;

	private int TOP;
	private int BOT;

	private int N;
	// count of all site, including top and bottom
	private int COUNT;

	public Percolation(int n) {

		if (n<=0){
			throw new IllegalArgumentException("n<=0");
		}
		N = n;
		TOP = N * N;
		BOT = TOP + 1;
		COUNT = N * N + 2;

		father_of = new int[COUNT];
		size = new int[COUNT];
		is_open = new boolean[COUNT];

		for (int i = 0; i < COUNT; i++) {
			father_of[i] = i;
			size[i] = 1;
			is_open[i] = false;
		}

		for (int i = 0; i < N; i++) {
			union(i, TOP);
			union(N * N - 1 - i, BOT);
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

	private boolean is_connected (int a, int b) {
		return root(a) == root(b);
	}


	private void union (int i, int j) {
		int ri = root(i);
		int rj = root(j);

		if (ri == rj) return;

		if (size[rj] > size[ri]) {
			father_of[ri] = rj;
			size[rj] += size[ri];
		} else {
			father_of[rj] = ri;
			size[ri] += size[rj];
		}
	}


	public void open(int i, int j) {
		// corner cases
		int para[] = new int[2];
		// start from 1,1
		i--;
		j--;
		para[0] = i;
		para[1] = j;
		for (int k = 0; k < 2; k++) {
			if (para[k] >= N || para[k] < 0)
				throw new IndexOutOfBoundsException("Argument is outside its prescribed range");
			}

		int self = i * N + j;
		if (is_open[self]) return;
		is_open[self] = true;

		int NOT_EXIST = -1;

		int x[] = new int[4]; // up, down, left, right
		x[0] = i > 0 ? ((i - 1) * N + j) : NOT_EXIST;
		x[1] = i < N - 1 ? (i + 1) * N + j : NOT_EXIST;
		x[2] = j > 0 ? i * N + j - 1 : NOT_EXIST;
		x[3] = j < N - 1 ? i * N + j + 1 : NOT_EXIST;

		for (int k = 0; k < 4; k++) {
			if (x[k] != NOT_EXIST && is_open[x[k]]) {
				union(x[k], self);
			}
		}
		return;
	}

	public boolean isOpen(int i, int j) {
		i--;
		j--;
		return is_open[i * N + j];
	}
	public boolean isFull(int i, int j) {
		i--;
		j--;
		return is_connected(i * N + j, TOP);
	}
	public boolean percolates() {
		return is_connected(TOP, BOT);
	}
}
