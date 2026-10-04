package edu.assignment2;

import edu.assignment2.structures.*;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class RandomizedStructuresTest {
 @Test void structuresMatchReferenceSequence(){Random r=new Random(42);IntSequence[] own={new DynamicArray(),new MyLinkedList()};java.util.ArrayList<Integer> ref=new java.util.ArrayList<>();for(int i=0;i<3000;i++){if(ref.isEmpty()||r.nextBoolean()){int v=r.nextInt(50);int at=r.nextInt(ref.size()+1);ref.add(at,v);for(IntSequence s:own)s.add(at,v);}else{int at=r.nextInt(ref.size());int expected=ref.remove(at);for(IntSequence s:own)assertEquals(expected,s.remove(at));}for(IntSequence s:own){assertEquals(ref.size(),s.size());if(!ref.isEmpty()){int at=r.nextInt(ref.size());assertEquals(ref.get(at).intValue(),s.get(at));}}}}
}
