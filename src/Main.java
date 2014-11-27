package Sequescence;

import java.io.*;
import java.lang.String;

public class Main {
    
    public Main() {
    }
    
    public static void main(String[] args) {
        int i = 0;                              // Counter
        int numseq = 0;                         // Number of sequences
        int seqlength = 0;                      // Length of each sequence
        double damagerate = -1;                 // Rate of sequence damage
        double type1dam = -1, type2dam = -1;    // Proportions of damage types
        double alpha = -1;                      // Alpha parameter of gamma distribution for among-site variation
        double[] rates = new double[8];         // Array containing probabilities of gamma categories
        String[] name;                          // Array storing sequence names
        String[] seqString;                     // Array storing sequences
        char[][] sequence;                      // Array storing sequences as inidivudal characters

        /* --------------- Introduction and user input --------------- */

        System.out.println("");
        System.out.println("+-----------------------------------+");
        System.out.println("|          * Sequescence *          |");
        System.out.println("|     Sequence Ageing Programme     |");
        System.out.println("|            Version 1.0            |");
        System.out.println("+-----------------------------------+");
        System.out.println("\nAlignment should be in FASTA format, with each sequence on its own, single line.");
        System.out.print("\nInput file: ");
        String infile = UserInput.readString();
        String outfile = (infile + ".damaged");

        while (numseq <= 0) {
            System.out.print("Number of aligned sequences: ");
            numseq = UserInput.readInt();
        }

        while ((damagerate < 0) || (damagerate >= 1)) {
            System.out.print("Damage rate (average per base): ");
            damagerate = UserInput.readDouble();
            if (damagerate >= 1) {
                System.out.println("Damage rate is too high! Please enter a value <1.");
            }
        }

        while ((type1dam < 0) || (type1dam > 1)) {
            System.out.print("Proportion of Type I damage: ");
            type1dam = UserInput.readDouble();
            if (type1dam > 1) {
                System.out.println("\nValue is too high! Please enter a value between 0 and 1.");
            }
        }

        while ((type2dam < 0) || ((type1dam + type2dam) > 1)) {
            System.out.print("Proportion of Type II damage: ");
            type2dam = UserInput.readDouble();
            if ((type1dam + type2dam) > 1) {
                System.out.println("\nValue is too high! Sum of proportions must be <=1");
            }
        }
        
        while (alpha < 0) {
            System.out.print("Alpha parameter for among-site rate variation (0 for no variation): ");
            alpha = UserInput.readDouble();
        }

        name = new String[numseq];
        seqString = new String[numseq];

        /* ---------- Begin reading sequence alignment file ---------- */

        try {
            Reader alignment = new FileReader(infile);
            BufferedReader buffer = new BufferedReader(alignment);
            System.out.println("\n-----------------------");
            System.out.println("\nCommencing analysis ... \n");
            System.out.println(infile + " successfully opened.");
            for (i = 0; i < numseq; i++) {
                name[i] = buffer.readLine();
                seqString[i] = buffer.readLine();
            }
            System.out.println("Alignment length is " + seqString[1].length() + " nucleotides.");
        } catch (IOException ioException) {
            System.out.println(ioException + " Error opening the file " + infile);
        }        
        
        // Get the rates for different rate categories
        rates = DiscretizedGamma.DiscretizedGamma(alpha);

        for (i = 0; i < numseq; i++) {
            System.out.println("\nDAMAGING SEQUENCE " + (i+1));
            seqString[i] = Mutator.Mutator(seqString[i], PoissonGenerator.PoissonGenerator(damagerate, seqString[i].length()), type1dam, type2dam, rates);
        }

        // Write the sequences to a file
        try {
        BufferedWriter out = new BufferedWriter(new FileWriter(outfile, true));
            for (i = 0; i < numseq; i++) {
                out.write(name[i]);
                out.newLine();
                out.write(seqString[i]);
                out.newLine();
            }
            out.close();
        } catch (IOException e) {}
                
        System.out.println("\nSequence damaging complete.\n");
    }
}
