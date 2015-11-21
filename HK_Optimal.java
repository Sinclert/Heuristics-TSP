/* Author: Sinclert Perez (UC3M) */

import java.io.*;
import java.util.Scanner;

/** The Held Karp algorithm:
 *
 * There are 2 possible cases in each iteration:
 *
 * A) A base case where we already know the answer. (Stopping condition)
 * B) Decreasing the number of considered vertices and calling our algorithm again. (Recursion)
 *
 * Explanation of every case:
 *
 * A) If the list of vertices is empty, return the distance between starting point and vertex.
 * B) If the list of vertices is not empty, lets decrease our problem space:
 *
 *      1) Consider each vertex in vertices as a starting point ("initial")
 *      2) Since we are considering "initial" as a starting point, we have to adjust the list of vertices, by removing "initial"
 *      3) Calculate the cost of visiting "initial" (costCurrentNode) + cost of visiting rest of vertices starting from there ("costChildren")
 *      4) Return the minimum result from step 3
 */

public class HK_Optimal {

    /* ----------------------------- GLOBAL VARIABLES ------------------------------ */
    public static int[][] distances;
    public static int optimalDistance = 0;
    public static String optimalPath = "";


    /* ------------------------------ MAIN FUNCTION -------------------------------- */

    public static void main(String args[]) throws IOException{


        /* ----------------------------- IO MANAGEMENT ----------------------------- */

        // The path to the files with the distances is asked
        Scanner input = new Scanner(System.in);
        System.out.println("Please, introduce the path where the text file is stored");
        String file = input.nextLine();

        // The size of the distance matrix is asked
        System.out.println("Please, introduce the size of the matrix");
        int size = input.nextInt();

        // Global variables are initialized taking into account the size of the matrix
        distances = new int[size][size];

        // The file in that location is opened
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);


        // Our matrix is filled with the values of the file matrix
        for (int row = 0 ; row < size ; row++) {

            // Every value of each row is read and stored
            String line = b.readLine();
            String[] values = line.trim().split("\\s+");

            for (int col = 0; col < size; col++) {
                distances[row][col] = Integer.parseInt(values[col]);
            }
        }

        // Closing file
        b.close();

        /* ------------------------- ALGORITHM INITIALIZATION ----------------------- */

        // Initial variables to start the algorithm
        String path = "";
        int[] vertices = new int[size - 1];

        // Filling the initial vertices array with the proper values
        for (int i = 1; i < size; i++) {
            vertices[i - 1] = i;
        }

        // FIRST CALL TO THE RECURSIVE FUNCTION
        procedure(0, vertices, path, 0);

        System.out.print("Path: " + optimalPath + ". Distance = " + optimalDistance);
    }


    /* ------------------------------- RECURSIVE FUNCTION ---------------------------- */

    public static int procedure(int initial, int list[], String path, int costUntilHere) {

        // We concatenate the current path and the vertex taken as initial
        path = path + "" + Integer.toString(initial) + " - ";
        int length = list.length;
        int newCostUntilHere;


        // Exit case, if there are no more options to evaluate (last node)
        if (length == 0) {

            path = path + "0";
            newCostUntilHere = costUntilHere + distances[initial][0];

            // If it is the first evaluated branch (optimalDistance = 0)
            if (optimalDistance == 0){
                optimalDistance = newCostUntilHere;
            }

            // If it is another branch and its value is lower than the stored one
            else if (newCostUntilHere < optimalDistance){
                optimalDistance = newCostUntilHere;
                optimalPath = path;
            }

            return (distances[initial][0]);
        }

        // If the traversed branch reaches a point where the cost is higher than the stored one: stop traversing.
        else if (costUntilHere > optimalDistance && optimalDistance != 0){
            return 0;
        }

        // Common case, when there are several nodes in the list
        else {

            int[] costChildren = new int[length];
            int[][] newList = new int[length][(length - 1)];
            int costCurrentNode, costChild;
            int totalCost = 10000000;           // Big number to simulate infinity

            // For each of the nodes of the list
            for (int i = 0; i < length; i++) {

                // First of all we construct our new list, the one to be passed to each recursion
                for (int j = 0, k = 0; j < length; j++, k++) {

                    // The first iteration is not taken into account due to avoid pass the select
                    if (j == i) {
                        k--;
                        continue;
                    }
                    newList[i][k] = list[j];
                }

                // Cost of arriving the current node from its parent
                costCurrentNode = distances[initial][list[i]];

                // Here the cost to be passed to the recursive function is computed
                newCostUntilHere = costCurrentNode + costUntilHere;

                // RECURSIVE CALLS TO THE FUNCTION IN ORDER TO COMPUTE THE COSTS
                costChildren[i] = procedure(list[i], newList[i], path, newCostUntilHere);

                // The cost of every child + the current node cost is computed
                costChild = costChildren[i] + costCurrentNode;

                // Finally we select from the all possible children costs, the one with minimum value
                if (costChild < totalCost) {
                    totalCost = costChild;
                }
            }

            return (totalCost);
        }
    }
}