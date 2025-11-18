package com.coskun.tr.projects.mazeproj;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

enum Operation{
    TOP,
    RIGHT,
    BOTTOM,
    LEFT
}


public class ArdaCoskun_project1 {
    public static void main(String[] args) {

        char[][] maze;

        try{
            maze = mazeReader("src/com/coskun/tr/projects/mazeproj/mazes/maze.txt");
        }
        catch (IOException exception){
            System.out.println("Error loading the file: " + exception);
            return;
        }
        catch (IllegalArgumentException exception){
            System.out.println("Error getting data from the file: " + exception);
            return;
        }

        Point[] coordinates = solveAndGetCoordinates(maze);
        String output = convertCoordinatesToOutput(coordinates);

        System.out.println(output);
    }
    static Point[] solveAndGetCoordinates(char[][] maze) {
        Stack<Point> coordinateStack = new Stack<>();

        int rowCount = maze.length;
        int columnCount = maze[0].length;

        for (int i = 0; i < columnCount; i++){
            if (maze[0][i] == '0') {
                coordinateStack.push(new Point(i, 0, Operation.BOTTOM));
                break;
            }
        }

        int x = coordinateStack.top().x, y = 0;

        Operation nextOperation = Operation.BOTTOM;
        boolean zeroFound = false, continueLoop = true, returned = false;

        while(y < rowCount - 1 && y >= 0 && x < columnCount - 1 && x > 0) {
            Point top = coordinateStack.top();

            boolean isZeroTop =
                        y - 1 >= 0 &&
                        maze[y - 1][x] == '0' &&
                        !top.equals(new Point(x, y - 1));

            boolean isZeroLeft =
                        maze[y][x - 1] == '0' &&
                        !top.equals(new Point(x - 1, y));

            boolean isZeroBottom =
                        y + 1 < rowCount &&
                        maze[y + 1][x] == '0' &&
                        !top.equals(new Point(x, y + 1));

            boolean isZeroRight =
                        x + 1 < columnCount &&
                        maze[y][x + 1] == '0' &&
                        !top.equals(new Point(x + 1, y));

            top.setMovables(isZeroTop, isZeroLeft, isZeroBottom, isZeroRight);

            if(returned){
                if(top.operation == Operation.TOP)
                    top.isMovableTop = false;

                else if(top.operation == Operation.LEFT)
                    top.isMovableLeft = false;

                else if(top.operation == Operation.BOTTOM)
                    top.isMovableBottom = false;

                else if(top.operation == Operation.RIGHT)
                    top.isMovableRight = false;
            }
            returned = false;

            while(!zeroFound && continueLoop){
                switch(nextOperation){
                    case TOP -> {
                        if(isZeroTop && top.isMovableTop) {
                            coordinateStack.push(new Point(x, --y, Operation.TOP));
                            nextOperation = Operation.LEFT;
                            top.isMovableTop = false;
                            zeroFound = true;
                        }

                        else if(isZeroLeft && top.operation != Operation.RIGHT)
                            nextOperation = Operation.LEFT;

                        else if(isZeroRight && top.operation != Operation.LEFT)
                            nextOperation = Operation.RIGHT;

                        else if(top.operation == Operation.RIGHT)
                            nextOperation = Operation.BOTTOM;

                        else
                            continueLoop = false;
                    }

                    case LEFT -> {
                        if(isZeroLeft && top.isMovableLeft){
                            coordinateStack.push(new Point(--x, y, Operation.LEFT));
                            nextOperation = Operation.BOTTOM;
                            top.isMovableLeft = false;
                            zeroFound = true;
                        }

                        else if(isZeroBottom && top.operation != Operation.TOP)
                            nextOperation = Operation.BOTTOM;

                        else if(isZeroTop && top.operation != Operation.BOTTOM)
                            nextOperation = Operation.TOP;

                        else if(top.operation == Operation.TOP)
                            nextOperation = Operation.RIGHT;

                        else
                            continueLoop = false;

                    }

                    case BOTTOM -> {
                        if(isZeroBottom && top.isMovableBottom){
                            coordinateStack.push(new Point(x, ++y, Operation.BOTTOM));
                            nextOperation = Operation.RIGHT;
                            top.isMovableBottom = false;
                            zeroFound = true;
                        }

                        else if(isZeroRight && top.operation != Operation.LEFT)
                            nextOperation = Operation.RIGHT;

                        else if(isZeroLeft && top.operation != Operation.RIGHT)
                            nextOperation = Operation.LEFT;

                        else if(top.operation == Operation.LEFT)
                            nextOperation = Operation.TOP;

                        else
                            continueLoop = false;
                    }

                    case RIGHT -> {
                        if(isZeroRight && top.isMovableRight){
                            coordinateStack.push(new Point(++x, y, Operation.RIGHT));
                            nextOperation = Operation.TOP;
                            top.isMovableRight = false;
                            zeroFound = true;
                        }

                        else if(isZeroTop && top.operation != Operation.BOTTOM)
                            nextOperation = Operation.TOP;

                        else if(isZeroBottom && top.operation != Operation.TOP)
                            nextOperation = Operation.BOTTOM;

                        else if(top.operation == Operation.BOTTOM)
                            nextOperation = Operation.LEFT;

                        else
                            continueLoop = false;
                    }
                }
            }

            if(!zeroFound){

                while(!coordinateStack.isEmpty() && coordinateStack.top().countMovables() < 1)
                    coordinateStack.pop();

                if(coordinateStack.top().operation == Operation.TOP)
                    nextOperation = Operation.LEFT;

                else if(coordinateStack.top().operation == Operation.LEFT)
                    nextOperation = Operation.BOTTOM;

                else if(coordinateStack.top().operation == Operation.BOTTOM)
                    nextOperation = Operation.RIGHT;

                else
                    nextOperation = Operation.TOP;

                Point coordinate = coordinateStack.top();
                x = coordinate.x;
                y = coordinate.y;

                returned = true;
            }
            zeroFound = false;
            continueLoop = true;
        }

        int coordinateSize = coordinateStack.size();

        Point[] coordinates = new Point[coordinateSize];

        for(int i = coordinateSize - 1; i >= 0; i--){
            coordinates[i] = coordinateStack.pop();
        }

        return coordinates;
    }

    static String convertCoordinatesToOutput(Point[] coordinates){
        StringBuilder stringBuilder = new StringBuilder();

        for(Point coordinate : coordinates){
            stringBuilder.append("(").append(coordinate.x).append(", ").append(coordinate.y).append(")").append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        return stringBuilder.toString();
    }

    static char[][] mazeReader(String filePath) throws IOException {

        String[] rows = Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8).toArray(new String[0]);

        if(rows.length == 0)
            throw new IllegalArgumentException("The file is empty!");


        int rowCount = rows.length;
        int columnCount = rows[0].length();

        char[][] maze = new char[rowCount][columnCount];

        for(int i = 0; i < rowCount; i++)
            maze[i] = rows[i].toCharArray();

        return maze;
    }
}

class Node<T>{
    T data;
    Node<T> next;

    Node(T data){
        this.data = data;
        this.next = null;
    }
}

class Stack<T>{
    private Node<T> head;

    void push(T data){
        Node<T> dataNode = new Node<>(data);
        dataNode.next = head;
        head = dataNode;
    }

    T pop(){
        T data = head.data;
        head = head.next;
        return data;
    }

    T top(){
        return head.data;
    }

    int size(){
        Node<T> temp = head;
        int size = 0;

        while(temp != null){
            size++;
            temp = temp.next;
        }

        return size;
    }

    boolean isEmpty(){
        return head == null;
    }
}

class Point{
    int x;
    int y;
    Operation operation;
    boolean isMovableTop;
    boolean isMovableLeft;
    boolean isMovableBottom;
    boolean isMovableRight;

    Point(int x, int y, Operation operation){
        this.x = x;
        this.y = y;
        this.operation = operation;
    }

    Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setMovables(boolean isZeroTop, boolean isZeroLeft, boolean isZeroBottom, boolean isZeroRight) {
        isMovableTop = isZeroTop;
        isMovableLeft = isZeroLeft;
        isMovableBottom = isZeroBottom;
        isMovableRight = isZeroRight;
    }

    public int countMovables(){
        int movable = 0;
        if(isMovableTop) movable++;
        if(isMovableLeft) movable++;
        if(isMovableBottom) movable++;
        if(isMovableRight) movable++;

        return movable;
    }

    public boolean equals(Point point){
        return (this.x == point.x && this.y == point.y);
    }

}
