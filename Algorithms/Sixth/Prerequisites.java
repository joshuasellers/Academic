/**
 * Class Prerequisites finds the longest path in a directed graph.
 * It takes three sections of inputs <I>n</I> and n lines of <I>a a1 ... 0</I>.
 * It outputs the length of the longest path using topological sort.
 * <P>
 * Usage: java Prerequisites < Input File
 *
 * @author  Joshua Sellers
 * @collaborator Jacob Cozzarin
 * @version 22-Nov-2017
 */

import java.util.Scanner;

public class Prerequisites {
    /**
     * Class Tuple contains the current values in the topological ordering
     */
    public static class Tuple{
        private String a;
        private String b;
        /**
         * Runs the shortest path algorithm using BFS
         * and outputs the number of shortest paths
         *
         * @param a      point added
         * @param b      current ordering
         */
        public Tuple(String a, String b){
            this.a = a;
            this.b = b;
        }
        public String getA(){return a;}
        public String getB(){return b;}
    }

    /**
     * Runs DFS algorithm on the directed graph
     *
     * @param seen      seen vertices list
     * @param fin       runtime list
     * @param time      current runtime
     * @param Alist     adjacency list
     * @param i         starting vertex
     * @param o         topographic ordering
     *
     * @return Tuple    ordering and path
     */
    public static Tuple DFS(boolean[] seen, int[] fin, int time, int i, int[][] Alist, String o){
        seen[i] = true;
        for (int j = 0; j < Alist[i].length; j++){
            if (!seen[Alist[i][j]]){
                Tuple x = DFS(seen,fin,time,Alist[i][j],Alist,o);
                // update ordering
                o = x.a+" "+x.b;
            }
        }
        time++;
        fin[i] = time;
        return new Tuple(String.valueOf(i),o);
    }

    /**
     * Finds longest path using topological sort
     *
     * @param Alist     adjacency list
     *
     * @return int      length of path
     */
    public static int preReq(int[][] Alist){
        // seen list
        boolean[] seen = new boolean[Alist.length];
        // end list
        int[] fin = new int[Alist.length];
        String o = "";
        for (int i = 0; i < Alist.length; i++){
            seen[i]=false;
            fin[i]=0;
        }
        int time = 0;
        Tuple t;
        for (int i = 0; i < Alist.length; i++){
            if(!seen[i]){
                // get updated ordering
                t = DFS(seen,fin,time,i,Alist,o);
                // combine with current ordering
                o = String.valueOf(i) +" "+ t.getB();
            }
        }
        String[] order = o.split(" ");
        int[] finalOrder = new int[Alist.length];
        int[] dist = new int[Alist.length];
        for (int i = 0; i < finalOrder.length; i++){
            finalOrder[i] = Integer.parseInt(order[i]);
            dist[i] = 1;
            //System.out.println(order[i]);
        }
        // compute distance by traveling the different possible paths
        int max = 0;
        for (int v = 0; v < finalOrder.length; v++){
            for (int e = 0; e < Alist[finalOrder[v]].length; e++){
                if (dist[Alist[finalOrder[v]][e]] < dist[finalOrder[v]] + 1) {
                    dist[Alist[finalOrder[v]][e]] = dist[finalOrder[v]] + 1;
                    max = Math.max(max, dist[Alist[finalOrder[v]][e]]);
                }
            }
        }
        return max;
    }

    /**
     * Main program. Gets inputs,
     * then outputs the solution for the Perquisites
     * algorithm based on the topological sort.
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        String[] lines = new String[n];
        int[] count = new int[n];
        int[][] aList = new int[n][];
        for (int i = 0; i < n; i++) {
            // Takes in the lines as a string
            String line = sc.nextLine();
            lines[i] = line;
            String[] c = line.split(" ");
            count[i] = c.length-1;
        }
        // create adjacency lists
        for (int i = 0; i < n; i++){
            aList[i] = new int[count[i]];
        }
        int counter = 0;
        // populate adjacency list
        while (counter < n){
            String[] l = lines[counter].split(" ");
            for (int i = 0; i < l.length; i++){
                if (Integer.parseInt(l[i])-1 > 0){
                    aList[counter][i] = Integer.parseInt(l[i])-1;
                }
            }
            counter++;
        }
        System.out.println(preReq(aList));
    }

}
