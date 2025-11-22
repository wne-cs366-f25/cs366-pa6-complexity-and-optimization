package edu.wne.cs366;

import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * CS 366 - PA6: Complexity & Optimization
 * Implements algorithms for Coin Change (DP), TSP (Backtracking), and SAT Verification.
 * 
 * @author [Your Name]
 * @version 2025-11-22
 */
public class Complexity {

    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            if (args.length > 0) {
                scanner = new Scanner(new File(args[0]));
            } else {
                scanner = new Scanner(System.in);
            }

            if (!scanner.hasNext()) return;

            String type = scanner.next();
            
            switch (type) {
                case "COINS":
                    solveCoins(scanner);
                    break;
                case "TSP":
                    solveTSP(scanner);
                    break;
                case "SAT":
                    solveSAT(scanner);
                    break;
                default:
                    System.out.println("Unknown problem type: " + type);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + args[0]);
        } finally {
            if (scanner != null) scanner.close();
        }
    }

    // --- Part 1: Coin Change (Dynamic Programming) ---

    private static void solveCoins(Scanner scanner) {
        // Format:
        // COINS
        // 1 5 10 25
        // 37
        
        scanner.nextLine(); // Consume newline after "COINS"
        String coinLine = scanner.nextLine().trim();
        while(coinLine.isEmpty() && scanner.hasNextLine()) {
            coinLine = scanner.nextLine().trim();
        }
        
        String[] coinParts = coinLine.split("\\s+");
        int[] coins = new int[coinParts.length];
        for(int i=0; i<coinParts.length; i++) {
            coins[i] = Integer.parseInt(coinParts[i]);
        }
        
        int amount = 0;
        if(scanner.hasNextInt()) {
            amount = scanner.nextInt();
        }

        System.out.println("Coin Change Solution:");
        int result = minCoins(coins, amount);
        System.out.println("Minimum coins for " + amount + ": " + result);
    }

    /**
     * Finds the minimum number of coins needed to make the amount.
     * @param coins Array of coin denominations
     * @param amount Target amount
     * @return Minimum coins, or -1 if impossible
     */
    public static int minCoins(int[] coins, int amount) {
        // dp[i] = min coins to make amount i
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // Initialize with a value > amount
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    // --- Part 2: Traveling Salesperson (Backtracking) ---

    private static void solveTSP(Scanner scanner) {
        // Format:
        // TSP
        // 4 (N)
        // Row 0...
        
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        int[][] graph = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (scanner.hasNextInt()) {
                    graph[i][j] = scanner.nextInt();
                }
            }
        }

        System.out.println("TSP Solution:");
        int cost = solveTSP(graph);
        System.out.println("Minimum Tour Cost: " + cost);
    }

    /**
     * Solves TSP using backtracking to find the minimum tour cost.
     * @param graph Adjacency matrix where graph[i][j] is distance from i to j
     * @return Minimum tour cost
     */
    public static int solveTSP(int[][] graph) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        visited[0] = true; // Start at city 0
        
        // Current cost, current city, count of visited, answer wrapper
        int[] minCost = {Integer.MAX_VALUE};
        
        tspBacktrack(graph, visited, 0, n, 1, 0, minCost);
        
        return minCost[0];
    }
    
    private static void tspBacktrack(int[][] graph, boolean[] visited, int currPos, int n, int count, int cost, int[] minCost) {
        if (count == n && graph[currPos][0] > 0) {
            minCost[0] = Math.min(minCost[0], cost + graph[currPos][0]);
            return;
        }
        
        // Pruning
        if (cost >= minCost[0]) return;
        
        for (int i = 0; i < n; i++) {
            if (!visited[i] && graph[currPos][i] > 0) {
                visited[i] = true;
                tspBacktrack(graph, visited, i, n, count + 1, cost + graph[currPos][i], minCost);
                visited[i] = false;
            }
        }
    }

    // --- Part 3: SAT Verification ---

    private static void solveSAT(Scanner scanner) {
        // Format:
        // SAT
        // 3 4 (vars clauses)
        // 1 -2 3 ...
        // 1 0 1 (assignment)
        
        if (!scanner.hasNextInt()) return;
        int numVars = scanner.nextInt();
        int numClauses = scanner.nextInt();
        
        int[][] clauses = new int[numClauses][3]; // Assuming 3-SAT
        for(int i=0; i<numClauses; i++) {
             for(int j=0; j<3; j++) {
                 if(scanner.hasNextInt()) clauses[i][j] = scanner.nextInt();
             }
        }
        
        boolean[] assignment = new boolean[numVars + 1];
        for(int i=1; i<=numVars; i++) {
            if(scanner.hasNextInt()) {
                int val = scanner.nextInt();
                assignment[i] = (val == 1);
            }
        }
        
        System.out.println("SAT Verification:");
        boolean result = verifySAT(clauses, assignment);
        System.out.println("Satisfied: " + result);
    }

    /**
     * Verifies if a given assignment satisfies the CNF formula.
     * @param clauses 2D array where each row is a clause (e.g., {1, -2, 3})
     * @param assignment Boolean array where index i corresponds to variable i (1-indexed)
     * @return true if satisfied, false otherwise
     */
        public static boolean verifySAT(int[][] clauses, boolean[] assignment) {
        for (int[] clause : clauses) {
            boolean clauseSatisfied = false;
            for (int literal : clause) {
                // Literal k: true if assignment[k] is true
                // Literal -k: true if assignment[k] is false
                int varIndex = Math.abs(literal);
                boolean varValue = assignment[varIndex];
                
                if (literal > 0) {
                    if (varValue) clauseSatisfied = true;
                } else {
                    if (!varValue) clauseSatisfied = true;
                }
                if (clauseSatisfied) break;
            }
            if (!clauseSatisfied) return false;
        }
        return true;
    }
}