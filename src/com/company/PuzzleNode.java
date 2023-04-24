package com.company;

import java.util.*;


public class PuzzleNode {

    private int [][] state = new int[3][3];
    private PuzzleNode parent;
    private List<PuzzleNode> children;
    private int depth;
    private int blankRow;
    private int blankCol;
    private int totalCost;
    private DIRECTIONS direction;
    private String stringState;
    private String goalState = "123456780";
    private int heuristics;

    public PuzzleNode() {
        heuristics = h1();
        children = new ArrayList<>();
        parent = null;
        direction = null;
        this.depth = 0;
    }

    public PuzzleNode(int[][] state) {
        this.state = state;
        this.depth = 0;
        parent = null;
        direction = null;
        children = new ArrayList<>();
        stringState = getStringState();
        for (int i=0;i<=2;i++){
            for (int j=0;j<=2;j++){
                if(state[i][j] == 0){
                    blankRow = i;
                    blankCol = j;
                    break;
                }
            }
        }
        heuristics = h1();

    }

    public String getStringState(){
        String stringState="";
        for (int i =0; i<state.length; i++) {
            for(int j = 0; j<state[i].length;j++ ) {
                stringState+=state[i][j];
            }
        }
        return stringState;
    }

    public boolean isGoal(){
        boolean result = true;
        int [][] goalState = {{1,2,3},{4,5,6},{7,8,0}};
        for(int i = 0; i<=state.length-1;i++){
            for (int j =0; j<= state[i].length-1; j++){
                if(state[i][j] != goalState[i][j]){
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public PuzzleNode getRandomNode(){
        List<PuzzleNode> neighbours = this.getSuccessors();
        Random random = new Random();

        PuzzleNode randomNeighbour = neighbours.get(random.nextInt(neighbours.size()));
        randomNeighbour.setHeuristics(randomNeighbour.h1());
        return  randomNeighbour;
    }

    public List<PuzzleNode> getSuccessors(){
        List<PuzzleNode> list = new ArrayList<>();

        //move left
        if((blankCol != 0)){
            PuzzleNode leftNode = this.createChild(blankRow, blankCol-1);
            leftNode.setDirection(DIRECTIONS.LEFT);
            list.add(leftNode);
        }

        //move right
        if((blankCol != 2)){
            PuzzleNode upNode = this.createChild(blankRow, blankCol+1);
            upNode.setDirection(DIRECTIONS.RIGHT);
            list.add(upNode);
        }

        //move up
        if((blankRow != 0)){
            PuzzleNode upNode = this.createChild(blankRow-1, blankCol);
            upNode.setDirection(DIRECTIONS.UP);
            list.add(upNode);
        }

        //move down
        if((blankRow != 2)){
            PuzzleNode downNode = this.createChild(blankRow+1, blankCol);
            downNode.setDirection(DIRECTIONS.DOWN);
            list.add(downNode);
        }
        return list;
    }

    private PuzzleNode createChild(int a, int b){
        int [][] tmp = new int [state.length][state.length];
        for (int i=0;i<state.length;i++){
            for (int j =0;j<state[i].length;j++){
                tmp[i][j] = state[i][j];
            }
        }
        tmp[blankRow][blankCol] = tmp[a][b];
        tmp[a][b] = 0;
        PuzzleNode child = new PuzzleNode(tmp);
        addChild(child);
        return child;
    }

    private void addChild(PuzzleNode child){
        this.children.add(child);
        child.setParent(this);
    }


    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void setTotalCost(int cost, int estimatedCost) {
        this.totalCost = cost + estimatedCost;
    }

    public int getHeuristics() {
        return heuristics;
    }

    public void setHeuristics(int heuristics) {
        this.heuristics = heuristics;
    }

    public int[][] getState() {
        return state;
    }

    public PuzzleNode getParent() {
        return parent;
    }

    public void setParent(PuzzleNode parent) {
        this.parent = parent;
    }

    public DIRECTIONS getDirection() {
        return direction;
    }

    public void setDirection(DIRECTIONS direction) {
        this.direction = direction;
    }


    @Override
    public boolean equals(Object object) {
        if(!(object instanceof PuzzleNode)) {
            return false;
        }
        PuzzleNode node = (PuzzleNode) object;

        return node.getStringState().equals(this.getStringState());
    }

    @Override
    public int hashCode() {
        int result = 20;
        result = 31 * result +this.stringState.hashCode();
        return result;
    }

    // h1 - heuristic one number of misplaced elements
    public int h1(){
        String currentNodeState = this.getStringState();
        int missplaced = 0;
        for (int i=0;i<currentNodeState.length();i++)
            if(currentNodeState.charAt(i) != goalState.charAt(i))
                missplaced += 1;
        return missplaced;
    }
}
