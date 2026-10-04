package edu.assignment2;

import edu.assignment2.structures.MyLinkedList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {
 @Test void indexedChangesAndDuplicates(){MyLinkedList l=new MyLinkedList();l.add(1);l.add(1);l.add(1,9);assertEquals(9,l.remove(1));assertTrue(l.contains(1));assertEquals(2,l.size());}
 @Test void headTailAndInvalidIndices(){MyLinkedList l=new MyLinkedList();assertThrows(IndexOutOfBoundsException.class,()->l.remove(0));assertThrows(IndexOutOfBoundsException.class,()->l.get(0));l.add(0,2);l.add(1,3);assertEquals(3,l.get(1));assertEquals(2,l.remove(0));assertEquals(3,l.remove(0));assertThrows(IndexOutOfBoundsException.class,()->l.add(2,0));}
}

