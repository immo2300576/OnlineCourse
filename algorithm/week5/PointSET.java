import java.util.TreeSet;

// brute force
// RB tree datastructure containing Points
public class PointSET {
	private int numPoints = 0;
	TreeSet<Point2D> tree = null;
	public PointSET() {
		tree = new TreeSet<Point2D>();
	}
	public boolean isEmpty() {
		return numPoints == 0;
	}
	public void insert(Point2D p) { // O(logn)
		tree.add(p);
	}
	public boolean contains(Point2D p) { // O(logn)
		return tree.contains(p);
	}
	public void draw() { // draw all points to standard draw
		
	}
	public Iterable<Point2D> range(RectHV rect) { // time complexity O(n)
		Stack<Point2D> stack = new Stack<Point>();
		for (Point2D p : tree) {
			if (p.x()>=rect.xmin && p.x()<=rect.xmax && p.y()>=rect.ymin && p.y()<=rect.ymax)
				stack.push(p);
		}
		return stack;
	}
	public Point2D nearest(Point2D p) { //time complexity O(n)
		int min = Integer.MAX_VALUE;
		Point2D nn;
		for (Point2D this : tree) {
			if (this.distanceTo(p)<min)
				nn = this;
		}
		return nn;
	}
	public static void main(String[] args) {
	
	}
}