# CS 366 - PA6: Complexity & Optimization

**Due Date:** December 4, 2025, 11:59 PM

This assignment explores the boundaries of efficient computation. You will implement algorithms for problems ranging from polynomial-time dynamic programming to NP-Hard optimization problems, making connections to the "Hard Problems" covered in Exam 3.

---

## Background

In this course, we have primarily dealt with problems that can be solved efficiently (in polynomial time). In this final assignment, we encounter problems that are computationally difficult:

1.  **Coin Change Problem**: A variation of the Knapsack problem (unbounded knapsack) where we want to minimize the number of coins to make a value. Solvable efficiently with DP.
2.  **Traveling Salesperson Problem (TSP)**: Finding the shortest tour visiting all cities. This is an **NP-Hard** problem, meaning no polynomial-time algorithm is known. We will solve small instances using backtracking.
3.  **Boolean Satisfiability (SAT)**: Determining if a boolean formula can be true. This is the first problem proven to be **NP-Complete**. We will implement a polynomial-time _verifier_ for SAT.

---

## Assignment Tasks

### Part 1: Java Implementation

You will implement three distinct algorithms in `Complexity.java`.

#### 1. Dynamic Programming: Coin Change

Given a set of coin denominations and a target amount, find the **minimum number of coins** needed to make that amount. This differs from the 0/1 Knapsack (PA5) because you can use each coin multiple times.

- **Input**: Array of coin values (e.g., `[1, 5, 10, 25]`) and target amount (e.g., `37`).
- **Output**: Minimum count (e.g., `4` -> 25 + 10 + 1 + 1).
- **Method**: `public static int minCoins(int[] coins, int amount)`

#### 2. Backtracking: Traveling Salesperson (TSP)

Given a graph of distances between cities, find the cost of the shortest tour that visits every city exactly once and returns to the start.

- **Input**: Adjacency matrix of distances.
- **Output**: Minimum tour cost.
- **Method**: `public static int solveTSP(int[][] graph)`
- **Approach**: Recursive backtracking (try all permutations).

#### 3. Verification: 3-SAT Verifier

One of the defining properties of **NP** problems is that a proposed solution can be _verified_ in polynomial time. You will implement a verifier for 3-SAT.

- **Input**: A boolean formula in Conjunctive Normal Form (CNF) and a proposed assignment of truth values.
- **Output**: `true` if the assignment satisfies the formula, `false` otherwise.
- **Method**: `public static boolean verifySAT(int[][] clauses, boolean[] assignment)`
- **Format**: Clauses are arrays of integers where `k` is variable `x_k` and `-k` is `NOT x_k`.

---

### Part 2: Written Analysis

Answer the following questions in a separate document (`ANALYSIS.md`, PDF or paper submission):

1.  **DP Comparison**:

    - Compare the logic of your `minCoins` (Coin Change) solution to the `dynamicKnapsack` (0/1 Knapsack) from PA5.
    - How does the "unbounded" nature of Coin Change affect the recurrence relation?

2.  **TSP Complexity**:

    - Trace the execution of your TSP backtracking on a small graph (4 cities). How many recursive calls are made?
    - What is the time complexity of your solution in Big-O notation? Why is this impractical for N=50?

3.  **P vs NP**:
    - Explain why your `verifySAT` method is O(N) (polynomial), even though finding the solution is difficult.
    - If you had a "magic box" that could solve SAT in O(1), how could you use it to solve TSP? (Briefly explain the reduction concept).

---

## Input/Output Format

Your program should read from standard input (or a file specified as an arg) with the following format:

```
<problem_type> (COINS, TSP, or SAT)
<problem_specific_data>
```

**Example Input (Coins):**

```
COINS
1 5 10 25
37
```

**Example Input (TSP):**

```
TSP
4
0 10 15 20
10 0 35 25
15 35 0 30
20 25 30 0
```

**Example Input (SAT):**

```
SAT
3 4  (3 variables, 4 clauses)
1 -2 3
-1 2 3
1 2 -3
-1 -2 -3
1 0 1 (Assignment: x1=T, x2=F, x3=T)
```

---

## Submission Requirements

1.  **Source Code**: `Complexity.java` in `edu.wne.cs366`.
2.  **Analysis**: `ANALYSIS.md` or PDF.

## Grading Criteria

- **Implementation (60%)**: Correctness of all three algorithms.
- **Analysis (40%)**: Depth of understanding of complexity classes and algorithmic behavior.

**Due Date:** December 4, 11:59 PM
**Late Policy:** 10% per day, maximum 5 days late.

---

_Course content developed by Declan Gray-Mullen for WNEU with Gemini_
