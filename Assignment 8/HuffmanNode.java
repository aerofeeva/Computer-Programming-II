// Anastasia Erofeeva
// CSE 143
// TA: Grace Chen
// Assignment #8
//
// This program will create a frequency or a character/frequency pair 
// for the tree of character integer values and frequencies. It will
// also compare two frequencies. 

public class HuffmanNode implements Comparable<HuffmanNode> {
   
   // The integer value of a character.
   public int character;
   
   // The frequency of a character.
   public int frequency;
   
   // The "0" of the code.
   public HuffmanNode left;
   
   // The "1" of the code.
   public HuffmanNode right;
   
   // Takes an integer value of a character and a frequency of a
   // character. Uses them to create a character/frequency pair
   // for the tree of character integer values and frequencies.
   public HuffmanNode(int character, int frequency) {
      this(character, frequency, null, null);
   }
   
   // Takes a frequency of a character and uses it to create
   // a frequency for the tree of character integer values and
   // frequencies. If a character value is not specified, uses -1.
   public HuffmanNode(int frequency) {
      this(-1, frequency, null, null);
   }
   
   // Takes an integer value of a character, a frequency of a character,
   // the "0" of the code, and the "1" of the code. Uses them to create 
   // a character/frequency pair for the tree of character integer values 
   // and frequencies.
   public HuffmanNode(int character, int frequency, HuffmanNode left, HuffmanNode right) {
      this.character = character;
      this.frequency = frequency;
      this.left = left;
      this.right = right;
   }
   
   // Takes a frequency from the tree of character integer values and 
   // frequencies. Returns a negative number if this frequency is less 
   // than the other frequency, 0 if this frequency equals the other 
   // frequency, and a positive number if this frequency is greater than
   // the other frequency. 
   public int compareTo(HuffmanNode other) {
      return frequency - other.frequency;
   }
}