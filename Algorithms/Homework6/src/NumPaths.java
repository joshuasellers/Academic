/**
 * Class NumPaths finds the number of shortest paths in a graph between two vertices.
 * It takes three sections of inputs <I>n m</I>, <I>s t</I> and m lines of <I>a b</I>.
 * It outputs the number of paths.
 * <P>
 * Usage: java NumPaths < Input File
 *
 * @author  Joshua Sellers
 * @collaborator Jacob Cozzarin
 * @version 22-Nov-2017
 */

import java.util.Scanner;
import java.math.*;
import java.io.*;

public class NumPaths {
    /**
     * Runs the shortest path algorithm using BFS
     * and outputs the number of shortest paths
     *
     * @param items      adjacency list
     * @param items      starting vertex
     * @param items      ending vertext
     *
     * @return int      number of paths
     */
    public static int shortPath(int[][] items,int s, int t){
        // vertices checked
        boolean[] seen = new boolean[items.length];
        // distance counter
        int[] dist = new int[items.length];
        // number of paths through a vertex
        int[] count = new int[items.length];
        // queue
        int[] Q = new int[items.length];
        for (int i = 0; i < dist.length; i++) {dist[i] = 0;}
        for (int i = 0; i < seen.length; i++) {seen[i] = false;}
        for (int i = 0; i < Q.length; i++) {Q[i] = 0;}
        for (int i = 0; i < count.length; i++) {count[i] = 0;}
        int beg = 0;
        int end = 1;
        // start
        Q[0] = s;
        dist[s] = 0;
        count[s] = 1;
        seen[s] = true;
        while (beg < end){
            // dequeue
            int head = Q[beg];
            // check surrounding points
            for (int i = 0; i < items[head].length; i++){
                if (!seen[items[head][i]]){
                    // update count and distance
                    Q[end] = items[head][i];
                    dist[items[head][i]] = dist[head]+1;
                    seen[items[head][i]] = true;
                    count[items[head][i]] = count[head];
                    end++;
                }
                else{
                    // check if count and dist need to be updated
                    if (dist[items[head][i]] == dist[head]+1) count[items[head][i]] += count[head];
                    else if (dist[items[head][i]] > dist[head]+1){
                        dist[items[head][i]] = dist[head]+1;
                        count[items[head][i]] = count[head];
                    }
                }
            }
            beg++;
        }
        // number of paths
        return count[t];
    }

    /**
     * Main program. Gets inputs,
     * then outputs the solution for the NumPaths
     * algorithm based on the starting and ending vertices.
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int v = sc.nextInt();
        int e = sc.nextInt();
        int s = sc.nextInt()-1;
        int t = sc.nextInt()-1;
        int[][] items = new int[e][2];
        int[] count = new int[v];
        for (int i = 0; i < e; i++) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            items[i][0] = v1;
            items[i][1] = v2;
        }
        // get adjacency lists' sizes
        for (int i = 0; i < e; i++){
            count[items[i][0]-1]++;
            count[items[i][1]-1]++;
        }
        // create adjacency list
        int[][] aList = new int[v][];
        for (int i = 0; i < v; i++){
            aList[i] = new int[count[i]];
        }
        int counter = 0;
        // populate adjacency list
        while (counter < e){
            aList[items[counter][0]-1][count[items[counter][0]-1]-1] = items[counter][1]-1;
            count[items[counter][0]-1]--;
            aList[items[counter][1]-1][count[items[counter][1]-1]-1] = items[counter][0]-1;
            count[items[counter][1]-1]--;
            counter++;
        }
        System.out.println(shortPath(aList,s,t));
    }
}
