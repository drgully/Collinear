import java.util.Comparator;

public class Point implements Comparable<Point>
{
    // compare points by slope to this point
    public final Comparator<Point> SLOPE_ORDER;
    private final int x;
    private final int y;
    
    // construct the point (x, y)
    public Point(int x, int y)
    {
        SLOPE_ORDER = new SlopeOrder();
        this.x = x;
        this.y = y;
    }
    
    // draw this point
    public void draw()
    {
        StdDraw.point(x, y);
    }
    
    // draw the line segment from this point to that point
    public void drawTo(Point that)
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    // string representation
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
    
    // is this point lexicographically smaller than that point?
    public int compareTo(Point that)
    {
        if (this.y < that.y)
        {
            return -1;
        }
        else if (this.y > that.y)
        {
            return 1;
        }
        else
        {
            if (this.x < that.x)
            {
                return -1;
            }
            else if (this.x > that.x)
            {
                return 1;
            }
        }
        return 0;
    }
    
    // the slope between this point and that point
    public double slopeTo(Point that)
    {
        if (this.x != that.x && this.y != that.y)
        {
            return (double) (that.y - this.y)/(that.x - this.x);
        }
        else if (this.x == that.x && this.y != that.y)
        {
            return Double.POSITIVE_INFINITY;
        }
        else if (this.x != that.x && this.y == that.y)
        {
            return 0.0;
        }
        return Double.NEGATIVE_INFINITY;
    }
    
    // initialize the comparator
    private class SlopeOrder implements Comparator<Point>
    {
         public int compare(Point v, Point w)
        {
            double vSlope = slopeTo(v);
            double wSlope = slopeTo(w);
            if (vSlope > wSlope)
            {
                return 1;
            }
            else if (vSlope < wSlope)
            {
                return -1;
            }
            return 0;
        }
    }
}