# CS 366 - PA6 Analysis

**Name:** Solutions
**Date:** 2025-11-22

## 1. DP Comparison: Coin Change vs. Knapsack

**Comparison:**
In the 0/1 Knapsack problem (PA5), we had a constraint that each item could be used at most once. The recurrence relation `dp[i][w] = max(dp[i-1][w], dp[i-1][w-weight] + value)` explicitly looked back at the *previous* row `i-1` to ensure we weren't reusing the current item `i`.

In the Coin Change problem, we implemented a similar 2D table `dp[i][j]`, but with a key difference. Since we have an *unbounded* supply of each coin, when considering coin `i`, we can look at the solution for the *same* row `i` after subtracting the coin's value.
- Knapsack (0/1): `dp[i][w] = max(dp[i-1][w], dp[i-1][w-wt] + val)`
- Coin Change (Unbounded): `dp[i][j] = min(dp[i-1][j], dp[i][j-coin] + 1)`

This small change in indices (looking at `dp[i]` instead of `dp[i-1]` for the inclusion case) allows the algorithm to "reuse" the current item multiple times in the same solution.

**Recurrence Relation:**
`dp[i][j] = min(dp[i-1][j], dp[i][j - coin_value] + 1)`
Base case: `dp[i][0] = 0`, all other `dp` values initialized to infinity.

## 2. TSP Complexity

**Trace (N=4):**
For 4 cities (0, 1, 2, 3), starting at 0:
1. 0 -> 1
   - 1 -> 2 -> 3 -> 0 (Tour 1)
   - 1 -> 3 -> 2 -> 0 (Tour 2)
2. 0 -> 2
   - 2 -> 1 -> 3 -> 0 (Tour 3)
   - 2 -> 3 -> 1 -> 0 (Tour 4)
3. 0 -> 3
   - 3 -> 1 -> 2 -> 0 (Tour 5)
   - 3 -> 2 -> 1 -> 0 (Tour 6)

Total distinct tours = (N-1)! = 3! = 6.

**Time Complexity:**
The brute-force backtracking algorithm explores all permutations of the cities. The time complexity is **O(N!)**.
For N=50, 50! is approximately $3 \times 10^{64}$. Even if a computer could check $10^{12}$ tours per second, it would take significantly longer than the age of the universe to solve.

## 3. P vs NP & Satisfiability

**Verification Complexity:**
The `verifySAT` method iterates through each of the $M$ clauses. For each clause, it checks the literals in that clause (variable length in CNF, or fixed 3 in 3-SAT). Since the total number of literals is linear with respect to the input size (length of the formula), the verification takes **O(Length of Formula)** or **O(M * max_clause_size)** time. This is polynomial, so verification is in P.

**Reduction Idea:**
If we had a magic O(1) box for SAT, we could solve TSP by "reducing" TSP to SAT.
We would construct a boolean formula that asks: "Is there a tour of cost < K?".
- Variables $x_{i,j}$ could represent "City $i$ is visited at step $j$".
- Clauses would enforce validity (every city visited once, adjacent cities have edges).
- Additional clauses would enforce the total weight constraint (this is complex to encode in boolean logic but possible with binary adders).
We would then use binary search on K, calling the Magic SAT box each time, to find the minimum cost.
