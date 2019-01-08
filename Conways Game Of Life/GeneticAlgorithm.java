//Nicholas Tofani
//11-7-2016
//CS-240

import java.util.*;
import java.util.Random;

public class GeneticAlgorithm {
   ConwaysGameOfLife[] population;
   Random r;
   Integer iterationCounter = 0;
    
   public static void main(String[] args) {
      GeneticAlgorithm G = new GeneticAlgorithm(50);
      G.runGA(0.1);
   }
  
   public GeneticAlgorithm(Integer numPopulation) {
      r  = new Random(System.currentTimeMillis());
      population = new ConwaysGameOfLife[numPopulation];
      for(int i = 0; i < numPopulation; i++) {
         population[i] = new ConwaysGameOfLife(32,32);
         population[i].smallerField(8,8);
         String willInput = "";
         for(int j = 0; j < 8; j++) {
            String hex1 = Integer.toHexString(r.nextInt(16));
            String hex2 = Integer.toHexString(r.nextInt(16));
            willInput = willInput + hex1 + hex2 + ",";
         }
         population[i].input(willInput);
      } 
   }
   
   public void runGA(double hours) {
      System.out.println("Running for " + hours + " hours...");
      long startTime = System.currentTimeMillis(); 
      ConwaysGameOfLife currentBest = population[0];
      while((System.currentTimeMillis() - startTime) < ((hours) * 3600000)) {
         ConwaysGameOfLife[] tmpArray = new ConwaysGameOfLife[population.length];
         int holdSpot = 0;
         for(int i = 0; i < population.length; i++) {
            if(i < (population.length * .25)) {
               tmpArray[i] = crossOver(population[i], population[i + 1]);
            }
            else if (i > (population.length * .25) && i < (population.length * .75)) {
               tmpArray[i] = mutation(population[i]);
            }
            else {
               tmpArray[i] = population[holdSpot];
               holdSpot++;
            }
         }
         population = tmpArray;
         
         Arrays.sort(population, new Comparator<ConwaysGameOfLife>() {
            @Override
            public int compare(ConwaysGameOfLife a, ConwaysGameOfLife b) {
               if(b.totalFitness() - a.totalFitness() > 0.0) {
                  return 1;
               }
               else if(b.totalFitness() - a.totalFitness() < 0.0) {
                  return -1;
               }
               else {
                  return 0;
               }
            }
         });
         
         if(population[0].totalFitness() > currentBest.totalFitness()) {
            currentBest = population[0];
            System.out.println("Iteration: " + iterationCounter + ", Pattern:" + population[0].originalInputString + " Fitness:" + population[0].totalFitness());
         }
         iterationCounter++;
         System.gc();
      }
      System.out.println("Best: " + population[0].originalInputString + " Fitness: " + population[0].totalFitness());
      String savePattern = population[0].originalInputString;
      population[0].reset();
      population[0].smallerField(8,8);
      population[0].input(savePattern);
      population[0].dumpUserFriendlyLife();
      for(int i = 0; i < 1000; i++) {
         population[0].nextInteration();
      }
      population[0].dumpUserFriendlyLife();
   }
   
   public ConwaysGameOfLife crossOver(ConwaysGameOfLife mostFit, ConwaysGameOfLife secondMostFit) {
      String mostFitPieces[] = mostFit.originalInputString.split(",");
      String secondMostFitPieces[] = secondMostFit.originalInputString.split(",");
      String crossString = "";
      ConwaysGameOfLife crossOvered = new ConwaysGameOfLife(32,32);
      for(int i = 0; i < mostFitPieces.length; i++) {
         if(r.nextInt(2) == 1) {
            crossString = crossString + mostFitPieces[i] + ",";
         }
         else {
            crossString = crossString + secondMostFitPieces[i] + ",";
         }
      }
      crossOvered.smallerField(8,8);
      crossOvered.input(crossString);
      return crossOvered;
   }
   
   public ConwaysGameOfLife mutation(ConwaysGameOfLife beingMutated) {
      Integer numOfChanges = r.nextInt(3);
      String beingMutatedPieces[] = beingMutated.originalInputString.split(",");
      String mutatedString = "";
      ConwaysGameOfLife mutated = new ConwaysGameOfLife(32,32);
      for (int i = -1; i != numOfChanges; i++) {
         Integer positionChanging = r.nextInt(8);
         String replacement1 = Integer.toHexString(r.nextInt(16));
         String replacement2 = Integer.toHexString(r.nextInt(16));
         beingMutatedPieces[positionChanging] = replacement1 + replacement2;
      }
      for(int j = 0; j != beingMutatedPieces.length; j++) {
         mutatedString = mutatedString + beingMutatedPieces[j] + ",";
      } 
      mutated.smallerField(8,8);
      mutated.input(mutatedString);
      return mutated;   
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
  ----jGRASP exec: java -ea GeneticAlgorithm
 Running for 8 hours...
 Iteration: 0, Pattern:99,88,48,0d,18,7c,84,19, Fitness:0.7391304347826086
 Iteration: 4, Pattern:ce,2a,6a,8b,bf,8d,35,a6, Fitness:1.2285714285714286
 Iteration: 23, Pattern:00,36,20,1b,26,8d,67,b7, Fitness:1.2777777777777777
 Iteration: 31, Pattern:1a,36,15,1b,26,8d,84,b7, Fitness:1.4482758620689655
 Iteration: 48, Pattern:47,98,15,1b,86,8d,03,b7, Fitness:1.4655172413793103
 Iteration: 80, Pattern:47,18,15,1b,86,8d,03,b7, Fitness:1.5178571428571428
 Iteration: 124, Pattern:00,06,15,32,86,8d,03,b7, Fitness:1.6956521739130435
 Iteration: 269, Pattern:00,06,15,32,c8,8d,03,21, Fitness:1.7894736842105263
 Iteration: 298, Pattern:00,06,15,1a,c8,14,03,21, Fitness:2.2058823529411766
 Iteration: 404, Pattern:00,06,15,1a,c8,14,03,02, Fitness:2.34375
 Iteration: 1035, Pattern:00,06,15,1a,84,14,0b,02, Fitness:2.46875
 Iteration: 2496, Pattern:00,98,c6,1a,84,14,8c,02, Fitness:2.6944444444444446
 Iteration: 2527, Pattern:00,98,c6,04,84,14,9a,02, Fitness:2.7058823529411766
 Iteration: 2714, Pattern:00,98,c6,04,84,14,1a,02, Fitness:2.875
 Iteration: 6232, Pattern:00,98,c6,04,84,1d,1a,b3, Fitness:3.4318181818181817
 Iteration: 6348, Pattern:00,98,c6,04,84,1d,1a,33, Fitness:3.5952380952380953
 Best: 00,98,c6,04,84,1d,1a,33, Fitness: 3.5952380952380953
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
    ............*..**...............
    ............**...**.............
    .............*..................
    ............*....*..............
    ...............***.*............
    ...............**.*.............
    ..............**..**............
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
    ..........................**....
    ..........................**....
    ...*............................
    ...*............................
    ...*......**.............***....
    ..........*.*....*......**.*....
    ......*..*..*....*.......**.*...
    .....*...***.....*.....**.......
    ....**..*..*.........**.....***.
    .....**.**...........**.**..***.
    ......****....*......**.....***.
    .............***.......**.......
    ............**.**........**.*...
    ...........***.***......**.*....
    ............**.**......*****....
    .............***......**........
    .**...........*......*..*.......
    .**...................**........
    ................................
    .**.............................
    .**.............................
    .................*..............
    .................*..............
    ...............*.**.............
    ..............**...**...........
    ............*..*..*..*..........
    ............*...***.***....**...
    .....**.........**...*.....**...
    ..*...*.......*.....*...........
    ..****........**..*.*...........
    ................................
 
 
  ----jGRASP: operation complete.
 
 */