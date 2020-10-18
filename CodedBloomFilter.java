import java.io.*;
import java.util.*;

public class CodedBloomFilter {
    int numOfSets;
    int numOfElements;
    int numOfFilters;
    int numOfBits;
    int[] s;
    Map<Integer, int[]> codeMap = new HashMap<>();

    CodedBloomFilter(int numOfSets, int numOfElements, int numOfFilters, int numofBits, int numOfHashes) {
        this.numOfSets = numOfSets;
        this.numOfElements = numOfElements;
        this.numOfFilters = numOfFilters;
        this.numOfBits = numofBits;
        s = new int[numOfHashes];
        assignCode();
        generateHash();
    }

    private void assignCode() {
        codeMap.put(0,new int[numOfBits]);
        codeMap.put(1,new int[numOfBits]);
        codeMap.put(2,new int[numOfBits]);
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
        List<Map<Integer, int[]>> list = new LinkedList<>();
        Map<Integer, int[]> allElements = new HashMap<>();
        for(int i = 0; i < numOfSets; i++) {
            Map<Integer, int[]> parentMap = new HashMap<>();
            generateRandomElements(parentMap, allElements);
            list.add(parentMap);
        }
        Map<Integer, String> lookupCodeMap = new HashMap<>();
        for(int i = 0; i < list.size(); i++) {
            String binary = String.format("%" + numOfFilters + "s", Integer.toBinaryString(i+1)).replaceAll(" ", "0");
            for(int j = 0; j < binary.length(); j++) {
                if(binary.charAt(j) == '1') {
                    encode(list.get(i), j, lookupCodeMap, binary);
                }
            }
        }
        int count = 0;

        //Lookup
        for(Map.Entry<Integer, int[]> entry : allElements.entrySet()) {
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < numOfFilters; j++) {
                int numHashCount = 0;
                int[] filter = codeMap.get(j);
                int[] resultHash = entry.getValue();
                for(int k = 0; k < resultHash.length; k++) {
                    if(filter[resultHash[k] % numOfBits] == 1) {
                        numHashCount++;
                    }
                }
                if(numHashCount == numOfSets) {
                    sb.append(1);
                } else {
                    sb.append(0);
                }
            }

            if(lookupCodeMap.containsKey(entry.getKey()) && lookupCodeMap.get(entry.getKey()).equals(sb.toString())) {
                count++;
            }
        }
        return count;
    }

    //Encode all Bits of the seen value to 1
    private void encode(Map<Integer, int[]> parentMap, int codeMapIndex, Map<Integer, String> lookupCodeMap, String binary) {
        int[] bitMap = codeMap.get(codeMapIndex);
        for(Map.Entry<Integer, int[]> entry : parentMap.entrySet()) {
            lookupCodeMap.put(entry.getKey(), binary);
            int[] resultHash = entry.getValue();
            for (int hash : resultHash) {
                bitMap[hash % numOfBits] = 1;
            }
        }
        codeMap.replace(codeMapIndex, bitMap);
    }

    //Generating random values for A and B
    private void generateRandomElements(Map<Integer, int[]> parentMap, Map<Integer, int[]> allElements) {
        for(int i = 0; i < numOfElements; i++) {
            int element = 0;
            do {
                element = random();
            }
            while (allElements.containsKey(element));

            int[] resultHash = generateHashFunction(element);
            parentMap.put(element, resultHash);
            allElements.put(element, resultHash);
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
        CodedBloomFilter cdbf = new CodedBloomFilter(7,1000,3, 30000, 7);
        if(arg.length == 5) {
            try{
                cdbf = new CodedBloomFilter(Integer.parseInt(arg[0]), Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), Integer.parseInt(arg[3]), Integer.parseInt(arg[4]));
            }
            catch (NumberFormatException nfe) {
                System.out.println("The Input is Invalid");
            }
        }
        File fout = new File("OutputCodedBloomFilter.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(Integer.toString(cdbf.fillBloomFilter()));
        bw.newLine();
        bw.close();
    }
}
