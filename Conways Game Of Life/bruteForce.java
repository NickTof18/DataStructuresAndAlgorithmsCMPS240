//Nicholas Tofani
//11-7-2016
//CS-240

import java.util.*;
import java.util.Random;

public class bruteForce {
   Random r;
   Integer iterationCounter = 0;
    
   public static void main(String[] args) {
      bruteForce bf = new bruteForce();
      bf.runBF(8.0);
   }
  
   public bruteForce() {
      r  = new Random(System.currentTimeMillis());
   }
   
   public void runBF(double hours) {
      System.out.println("Running for " + hours + " hours...");
      long startTime = System.currentTimeMillis(); 
      ConwaysGameOfLife currentBest = new ConwaysGameOfLife(32,32);
      currentBest.smallerField(8,8);
      currentBest.input("00,00,00,01,00,00,00,00");
      while(false || (System.currentTimeMillis()-startTime) < ((hours) * 3600000)) {
         ConwaysGameOfLife currentlyWorking = new ConwaysGameOfLife(32,32);
         String willInput = "";
         for(int j = 0; j < 8; j++) {
            String hex1 = Integer.toHexString(r.nextInt(16));
            String hex2 = Integer.toHexString(r.nextInt(16));
            willInput = willInput + hex1 + hex2 + ",";
         }
         currentlyWorking.smallerField(8,8);
         currentlyWorking.input(willInput);
         if(currentlyWorking.totalFitness() > currentBest.totalFitness()) {
            currentBest = currentlyWorking;
            System.out.println("Iteration: " + iterationCounter + ", Pattern:" + currentBest.originalInputString + " Fitness:" + currentBest.totalFitness());
         }
         iterationCounter++;
         System.gc();
      }
      System.out.println("Best: " + currentBest.originalInputString + " Fitness: " + currentBest.totalFitness());
      String savePattern = currentBest.originalInputString;
      currentBest.reset();
      currentBest.smallerField(8,8);
      currentBest.input(savePattern);
      currentBest.dumpUserFriendlyLife();
      for(int i = 0; i < 1000; i++) {
         currentBest.nextInteration();
      }
      currentBest.dumpUserFriendlyLife();
   }
   
   public class ConwaysGameOfLife {
      Boolean[][] coordinates;
      Boolean[][] originalInput;
      String originalInputString;
      boolean usingSmallerField = false;
      Integer currentRow;
      Integer currentColumn;
      double holdFitness = -1;
      Integer Length;
      Integer Width;
      
      public ConwaysGameOfLife(Integer length, Integer width) {
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
   }
}
/*
 ----jGRASP exec: java -ea bruteForce
 Running for 8.0 hours...
 Iteration: 0, Pattern:f0,5c,2b,3b,32,fb,a0,1e, Fitness:0.30303030303030304
 Iteration: 1, Pattern:0d,06,12,0c,05,96,b3,29, Fitness:0.3695652173913043
 Iteration: 10, Pattern:9e,53,62,0c,4f,0c,f7,8e, Fitness:0.5
 Iteration: 17, Pattern:fd,3a,0d,31,e2,24,4c,fd, Fitness:0.5151515151515151
 Iteration: 25, Pattern:e7,de,24,4c,22,58,eb,91, Fitness:0.6451612903225806
 Iteration: 46, Pattern:07,3a,63,01,84,c6,44,d0, Fitness:1.2391304347826086
 Iteration: 770, Pattern:68,6d,9e,0e,6c,b8,f3,72, Fitness:1.2794117647058822
 Iteration: 1538, Pattern:30,a3,35,80,b8,10,af,19, Fitness:1.28
 Iteration: 6357, Pattern:4f,80,35,d1,94,33,94,f8, Fitness:1.5344827586206897
 Iteration: 27274, Pattern:90,69,22,80,8f,91,4a,81, Fitness:1.6818181818181819
 Iteration: 53305, Pattern:45,a8,aa,40,46,92,a2,0c, Fitness:2.272727272727273
 Iteration: 649931, Pattern:29,e6,80,02,58,dc,58,61, Fitness:2.6041666666666665
 Best: 29,e6,80,02,58,dc,58,61, Fitness: 2.6041666666666665
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ..............*.*..*............
 ............***..**.............
 ............*...................
 ..............*.................
 .............*.**...............
 ............**.***..............
 .............*.**...............
 .............**....*............
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 ................................
 
 ................................
 ...........**...................
 ...........**...................
 .....*..........................
 ....****........................
 ...*.....*......................
 ...*.....**....*................
 ...*......*******...............
 ...*....**.***.*..**............
 ....********...**..*.*..........
 ......**..****..**....**........
 .....*..*..****..*.*.*..........
 ......*.........****............
 ...*.....*.......*..............
 ...*...**.......................
 ....****........................
 ..*..**.........................
 .....*..........................
 ..**.*..........................
 ....*........***................
 .............*..*...............
 ............*....*..............
 ...........**...................
 .**....***......**..............
 .**..**.**...*.*................
 ....*.**........................
 ....*...........................
 ....*.*.......**.**.............
 .....**........*.*..............
 ................*...............
 ................................
 ................................
 
 
  ----jGRASP: operation complete.
 */