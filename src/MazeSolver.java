import elements.Point;
import helpers.MazeHelper;

import java.io.IOException;

public class MazeSolver {
    public static void main(String[] args) {

        MazeHelper mazeHelper = new MazeHelper();

        int[][] maze;

        try{
            maze = mazeHelper.readMaze("src/mazes/maze.txt");
        }
        catch (IOException exception){
            System.out.println("Error loading the file: " + exception);
            return;
        }
        catch (IllegalArgumentException exception){
            System.out.println("Error getting data from the file: " + exception);
            return;
        }

        Point exit = mazeHelper.solveAndGetCoordinate(maze);
        mazeHelper.printCoordinates(exit);
    }
}




