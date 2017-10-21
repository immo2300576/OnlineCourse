Count the inversion number of an unsorted array
- based on merge sort
- inversion number of item i : if item i is originally placed in 27th in the unsorted array, 
  and after sorting item i moves to 4th pos, then the inversion of item i is (27-4)

Needed additional lib: princeton In.java

Usage:
  step1: compile: javac-algs4 CountInversion.java
  step2: run:     java-algs4 CountInversion IntegerArray.txt
  
Input format: IntegerArray.txt(sample test)
#1 line: length of integer array
#2~last line: array content
