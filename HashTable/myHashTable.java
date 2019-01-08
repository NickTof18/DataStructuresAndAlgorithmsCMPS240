//Nicholas Tofani
//10-13-2016
//CS-240

import java.util.*;

public class myHashTable<K, V> {
   public myQueue<K, V>[] hashTable;
   public int buckets;
   public int size;
      
   public myHashTable() {
      hashTable = new myQueue[10];
      buckets = 10;
      size = 0;
   }
   
   public myHashTable(int amountOfBuckets) {
      hashTable = new myQueue[amountOfBuckets];
      buckets = amountOfBuckets;
      size = 0;
   }
   
   public boolean insert(K key, V value) {
      //Finding Correct bucket
      int hashedAdding = hash(key);
      //Initializing Correct bucket if necessary
      if (hashTable[hashedAdding] == null) {
         myQueue newQueue = new myQueue<K, V>();  
         hashTable[hashedAdding] = newQueue; 
      }
      
      //Checking if the key already exists 
      if(hashTable[hashedAdding].find(key)) {
         System.out.println("An Element already exists for the key, " + key);
         return false;
      }
           
      hashTable[hashedAdding].enqueue(key, value);
      size++;  
      return true;         
   } 
   
   public void delete(K key) {
      int hashedDelete = hash(key);
      if (hashTable[hashedDelete] != null) {
         if(hashTable[hashedDelete].remove(key)) {
            size--;
         }
      }
      else {
         System.out.println("Error! " + key + " is not in the hashTable.");
      } 
   }
   
   private int hash(K key) {
       int hashCode = key.hashCode(); 
       return hashCode % buckets;
   }
   
   public int getSize() {
      return size;
   }

   public void dumpHash() {
      if(size == 0) {
         System.out.println("The hashTable has nothing in it to display");
      }
      else {
         for(int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null && !hashTable[i].isEmpty()) {
               System.out.print("Bucket " + i + ": ");
               hashTable[i].dumpQueue();
            }
         } 
      }
   }
   
   public static void main(String[] args) {
      HashTable<Integer, Integer> H = new myHashTable<Integer, Integer>(10);
      H.insert(1,1);
      H.insert(5,5);
      H.insert(28,28);
      H.delete(5);
      H.insert(15,15);
      H.insert(8,8);
      H.dumpHash();
      H.delete(1);
      H.insert(18,18);
      H.insert(28,28);
      H.delete(33);
      H.dumpHash();
      H.delete(15);
      H.delete(28);
      H.delete(8);
      H.delete(18);
      H.delete(1);
      H.dumpHash();
       
      System.out.println();
      System.out.println("NEW TEST CASE");
      System.out.println("-----------------------------");
      System.out.println();
      
      myHashTable<String, Integer> d = new myHashTable<String, Integer>(10);
      d.insert("a",1);
      d.insert("b",5);
      d.insert("c",28);
      d.delete("a");
      d.insert("d",15);
      d.insert("e",8);
      d.dumpHash();
      d.delete("a");
      d.insert("j",18);
      d.insert("c",28);
      d.delete("33");
      d.dumpHash();
      d.delete("d");
      d.delete("c");
      d.delete("e");
      d.delete("j");
      d.delete("a");
      d.delete("b");
      d.dumpHash();
   }
 
 
}

class myQueue<K, V> { 
   private Node<K, V> head;
   private int size;
   private Node<K, V> tail;
   private Node<K, V> mover;
   
   public myQueue() {
      head = null;
      tail = null;  
      mover = null;
      size = 0;
   }
   
   class Node<K, V> {
      private V value;
      private K key;
      private Node<K, V> next;

      public Node() {
         this.value = null;
         this.key = null;
         this.next = null;
      }
      
      public Node(K key, V value, Node<K, V> next) {
         this.value = value;
         this.key = key;
         this.next = next;
      }
      
      public Node(K key, V value) {
         this.value = value;
         this.key = key;
         this.next = null;
      }
      
      public void setKey(K key) {
         this.key = key;
      }
      
      public K getKey() {
         return key;
      }
      
      public void setValue(V value) {
         this.value = value;
      }
      
      public void setNext(Node<K, V> next) {
         this.next = next;
      }
      
      public V getValue() {
         return value;
      }
      
      public Node<K, V> getNext() {
         return next;
      }
   }
   
   public void dumpQueue() {
      if(size != 0) {
         mover = head;
         for(int i = 0; i != size; i++) {
            if (mover != null) {
               System.out.print("Key: " + mover.getKey() + " Value: " + mover.getValue() + " || ");
            }
            mover = mover.getNext();
         }
         System.out.println("");
         mover = head;
      }
   }
   
   public void enqueue(K key, V value) {
      mover = tail;
      tail = new Node<K, V>(key, value);
      if(size == 0) {
         head = tail;
      }
      else {
         mover.setNext(tail);
      }
      size++; 
   }  
   
   public boolean remove(K key) {
      if (size > 0) {
         mover = head;
         for(int i = 0; i < size && mover.getKey() != key; i++) {
            nextVal();
         }
         if(mover.getKey() == key) {
            mover.value = null;
            nextVal();
            head = mover;
            size--;
            return true;
         }
      }
      System.out.println("There is no item with the Key, " + key);
      return false;
   }
   
   public Node<K, V> dequeue() {
      if(size == 0) {
         System.out.println("There is nothing left to dequeue");
         return null;
      }
      mover = head;
      while(mover == null) {
         mover = mover.getNext();;      
      }
      Node<K, V> result = mover;
      mover.setValue(null);
      mover = mover.getNext();
      head = mover;
      size--;
      return result;
   }
   
   public void nextVal() {
      if (mover != null) {
         mover = mover.getNext();
      }
   }
   
   public boolean find(K key) {
      mover = head;
      while(mover != null) {
         if(mover.getKey().equals(key)) {
            return true;
         }
         mover = mover.getNext();
      }
      return false;
   }
   
   public boolean isEmpty() {
      return size == 0;
   }
   
   public Node<K, V> peek() {
      return head;
   }
   
   public Node<K, V> peekLast() {
      return tail;
   }
   
   public Node<K, V> peekMover() {
      return mover;
   }
   
   public int getSize() {
      return size;
   }
   
   public void setMover(Node<K, V> index) {
      mover = index;
   }   
}