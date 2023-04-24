package com.company;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private List<PuzzleNode> path;

    public Path(PuzzleNode initialNode, PuzzleNode goalNode) {
        this.path = getPath(initialNode, goalNode );
    }

    private List<PuzzleNode> getPath(PuzzleNode initialNode, PuzzleNode goalNode){
        PuzzleNode tmpNode = goalNode;
        List<PuzzleNode> list = new ArrayList<>();

        while (!(tmpNode.equals(initialNode))){
            list.add(tmpNode);
            tmpNode = tmpNode.getParent();
        }
        list.add(initialNode);
        return list;
    }

    public void printPath(int visitedNodes){
        int size = path.size();
        for (int i = size-1; i>=0; i--){
            if(i != size -1 )
                System.out.println("\nMoved: "+ path.get(i).getDirection());

            System.out.println("Node:");
            System.out.println(path.get(i).getStringState().substring(0,3));
            System.out.println(path.get(i).getStringState().substring(3,6));
            System.out.println(path.get(i).getStringState().substring(6,9));

        }
        System.out.println();
        System.out.println("Numbers of transition: " + (size-1));
        System.out.println("Time complexity: "+visitedNodes);
        System.out.println("Space complexity: "+visitedNodes);
    }

}
