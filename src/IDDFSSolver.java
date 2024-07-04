import java.util.ArrayList;
import java.util.List;

// Typename T: RubiksCube Representation used (3D, 1D, Bitboard)
// Typename H: Corresponding Hash function (could be used in more advanced methods)

public class IDDFSSolver<T extends RubiksCube, H> {

    private final int maxSearchDepth;
    private List<RubiksCube.Move> moves;

    private T rubiksCube;

    public IDDFSSolver(T rubiksCube, int maxSearchDepth) {
        this.rubiksCube = rubiksCube;
        this.maxSearchDepth = maxSearchDepth;
        this.moves = new ArrayList<>();
    }

    // Used DFSSolver with increasing maxSearchDepth
    public List<RubiksCube.Move> solve() {
        DFSSolver<T> dfsSolver = new DFSSolver<>(rubiksCube, 1);
        for (int i = 1; i <= maxSearchDepth; i++) {
            dfsSolver = new DFSSolver<>(rubiksCube, i);
            moves = dfsSolver.solve();
            if (dfsSolver.rubiksCube.isSolved()) {
                rubiksCube = dfsSolver.rubiksCube;
                break;
            }
        }
        return moves;
    }
}
