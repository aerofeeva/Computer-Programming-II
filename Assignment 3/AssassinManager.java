// Anastasia Erofeeva
// 01/26/16
// CSE 143
// TA: Grace Chen 
// Assignment #3
//
// This program will manage a game of assassin by keeping track of 
// who each player is stalking, as well as the history of who killed whom. 

import java.util.*;

public class AssassinManager {
   
   // The name of the first person playing the game.
   private AssassinNode killRingFront; 
   
   // The name of the first person killed in the game. 
   private AssassinNode graveyardFront;
   
   // Takes a list of names of the people playing the game and constructs 
   // a kill ring of who is stalking whom. Throws an IllegalArgumentException
   // if the list is empty (if there are no people playing the game).
   public AssassinManager(List<String> names) {
      if (names == null) {
         throw new IllegalArgumentException();       
      }
      LinkedList killRing = new LinkedList();
      killRingFront = new AssassinNode(names.get(0));
      for (int i = 1; i < names.size(); i++) {
         AssassinNode current = new AssassinNode(names.get(i));
         AssassinNode temp = killRingFront;
         while (temp.next != null) {
            temp = temp.next;
         }
         temp.next = current;
         current.next = null;
      }
   }
   
   // Prints the names of the people who are playing the game, reporting who 
   // is stalking whom. If only one person is playing the game, prints that the 
   // person is stalking themselves. 
   public void printKillRing() {
      if (killRingFront.next == null) {
         System.out.println("    " + killRingFront.name + " is stalking " + killRingFront.name);
      }
      AssassinNode current = killRingFront;
      AssassinNode temp = current.next;
      while (temp != null) {
         System.out.println("    " + current.name + " is stalking " + temp.name);
         current = current.next;
         temp = temp.next;
      }
      System.out.println("    " + current.name + " is stalking " + killRingFront.name);
   }
   
   // Prints the names of the people who are in the graveyard (no longer 
   // in the game), reporting who killed whom. Prints the name of the
   // most recently killed person first, then the next, and so on. Does
   // not print anything if the graveyard is empty (if all the players are still 
   // in the game).     
   public void printGraveyard() {      
      if (graveyardFront != null) {
         AssassinNode current = graveyardFront;
         while (current != null) {
            System.out.println("    " + current.name + " was killed by " + current.killer);
            current = current.next;
         }
      }     
   }
   
   // Takes a given name of a person and checks (ingnoring the case of the 
   // letters) if that person is on the list of people still in the game 
   // (players who haven't been killed yet). Returns whether or not the given 
   // name is found on the list. 
   public boolean killRingContains(String name) {
      AssassinNode current = killRingFront;
      while (current != null) {
         if (current.name.equalsIgnoreCase(name)) {
            return (current.name.equalsIgnoreCase(name));   
         }
         current = current.next;
      }
      return false;
   }
   
   // Takes a given name of a person and checks (ignoring the case of 
   // the letters) if that person is in the graveyard (the list players 
   // who were killed in the game). Returns whether or not the given name is 
   // found in the graveyard. 
   public boolean graveyardContains(String name) {
      AssassinNode current = graveyardFront;
      while (current != null) {
         if (current.name.equalsIgnoreCase(name)) {
            return (current.name.equalsIgnoreCase(name));
         }
         current = current.next;
      }
      return false;
   }
   
   // Checks if the game is over by seeing if there is only one person left
   // in the game (the person who was not killed as a result of the game).
   // Returns whether or not the game is over. 
   public boolean gameOver() {
      return (killRingFront.next == null);
   }
   
   // Checks whether or not the game is over and returns the name of the 
   // winner (the only person left in the game). Returns null if the game is
   // not over. 
   public String winner() {
      if (killRingFront.next == null) {
         return killRingFront.name;
      } else {
         return null;
      }
   }
   
   // Takes a name of a person and kills them in the game, by removing their 
   // name from the list of people playing the game and adding it to the
   // graveyard (the list of people who were killed in the game). Does not 
   // change the order of the list of the names of people still in the game. 
   // Throws and IllegalArgumentException if the given name (ignoring the 
   // case of the letters) is not the name of one of the players initially 
   // playing the game. Throws and IllegalStateException if the game is over 
   // (if there is only one person left in the game). 
   public void kill(String name) {
      if (killRingFront.next == null) {
         throw new IllegalStateException();
      }
      if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      LinkedList graveyard = new LinkedList();
      AssassinNode current = killRingFront;
      AssassinNode temp = killRingFront;
      if (killRingFront.name.equalsIgnoreCase(name)) {
         killRingFront = killRingFront.next;
         while (current.next != null) {
            current = current.next;
            temp.killer = current.name;
         }
         temp.next = graveyardFront;
         graveyardFront = temp;
         graveyard.add(current.name);
      } else {
         while (!current.next.name.equalsIgnoreCase(name)) {
            current = current.next;
         }
         temp = current.next;
         temp.killer = current.name;
         current.next = current.next.next;
         temp.next = graveyardFront;
         graveyardFront = temp;
         graveyard.add(temp.name);
      }
   }
}
