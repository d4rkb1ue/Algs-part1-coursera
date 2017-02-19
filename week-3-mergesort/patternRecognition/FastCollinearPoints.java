/**
 * A faster, sorting-based solution for 
 * Given a point p, the following method determines whether p participates in a set of 4 or more collinear points.
 *
 * Performance requirement.
 * time: n^2 log n in the worst case
 * space: n plus the number of line segments returned 
 * 
 * *FastCollinearPoints should work properly even if the input has 5 or more collinear points.*
 * Apply the Arrays.sort():
 * http://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#sort(double[])
 * >This algorithm offers O(n log(n)) performance on many data sets that cause other quicksorts to degrade to quadratic performance.
 */
import java.util.*;
import edu.princeton.cs.algs4.*;
import java.lang.NullPointerException;

public class FastCollinearPoints {

	// a line segment contains minium of 4 points
	private static final int LINE_LENGTH = 4;
	private LineSegment[] segments;

	private class LineSegmentWithPoints{
		private LineSegment line;
		private Point[] points;
		public LineSegmentWithPoints(LineSegment line, Point[] points){
			this.line = line;
			this.points = points;
		}
		public LineSegment getLine(){
			return line;
		}
		public Point[] getPoints(){
			return points;
		}
	}
	

    // finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points){
		if (points == null || points.length == 0) throw new java.lang.NullPointerException();
		if(pointsHaveRepeated(points)) throw new java.lang.IllegalArgumentException("point repeat.");

		int N = points.length;
		if (N < LINE_LENGTH) segments = new LineSegment[0];

		edu.princeton.cs.algs4.Stack<LineSegmentWithPoints> tmpSegments = new edu.princeton.cs.algs4.Stack<LineSegmentWithPoints>();

		/**
		 * if points[p1]->points[p2] is not on a line px->py,
		 * points[p2]->points[p1] is also not on any line.
		 *
		 * So for p' > p, we should only check p'+k to find a new line.
		 */
		
		for (int p = 0; p <= N-LINE_LENGTH; p++){
			/**
			 * sort directly other points
			 * it's not important how other points sorted
			 */

			Arrays.sort(points, p+1, N, points[p].slopeOrder());
			int start = p;
			while(start <= N-LINE_LENGTH){
				start = pushNextLineSegment(points, p, start, tmpSegments, this);
			}
		}

		segments = new LineSegment[tmpSegments.size()];
		for (int i = 0; tmpSegments.size() > 0; i++){
			segments[i] = tmpSegments.pop().getLine();
		}
	}

	/**
	 * Java 中是没有参数返回的，也就是不能通过对 s 的赋值作为函数运行的结果
	 */
	// private static int pushNextLineSegment(Point[] points, int start, LineSegment s){
	/**
	 * find next line segment contains no less than LINE_LENGTH = 4 points
	 * and push it into stack
	 * 
	 * @param  Points[] points array, already sorted by points[start].slopeTo([i])
	 * @param  start    from where
	 * @param  stack    push new line into this
	 * @return          end point of this segment + 1
	 */
	private static int pushNextLineSegment(Point[] points, int base, int start, edu.princeton.cs.algs4.Stack<LineSegmentWithPoints> stack, FastCollinearPoints thisFast){
		Point p = points[base];
		// current line segment total length
		int currentLength = LINE_LENGTH;
		for (int i = start+1; i+LINE_LENGTH-2 < points.length; i++){
			// if i.slopeTo(p).equals(i+LINE_LENGTH-2)
			// then p->this->(this+1)->(this+2) is a line (when LINE_LENGTH == 4)
			// and this line length == LINE_LENGTH

			if (p.slopeTo(points[i]) == p.slopeTo(points[i+LINE_LENGTH-2])){

				if (lineIsInTheStack(p, points[i], stack)) continue;

				// explore whether this line.length > LINE_LENGTH
				while (i+currentLength-1 < points.length && p.slopeTo(points[i]) == p.slopeTo(points[i+currentLength-1])) currentLength++;
				
				Point[] pointsOnLine = new Point[currentLength];
				copyToPoints(points, p, pointsOnLine, i, i+currentLength-2);

				Point minPoint = getMaxOrMinPoint(pointsOnLine, false);
				Point maxPoint = getMaxOrMinPoint(pointsOnLine, true);

				stack.push(thisFast.new LineSegmentWithPoints(new LineSegment(minPoint, maxPoint), pointsOnLine));
				
				return base+currentLength-1;
			}// end if find a line
		}// end for
		return points.length;
	}

	private static void copyToPoints(Point[] source, Point plusOne, Point[] des, int start, int end){
		des[0] = plusOne;
		int d = 1;
		for (int i = start; i <= end; i++, d++){
			des[d] = source[i];
		}
	}

	/**
	 * check if line a->b inside the stack
	 */
	private static boolean lineIsInTheStack(Point a, Point b, edu.princeton.cs.algs4.Stack<LineSegmentWithPoints> stack){
		/**
		 * 这么遍历，在最差情况下，得耗时多久？N^2
		 * A:
		 * What's the maximum number of (maximal) collinear sets of points in a set of n points in the plane?
		 * It can grow quadratically as a function of N.
		 * Consider the n points of the form: (x, y) for x = 0, 1, 2, and 3 and y = 0, 1, 2, ..., n / 4. 
		 * This means that if you store all of the (maximal) collinear sets of points, you will need quadratic space in the worst case.
		 */
		Point[] points;
		for (LineSegmentWithPoints lwp : stack){
			points = lwp.getPoints();
			Arrays.sort(points);
			if (Arrays.binarySearch(points, a) > -1 && Arrays.binarySearch(points, b) > -1) return true;
		}
		return false;
	}


	/**
	 * @param max : if true, get the max point out of all points; else, get the min point
	 * @return Point: if max, return max; else min
	 */
	private static Point getMaxOrMinPoint(Point[] points, boolean max){
		if (points == null || points.length == 0) throw new NullPointerException("no point.");
		Point ret = points[0];
		for (Point p : points){
			if (max && p.compareTo(ret) > 0) ret = p;
			else if (!max && p.compareTo(ret) < 0) ret = p;
		}
		return ret;
	}

   	// the number of line segments
	public int numberOfSegments(){
		return segments != null ? segments.length : 0;
	}

    /**
     * the line segments
     * segments() should include each maximal line segment containing 4 (or more) points exactly once.
     * 
     * @return [description]
     */
    public LineSegment[] segments(){
    	return segments;
    }

    private static boolean pointsHaveRepeated(Point[] points){
		// n^2 for cycle check
		// NlgN if use sort() and check
    	Arrays.sort(points);
    	for(int i = 0; i < points.length - 1; i++){
    		if (points[i].compareTo(points[i+1]) == 0)
    			return true;
    	}
    	return false;
    }

    public static void main(String[] args) {
    	// LineSegmentWithPoints lwp = new LineSegmentWithPoints(new LineSegment(new Point(1,2), new Point(3,4)), null);

    	// read the n points from a file
    	In in = new In(args[0]);
    	int n = in.readInt();
    	Point[] points = new Point[n];
    	for (int i = 0; i < n; i++) {
    		int x = in.readInt();
    		int y = in.readInt();
    		points[i] = new Point(x, y);
    	}

		// if(pointsHaveRepeated(points)) throw new java.lang.IllegalArgumentException("point repeat.");

    	// draw the points
    	// StdDraw.enableDoubleBuffering();
    	// StdDraw.setXscale(0, 32768);
    	// StdDraw.setYscale(0, 32768);
    	// for (Point p : points) {
    	// 	p.draw();
    	// }
    	// StdDraw.show();

    	// print and draw the line segments
    	
    	Stopwatch time = new Stopwatch();
    	FastCollinearPoints collinear = new FastCollinearPoints(points);
    	StdOut.println("time = " + time.elapsedTime());
    	for (LineSegment segment : collinear.segments()) {
    		StdOut.println(segment);
    		// segment.draw();
    	}
    	// StdDraw.show();
    }

    // private static void p(String s){
    	// StdOut.println(s);
    // }
}
