// Anastasia Erofeeva
// 01/18/16
// CSE 143
// TA: Grace Chen 
// Assignment #2 (Part 1)
//
// This program will simulate a guitar string of a given frequency.

import java.util.*;

public class GuitarString {

   // A ring buffer, which models a string tied down at both ends.
   private Queue<Double> ringBuffer;
   
   // A random value between -0.5 and 0.5.
   private Random value;
   
   public static final double ENERGY_DECAY_FACTOR = 0.996;
   
   // Takes a double frequency and uses it to compute the capacity 
   // of the ring buffer. Throws an IllegalArgumentException if the 
   // frequency < 0.0 or if the capacity < 2. Otherwise, constructs 
   // a ring buffer of the specific capacity.
   public GuitarString(double frequency) {
      if (frequency < 0.0) {
         throw new IllegalArgumentException();
      }
      int size = (int) Math.round(StdAudio.SAMPLE_RATE / frequency);
      if (size < 2) {
         throw new IllegalArgumentException();
      }
      ringBuffer = new LinkedList<Double>();
      for (int i = 0; i < size; i++) {
         ringBuffer.add(0.0);
      }  
   }
   
   // Takes a data structure of doubles and initializes the ring buffer 
   // to the values in the data structure. Throws an IllegalArgumentException 
   // if the data structure has less than 2 values.  
   public GuitarString(double[] init) {
      if (init.length < 2) {
         throw new IllegalArgumentException();
      }
      ringBuffer = new LinkedList<Double>();
      for (int i = 0; i < init.length; i++) {
         ringBuffer.add(init[i]);
      }  
   }
   
   // Replaces the values in the ring buffer with random values
   // between -0.5 and 0.5. 
   public void pluck() {
      value = new Random();
      for (int i = 0; i < ringBuffer.size(); i++) {
         double randomValue = value.nextDouble() - 0.5;
         ringBuffer.add(randomValue);
         ringBuffer.remove();
      }
   }
   
   // Applies the Karplus-Strong update by calculating the average of the 
   // first two values in the ring buffer multiplied by the energy decay factor. 
   // Adds this value to the end of the ring buffer and removes the value at the 
   // front of the ring buffer.  
   public void tic() {
      ringBuffer.add(((ringBuffer.remove() + ringBuffer.peek()) / 2.0) * ENERGY_DECAY_FACTOR);   
   }
   
   // Returns the value at the front of the ring buffer. 
   public double sample() {
      return ringBuffer.peek();   
   }
}