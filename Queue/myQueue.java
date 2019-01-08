//Nicholas Tofani
//9-22-2016
//CS-240
import java.util.*;

public class myQueue <T> { 
   private Node<T> head;
   private int size;
   private Node<T> tail;
   private Node<T> mover;
   
   public myQueue() {
      head = null;
      tail = null;  
      mover = null;
      size = 0;
   }
   
   class Node<T> {
      private T value;
      private Node<T> next;
      
      public void setValue(T value) {
         this.value = value;
      }
      
      public void setNext(Node<T> next) {
         this.next = next;
      }
      
      public T getValue() {
         return value;
      }
      
      public Node<T> getNext() {
         return next;
      }
   }
   
   public void dumpQueue() {
      if(size == 0) {
         System.out.println("There isn't anything to display, the Queue is empty!");
      }
      else {
         mover = head;
         for(int i = 0; i != size; i++) {
            if (mover != null) {
               System.out.print(mover.getValue() + " ");
            }
            mover = mover.getNext();
         }
         System.out.println("");
         mover = head;
      }
   }
   
   public void enqueue(T adding) {
      mover = tail;
      tail = new Node<T>();
      tail.setValue(adding);
      tail.setNext(null);
      if(size == 0) {
         head = tail;
      }
      else {
         mover.setNext(tail);
      }
      size++; 
   }  
   
   public T dequeue() {
      if(size == 0) {
         System.out.println("There is nothing left to dequeue");
         return null;
      }
      mover = head;
      while(mover.getValue() == null) {
         mover = mover.getNext();;      
      }
      T result = mover.getValue();
      mover.setValue(null);
      mover = mover.getNext();
      head = mover;
      size--;
      return result;
   }
   
   public T peek() {
      return head.getValue();
   }
   
   public int getSize() {
      return size;
   }
   
   public static void main(String[] args) {
      myQueue<Integer> q = new myQueue<>();
      q.enqueue(2); 
      q.enqueue(1); 
      q.dumpQueue(); 
      q.enqueue(3);
      q.enqueue(10); 
      q.dequeue(); 
      q.enqueue(15); 
      q.dumpQueue(); 
      q.dequeue(); 
      q.dequeue(); 
      q.dequeue();
      q.dumpQueue(); 
      q.dequeue();
      q.dequeue();
   }
  
}
