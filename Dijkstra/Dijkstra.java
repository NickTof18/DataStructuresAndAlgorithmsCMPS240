//Nicholas Tofani

import java.util.*;

public class Dijkstra<T> { 
   public int nodeCount = 0;
   public Node<T>[] holdNodes;
   
   public Dijkstra(Integer numOfTotalNodes) {
      holdNodes = new Node[numOfTotalNodes];
   }
   
   public void newNode(T nodeName) {
       Node newNode = new Node<T>(nodeName);
       newNode.currentWeight = 0;
       holdNodes[nodeCount] = newNode;
       nodeCount++;
   }
   
   private void relax(Path currentPath) {
      Integer compareWeight = currentPath.getStart().getCurrentWeight() + currentPath.getWeight();
      if (compareWeight <  currentPath.getEnd().getCurrentWeight() || currentPath.getEnd().getCurrentWeight() == 0) {
         currentPath.getEnd().setCurrentWeight(compareWeight);
      }     
   }
   
   public void startAtNode(T startingNodeName) {
      Node<T> currentNode = holdNodes[findNodesSpotInArray(startingNodeName)];
      currentNode.setIsStartNode(true);
      dumpNodes();
      for(int setNodesIndex = 0; setNodesIndex < nodeCount; setNodesIndex++) {
         for(int i = 0; i < currentNode.getNumOfPaths(); i++) {
            relax(currentNode.getPaths()[i]);
         }
         currentNode.setSet(true);
         if(currentNode.numOfPaths > 0) {
            currentNode = currentNode.getPaths()[neighborWithLowestWeight(currentNode)].getEnd();
         }
         dumpNodes();
      }
   }
   
   public void dumpNodes() {
      for(int i = 0; i < nodeCount; i++) {
         String nodeWeight = Integer.toString(holdNodes[i].getCurrentWeight());
         if(holdNodes[i].getCurrentWeight() == 0 && !holdNodes[i].getIsStartNode()) {
            nodeWeight = "?";
         }
         System.out.println("ID:" + holdNodes[i].getName() + ",  Weight:" + nodeWeight + ",  Used:" + holdNodes[i].getSet());
      }
      System.out.println();
   }
    
   public Integer findNodesSpotInArray(T nodeName) {
      boolean found = false;
      Integer position = 0;
      while(!found && position != holdNodes.length) {
         if(holdNodes[position].getName().equals(nodeName)) {
            found = true;
         }
         else {
            position++;
         }
      }
      return position;
   }
   
   public void addPath(T start, T end, Integer weight) {
      Node newStart = holdNodes[findNodesSpotInArray(start)];
      Node newEnd = holdNodes[findNodesSpotInArray(end)];
      if(newStart.getPaths() == null) {
         newStart.setPaths(new Path[3]);
      }
      Path newPath = new Path(newStart, newEnd, weight);
      newStart.getPaths()[newStart.getNumOfPaths()] = newPath;
      newStart.setNumOfPaths(newStart.getNumOfPaths() + 1);
   }
   
   public Integer neighborWithLowestWeight(Node currentNode) {
      int lowestPosition = 0;
      for (int i = 0; i < currentNode.getNumOfPaths(); i++) {
         if(currentNode.getPaths()[i].getEnd().getCurrentWeight() <= currentNode.getPaths()[lowestPosition].getEnd().getCurrentWeight()) {
             lowestPosition = i;
         }
      }
      return lowestPosition;
   }
   
   public class Node<T> {
      private T name;
      private Boolean set;
      private Path[] paths;
      private Integer numOfPaths;
      private Integer currentWeight;
      private boolean isStartNode;
      
      public Node(T name) {
         this.name = name;
         this.set = false;
         this.numOfPaths = 0;
         this.isStartNode = false;
      }
      
      public Node() {
         this.set = false;
         this.numOfPaths = 0;
         this.isStartNode = false;
      }
      
      public T getName() {
         return name;
      }
      
      public Boolean getSet() {
         return set;
      }
      
      public Path[] getPaths() {
         return paths;
      }
      
      public int getNumOfPaths() {
         return numOfPaths;
      } 
      
      public int getCurrentWeight() {
         return currentWeight;
      }
      
      public Boolean getIsStartNode() {
         return isStartNode;
      }
      
      public void setName(T name) {
         this.name = name;
      }
      
      public void setSet(Boolean set) {
         this.set = set;
      }
      
      public void setPaths(Path[] paths) {
         this.paths = paths;
      }
      
      public void setNumOfPaths(int numOfPaths) {
         this.numOfPaths =  numOfPaths;
      } 
      
      public void setCurrentWeight(int currentWeight) {
         this.currentWeight = currentWeight;
      }
      
      public void setIsStartNode(Boolean isStartNode) {
         this.isStartNode = isStartNode;
      }
   }
   
   public class Path<T> {
      private Integer weight;
      private Node<T> start;
      private Node<T> end;
      
      public Path(Node<T> start, Node<T> end, Integer weight) {
         this.weight = weight;
         this.start = start;
         this.end = end;
      }
      
      public int getWeight() {
         return weight;
      }
      
      public Node<T> getStart() {
         return start;
      }
      
      public Node<T> getEnd() {
         return end;
      }
   }
   
   public static void main(String[] args) {
      Dijkstra<String> dijkstra = new Dijkstra<String>(7);
      dijkstra.newNode("1");
      dijkstra.newNode("2");
      dijkstra.newNode("3");
      dijkstra.newNode("4");
      dijkstra.newNode("5");
      dijkstra.newNode("6");
      dijkstra.addPath("1","3", 3);
      dijkstra.addPath("2","1", 4);
      dijkstra.addPath("2","6", 2);
      dijkstra.addPath("3","5", 3);
      dijkstra.addPath("4","1", 6);
      dijkstra.addPath("4","2", 1);
      dijkstra.addPath("4","5", 10);
      dijkstra.addPath("6","1", 1);
      dijkstra.startAtNode("4");
   }
   
}