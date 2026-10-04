# Assignment 2 - Alisher Ospanov

## Layout

- `structures`: `DynamicArray`, `MyLinkedList`, `MinHeap`, operation counters
- `benchmark`: deterministic workloads W1–W4 and CSV export
- `src/test/java`: JUnit 5 correctness, edge, heap-property and randomized model tests
- `results/results.csv`, `results/plots/`: benchmark output and plots
- `REPORT.md`: complexity analysis, invariant proofs, and discussion

## Reproducibility and counting

Each case uses `new Random(42)`. Every sequence gets the same integer values for a given size. W2 uses exactly 500 present and 500 absent queries; W3 reinitializes the sequence before each run; W4 checks non-decreasing extraction as it runs. Counters are reset after setup so they describe workload operations. CSV counters are the median of the five measured-run counters, matching the median timing policy

A `step` means an array-cell read or a node-link traversal, `move` means a shifted/stored array element or pointer update, and `comparison` means an element-to-element comparison. Capacity growth counts each copied value as one array-cell read step and one move