package edu.wne.cs366;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComplexityTest {

    // --- Coin Change Tests ---

    @Test
    public void testMinCoinsBasic() {
        int[] coins = {1, 5, 10, 25};
        // 37 = 25 + 10 + 1 + 1 (4 coins)
        assertEquals(4, Complexity.minCoins(coins, 37), "Should be 4 coins (25+10+1+1)");
    }

    @Test
    public void testMinCoinsNonGreedy() {
        // A case where greedy fails.
        // Coins: 1, 3, 4. Target: 6.
        // Greedy: 4 + 1 + 1 (3 coins)
        // Optimal: 3 + 3 (2 coins)
        int[] coins = {1, 3, 4};
        assertEquals(2, Complexity.minCoins(coins, 6), "Should be 2 coins (3+3), not greedy (4+1+1)");
    }

    @Test
    public void testMinCoinsImpossible() {
        int[] coins = {2};
        assertEquals(-1, Complexity.minCoins(coins, 3), "Should return -1 for impossible amount");
    }

    // --- TSP Tests ---

    @Test
    public void testTSPBasic() {
        // 4 cities
        // 0 -> 1 (10) -> 2 (35) -> 3 (30) -> 0 (20) = 95
        // Optimal tour: 0 -> 1 -> 3 -> 2 -> 0
        // 0->1 (10)
        // 1->3 (25)
        // 3->2 (30)
        // 2->0 (15)
        // Sum = 10 + 25 + 30 + 15 = 80
        
        int[][] graph = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        
        assertEquals(80, Complexity.solveTSP(graph), "Minimum tour cost should be 80");
    }

    // --- SAT Tests ---

    @Test
    public void testVerifySATSimple() {
        // (x1 OR NOT x2) AND (x2 OR x3)
        int[][] clauses = {
            {1, -2},
            {2, 3}
        };
        
        // Case 1: Satisfying assignment
        // x1=F, x2=F, x3=T
        // Clause 1: F OR T -> T
        // Clause 2: F OR T -> T
        boolean[] satisfyingAssignment = {false, false, false, true}; // 1-indexed
        assertTrue(Complexity.verifySAT(clauses, satisfyingAssignment), "Should satisfy the formula");

        // Case 2: Unsatisfying assignment
        // x1=F, x2=T, x3=F
        // Clause 1: F OR F -> F (Unsatisfied)
        boolean[] unsatisfyingAssignment = {false, false, true, false}; // 1-indexed
        assertFalse(Complexity.verifySAT(clauses, unsatisfyingAssignment), "Should NOT satisfy the formula");
    }
}