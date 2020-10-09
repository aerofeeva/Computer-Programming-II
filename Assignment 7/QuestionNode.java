// Anastasia Erofeeva
// 02/28/16
// CSE 143
// TA: Grace Chen
// Assignment #7
//
// This program will create one question or answer for the tree of 
// questions and answers.

public class QuestionNode {
   
   // The data (question or answer) contained in the given unit of the tree.
   public String data;
   
   // The "yes" answer to the question.
   public QuestionNode left;
   
   // The "no" answer to the question.
   public QuestionNode right;
   
   // Takes a String data and uses it to create one answer for the tree of
   // questions and answers.
   public QuestionNode(String data) {
      this(data, null, null);
   }
   
   // Takes a String data, as well as the "yes" answer and the "no" answer.
   // Uses them to create one question for the tree of questions and answers.
   public QuestionNode(String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }
}