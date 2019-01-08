//Nicholas Tofani
//10-30-2016
//CS-240

import java.util.*;
  
public class conwaysGameOfLifeFitness {
      Boolean[][] coordinates;
      Boolean[][] originalInput;
      String originalInputString;
      boolean usingSmallerField = false;
      Integer currentRow;
      Integer currentColumn;
      double holdFitness = -1;
      Integer Length;
      Integer Width;
      
      public conwaysGameOfLifeFitness(Integer length, Integer width) {
         coordinates = new Boolean[length][width];
         for(int i = 0; i < length; i++) {
            for(int j = 0; j < width; j++) {
              coordinates[i][j] = new Boolean(false);
            }
         }
         currentRow = 1;
      } 
      
      public void reset() {
         if(coordinates == null) {
            coordinates = new Boolean[Length][Width];
         }
         for(int y = 0; y < coordinates.length; y++) {
            for(int x = 0; x < coordinates[y].length; x++) {
               coordinates[y][x] = new Boolean(false);
            }
         }
         holdFitness = -1;
      }
      
      public Boolean[][] smallerField(int newLength, int newWidth) {
         usingSmallerField = true;
         currentRow = (coordinates.length / 2) - (newLength / 2);
         currentColumn = (coordinates[0].length / 2) - (newWidth / 2);
         Boolean[][] newSmallerArray = new Boolean[newLength][newWidth]; 
         for(int y = 0; y < coordinates.length; y++) {
            for(int x = 0; x < coordinates[0].length; x++) {
               if(currentColumn <= x && (currentColumn + newWidth) > x && currentRow <= y && (currentRow + newLength) > y) {
                  newSmallerArray[x - currentColumn][y - currentRow] = new Boolean(false);
                  newSmallerArray[x - currentColumn][y - currentRow] = coordinates[x][y];
               }
            }
         }
         return newSmallerArray;
      }
      
      public double totalFitness() {
         holdFitness = fitness(originalInput, 1000); 
         return holdFitness;
      }
      
      public double fitness(Boolean[][] fitArray, int numOfIterations) {
         double startLiveCells = fitnessCounter(fitArray);
         for(int i = 0; i < numOfIterations; i++) {
            nextInteration();
         }
         double endLiveCells = fitnessCounter(smallerField(fitArray.length, fitArray[0].length));
         return (endLiveCells/(2 * startLiveCells));
      }
      
      public double fitnessCounter(Boolean[][] countArray) {
         double counter = 0;
         for(int i = 0; i < countArray.length; i++) {
            for(int j = 0; j < countArray[0].length; j++) {
               if(countArray[i][j]) {
                  counter++;
               }
            }
         }
         return counter;
      }
      
      public void input(String setOfData) {
         Integer tmp = currentColumn;
         originalInputString = setOfData;
         for(int i = 0; i < setOfData.length(); i++) {
            Integer currentInputNum = Character.getNumericValue(setOfData.charAt(i));
            if(currentInputNum == -1) {
               currentRow++;
               currentColumn = tmp;
            }
            else if(currentInputNum != 0) {
               inputConverter(currentInputNum, (i * 4));
            }
         }
         currentRow++;
         originalInput = coordinates;
      }
      
      public void inputConverter(Integer currentInputNum, Integer widthPosition) {
         Integer currentInput = currentInputNum;
         Integer currentX = widthPosition;
         if(usingSmallerField) {
            currentX = currentColumn;
            currentColumn = currentColumn + 4;
         }
         if((currentInput / 8) > 0) {
            coordinates[currentRow][currentX] = true;
            currentInput = currentInput - 8;
         } 
         if((currentInput / 4) > 0) {
            coordinates[currentRow][currentX + 1] = true;
            currentInput = currentInput - 4;
         }
         if((currentInput / 2) > 0) {
            coordinates[currentRow][currentX + 2] = true;
            currentInput = currentInput - 2;
         }
         if((currentInput / 1) > 0) {
            coordinates[currentRow][currentX + 3] = true;
         } 
      }
      
      public void dumpCompactLife() {
         for(int y = 0; y < (coordinates.length); y++) {
            Integer counter = 0;
            Integer tempWillBecomeHex = 0;
            for(int x = 0; x < (coordinates[0].length); x++) {
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
                  tempWillBecomeHex = 0;
                  counter = 0;
               }
            }
            System.out.println();
         }
         System.out.println();
      }
      
      public void dumpUserFriendlyLife() {
         for(int i = 0; i < coordinates.length; i++) {
            for(int j = 0; j < coordinates[0].length; j++) {
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
         Integer liveNeighbors = 0;
         boolean alive = false;
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
            if(liveNeighbors > 1 && 4 > liveNeighbors) {
               alive = true;
            }
         }
         else {
            if(liveNeighbors == 3) {
               alive = true;
            }
         }
         return alive;
      }
      
      public void nextInteration() {
         Boolean[][] temp = new Boolean[coordinates.length][coordinates[0].length];
         for(int i = 0; i < coordinates.length; i++) {
            for(int j = 0; j < coordinates[0].length; j++) {
              temp[i][j] = new Boolean(false);
            }
         }
         for(int y = 1; y < (coordinates.length - 1); y++) {
            for(int x = 1; x < (coordinates[0].length - 1); x++) {
               temp[y][x] = checkNeighbors(coordinates[y][x], y, x);
            }
         }
         coordinates = temp;
      }
      
      public static void main(String[] args) {
         conwaysGameOfLifeFitness CW = new conwaysGameOfLifeFitness(32, 32);
         CW.smallerField(8,8);
         CW.input("00,98,c6,04,84,1d,1a,33");
         CW.input("00,98,c6,04,84,1d,1a,33");
         System.out.println("00,98,c6,04,84,1d,1a,33");
         CW.dumpUserFriendlyLife();
         System.out.print("Fitness of just 8 by 8 Section: ");
         System.out.println(CW.fitness(CW.smallerField(8,8), 1000));
         System.out.print("Fitness of entire 32 by 32 Board: ");
         System.out.println(CW.totalFitness());
         CW.dumpUserFriendlyLife();
      }
      
}