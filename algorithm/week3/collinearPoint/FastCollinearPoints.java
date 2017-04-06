import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class FastCollinearPoints {
    private Point[] pointSet;
    private LineSegment[] linesegments;
    private Point[] lineStart;
    private Point[] lineEnd;
    private double[] lineSlope;
    private int linesegmentsCapacity = 1;
    private int pointsNum;
    private int segmentNum = 0;
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException();
        
        pointsNum = points.length;
        pointSet = new Point[pointsNum];
        lineStart = null;
        lineEnd = null;
        lineSlope = null;
        linesegments = null;
        for (int i = 0; i < pointsNum; i++) {
            if (points[i] == null)
                throw new java.lang.NullPointerException();
            pointSet[i] = points[i];
        }
  
        // Check whether two of points are identical
        Merge merge = new Merge();
        merge.sort(pointSet);
        // printPoints();
        if (checkIdentical())
            throw new java.lang.IllegalArgumentException();
        calculate();
        resize(segmentNum);
        // System.out.printf("End constructor()\n");
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
    public int numberOfSegments() {
        return segmentNum;
    }
    public LineSegment[] segments() {
        LineSegment[] copy = new LineSegment[segmentNum];
        for (int i = 0; i < segmentNum; i++)
            copy[i] = linesegments[i];
        return copy;
    }
    private void calculate() {
        Merge merge = new Merge();
        // System.out.printf("Enter calculate()\n");
        // copy the point array first
        Point[] copy = new Point[pointsNum];      
        for (int i = 0; i < pointsNum; i++) { // each p
            for (int j = 0; j < pointsNum; j++)
                copy[j] = pointSet[j];
            // sort the other points according to the slope it makes with p
            merge.sort(copy, pointSet[i].slopeOrder());
            check4Segment(copy, pointSet[i]);            
        }
    }
    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        Point[] copyStartPoint = new Point[capacity];
        Point[] copyEndPoint = new Point[capacity];
        double[] copySlope = new double[capacity];
        for (int i = 0; i < segmentNum; i++) {
            copy[i] = linesegments[i];
            copyStartPoint[i] = lineStart[i];
            copyEndPoint[i] = lineEnd[i];
            copySlope[i] = lineSlope[i];
        }
        linesegments = copy;
        lineStart = copyStartPoint;
        lineEnd = copyEndPoint;
        lineSlope = copySlope;
    }
    private void check4Segment(Point[] a, Point origin) {
        if (a.length < 4)
            return;
        for (int i = 0; i < a.length - 2; i++) {
                // more than four points
            double slope = origin.slopeTo(a[i]);
            boolean isRepeated = false;    
            int k = 1;
            
            while (i+k < a.length) {
                double slope2 = origin.slopeTo(a[i+k]);
                if (slope2 > slope)
                    break;
                k++;
            }
            
            if (k > 2) {  // origin a[i] a[i+1] a[i+2] ... a[i+k-1]
                LineSegment newSegment;
                Point startPoint;
                Point endPoint;
                if (origin.compareTo(a[i]) < 0)
                    startPoint = origin;
                else
                    startPoint = a[i];
                
                if (origin.compareTo(a[i+k-1]) > 0)
                    endPoint = origin;
                else
                    endPoint = a[i+k-1];
                
                // System.out.printf("Test Segment: %s -> %s slope = %f\n",startPoint.toString(), endPoint.toString(), slope);
                
                for (int n = 0; n < segmentNum; n++) {
                    if (startPoint == lineStart[n] && slope == lineSlope[n])
                        isRepeated = true;
                    if (endPoint == lineEnd[n] && slope == lineSlope[n])
                        isRepeated = true;
                }
                if (isRepeated)
                    continue;
                else
                    newSegment = new LineSegment(startPoint, endPoint);
                
                // System.out.printf("Create: %s\n",newSegment.toString());
                
                if (segmentNum == 0) {
                    lineStart = new Point[1];
                    lineEnd = new Point[1];
                    lineSlope = new double[1];
                    linesegments = new LineSegment[1];
                }
                
                lineStart[segmentNum] = startPoint;
                lineEnd[segmentNum] = endPoint;
                lineSlope[segmentNum] = slope;
                linesegments[segmentNum++] = newSegment;
                
                // System.out.printf("Line %d %s\n",segmentNum,l.toString());
                if (segmentNum == linesegmentsCapacity) {
                    linesegmentsCapacity *= 2;
                    resize(linesegmentsCapacity);
                }
                // break;
            }
         
        }
    }
    private boolean checkIdentical() {
        if (pointSet.length <= 1)
            return false;
        for (int i = 1; i < pointSet.length; i++)
            if (pointSet[i].compareTo(pointSet[i-1]) == 0)
                return true;
        return false;
    }
    /* DEBUG
    private void printPoints() {
        for (int i = 0; i < pointSet.length; i++)
            StdOut.println(pointSet[i].toString());
    }
    */
    private class Merge {
        // natural order
        private Point[] copy;
        private void sort(Point[] a) {
            copy = new Point[a.length];
            sort(a, copy, 0, a.length - 1);
        }
        private void sort(Point[] a, Point[] aux, int lo, int hi) {
            if (hi <= lo) return;
            int mid = lo + (hi - lo) / 2;
            sort(a, aux, lo, mid);
            sort(a, aux, mid+1, hi);
            merge(a, aux, lo, mid, hi);
        }        
        private void merge(Point[] a, Point[] aux, int lo, int mid, int hi) {
            for (int k = lo; k <= hi; k++)
                aux[k] = a[k];
            
            int i = lo, j = mid + 1;
            for (int k = lo; k <= hi; k++) {
                if (i > mid)                           a[k] = aux[j++];
                else if (j > hi)                       a[k] = aux[i++];
                else if (aux[j].compareTo(aux[i]) < 0) a[k] = aux[j++];
                else                                   a[k] = aux[i++];
            }
        }
        private void sort(Point[] a, Comparator<Point> c) {
            copy = new Point[a.length];
            sort(a, copy, 0, a.length - 1, c);
        }
        private void sort(Point[] a, Point[] aux, int lo, int hi, Comparator<Point> c) {
            if (hi <= lo) return;
            int mid = lo + (hi - lo) / 2;
            sort(a, aux, lo, mid, c);
            sort(a, aux, mid+1, hi, c);
            merge(a, aux, lo, mid, hi, c);
        }        
        private void merge(Point[] a, Point[] aux, int lo, int mid, int hi, Comparator<Point> c) {
            for (int k = lo; k <= hi; k++) {
                aux[k] = a[k];
            }
            int i = lo, j = mid + 1;
            for (int k = lo; k <= hi; k++) {
                if (i > mid) {
                    a[k] = aux[j++];
                }
                else if (j > hi) {
                    a[k] = aux[i++];
                }
                else if (c.compare(aux[j], aux[i]) < 0) {
                    a[k] = aux[j++];
                }
                else {
                    a[k] = aux[i++];
                }
            }
        }
    }
}