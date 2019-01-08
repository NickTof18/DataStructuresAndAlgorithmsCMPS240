//Nicholas Tofani
//9-16-2016
//CS-240
class MyLinkedList { 
   Node head;  
    
   class Node { 
      private int value; 
      private Node next;
       
      public Node(int value)  { 
         this.value = value;  
         this.next = null; 
      }
      
      public Node(int value, Node next) {
         this.value = value;
         this.next = next;
      }
      
      public void setValue(int value) {
         this.value = value;
      }
      
      public void setNext(Node next) {
         this.next = next;
      }
      
      public int getValue() {
         return value;
      }
      
      public Node getNext() {
         return next;
      }    
   }
   
   public Node getHead() { 
      return head;
   }
   
   public Node getTail() {
      Node last = head;
      while(last.getNext() != null) {
         last = last.getNext();
      }
      return last;
   }
   
   public void addNode(int newNodeValue) {
      Node newNode = new Node(newNodeValue);
      addNode(newNode);
   }
   
   public void addNode(Node newNode) {
      if(head == null) {
         head = newNode;
      }
      else {
         Node lastNode = getTail();
         lastNode.setNext(newNode);
      }
   }
   
   public int findValue(int value) {
      Node currentNode = head;
      int result = 0;
      while(currentNode.getValue() != value) {
         currentNode = currentNode.getNext();
         if(currentNode == null) {
            return -1;
         }
         result++;
      }
      return result;
   }
   
   public boolean removeValue(int value) {
      int indexOfValue = findValue(value);
      Node currentNode = head;
      if(indexOfValue > 0) {
         for(int i = 0; i < indexOfValue - 1; i++) {
            currentNode = currentNode.getNext();
         }
         if(currentNode.getNext() != null) {
            currentNode.setNext(currentNode.getNext().getNext());
         }
         else {
            currentNode.setNext(null);
         }
         System.out.println(value + " was removed from the linked list");
         return true;
      }
      else if(indexOfValue == 0) {
         head = head.getNext();
         System.out.println(value + " was removed from the linked list");
         return true;
      }

      System.out.println("The Value " + value + " does not Exist in the Linked List");
      return false;
   } 
   
   public void printLinkedList() {
      if(head != null) {
         Node currentNode = head;
         while(currentNode != null) {
             System.out.print(currentNode.getValue() + " --> ");
             currentNode = currentNode.getNext();
         }
         System.out.println("NULL");
      }
      else {
         System.out.println("Linked List is empty");
      }
   }

   public static void main(String[] args) { 
      MyLinkedList linkedList = new MyLinkedList();
      for(int i = 0; i < 11; i++) {
         linkedList.addNode(i);
      }
      linkedList.removeValue(10);
      linkedList.printLinkedList();
      
      for(int i = 15; i < 33; i++) {
         linkedList.addNode(i);
      }
      linkedList.printLinkedList();
      linkedList.removeValue(22);
      linkedList.printLinkedList();
      
      linkedList.removeValue(-1);
      linkedList.printLinkedList();
      
      linkedList.removeValue(0);
      linkedList.printLinkedList();
      
      linkedList.removeValue(32);
      linkedList.printLinkedList();
      
      linkedList.removeValue(32);
      linkedList.printLinkedList();
   } 
} 