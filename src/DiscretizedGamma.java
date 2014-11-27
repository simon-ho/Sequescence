package Sequescence;

import java.util.*;

public class DiscretizedGamma {

    private static Random rn = new Random();
    
    // Creates a new instance of DiscretizedGamma
    public static double[] DiscretizedGamma(double alpha) {
        double[] rates = new double[8];             /* Array containing means of gamma categories */
        double[] variates = new double[1000000];    /* Array containing gamma-distributed variates */
        double u1 = 0, u2 = 0;                      /* Uniform RVs */
        double a = 0, b = 0;                        /* Variables used when alpha > 1 */
        double c = 0;                               /* Variable used for several purposes */
        double x = 0, y = 0;                        /* Variables */
        double cattot = 0, ratetot = 0;             /* Total storers */
        int i = 0, j = 0, s = 0;                    /* Counters */

        System.out.println("\nAlpha shape parameter is: " + alpha);
        
        // Fill an array with 1000000 gamma-distributed variates

        if (alpha == 0) {
            for (i = 0; i < 8; i++) {
                rates[i] = 1;
            }
            return rates;
        }
        else {
            System.out.println("Calculating means of gamma categories ... ");

            if (alpha < 1) {
                for (i = 0; i < 1000000; i++) {
                    s = 0;
                    while (s == 0) {
                        u1 = rn.nextDouble();
                        u2 = rn.nextDouble();
                        c = 1 + (alpha/Math.E);
                        if (u1 <= (1/c)) {
                            x = Math.pow((c*u1), (1/alpha));
                            y = Math.exp(-x);
                        }
                        else {
                            x = -Math.log(c*(1-u1)/alpha);
                            y = Math.pow(x, (alpha-1));
                        }
                        if (u2 < y) {
                            variates[i] = x;
                            s = 1;
                        }
                    }
                }                
            }
            else if (alpha <= 2.5) {
                if (alpha == 1) {
                    alpha = 1.0001;
                }
                for (i = 0; i < 1000000; i++) {
                    a = Math.pow(((alpha-1)/Math.E), ((alpha-1)/2));
                    b = Math.pow(((alpha+1)/Math.E), ((alpha+1)/2));
                    s = 0;
                    while (s == 0) {
                        c = 0;
                        u1 = a*rn.nextDouble();
                        u2 = b*rn.nextDouble();
                        y = u1/u2;
                        c = 2*Math.log(u1) - ((alpha-1)*Math.log(y)) + y;
                        if (c <= 1) {
                            variates[i] = y;
                            s = 1;
                        }
                    }
                }
            }
            else {
                for (i = 0; i < 1000000; i++) {
                    s = 0;
                    while (s == 0) {
                        a = alpha - (1/(6*alpha));
                        u1 = rn.nextDouble();
                        u2 = rn.nextDouble();
                        do {
                            u1 = u2 + (1 - (1.86*u1))/(Math.sqrt(alpha));
                        } while ((u1 <= 0) || (u1 >= 1));
                        y = a*(u2/u1);
                        c = 2*Math.log(u1) - (((alpha-1)*Math.log(y))/(alpha-1)) + y;
                        if (c <= 1) {
                            variates[i] = y;
                            s = 1;
                        }
                    }
                }
            }

            // Sort array, then divide into eight categories and take the mean of each category

            Arrays.sort(variates);
            for (i = 0; i < 8; i++) {
                cattot = 0;
                for (j = 0; j < 125000; j++) {
                    cattot = cattot + variates [(i*125000) + j];
                }
                rates[i] = (cattot/125000);
            }

            // Convert means of rate categories into probabilities

            for (i = 0; i < 8; i++) {
                ratetot = ratetot + rates[i];
            }
            for (i = 0; i < 8; i++) {
                rates[i] = ((rates[i])/ratetot);
            }        

            return rates;
        }
    }
}
