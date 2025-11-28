package helpers;

import collections.Queue;
import elements.Point;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class MazeHelper {
    public Point solveAndGetCoordinate(int[][] maze) {
        Queue<Point> coordinateQueue = new Queue<>();

        int rowCount = maze.length;
        int columnCount = maze[0].length;

        for (int i = 0; i < columnCount; i++){
            if (maze[0][i] == 0) {
                coordinateQueue.enqueue(new Point(i, 0, null));
                maze[0][i] = -1;
                break;
            }
        }

        Point currentPoint = coordinateQueue.peek();

        while(!coordinateQueue.isEmpty()) {
            currentPoint = coordinateQueue.dequeue();


            if((currentPoint.y == rowCount - 1 || currentPoint.x == 0 || currentPoint.x == columnCount - 1) && currentPoint.y != 0){
                break;
            }

            boolean isZeroTop =
                    currentPoint.y - 1 >= 0 &&
                            maze[currentPoint.y - 1][currentPoint.x] == 0;

            boolean isZeroLeft =
                    currentPoint.x - 1 >= 0 &&
                            maze[currentPoint.y][currentPoint.x - 1] == 0;

            boolean isZeroBottom =
                    currentPoint.y + 1 < rowCount &&
                            maze[currentPoint.y + 1][currentPoint.x] == 0;

            boolean isZeroRight =
                    currentPoint.x + 1 < columnCount &&
                            maze[currentPoint.y][currentPoint.x + 1] == 0;

            if(isZeroTop) {
                maze[currentPoint.y][currentPoint.x] = -1;
                coordinateQueue.enqueue(new Point(currentPoint.x, currentPoint.y - 1, currentPoint));
            }

            if(isZeroLeft){
                maze[currentPoint.y][currentPoint.x] = -1;
                coordinateQueue.enqueue(new Point(currentPoint.x - 1, currentPoint.y, currentPoint));
            }

            if(isZeroBottom){
                maze[currentPoint.y][currentPoint.x] = -1;
                coordinateQueue.enqueue(new Point(currentPoint.x, currentPoint.y + 1, currentPoint));
            }

            if(isZeroRight){
                maze[currentPoint.y][currentPoint.x] = -1;
                coordinateQueue.enqueue(new Point(currentPoint.x + 1, currentPoint.y, currentPoint));
            }
        }

        return currentPoint;
    }

    public void printCoordinates(Point current){
        if(current == null) return;
        printCoordinates(current.parent);
        System.out.println("(" + current.x + ", " + current.y + ")");
    }

    public int[][] readMaze(String filePath) throws IOException {

        String[] rows = Files.readAllLines(Path.of(filePath), StandardCharsets.UTF_8).toArray(new String[0]);

        if(rows.length == 0)
            throw new IllegalArgumentException("The file is empty!");


        int rowCount = rows.length;
        int columnCount = rows[0].length();

        int[][] maze = new int[rowCount][columnCount];

        for(int i = 0; i < rowCount; i++)
            for(int j = 0; j < columnCount; j++){
                maze[i][j] = Integer.parseInt(String.valueOf(rows[i].charAt(j)));
            }

        return maze;
    }
}
