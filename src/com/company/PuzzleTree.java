package com.company;

import java.util.*;

public class PuzzleTree {

    PuzzleNode root;
    private String goalState = "123456780";
    int nodesGenerated;


    public PuzzleTree(PuzzleNode root) {
        this.root = root;
        nodesGenerated = 0;
    }

    public PuzzleNode getRoot() {
        return root;
    }

    public void setRoot(PuzzleNode root) {
        this.root = root;
    }

    public void BFS(){
        Set<PuzzleNode> visited = new HashSet<>();
        PuzzleNode node = new PuzzleNode(root.getState());
        Queue<PuzzleNode> queue = new LinkedList<>();
        PuzzleNode currentNode = node;

        while (!currentNode.isGoal()) {
            visited.add(currentNode);
            List<PuzzleNode> nodeSuccessors = currentNode.getSuccessors();
            for (PuzzleNode n : nodeSuccessors) {
                if (visited.contains(n))
                    continue;
                visited.add(n);
                queue.add(n);

            }
            currentNode = queue.remove();
        }


        Path p = new Path(root,currentNode);
        p.printPath(visited.size());

    }

    public void aStar(){
        Set<PuzzleNode> visited = new HashSet<>();
        PuzzleNode node = new PuzzleNode(root.getState());
        node.setTotalCost(0);
        NodeComparator nodeComparator = new NodeComparator();
        PriorityQueue<PuzzleNode> nodePriorityQueue = new PriorityQueue<>(1, nodeComparator);
        PuzzleNode currentNode = node;

        while (!currentNode.isGoal()) {
            visited.add(currentNode);
            List<PuzzleNode> nodeSuccessors = currentNode.getSuccessors();
            for (PuzzleNode n : nodeSuccessors) {
                if (visited.contains(n))
                    continue;
                visited.add(n);
                n.setTotalCost(n.getTotalCost() + n.getParent().getTotalCost(), h1(n));
                nodePriorityQueue.add(n);

            }
            currentNode = nodePriorityQueue.poll();
        }

        Path p = new Path(root,currentNode);
        p.printPath(visited.size());
    }

    public void simulatedAnnealing(double initialTemperature, double coolingRate){
        PuzzleNode currentNode = new PuzzleNode(root.getState());
        double temperature = initialTemperature;
        double coolRate = coolingRate;
        double probability;
        int delta;
        double random;
        PuzzleNode nextNode;
        int accepted = 0;
        ArrayList directions = new ArrayList();
        PuzzleNode startNode = new PuzzleNode();

        int [][]tabZestaw1 = new int[3][3];
        int [][]tabZestaw2 = new int[3][3];
        int [][]tabZestaw3 = new int[3][3];
        int [][]tabZestaw4 = new int[3][3];
        if(currentNode.getStringState().equals("123406758")){
            tabZestaw1[0][0] = 0;
            tabZestaw1[0][1] = 2;
            tabZestaw1[0][2] = 3;
            tabZestaw1[1][0] = 1;
            tabZestaw1[1][1] = 4;
            tabZestaw1[1][2] = 6;
            tabZestaw1[2][0] = 7;
            tabZestaw1[2][1] = 5;
            tabZestaw1[2][2] = 8;
            currentNode = new PuzzleNode(tabZestaw1);
            startNode = new PuzzleNode(tabZestaw1);
        }
        else if(currentNode.getStringState().equals("162403758")){
            tabZestaw2[0][0] = 6;
            tabZestaw2[0][1] = 0;
            tabZestaw2[0][2] = 2;
            tabZestaw2[1][0] = 1;
            tabZestaw2[1][1] = 4;
            tabZestaw2[1][2] = 3;
            tabZestaw2[2][0] = 7;
            tabZestaw2[2][1] = 5;
            tabZestaw2[2][2] = 8;
            currentNode = new PuzzleNode(tabZestaw2);
            startNode = new PuzzleNode(tabZestaw2);

        }
        else if(currentNode.getStringState().equals("623154708")){
            tabZestaw3[0][0] = 6;
            tabZestaw3[0][1] = 3;
            tabZestaw3[0][2] = 0;
            tabZestaw3[1][0] = 1;
            tabZestaw3[1][1] = 2;
            tabZestaw3[1][2] = 4;
            tabZestaw3[2][0] = 7;
            tabZestaw3[2][1] = 5;
            tabZestaw3[2][2] = 8;
            currentNode = new PuzzleNode(tabZestaw3);
            startNode = new PuzzleNode(tabZestaw3);
        }
        else if(currentNode.getStringState().equals("854730261")){
            tabZestaw4[0][0] = 8;
            tabZestaw4[0][1] = 5;
            tabZestaw4[0][2] = 4;
            tabZestaw4[1][0] = 0;
            tabZestaw4[1][1] = 7;
            tabZestaw4[1][2] = 1;
            tabZestaw4[2][0] = 2;
            tabZestaw4[2][1] = 3;
            tabZestaw4[2][2] = 6;

            currentNode = new PuzzleNode(tabZestaw4);
            startNode = new PuzzleNode(tabZestaw4);

        }






        while(temperature > 0 && (currentNode.getHeuristics() != 0)){
            for(int j = 0; j<10; j++){
                //select random neighbour from current node
                nextNode = currentNode.getRandomNode();
                if(nextNode.equals(currentNode.getParent())){
                   while(nextNode.equals(currentNode.getParent())){
                       nextNode = currentNode.getRandomNode();

                   }
                }

                nodesGenerated += 1;

                if (nextNode.getHeuristics() == 0){
                    directions.add(nextNode.getDirection());
                    System.out.println();
                    System.out.println("Directions: "+directions.toString());
                    System.out.println("Numbers of transition: " + directions.size());
                    System.out.println("Time complexity: "+nodesGenerated);
                    System.out.println("Space complexity: "+nodesGenerated);

                    System.out.println("Startowa Plansza:");
                    System.out.println(startNode.getStringState().substring(0,3));
                    System.out.println(startNode.getStringState().substring(3,6));
                    System.out.println(startNode.getStringState().substring(6,9));

                    System.out.println("Koncowa plansza:");
                    System.out.println(nextNode.getStringState().substring(0,3));
                    System.out.println(nextNode.getStringState().substring(3,6));
                    System.out.println(nextNode.getStringState().substring(6,9));
                    return;
                }

                delta = currentNode.getHeuristics() - nextNode.getHeuristics();

                if(delta > 0){
                    currentNode = nextNode;
                    directions.add(currentNode.getDirection());
                } else{
                    probability = Math.exp(delta/temperature);

                    //checking whether we want to chose next node or continue with currentNode
                    random = Math.random();

                    if(random <= probability){ // choose next
                        currentNode = nextNode;
                        directions.add(currentNode.getDirection());
                    }
                }
            }
            temperature = temperature - coolingRate;
        }

        if (currentNode.getHeuristics() == 0){
            System.out.println();

            System.out.println("Directions: "+directions.toString());
            System.out.println("Numbers of transition: " + directions.size());
            System.out.println("Time complexity: "+nodesGenerated);
            System.out.println("Space complexity: "+nodesGenerated);

            System.out.println("Startowa Plansza:");
            System.out.println(startNode.getStringState().substring(0,3));
            System.out.println(startNode.getStringState().substring(3,6));
            System.out.println(startNode.getStringState().substring(6,9));

            System.out.println("Koncowa plansza:");
            System.out.println(currentNode.getStringState().substring(0,3));
            System.out.println(currentNode.getStringState().substring(3,6));
            System.out.println(currentNode.getStringState().substring(6,9));
            return;
        }else{
            System.out.println("Nie udalo sie znalezc rozwiazania. temperatura spadla do 0");
        }

    }


    // h1 - heuristic one number of misplaced elements
    private int h1(PuzzleNode node){
        String currentNodeState = node.getStringState();
        int missplaced = 0;
        for (int i=0;i<currentNodeState.length();i++)
            if(currentNodeState.charAt(i) != goalState.charAt(i))
                missplaced += 1;
        return missplaced;
    }

}
