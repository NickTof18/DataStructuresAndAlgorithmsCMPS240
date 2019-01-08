//Nicholas Tofani
//10-23-2016
//CS-240

import java.util.*;

public class conwaysGameOfLife<T> {
   Boolean[][] coordinates;
   Integer length;
   Integer width;
   Integer currentRow;
   
   public conwaysGameOfLife(Integer length, Integer width) {
      coordinates = new Boolean[length][width];
      for(int i = 0; i < length; i++) {
         for(int j = 0; j < width; j++) {
           coordinates[i][j] = new Boolean(false);
         }
      }
      this.length = length;
      this.width = width;
      this.currentRow = 2;
   } 
   
   public void input(String setOfData) {
      for(int i = 0; i < setOfData.length(); i++) {
         Integer currentInputNum = Character.getNumericValue(setOfData.charAt(i));
         if(currentInputNum != 0) {
            inputConverter(currentInputNum, (i * 4));
         }
      } 
      currentRow++;
   }
   
   public void inputConverter(Integer currentInputNum, Integer currentNode) {
      Integer currentInput = currentInputNum;
      if((currentInput / 8) > 0) {
         coordinates[currentRow][currentNode] = true;
         currentInput = currentInput - 8;
      } 
      if((currentInput / 4) > 0) {
         coordinates[currentRow][currentNode + 1] = true;
         currentInput = currentInput - 4;
      }
      if((currentInput / 2) > 0) {
         coordinates[currentRow][currentNode + 2] = true;
         currentInput = currentInput - 2;
      }
      if((currentInput / 1) > 0) {
         coordinates[currentRow][currentNode + 3] = true;
      } 
   }
   
   public void dumpCompactLife() {
      for(int y = 0; y < (length); y++) {
         Integer counter = 0;
         Integer tempWillBecomeHex = 0;
         for(int x = 0; x < (width); x++) {
            if(coordinates[y][x]) {
               if(counter == 0) {
                  tempWillBecomeHex = tempWillBecomeHex + 8;
               }
               if(counter == 1) {
                  tempWillBecomeHex = tempWillBecomeHex + 4;
               }
               if(counter == 2) {
                  tempWillBecomeHex = tempWillBecomeHex + 2;
               }
               if(counter == 3) {
                  tempWillBecomeHex = tempWillBecomeHex + 1;
               }                 
            }
            counter++;
            if(counter == 4) {
               System.out.print(Integer.toHexString(tempWillBecomeHex));
               tempWillBecomeHex = 0;
               counter = 0;
            }
         }
         System.out.println();
      }
      System.out.println();
   }
   
   public void dumpUserFriendlyLife() {
      for(int i = 0; i < length; i++) {
         for(int j = 0; j < width; j++) {
            if(coordinates[i][j]) {
               System.out.print("*");
            }
            else {
               System.out.print(".");
            }
         }
         System.out.println();
      }
      System.out.println();
   }
   
   public boolean checkNeighbors(Boolean currentNode, Integer y, Integer x) {
      int liveNeighbors = 0;
      if(coordinates[y + 1][x]) {
         liveNeighbors++;
      }
      if(coordinates[y + 1][x + 1]) {
         liveNeighbors++;
      }
      if(coordinates[y][x + 1]) {
         liveNeighbors++;
      }
      if(coordinates[y - 1][x + 1]) {
         liveNeighbors++;
      }
      if(coordinates[y - 1][x]) {
         liveNeighbors++;
      }
      if(coordinates[y - 1][x - 1]) {
         liveNeighbors++;
      }
      if(coordinates[y][x - 1]) {
         liveNeighbors++;
      }
      if(coordinates[y + 1][x - 1]) {
         liveNeighbors++;
      }
      if(currentNode) {
         if(liveNeighbors > 1 && liveNeighbors < 4) {
            return true;
         }
      }
      else {
         if(liveNeighbors == 3) {
            return true;
         }
      }
      return false;
   }
   
   public void nextInteration() {
      Boolean[][] temp = new Boolean[length][width];
      for(int i = 0; i < length; i++) {
         for(int j = 0; j < width; j++) {
           temp[i][j] = new Boolean(false);
         }
      }
      for(int y = 1; y < (length - 1); y++) {
         for(int x = 1; x < (width - 1); x++) {
            temp[y][x] = checkNeighbors(coordinates[y][x], y, x);
         }
      }
      coordinates = temp;
   }
   
   public static void main(String[] args) {
      conwaysGameOfLife<Integer> CW = new conwaysGameOfLife<Integer>(32, 32);
      CW.input("00030000");
      CW.input("00030000");
      CW.input("0000C000");
      CW.input("0000C000");
      CW.dumpUserFriendlyLife();
      CW.dumpCompactLife();
      
      CW.nextInteration();
      CW.dumpUserFriendlyLife();
      CW.dumpCompactLife();
      
      CW.nextInteration();
      CW.dumpUserFriendlyLife();
      CW.dumpCompactLife();
   }
   
}