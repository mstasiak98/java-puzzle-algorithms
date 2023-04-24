package com.company;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int[][] state;
        Scanner scanner = new Scanner(System.in);

        boolean _continue = true;

        while (_continue == true) {
            System.out.println("Wpisz nazwę pliku z którego chcesz wczytać");
            String fileName = scanner.nextLine();
            try {
                Scanner input = new Scanner(new File(fileName));
                String inputState = input.nextLine();
                state = stringToMatrix(inputState);
            } catch (Exception e) {
                System.out.println("Wystapil blad wczytywania pliku. Podaj nazwe pliku jeszcze raz " );
                continue;
            }

            System.out.println("\nWybierz operacje: \n" +
                    "1: Algorytm BFS\n2:Algorytm A*(misplaced elements)\n3:Symulowane wyzarzanie\n0: Wyjdz");
            int result = scanner.nextInt();
            switch (result){
                case 1: {
                    PuzzleTree puzzleTree = new PuzzleTree(new PuzzleNode(state));

                    puzzleTree.BFS();

                    _continue = false;
                    break;
                }

                case 2: {
                    PuzzleTree puzzleTree = new PuzzleTree(new PuzzleNode(state));

                    puzzleTree.aStar();

                    _continue = false;
                    break;
                }

                case 3: {
                    PuzzleTree puzzleTree = new PuzzleTree(new PuzzleNode(state));

                    puzzleTree.simulatedAnnealing(10, 0.0001);


                    _continue = false;
                    break;
                }

                case 0:{
                    _continue = false;
                    break;
                }
            }
        }

    }

    public static int[][] stringToMatrix(String inputString){
        int [][] matrix = new int[3][3];

        int k =0;
        for (int i =0;i<3;i++){
            for (int j =0;j<3;j++){
                matrix[i][j] = Character.getNumericValue(inputString.charAt(k));
                k++;
            }
        }
        return matrix;
    }
}

