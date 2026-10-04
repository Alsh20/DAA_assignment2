package edu.assignment2;

import edu.assignment2.structures.DynamicArray;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {
    @Test void growsAndPreservesValues() { DynamicArray a = new DynamicArray(1); for (int i=0;i<100;i++) a.add(i); for (int i=0;i<100;i++) assertEquals(i,a.get(i)); }
    @Test void indexedChangesAndDuplicates() { DynamicArray a=new DynamicArray(); a.add(1);a.add(1);a.add(1,7);assertEquals(7,a.remove(1));assertTrue(a.contains(1));assertEquals(2,a.size()); }
    @Test void boundariesAndInvalidIndices() { DynamicArray a=new DynamicArray();assertThrows(IndexOutOfBoundsException.class,()->a.get(0));assertThrows(IndexOutOfBoundsException.class,()->a.remove(0));a.add(0,4);assertEquals(4,a.remove(0));assertThrows(IndexOutOfBoundsException.class,()->a.add(2,1)); }
}

