import java.util.Scanner;
import java.util.StringJoiner;

/**
 * Created by joshuasellers on 12/10/17.
 */
public class NetworkConnect {

    public static class Tuple {
        private int vertex;
        private int capacity;
        private int c_f;
        private int flow;

        public Tuple(int vertex, int capacity, int c_f, int flow, boolean type) {
            this.vertex = vertex;
            this.capacity = capacity;
            if (type) this.c_f = c_f;
            else this.c_f = flow;
            this.flow = flow;
        }

        public int getVertex() {
            return this.vertex;
        }

        public int getCapacity() {
            return this.capacity;
        }

        public int getC_f() {
            return this.c_f;
        }

        public int getFlow() {
            return this.flow;
        }

        public void setFlow(int flow,int other, boolean b) {
            if (b) {
                this.flow = this.flow + flow;
                this.c_f = this.capacity - this.flow;
            }
            else{
                this.flow = this.flow + flow;
                this.c_f = other;
            }
        }
    }


    public static void nconn(Tuple[][] fList, Tuple[][] rList, int s, int t){
        boolean exist = true;
        String[] stPath = new String[fList.length];
        while (exist){
            stPath = new String[fList.length];
            boolean[] seen = new boolean[fList.length];
            // distance counter
            int[] dist = new int[fList.length];
            // queue
            int[] Q = new int[fList.length];
            for (int i = 0; i < dist.length; i++) {dist[i] = 0;}
            for (int i = 0; i < seen.length; i++) {seen[i] = false;}
            for (int i = 0; i < Q.length; i++) {Q[i] = 0;}
            int beg = 0;
            int end = 1;
            // start
            Q[0] = s;
            dist[s] = 0;
            seen[s] = true;
            stPath[s] = String.valueOf(s);
            while (beg < end){
                // dequeue
                int head = Q[beg];
                // check surrounding points
                for (int i = 0; i < fList[head].length+rList[head].length; i++){
                    if (i<fList[head].length) {
                        if (!seen[fList[head][i].getVertex()] && fList[head][i].getC_f()>0) {
                            // update count and distance
                            Q[end] = fList[head][i].getVertex();
                            dist[fList[head][i].getVertex()] = dist[head] + 1;
                            seen[fList[head][i].getVertex()] = true;
                            stPath[fList[head][i].getVertex()] = stPath[head] +"f"+ String.valueOf(fList[head][i].getVertex());
                            end++;
                        }
                        else if (seen[fList[head][i].getVertex()] && fList[head][i].getC_f()>0) {
                            // check if dist needs to be updated
                            if (dist[fList[head][i].getVertex()] > dist[head] + 1) {
                                dist[fList[head][i].getVertex()] = dist[head] + 1;
                                stPath[fList[head][i].getVertex()] = stPath[head] +"f"+ String.valueOf(fList[head][i].getVertex());
                            }
                        }
                    }
                    else {
                        int j = i-fList[head].length;
                        if (!seen[rList[head][j].getVertex()] && rList[head][j].getC_f()>0) {
                            // update count and distance
                            Q[end] = rList[head][j].getVertex();
                            dist[rList[head][j].getVertex()] = dist[head] + 1;
                            seen[rList[head][j].getVertex()] = true;
                            stPath[rList[head][j].getVertex()] = stPath[head] +"r"+ String.valueOf(rList[head][j].getVertex());
                            end++;
                        }
                        else if (seen[rList[head][j].getVertex()] && rList[head][j].getC_f()>0) {
                            // check if dist needs to be updated
                            if (dist[rList[head][j].getVertex()] > dist[head] + 1) {
                                dist[rList[head][j].getVertex()] = dist[head] + 1;
                                stPath[rList[head][j].getVertex()] = stPath[head] +"r"+ String.valueOf(rList[head][j].getVertex());
                            }
                        }
                    }
                }
                beg++;
            }
            if (stPath[t]!=null){
                String[] pt = stPath[t].split("");
                int d = -1;
                for (int i = 2; i < pt.length; i+=2){
                    if (pt[i-1].equals("f")){
                        for (int j = 0; j < fList[Integer.parseInt(pt[i-2])].length; j++) {
                            if (fList[Integer.parseInt(pt[i-2])][j].getVertex()==Integer.parseInt(pt[i])) {
                                if (d == -1) d = fList[Integer.parseInt(pt[i - 2])][j].getC_f();
                                else d = Math.min(d, fList[Integer.parseInt(pt[i - 2])][j].getC_f());
                            }
                        }
                    }
                    else if (pt[i-1].equals("r")){
                        for (int j = 0; j < rList[Integer.parseInt(pt[i-2])].length; j++) {
                            if (rList[Integer.parseInt(pt[i-2])][j].getVertex()==Integer.parseInt(pt[i])) {
                                if (d == -1) d = rList[Integer.parseInt(pt[i - 2])][j].getC_f();
                                else d = Math.min(d, rList[Integer.parseInt(pt[i - 2])][j].getC_f());
                            }
                        }
                    }
                }
                for (int i = 0; i < pt.length-2; i+=2){
                    int other=0;
                    if (pt[i+1].equals("f")){
                        for (int j = 0; j < fList[Integer.parseInt(pt[i])].length; j++){
                            if(fList[Integer.parseInt(pt[i])][j].getVertex() == Integer.parseInt(pt[i+2])){
                                fList[Integer.parseInt(pt[i])][j].setFlow(d,0,true);
                                other = fList[Integer.parseInt(pt[i])][j].getFlow();
                            }
                        }
                        for (int j = 0; j < rList[Integer.parseInt(pt[i+2])].length; j++){
                            if(rList[Integer.parseInt(pt[i+2])][j].getVertex() == Integer.parseInt(pt[i])){
                                rList[Integer.parseInt(pt[i+2])][j].setFlow(d,other,false);
                            }
                        }
                    }
                    else if (pt[i+1].equals("r")){
                        for (int j = 0; j < fList[Integer.parseInt(pt[i])].length; j++){
                            if(fList[Integer.parseInt(pt[i+2])][j].getVertex() == Integer.parseInt(pt[i])){
                                fList[Integer.parseInt(pt[i+2])][j].setFlow(d,0,true);
                                other = fList[Integer.parseInt(pt[i+2])][j].getFlow();
                            }
                        }
                        for (int j = 0; j < rList[Integer.parseInt(pt[i])].length; j++){
                            if(rList[Integer.parseInt(pt[i])][j].getVertex() == Integer.parseInt(pt[i+2])){
                                rList[Integer.parseInt(pt[i])][j].setFlow(-d,other,false);
                            }
                        }
                    }
                }
            }
            else exist=false;
        }
        int count = 0;
        while (stPath[count]!=null){
            String[] Path = new String[fList.length];
            boolean[] seen = new boolean[fList.length];
            // distance counter
            int[] dist = new int[fList.length];
            // queue
            int[] Q = new int[fList.length];
            for (int i = 0; i < dist.length; i++) {dist[i] = 0;}
            for (int i = 0; i < seen.length; i++) {seen[i] = false;}
            for (int i = 0; i < Q.length; i++) {Q[i] = 0;}
            int beg = 0;
            int end = 1;
            // start
            Q[0] = s;
            dist[s] = 0;
            seen[s] = true;
            Path[s] = String.valueOf(s);
            while (beg < end){
                // dequeue
                int head = Q[beg];
                // check surrounding points
                for (int i = 0; i < fList[head].length+rList[head].length; i++){
                    if (i<fList[head].length) {
                        if (!seen[fList[head][i].getVertex()] && fList[head][i].getC_f()>0) {
                            // update count and distance
                            Q[end] = fList[head][i].getVertex();
                            dist[fList[head][i].getVertex()] = dist[head] + 1;
                            seen[fList[head][i].getVertex()] = true;
                            Path[fList[head][i].getVertex()] = Path[head] +"f"+ String.valueOf(fList[head][i].getVertex());
                            end++;
                        }
                        else if (seen[fList[head][i].getVertex()] && fList[head][i].getC_f()>0) {
                            // check if dist needs to be updated
                            if (dist[fList[head][i].getVertex()] > dist[head] + 1) {
                                dist[fList[head][i].getVertex()] = dist[head] + 1;
                                Path[fList[head][i].getVertex()] = Path[head] +"f"+ String.valueOf(fList[head][i].getVertex());
                            }
                        }
                    }
                    else {
                        int j = i-fList[head].length;
                        if (!seen[rList[head][j].getVertex()] && rList[head][j].getC_f()>0) {
                            // update count and distance
                            Q[end] = rList[head][j].getVertex();
                            dist[rList[head][j].getVertex()] = dist[head] + 1;
                            seen[rList[head][j].getVertex()] = true;
                            Path[rList[head][j].getVertex()] = Path[head] +"r"+ String.valueOf(rList[head][j].getVertex());
                            end++;
                        }
                        else if (seen[rList[head][j].getVertex()] && rList[head][j].getC_f()>0) {
                            // check if dist needs to be updated
                            if (dist[rList[head][j].getVertex()] > dist[head] + 1) {
                                dist[rList[head][j].getVertex()] = dist[head] + 1;
                                Path[rList[head][j].getVertex()] = Path[head] +"r"+ String.valueOf(rList[head][j].getVertex());
                            }
                        }
                    }
                }
                beg++;
            }
        }
        /*Initialize flow f to 0
        While exists augm. path p (check with BFS) do
            Augment flow f along p
        Return f*/
    }

    /**
     * Main program. Gets inputs,
     * then outputs the solution for the NegativeCycle
     * algorithm based on dijkstra's algorithm.
     */
    public static void main(String[] args){
        //Scanner sc = new Scanner(System.in);
        int v = 5;//sc.nextInt();
        int e = 7;//sc.nextInt();
        int s = 1;//sc.nextInt()-1;
        int t = 4;//sc.nextInt()-1;
        int[] count1 = new int[v];
        int[] count2 = new int[v];
        String[] lines = {"2 1 4", "2 3 4", "1 4 2", "1 3 1", "3 4 2", "1 5 1", "4 5 6"};//new String[e];
        Tuple[][] fList = new Tuple[v][];
        Tuple[][] rList = new Tuple[v][];
        /*sc.nextLine();
        for (int i = 0; i < e; i++) {
            // Takes in the lines as a string
            String ll = sc.nextLine();
            lines[i] = ll;
        }*/
        // get size of forward/reverse list
        for (int i = 0; i < lines.length; i++){
            String[] set = lines[i].split(" ");
            count1[Integer.parseInt(set[0])-1]++;
            count2[Integer.parseInt(set[1])-1]++;
        }
        // create lists
        for (int i = 0; i < v; i++){
            fList[i] = new Tuple[count1[i]];
            rList[i] = new Tuple[count2[i]];
        }
        int counter = 0;
        // populate lists
        while (counter < e){
            String[] l = lines[counter].split(" ");
            fList[Integer.parseInt(l[0])-1][count1[Integer.parseInt(l[0])-1]-1] = new Tuple(Integer.parseInt(l[1])-1,Integer.parseInt(l[2]),Integer.parseInt(l[2]),0,true);
            count1[Integer.parseInt(l[0])-1]--;
            rList[Integer.parseInt(l[1])-1][count2[Integer.parseInt(l[1])-1]-1] = new Tuple(Integer.parseInt(l[0])-1,Integer.parseInt(l[2]),0,0,false);
            count2[Integer.parseInt(l[1])-1]--;
            counter++;
        }
        // call nconn
        nconn(fList,rList, s,t);
    }
}
