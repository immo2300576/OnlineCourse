import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] openBlock;
    //private WeightedQuickUnionUF uf;
    //private WeightedQuickUnionUF uf2;
    private MyWeightedUF uf;
    private MyWeightedUF uf2;
    private int blockWidth;
    private int openBlockNumber;
    private boolean percolates;
    
    public Percolation(int n) {
        // corner case (n<0)
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();
        
        uf = new MyWeightedUF(n*n+2); //check percolates
        uf2 = new MyWeightedUF(n*n+1); //check isFull
        openBlock = new boolean[n*n];
        blockWidth = n;
        openBlockNumber = 0;
        for (int i = 0; i < n*n; i++)
            openBlock[i] = false;
    }
    public void open(int row, int col) {
        
        // check corner case
        checkCorner(row, col);
        
        int block = (row-1)*blockWidth+col-1;
        if (isOpen(row, col)) return;
        // open blocks=> _block[]=self, _weight[]=1
        openBlock[block] = true;
        openBlockNumber++;
        
        // four direction connect
        if (row > 1)
            if (isOpen(row-1, col)) {
                uf.union(block, block-blockWidth);
                uf2.union(block, block-blockWidth);
            }
        if (row < blockWidth)
            if (isOpen(row+1, col)) {
                uf.union(block, block+blockWidth);
                uf2.union(block, block+blockWidth);
            }
        if (col > 1)
            if (isOpen(row, col-1)) {
                uf.union(block, block-1);
                uf2.union(block, block-1);
            }
        if (col < blockWidth)
            if (isOpen(row, col+1)) {
                uf.union(block, block+1);
                uf2.union(block, block+1);
            }
        
        // top case
        if (row == 1) {
            uf.union(block, blockWidth*blockWidth);
            uf2.union(block, blockWidth*blockWidth);
        }
      
        if (row == blockWidth)
            uf.union(block, blockWidth*blockWidth+1);                                                        
    }
    public boolean isOpen(int row, int col) {
        // check corner case
        checkCorner(row, col);
        // return if the block is already open
        return openBlock[(row-1)*blockWidth+col-1];
    }
    public boolean isFull(int row, int col) {        
        // check corner case
        checkCorner(row, col);
        
        if (!isOpen(row, col)) return false;
        
        return uf2.connected((row-1)*blockWidth+col-1, blockWidth*blockWidth);
    }
    public int numberOfOpenSites() {
        
        return openBlockNumber;
    }
    public boolean percolates() {
        if (!percolates) {
            percolates = uf.connected(blockWidth*blockWidth, blockWidth*blockWidth+1);
        }
        return percolates;
    }    
    // check corner case
    private void checkCorner(int row, int col) {
        // System.out.printf("[Check Corner] (%d,%d)\n", row, col);
        if (col < 1 || col > blockWidth || row < 1 || row > blockWidth)
            throw new IndexOutOfBoundsException();
    }
}