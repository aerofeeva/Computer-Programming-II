// Anastasia Erofeeva
// 01/18/16
// CSE 143
// TA: Grace Chen 
// Assignment #2 (Part 2)
//
// This program will simulate a guitar with 37 different strings.

public class Guitar37 implements Guitar {
    
    // Data structure with 37 different strings.
    private GuitarString[] arrayOfStrings;
    
    // The number of times the Karplus-Strong update has been applied. 
    private int time;
    
    public static final String KEYBOARD =
      "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
    
    // Constructs a data structure with 37 guitar strings, each 
    // with a different frequency.
    public Guitar37() {
      arrayOfStrings = new GuitarString[37];
      for (int i = 0; i < 37; i++) {
         double frequency = 440.0 * Math.pow(2.0,((i - 24.0) / 12.0));
         arrayOfStrings[i] = new GuitarString(frequency);
      }  
    }
    
    // Takes an integer pitch and converts it to the index of 
    // a guitar string in the data structure. Plucks the string at
    // that index in the data structure in order to play the note. 
    // Pitch must be >= -12 and < 25. 
    public void playNote(int pitch) {
      if (pitch >= -12 && pitch < 25)
      arrayOfStrings[pitch + 12].pluck();         
    }
    
    // Takes a character string and checks whether the character 
    // corresponds to a key on the keyboard. 
    public boolean hasString(char string) {
      return (KEYBOARD.contains(string + "")); 
    }
    
    // Takes a character string and determines its index on the keyboard. 
    // Plucks the string at the specific index in the data structure in order 
    // to play the note. Throws an IllegalArgumentException if the key is not 
    // one of the 37 keys this guitar is designed to play.  
    public void pluck(char string) {
      if (!KEYBOARD.contains(string + "")) {
         throw new IllegalArgumentException();
      } else {
         int index = KEYBOARD.indexOf(string);
         arrayOfStrings[index].pluck();      
      }
    }
    
    // Calculates and returns the cumulative sum of all of the
    // frequencies in the data structure of guitar strings. 
    public double sample() {
      double sum = 0.0;
      for (int i = 0; i < 37; i++) {
         sum += arrayOfStrings[i].sample();   
      }
      return sum;
    }
    
    // Advances the time forward by one, each time the 
    // Karplus-Strong update is performed. 
    public void tic() {
      for (int i = 0; i < 37; i++) {
         arrayOfStrings[i].tic();
      }
      time++;
    }
    
    // Returns the current time, based on the number of "tics"
    // (Karplus-Strong updates) performed. 
    public int time() {
      return time;  
    }
}