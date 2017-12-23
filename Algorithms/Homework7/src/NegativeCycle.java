/**
 * Class NegativeCycle checks if a weighted, directed graph with one
 * negative edge has a negative cycle.
 * The class reads in the the number of vertices and edges on the first line
 * and then the edges <I>u v weight</I> on the remaining lines.
 * <P>
 * Usage: <TT>java NegativeCycle < input_file</TT>
 *
 * @author  Josh Sellers
 * @collaborator Jacob Cozzarin (jpc4394)
 * @version 10-Dec-2017
 */


import java.util.Scanner;

public class NegativeCycle {

    /**
     * Checks if there is a negative cycle in a weighted directed
     * graph with one negative edge.  Uses dijkstra's algorithm.
     *
     * @param  aList      adjacency list
     * @param  start      starting vertex
     * @param  finish     finishing vertex
     * @param  w          negative weight
     */
    public static String ncylce(Tuple[][] aList, int start, int finish, int w){
        // distances
        int[] d = new int[aList.length];
        // visited vertices
        boolean[] seen = new boolean[aList.length];
        // queue
        int[] Q = new int[aList.length*aList.length];
        // initialize queue
        Q[0] = start;
        int beg = 0;
        int end = 1;
        // initialize distances
        for (int i = 0; i < d.length; i++){
            d[i] = -1;
        }
        d[start] = 0;
        // go through elements in the queue
        while (beg < end){
            int head = Q[beg];
            // go through neighboring vertices
            for (int i = 0; i < aList[head].length; i++) {
                // skip negative weight
                if (aList[head][i].weight < 0){
                    continue;
                }
                // otherwise add weights
                else if (d[aList[head][i].edge] == -1) {
                    Q[end] = aList[head][i].edge;
                    d[aList[head][i].edge] = d[head]+aList[head][i].weight;
                    end++;
                }
                else if (d[aList[head][i].edge] > d[head]+aList[head][i].weight){
                    Q[end] = aList[head][i].edge;
                    d[aList[head][i].edge] = d[head]+aList[head][i].weight;
                    end++;
                }
            }
            seen[head] = true;
            beg++;
        }
        // check minimum distance to finishing node against negative weight
        int distance = d[finish];
        if (distance+w < 0) return "YES";
        else return "NO";
    }

    public static class Tuple{
        private int edge;
        private int weight;
        public Tuple(int edge, int weight){
            this.edge = edge;
            this.weight = weight;
        }

        public int getEdge(){return this.edge;}
        public int getWeight(){return this.weight;}
    }


    /**
     * Main program. Gets inputs,
     * then outputs the solution for the NegativeCycle
     * algorithm based on dijkstra's algorithm.
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int v = sc.nextInt();
        int e = sc.nextInt();
        int[] count = new int[v];
        String[] lines = new String[e];
        Tuple[][] aList = new Tuple[v][];
        sc.nextLine();
        for (int i = 0; i < e; i++) {
            // Takes in the lines as a string
            String ll = sc.nextLine();
            lines[i] = ll;
        }
        // get size of adjacency list
        for (int i = 0; i < lines.length; i++){
            String[] set = lines[i].split(" ");
            count[Integer.parseInt(set[0])-1]++;
        }
        // create adjacency lists
        for (int i = 0; i < v; i++){
            aList[i] = new Tuple[count[i]];
        }
        int counter = 0;
        int start = 0;
        int finish = 0;
        int w = 0;
        // populate adjacency list
        while (counter < e){
            String[] l = lines[counter].split(" ");
            if (Integer.parseInt(l[2])<0) {
                start = Integer.parseInt(l[1])-1;
                finish = Integer.parseInt(l[0])-1;
                w = Integer.parseInt(l[2]);
            }
            aList[Integer.parseInt(l[0])-1][count[Integer.parseInt(l[0])-1]-1] = new Tuple(Integer.parseInt(l[1])-1,Integer.parseInt(l[2]));
            count[Integer.parseInt(l[0])-1]--;
            counter++;
        }
        // call ncylce
        System.out.println(ncylce(aList,start, finish,w));
    }
}
