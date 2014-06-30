import java.util.Arrays;
import java.util.ArrayList;

public class Fast
{
    public static void main(String[] args)
    {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(.008); // for larger points
        StdDraw.show(0);
        
        // read in the input
        In input = new In(args[0]);
        int N = input.readInt();
        Point[] point = new Point[N];
        for (int i = 0; i < N; i++)
        {
            Point np = new Point(input.readInt(), input.readInt());
            point[i] = np;
            np.draw();
        }
        StdDraw.setPenRadius(); // for thinner lines
        
        // sort array
        Arrays.sort(point);
        
        // find collinear points range: lo to hi
        ArrayList<Double> slopes = new ArrayList<Double>();
        ArrayList<Double> yIntercepts = new ArrayList<Double>();
        ArrayList<Double> xIntercepts = new ArrayList<Double>();
        for (int i = 0; i < N; i++)
        {
            Point[] copy = point;
            Arrays.sort(copy, copy[i].SLOPE_ORDER);
            
            int lo = 0, hi = N - 1;
            for (int j = 0; j < N; j++)
            {
                if (lo == hi || lo > N - 3)
                {
                    break; // no collinear points
                }
                if (copy[0].slopeTo(copy[lo]) == copy[0].slopeTo(copy[lo + 2]))
                {
                    if (copy[0].slopeTo(copy[lo]) == copy[0].slopeTo(copy[hi]))
                    {
                        break; // collinear range found
                    }
                    else
                    {
                        hi--;
                    }
                }
                else
                {
                    lo++;
                }
            }
            if ((hi - lo) >= 2) // check if collinear of four points
            {
                // parse the coordinates
                String xy = copy[0].toString();
                xy = xy.substring(1, xy.length()-1);
                String[] coords = xy.split(", ");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                
                // find the slope of the line
                double m = copy[0].slopeTo(copy[hi]);
                
                // find y intercept (y = mx + b -> b = y - mx)
                double b = y - m * x;
                
                // find x intercept (y / m - x)
                double xInt = y / m - x;
                                                
                // check if unique line
                boolean display = false;
                if (!slopes.contains(m))
                {
                    slopes.add(m);
                    yIntercepts.add(b);
                    xIntercepts.add(xInt);
                    display = true;
                }
                else if (!yIntercepts.contains(b))
                {
                    yIntercepts.add(b);
                    xIntercepts.add(xInt);
                    display = true;
                }
                else if (!xIntercepts.contains(xInt))
                {
                    xIntercepts.add(xInt);
                    display = true;
                }
                                
                // if unique, display
                if (display)
                {
                    // copy collinear points to output array, sort, print, and draw
                    Point[] output = new Point[(hi - lo) + 2];
                    output[0] = copy[0];
                    for (int j = 1; j < output.length; j++)
                    {
                        output[j] = copy[lo++];
                    }
                    Arrays.sort(output);
                    for (int j = 0; j < output.length - 1; j++)
                    {
                        System.out.print(output[j] + " -> ");
                    }
                    System.out.println(output[output.length - 1]);
                    output[0].drawTo(output[output.length - 1]);
                }
            }
        }
        StdDraw.show();
    }
}