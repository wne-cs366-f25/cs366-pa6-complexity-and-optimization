# CS366 - PA5: Graph Algorithms - Strategic Game Pathfinding

This assignment explores graph algorithms through the lens of strategic video game design, inspired by grand strategy games from Paradox Interactive (Europa Universalis, Hearts of Iron, Crusader Kings, Victoria). You will implement pathfinding algorithms that demonstrate how game developers balance performance, accuracy, and moddability when designing game systems.

## Learning Objectives

Through this assignment, you will:
- Implement and analyze BFS for unweighted graph pathfinding
- Implement and analyze Dijkstra's algorithm for weighted graph pathfinding
- Understand space-time tradeoffs through path precomputation
- Explore how dynamic systems (weather) impact algorithm choice
- Appreciate how architectural decisions affect game moddability
- Connect theoretical graph algorithms to real-world game development

## Background: Pathfinding in Strategy Games

In grand strategy games, armies and units must navigate across a world map divided into provinces or tiles. Game designers face several challenges:

1. **Map Representation**: How to efficiently represent thousands of provinces (nodes) and their connections (edges)
2. **Movement Costs**: How to model terrain (mountains, rivers, forests) affecting movement speed
3. **Performance**: How to compute paths quickly when players command dozens of units
4. **Moddability**: How to allow modders to add features without breaking optimizations

### The Paradox Approach

Games like Europa Universalis IV use a clever optimization: instead of uniform grids, they use larger provinces in less populated areas (Siberia, Sahara) and smaller provinces in important regions (Europe, China). This reduces the graph size while maintaining detail where it matters.

**Example**: Moving an army from Paris to Moscow
- **Naive approach**: Calculate path through hundreds of small tiles
- **Paradox approach**: Calculate path through ~50 provinces of varying sizes
- **Optimization**: Precompute common paths for AI units

## Problem Description

You are designing the pathfinding system for a new strategy game. Your game world is represented as a graph where:
- **Nodes** represent provinces/tiles on the map
- **Edges** represent borders between adjacent provinces
- **Edge weights** represent movement cost (terrain difficulty)

You must implement multiple pathfinding strategies and analyze their tradeoffs.

### Part A: Theoretical Analysis

Complete the theoretical analysis in `ANALYSIS.md` covering:

#### Problem 1: BFS Complexity Analysis (25 points)
- Analyze time complexity of BFS on unweighted graphs
- Explain why BFS finds shortest paths in unweighted graphs
- Calculate space complexity for storing the path
- Discuss when BFS is appropriate for game pathfinding

#### Problem 2: Dijkstra's Algorithm Analysis (25 points)
- Analyze time complexity with binary heap priority queue
- Explain the greedy choice property
- Compare complexity with and without priority queue
- Discuss memory requirements for large game maps

#### Problem 3: Precomputation Tradeoffs (25 points)
- Calculate space required to store all-pairs shortest paths
- Analyze time to precompute paths vs query time
- Determine the "break-even point" where precomputation pays off
- Explain why this works for static but not dynamic maps

#### Problem 4: Game Design Implications (25 points)
- Explain why weather as a multiplier differs from terrain as edge weight
- Discuss how precomputation limits modding possibilities
- Analyze why "making games in games" is computationally harder
- Propose solutions for balancing performance and flexibility

### Part B: Programming Implementation

Implement the following methods in `PathfindingAlgorithms.java`:

#### Method 1: `findPathBFS(GameMap map, int start, int target)`
Implement breadth-first search for unweighted graphs.

**Scenario**: Early game prototype where all provinces have equal movement cost.

**Requirements**:
- Use a queue for BFS traversal
- Track parent pointers to reconstruct path
- Return list of node IDs representing shortest path
- Handle unreachable targets (return empty list)

**Time Complexity**: O(V + E)
**Space Complexity**: O(V)

#### Method 2: `findPathDijkstra(GameMap map, int start, int target)`
Implement Dijkstra's algorithm for weighted graphs.

**Scenario**: Full game with terrain-based movement costs (mountains = 3, plains = 1, etc.)

**Requirements**:
- Use `PriorityQueue<PathNode>` for efficient minimum extraction
- Track distances and parent pointers
- Return list of node IDs representing shortest path
- Handle unreachable targets (return empty list)

**Time Complexity**: O((V + E) log V)
**Space Complexity**: O(V)

#### Method 3: `precomputeAllPaths(GameMap map)`
Precompute shortest paths between all pairs of nodes.

**Scenario**: Game startup optimization for AI pathfinding.

**Requirements**:
- Run Dijkstra from each node as source
- Store results in `Map<PathQuery, List<Integer>>`
- PathQuery contains (start, target) pair
- Enable O(1) path lookups after precomputation

**Time Complexity**: O(V² log V + VE)
**Space Complexity**: O(V²)

#### Method 4: `getPrecomputedPath(int start, int target)`
Retrieve precomputed path in constant time.

**Requirements**:
- Look up path in precomputed table
- Return stored path or empty list if not found
- Must be O(1) operation

#### Method 5: `findPathWithWeather(GameMap map, WeatherSystem weather, int start, int target)`
Modified Dijkstra accounting for dynamic weather effects.

**Scenario**: Advanced game feature where weather affects movement speed.

**Requirements**:
- Apply weather multipliers to edge weights during traversal
- Weather affects unit speed, not terrain difficulty
- Cannot use precomputed paths (weather changes dynamically)
- Demonstrate why static optimization fails

**Example**:
- Base: Plains (cost 1) → Forest (cost 2)
- With rain: Plains (cost 1 × 1.5 = 1.5) → Forest (cost 2 × 1.5 = 3)

#### Method 6: `compareApproaches(GameMap map, List<PathQuery> queries, WeatherSystem weather)`
Compare all pathfinding approaches with timing analysis.

**Requirements**:
- Time each approach for the same queries
- Show when precomputation beats on-demand calculation
- Demonstrate weather's impact on precomputed paths
- Output formatted comparison table

## Getting Started

### Step 1: Understand the Problem
Read through the game scenario and understand how graphs model game maps.

### Step 2: Run Initial Tests
```bash
./gradlew test
```
Tests will fail initially - use them to guide your implementation.

### Step 3: Implement BFS
Start with the simplest algorithm - BFS for unweighted graphs.

### Step 4: Implement Dijkstra
Extend to weighted graphs using priority queue optimization.

### Step 5: Add Precomputation
Implement the lookup table approach and analyze memory usage.

### Step 6: Handle Weather
Add dynamic weather system and observe how it breaks precomputation.

### Step 7: Run Performance Comparison
```bash
./gradlew run
```
Observe timing differences between approaches on sample maps.

### Step 8: Complete Analysis
Document your findings in `ANALYSIS.md` with theoretical and empirical results.

## Sample Maps

Three maps are provided in the `maps/` directory:

1. **small_europe.txt**: Dense graph representing Western Europe (50 nodes)
2. **large_world.txt**: Sparse graph with varying province sizes (200 nodes)
3. **test_simple.txt**: Minimal graph for debugging (10 nodes)

Map format:
```
<num_nodes>
<node_id> <node_name>
...
<num_edges>
<source_id> <target_id> <weight>
...
```

## Real-World Context

### Why This Matters

Modern games increasingly support user-generated content (mods). Design decisions that optimize performance often limit modding:

- **Minecraft**: Chose flexibility over optimization, enabling massive modding community
- **Paradox Games**: Precomputed paths limit weather/season mods
- **Roblox**: Sacrifices performance for user creativity

Understanding these tradeoffs prepares you for real game development challenges.

### Industry Insights

- **Static Optimization**: Works for fixed game rules but breaks with mods
- **Dynamic Systems**: More CPU-intensive but enable creative gameplay
- **Hybrid Approaches**: Cache recent paths, invalidate when map changes
- **Future Trends**: GPU pathfinding, hierarchical pathfinding, neural network approximations

## Submission Requirements

### Files to Submit

1. **PathfindingAlgorithms.java** - Your complete implementation
2. **ANALYSIS.md** - Theoretical analysis and empirical results
3. All provided files (tests, build files, maps)

### Submission Process

```bash
cd /workspace/assignments
tar -czf pa5-YOURNAME.tar.gz pa5/
```

Download and submit to Kodiak before the deadline.

## Grading Criteria

- **Submission (33.3%)**: All required files submitted correctly
- **Completeness (33.3%)**: All methods implemented, analysis attempted
- **Correctness (33.3%)**: Accurate implementations, sound analysis

**Due Date**: November 20 by 11:59 PM
**Late Policy**: 10% per day, maximum 5 days late

## Tips for Success

1. **Start with BFS**: It's simpler and helps you understand the graph structure
2. **Use the debugger**: Step through small examples to verify logic
3. **Test incrementally**: Get each method working before moving on
4. **Profile your code**: Use timing to verify complexity analysis
5. **Think like a game developer**: Consider player experience and modding

## Common Pitfalls

- Forgetting to mark nodes as visited (infinite loops)
- Not handling disconnected graphs (unreachable targets)
- Incorrect priority queue comparison for Dijkstra
- Underestimating memory usage of precomputation
- Confusing node IDs with array indices

---

_Course content developed by Declan Gray-Mullen for WNEU with Claude_