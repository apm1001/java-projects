package apm1001;

import edu.duke.FileResource;
import edu.duke.StorageResource;

/**
 * This class contains dna string field
 * and there are methods to interact with DNA
 * Each letter of DNA string represents one nucleotide
 * Three nucleotides organize one codon.
 */

public class Dna {

    //The Main dna String
    private String dna = new String();

    //Getting method. It returnes the dna field
    public String getDna(){
        return this.dna;
    }

    /**
     * Setting method.
     * @param dna is setted to object
     */
    public void setDna(String dna){
        this.dna = dna;
    }

    /**
     * This method helps to find a gene.
     *
     * @param startIndex from that index it starts to search
     * @param stopCodon - the last codon of gene. It can be different
     * @return index of last codon of valid gene
     */
    private int findStopCodon(int startIndex, String stopCodon) {
        int lastIndex = this.dna.indexOf(stopCodon, startIndex + 3);
        // while loop that checks is it a valid gene that should be multiple of three
        while (((lastIndex - startIndex) % 3 != 0) && (lastIndex != -1)) {
            lastIndex++;
            lastIndex = this.dna.indexOf(stopCodon, lastIndex);
        }
        if (lastIndex == -1) {
            return this.dna.length();
        } else return lastIndex;
    }

    /**
     *
     * ATG codon is the starting point of the gene
     * There are three codons of ending of the gene:
     * TAA, TAG, TGA
     * @param startPoint is used to find all the genes
     * @return "" if there is not found a valid gene or
     * returns a valid gene String
     */
    public String findGene(int startPoint) {
        int atgIndex = this.dna.indexOf("ATG", startPoint);
        if (atgIndex == -1) {
            return "";
        }
        int taaIndex = findStopCodon(atgIndex, "TAA");
        int tagIndex = findStopCodon(atgIndex, "TAG");
        int tgaIndex = findStopCodon(atgIndex, "TGA");

        int minStopCodon = Math.min(taaIndex, Math.min(tagIndex, tgaIndex));
        if (minStopCodon == this.dna.length()) {
            return "";
        } else return this.dna.substring(atgIndex, minStopCodon + 3);
    }

    /**
     * THis method prints all found Genes in dna
     */
    public void printAllGenes() {
        int startPoint = 0;
        while (true) {
            String currentGene = findGene(startPoint);
            if (currentGene.isEmpty()) {
                break;
            } else {
                System.out.println(currentGene);
                startPoint = dna.indexOf(currentGene, startPoint) + currentGene.length();
            }
        }
    }

    /**
     * This method creates and fills the list of all found genes
     * @return StorageResourse from edu.duke.StorageResource
     * It is class to store and manipulate with list of strings
     */
    public StorageResource getAllGenes() {
        StorageResource geneList = new StorageResource();
        int startPoint = 0;
        while (true) {
            String currentGene = findGene(startPoint);
            if (currentGene.isEmpty()) {
                break;
            } else {
                geneList.add(currentGene);
                startPoint = this.dna.indexOf(currentGene, startPoint) + currentGene.length();
            }
        }
        return geneList;
    }

    /**
     * THis method counts the amount of genes in dna string
     * @return an integer number that represents the amount of genes
     */
    public int countGenes() {
        int startPoint = 0;
        int count = 0;
        while (true) {
            String currentGene = findGene(startPoint);
            if (currentGene.isEmpty()) {
                break;
            } else {
                count++;
                startPoint = this.dna.indexOf(currentGene, startPoint) + currentGene.length();
            }
        }
        return count;
    }

    /**
     * This method calculate the ratio of 'C' and 'G' nucleotides in dna
     * @return float number - the ratio
     */
    public float cgRatio() {
        int countCG = 0;
        for (char ch : this.dna.toCharArray()) {
            if (ch == 'C' || ch == 'G') {
                countCG++;
            }
        }
        return (float) countCG / this.dna.length();
    }

    /**
     * This method counts CTG codons in dna
     * @return integer number
     */
    public int countCTG() {

        int startPoint = 0;
        int count = 0;

        while (true) {
            int currentPoint = this.dna.indexOf("CTG", startPoint);
            if (currentPoint == -1) {
                break;
            } else {
                count++;
                startPoint = this.dna.indexOf("CTG", startPoint) + 3;
            }
        }
        return count;
    }

    /**
     * This method processes a list of genes
     * It prints genes with length more than 60 and number of them
     * It prints genes with 'C' and 'G' ratio more than 0.35 and number of them
     * It prints the maximum length of genes
     * @param sr of type StorageResource from edu.duke.StorageResource
     *           It is a list of genes
     */
    public void processGenes(StorageResource sr) {

        int count = 0;
        int maxLength = 0;
        for (String gene : sr.data()) {
            if (gene.length() > 60) {
                count++;
                System.out.println(gene);
            }
            if (maxLength < gene.length()) {
                maxLength = gene.length();
            }
        }
        System.out.println("number: " + count);
        count = 0;
        for (String gene : sr.data()) {
            if (cgRatio() > 0.35) {
                System.out.println(gene + "(cgRatio)");
                count++;
            }
        }
        System.out.println("number: " + count);
        System.out.println("Max length: " + maxLength);
    }

    /**
     * A constructor that sets dna field
     * @param dna is the string
     */
    public Dna(String dna){
        this.dna = dna;
    }
}
