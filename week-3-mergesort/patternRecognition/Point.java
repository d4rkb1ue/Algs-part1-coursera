/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.*;
import edu.princeton.cs.algs4.*;


public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    // x and y are each between 0 and 32,767.
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x){
            if (this.y != that.y) return Double.POSITIVE_INFINITY;
            else return Double.NEGATIVE_INFINITY;
        }
        if (this.y == that.y) return +0.0;
        return (double)(that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     *
     * @param  that the other point
     * @return 0 if this point is equal (x0 = x1 and y0 = y1)
     *         -1 if and only if either y0 < y1 or if y0 = y1 and x0 < x1
     *         1 if else
     */
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new BySlope();
    }

    private class BySlope implements Comparator<Point> {
        // this->x < this->y : -1
        public int compare(Point x, Point y){
            double thisx = Point.this.slopeTo(x);
            double thisy = Point.this.slopeTo(y);
            if (thisx == thisy) return 0;
            if (thisx > thisy) return 1;
            return -1;
        }
    }

    
    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // boolean eee = (Double.POSITIVE_INFINITY == Double.POSITIVE_INFINITY); // true
        // boolean ee2 = (Double.POSITIVE_INFINITY > Double.NEGATIVE_INFINITY); // true

        Point kk = new Point(80, 477);
        Point mm = new Point(80, 17);
        p(kk.slopeOrder().compare(mm, mm)+"");


        /** 
        * test draw
        **/
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 100);
        // StdDraw.setYscale(0, 100);
        
        // Point x = new Point(3, 4);
        // x.draw();
        // x.drawTo(new Point(100,100));
        // StdDraw.show();

        /**
        * create data
        **/
        // int N = 100;
        // int SCALE = 100;
        // Point[] ps = new Point[N];
        // for (int i = 0; i < N; i++){
        //     ps[i] = new Point(StdRandom.uniform(SCALE), StdRandom.uniform(SCALE));
        // }

        /**
        * test slopeTo
        **/
        // for (int i = 0; i < N; i++){
        //     int kk = StdRandom.uniform(N);
        //     p(ps[i].toString() + "->" + ps[kk].toString() + "=" + ps[i].slopeTo(ps[kk]));
        // }

        /**
        * test compareTo
        **/
        // for (int i = 0; i < N; i++){
        //     int kk = StdRandom.uniform(N);
        //     String symbol = ps[i].compareTo(ps[kk]) == 0 ? " = " : ps[i].compareTo(ps[kk]) == 1 ? " > " : " < ";
        //     p(ps[i].toString() + symbol + ps[kk].toString());
        // }

        /**
        * test Comparator
        **/
        // Arrays.sort(ps, new Point(0, 0).slopeOrder());
        // for (Point p : ps)
        //     p(p.toString());

    }
    private static void p(String s){
        StdOut.println(s);
    }
}
