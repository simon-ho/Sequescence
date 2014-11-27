package Sequescence;

import java.util.*;

public class PoissonGenerator {

    private static Random rn = new Random();           
    
    public static int PoissonGenerator(double damagerate, int seqlength) {
        int numdamage = 0;      // Number of damaged sites
        double a = 0, b = 0;    // Misc. variables
        double rannum = 0;      // Uniform RV
        
        a = Math.exp(-damagerate*seqlength);
        b = 1;
        numdamage = -1;
        while (b > a) {
            rannum = rn.nextDouble();
            b = b * rannum;
            numdamage = numdamage + 1;        
        }
        return numdamage;
    }
}
