/**
 * Class DoubleTrouble finds the smallest number of steps out of a maze for two players
 * moving in identical directions. It takes two sections of inputs <I>a b</I> and the maze
 * (which can look like: <I>xx.xxx.1</I><I>xx.xxx.2</I><I>...xxx.x</I>).
 * It outputs the smallest number of moves using a modified BFS.
 * <P>
 * Usage: java Prerequisites < Input File
 *
 * @author  Joshua Sellers
 * @collaborator Jacob Cozzarin
 * @version 22-Nov-2017
 */

import java.util.Scanner;

public class DoubleTrouble {
    /**
     * Class Tuple contains the coordinates of a player
     */
    public static class Tuple{
        private int x;
        private int y;
        /**
         * Inputs the coordinates
         *
         * @param x      x coordinates
         * @param y      y coordinates
         */
        public Tuple(int x, int y){
            this.x = x;
            this.y = y;
        }
        public int getX(){return x;}
        public int getY(){return y;}
    }

    /**
     * Class Position has the position of the two players
     */
    public static class Position{
        private Tuple one;
        private Tuple two;
        private int steps;
        /**
         * Inputs the two players data
         *
         * @param one      player one tuple
         * @param two      player two tuple
         * @param steps    current number of moves made
         */
        public Position(Tuple one, Tuple two, int steps){
            this.one = one;
            this.two = two;
            this.steps =steps;
        }
        public Tuple getOne(){return this.one;}
        public Tuple getTwo(){return this.two;}
        public int getSteps(){return this.steps;}
    }

    /**
     * Runs a modified BFS on the two players to find the minimum number of
     * steps to exit the maze.
     *
     * @param maze      representation of maze
     * @param a         y dimension of maze
     * @param b         x dimension of maze
     * @param aList     adjacency list
     */
    public static void path(String[][] maze,int a, int b, String[][][] aList){
        int x1=0;
        int x2=0;
        int y1=0;
        int y2=0;
        // get starting positions
        for (int i = 0; i < a; i++){
            for (int j = 0; j < b; j++){
                if (maze[i][j].equals("1")){
                    x1 = j;
                    y1 = i;
                }
                else if (maze[i][j].equals("2")){
                    x2 = j;
                    y2 = i;
                }
                else continue;
            }
        }
        Tuple start1 = new Tuple(x1,y1);
        Tuple start2 = new Tuple(x2,y2);
        Position startingPosition = new Position(start1,start2,0);
        boolean[][][][] spots = new boolean[b][a][b][a];
        // set starting values
        spots[start1.getX()][start1.getY()][start2.getX()][start2.getY()] = true;
        Position[] Queue = new Position[a*a*b*b];
        Queue[0] = startingPosition;
        int beg = 0;
        int end = 1;
        int steps = 0;
        int timer = a*a*b*b;
        while (beg < end && timer > 0) {
            Position head = Queue[beg];
            // run through possible moves
            for (int i = 0; i < 4; i++){
                // both players exit
                if (aList[head.getOne().getY()][head.getOne().getX()][i].equals("OUT") &&
                        aList[head.getTwo().getY()][head.getTwo().getX()][i].equals("OUT")){
                    if (steps>0) steps = Math.min(steps, head.getSteps()+1);
                    else steps = head.getSteps()+1;
                }
                // one player can exit and another cannot
                else if (aList[head.getOne().getY()][head.getOne().getX()][i].equals("OUT") &&
                        !aList[head.getTwo().getY()][head.getTwo().getX()][i].equals("OUT")){
                    continue;
                }
                // one player can exit and another cannot
                else if (!aList[head.getOne().getY()][head.getOne().getX()][i].equals("OUT") &&
                        aList[head.getTwo().getY()][head.getTwo().getX()][i].equals("OUT")){
                    continue;
                }
                else {
                    Tuple new1;
                    Tuple new2;
                    // move down
                    if (i == 0) {
                        // checks if move is possible
                        if (head.getOne().getY()+1==head.getTwo().getY() && head.getOne().getX() == head.getTwo().getX()){
                            if(aList[head.getTwo().getY()][head.getTwo().getX()][i].equals(".")) new1 = new Tuple(head.getOne().getX(),head.getOne().getY()+1);
                            else new1 = new Tuple(head.getOne().getX(),head.getOne().getY());
                        }
                        else if (aList[head.getOne().getY()][head.getOne().getX()][i].equals(".")) {
                            new1 = new Tuple(head.getOne().getX(),head.getOne().getY()+1);
                        }
                        else{
                            new1 = new Tuple(head.getOne().getX(),head.getOne().getY());
                        }
                        if (head.getOne().getY()==head.getTwo().getY()+1 && head.getOne().getX() == head.getTwo().getX()){
                            if(aList[head.getOne().getY()][head.getOne().getX()][i].equals(".")) {
                                new2 = new Tuple(head.getTwo().getX(), head.getTwo().getY() + 1);
                            }
                            else new2 = new Tuple(head.getTwo().getX(),head.getTwo().getY());
                        }
                        else if (aList[head.getTwo().getY()][head.getTwo().getX()][i].equals(".")) {
                            new2 = new Tuple(head.getTwo().getX(),head.getTwo().getY()+1);
                        }
                        else{
                            new2 = new Tuple(head.getTwo().getX(),head.getTwo().getY());
                        }
                    }
                    // move right
                    else if (i == 1){
                        // checks if move is possible
                        if (head.getOne().getY()==head.getTwo().getY() && head.getOne().getX()+1 == head.getTwo().getX()){
                            if(aList[head.getTwo().getY()][head.getTwo().getX()][i].equals(".")) {
                                new1 = new Tuple(head.getOne().getX() + 1, head.getOne().getY());
                            }
                            else new1 = new Tuple(head.getOne().getX(),head.getOne().getY());
                        }
                        else if (aList[head.getOne().getY()][head.getOne().getX()][i].equals(".")) {
                            new1 = new Tuple(head.getOne().getX()+1,head.getOne().getY());
                        }
                        else{
                            new1 = new Tuple(head.getOne().getX(),head.getOne().getY());
                        }
                        if (head.getOne().getY()==head.getTwo().getY() && head.getOne().getX() == 1+head.getTwo().getX()){
                            if(aList[head.getOne().getY()][head.getOne().getX()][i].equals(".")) {
                                new2 = new Tuple(head.getTwo().getX() + 1, head.getTwo().getY());
                            }
                            else new2 = new Tuple(head.getTwo().getX(),head.getTwo().getY());
                        }
                        else if (aList[head.getTwo().getY()][head.getTwo().getX()][i].equals(".")) {
                            new2 = new Tuple(head.getTwo().getX()+1,head.getTwo().getY());
                        }
                        else{
                            new2 = new Tuple(head.getTwo().getX(),head.getTwo().getY());
                        }
                    }
                    // move up
                    else if (i == 2){
                        // checks if move is possible
                        if (head.getOne().getY()-1==head.getTwo().getY() && head.getOne().getX() == head.getTwo().getX()){
                            if(aList[head.getTwo().getY()][head.getTwo().getX()][i].equals(".")) {
                                new1 = new Tuple(head.getOne().getX(), head.getOne().getY() - 1);
                            }
                            else new1 = new Tuple(head.getOne().getX(),head.getOne().getY());
                        }
                        else if (aList[head.getOne().getY()][head.getOne().getX()][i].equals(".")) {
                            new1 = new Tuple(head.getOne().getX(),head.getOne().getY()-1);
                        }
                        else{
                            new1 = new Tuple(head.getOne().getX(),head.getOne().getY());
                        }
                        if (head.getOne().getY()==head.getTwo().getY()-1 && head.getOne().getX() == head.getTwo().getX()){
                            if(aList[head.getOne().getY()][head.getOne().getX()][i].equals(".")) {
                                new2 = new Tuple(head.getTwo().getX(), head.getTwo().getY() - 1);
                            }
                            else new2 = new Tuple(head.getTwo().getX(),head.getTwo().getY());
                        }
                        else if (aList[head.getTwo().getY()][head.getTwo().getX()][i].equals(".")) {
                            new2 = new Tuple(head.getTwo().getX(),head.getTwo().getY()-1);
                        }
                        else{
                            new2 = new Tuple(head.getTwo().getX(),head.getTwo().getY());
                        }
                    }
                    // move right
                    else {
                        // checks if move is possible
                        if (head.getOne().getY()==head.getTwo().getY() && head.getOne().getX()-1 == head.getTwo().getX()){
                            if(aList[head.getTwo().getY()][head.getTwo().getX()][i].equals(".")) {
                                new1 = new Tuple(head.getOne().getX() - 1, head.getOne().getY());
                            }
                            else new1 = new Tuple(head.getOne().getX(),head.getOne().getY());
                        }
                        else if (aList[head.getOne().getY()][head.getOne().getX()][i].equals(".")) {
                            new1 = new Tuple(head.getOne().getX()-1,head.getOne().getY());
                        }
                        else{
                            new1 = new Tuple(head.getOne().getX(),head.getOne().getY());
                        }
                        if (head.getOne().getY()==head.getTwo().getY() && head.getOne().getX() == head.getTwo().getX()-1){
                            if(aList[head.getOne().getY()][head.getOne().getX()][i].equals(".")) {
                                new2 = new Tuple(head.getTwo().getX() - 1, head.getTwo().getY());
                            }
                            else new2 = new Tuple(head.getTwo().getX(),head.getTwo().getY());
                        }
                        else if (aList[head.getTwo().getY()][head.getTwo().getX()][i].equals(".")) {
                            new2 = new Tuple(head.getTwo().getX()-1,head.getTwo().getY());
                        }
                        else{
                            new2 = new Tuple(head.getTwo().getX(),head.getTwo().getY());
                        }
                    }
                    // if not seen, adds position
                    Position newP = new Position(new1, new2, head.getSteps() + 1);
                    if (!spots[newP.getOne().getX()][newP.getOne().getY()][newP.getTwo().getX()][newP.getTwo().getY()]){
                        Queue[end] = newP;
                        spots[newP.getOne().getX()][newP.getOne().getY()][newP.getTwo().getX()][newP.getTwo().getY()] = true;
                        end++;
                    }

                }
            }
            beg++;
            timer--;
            if (steps > 0) break;
        }
        // print number of moves
        if (steps!=0) System.out.println(steps);
        // print stuck if no way out
        else System.out.println("STUCK");
    }

    /**
     * Main program. Gets inputs,
     * then outputs the solution for the DoubleTrouble
     * algorithm based on modified BFS sort.
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        String[][] maze = new String[a][];
        String line = sc.nextLine();
        // read in maze
        for (int i = 0; i < a; i++) {
            // Takes in the lines as a string
            line = sc.nextLine();
            String[] c = line.split("");
            maze[i] = c;
        }
        // create adjacency list
        String[][][] aList = new String[a][b][4];
        for (int i = 0; i < a; i++){
            for (int j = 0; j < b; j++){
                if (!maze[i][j].equals("x")) {
                    int count = 0;
                    if (i+1<a){
                        if (maze[i+1][j].equals("1") || maze[i+1][j].equals("2")) aList[i][j][count] = ".";
                        else aList[i][j][count] = maze[i+1][j];
                        count++;
                    }
                    else if (i+1==a) {
                        aList[i][j][count] = "OUT";
                        count++;
                    }
                    if (j+1<b) {
                        if (maze[i][j+1].equals("1") || maze[i][j+1].equals("2")) aList[i][j][count] = ".";
                        else aList[i][j][count] = maze[i][j+1];
                        count++;
                    }
                    else if (j+1==b) {
                        aList[i][j][count] = "OUT";
                        count++;
                    }
                    if (i-1>=0) {
                        if (maze[i-1][j].equals("1") || maze[i-1][j].equals("2")) aList[i][j][count] = ".";
                        else aList[i][j][count] = maze[i-1][j];
                        count++;
                    }
                    else if (i-1 < 0) {
                        aList[i][j][count] = "OUT";
                        count++;
                    }
                    if (j-1>=0) {
                        if (maze[i][j-1].equals("1") || maze[i][j-1].equals("2")) aList[i][j][count] = ".";
                        else aList[i][j][count] = maze[i][j-1];
                    }
                    else if (j-1 < 0) {
                        aList[i][j][count] = "OUT";
                    }

                }
            }
        }
        path(maze,a,b,aList);
    }
}
