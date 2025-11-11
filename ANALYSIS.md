# CS366 - PA5: Graph Algorithms Analysis

**Name:** [Your Name]
**Date:** [Submission Date]

## Part A: Theoretical Analysis

### Problem 1: BFS Complexity Analysis (25 points)

#### Time Complexity of BFS
*Analyze the time complexity of BFS on unweighted graphs. Show your work step by step.*

**Your Answer:**
```
[Fill in your analysis here]
- Initialization: O(?)
- Queue operations per vertex: O(?)
- Edge exploration: O(?)
- Path reconstruction: O(?)
- Total complexity: O(?)
```

#### Why BFS Finds Shortest Paths
*Explain why BFS guarantees shortest paths in unweighted graphs.*

**Your Answer:**
```
[Fill in your explanation]
```

#### Space Complexity
*Calculate the space complexity for BFS including the path storage.*

**Your Answer:**
```
[Fill in your analysis]
- Queue space: O(?)
- Visited array: O(?)
- Parent pointers: O(?)
- Path storage: O(?)
- Total space: O(?)
```

#### When to Use BFS for Game Pathfinding
*Discuss scenarios where BFS is appropriate in game development.*

**Your Answer:**
```
[Fill in your discussion]
```

### Problem 2: Dijkstra's Algorithm Analysis (25 points)

#### Time Complexity with Binary Heap
*Analyze Dijkstra's time complexity using a binary heap priority queue.*

**Your Answer:**
```
[Fill in your analysis]
- Heap initialization: O(?)
- Extract-min operations: ? × O(?)
- Decrease-key operations: ? × O(?)
- Total complexity: O(?)
```

#### Greedy Choice Property
*Explain why Dijkstra's greedy choice leads to optimal solutions.*

**Your Answer:**
```
[Fill in your explanation]
```

#### Comparison: With vs Without Priority Queue
*Compare the complexity of Dijkstra with and without a priority queue.*

**Your Answer:**
| Operation | With Priority Queue | Without Priority Queue |
|-----------|-------------------|----------------------|
| Find minimum | O(?) | O(?) |
| Update distance | O(?) | O(?) |
| Total complexity | O(?) | O(?) |

#### Memory Requirements for Large Maps
*Discuss memory usage for a game with 10,000 provinces.*

**Your Answer:**
```
[Fill in your analysis]
- Distance array: ? bytes
- Parent array: ? bytes
- Priority queue: ? bytes
- Adjacency list: ? bytes
- Total estimate: ? MB
```

### Problem 3: Precomputation Tradeoffs (25 points)

#### Space Required for All-Pairs Paths
*Calculate the space needed to store all shortest paths.*

**Your Answer:**
```
For V vertices:
- Number of pairs: ?
- Average path length: ?
- Bytes per node ID: ?
- Total space complexity: O(?)
- For 1000 nodes: approximately ? MB
```

#### Time Analysis: Precompute vs Query
*Compare precomputation time against query time.*

**Your Answer:**
```
Precomputation:
- Time to compute all paths: O(?)
- One-time cost at game start

Query without precomputation:
- Time per query: O(?)
- Cost per unit movement

Break-even analysis:
[Show when precomputation becomes worthwhile]
```

#### Break-Even Point Calculation
*Determine when precomputation pays off.*

**Your Answer:**
```
Let Q = number of queries
Precompute if: [precomputation time] < Q × [query time]
Solving for Q: ?
```

#### Static vs Dynamic Maps
*Explain why precomputation fails for dynamic maps.*

**Your Answer:**
```
[Fill in your explanation]
```

### Problem 4: Game Design Implications (25 points)

#### Weather Multipliers vs Terrain Weights
*Explain the fundamental difference between these approaches.*

**Your Answer:**
```
Terrain as edge weight:
- [Your explanation]

Weather as multiplier:
- [Your explanation]

Key difference:
- [Your explanation]
```

#### Impact on Modding
*Discuss how precomputation limits modding possibilities.*

**Your Answer:**
```
[Fill in your discussion]
Examples of mods that would break:
1.
2.
3.
```

#### Why "Making Games in Games" is Harder
*Analyze the computational challenges of user-generated content.*

**Your Answer:**
```
[Fill in your analysis]
Key challenges:
1.
2.
3.
```

#### Proposed Solutions
*Suggest approaches to balance performance and flexibility.*

**Your Answer:**
```
[Fill in your solutions]
1.
2.
3.
```

## Part B: Empirical Results

### Performance Measurements

#### Test Environment
```
Java Version: [Your Java version]
OS: [Your OS]
RAM: [Your RAM]
Processor: [Your processor]
```

#### BFS vs Dijkstra Comparison
*Record actual running times for both algorithms on the same graph.*

| Map Size | BFS Time (ms) | Dijkstra Time (ms) | Ratio |
|----------|---------------|-------------------|-------|
| 10 nodes | | | |
| 50 nodes | | | |
| 200 nodes | | | |

#### Precomputation Analysis
*Measure the cost and benefit of precomputation.*

| Map Size | Precompute Time (ms) | Memory Used (MB) | Query Time (μs) | Break-even Queries |
|----------|---------------------|-----------------|----------------|-------------------|
| 10 nodes | | | | |
| 50 nodes | | | | |
| 200 nodes | | | | |

#### Weather System Impact
*Compare pathfinding with and without weather.*

| Scenario | Static Dijkstra (ms) | Weather Dijkstra (ms) | Precomputed Valid? |
|----------|---------------------|---------------------|-------------------|
| Clear weather | | | Yes/No |
| Rain (1.5x) | | | Yes/No |
| Snow (2.0x) | | | Yes/No |

### Observations and Insights

#### Performance Observations
*What did you observe about algorithm performance?*

**Your Answer:**
```
[Fill in your observations]
```

#### Memory vs Speed Tradeoff
*What did you learn about the space-time tradeoff?*

**Your Answer:**
```
[Fill in your insights]
```

#### Real-World Applications
*How do these findings apply to actual game development?*

**Your Answer:**
```
[Fill in your applications]
```

## Reflection

### Key Takeaways
*What are the three most important things you learned?*

1.
2.
3.

### Challenges Encountered
*What was the most challenging part of this assignment?*

**Your Answer:**
```
[Fill in your reflection]
```

### Connection to Course Material
*How does this assignment relate to topics covered in class?*

**Your Answer:**
```
[Fill in your connections]
```

---

_I certify that this submission represents my own work and understanding of the material._

**Signature:** _____________________