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
        return new int[] {encodeLookup(), encodeLookup()};
    }

    private int encodeLookup() {
        Set<Integer> uniqueElements = new HashSet<>();
        int count = 0;
        for(int i = 0; i < numOfElements; i++) {
            int element = 0;
            while(true) {
                element = random();
                if(!uniqueElements.contains(element)) {
                    uniqueElements.add(element);
                    break;
                }
            }
            int[] resultHash = generateHashFunction(element);

            //Lookup if the number has not been encoded yet, number is unseen
            for (int hash : resultHash) {
                if (bitMap[hash % numOfBits] == 0) {
                    count++;
                    break;
                }
            }

            //Encode all Bits to 1
            for (int hash : resultHash) {
                bitMap[hash % numOfBits] = 1;
            }
        }
        return count;
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
        System.out.println(Arrays.toString(result));
        for (int count : result) {
            bw.write(Integer.toString(count));
            bw.newLine();
        }
        bw.close();
    }
}
