import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * This is my solution to the Percolation problem
 *
 * @author Robin Chowdhury
 * @date 2017-04-17
 *
 */
public class Percolation {

    private int mSiteMatrixSize;
    private WeightedQuickUnionUF mUF;
    private int mTopSiteIndex;
    private int mBottomSiteIndex;

    private boolean[][] mOpenSiteMatrix;
    private int mNrOfOpenSites;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        mNrOfOpenSites = 0;

        mTopSiteIndex = 0;
        mBottomSiteIndex = n * n + 1;

        mSiteMatrixSize = n;

        initializeGrid(n);
        initializeUF(n * n + 2); // all sites + virtual top and bottom
    }


    private void initializeGrid(int n) {
        mOpenSiteMatrix = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mOpenSiteMatrix[j][i] = false;
            }
        }
    }

    private void initializeUF(int size) {
        mUF = new WeightedQuickUnionUF(size);
    }

    /**
     * Opens the site if it's not already opened
     * @param row
     * @param col
     */
    public void open(int row, int col) {
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

        // Right
        if (col < mSiteMatrixSize && isOpen(row, col + 1)) {
            mUF.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }

        // Up
        if (row == 1) { // It's furthest up so connect with the virtual top site
            mUF.union(xyTo1D(row, col), mTopSiteIndex);
        } else if (isOpen(row - 1, col)) {
            mUF.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }

        // Down
        if (row == mSiteMatrixSize) { // It's furthest down so connect with the virtual bottom
            mUF.union(xyTo1D(row, col), mBottomSiteIndex);
        } else if (isOpen(row + 1, col)){
            mUF.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
    }

    /**
     * Checks if the site is open
     * @param row
     * @param col
     * @return true if site is open otherwise false
     */
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);

        return isSiteOpen(row, col);
    }

    private boolean isSiteOpen(int row, int col) {
        return mOpenSiteMatrix[convertToJavaIndex(col)][convertToJavaIndex(row)];
    }

    /**
     * See if the given site is full.
     * Full means that the site is connected to an opened site on the top layer
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        validateIndices(row, col);

        return mUF.connected(mTopSiteIndex, xyTo1D(row, col));
    }


    /**
     *
     * @return number of opened sites
     */
    public int numberOfOpenSites() {
        return mNrOfOpenSites;
    }

    /**
     * It percolates if there are opened sites that connects the top with the bottom layer
     * @return returns true if it percolates otherwise false;
     */
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

    private void validateIndices(int row, int col) {
        validateIndex(row);
        validateIndex(col);
    }

    private void validateIndex(int i) {
        if (i <= 0 || i > mSiteMatrixSize) throw new IndexOutOfBoundsException("index i out of bounds");
    }
}
