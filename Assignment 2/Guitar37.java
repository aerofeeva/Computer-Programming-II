// Anastasia Erofeeva
// 01/18/16
// CSE 143
// TA: Grace Chen 
// Assignment #2
//
// This program will...

public class Guitar37 implements Guitar {
    
    public static final String KEYBOARD =
        "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
    
    private GuitarString[] arrayOfStrings;
    private int time;
    
    public Guitar37() {
      arrayOfStrings = new GuitarString[37];
      for (int i = 0; i < 37; i++) {
         double frequency = 440.0 * Math.pow(2.0,((i - 24.0) / 12.0));
         arrayOfStrings[i] = new GuitarString(frequency);
      }  
    }
    
    public void playNote(int pitch) {
      if (pitch >= -12 && pitch < 25)
      arrayOfStrings[pitch + 12].pluck();         
    }
    
    public boolean hasString(char string) {
      return (KEYBOARD.contains(string + "")); 
    }
    
    public void pluck(char string) {
      if (!KEYBOARD.contains(string + "")) {
         throw new IllegalArgumentException();
      } else {
         int index = KEYBOARD.indexOf(string);
         arrayOfStrings[index].pluck();      
      }
    }
    
    public double sample() {
      double sum = 0.0;
      for (int i = 0; i < 37; i++) {
         sum += arrayOfStrings[i].sample();   
      }
      return sum;
    }
    
    public void tic() {
      for (int i = 0; i < 37; i++) {
         arrayOfStrings[i].tic();
      }
      time++;
    }
    
    public int time() {
      return time;  
    }
}