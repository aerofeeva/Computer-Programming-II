// Anastasia Erofeeva
// 01/14/16
// CSE 143
// TA: Grace Chen
// Assignment #1
//
// This program will keep track of an inventory of letters of the alphabet.

import java.util.*;

public class LetterInventory {
   private String string;
   private int[] countLetters;
   private int size;
   private int value; 
   private char letter;
   private LetterInventory sum;
   private LetterInventory difference;
   
   public static final int NUM_LETTERS = 26;
    
   // post: constructs an empty array with a capacity of 26
   public LetterInventory(String data) {
      countLetters = new int[NUM_LETTERS];
      string = data.toLowerCase();
      size = 0;
      
      for (int i = 0; i < string.length(); i++) {
         char letter = string.charAt(i);
         if (Character.isAlphabetic(letter)) {
            countLetters[letter - 'a']++;
         } 
      }
   }
   
   // post: returns the current number of elements in the array
   public int size() {
      for (int i = 0; i <= countLetters.length - 1; i++) {
         size = size + countLetters[i];    
      }
      return size; 
   }
   
   // post: returns true if the inventory is empty, false otherwise
   public boolean isEmpty() {
      return size == 0;  
   }
   
   // pre: character is alphabetic (throws IllegalArgumentException if not)
   // post: returns a count of how many of the letter are in the inventory
   public int get(char letter) { 
      letter = Character.toLowerCase(letter);  
      if (!Character.isAlphabetic(letter)) {
         throw new IllegalArgumentException();
      } 
      return countLetters[letter - 'a'];
   }
   
   // post: creates a sorted, lowercase, bracketed version of the letters in the inventory
   public String toString() {
      char[] arrayOfChars = string.replaceAll("[^a-zA-Z]", "").toCharArray();
      String result = "[";
      Arrays.sort(arrayOfChars);
      result += new String(arrayOfChars);
      result += "]";
      return result;  
   }
   
   // pre: character is alphabetic and value > 0 (throws IllegalArgumentException if not)
   // post: sets the count for the given letter to the given value
   public void set(char letter, int value) {
      letter = Character.toLowerCase(letter);
      if (!Character.isAlphabetic(letter) || value < 0) {
         throw new IllegalArgumentException(); 
      }
      countLetters[letter - 'a'] = value; 
      for (int i = 1; i <= value; i++) {
         char[] array = new char[value];
         Arrays.fill(array, letter);
         string = Arrays.toString(array);   
      }
   }
   
   // post: returns the sum of two letter inventories
   public LetterInventory add(LetterInventory other) {
      return sum;
   }
   
   // post: returns the difference of the two inventories
   public LetterInventory subtract(LetterInventory other) {
      return difference;
   }
}

