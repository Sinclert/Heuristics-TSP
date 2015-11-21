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

public class HK_Paths {

    /* ----------------------------- GLOBAL VARIABLES ------------------------------ */
    public static int[][] distances;
    public static int numSolutions;
    public static int finalResults[];
    public static String paths[];
    public static int counter = 0;


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
        numSolutions = factorial(size - 1);
        finalResults = new int[numSolutions];
        paths = new String[numSolutions];

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
        int distance = procedure(0, vertices, path, 0);

        int optimal = 0;
        for (int i = 0; i < numSolutions; i++) {

            // IF THIS LINE IS UNCOMMENTED, THE USER CAN SEE ALL THE POSSIBLE PATHS WITH THEIR DISTANCES
            // System.out.print("Path: " + paths[i] + ". Distance = " + finalResults[i] + "\n");

            // When we reach the optimal one, its index is saved
            if (finalResults[i] == distance) {
                optimal = i;
            }
        }
        System.out.println();
        System.out.print("Path: " + paths[optimal] + ". Distance = " + finalResults[optimal] + " (OPTIMAL)");
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

            // Both results, numerical distances and paths to those distances, are stored
            paths[counter] = path;
            finalResults[counter] = costUntilHere + distances[initial][0];

            counter++;
            return (distances[initial][0]);
        }

        // Common case, where there are more than 1 node
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

    // Factorial function used to calculate the number of solutions
    public static int factorial(int n) {

        if (n <= 1) return 1;
        else return (n * factorial(n - 1));
    }
}