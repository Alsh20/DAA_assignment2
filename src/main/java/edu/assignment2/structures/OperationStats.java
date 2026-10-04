package edu.assignment2.structures;

public final class OperationStats {
    private long steps, moves, comparisons;
    public long steps() { return steps; }
    public long moves() { return moves; }
    public long comparisons() { return comparisons; }
    public void reset() { steps = moves = comparisons = 0; }
    void step() { steps++; }
    void move() { moves++; }
    void comparison() { comparisons++; }
    public void add(OperationStats other) { steps += other.steps; moves += other.moves; comparisons += other.comparisons; }
}
