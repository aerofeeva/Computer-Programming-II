// Anastasia Erofeeva
// CSE 143
// TA: Grace Chen
// Assignment #8
//
// This program will compress a text file based on the frequency of 
// characters in that file.

import java.util.*;
import java.io.*;

public class HuffmanTree {
   
   // The overall root of the tree of character frequencies.
   private HuffmanNode overallRoot;
   
   // Takes in the frequencies of the characters and uses them to create
   // a tree of character frequencies and character integer values. Adds
   // an end-of-file character to signal the end of the file.
   public HuffmanTree(int[] count) {
      Queue<HuffmanNode> frequencies = new PriorityQueue<HuffmanNode>();
      int max = 0;
      int rootIndex = 0;
      for (int i = 0; i < count.length; i++) {
         if (count[i] != 0) {
            HuffmanNode leaf = new HuffmanNode(i, count[i]);
            frequencies.add(leaf);
         }
      }
      HuffmanNode eof = new HuffmanNode(count.length, 1);
      frequencies.add(eof);
      while (frequencies.size() >= 2) {
         HuffmanNode node1 = frequencies.remove();
         HuffmanNode node2 = frequencies.remove();
         HuffmanNode branch = new HuffmanNode(node1.frequency + node2.frequency);
         branch.left = node1;
         branch.right = node2;
         frequencies.add(branch);
         overallRoot = branch;
      }
   }
   
   // Takes an output file and prints the current tree to the ouput file 
   // in standard format. The first line of ouptut represents the character
   // integer value. The secode line represents the code to be used for that
   // character.
   public void write(PrintStream output) {
      String code = "";
      writeHelper(output, overallRoot, code);
   }
   
   // Private helper method that takes an output file, a HuffmanNode root and
   // a String code. Uses the parameters to print a tree to the output file in
   // standard format. The first line represents the character integer value. 
   // The second line represents the code to be used for that character. The 
   // code represents the path from the root of the tree to the leaf node with
   // the specific character.  
   private void writeHelper(PrintStream output, HuffmanNode root, String code) { 
      if (root.left != null && root.right != null) {
         writeHelper(output, root.left, code + "0");
         writeHelper(output, root.right, code + "1");
      } else {
         output.println(root.character);
         output.println(code);
      }   
   }
   
   // Takes an input file and reads the character integer value and the
   // code for each character. Uses these to create a tree of character
   // integer values.
      public HuffmanTree(Scanner input) {
      while (input.hasNextLine()) {
         int value = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = HuffmanTreeHelper(input, value, code, overallRoot); 
      }
   }
   
   // Private helper method that takes an input file, an integer value of a 
   // character, a String code and a HuffmanNode root. Uses the parameters 
   // to build a tree based on the code. The code represents the path from 
   // the root of the tree to the leaf node with the given character.
   private HuffmanNode HuffmanTreeHelper(Scanner input, int value, String code, HuffmanNode root) {
      if (code.length() == 0) {
         root = new HuffmanNode(value, -1);
      } else {
         if (root == null) {
            root = new HuffmanNode(-1);
         }
         if (code.charAt(0) == '0') {
            root.left = HuffmanTreeHelper(input, value, code.substring(1), root.left);
         } else {
            root.right = HuffmanTreeHelper(input, value, code.substring(1), root.right);
         } 
      } 
      return root;
   }
   
   // Takes an input file, an output file and an integer end-of-file character. 
   // Uses them to read code from the input file and write the character integer 
   // values to the output file. Stops reading from the input file, when the 
   // enf-of-file character is encountered. Assumes that the input file contains
   // legal code for the characters.
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode current = overallRoot;
      while (current.character != eof) {
         if (current.left == null || current.right == null) {
            output.write(current.character);
            current = overallRoot;
         } else {
            while (current.left != null && current.right != null) {
               if (input.readBit() == 0) {
                  current = current.left;
               } else {
                  current = current.right;
               }
            }
         }
      }
   }
}