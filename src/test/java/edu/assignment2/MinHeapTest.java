package edu.assignment2;

import edu.assignment2.structures.MinHeap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {
 @Test void sortedOutputAndHeapInvariant(){MinHeap h=new MinHeap();int[] values={8,1,4,1,-2,9};for(int x:values){h.insert(x);assertTrue(h.isHeapOrdered());}int previous=Integer.MIN_VALUE;while(!h.isEmpty()){int x=h.extractMin();assertTrue(x>=previous);previous=x;assertTrue(h.isHeapOrdered());}}
 @Test void emptyAndSingleElement(){MinHeap h=new MinHeap();assertThrows(IllegalStateException.class,h::peekMin);assertThrows(IllegalStateException.class,h::extractMin);h.insert(7);assertEquals(7,h.peekMin());assertEquals(7,h.extractMin());assertTrue(h.isEmpty());}
}
