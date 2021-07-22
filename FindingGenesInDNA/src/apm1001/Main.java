package apm1001;
import edu.duke.*;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {
	// write your code here
        // initialize the file
        FileResource fr = new FileResource("GRch38dnapart.fa");
        //get the string from file
        String dna = fr.asString();
        //creating an object longDna of Dna class with constructor
        //that sets the dna field
        Dna longDna = new Dna(dna);

        StorageResource sr = new StorageResource();
        //calling methods
        sr = longDna.getAllGenes();
        System.out.println(sr.size());
        longDna.processGenes(sr);

    }
}
