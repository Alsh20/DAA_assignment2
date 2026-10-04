package edu.assignment2.benchmark;

import edu.assignment2.structures.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public final class BenchmarkRunner {
 private static final int[] SIZES={100,1000,10000,100000}; private static final int RUNS=5;
 private record Row(String workload,String variant,String structure,int n,double ms,long steps,long moves,long comparisons){}
 private record Sample(double ns,long steps,long moves,long comparisons){}
 private interface Case { Sample run(int n,int seed); }
 public static void main(String[] args)throws Exception {
  List<Row> rows=new ArrayList<>();
  for(int n:SIZES){
   for(String kind:new String[]{"DynamicArray","MyLinkedList"}){
    rows.add(measure("W1","-",kind,n,(size,seed)->w1(kind,size,seed)));
    rows.add(measure("W2","-",kind,n,(size,seed)->w2(kind,size,seed)));
    for(String variant:new String[]{"head","middle"}) rows.add(measure("W3",variant,kind,n,(size,seed)->w3(kind,size,seed,variant)));
   }
   rows.add(measure("W4","-","MinHeap",n,(size,seed)->w4(size,seed)));
  }
  Path output=Path.of("results","results.csv");Files.createDirectories(output.getParent());
  try(BufferedWriter w=Files.newBufferedWriter(output)){w.write("workload,variant,structure,n,time_ms,steps,moves,comparisons\n");for(Row r:rows)w.write(String.format(Locale.ROOT,"%s,%s,%s,%d,%.6f,%d,%d,%d%n",r.workload,r.variant,r.structure,r.n,r.ms,r.steps,r.moves,r.comparisons));}
  System.out.println("Wrote "+output.toAbsolutePath()+" ("+rows.size()+" cases)");
 }
 private static Row measure(String workload,String variant,String structure,int n,Case c){c.run(n,42);Sample[] a=new Sample[RUNS];for(int i=0;i<RUNS;i++)a[i]=c.run(n,42);return new Row(workload,variant,structure,n,medianNs(a)/1_000_000.0,median(a,true),median(a,false),median(a,null));}
 private static double medianNs(Sample[] a){double[] x=new double[a.length];for(int i=0;i<a.length;i++)x[i]=a[i].ns;Arrays.sort(x);return x[x.length/2];}
 private static long median(Sample[] a,Boolean steps){long[] x=new long[a.length];for(int i=0;i<a.length;i++)x[i]=steps==null?a[i].comparisons:steps?a[i].steps:a[i].moves;Arrays.sort(x);return x[x.length/2];}
 private static Sample done(long start,OperationStats s){return new Sample(System.nanoTime()-start,s.steps(),s.moves(),s.comparisons());}
 private static IntSequence sequence(String k){return k.equals("DynamicArray")?new DynamicArray():new MyLinkedList();}
 private static int[] dataset(int n){int[] values=new int[n];Random r=new Random(42);for(int i=0;i<n;i++)values[i]=r.nextInt();return values;}
 private static void fill(IntSequence s,int[] values){for(int value:values)s.add(value);}
 private static boolean has(int[] values,int target){for(int value:values)if(value==target)return true;return false;}
 private static Sample w1(String k,int n,int seed){IntSequence s=sequence(k);fill(s,dataset(n));s.resetStats();Random r=new Random(seed);long t=System.nanoTime();for(int i=0;i<10000;i++)s.get(r.nextInt(n));return done(t,s.stats());}
 private static Sample w2(String k,int n,int seed){int[] values=dataset(n);IntSequence s=sequence(k);fill(s,values);s.resetStats();Random r=new Random(seed);int[] q=new int[1000];for(int i=0;i<500;i++)q[i]=values[r.nextInt(n)];for(int i=500;i<1000;i++){int absent=Integer.MIN_VALUE+i;while(has(values,absent))absent++;q[i]=absent;}for(int i=q.length-1;i>0;i--){int j=r.nextInt(i+1),v=q[i];q[i]=q[j];q[j]=v;}long t=System.nanoTime();for(int v:q)s.contains(v);return done(t,s.stats());}
 private static Sample w3(String k,int n,int seed,String variant){IntSequence s=sequence(k);fill(s,dataset(n));s.resetStats();Random r=new Random(seed);long t=System.nanoTime();for(int i=0;i<1000;i++){int at=variant.equals("head")?0:s.size()/2;s.add(at,-r.nextInt(100000)-1);}for(int i=0;i<1000;i++){int at=variant.equals("head")?0:s.size()/2;s.remove(at);}return done(t,s.stats());}
 private static Sample w4(int n,int seed){MinHeap h=new MinHeap();Random r=new Random(seed);int[] input=dataset(n);h.resetStats();long t=System.nanoTime();for(int v:input)h.insert(v);int previous=Integer.MIN_VALUE;for(int i=0;i<n;i++){int v=h.extractMin();if(v<previous)throw new IllegalStateException("heap output is not sorted");previous=v;}return done(t,h.stats());}
}

