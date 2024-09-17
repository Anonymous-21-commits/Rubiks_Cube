import java.util.ArrayList;
import java.util.List;

// Typename T: RubiksCube Representation used (3D, 1D, Bitboard)
// Typename H: Corresponding Hash function (could be used in more advanced methods)

public class DFSSolver<T extends RubiksCube> {
    private final List<RubiksCube.Move> moves;
    private final int maxSearchDepth;
     final T rubiksCube;

    // Constructor
    public DFSSolver(T rubiksCube, int maxSearchDepth) {
        this.rubiksCube = rubiksCube;
        this.maxSearchDepth = maxSearchDepth;
        this.moves = new ArrayList<>();
    }

    // DFS method to find the solution
    private boolean dfs(int depth) {
        if (rubiksCube.isSolved()) {
            return true;
        }
        if (depth > maxSearchDepth) {
            return false;
        }
        for (int i = 0; i < 18; i++) {
            rubiksCube.performMove(RubiksCube.Move.values()[i]);
            moves.add(RubiksCube.Move.values()[i]);
            if (dfs(depth + 1)) {
                return true;
            }
            moves.removeLast();
            rubiksCube.invert(RubiksCube.Move.values()[i]);
        }
        return false;
    }

    // Solve method to initiate DFS and return the solution
    public List<RubiksCube.Move> solve() {
        dfs(1);
        return moves;
    }

}
