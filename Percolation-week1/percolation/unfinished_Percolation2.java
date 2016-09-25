package percolation;

/**
问题：渗透问题。将一个不透水的均质方块分割为矩阵N*N，最上方为水源。随机打开矩阵中任意格子，重复此项操作多次，直到产生一条路径使水能穿过这个方块到达最下方。

思路2：
采用quick-find算法。

数据结构：使用唯一数组grid[][]。同时记录这个格子的开闭和其连接的最靠左上（先比最上，再比最左）的格子的序号（行数*N+列数）。如果是关闭的，记录为-1（BLOCK)；否则就是最左上的那个序号，也就是整条路径中的格子里最小的序号。每条路径都有唯一一个最小序号。

算法：
打开格子，检测其邻近的4个格子的连接情况：比较每一个邻居的值，如果比本格子小，那么就赋值给本格子；若产生赋值，同时如果本格子值不为-1，将这个更小的赋给全部等于这个大值的格子（即更新本路径的其他的格子为新值）。（如果是关闭的，那么记录为-1（BLOCK），肯定不比本格子小，也就不会产生操作）
这样的话，不如直接比较上下左右，存在x1,x2,x3,x4,取最小值赋值给本格子（注意本格子再不济，都是BLOCK的话，需要给自己赋值为自己的序号），若xk最小，那么将所有等于x1,x2,x3,x4（除了xk自身，注意排除BLOCK）赋值为xk。
测试渗透状态：如果存在底部格子grid[N-1][x]联通grid[0][x]，即grid[N-1][x]的值小于N，即这个格子所在路径最上方到顶了。即渗透。

存在问题：
open消耗太高。每个open都要检查全部格子的数据4遍：4N^2。平均总open耗时(0.59*N^2)*(4N^2) -> N^4
**/

public class Percolation {

	private int N;
	private int[][] grid;
	private BLOCK = -1;

	public Percolation(int n) {
		N = n;
		grid = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				grid[i][j] = BLOCK;
			}
		}
		System.out.println("Created " + N + "-by-" + N + "grid.");
	}

	public int min(int x, int y) {
		return x < y ? x : y;
	}
	public void changeAll(int x, int min) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (grid[i][j] == x)
					grid[i][j] == min;
			}
		}
	}

	public void open(int i, int j) {
		if (isOpen(i, j)) return;
		int self = i * N + j;
		int x[4]; // up,down,left,right
		x[0] = i > 0 ? grid[i - 1][j] : BLOCK;
		x[1] = i < N - 1 ? grid[i + 1][j] : BLOCK;
		x[2] = j > 0 ? grid[i][j - 1] : BLOCK;
		x[3] = j < N - 1 ? grid[i][j + 1] : BLOCK;
		int minx = min(x[0], min(x[1], min(x[2], x[3])));
		if (minx == BLOCK) {
			grid[i][j] == self;
			return;
		}
		int min = min(minx, self);
		grid[i][j] = min;
		for (int i = 0; i < 4; i++) {
			if (x[i] != BLOCK && x[i] > min) {
				changeAll(x[i], min)
			}
		}

		return;

	}
	public boolean isOpen(int i, int j) {
		return grid[i][j] != BLOCK;
	}
	public boolean isBLOCK(int i, int j) {
		return grid[i][j] == BLOCK;
	}

	// does the system percolate?
	public boolean percolates() {

	}

	public void printGrid () {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(grid[i][j] == BLOCK ? "*" : "-");
			}
			System.out.println();
		}
	}

}