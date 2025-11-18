# Maze Solver – Java

A simple and flexible **maze-solving algorithm** implemented in Java.  
The solver reads a maze from a `.txt` file, parses it into a grid, and finds a valid path from an **entrance on the top row** to an **exit on any border** (left, right, bottom).

The maze can be any size as long as it follows two rules:
1. **Entrance is on the top row.**
2. **Exit is on the left, right, or bottom boundary.**

---

## 🚀 Features

- ✔ Reads maze from an external text file  
- ✔ Works on **any maze layout** fitting the entrance/exit rules  
- ✔ Uses **DFS (Depth-First Search)** with backtracking  
- ✔ Prevents revisiting nodes  
- ✔ Outputs:
  - Full coordinate path  
  - Visual, formatted path representation  
- ✔ Clean, object-oriented structure (`Point`, path tracking, validation methods)
