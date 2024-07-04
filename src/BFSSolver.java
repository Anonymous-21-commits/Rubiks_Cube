import java.util.*;

// Typename T: RubiksCube Representation used (3D, 1D, Bitboard)
// Typename H: Corresponding Hash function (could be used in more advanced methods)

public class BFSSolver<T extends RubiksCube> {
    private final List<RubiksCube.Move> moves;
    private final Map<T, Boolean> visited;
    private final Map<T, RubiksCube.Move> moveDone;
    private T rubiksCube;

    // Constructor
    public BFSSolver(T rubiksCube) {
        this.rubiksCube = rubiksCube;
        this.moves = new ArrayList<>();
        this.visited = new HashMap<>();
        this.moveDone = new HashMap<>();
    }

    // BFS method to find the solution and return the solved Rubik's Cube
    private T bfs() {
        Queue<T> queue = new LinkedList<>();
        queue.add(rubiksCube);
        visited.put(rubiksCube, true);

        while (!queue.isEmpty()) {
            T node = queue.poll();
            if (node.isSolved()) {
                return node;
            }
            for (int i = 0; i < 18; i++) {
                RubiksCube.Move currMove = RubiksCube.Move.values()[i];
                node.performMove(currMove);
                if (!visited.containsKey(node)) {
                    visited.put(node, true);
                    moveDone.put(node, currMove);
                    queue.add(createCopy(node)); // Create a copy instead of casting
                }
                node.invert(currMove);
            }
        }
        return rubiksCube; // If no solution found, return the original cube
    }

    // Helper method to create a copy of a RubiksCube instance
    @SuppressWarnings("unchecked")
    private T createCopy(T original) {
        return (T) original.clone(); // Safe cast if clone() method is properly overridden
    }

    // Solve method to initiate BFS and return the list of moves to solve the cube
    public List<RubiksCube.Move> solve() {
        T solvedCube = bfs();
        assert solvedCube.isSolved();
        T currCube = createCopy(solvedCube); // Start from the solved cube
        while (!currCube.equals(rubiksCube)) {
            RubiksCube.Move currMove = moveDone.get(currCube);
            moves.add(currMove);
            currCube.invert(currMove);
        }
        rubiksCube = solvedCube;
        Collections.reverse(moves);
        return moves;
    }
}
