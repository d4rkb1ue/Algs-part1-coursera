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








// --------------------------------------------------TODO: 如果一条线上有 5 个点，第一次循环找到了这个线；从下一个点开始，又有一个 4 个点的同样的线，怎么排除？







public class FastCollinearPoints {

	// a line segment contains minium of 4 points
	private static final int LINE_LENGTH = 4;
	private LineSegment[] segments;
	private Point[] points;

    // finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points){
		if (points == null || points.length == 0) throw new java.lang.NullPointerException();
		if(pointsHaveRepeated(points)) throw new java.lang.IllegalArgumentException("point repeat.");

		int N = points.length;
		if (N < LINE_LENGTH) segments = new LineSegment[0];
		this.points = points;

		edu.princeton.cs.algs4.Stack<LineSegment> tmpSegments = new edu.princeton.cs.algs4.Stack<LineSegment>();

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
			for (int kk = p+1; kk < N; kk++){
				p("-" + points[p].toString() + " slopeTo " + points[kk].toString() + " = " + points[p].slopeTo(points[kk]));
			}
			p("----after sort----");
			Arrays.sort(points, p+1, N-1, points[p].slopeOrder());
			for (int kk = p+1; kk < N; kk++){
				p("-" + points[p].toString() + " slopeTo " + points[kk].toString() + " = " + points[p].slopeTo(points[kk]));
			}
			int start = p;
			while(start <= N-LINE_LENGTH){
				start = pushNextLineSegment(points, start, tmpSegments);				
			}
		}

		segments = new LineSegment[tmpSegments.size()];
		for (int i = 0; tmpSegments.size() > 0; i++){
			segments[i] = tmpSegments.pop();
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
	private static int pushNextLineSegment(Point[] points, int start, edu.princeton.cs.algs4.Stack<LineSegment> stack){
		p("call pushNextLineSegment");
		Point p = points[start];
		// current line segment total length
		int currentLength = LINE_LENGTH;
		for (int i = start+1; i+LINE_LENGTH-2 < points.length; i++){
			// if i.slopeTo(p).equals(i+LINE_LENGTH-2)
			// then p->this->(this+1)->(this+2) is a line (when LINE_LENGTH == 4)
			// and this line length == LINE_LENGTH
			
			p("for i = " + i);

			if (p.slopeTo(points[i]) == p.slopeTo(points[i+LINE_LENGTH-2])){
				p(p.toString() + " -> " + points[i].toString() + " is a line");

				// if (lineIsInTheStack(p, points[i], stack)) continue;

				// explore whether this line.length > LINE_LENGTH
				while (i+currentLength-1 < points.length && p.slopeTo(points[i]) == p.slopeTo(points[i+currentLength-1])) currentLength++;
				
				Point minPoint = getMaxOrMinPoint(p, points, i, i+currentLength-2, false);
				Point maxPoint = getMaxOrMinPoint(p, points, i, i+currentLength-2, true);
				p("getMaxOrMinPoint from " + minPoint.toString() + " to " + maxPoint.toString());
				
				stack.push(new LineSegment(maxPoint, minPoint));
				
				return i+currentLength-1;
			}// end if find a line
		}// end for
		return points.length;
	}
	/**
	 * check if line a-> b inside the stack
	 */
	private boolean lineIsInTheStack(Point a, Point b, edu.princeton.cs.algs4.Stack<LineSegment> stack){
		/**
		 * 这么遍历，在最差情况下，得耗时多久？N^2
		 * A:
		 * What's the maximum number of (maximal) collinear sets of points in a set of n points in the plane?
		 * It can grow quadratically as a function of N.
		 * Consider the n points of the form: (x, y) for x = 0, 1, 2, and 3 and y = 0, 1, 2, ..., n / 4. 
		 * This means that if you store all of the (maximal) collinear sets of points, you will need quadratic space in the worst case.
		 */
		for (LineSegment line : stack){
			if (isSameLine(a, b, line)){
				return true;
			}
		}
		return false;
	}
	/**
	 * check if a and b both on this line
	 * if so, namely a->b is this line 
	 */
	private static boolean isSameLine(Point a, Point b, LineSegment line){
		if (onTheLine(a, line) && onTheLine(b, line)) return true;
		return false;
	}
	/**
	 * return if point p is on the line
	 */
	private static boolean onTheLine(Point p, LineSegment line){
		// line.toString
		// TODO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		return false;
	}

	/**
	 * version 2.0 
	 * with start and end
	 * [points[start], points[end]]: boundary included
	 *
	 * @param  p 	  addtional to points
	 * @param  points source
	 * @param  start  start at
	 * @param  end    end with (include points[end])
	 * @param  max    if true, get the max point out of all points; else, get the min point
	 * @return        if max, return max; else min
	 */
	private static Point getMaxOrMinPoint(Point p, Point[] points, int start, int end, boolean max){
		if (points == null || points.length == 0 || start > end || points.length < end) 
			throw new NullPointerException("no point.");
		Point ret = p;
		for (int i = start; i <= end; i++){
			if (max && points[i].compareTo(ret) > 0) ret = points[i];
			else if (!max && points[i].compareTo(ret) < 0) ret = points[i];
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
    	StdDraw.enableDoubleBuffering();
    	StdDraw.setXscale(0, 32768);
    	StdDraw.setYscale(0, 32768);
    	for (Point p : points) {
    		p.draw();
    	}
    	StdDraw.show();

    	// print and draw the line segments
    	FastCollinearPoints collinear = new FastCollinearPoints(points);
    	p("---- numberOfSegments = " + collinear.numberOfSegments());
    	for (LineSegment segment : collinear.segments()) {
    		StdOut.println(segment);
    		segment.draw();
    	}
    	StdDraw.show();
    }

    private static void p(String s){
    	StdOut.println(s);
    }

}
