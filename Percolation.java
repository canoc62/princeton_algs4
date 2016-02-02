
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.*;

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
    private int closedSites;
    
    public Percolation(int N){
        
        length = N; 
        virtualSiteBotIndex = length*length + 1;
        if(length <= 0){
            throw new java.lang.IllegalArgumentException("N(length) must be greater than 0!");
        }
        
        // WeightedQuickUnionFindUF object adds two lengths for top and bottom virtual sites
        percolationTree = new WeightedQuickUnionUF(length*length + virtualSite + virtualSite);
        
        closedSites = percolationTree.count();
            
        grid = new boolean[length][length];
        int k = 0;
       
        for(int i = 0; i < length; i++){
            
            for(int j = 0; j < length; j++){
                
               grid[i][j] = blocked;
            }
        }
       
    }
    
    public void open(int i, int j){
        
        if( i < 1 || i > length || j < 1 || j > length){
            throw new IndexOutOfBoundsException("Indices start at 1 and are at most 'length' ");
        }
        // Indices given are given in the convention such that (1,1) is the top left square (0,0) in the grid 2d array
        grid[i - gridIndexAdjustment][j - gridIndexAdjustment] = open;
        //closedSites = percolationTree.count();
        
        // Non edges
        if(i > 1 && i < length && j > 1 && j < length){
            // Check for open site upwards
            if(isOpen(i - gridIndexAdjustment - 1,j - gridIndexAdjustment) == true){
                percolationTree.union(toUFIndex(i - gridIndexAdjustment - 1, j), toUFIndex(i - gridIndexAdjustment, j));    
            }
            // Check for open site downwards
            if(isOpen(i - gridIndexAdjustment + 1,j - gridIndexAdjustment) == true){
                percolationTree.union(toUFIndex(i - gridIndexAdjustment + 1, j), toUFIndex(i - gridIndexAdjustment, j));    
            }
            // Check for open site to the left
            if(isOpen(i - gridIndexAdjustment,j - gridIndexAdjustment - 1) == true){
                percolationTree.union(toUFIndex(i - gridIndexAdjustment, j - 1), toUFIndex(i - gridIndexAdjustment, j));    
            }
            // Check for open site to the right
            if(isOpen(i - gridIndexAdjustment,j - gridIndexAdjustment + 1) == true){
                percolationTree.union(toUFIndex(i - gridIndexAdjustment, j + 1), toUFIndex(i - gridIndexAdjustment, j));    
            }
            
        }
        // Top row
        else if(i == 1){
            // Connect to top virual site
           percolationTree.union(virtualSiteTopIndex, (toUFIndex(i - gridIndexAdjustment,j)));
            
            // Left top corner
            if(j == 1){
                // Check for open site downwards
                 if(isOpen(i - gridIndexAdjustment + 1,j - gridIndexAdjustment) == true){
                     percolationTree.union(toUFIndex(i - gridIndexAdjustment + 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                 }
                 // Check for open site to the right
                 if(isOpen(i - gridIndexAdjustment,j - gridIndexAdjustment + 1) == true){
                     percolationTree.union(toUFIndex(i - gridIndexAdjustment, j + 1), toUFIndex(i - gridIndexAdjustment, j));    
                 }
            }
            // Right top corner
            else if(j == length){
                // Check for open site to the left
                if(isOpen(i - gridIndexAdjustment,j - gridIndexAdjustment - 1) == true){
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j - 1), toUFIndex(i - gridIndexAdjustment, j));    
                }
                // Check for open site downwards
                if(isOpen(i - gridIndexAdjustment + 1,j - gridIndexAdjustment) == true){
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment + 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                }
            }
            // In between top 
            else{
                // Check for an open site downwards
                if(isOpen(i - gridIndexAdjustment + 1,j - gridIndexAdjustment) == true){
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment + 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                 }
                // Check for an open site to the right
                if(isOpen(i - gridIndexAdjustment,j - gridIndexAdjustment + 1) == true){
                     percolationTree.union(toUFIndex(i - gridIndexAdjustment, j + 1), toUFIndex(i - gridIndexAdjustment, j));    
                 }
                 // Check for an open site to the left
                if(isOpen(i - gridIndexAdjustment,j - gridIndexAdjustment - 1) == true){
                     percolationTree.union(toUFIndex(i - gridIndexAdjustment, j - 1), toUFIndex(i - gridIndexAdjustment, j));    
                 }   
            }
        }
        // Bottom row
        else if (i == length){
            // Connect to bottom virual site
            percolationTree.union(virtualSiteBotIndex, (toUFIndex(i - gridIndexAdjustment, j)));
            
            // Left bottom corner
            if(j == 1){
                // Check for open site upwards
                if(isOpen(i - gridIndexAdjustment - 1,j - gridIndexAdjustment) == true){
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment - 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                }
                // Check for open site to the right
                if(isOpen(i - gridIndexAdjustment,j - gridIndexAdjustment + 1) == true){
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j + 1), toUFIndex(i - gridIndexAdjustment, j));    
                }
            }
            // Right bottom corner
            else if(j == length){
                // Check for open site upwards
                if(isOpen(i - gridIndexAdjustment - 1,j - gridIndexAdjustment) == true){
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment - 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                }
                // Check for an open site to the left
                if(isOpen(i - gridIndexAdjustment,j - gridIndexAdjustment - 1) == true){
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j - 1), toUFIndex(i - gridIndexAdjustment, j));    
                }   
            }
            // In between bottom 
            else{
                // Check for open site upwards
                if(isOpen(i - gridIndexAdjustment - 1,j - gridIndexAdjustment) == true){
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment - 1, j), toUFIndex(i - gridIndexAdjustment, j));    
                }
                 // Check for an open site to the left
                if(isOpen(i - gridIndexAdjustment,j - gridIndexAdjustment - 1) == true){
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j - 1), toUFIndex(i - gridIndexAdjustment, j));    
                }
                // Check for open site to the right
                if(isOpen(i - gridIndexAdjustment,j - gridIndexAdjustment + 1) == true){
                    percolationTree.union(toUFIndex(i - gridIndexAdjustment, j + 1), toUFIndex(i - gridIndexAdjustment, j));    
                }
            }
        }
        closedSites = percolationTree.count();
    }
    
    public boolean isOpen(int i, int j){
        if(grid[i][j] == open){
            return true;
        }
        return false;
    }
    
    public boolean isFull(int i, int j){
        if(i < 1 || i > length || j < 1 || j > length){
            throw new IndexOutOfBoundsException("Indices start at 1 and are at most 'length' ");
        }
        else{
            return percolationTree.connected(virtualSiteTopIndex, toUFIndex(i - gridIndexAdjustment,j));
        }
    }
    
    public boolean percolates(){
        return percolationTree.connected(virtualSiteTopIndex, virtualSiteBotIndex);
    }
    
    public double getNumClosedSites(){
        return closedSites;
    }
    
    // Test class with main
    public static void main(String[] args){
        int length = 100;
        int numSites = length*length;
        Percolation grid = new Percolation(length);
        
        while(true){
            int a = StdRandom.uniform(1, length + 1);
            int b = StdRandom.uniform(1, length + 1);
            
            
            grid.open(a,b);
            
            if(grid.percolates() == true){
                System.out.println("The number of open sites is " + (numSites - grid.getNumClosedSites()));
                System.out.println("The percolation threshold is: " + (numSites - grid.getNumClosedSites())/numSites );
                break;
            }
            
        }
        
    }
    
    // Converts the grid 2d array index to an index for the 1d weighted union find array
    private int toUFIndex(int i, int j){
        int ufIndex = ( (length * i ) + j);
        return ufIndex;
    }
    
}