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
        
        // sort array and make copy
        Arrays.sort(point);
        Point[] copy = point;
        
        // find collinear points range: lo to hi
        ArrayList<String> lines = new ArrayList<String>();
        for (int i = 0; i < N; i++)
        {
            Point current = point[i];
            Arrays.sort(copy, current.SLOPE_ORDER);
            
            int lo = 0, hi = N - 1;
            
            for (int j = 0; j < N; j++)
            {
                if (lo == hi || lo > N - 3)
                {
                    break; // no minimum collinear points
                }
                if (current.slopeTo(copy[lo]) == current.slopeTo(copy[lo + 2]))
                {
                    if (current.slopeTo(copy[lo]) == current.slopeTo(copy[hi]))
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
            if ((hi - lo) >= 2) // display if line contains enough points
            {
                output(current, copy, lo, hi, lines);
            }
            // Second Pass
            lo = hi + 1;
            hi = N - 1;
            for (int j = 0; j < N; j++)
            {
                if (lo == hi || lo > N - 3)
                {
                    break; // no minimum collinear points
                }
                if (current.slopeTo(copy[lo]) == current.slopeTo(copy[lo + 2]))
                {
                    if (current.slopeTo(copy[lo]) == current.slopeTo(copy[hi]))
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
            if ((hi - lo) >= 2) // display if line contains enough points
            {
                output(current, copy, lo, hi, lines);
            }
        }
        StdDraw.show();
    }
    
    private static void output(Point current, Point[] copy, int lo, int hi, ArrayList<String> lines)
    {
        // parse the coordinates
        String xy = current.toString();
        xy = xy.substring(1, xy.length()-1);
        String[] coords = xy.split(", ");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        
        // get the slope of the line
        double m = current.slopeTo(copy[hi]);
        
        // calculate y-intercept (y = mx + b -> b = y - mx)
        double b = y - m * x;
        
        // calculate x-intercept (y / m - x)
        double xInt = y / m - x;
        
        // create line string
        String line = "" + m + b + xInt;
        
        // if unique, display
        if (!lines.contains(line))
        {
            lines.add(line);
            
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