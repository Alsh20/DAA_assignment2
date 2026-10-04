package edu.assignment2.structures;

public final class MyLinkedList implements IntSequence {
    private static final class Node { int value; Node next; Node(int value) { this.value=value; } }
    private Node head, tail;
    private int size;
    private final OperationStats stats = new OperationStats();
    @Override public void add(int value) {
        Node node=new Node(value);
        if (tail==null) { head=tail=node; stats.move(); stats.move(); }
        else { tail.next=node; stats.move(); tail=node; stats.move(); }
        size++;
    }
    @Override public void add(int index,int value) {
        checkPosition(index); Node node=new Node(value);
        if(index==0) { node.next=head; stats.move(); head=node; stats.move(); if(size==0){tail=node;stats.move();} }
        else { Node prev=nodeAt(index-1); node.next=prev.next;stats.move();prev.next=node;stats.move();if(index==size){tail=node;stats.move();} }
        size++;
    }
    @Override public int remove(int index) {
        checkElement(index); Node removed;
        if(index==0){removed=head;head=head.next;stats.move();if(size==1){tail=null;stats.move();}}
        else {Node prev=nodeAt(index-1);removed=prev.next;prev.next=removed.next;stats.move();if(index==size-1){tail=prev;stats.move();}}
        size--;return removed.value;
    }
    @Override public int get(int index) { checkElement(index); return nodeAt(index).value; }
    @Override public boolean contains(int value) {
        Node current=head;
        while(current!=null){stats.step();stats.comparison();if(current.value==value)return true;current=current.next;}
        return false;
    }
    private Node nodeAt(int index){Node current=head;for(int i=0;i<index;i++){stats.step();current=current.next;}return current;}
    private void checkElement(int index){if(index<0||index>=size)throw new IndexOutOfBoundsException("index="+index+", size="+size);}
    private void checkPosition(int index){if(index<0||index>size)throw new IndexOutOfBoundsException("index="+index+", size="+size);}
    @Override public int size(){return size;}
    @Override public OperationStats stats(){return stats;}
    @Override public void resetStats(){stats.reset();}
}
