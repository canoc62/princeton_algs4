
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.*;
import java.lang.*;
//import java.util.*;

public class Percolation {
    
    private WeightedQuickUnionUF percolationTree;
    private boolean[][] grid;
    private int size;
    private boolean blocked = false;
    private boolean open = true;
    private int virtualSite = 1;
    private int virtualSiteTopIndex = 0;
    private int gridIndexAdjustment = 1;
    private int closedSites;
    
    public Percolation(int N){
        
        size = N; 
        if(size <= 0){
            throw new java.lang.IllegalArgumentException("N must be greater than 0!");
        }
        
        // WeightedQuickUnionFindUF object adds two lengths for top and bottom virtual sites
        percolationTree = new WeightedQuickUnionUF(size*size + virtualSite + virtualSite);
        
        closedSites = percolationTree.count();
            
        grid = new boolean[size][size];
        int k = 0;
       
        for(int i = 0; i < size; i++){
            
            for(int j = 0; j < size; j++){
                
               grid[i][j] = blocked;
            }
        }
        
        /*for( int i = 1; i <= size; i++){
            percolationTree.union(virtualSiteTopIndex, (toUFIndex(size, 1 - gridIndexAdjustment,i - gridIndexAdjustment)));
            percolationTree.union(size, (toUFIndex(size, size - gridIndexAdjustment, i - gridIndexAdjustment)));
        }*/
    }
    
    public void open(int i, int j){
        
        if( i < 1 || i > size || j < 1 || j > size){
            throw new IndexOutOfBoundsException("Indices start at 1 and are at most 'size' ");
        }
        // Indices given are given in the convention such that (1,1) is the top left square (0,0) in the grid 2d array
        grid[i - gridIndexAdjustment][j - gridIndexAdjustment] = open;
        //closedSites = percolationTree.count();
        
        // Non edges
        if(i > 1 && i < size && j > 1 && j < size){
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
            else if(j == size){
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
        else if (i == size){
            // Connect to bottom virual site
            percolationTree.union((size*size + 1), (toUFIndex(i - gridIndexAdjustment, j)));
            
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
            else if(j == size){
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
       return percolationTree.connected(virtualSiteTopIndex, toUFIndex(i - gridIndexAdjustment,j));
    }
    
    public boolean percolates(){
        return percolationTree.connected(virtualSiteTopIndex, size*size + 1);
    }
    
    public double getNumClosedSites(){
        return closedSites;
    }
    
    public static void main(String[] args){
        int size = 20;
        int numSites = size*size;
        Percolation grid = new Percolation(size);
        
        while(true){
            int a = StdRandom.uniform(1, size + 1);
            int b = StdRandom.uniform(1, size + 1);
            
            
            grid.open(a,b);
            
            if(grid.percolates() == true){
                System.out.println("The number of open sites is " + (numSites - grid.getNumClosedSites()));
                System.out.println("The percolation threshold is: " + (numSites - grid.getNumClosedSites())/numSites );
                break;//System.out.println("Hello");
                //return ;//"Hello";
            }
            
        }
        
    }
    
    // Converts the grid 2d array index to an index for the 1d weighted union find array
    private int toUFIndex(int i, int j){
        int ufIndex = ( (size * i ) + j);
        return ufIndex;
    }
    
}