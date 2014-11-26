sequescence
===========

Sequescence is a Java application that simulates the appearance of miscoding lesions as the result of post-mortem damage in ancient DNA.

Using Sequescence
The application can be executed on any platform provided that Java has been installed. 
To execute Sequescence, place the .jar file and the sequence alignment in the same directory. From a command line in the directory, type the following:
java –jar Sequescence.jar
The application will prompt the user for the following:
1) Input file. This is the name of the file containing the sequence alignment. The alignment should be in Fasta format. Individual sequences should not run across multiple lines. 
2) Number of aligned sequences.
3) Damage rate (average per base). This is the mean damage rate for each nucleotide in the alignment. Note that Sequescence implements an age-independent model of sequence damage. 
4) Proportion of Type I damage. The proportion of miscoding lesions that represent Type I damage (A→G and T→C). 
5) Proportion of Type II damage. The proportion of miscoding lesions that represent Type II damage (C→T and G→A). 
6) Alpha parameter for among-site rate variation. The shape parameter for gamma-distributed rates among sites. Small values of alpha indicate substantial rate variation among sites. Enter a value of 0 for homogeneous rates among sites. 

Citation
If you use Sequescence in your research, please cite the following paper.
Ho SYW, Heupink TH, Rambaut A, and Shapiro B (2007) Bayesian estimation of sequence damage in ancient DNA. Molecular Biology and Evolution, 24: 1416-1422.
