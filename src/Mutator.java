package Sequescence;

import java.util.*;
import java.lang.String;

public class Mutator {

    private static Random rn = new Random();       
    
    public static String Mutator(String seq, int numdamage, double type1dam, double type2dam, double[] rates) {

        int s = 0;                              // A counter
        int len = 0;                            // Sequence length
        int damagedsite = 0;                    // Site number of damaged base
        int type1 = 0, type2 = 0, type3 = 0;    // Damage counters
        int i = 0;                              // Counter
        int currentcat = 0;                     // Current rate category
        double ransite = 0;                     // Random site to be mutated
        double rannum = 0;                      // A random number to determine type of damage
        double ratecatfloor = 0;                // Variable representing boundary of rate category
        char base = 'A';                        // Identity of damaged base
        
        len = seq.length();       

        StringBuffer buffer = new StringBuffer(seq);

        if (rates[0] > 0.99) {
            for (s = 0; s <= numdamage-1; s++) {
                ransite = len*rn.nextDouble();
                damagedsite = (int)ransite;
                base = buffer.charAt(damagedsite);
                while (base == buffer.charAt(damagedsite)) {
                    rannum = rn.nextDouble();
                    if (rannum < type2dam) {
                        while ((buffer.charAt(damagedsite) == 'T') || (buffer.charAt(damagedsite) == 'A')) {
                            damagedsite = (int)(len*rn.nextDouble());
                        }
                        if (buffer.charAt(damagedsite) == 'C') {
                            base = 'T';
                            type1 = type1 + 1;
                            System.out.println("Site " + damagedsite + ", C->T, type II");
                        }
                        else {
                            base = 'A';
                            type1 = type1 + 1;
                            System.out.println("Site " + damagedsite + ", G->A, type II");
                        }
                    }
                    else if (rannum < (type2dam + type1dam)){
                        while ((buffer.charAt(damagedsite) == 'G') || (buffer.charAt(damagedsite) == 'C')) {
                            damagedsite = (int)(len*rn.nextDouble());
                        }
                        if (buffer.charAt(damagedsite) == 'A') {
                            base = 'G';
                            type2 = type2 + 1;
                            System.out.println("Site " + damagedsite + ", A->G, type I");
                        }
                        else {
                            base = 'C';
                            type2 = type2 + 1;
                            System.out.println("Site " + damagedsite + ", T->C, type I");
                        }
                    }
                    else {
                        type3 = type3 + 1;
                        if (base == 'A') {
                            if (rn.nextDouble() > 0.5) {
                                base = 'C';
                                System.out.println("Site " + damagedsite + ", A->C, transversion");
                            }
                            else {
                                base = 'T';
                                System.out.println("Site " + damagedsite + ", A->T, transversion");
                            }
                        }
                        else if (base == 'C') {
                            if (rn.nextDouble() > 0.5) {
                                base = 'A';
                                System.out.println("Site " + damagedsite + ", C->A, transversion");
                            }
                            else {
                                base = 'G';
                                System.out.println("Site " + damagedsite + ", C->G, transversion");
                            }
                        }
                        else if (base == 'G') {
                            if (rn.nextDouble() > 0.5) {
                                base = 'C';
                                System.out.println("Site " + damagedsite + ", G->C, transversion");
                            }
                            else {
                                base = 'T';
                                System.out.println("Site " + damagedsite + ", G->T, transversion");
                            }
                        }
                        else {
                            if (rn.nextDouble() > 0.5) {
                                base = 'A';
                                System.out.println("Site " + damagedsite + ", T->A, transversion");
                            }
                            else {
                                base = 'G';
                                System.out.println("Site " + damagedsite + ", T->G, transversion");
                            }
                        }
                    }
                }
                buffer.setCharAt(damagedsite, base);
            }            
        }
        else {
            for (s = 0; s <= numdamage-1; s++) {
                currentcat = 0;
                ratecatfloor = 0;
                rannum = rn.nextDouble();
                for (i = 0; i < 8; i++) {
                    ratecatfloor = ratecatfloor + rates[i];
                    if (rannum >= ratecatfloor) {
                        currentcat = (i+1);
                    }
                }
                ransite = (currentcat*0.125*len) + (0.125*len*rn.nextDouble());
                damagedsite = (int)ransite;
                base = buffer.charAt(damagedsite);
                while (base == buffer.charAt(damagedsite)) {
                    rannum = rn.nextDouble();
                    if (rannum < type2dam) {
                        while ((buffer.charAt(damagedsite) == 'T') || (buffer.charAt(damagedsite) == 'A')) {
                            damagedsite = (int)((currentcat*0.125*len) + (0.125*len*rn.nextDouble()));
                        }
                        if (buffer.charAt(damagedsite) == 'C') {
                            base = 'T';
                            type2 = type2 + 1;
                            System.out.println("Site " + damagedsite + ", C->T, type II");
                        }
                        else {
                            base = 'A';
                            type2 = type2 + 1;
                            System.out.println("Site " + damagedsite + ", G->A, type II");
                        }
                    }
                    else if (rannum < (type2dam + type1dam)){
                        while ((buffer.charAt(damagedsite) == 'G') || (buffer.charAt(damagedsite) == 'C')) {
                            damagedsite = (int)((currentcat*0.125*len) + (0.125*len*rn.nextDouble()));
                        }
                        if (buffer.charAt(damagedsite) == 'A') {
                            base = 'G';
                            type1 = type1 + 1;
                            System.out.println("Site " + damagedsite + ", A->G, type I");
                        }
                        else {
                            base = 'C';
                            type1 = type1 + 1;
                            System.out.println("Site " + damagedsite + ", T->C, type I");
                        }
                    }
                    else {
                        type3 = type3 + 1;
                        if (base == 'A') {
                            if (rn.nextDouble() > 0.5) {
                                base = 'C';
                                System.out.println("Site " + damagedsite + ", A->C, transversion");
                            }
                            else {
                                base = 'T';
                                System.out.println("Site " + damagedsite + ", A->T, transversion");
                            }
                        }
                        else if (base == 'C') {
                            if (rn.nextDouble() > 0.5) {
                                base = 'A';
                                System.out.println("Site " + damagedsite + ", C->A, transversion");
                            }
                            else {
                                base = 'G';
                                System.out.println("Site " + damagedsite + ", C->G, transversion");
                            }
                        }
                        else if (base == 'G') {
                            if (rn.nextDouble() > 0.5) {
                                base = 'C';
                                System.out.println("Site " + damagedsite + ", G->C, transversion");
                            }
                            else {
                                base = 'T';
                                System.out.println("Site " + damagedsite + ", G->T, transversion");
                            }
                        }
                        else {
                            if (rn.nextDouble() > 0.5) {
                                base = 'A';
                                System.out.println("Site " + damagedsite + ", T->A, transversion");
                            }
                            else {
                                base = 'G';
                                System.out.println("Site " + damagedsite + ", T->G, transversion");
                            }
                        }
                    }
                }
                buffer.setCharAt(damagedsite, base);
            }
        }

        System.out.println("Total damage: " + (type1+type2) + " sites, type I: " + type1 + " sites, type II: " + type2 + " sites, transversions: " + type3 + " sites.");
        seq = buffer.toString();
        return seq;
    }
}
