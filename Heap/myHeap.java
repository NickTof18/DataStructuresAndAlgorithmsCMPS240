//Nicholas Tofani
//9-30-2016
//CS-240

import java.util.*;

public class myHeap { 
   public int size;
   public Integer[] arrayRep;
    
   public myHeap() { 
      size = 0;
      arrayRep = new Integer[3];
   }
   
   public void insert(Integer element) {
      if(size + 2 > arrayRep.length) {
         doubleArraySize();
      }
      arrayRep[size + 1] = (Integer)element;
      size++;
      dumpHeap();
      heapSort();
   }
     
   public Integer extractMax() {
      Integer maxNum = -1;
      for(int i = 1; i != size; i++) {
         if(arrayRep[i] > maxNum) {
            maxNum = arrayRep[i];
         }
      }
      return maxNum;
   }
   
   private void doubleArraySize() {
      Integer[] tmpArray = new Integer[(arrayRep.length * 2)];
      for(int i = 0; i != arrayRep.length; i++) {
         tmpArray[i] = arrayRep[i];
      }
      arrayRep = tmpArray;
   }
   
   public void heapSort() {
      if(size > 3) {
         boolean wasSorted = false;
         for(int i = 1; i != arrayRep.length; i++) {
            if(getLeftChild(i) > 0 && arrayRep[i] < arrayRep[getLeftChild(i)]) {
               Integer tmp = arrayRep[i];
               int hold = getLeftChild(i);
               arrayRep[i] = arrayRep[hold];
               arrayRep[hold] = tmp;
               wasSorted = true;
            }
            if(getRightChild(i) > 0 && arrayRep[i] < arrayRep[getRightChild(i)]) {
               Integer tmp = arrayRep[i];
               int hold = getRightChild(i);
               arrayRep[i] = arrayRep[hold];
               arrayRep[hold] = tmp;
               wasSorted = true;
            }
         }
         if(wasSorted || extractMax() != arrayRep[1]) {
            dumpHeap();
            heapSort();
         }
      }
   }
   
   private int getLeftChild(Integer parent) {
      int parentIndex = 0;
      for(int i = 1; i != arrayRep.length; i++) {
         if(arrayRep[i] == arrayRep[parent]) {
            parentIndex = i;
         }
      }
      if ((parentIndex * 2) <= size) {
         return (parentIndex * 2);
      }
      else {
         return -1;
      }
   }
   
   private int getRightChild(Integer parent) {
      int parentIndex = 0;
      for(int i = 1; i != arrayRep.length; i++) {
         if(arrayRep[i] == arrayRep[parent]) {
            parentIndex = i;
         }
      }
      if ((parentIndex * 2 + 1) <= size) {
         return (parentIndex * 2 + 1);
      }
      else {
         return -1;
      }
   }
   
   public void dumpHeap() {
      if(size == 0) {
         System.out.println("There is nothing to print.");
         return;
      }
      printSpaces(arrayRep.length);
      //prints root
      System.out.println(arrayRep[1]);
      //each depth of leaves
      int i = 2; 
      while(i != arrayRep.length && arrayRep[i] != null) {
         printSpaces(arrayRep.length - (i + 2));
         String willPrint = "";
         //each set of leaves
         int j = i * 2;
         while(i < j && i < arrayRep.length) { 
            if(arrayRep[i] != null) {
               willPrint = willPrint + "  " + arrayRep[i];
            }
            i++;
         }
         System.out.println(willPrint);    
      }
      System.out.println();
   }
   
   private void printSpaces(int n) {
      if(n > 0) {
         System.out.print(" ");
         printSpaces(n-1);
      }
   }
   
   public class Node<T> {
      public T value;
      public Node<T> right;
      public Node<T> left;
   }
   
   public static void main(String[] args) {
      myHeap H = new myHeap();
      H.insert(10);
      H.insert(2);
      H.insert(8);
      H.insert(4);
      H.insert(18);
      H.insert(20);
      H.insert(3);
      H.insert(16);
      H.insert(5);
   }
   
}

/*
Output:
 ----jGRASP exec: java -ea myHeap
   10

   10
  2

      10
    2  8

      10
    2  8
  4

      10
    4  8
  2

      10
    4  8
  2  18

      10
    18  8
  2  4

      18
    10  8
  2  4

            18
          10  8
        2  4  20

            18
          10  20
        2  4  8

            20
          10  18
        2  4  8

            20
          10  18
        2  4  8  3

            20
          10  18
        2  4  8  3
    16

            20
          10  18
        16  4  8  3
    2

            20
          16  18
        10  4  8  3
    2

            20
          16  18
        10  4  8  3
    2  5


 ----jGRASP: operation complete.
*/