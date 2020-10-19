# BloomFilter-CountingBloomFilter-CodedBloomFilter

# Description
In the project, I have implemented a Bloom filter, counting Bloom filter, and coded Bloom filter.

## Bloom Filter

**Input**: number of elements to be encoded, number of bits in the filter, number of hashes – for demo, they are 1,000,  10,000 and 7, respectively

**Function**: generate 1,000 elements (denoted as set A) randomly, encode them in the filter, look up them in the filter, and generate another 1.000 elements randomly (denoted as set B) and look up them in the filter.   

**Output**: (1) the first line of the output: After lookup of elements in A, what is the number of elements you find in the filter?


(2) the second line of the output: After lookup of elements in B, what is the number of elements you find in the filter?   

## Counting Bloom Filter

**Input**: number of elements to be encoded initially, number of elements to be removed, number of elements to be added, number of counters in the filter, number of hashes – for demo, they are 1,000,  500, 500, 10,000 and 7, respectively

**Function**: generate 1,000 elements (denoted A) randomly, encode them in the filter, remove 500 elements in A from the filter, add other 500 randomly generated elements in the filter, and look up all original elements from A in the filter.

**Output**: After lookup of elements in A, what is the number of elements you find in the filter?

## Coded Bloom filter

**Input**: number of sets, number of elements in each set, number of filters, number of bits in each filter, number of hashes – for demo, they are 7, 1000, 3, 30,000, and 7 respectively

**Function**: generate 7 sets of 1000 elements each, their codes are 001 through 111 respectively, encode all sets in 3 filters according to the algorithm, perform lookup on all elements in the 7 sets

**Output**: number of elements whose lookup results are correct 

# For running the file:
•	Extract the files from the folder.


•	They consist of three java files and three output files denoted to each hash table.


•	Run the files using the system having Java Run time environment setup. 


     javac BloomFilter.java | 
     CountingBloomFilter.java | 
     CodedBloomFilter.java
     
     
     (java BloomFilter 1000 10000 7) | 
     (java CountingBloomFilter 1000 500 500 10000 7) | 
     (java CodedBloomFilter 7 1000 3 30000 7)
     
Please pass the dynamic parameters otherwise default parameters will run.
The output file will be generated.

# Implementation of Bloom Filters

## 1)	Bloom Filter

Below are the input parameters for Demo: 

number of Elements to be encoded: 1000

number of bits in the filter: 10000

number of hashes: 7

These input parameters are passed to the constructor. These are default parameters. 

Below is the structure of the program:

### •	BloomFilter(): 
The constructor of the program initializes the values of elements in the set, number of bits in the bit map, and the number of hashes. Then it calls the generateHash method.

### •	generateHash(int[] s): 
This method is called by the constructor of the BloomFilter and assigns unique values to the number of hash values needed in the program.

### •	random(): 
This method uses the random class in java. According to the functionality I have assigned to the method it generates a random number between 1 to maximum integer value supported in java.
### •	generateHashFunction(int flowID): 
This method create the XOR values obtained and assigns for each flowID.

### •	fillBloomFilter(): 
This method is driver function of the program. It calls a method to generate random number for set A and then encode them and then look them up in the bloom filter. Then it calls to generate random number for set B and look up them in the Bloom Filter. Then it returns the count of both sets elements found in the bloom filter.

### •	generateRandomElements(): 
It generates unique elements that need to be stored in the bloom filter. 

### •	encode(): 
This Encodes the values of set A values in the Bloom Filter. The values in bloom filter are filled with 1 denoting the element has been seen.

### •	Lookup(): 
Once the elements have been entered we check them if all the values are encoded as one for their hashes. We check for the false positives.


It generates the output file “OutputBloomFilter.txt”.  As per the project requirement, the first line consists of the elements when looked up for set A. the second line consists of the elements looked up for set B. 

According to the output generated, for the elements in set A, we can find everyone of them so 1000 is generated whereas B gives around under 15 false positive values.


## 2)	Counting Bloom Filter

Below are the input parameters for Demo: 

number of Elements to be encoded: 1000

number of elements to be removed: 500

number of elements to be added: 500

number of bits in the filter: 10000

number of hashes: 7

These input parameters are passed to the constructor. These are default parameters. 

Below is the structure of the program:

### •	CountingBloomFilter (): 
The constructor of the program initializes the values of elements in the set, number of elements to be removed, number of elements to be added, number of bits in the bit map, and the number of hashes. Then it calls the generateHash method.

### •	generateHash(int[] s): 
This method is called by the constructor of the CountingBloomFilter and assigns unique values to the number of hash values needed in the program.

### •	random(): 
This method uses the random class in java. According to the functionality I have assigned to the method it generates a random number between 1 to maximum integer value supported in java.

### •	generateHashFunction(int flowID): 
This method create the XOR values obtained and assigns for each flowID.

### •	fillBloomFilter(): 
This method is driver function of the program. It calls a method to generate random number for set A and then encode them and then look them up in the coding bloom filter. Then it removed the elements from the Bloom Filter and then it adds another set of random numbers and encodes them. Then it lookups the count of the originally generated numbers.

### •	removeElements():
This method removes the values Bloom Filter and decrements the counter of the seen values.

### •	AddElements(): 
This method adds the set of random number and encodes them in the counting  bloom filter.

### •	generateRandomElements(): 
It generates unique elements that need to be stored in the bloom filter. 

### •	encode():
This Encodes the values of set A values in the Bloom Filter. The values in counting bloom filter are filled counters, they are incrementing the values, denoting the element has been seen.

### •	Lookup():
Once the elements have been entered we check them if all the values are encoded as one for their hashes. We check for the false positives.

It generates the output file “OutputCountingBloomFilter.txt”. As per the project requirement, the first line consists of the elements when looked up for set A. 
According to the output generated, for the original elements in the Hashtable end up around to be 500-510.


## 3)	Coded Bloom Filter

Below are the input parameters for Demo: 

number of sets: 7

number of Elements to be encoded: 1000

number of filters: 3

number of bits in the filter: 30000

number of hashes: 7

These input parameters are passed to the constructor. These are default parameters.

Below is the structure of the program:

### •	CodedBloomFilter():
The constructor of the program initializes the number of sets, number of elements in the set, number of filters, number of bits in the bit map, and the number of hashes. Then it calls the generateHash and assign code method.

### •	assignCode():
This method stores the map to the bloom filter that needs to be filled by the given random number.

### •	generateHash(int[] s):
This method is called by the constructor of the CodedBloomFilter and assigns unique values to the number of hash values needed in the program.

### •	random():
This method uses the random class in java. According to the functionality I have assigned to the method it generates a random number between 1 to maximum integer value supported in java.

### •	generateHashFunction(int flowID):
This method create the XOR values obtained and assigns for each flowID.

### •	generateRandomElements(): 
It generates unique elements that need to be stored in the bloom filter. 

### •	fillBloomFilter(): 
This method is driver function of the program. It functions in following three steps: 

*1) Firstly, it generates random number for all the sets that have been provided.*

*2) Secondly, it encodes the bloom filter according to the binary code that is generated for the set. If the value in the string is 1 then bloom filter is filled otherwise it is not filled.*

*3) Lastly, it lookups the value of the random numbers that are generated and matches how many have their correct lookup found. This is done by matching the binary code.*

### •	encode(): 
This Encodes the values of set A values in the Bloom Filter. The values in counting bloom filter are filled counters, they are incrementing the values, denoting the element has been seen.

It generates the output file “OutputCodedBloomFilter.txt”. As per the project requirement, the first line consists of the elements when looked up for set A which are correct. The value is greater than 6700+ for set of 7000 elements.
