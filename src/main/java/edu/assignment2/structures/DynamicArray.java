package edu.assignment2.structures;

import java.util.Arrays;

public final class DynamicArray implements IntSequence {
    private int[] elements;
    private int size;
    private final OperationStats stats = new OperationStats();

    public DynamicArray() { this(8); }
    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 0) throw new IllegalArgumentException("capacity must be non-negative");
        elements = new int[Math.max(1, initialCapacity)];
    }
    @Override public void add(int value) {
        ensureCapacity(); elements[size++] = value; stats.move();
    }
    @Override public void add(int index, int value) {
        checkPosition(index); ensureCapacity();
        for (int i = size; i > index; i--) { elements[i] = elements[i - 1]; stats.step(); stats.move(); }
        elements[index] = value; size++; stats.move();
    }
    @Override public int remove(int index) {
        checkElement(index); int removed = elements[index]; stats.step();
        for (int i = index; i < size - 1; i++) { elements[i] = elements[i + 1]; stats.step(); stats.move(); }
        size--; return removed;
    }
    @Override public int get(int index) { checkElement(index); stats.step(); return elements[index]; }
    @Override public boolean contains(int value) {
        for (int i = 0; i < size; i++) { stats.step(); stats.comparison(); if (elements[i] == value) return true; }
        return false;
    }
    private void ensureCapacity() {
        if (size == elements.length) { int[] grown = new int[elements.length * 2];
            for (int i = 0; i < size; i++) { grown[i] = elements[i]; stats.step(); stats.move(); }
            elements = grown; }
    }
    private void checkElement(int index) { if (index < 0 || index >= size) throw new IndexOutOfBoundsException("index=" + index + ", size=" + size); }
    private void checkPosition(int index) { if (index < 0 || index > size) throw new IndexOutOfBoundsException("index=" + index + ", size=" + size); }
    @Override public int size() { return size; }
    @Override public OperationStats stats() { return stats; }
    @Override public void resetStats() { stats.reset(); }
}


