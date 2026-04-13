package com.mjsamaha.codelobster.problem;

public enum ProblemTag {

    ARRAY("Array", TagType.DATA_STRUCTURE),
    STRING("String", TagType.DATA_STRUCTURE),
    LINKED_LIST("Linked List", TagType.DATA_STRUCTURE),
    
    STACK("Stack", TagType.DATA_STRUCTURE),
    QUEUE("Queue", TagType.DATA_STRUCTURE),
    DEQUE("Deque", TagType.DATA_STRUCTURE),
    
    HASH_MAP("Hash Map", TagType.DATA_STRUCTURE),
    HASH_SET("Hash Set", TagType.DATA_STRUCTURE),
    
    HEAP("Heap", TagType.DATA_STRUCTURE),
    PRIORITY_QUEUE("Priority Queue", TagType.DATA_STRUCTURE),
    
    TREE("Tree", TagType.DATA_STRUCTURE),
    BINARY_TREE("Binary Tree", TagType.DATA_STRUCTURE),
    BINARY_SEARCH_TREE("Binary Search Tree", TagType.DATA_STRUCTURE),
    TRIE("Trie", TagType.DATA_STRUCTURE),
    SEGMENT_TREE("Segment Tree", TagType.DATA_STRUCTURE),
    FENWICK_TREE("Fenwick Tree", TagType.DATA_STRUCTURE),
    
    GRAPH("Graph", TagType.DATA_STRUCTURE),
    DISJOINT_SET("Disjoint Set", TagType.DATA_STRUCTURE),

    BINARY_SEARCH("Binary Search", TagType.ALGORITHM),
    TWO_POINTERS("Two Pointers", TagType.ALGORITHM),
    SLIDING_WINDOW("Sliding Window", TagType.ALGORITHM),
    PREFIX_SUM("Prefix Sum", TagType.ALGORITHM),
    BACKTRACKING("Backtracking", TagType.ALGORITHM),
    DIVIDE_AND_CONQUER("Divide and Conquer", TagType.ALGORITHM),
    GREEDY("Greedy", TagType.ALGORITHM),
    DYNAMIC_PROGRAMMING("Dynamic Programming", TagType.ALGORITHM),
    MEMOIZATION("Memoization", TagType.ALGORITHM),
    RECURSION("Recursion", TagType.ALGORITHM),
    BRUTE_FORCE("Brute Force", TagType.ALGORITHM),

    DFS("DFS", TagType.GRAPH),
    BFS("BFS", TagType.GRAPH),
    DIJKSTRA("Dijkstra", TagType.GRAPH),
    BELLMAN_FORD("Bellman-Ford", TagType.GRAPH),
    FLOYD_WARSHALL("Floyd-Warshall", TagType.GRAPH),
    TOPOLOGICAL_SORT("Topological Sort", TagType.GRAPH),
    MINIMUM_SPANNING_TREE("Minimum Spanning Tree", TagType.GRAPH),
    KRUSKAL("Kruskal", TagType.GRAPH),
    PRIM("Prim", TagType.GRAPH),
    STRONGLY_CONNECTED_COMPONENTS("Strongly Connected Components", TagType.GRAPH),

    NUMBER_THEORY("Number Theory", TagType.MATH),
    PRIME_NUMBERS("Prime Numbers", TagType.MATH),
    SIEVE("Sieve", TagType.MATH),
    GCD("GCD", TagType.MATH),
    LCM("LCM", TagType.MATH),
    MODULAR_ARITHMETIC("Modular Arithmetic", TagType.MATH),
    COMBINATORICS("Combinatorics", TagType.MATH),
    PROBABILITY("Probability", TagType.MATH),
    MATRIX("Matrix", TagType.MATH),
    FAST_EXPONENTIATION("Fast Exponentiation", TagType.MATH),

    BITMASK("Bitmask", TagType.TECHNIQUE),
    MEET_IN_THE_MIDDLE("Meet in the Middle", TagType.TECHNIQUE),
    MONOTONIC_STACK("Monotonic Stack", TagType.TECHNIQUE),
    MONOTONIC_QUEUE("Monotonic Queue", TagType.TECHNIQUE),
    LAZY_PROPAGATION("Lazy Propagation", TagType.TECHNIQUE),
    HEAVY_LIGHT_DECOMPOSITION("Heavy-Light Decomposition", TagType.TECHNIQUE),
    CENTROID_DECOMPOSITION("Centroid Decomposition", TagType.TECHNIQUE),

    KMP("KMP", TagType.STRING),
    Z_ALGORITHM("Z Algorithm", TagType.STRING),
    RABIN_KARP("Rabin-Karp", TagType.STRING),
    SUFFIX_ARRAY("Suffix Array", TagType.STRING),
    SUFFIX_TREE("Suffix Tree", TagType.STRING),
    STRING_HASHING("String Hashing", TagType.STRING),

    SIMULATION("Simulation", TagType.PATTERN),
    SORTING("Sorting", TagType.PATTERN),
    CUSTOM_COMPARATOR("Custom Comparator", TagType.PATTERN),
    INTERACTIVE("Interactive", TagType.PATTERN),
    GAME_THEORY("Game Theory", TagType.PATTERN);

    private final String displayName;
    private final TagType type;

    ProblemTag(String displayName, TagType type) {
        this.displayName = displayName;
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public TagType getType() {
        return type;
    }
}
