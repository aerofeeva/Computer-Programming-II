// Anastasia Erofeeva
// 02/21/16
// CSE 143
// TA: Grace Chen
// Assignment #6
//
// This program will use a dictionary to find all combinations of words
// that have the same letters as a given phrase.

import java.util.*;

public class AnagramSolver {
  
   // The dictionary stored as the associations between words and the 
   // combinations of letters that make up those words. 
   private Map<String, LetterInventory> inventoryMap;
   
   // The dictionary stored as a list of words.
   private List<String> dictionary;
   
   // Takes a nonempty list of words and uses the list as a dictionary.
   // Assumes that there are no duplicates in the dictionary. Creates 
   // associations between the words in the dictionary and the combinations
   // of letters that make up those words. Does not change the original list
   // of words.
   public AnagramSolver(List<String> list) {
      dictionary = list;
      inventoryMap = new HashMap<String, LetterInventory>();
      for (String word: dictionary) {
         inventoryMap.put(word, new LetterInventory(word));
      }
   }
   
   // Takes a string s and an integer max as parameters. Uses the given word
   // or phrase to preprocess the dictionary, keeping only the words that are
   // relevant. Uses the pruned dictionary to find and print combinations of 
   // words that have the same letters as the given string and that include at
   // most max words. If max is zero, the maximum number of words is unlimited. 
   // If max is less than zero, throws an IllegalArgumentException.
   public void print(String s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      }
      List<String> anagram  = new ArrayList<String>();
      List<String> relevantDictionary = new ArrayList<String>();
      LetterInventory phrase = new LetterInventory(s);
      for (String word: dictionary) {
         if (phrase.subtract(inventoryMap.get(word)) != null) {
            relevantDictionary.add(word);
         }
      }
      explore(phrase, anagram, relevantDictionary, max);
   }
   
   // Helper method that takes a LetterInventory of a phrase, an anagram
   // of a list of strings, a relevant dictionary of a list of strings, and
   // an integer max as parameters. If the length of the phrase is zero and 
   // the number of words in the anagram is less than or equal to the max, or 
   // if the length of the phrase is zero and max is zero, prints the anagram.
   // Otherwise, checks all of the words in the relevant dictionary to see
   // whether or not they can be subtracted from the given phrase. If the word
   // can be subtracted, it is added to the anagram and the phrase is updated.
   // Then, the entire process is repeated, using the updated phrase. At the 
   // end, the word is removed from the anagram, allowing other possibilities 
   // to be explored. This method calls itself and is called in the public 
   // print method above.
   private void explore(LetterInventory phrase, List<String> anagram, 
                        List<String> relevantDictionary, int max) {
      if (phrase.size() == 0 && (anagram.size() <= max || max == 0)) {
         System.out.println(anagram);
      } else {
         for (String word: relevantDictionary) {
            if (phrase.subtract(inventoryMap.get(word)) != null) {
               anagram.add(word);
               LetterInventory updatedPhrase = phrase.subtract(inventoryMap.get(word)); 
               explore(updatedPhrase, anagram, relevantDictionary, max);
               anagram.remove(word);
            }
         }
      }         
   }
}