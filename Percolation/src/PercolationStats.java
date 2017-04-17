import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * This is my solution to the Percolation problem
 *
 * @author Robin Chowdhury
 * @date 2017-04-17
 *
 */
public class PercolationStats {
    private double[] mPerculationTresholdsOfTrials;
    private int mTrials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        mPerculationTresholdsOfTrials = new double[trials];
        mTrials = trials;

        for (int i = 0; i < trials; i++) {
            mPerculationTresholdsOfTrials[i] = getPerculationTreshold(n);
        }
    }

    private double getPerculationTreshold(int n) {
        Percolation p = new Percolation(n);
        int nrOfSitesOpened = 0;
        int uniformRandomRow;
        int uniformRandomCol;

        while (!p.percolates()) {
            uniformRandomRow = StdRandom.uniform(1, n + 1);
            uniformRandomCol = StdRandom.uniform(1, n + 1);

            if (!p.isOpen(uniformRandomRow, uniformRandomCol)) {
                nrOfSitesOpened++;
                p.open(uniformRandomRow, uniformRandomCol);
            }
        }

        return ((double) nrOfSitesOpened / (double) (n*n));
    }

    /**
     * @return the mean value of the tresholds
     */
    public double mean() {
        return StdStats.mean(mPerculationTresholdsOfTrials);
    }

    // sample standard deviation of percolation threshold

    /**
     * @return @return the standard deviation of the tresholds
     */
    public double stddev() {
        return StdStats.stddev(mPerculationTresholdsOfTrials);
    }

    /**
     *
     * @return low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96* stddev()/Math.sqrt((mTrials));
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + 1.96* stddev()/Math.sqrt((mTrials));

    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.format("95%% confidence interval = [%f, %f]%n", ps.confidenceLo(), ps.confidenceHi());
    }
}
