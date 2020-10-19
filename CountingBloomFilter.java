import java.io.*;
import java.util.*;

public class CountingBloomFilter {
    int[] Filter;
    int numOfElements;
    int numOfElementsRemoved;
    int numOfElementsAdded;
    int numOfBits;
    int[] s;

    CountingBloomFilter(int numOfElements, int numOfElementsRemoved, int numOfElementsAdded, int numofBits, int numOfHashes) {
        this.numOfElements = numOfElements;
        this.numOfBits = numofBits;
        this.numOfElementsRemoved = numOfElementsRemoved;
        this.numOfElementsAdded = numOfElementsAdded;
        Filter = new int[numofBits];
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

    //Driver Function for the program
    public int fillBloomFilter(){
        Map<Integer, int[]> parentMapA = new HashMap<>();
        Set<Integer> originalElement = new HashSet<>();
        generateRandomElements(parentMapA,originalElement);
        encode(parentMapA);
        removeElements(parentMapA);
        addElements(parentMapA);
        return lookup(parentMapA, originalElement);
    }

    //Removing the values from Counting Filter
    private void removeElements(Map<Integer, int[]> parentMap) {
        int size = numOfElementsRemoved;
        for(Map.Entry<Integer, int[]> entry : parentMap.entrySet()) {
            int[] resultHash = entry.getValue();
            for (int hash : resultHash) {
                Filter[hash % numOfBits]--;
            }
            size--;
            if(size == 0) {
                break;
            }
        }
    }

    //Generate new Set of Elements and Encode them in the Filter
    private void addElements(Map<Integer, int[]> parentMap){
        for(int i = 0; i < numOfElementsAdded; i++) {
            int element = 0;
            do {
                element = random();
            }
            while (parentMap.containsKey(element));

            int[] resultHash = generateHashFunction(element);
            parentMap.put(element, resultHash);
            for (int hash : resultHash) {
                Filter[hash % numOfBits]++;
            }
        }
    }

    //Encode all Bits of the seen value to 1
    private void encode(Map<Integer, int[]> parentMap) {
        for(Map.Entry<Integer, int[]> entry : parentMap.entrySet()) {
            int[] resultHash = entry.getValue();
            for (int hash : resultHash) {
                Filter[hash % numOfBits]++;
            }
        }
    }

    //Check if the value exists in the table
    private int lookup(Map<Integer, int[]> parentMap, Set<Integer> originalElement){
        int count = 0;
        for(int element : originalElement) {
            int[] resultHash = parentMap.get(element);
            for(int hash : resultHash) {
                if(Filter[hash % numOfBits] <= 0) {
                    count++;
                    break;
                }
            }
        }
        return numOfElements - count;
    }

    //Generating random values for A
    private void generateRandomElements(Map<Integer, int[]> parentMap, Set<Integer> orignalElements) {
        for(int i = 0; i < numOfElements; i++) {
            int element = 0;
            do {
                element = random();
            }
            while (parentMap.containsKey(element));

            int[] resultHash = generateHashFunction(element);
            parentMap.put(element, resultHash);
            orignalElements.add(element);
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
        CountingBloomFilter cbf = new CountingBloomFilter(1000, 500, 500, 10000, 7);
        if(arg.length == 5) {
            try{
                cbf = new CountingBloomFilter(Integer.parseInt(arg[0]), Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), Integer.parseInt(arg[3]), Integer.parseInt(arg[4]));
            }
            catch (NumberFormatException nfe) {
                System.out.println("The Input is Invalid");
            }
        }

        File fout = new File("OutputCountingBloomFilter.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write("After lookup of elements in A the number of elements are: " + Integer.toString(cbf.fillBloomFilter()));
        bw.newLine();
        bw.close();
    }
}

