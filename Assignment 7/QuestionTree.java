// Anastasia Erofeeva
// 02/28/16
// CSE 143
// TA: Grace Chen
// Assignment #7
//
// This program will allow the user to play a yes/no guessing game by
// using a tree to keep track of the questions and answers.

import java.io.*;
import java.util.*;

public class QuestionTree {
   
   // The input from the user. 
   private Scanner console;
   
   // The overall root of the tree of questions and answers.
   private QuestionNode overallRoot;
   
   // Sets up a way for the user to interact with the computer. Creates 
   // a tree of questions and answers with one initial object with the value
   // of "computer".
   public QuestionTree() {
      console = new Scanner(System.in);
      overallRoot = new QuestionNode("computer");
   }
   
   // Takes an input file and reads the file line by line.
   // Uses the information from the file to replace the current tree of 
   // questions and answers with a new tree. Assumes that the file is 
   // legal and in standard format. 
   public void read(Scanner input) {
      overallRoot = readHelper(input);
   }
   
   // Private helper method that takes a Scanner input linked to a file 
   // and reads the input using calls on nextLine. Uses the information
   // from the file to replace the current tree of questions and answers 
   // with a new tree. Assumes that the file is legal and in standard
   // format. Returns the tree. This method calls itself and is called by
   // the public read method above.
   private QuestionNode readHelper(Scanner input) {
      String nodeType = input.nextLine();
      String data = input.nextLine();
      QuestionNode root = new QuestionNode(data);
      if (nodeType.equals("Q:")) {
         root.left = readHelper(input);
         root.right = readHelper(input);
      }
      return root;
   }
   
   // Takes an output file, which is open for writing, and prints the
   // current tree to the output file. If the root is an answer, prints
   // "A:" and the object. If the root is a question, prints "Q:" and
   // the question.
   public void write(PrintStream output) {
      writeHelper(output, overallRoot);
   }
   
   // Private helper method that takes a PrintStream output and a 
   // QuestionNode root. If the root is an answer (leaf node), prints 
   // "A:" and the data of the given root. Otherwise, if the root is
   // a question (branch node), prints "Q:" and the data of the given
   // root. Repeats this process for all of the nodes in the tree. This
   // method calls itself and is called in the public write method above.
   private void writeHelper(PrintStream output, QuestionNode root) {
      if (root.left == null || root.right == null) {
         output.println("A:");
         output.println(root.data);
      } else {
         output.println("Q:");
         output.println(root.data);
         writeHelper(output, root.left);
         writeHelper(output, root.right);
      }
   }
   
   // Uses the current tree of questions and answers to ask the user
   // a series of yes/no questions until the computer guesses the correct
   // object, or until the computer fails. If the computer fails to guess
   // the correct object, it asks the user for the name of the object and 
   // a distinguishing question for that object. The tree of questions is 
   // then expanded to include the new data.
   public void askQuestions() {
      overallRoot = askQuestionsHelper(overallRoot);
   }
   
   // Private helper method that takes a QuestionNode root. If the root
   // is an answer (leaf node), asks the user if their object is the root.
   // If the user responds "y", prints "Great, I got it right!". Otherwise, 
   // if the user responds "n", asks the user for the name of the object and 
   // a yes/no question that distinguishes between their object and the root.
   // Creates new QuestionNodes for the object and the question that
   // distinguishes it. Then, asks for the answer to the distinguishing 
   // question for the user's object. If the answer is "y", adds the object
   // to the left of the question branch, and adds the original root to the 
   // right of the question branch. Otherwise, if the answer is "n", does the 
   // opposite. Sets root equal to the new tree. If the original root was a 
   // question (branch node), prints the question and asks the user for a 
   // yes/no answer. If the answer is "y", adds to the left of the branch node
   // the result of recursing with root.left as the parameter. Otherwise, if
   // the answer is "n", adds to the right of the branch node the result of
   // recursing with root.right as the parameter. Returns the new tree. This
   // method calls itself and is called in the askQuestions method above.
   private QuestionNode askQuestionsHelper(QuestionNode root) {
      if (root.left == null || root.right == null) {
         if (yesTo("Would your object happen to be " + root.data + "?") == true) {
            System.out.println("Great, I got it right!");
         } else {
            System.out.print("What is the name of your object? ");
            String object = console.nextLine();
            QuestionNode leaf = new QuestionNode(object);
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            String question = console.nextLine();
            QuestionNode branch = new QuestionNode(question);
            if (yesTo("And what is the answer for your object?") == true) {
               branch.left = leaf;
               branch.right = root;
            } else {
               branch.left = root;
               branch.right = leaf;
            }
            root = branch;
         }
      } else {
         if (yesTo(root.data)) {
            root.left = askQuestionsHelper(root.left);
         } else {
            root.right = askQuestionsHelper(root.right);
         }
      }
      return root;
   }
   
   // Takes a string prompt as a parameter and asks the user a question,
   // forcing an answer of "y" or "n". Returns true if the answer was yes,
   // returns false otherwise.
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
}