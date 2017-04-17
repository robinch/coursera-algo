import edu.princeton.cs.algs4.QuickFindUF;

/**
 * This is my solution to the Percolation problem
 *
 * Some remarks. The index will start from 1 due to convention.
 *
 * @author Robin Chowdhury
 */
public class Percolation {

    private int mSiteMatrixSize;
    private QuickFindUF mUF;
    private int mTopSiteIndex;
    private int mBottomSiteIndex;

    private boolean[][] mOpenSiteMatrix;
    private int mNrOfOpenSites;

    public Percolation(int n) {
        // Should be O(n^2)
        if (n <= 0) throw new IllegalArgumentException();

        mNrOfOpenSites = 0;

        mTopSiteIndex = 0;
        mBottomSiteIndex = n * n + 1;

        mSiteMatrixSize = n;

        initializeGrid(n);
        initializeUF(n);
    }


    private void initializeGrid(int n) {
        mOpenSiteMatrix = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mOpenSiteMatrix[j][i] = false;
            }
        }
    }

    private void initializeUF(int n) {
        mUF = new QuickFindUF(n + 2);
    }

    // open site (row, col) if it is not open already
    void open(int row, int col) {
        validateIndices(row, col);

        if (isOpen(row, col)) return;

        mNrOfOpenSites++;

        openSite(row, col);

        connectSurroundingOpenedSites(row, col);
    }

    private void openSite(int row, int col) {
        mOpenSiteMatrix[convertToJavaIndex(col)][convertToJavaIndex(row)] = true;
    }

    private void connectSurroundingOpenedSites(int row, int col) {
        // Connect with the surrounding sites if they are open

        // Left
        if (col > 1 && isOpen(row, col - 1)) {
            mUF.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }

        // Up
        if (row == 1) { // It's furthest up so connect with the virtual top site
            mUF.union(xyTo1D(row, col), mTopSiteIndex);
        } else if (isOpen(row - 1, col)) {
            mUF.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }

        // Right
        if (col < mSiteMatrixSize && isOpen(row, col + 1)) {
            mUF.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }

        // Down
        if (row == mSiteMatrixSize) { // It's furthest down so connect with the virtual bottom
            mUF.union(xyTo1D(row, col), mBottomSiteIndex);
        } else if (isOpen(row + 1, col)){
            mUF.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);

        return isSiteOpen(row, col);
    }

    private boolean isSiteOpen(int row, int col) {
        return mOpenSiteMatrix[convertToJavaIndex(col)][convertToJavaIndex(row)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);

        return mUF.connected(mTopSiteIndex, xyTo1D(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return mNrOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return mUF.connected(mTopSiteIndex, mBottomSiteIndex);
    }

    private int convertToJavaIndex(int i) {
        return i - 1;
    }

    /**
     * Returns the UF index given the grid indices in physics index format (beginning with 1)
     *
     * @param row row in physics index convention
     * @param col column in physics index convention
     * @return index for the corresponding site in UF
     */
    private int xyTo1D(int row, int col) {
        return mSiteMatrixSize * (row - 1) + col;
    }

    private void validateIndices(int i, int j) {
        validateIndice(i);
        validateIndice(j);
    }

    private void validateIndice(int i) {
        if ((i <= 0) || i > mSiteMatrixSize) throw new IndexOutOfBoundsException();
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(1);

        System.out.println("is 1, 1 open?" + p.isOpen(1,1));
        System.out.println("percolates: " + p.percolates());
        p.open(1, 1);
        System.out.println("open 1,1");

        System.out.println("is 1, 1 open?" + p.isOpen(1,1));
        System.out.println("percolates: " + p.percolates());
    }
}
