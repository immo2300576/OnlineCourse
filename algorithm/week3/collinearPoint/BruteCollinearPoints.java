import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class BruteCollinearPoints {
    private Point[] pointSet;
    private LineSegment[] linesegments;
    private int linesegmentsCapacity = 1;
    private int pointsNum;
    private int segmentNum = 0;
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException();
        
        pointsNum = points.length;
        pointSet = new Point[pointsNum];
        linesegments = null;
        for (int i = 0; i < pointsNum; i++) {
            if (points[i] == null)
                throw new java.lang.NullPointerException();
            pointSet[i] = points[i];
        }
        // System.out.printf("Before sorting...\n");
        // printPoints();
        // Check whether two of points are identical
        Quick quick = new Quick();
        quick.sort(pointSet);
        if (checkIdentical())
            throw new java.lang.IllegalArgumentException();
        // System.out.printf("After sorting...\n");
        // printPoints();
        calculate();
        resize(segmentNum);
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        // StdOut.println(n);
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            // StdOut.println(x);
            // StdOut.println(y);
            points[i] = new Point(x, y);
        }

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
    private void calculate() {
        int[] pointBuffer = new int[2];
        for (int i = 0; i < pointsNum; i++) {
            // System.out.printf("Point i = %s\n",pointSet[i].toString());
            for (int j = i+1; j < pointsNum; j++) {
                double slope = pointSet[i].slopeTo(pointSet[j]);
                int count = 0;
                // System.out.printf("Point i = %s, Point j = %s, slope = %f\n",
                // pointSet[i].toString(), pointSet[j].toString(), slope);
                for (int k = j+1; k < pointsNum; k++) {
                    if (pointSet[i].slopeTo(pointSet[k]) > slope || pointSet[i].slopeTo(pointSet[k]) < slope)
                        continue; 
                    pointBuffer[count++] = k;
                    // System.out.printf("Point k = %s, slope = %f\n",pointSet[k].toString(), pointSet[i].slopeTo(pointSet[k]));
                }
                // System.out.printf("Count = %d\n", count);
                        // System.out.printf("k = %d, slope = %2.2f\n", k, pointSet[i].slopeTo(pointSet[k]));
                if (count >= 2) { // not supporting more than 4 point segment
                    LineSegment l = sort4(pointSet[i], pointSet[j], pointSet[pointBuffer[0]], pointSet[pointBuffer[1]]);
                        
                    if (segmentNum == 0)
                        linesegments = new LineSegment[1];
                        
                    linesegments[segmentNum++] = l;

                    // System.out.printf("Line %d %s\n",segmentNum,l.toString());
                    if (segmentNum == linesegmentsCapacity) {
                        linesegmentsCapacity *= 2;
                        resize(linesegmentsCapacity);
                    }
                    // break;
                }
            }
        }
    }
    public int numberOfSegments() {     
        return segmentNum;
    }
    public LineSegment[] segments() {
        LineSegment[] copy = new LineSegment[segmentNum];
        // System.out.printf("segment no = %d\n",segmentNum);
        for (int i = 0; i < segmentNum; i++)
            copy[i] = linesegments[i];
        return copy;
    }
    private LineSegment sort4(Point p1, Point p2, Point p3, Point p4) {
        Point max = new Point(0, 0);
        Point min = new Point(32767, 32767);
        
        if (p1.compareTo(max) > 0) max = p1;
        if (p2.compareTo(max) > 0) max = p2;
        if (p3.compareTo(max) > 0) max = p3;
        if (p4.compareTo(max) > 0) max = p4;
        
        if (p1.compareTo(min) < 0) min = p1;
        if (p2.compareTo(min) < 0) min = p2;
        if (p3.compareTo(min) < 0) min = p3;
        if (p4.compareTo(min) < 0) min = p4;
        
        LineSegment l = new LineSegment(max, min);
        return l;
    }
    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0; i < segmentNum; i++)
            copy[i] = linesegments[i];
        linesegments = copy;
    }
    /*
    private void printPoints() {
        for (int i = 0; i < pointSet.length; i++)
            StdOut.println(pointSet[i].toString());
    }*/
    private boolean checkIdentical() {
        if (pointSet.length <= 1)
            return false;
        for (int i = 1; i < pointSet.length; i++)
            if (pointSet[i].compareTo(pointSet[i-1]) == 0)
                return true;
        return false;
    }
    private class Quick {
        private void sort(Point[] a) {
            // sort pointSet to find whether two of points are identical
            StdRandom.shuffle(a);
            sort(a, 0, a.length - 1);
        }
        private void sort(Point[] a, int lo, int hi) {
            if (hi <= lo) return;
            int j = partition(a, lo, hi);
            sort(a, lo, j-1);
            sort(a, j+1, hi);
        }        
        private int partition(Point[] a, int lo, int hi) {
            int i = lo;
            int j = hi + 1;
            while (true) {
                while (a[++i].compareTo(a[lo]) < 0)
                    if (i == hi) break;
                while (a[lo].compareTo(a[--j]) < 0)
                    if (j == lo) break;
            
                if (i >= j) break;
                exch(a, i, j);
            }
            exch(a, lo, j);
            return j;
        }
        private void exch(Point[] a, int i, int j) {
            Point tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }
}