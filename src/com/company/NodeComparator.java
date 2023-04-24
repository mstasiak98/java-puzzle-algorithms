package com.company;

import java.util.Comparator;

public class NodeComparator implements Comparator<PuzzleNode> {

    @Override
    public int compare(PuzzleNode o1, PuzzleNode o2) {
        if(o1.getTotalCost() < o2.getTotalCost()){
            return -1;
        }
        if(o1.getTotalCost() > o2.getTotalCost()){
            return 1;
        }
        return 0;
    }
}
