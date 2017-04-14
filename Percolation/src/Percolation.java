import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * This is my solution to the Percolation problem
 *
 * Some remarks. The index will start from 1 due to convention.
 *
 * @author Robin Chowdhury
 */
public class Percolation {

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException ();

    }

    // open site (row, col) if it is not open already
    void open(int row, int col) {
        throw new NotImplementedException();
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        throw new NotImplementedException();
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        throw new NotImplementedException();
    }

    // number of open sites
    public int numberOfOpenSites() {
        throw new NotImplementedException();
    }

    // does the system percolate?
    public boolean percolates() {
        throw new NotImplementedException();
    }


    public static void main(String[] args) {
        System.out.println("Hello!");
    }
}
