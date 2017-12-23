/**
 * File: SpanTree.java
 * @author Jacob Cozzarin
 * @collaborator Josh Sellers
 * @date 11/20/17
 * @description:
 *      Input specification: the first line contains n and m (the number of vertices and edges of the graph)
 *      . The vertices are labeled with numbers 1, 2, ..., n. The first line is followed by m lines, each
 *      of these lines describes an edge. The i-th of these lines contains four numbers. The first two
 *      numbers specify the end-point vertices, the third number is the weight and the fourth number is
 *      either 0 or 1 (1 means the edge belongs to F, 0 means the edge is not in F). You may assume that n
 *      is not bigger than 10000 and the weights fit in double.
 *
 *      Output specification: the output contains one line with either the cost of a minimum-cost
 *      F-containing spanning tree, or -1 if no such spanning tree exists. 
 */

import java.util.Scanner;

public class SpanTree {
    /**
     * Class Edge contains the information for an edge
     */
    public static class Edge {
        public int weight;
        public int startPoint;
        public int endPoint;
        public boolean inF;
        /**
         * Inputs the coordinates
         *
         * @param weight          weight
         * @param startPoint      starting point
         * @param endPoint        ending point
         * @param inF             is the edge infinite
         */
        public Edge(int weight, int startPoint, int endPoint, boolean inF) {
            this.weight = weight;
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            this.inF = inF;
        }
    }

    /**
     * Runs merge sort on the weighted edges
     *
     * @param edges      list of edges
     * @param l          left point
     * @param r          right point
     */
    public static void edgeMergeSort(Edge[] edges, int l, int r) {
        // check if l is smaller than r, if not then the array is sorted
        if (l < r) {
            // Get the index of the element which is in the middle
            int middle = l + (r - l) / 2;
            // Sort the left side of the array
            edgeMergeSort(edges, l, middle);
            // Sort the right side of the array
            edgeMergeSort(edges, middle + 1, r);
            // Combine them both
            edgeMerge(edges, l, middle, r);
        }
    }

    /**
     * Merge the edges from the merge sort
     *
     * @param edges      list of edges
     * @param l          left point
     * @param m          middle point
     * @param r          right point
     */
    public static void edgeMerge(Edge[] edges, int l, int m, int r) {
        int i, j, k;
        int n1 = m - l + 1;
        int n2 =  r - m;
        // left and right arrays
        Edge[] L = new Edge[n1];
        Edge[] R = new Edge[n2];
        // populate the arrays
        for (i = 0; i < n1; i++)
            L[i] = edges[l + i];
        for (j = 0; j < n2; j++)
            R[j] = edges[m + 1+ j];
        // set indices
        i = 0;
        j = 0;
        k = l;
        while (i < n1 && j < n2) {
            if (L[i].weight <= R[j].weight) {
                edges[k] = L[i];
                i++;
            }
            else {
                edges[k] = R[j];
                j++;
            }
            k++;
        }
        // finish merging
        while (i < n1) {
            edges[k] = L[i];
            i++;
            k++;
        }
        // finish merging
        while (j < n2) {
            edges[k] = R[j];
            j++;
            k++;
        }
    }

    /**
     * Checks for a cycle in the the graph
     *
     * @param edgeList   list of edges
     * @param edgeSize   edge size
     * @param n          number of points
     */
    public static boolean checkForCycle(Edge[] edgeList, int edgeSize, int n) {
        // Mark all the vertices as not visited and not part of
        // recursion stack
        boolean visited[] = new boolean[n];
        for (int i = 0; i < n; i++)
            visited[i] = false;
        // Call the recursive helper function to detect cycle in
        // different DFS trees
        for (int i = 0; i < n; i++)
            if (!visited[i]) { // Don't recur for i if already visited
                if (isCyclicUtil(i, visited, edgeList, edgeSize, -1)) {
                    return true;
                }
            }
        return false;
    }

    /**
     * Runs through the edges and searches them for the tree
     *
     * @param v                vertex
     * @param visited          visited array
     * @param edgeList         edges in graph
     * @param edgeSize         size of the edge
     * @param parent           parent vertex
     */
    public static boolean isCyclicUtil(int v, boolean visited[], Edge[] edgeList, int edgeSize, int parent)
    {
        // Mark the current node as visited
        visited[v] = true;
        int[] adjList = new int[edgeSize+1];
        int aIndex = 0;
        // Find all neighbors of the current node
        for (int i = 0; i < edgeSize+1; i++) {
            if (edgeList[i].startPoint == v+1) {
                adjList[aIndex] = edgeList[i].endPoint;
                aIndex++;
            }
            else if (edgeList[i].endPoint == v+1) {
                adjList[aIndex] = edgeList[i].startPoint;
                aIndex++;
            }
        }
        // Recur for all the vertices adjacent to this vertex
        for (int i = 0; i < aIndex; i++) {
            // If an adjacent is not visited, then recur for that
            // adjacent
            if (!visited[adjList[i]-1]) {
                if (isCyclicUtil(adjList[i]-1, visited, edgeList, edgeSize, v))
                    return true;
            }
            // If an adjacent is visited and not parent of current
            // vertex, then there is a cycle.
            else if (adjList[i]-1 != parent)
                return true;
        }
        return false;
    }

    /**
     * Main program. Gets inputs,
     * then outputs the solution for the SpanTree
     * finding the size of the tree.
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        Edge[] edgeList = new Edge[m];
        int lstIndex = 0;
        Edge[] FEdges = new Edge[m];
        int fIndex = 0;
        Edge[] solution = new Edge[n-1];
        int sIndex = 0;
        Edge e;
        int weight = 0;
        int startpoint = 0;
        int endpoint = 0;
        int isF = 0;
        boolean inF = false;
        // get values
        for (int i = 0; i < m; i++) {
            startpoint = sc.nextInt();
            endpoint = sc.nextInt();
            weight = sc.nextInt();
            isF = sc.nextInt();
            if (isF == 0) {
                inF = false;
            }
            else {
                inF = true;
            }

            e = new Edge(weight, startpoint, endpoint, inF);
            if (e.inF) {
                FEdges[fIndex] = e;
                fIndex++;
            }
            else {
                edgeList[lstIndex] = e;
                lstIndex++;
            }
        }

        for (int i = 0; i < fIndex; i++) {
            solution[i] = FEdges[i];
            sIndex++;
        }
        // run the algorithm
        edgeMergeSort(edgeList, 0, lstIndex-1);
        // check for cycles
        boolean containsCycle = false;
        int counter = 0;
        for (int i = 0; i < lstIndex; i++) {
            if (sIndex < n - 1) {
                solution[sIndex] = edgeList[i];
                containsCycle = checkForCycle(solution, sIndex, n);
                if (!containsCycle) {
                    sIndex++;
                    counter++;
                }
            }
        }
        // get size
        int minimumSpanningTree = 0;
        if (sIndex != n-1) {
            minimumSpanningTree = -1;
        }
        else {
            for (int i = 0; i < sIndex; i++) {
                minimumSpanningTree += solution[i].weight;
            }
        }
        System.out.println(minimumSpanningTree);
    }
}

