import java.util.Arrays;

public class Brute
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
        
        // check the slopes
        for (int i = 0; i < N; i++)
        {
            for (int j = i + 1; j < N; j++)
            {
                double slope1 = point[i].slopeTo(point[j]);
                for (int m = j + 1; m < N; m++)
                {
                    double slope2 = point[i].slopeTo(point[m]);
                    for (int n = m + 1; n < N; n++)
                    {
                        double slope3 = point[i].slopeTo(point[n]);
                        double negInf = Double.NEGATIVE_INFINITY;
                        if (slope1 != negInf && slope2 != negInf && slope3 != negInf)
                        {
                            if (slope1 == slope2 && slope2 == slope3)
                            {
                                System.out.println(point[i] + " -> " + point[j] + " -> " + point[m] + " -> " + point[n]);
                                point[i].drawTo(point[n]);
                            }
                        }
                    }
                }
            }
        }
        StdDraw.show();
    }
}