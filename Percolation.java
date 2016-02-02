
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    
    private WeightedQuickUnionUF percolationTree;
    private boolean[][] grid;
    private int length;
    private boolean blocked = false;
    private boolean open = true;
    private int virtualSite = 1;
    private int virtualSiteTopIndex = 0;
    private int virtualSiteBotIndex;
    private int gridIndexAdjustment = 1;
   // private int closedSites;
    
    public Percolation(int N) {
        
        length = N; 
        virtualSiteBotIndex = length*length + 1;
        if(length <= 0){
            throw new java.lang.IllegalArgumentException("N(length) must be greater than 0!");
        }
        
        // WeightedQuickUnionFindUF object adds two lengths for top and bottom virtual sites
        percolationTree = new WeightedQuickUnionUF(length*length + virtualSite + virtualSite);
        
        grid = new boolean[length][length];
        int k = 0;
       
        for(int i = 0; i < length; i++) {
            
            for(int j = 0; j < length; j++) {
                
               grid[i][j] = blocked;
            }
        }
       
    }
    
    
    public void open(int i, int j) {
        throwIndexException(i,j);
       
        // Indices given are given in the convention such that (1,1) is the top left square (0,0) in the grid 2d array
        grid[i - gridIndexAdjustment][j - gridIndexAdjustment] = open;
        //closedSites = percolationTree.count();
        
        // Non edges
        if(i > 1 && i < length && j > 1 && j < length) {
            // Check for open site upwards
            if(isOpen(i-1,j) == true) {
                percolationTree.union(toUFIndex(i - gridIndexAdjustment - 1, j), toUFIndex(i - gridIndexAdjustment, j));    
            }
            // Check for open site downwards
            if(isOpen(i+1,j) == true) {
                percolationTree.union(toUFIndex(i - gridIndexAdjustment + 1, j), toUFIndex(i - gridIndexAdjustment, j));    
            }
            // Check for open site to the left
            if(isOpen(i,j-1) == true) {
                percolationTree.union(toUFIndex(i - gridIndexAdjustment, j - 1), toUFIndex(i - gridIndexAdjustment, j));    
            }
            // Check for open site to the right
            if(isOpen(i,j+1) == true) {
                percolationTree.union(toUFIndex(i - gridIndexAdjustment, j + 1), toUFIndex(i - gridIndexAdjustment, j));    
            }
            
        }
        // Top row
        else if(i == 1) {
            // Connect to top virual site
           percolationTree.union(virtualSiteTopIndex, (toUFIndex(i - gridIndexAdjustment,j)));
            
            // Left top corner
            if(j == 1) {
                // Check for open site downwards
                 if(isOpen(i+1,j) == true) {
                     percolationTree.union(toUFIndex(i - gridIndexAdjustment + 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                 }
                 // Check for open site to the right
                 if(isOpen(i,j+1) == true) {
                     percolationTree.union(toUFIndex(i - gridIndexAdjustment, j + 1), toUFIndex(i - gridIndexAdjustment, j));    
                 }
            }
            // Right top corner
            else if(j == length) {
                // Check for open site to the left
                if(isOpen(i,j-1) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j - 1), toUFIndex(i - gridIndexAdjustment, j));    
                }
                // Check for open site downwards
                if(isOpen(i+1,j) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment + 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                }
            }
            // In between top 
            else {
                // Check for an open site downwards
                if(isOpen(i+1,j) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment + 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                 }
                // Check for an open site to the right
                if(isOpen(i,j+1) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j + 1), toUFIndex(i - gridIndexAdjustment, j));    
                 }
                 // Check for an open site to the left
                if(isOpen(i,j-1) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j - 1), toUFIndex(i - gridIndexAdjustment, j));    
                 }   
            }
        }
        // Bottom row
        else if (i == length) {
            // Connect to bottom virual site
            percolationTree.union(virtualSiteBotIndex, (toUFIndex(i - gridIndexAdjustment, j)));
            
            // Left bottom corner
            if(j == 1) {
                // Check for open site upwards
                if(isOpen(i-1,j) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment - 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                }
                // Check for open site to the right
                if(isOpen(i,j+1) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j + 1), toUFIndex(i - gridIndexAdjustment, j));    
                }
            }
            // Right bottom corner
            else if(j == length) {
                // Check for open site upwards
                if(isOpen(i- 1,j) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment - 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                }
                // Check for an open site to the left
                if(isOpen(i,j-1) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j - 1), toUFIndex(i - gridIndexAdjustment, j));    
                }   
            }
            // In between bottom 
            else {
                // Check for open site upwards
                if(isOpen(i-1,j) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment - 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                }
                 // Check for an open site to the left
                if(isOpen(i,j-1) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j - 1), toUFIndex(i - gridIndexAdjustment, j));    
                }
                // Check for open site to the right
                if(isOpen(i,j+1) == true) {
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j + 1), toUFIndex(i - gridIndexAdjustment, j));    
                }
            }
        }
       
    }
    
    public boolean isOpen(int i, int j) { 
        throwIndexException(i,j);
        
        if(grid[i-gridIndexAdjustment][j-gridIndexAdjustment] == open) {
            return true;
        }
        return false;
    }
    
    public boolean isFull(int i, int j) {
        throwIndexException(i,j);
        
        return percolationTree.connected(virtualSiteTopIndex, toUFIndex(i - gridIndexAdjustment,j)); 
    }
    
    public boolean percolates() {
        return percolationTree.connected(virtualSiteTopIndex, virtualSiteBotIndex);
    }
    
    // Test class with main
    public static void main(String[] args) {
        int length = 20;
        int numSites = length*length;
        Percolation grid = new Percolation(length);
        
        int numOpenSites = 0;
        
        while(true) {
            int a = StdRandom.uniform(1, length + 1);
            int b = StdRandom.uniform(1, length + 1);
            
            if(!grid.isOpen(a,b)) {
                grid.open(a,b);
                numOpenSites++;
            }
            
            if(grid.percolates() == true) {
                System.out.println("The number of open sites is " + numOpenSites);
                System.out.println("The percolation threshold is: " + ((double)numOpenSites/numSites));
                break;
            }
            
        }
        
    }
    
    // Converts the grid 2d array index to an index for the 1d weighted union find array
    private int toUFIndex(int i, int j) {
        int ufIndex = ( (length * i ) + j);
        return ufIndex;
    }
    
    private void throwIndexException(int i, int j) {
         if( i < 1 || i > length || j < 1 || j > length) {
            throw new IndexOutOfBoundsException("Indices start at 1 and are at most 'length' ");
        }
    }
    
}