
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
 * @Author: Waleed Faruki
 * Murashkina
 * CSC-255-0C1
 * 26 February 2023
 * 
 * 
 */
public class Driver {
    static final int tableSize = 191; // hash table size
    static final int doubleFactor = 181; // factor R to be used in double hashing

    static FileOutputStream fout; // declare and create an output stream
    static FileOutputStream fout2;
    static ArrayList<Integer> data = new ArrayList<>(); // all keys from the input file
    String output = "";
    
    public static void testKeyValue(HashInterface hashTable, final Integer key, final Integer value) throws RuntimeException {
        final int previousCollisions = hashTable.getCollisions();

        hashTable.put(key, value);

        final Integer retrievedValue = hashTable.get(key);
        final String retrievedText = retrievedValue.toString(); 

        System.out.print(key + " : " + value + " -> " + retrievedText + ", collisions " + (hashTable.getCollisions() - previousCollisions) + "\n");

        if (!retrievedValue.equals(value)) {
            System.out.print("Retrieved value " + retrievedText + " does not match stored value"  + value + " for key " + key + "\n");

            throw new RuntimeException("value mismatch");
        }
    }

    public static void testInputKey(final Integer key, HashInterface lph, HashInterface qph, HashInterface dhph) { // Removed other hashing
        final Integer value = key * 2;

        testKeyValue(lph, key, value);
        testKeyValue(qph, key, value );
        testKeyValue(dhph, key, value );
        
        System.out.print("\n");
    }

    public static String testData(final String description) {
        System.out.print("*** " + description + " Start ***" + "\n\n");

        LinearProbingHash lph = new LinearProbingHash(tableSize);
        QuadraticProbingHash qph = new QuadraticProbingHash(tableSize);
        DoubleProbingHash dhph = new DoubleProbingHash(tableSize, doubleFactor);
        String collisions = "";
        for (Integer key : data) {
            testInputKey(key, lph, qph, dhph);
        }
        
        	
        System.out.print("Linear    " + lph.getCollisions() + " collisions" + "\n");
        collisions += lph.printTable(description);
        System.out.print("Quadratic " + qph.getCollisions() + " collisions" + "\n");
        collisions += qph.printTable(description);
        System.out.print("Double    " + dhph.getCollisions() + " collisions" + "\n");
        collisions += dhph.printTable(description);
        
        System.out.print("\n" + "*** " + description + " End ***" + "\n\n");
        return collisions; 
    }

    public static  void readData(final String inputFile) throws FileNotFoundException {
        Scanner scanner= new Scanner(new File(inputFile ));

        while (scanner.hasNext()) {
            data.add(scanner.nextInt());
        }

        scanner.close();
    }

    public static void testFile(final String inputFilename, final String outputFilename, final String outputFilename2) throws IOException {
        PrintStream original = new PrintStream(System.out);
        fout = new FileOutputStream(outputFilename);
        fout2 = new FileOutputStream(outputFilename2);
        PrintStream ps = new PrintStream(fout);
        PrintStream ps2 = new PrintStream(fout2);
        	
        String collisionsTableOutput = "";
        
        readData(inputFilename);

        System.out.println("Input file: " + inputFilename + ", output file: " + outputFilename);
        
        System.setOut(ps);
        collisionsTableOutput += testData("Random Order");


        
        System.setOut(ps);
        Collections.sort(data);
        collisionsTableOutput += testData("Ascending Order");

        System.setOut(ps);
        data.sort(Collections.reverseOrder());
        collisionsTableOutput += testData("Descending Order");
 
        System.setOut(ps2);
        System.out.print(collisionsTableOutput);
        fout.close();
        data.clear();
        System.setOut(original);
        System.out.println("Done");
        
    }
   
    public static void main(String[] args) {
    	try {
            testFile("src/in" + 150 + ".txt", "src/out" + 150  + "collisions.txt", "src/out" + 150  + "tables.txt" );
            testFile("src/in" + 160 + ".txt", "src/out" + 160 + "collisions.txt", "src/out" + 160 + "tables.txt");
            testFile("src/in" + 170 + ".txt", "src/out" + 170 + "collisions.txt", "src/out" + 170 + "tables.txt");
            testFile("src/in" + 180 + ".txt", "src/out" + 180  + "collisions.txt", "src/out" + 180  + "tables.txt" );
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }
}