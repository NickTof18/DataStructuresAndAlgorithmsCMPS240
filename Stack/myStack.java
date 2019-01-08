//Nicholas Tofani
//9-2-2016
//CS-240

import java.util.*;
import java.util.NoSuchElementException;

public class myStack <T> { 
   private Integer[] stack;
   private int size;
    
   public myStack(Integer stackSize) {
      if(stackSize <= 0) {
         throw new NoSuchElementException("Stack Size must be a positive number greater then 0");
      }
      else {
         stack = new Integer[stackSize];
         size = 0;
      }
   }
   
   public void dumpStack() {
      if(size == 0) {
         System.out.println("The stack has nothing in it to display");
      }
      else {
         String willPrint = "";
         for(int i = size -1; i >= 0 ; i--) {
            willPrint = (stack[i]) + " " + willPrint;
         }       
         System.out.println(willPrint);
      }
   }
   
   public void push(Integer adding) {
      if((size +1) >= stack.length) {
         System.out.println("There is no room left in the Stack");
      }
      else {
         stack[size] = adding;
         size++; 
      }
   }
   
   public void forcePush(Integer adding) {
      if((size + 1) >= stack.length) {
         System.out.println("There is no room left in the Stack, the Array size will be Doubled");
         Integer[] newStack = new Integer[size * 2];
         for(int i = 0; i < size; i++) {
            newStack[i] = stack[i];
         }
         if(size <= 0) {
            newStack = new Integer[1];
         }
         newStack[size] = adding;
         stack = newStack;
         size++;
      }
      else {
         push(adding);
      }
   }
   
   public void trimStack() {
      Integer[] newStack = new Integer[size];
      for(int i = 0; i < size; i++) {
         newStack[i] = stack[i];
      }
      stack = newStack;
   }
     
   public void pop() {
      if(size == 0) {
         System.out.println("There is nothing left to pop");
      }
      else {
         stack[size-1] = null;
         size--;
     }
   }
   
   public int getSize() {
      return size;
   }
   
   public static void main(String[] args) {
      myStack<Integer> stack = new myStack<Integer>(10);
      stack.push(2); 
      stack.push(1); 
      stack.dumpStack(); 
      stack.push(3); 
      stack.push(10); 
      stack.pop();
      stack.push(15); 
      stack.dumpStack(); 
      stack.pop(); 
      stack.pop(); 
      stack.dumpStack(); 
      stack.pop(); 
      stack.pop(); 
      stack.pop(); 
      stack.dumpStack();
      stack.trimStack(); 
      
      for(int i = 0; i < 10; i++) {
         stack.push(i);   
      }
      stack.dumpStack(); 
      stack.forcePush(10);
      stack.dumpStack(); 
      stack.trimStack();
      stack.dumpStack(); 
      stack.push(15);
      stack.dumpStack(); 
   }
   
}