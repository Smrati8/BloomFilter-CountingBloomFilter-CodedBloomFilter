import java.io.*;
import java.util.*;

public class BloomFilter {
    int[] bitMap;
    int numOfElements;
    int numOfBits;
    int[] s;

    BloomFilter(int numOfElements, int numofBits, int numOfHashes) {
        this.numOfElements = numOfElements;
        this.numOfBits = numofBits;
        bitMap = new int[numofBits];
        s = new int[numOfHashes];
        generateHash();
    }

    // Generate all the Unique Hash values
    private void generateHash() {
        Set<Integer> uniqueHash = new HashSet<>();
        for(int i = 0; i < s.length; i++) {
            while (true) {
                int hash = random();
                if(!uniqueHash.contains(random())) {
                    uniqueHash.add(hash);
                    s[i] = hash;
                    break;
                }
            }
        }
    }

    //Generate a random positive number greater than 1
    private int random() {
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE - 1) + 1;
    }

    //Driver Function for the program for two sets of Input Set A and Set B
    public int[] fillBloomFilter(){
        Map<Integer, int[]> parentMapA = new HashMap<>();
        Map<Integer, int[]> parentMapB = new HashMap<>();
        Set<Integer> globalSet = new HashSet<>();
        generateRandomElements(parentMapA, globalSet);
        encode(parentMapA);
        int countA = lookup(parentMapA);
        generateRandomElements(parentMapB, globalSet);
        int countB = lookup(parentMapB);
        return new int[] {countA, countB};
    }

    //Encode all Bits of the seen value to 1
    private void encode(Map<Integer, int[]> parentMap) {
        for(Map.Entry<Integer, int[]> entry : parentMap.entrySet()) {
            int[] resultHash = entry.getValue();
            for (int hash : resultHash) {
                bitMap[hash % numOfBits] = 1;
            }
        }
    }

    //Check if the value exists in the table
    private int lookup(Map<Integer, int[]> parentMap){
        int count = 0;
        for(Map.Entry<Integer, int[]> entry : parentMap.entrySet()) {
            int[] resultHash = entry.getValue();
            for(int hash : resultHash) {
                if(bitMap[hash % numOfBits] == 0) {
                    count++;
                    break;
                }
            }
        }
        return numOfElements - count;
    }

    //Generating random values for A and B
    private void generateRandomElements(Map<Integer, int[]> parentMap, Set<Integer> globalSet) {
        for(int i = 0; i < numOfElements; i++) {
            int element = 0;
            do {
                element = random();
            }
            while (globalSet.contains(element));

            int[] resultHash = generateHashFunction(element);
            parentMap.put(element, resultHash);
            globalSet.add(element);
        }
    }

    //Create the hash function
    private int[] generateHashFunction(int element){
        int[] result = new int[s.length];
        for(int i = 0; i < s.length; i++) {
            result[i] = element ^ s[i];
        }
        return result;
    }

    public static void main(String[] arg) throws IOException {
        BloomFilter bf = new BloomFilter(1000, 10000, 7);
        if(arg.length == 3) {
            try{
                bf = new BloomFilter(Integer.parseInt(arg[0]), Integer.parseInt(arg[1]), Integer.parseInt(arg[2]));
            }
            catch (NumberFormatException nfe) {
                System.out.println("The Input is Invalid");
            }
        }
        File fout = new File("OutputBloomFilter.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        int[] result = bf.fillBloomFilter();
        bw.write("After lookup of elements in A the number of elements are: " + Integer.toString(result[0]));
        bw.newLine();
        bw.write("After lookup of elements in B the number of elements are: " + Integer.toString(result[1]));
        bw.close();
    }
}
