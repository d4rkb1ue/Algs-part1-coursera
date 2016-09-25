package percolation;


/**
问题：渗透问题。将一个不透水的均质方块分割为矩阵N*N，最上方为水源。随机打开矩阵中任意格子，重复此项操作多次，直到产生一条路径使水能穿过这个方块到达最下方。


思路：
采用weighted-quick-union算法。
使用3个数组，grid[][]记录每个格子的开闭，tree[]记录每个格子在树中的父亲节点的序号（行数*N+列数），size[]记录每个格子之下包含的节点个数（包括自己）。
每次打开一个格子，若其相邻（上下左右）格子打开，则在树中将这两个格子相连。将小树连接到大树上。（weighted）
如果打开的格子是最上面一层，那么设定其父节点为N^2（水源）。
最终判断整个矩阵连通通过判断是否存在一个最底下的格子的根节点为N^2（即水源）。

这个思路是错误的！原因：
不能保证在最上方的水源是根节点。水源可能为一棵树的叶结点。因此给定某一底部格子，无法判断其所在树中是否存在水源。

**/

public class Percolation {

	// wether site is blocked or not
	private int[][] grid;

	// record each site's root, grid[i][j] -> tree[i*N+j], add one for Top above
	private int[] tree;

	// record each site's tree's size, add one for Top above
	private int[] size;

	private int N;
	private int TOP_ABOVE;

	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		N = n;
		TOP_ABOVE = n * n;
		grid = new int[N][N];

		// add one for Top above
		tree = new int[N * N + 1];
		size = new int[N * N + 1];

		for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
				// blocked(full)
				grid[row][col] = 1;
				tree[row * N + col] = row * N + col;
				size[row * N + col] = 1;
			}
		}
		tree[TOP_ABOVE] = size[TOP_ABOVE] = 1;
		System.out.println("Created a " + n + "-by-" + n + " grid.");
	}

	// open site (row i, column j) if it is not open already
	public void open(int i, int j) {
		grid[i][j] = 0;

		// set Top above as N*N
		if (i == 0) {
			tree[i * N + j] = TOP_ABOVE;
		} else if (grid[i - 1][j] == 0) {
			union(i * N + j, (i - 1) * N + j);
		}

		if (i != N - 1 && grid[i + 1][j] == 0) {
			union(i * N + j, (i + 1) * N + j);
		}

		if (j - 1 >= 0 && grid[i][j - 1] == 0) {
			union(i * N + j, i * N + j - 1);
		}
		if (j + 1 < N && grid[i][j + 1] == 0) {
			union(i * N + j, i * N + +j + 1);
		}
	}

	public void union(int a, int b) {
		int ra = root(a);
		int rb = root(b);

		if (ra == rb) return;

		if (size[ra] >= size[rb]) {
			tree[rb] = ra;
			size[ra] += size[rb];
		} else {
			tree[ra] = rb;
			size[rb] += size[ra];
		}
	}

	public int root(int i) {
		while ( tree[i] != -1 && tree[i] != i) {
			// path compression
			tree[i] = tree[tree[i]];
			i = tree[i];
		}
		return tree[i];
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		return grid[i][j] == 0 ? true : false;
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		return grid[i][j] == 1 ? true : false;
	}

	public boolean connected (int i, int j) {
		return root(i) == root(j);
	}

	// does the system percolate?
	public boolean percolates() {
		for (int i = 0; i < N; i++) {
			if (root(i) == -1)
				return true;
		}
		return false;
	}
}