import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Data {

 public int value;
 public Data newData;
 public String binaryCode;
 public String greyCode;
 public String encoded;
 public int noOfOccurrences = 0;
 public double probability;

 public Data(int value) {
  this.value = value;
 }

 public int getValue() {
  return this.value;
 }
 public void setValue(int value) {
  this.value = value;
 }

 public void setEncoded(String value) {
  this.encoded = value;
 }
 public String getEncoded() {
  return this.encoded;
 }

 public void setBinaryCode(String value) {
  this.binaryCode = value;
 }
 public String getBinaryCode() {
  return this.binaryCode;
 }

 public void setGreyCode(String value) {
  this.greyCode = value;
 }
 public String getGreyCode() {
  return this.greyCode;
 }

 public Data getNewData() {
  return this.newData;
 }
 public void setNewData(Data data) {
  this.newData = data;
 }

 public void setNoOfOccurrences(int value) {
  this.noOfOccurrences = value;
 }
 public int getNoOfOccurrences() {
  return this.noOfOccurrences;
 }

 public void setProbability(double value) {
  this.probability = value;
 }
 public double getProbability() {
  return this.probability;
 }
}

class DataList {

 public Data headData;
 public double size;

 public List < Integer > unique;
 public DataList() {}

 public Data getHeadNode() {
  return this.headData;
 }

 // Getters & Setters for the DataList class
 public void populateTheList(String data) {

  for (int i = 0; i < data.length(); i++) {
   this.insertData(Integer.parseInt("" + data.charAt(i)));
  }
 }

 // Put Binary code into the Linked List
 public void addBinaryCodeIntoTheList(String[] data) {

  int current = 0;
  Data currentData = this.headData;

  while (currentData != null) {
   currentData.setBinaryCode(data[current]);
   current++;
   currentData = currentData.getNewData();
  }
 }

 // Put Grey code into the Linked List
 public void addGreyCodeIntoTheList(String[] data) {

  int current = 0;
  Data currentData = this.headData;

  while (currentData != null) {
   currentData.setGreyCode(data[current]);
   current++;
   currentData = currentData.getNewData();
  }
 }

 // Put Encoded data into the Linked List
 public void addEncodedIntoTheList(String[] data) {

  int current = 0;
  Data currentData = this.headData;

  while (currentData != null) {
   currentData.setEncoded(data[current]);
   current++;
   currentData = currentData.getNewData();
  }
 }


 public void insertData(int value) {
  if (this.headData == null) {
   this.headData = new Data(value);
  } else {
   Data current = this.headData;
   Data last = null;

   while (current != null) {
    if (current.getNewData() == null) {
     last = current;
    }
    current = current.getNewData();
   }

   Data newSymbol = new Data(value);
   last.setNewData(newSymbol);
  }
  this.size++;
 }

 public Data getData(int value) {

  Data currentData = this.headData;

  while (currentData != null) {
   if (currentData.getValue() == value) {
    return currentData;
   }
   currentData = currentData.getNewData();
  }

  return null;
 }

 // The occurrences in the list
 public void calculateTheOccurrences() {
  List < Integer > data = new ArrayList < Integer > ();
  List < Integer > distinct = new ArrayList < Integer > ();
  List < Integer > occurrence = new ArrayList < Integer > ();
  Data currentData = this.headData;

  while (currentData != null) {
   data.add(currentData.getValue());
   currentData = currentData.getNewData();
  }

  int totalNumber = data.size();
  boolean occ = true;

  for (int i = 0; i < data.size(); i++) {
   for (int q = i - 1; q >= 0; q--) {
    if (data.get(i) == data.get(q)) {
     occ = false;
    }
   }
   if (occ == true) {
    distinct.add(data.get(i));
   }

   occ = true;
  }

  int temp = 0;
  currentData = this.headData;

  while (currentData != null) {
   for (int i = 0; i < data.size(); i++) {
    if (data.get(i) == currentData.getValue()) {
     temp = currentData.getNoOfOccurrences();
     temp++;
     currentData.setNoOfOccurrences(temp);
    }
   }

   currentData = currentData.getNewData();
  }
 }

 // The Probability calculation
 public void calcProbabilities() {

  Data currentData = this.headData;
  double probabilities = 0.0;

  while (currentData != null) {
   currentData.setProbability(currentData.getNoOfOccurrences() / this.size);
   currentData = currentData.getNewData();
  }
 }

 // Print from List - Data, Binary code, Grey code, Encoded data
 public void printTheList() {

  Data currentData = this.headData;

  while (currentData != null) {
   System.out.print(currentData.getValue() + "  " + currentData.getBinaryCode() + "  " + currentData.getGreyCode() + "  " + currentData.getEncoded());
   currentData = currentData.getNewData();
   System.out.println("");
  }
 }

 // The log function calculation
 static double calcTheLog(double x, double base) {
  return (double)(Math.log(x) / Math.log(base));
 }

 // The Entropy calculation
 public double calcTheEntropy() {

  Data currentData = this.headData;
  double entropy = 0.0;
  double temp = 0.0;

  while (currentData != null) {
   temp = 1.0 / currentData.getProbability();
   entropy += currentData.getProbability() * calcTheLog(temp, 2.0);
   currentData = currentData.getNewData();
  }
  return entropy;
 }

 // The Average Length of the Binary code calculation
 public double calcBinaryAVGLength() {

  Data current = this.headData;
  double length = 0.0;

  while (current != null) {
   length += current.getProbability() * current.getBinaryCode().length();
   current = current.getNewData();
  }

  return length;
 }

 // The Average Length of the Grey code calculation
 public double calcGreyAVGLength() {

  Data current = this.headData;
  double length = 0.0;

  while (current != null) {
   length += current.getProbability() * current.getGreyCode().length();
   current = current.getNewData();
  }

  return length;
 }

 // The Average Length of the Encoded data calculation
 public double calcEncodedAVGLength() {

  Data current = this.headData;
  double length = 0.0;

  while (current != null) {
   length += current.getProbability() * current.getEncoded().length();
   current = current.getNewData();
  }

  return length;
 }
}


public class RunLengthEncoding {

 public static String encode(String source) {
  StringBuffer dest = new StringBuffer();
  for (int i = 0; i < source.length(); i++) {
   int runLength = 1;
   while (i + 1 < source.length() && source.charAt(i) == source.charAt(i + 1)) {
    runLength++;
    i++;
   }
   dest.append(runLength);
   dest.append(source.charAt(i));
  }
  return dest.toString().replace(" ", "");
 }

 public static String decode(String source) {
  StringBuffer dest = new StringBuffer();
  Pattern pattern = Pattern.compile("[0-9]");
  Matcher matcher = pattern.matcher(source);
  while (matcher.find()) {
   int number = Integer.parseInt(matcher.group());
   matcher.find();
   while (number-- != 0) {

    dest.append(matcher.group());
   }
  }
  return dest.toString();
 }
}


class menu {

 public static int size = 0;
 private static final String TITLE =
  "\nCO3325 Data Compression coursework 2\n" +
  "   by RIZZO-anastasia_140359547\n";

 public menu() throws IOException {}
 public static char xor(char a, char b) {
  return (a == b) ? '0' : '1';
 }

 // Read from a file
 public static List < String > read(String fileName, boolean original) {
  List < String > list = new ArrayList < String > ();
  String line = "";
  try {
   BufferedReader in = new BufferedReader(new FileReader(fileName));
   while (line != null) {
    line = in .readLine();
    if (line == null) {
     break;
    }

    if (original) {
     list.add(line);
    } else {
     list.add(line.replace(",", ""));
    }
   } in .close();
  } catch (FileNotFoundException e) {
   System.out.println("File not");
  } catch (IOException e) {
   System.out.println("Pfff");
  }
  for (int k = 0; k < list.size(); k++) {
   if (list.get(k).length() == 0) {
    list.remove(k);
   }
  }
  return list;
 }

 // Write to a file
 public static void write(List < String > list, String fileName) {
  try {
   BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
   int line = 0;
   for (int i = 0; i < list.size(); i++) {
    if (list.get(i).length() > 0) {
     String[] data = list.get(i).split(" ");
     int count = 0;
     for (String w: data) {
      out.write(w);
      count++;
      if (count != data.length) {
       out.write(" ");
      }
     }
     line++;
     if (line != list.size()) {
      out.write("\n");
     }
    }
   }
   out.flush();
   out.close();
  } catch (FileNotFoundException ex) {
   ex.printStackTrace();
  } catch (IOException e) {
   e.printStackTrace();
  }
 }

 // Convert Integer to Binary code
 public static List < String > integerToBinaryCode(List < String > list) {
  List < String > result = new ArrayList < String > ();
  for (int i = 0; i < list.size(); i++) {
   if (list.get(i).length() > 0) {
    String[] data = list.get(i).split(" ");
    int count = 0;
    String temp = "";
    for (String w: data) {
     for (int k = 0; k < w.length(); k++) {
      temp += Integer.toBinaryString(Integer.parseInt(w.charAt(k) + "")) + " ";
     }
    }
    result.add(temp);
   }
  }
  return result;

 }

 // Convert Binary code to Grey code
 public static List < String > binaryToGreyCode(List < String > list) {
  List < String > result = new ArrayList < String > ();
  for (int i = 0; i < list.size(); i++) {
   if (list.get(i).length() > 0) {
    String[] data = list.get(i).split(" ");
    int count = 0;
    String temp = "";
    for (String w: data) {
     temp += w.charAt(0);
     for (int q = 1; q < w.length(); q++) {
      temp += xor(w.charAt(q - 1), w.charAt(q));
     }
     temp += " ";
    }
    result.add(temp);
   }
  }
  return result;
 }

 // Read file to an Array
 public static String[] readFileToArray(List < String > list) {
  List < String > listNoLines = new ArrayList < String > ();
  for (int i = 0; i < list.size(); i++) {
   if (list.get(i).length() > 0) {
    listNoLines.add(list.get(i));
   }
  }
  String totalData = "";
  List < Integer > distinct = new ArrayList < Integer > ();
  boolean occ = true;
  for (String s: listNoLines) {
   totalData += s + " ";
  }
  String[] totalDataArray = totalData.split(" ");
  return totalDataArray;
 }

 public static double calcCompressionRatio(double sizeBefore, double sizeAfter) {
  return sizeAfter / sizeBefore;
 }

 public static double calcCompressionFactor(double sizeBefore, double sizeAfter) {
  return sizeBefore / sizeAfter;
 }

 public static double calcSavingPercentage(double sizeBefore, double sizeAfter) {
  return ((sizeBefore - sizeAfter) / sizeBefore) * 100;
 }

 public static String arrayToString(String[] data) {
  String result = "";
  for (int i = 0; i < data.length; i++) {
   result += data[i];
  }
  return result;
 }

 // Convert List to String
 public static String listToString(List < String > data) {
  String result = "";
  for (int i = 0; i < data.size(); i++) {
   result += data.get(i);
  }
  return result;
 }

 public static void main(String[] args) throws IOException {
  System.out.println(TITLE);
  // Read the Input from a file
  String[] dataInputArray = readFileToArray(read("input.txt", false));
  // Make it a String
  String dataInputForList = arrayToString(dataInputArray);
  System.out.println("Data Input: " + arrayToString(readFileToArray(read("input.txt", true))));
  String dataInput = "";
  for (int i = 0; i < dataInputArray.length; i++) {
   dataInput += dataInputArray[i] + " ";
  }
  dataInput = dataInput.replace(",", "");
  System.out.println("");

  // Read an Input, convert it to Binary code, write it to outputBinary.txt file
  write(integerToBinaryCode(read("input.txt", false)), "outputBinary.txt");
  // Make it an Array
  String[] binaryDataArray = readFileToArray(read("outputBinary.txt", false));
  // Make it a String
  String binaryData = arrayToString(binaryDataArray);

  // Read Binary code, convert it to Grey code, write it to outputGrey.txt file
  write(binaryToGreyCode(read("outputBinary.txt", false)), "outputGrey.txt");
  // Make it an Array
  String[] greyDataArray = readFileToArray(read("outputGrey.txt", false));
  // Make it a String
  String greyData = arrayToString(greyDataArray);

  // Create a Linked List to put all the data: Data, Binary code, Grey code and Encoded code
  DataList list = new DataList();
  // Insert an Input data
  list.populateTheList(dataInputForList);

  // Find distinct elements
  list.calculateTheOccurrences();
  // Calcualte their occurrences probabilities
  list.calcProbabilities();

  // Put a Binary code into the Linked List
  list.addBinaryCodeIntoTheList(binaryDataArray);
  // Put a Grey code into the Linked List
  list.addGreyCodeIntoTheList(greyDataArray);

  // The Entropy calcualtion
  double entropyOfInt = list.calcTheEntropy();

  // The AVG length of Binary code calcualtion
  double binaryLength = list.calcBinaryAVGLength();

  // The AVG length of Grey code calcualtion
  double greyLength = list.calcGreyAVGLength();

  // Print an output from outputBinary.txt file:
  System.out.println("BinaryCode Data from outputBinary.txt file: " + binaryData);
  // Print an output from outputGrey.txt file:
  System.out.println("GreyCode Data from outputGrey.txt file: " + greyData);

  // Run the RunLengthEncoding on Binary code
  String rleBinary = RunLengthEncoding.encode(binaryData);
  // Run the RunLengthEncoding on Grey code
  String rleGreyData = RunLengthEncoding.encode(greyData);

  // Print an output of compressed Binary code after RLE
  System.out.println(" ");
  System.out.println("BinaryCode Data RLE: " + rleBinary);
  // Print an output of compressed Grey code after RLE
  System.out.println("GreyCode Data RLE: " + rleGreyData);

  // Run the RunLengthEncoding on input.txt file
  String encoded = RunLengthEncoding.encode(dataInput.replace(" ", ""));
  // Calcualte the length of Encoded data
  int encodedInputLength = encoded.length();
  // Run the RunLengthEncoding on Encoded data
  String decoded = RunLengthEncoding.decode(encoded);

  // Print an Encoded output
  System.out.println(" ");
  System.out.println("Encoded data: " + encoded);
  // Print a Decoded output
  System.out.println("Decoded data: " + decoded);

  // Print Calculations
  System.out.println(" ");
  System.out.println("Calculations: ");
  // Print the Compression ratio of Binary code, Grey code and Encoded data
  System.out.println(" ");
  System.out.println("Compression Ratio [Integer to Binary code] = " + calcCompressionRatio(dataInput.length(), binaryData.length()));
  System.out.println("Compression Ratio [Integer to Grey code] = " + calcCompressionRatio(dataInput.length(), greyData.length()));
  System.out.println("Compression Ratio [Integer to Encoded data] = " + calcCompressionRatio(dataInput.length(), encodedInputLength));

  // Print the Entropy
  System.out.println("");
  System.out.println("Entropy [Integer] = " + entropyOfInt);

  // Print the AVG length of Binary code, Grey code and Encoded data
  System.out.println("");
  System.out.println("Average length [Binary code] = " + binaryLength);
  System.out.println("Average length [Grey code] = " + greyLength);

  // Print the Compression factor of Binary code, Grey code and Encoded data
  System.out.println("");
  System.out.println("Compression Factor [Integer to Binary code] = " + calcCompressionFactor(dataInput.length(), binaryData.length()));
  System.out.println("Compression Factor [Integer to Grey code] = " + calcCompressionFactor(dataInput.length(), greyData.length()));
  System.out.println("Compression Factor [Integer to Encoded data] = " + calcCompressionFactor(dataInput.length(), encodedInputLength));

  // Print the Saving Percetage of Binary code, Grey code and Encoded data
  System.out.println("");
  System.out.println("Saving percentage [Integer to Binary code] = " + calcSavingPercentage(dataInput.length(), binaryData.length()));
  System.out.println("Saving percentage [Integer to Grey code] = " + calcSavingPercentage(dataInput.length(), greyData.length()));
  System.out.println("Saving percentage [Integer to Encoded data] = " + calcSavingPercentage(dataInput.length(), encodedInputLength));
 }
}
