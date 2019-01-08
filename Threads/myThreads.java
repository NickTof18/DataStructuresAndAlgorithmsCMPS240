//Nicholas Tofani
//11-17-2016
//CS-240

import java.util.*;

public class myThreads {
   public int counter;
   private extThreads[] threadArray;
   private int topTenHolder[];
   
   public myThreads(int numOfThreads, int starting, int ending) {
      counter = 0;
      topTenHolder = new int[10];
      if(numOfThreads == 1) {
         System.out.println("Serial Run: ");
      }
      else {
         System.out.println("Parallel Run: ");
      }
      threadArray = new extThreads[numOfThreads];
      for(int i = 0; i < numOfThreads; i++) {
         threadArray[i] = new extThreads();
         threadArray[i].startingPoint = starting + (int)(((double)i/(double)numOfThreads)*(double)(starting));
         threadArray[i].endingPoint = threadArray[i].startingPoint + (int)((1/(double)numOfThreads) * (double)(starting));
         threadArray[i].linkingGlobalCounter(this);
      }
      for(int j = 0; j != numOfThreads; j++) {
         threadArray[j].start();
      }
   }
   
   synchronized void addToCounter(int currentNum) {  
      if (counter < 10) {
         topTenHolder[counter] = currentNum;
      }
      counter++;
   }
      
   public void dumpOutput() throws InterruptedException {
      for(int j = 0; j != threadArray.length; j++) {
         threadArray[j].join();
      }
      System.out.println("  Prime Count: " + counter);
      System.out.println("   First 10 Primes found by Thread(s): ");
      System.out.print("    ");
      for(int i = 0; i < topTenHolder.length; i++) {
         System.out.print(topTenHolder[i] + ", ");
      }
   }
   
   class extThreads extends Thread {
      public myThreads MT;
      private int startingPoint;
      private int endingPoint;
      
      public void linkingGlobalCounter(myThreads linkingCount) {
         MT = linkingCount;
      }
   
      @Override
      public void run() {
         for(int i = startingPoint; i != endingPoint; i++) {
            if(isPrime(i)) {
               MT.addToCounter(i);
            }
         }    
      }
      
      public boolean isPrime(int num) {
         if(num % 2 == 0) {
            return false;
         }
         for(int i = 3; i * i <= num; i += 2) {
            if(num % i == 0) {
               return false;
            }
         }
         return true;
      }
   
   } 
   
   public static void main(String[] args) throws InterruptedException {
      myThreads serialRun = new myThreads(1, 1000000, 2000000);
      serialRun.dumpOutput();
      serialRun.counter = 0;
      serialRun.topTenHolder = null;
      serialRun = null;
      System.out.println();
      myThreads parallelRun = new myThreads(4, 1000000, 2000000);
      parallelRun.dumpOutput();
   }
   
}

/* 
   Output:
    ----jGRASP exec: java -ea myThreads
 Serial Run: 
   Prime Count: 70435
    First 10 Primes found by Thread(s): 
     1000003, 1000033, 1000037, 1000039, 1000081, 1000099, 1000117, 1000121, 1000133, 1000151, 
 Parallel Run: 
   Prime Count: 70435
    First 10 Primes found by Thread(s): 
     1000003, 1000033, 1000037, 1000039, 1000081, 1250003, 1000099, 1250009, 1000117, 1500007, 
  ----jGRASP: operation complete.
*/