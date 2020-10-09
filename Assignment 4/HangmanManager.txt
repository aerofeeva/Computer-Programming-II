// Anastasia Erofeeva
// 02/02/16
// CSE 143
// TA: Grace Chen 
// Assignment #4
//
// This program will manage a game of Hangman by keeping track of the 
// guesses the user makes, which allows the computer to delay choosing 
// a word until it is forced to. 

import java.util.*;

public class HangmanManager {
   
   // Originally: the set of words from the dictionary of the given length. 
   // After guesses are made: the largest set of words with the given pattern.
   private Set<String> setOfWords;
   
   // The number of guesses the user has left.
   private int guessesLeft;
   
   // The current set of all letters that have been guessed by the user. 
   private SortedSet<Character> guesses;
   
   // Originally: a pattern in which each letter in a word is displayed as a dash.
   // After guesses are made: a pattern in which only letters that have not yet 
   // been guessed are displayed as dashes.
   private String pattern;
   
   // Takes a dictionary of words, a target word length and a maximum number
   // of guesses the player is allowed to make, and initializes the set of 
   // words to contain all words from the dictionary of the given length, 
   // eliminating duplicates. Throws an IllegalArgumentException if length is
   // less than 1 or if max is less than 0. Initializes the number of guesses
   // left to the maximum number of guesses the player is allowed to make. 
   // Creates a set of characters to keep track of which letters have been guessed. 
   // Initializes the pattern to have each letter in a word represented by a dash, 
   // separated by spaces, with no leading or trailing spaces. 
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      setOfWords = new TreeSet<String>();
      for (String word: dictionary) {
         if (word.length() == length) {
            setOfWords.add(word);
         }
      }
      guessesLeft = max;
      guesses = new TreeSet<Character>();
      pattern = "-";
      for (int i = 1; i <= length - 1; i++) {
         pattern += " -"; 
      }
   }
   
   // Originally: returns the set of words from the dictionary of the given length. 
   // After guesses are made: returns the largest set of words with the given pattern, 
   // which are being considered by the computer. 
   public Set<String> words() {
      return setOfWords;
   }
   
   // Returns the number of guesses the user has left.
   public int guessesLeft() {
      return guessesLeft;
   }
   
   // Returns the current set of letters that have already been guessed by the user. 
   public SortedSet<Character> guesses() {
      return guesses;     
   }
   
   // Returns the current pattern (separated by spaces, without leading or trailing spaces)
   // displayed by the game, taking into account the guesses that have been made. 
   // Letters that have not yet been guessed are displayed as dashes, while letters
   // that have been guessed are displayed as lowercase letters. Throws an 
   // IllegalStateException if the set of words is empty. 
   public String pattern() {
      if (setOfWords.isEmpty()) {
         throw new IllegalStateException();
      }
      return pattern;
   }
   
   // Takes a character guess and records the guess made by the user by 
   // adding it to the set of guesses. Uses the set of words to create all 
   // possible patterns with the given guess. Based on which pattern matches the
   // most words, the computer determines which set of words to use going forward.
   // The computer chooses the pattern with the most words. If the new set (chosen
   // by the computer) contains the guessed letter, the guess is correct, and the 
   // pattern is updated to have all  occurences of the letter. If the guess is incorrect, 
   // the number of guesses left decreases by 1. Throws an IllegalStateException
   // if the user has fewer than 1 guess left or if the set of words is empty.
   // Throws an IllegalArgumentException if the set of words is not empty and the 
   // character being guessed was guessed before. 
   public int record(char guess) {
      throwExceptions(guess);
      guesses.add(guess);
      Map <String, Set<String>> patternToWords = new TreeMap<String, Set<String>>();
      String currentPattern = pattern;
      for (String word: setOfWords) {
         currentPattern = makePattern(word, guess);
         if (!patternToWords.containsKey(currentPattern)) {
            patternToWords.put(currentPattern, new TreeSet<String>());
         }
         patternToWords.get(currentPattern).add(word);
      }
      String newPattern = updatePattern(patternToWords);
      pattern = newPattern;
      updateGuessesLeft(newPattern, guess);
      return countLetters(newPattern, guess);
   }
   
   // Private method that throws the IllegalState and IllegalArgument exceptions 
   // for the record method. This method is called in the record method.
   private void throwExceptions(char guess) {
      if (guessesLeft < 1 || setOfWords.isEmpty()) {
         throw new IllegalStateException();
      }
      if (guesses.contains(guess)) {
         throw new IllegalArgumentException();
      }   
   } 
   
   // Private method that takes a String word and a character guess as parameters 
   // and returns the current pattern made based on the occurrences of the guessed 
   // letter in the given word. If the word contains the guessed letter, the letter 
   // replaces the dash in the pattern for that word. If the word does not contain
   // the guessed letter, the pattern remains unchanged. This method is called in the
   // record method.
   private String makePattern(String word, char guess) {
      String currentPattern = pattern;
      for (int i = 0; i < word.length(); i++) {
         if (word.charAt(i) == guess) {
            String substring1 = currentPattern.substring(0, i * 2);  
            String substring2 = currentPattern.substring((i * 2) + 1, currentPattern.length());
            currentPattern = substring1 + guess + substring2;   
         }
      }
      return currentPattern;      
   } 
   
   // Private method that takes a map of patterns to words as a parameter and 
   // returns the new pattern to be used by the game. The pattern that matches 
   // the most words in the set of words is chosen as the new pattern. This method
   // is called in the record method. 
   private String updatePattern(Map <String, Set<String>> patternToWords) {
      int max = 0;
      String newPattern = pattern;
      Set<String> values = setOfWords;
      for (String pattern: patternToWords.keySet()) {
         values = patternToWords.get(pattern);
         if (values.size() > max) {
            max = values.size();
            newPattern = pattern;
            setOfWords = values;
         }
      }
      return newPattern;   
   }
   
   // Private method that takes a String pattern and a character guess as parameters
   // and uses them to update the number of guesses the user has left. If the guess 
   // is incorrect, the number of guesses decreases by 1. This method is called in the
   // record method. 
   private void updateGuessesLeft(String pattern, char guess) {
      if (pattern.indexOf(guess) == -1) {
         guessesLeft--;
      }
   }
   
   // Private method that takes a String pattern and a character guess as parameters
   // and uses them to count and return the number of occurrences of the guessed letter 
   // in the pattern. This method is called in the record method. 
   private int countLetters(String pattern, char guess) {
      int count = 0;
      for (int i = 0; i < pattern.length(); i++) {
         if (pattern.charAt(i) == guess) {
            count++;
         }
      }
      return count;
   }
}