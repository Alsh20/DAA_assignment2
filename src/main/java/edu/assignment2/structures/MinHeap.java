package edu.assignment2.structures;

import java.util.Arrays;

public final class MinHeap {
 private int[] data; private int size; private final OperationStats stats=new OperationStats();
 public MinHeap(){this(8);} public MinHeap(int capacity){if(capacity<0)throw new IllegalArgumentException("capacity must be non-negative");data=new int[Math.max(1,capacity)];}
 public void insert(int value){ensureCapacity();int i=size++;data[i]=value;stats.move();while(i>0){int parent=(i-1)/2;stats.step();stats.step();stats.comparison();if(data[parent]<=data[i])break;swap(parent,i);i=parent;}}
 public int peekMin(){if(size==0)throw new IllegalStateException("heap is empty");stats.step();return data[0];}
 public int extractMin(){if(size==0)throw new IllegalStateException("heap is empty");int result=data[0];stats.step();size--;if(size>0){data[0]=data[size];stats.step();stats.move();siftDown(0);}return result;}
 private void siftDown(int i){while(true){int left=2*i+1;if(left>=size)break;int smaller=left;int right=left+1;stats.step();if(right<size){stats.step();stats.comparison();if(data[right]<data[left])smaller=right;}stats.step();stats.step();stats.comparison();if(data[i]<=data[smaller])break;swap(i,smaller);i=smaller;}}
 private void swap(int a,int b){int t=data[a];data[a]=data[b];data[b]=t;stats.step();stats.step();stats.move();stats.move();}
 private void ensureCapacity(){if(size==data.length){int[] grown=new int[data.length*2];for(int i=0;i<size;i++){grown[i]=data[i];stats.step();stats.move();}data=grown;}}
 public int size(){return size;} public boolean isEmpty(){return size==0;} public OperationStats stats(){return stats;} public void resetStats(){stats.reset();}
 public boolean isHeapOrdered(){for(int i=1;i<size;i++)if(data[(i-1)/2]>data[i])return false;return true;}
}

