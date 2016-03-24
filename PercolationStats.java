import edu.princeton.cs.algs4.*;

public class PercolationStats { 
    
    private int numOfExperiments; 
    private double[] fractionOpenSites;
    
    public PercolationStats(int N, int T){
        if( N <= 0 || T <= 0){
            throw new IllegalArgumentException("Grid size(N) must be at least 1, amount of experiments(T) must be at least 1.");
        }
        int lengthOfGridSide = N;
        numOfExperiments = T;
        fractionOpenSites = new double[numOfExperiments];
        
        int numSites = lengthOfGridSide*lengthOfGridSide;
        
        for(int i = 0; i < numOfExperiments; i++){
            
            Percolation grid = new Percolation(lengthOfGridSide);
            int numOpenSites = 0;
            while(true){
                int a = StdRandom.uniform(1, lengthOfGridSide + 1);
                int b = StdRandom.uniform(1, lengthOfGridSide + 1);
                
                if(grid.isOpen(a,b) == false){
                    grid.open(a,b);
                    numOpenSites++;
                }
                
                if(grid.percolates() == true){
                    double openSitesForThisExp = ((double)numOpenSites/numSites);
                    fractionOpenSites[i] = openSitesForThisExp;
                    break;
                }
                
            }
        }
    }
    
    public double mean(){
        double avg = StdStats.mean(fractionOpenSites);
        return avg;
    }
    
    public double stddev(){
        double standardDev = StdStats.stddev(fractionOpenSites);
        return standardDev;
    }
    
    public double confidenceLo(){
        double confidenceLoFraction = (1.96*this.stddev())/Math.sqrt(numOfExperiments);
        double confLo = (this.mean() - confidenceLoFraction);
        return confLo;
    }
    
    public double confidenceHi(){
        double confidenceHiFraction = (1.96*this.stddev())/Math.sqrt(numOfExperiments);
        double confHi = (this.mean() + confidenceHiFraction);
        return confHi;
    }
    
    public static void main(String[] args){
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
        
    }
    
    
}