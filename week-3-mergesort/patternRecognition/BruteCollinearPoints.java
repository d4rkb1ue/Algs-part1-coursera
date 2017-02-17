/**
* examines 4 points at a time and checks whether they all lie on the same line segment
* returning all such line segments.
*
* To check whether the 4 points p, q, r, and s are collinear,
* check whether the three slopes between p and q, between p and r, and between p and s are all equal.
*
* *
* Performance requirement:
* time should be n^4 in the worst case 
* space proportional to n plus the number of line segments returned.
*
* *
* author: d4rkb1ue
* website: drkbl.com
* date: 2017-2-18
**/
import java.util.*;
import edu.princeton.cs.algs4.*;
import java.lang.NullPointerException;

public class BruteCollinearPoints {

	private LineSegment[] segments;
	private Point[] points;

   	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points){
		if (points == null || points.length == 0) throw new java.lang.NullPointerException();
		int N = points.length;
		if (N < 4) segments = new LineSegment[0];
		this.points = points;
		int count = 0;
		
		// max segments number: C(N)(4) = N! / 24*(N-4)!
		// it shouble be resizable
		edu.princeton.cs.algs4.Stack<LineSegment> tmpSegments = new edu.princeton.cs.algs4.Stack<LineSegment>();

		// ~n^4
		for (int i = 0; i < N; i++){

			for (int j = i + 1; j < N; j++){
				double ij = points[i].slopeTo(points[j]);
				// p("i = " + points[i] + "j = " + points[j] + "slopeTo ij = " + ij);

				for (int m = j + 1; m < N; m++){
					double im = points[i].slopeTo(points[m]);
					// p("slopeTo im = " + im);
					if (ij != im) continue; // jump this j

					for (int n = m + 1; n < N; n++){
						double in = points[i].slopeTo(points[n]);
						// p("slopeTo in = " + in);
						if (ij == in){
							// find the start and end point
							Point[] tmp = new Point[4];
							tmp[0] = points[i];
							tmp[1] = points[j];
							tmp[2] = points[m];
							tmp[3] = points[n];
							
							Point maxPoint = getMaxOrMinPoint(tmp, true);
							Point minPoint = getMaxOrMinPoint(tmp, false);
							tmpSegments.push(new LineSegment(maxPoint, minPoint));
						}// end if ij == in == im
					}// end for n
				}//end for m
			}// end for j
		}// end for i

		segments = new LineSegment[tmpSegments.size()];

		/**
		 * 天坑！！！
		 * tmpSegments.size() 是会随着 pop -1 的
		 * 而 i 不管它会不会变，会持续 +1
		 *
		 * 当 i == 最初的 tmpSegements 的大小的一半时，
		 * i == tmpSegments.size()
		 *
		 * 所以
		 * for (int i = 0; i < tmpSegments.size(); i++){
		 * 的结果就是 stack 里面的6个元素有3个复制不到 array 中
		 * 
		 */

		// for (int i = 0; i < tmpSegments.size(); i++){
		for (int i = 0; tmpSegments.size() > 0; i++){
			segments[i] = tmpSegments.pop();
		}
		
	}

	/**
	 * @param max : if true, get the max point out of all points; else, get the min point
	 * @return Point: if max, return max; else min
	 */
	private Point getMaxOrMinPoint(Point[] points, boolean max){
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
		return segments == null ? segments.length : 0;
	}

   	// the line segments
	public LineSegment[] segments(){
		return segments;
	}
	/**
	 * @param
	 * @return
	 */
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

		if(pointsHaveRepeated(points)) throw new java.lang.IllegalArgumentException("point repeat.");

    	// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

    	// print and draw the line segments
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
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