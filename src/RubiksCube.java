import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstract class representing a Rubik's Cube.
 * Provides the structure and common functionalities for any Rubik's Cube implementation.
 */
public abstract class RubiksCube {

    /**
     * Enum representing possible moves on a Rubik's Cube.
     * Each move can be a clockwise turn, a counter-clockwise turn (denoted by 'PRIME'), or a 180-degree turn (denoted by '2').
     */
    public enum Move {
        L, LPRIME, L2,  // Left face turns
        R, RPRIME, R2,  // Right face turns
        F, FPRIME, F2,  // Front face turns
        B, BPRIME, B2,  // Back face turns
        U, UPRIME, U2,  // Up face turns
        D, DPRIME, D2   // Down face turns
    }

    /**
     * Enum representing the colors of a Rubik's Cube's tiles.
     */
    public enum Color {
        WHITE, BLUE, YELLOW, RED, GREEN, ORANGE
    }

    /**
     * Enum representing the six faces of a Rubik's Cube.
     */
    public enum Face {
        FRONT, BOTTOM, LEFT, RIGHT, UP, DOWN
    }

    /**
     * Gets the color of the tile at the specified position on the given face.
     *
     * @param face The face of the cube.
     * @param row  The row index (0-based) on the face.
     * @param col  The column index (0-based) on the face.
     * @return The color of the tile.
     */
    public abstract Color getColor(Face face, int row, int col);

    /**
     * Converts a Color enum to its corresponding character.
     *
     * @param color The color to convert.
     * @return The character representing the color.
     */
    public static char getColorLetter(Color color) {
        return switch (color) {
            case WHITE -> 'W';
            case GREEN -> 'G';
            case RED -> 'R';
            case BLUE -> 'B';
            case ORANGE -> 'O';
            case YELLOW -> 'Y';
        };
    }

    /**
     * Prints the current state of the Rubik's Cube.
     * This method is intended to be overridden by concrete implementations to provide specific print behavior.
     */
    public void print() {
        // Abstract method for printing the state of the cube
    }

    /**
     * Checks if the Rubik's Cube is in a solved state.
     *
     * @return True if the cube is solved, otherwise false.
     */
    public abstract boolean isSolved();

    /**
     * Converts a Move enum to its corresponding string representation.
     *
     * @param move The move to convert.
     * @return The string representation of the move.
     */
    public static String getMove(Move move) {
        return switch (move) {
            case L -> "L";
            case LPRIME -> "L'";
            case L2 -> "L2";
            case R -> "R";
            case RPRIME -> "R'";
            case R2 -> "R2";
            case U -> "U";
            case UPRIME -> "U'";
            case U2 -> "U2";
            case D -> "D";
            case DPRIME -> "D'";
            case D2 -> "D2";
            case F -> "F";
            case FPRIME -> "F'";
            case F2 -> "F2";
            case B -> "B";
            case BPRIME -> "B'";
            case B2 -> "B2";
        };
    }

    /**
     * Applies the given move to the Rubik's Cube.
     * This method must be implemented by subclasses to define how each move affects the cube's state.
     *
     * @param move The move to perform.
     */
    public abstract void performMove(Move move);

    /**
     * Applies the inverse of the given move to the Rubik's Cube.
     *
     * @param move The move to invert.
     * @ The updated Rubik's Cube after the inverse move.
     */
    public void invert(Move move) {
        switch (move) {
            case L -> this.performMove(Move.LPRIME);
            case LPRIME -> this.performMove(Move.L);
            case L2 -> this.performMove(Move.L2);
            case R -> this.performMove(Move.RPRIME);
            case RPRIME -> this.performMove(Move.R);
            case R2 -> this.performMove(Move.R2);
            case U -> this.performMove(Move.UPRIME);
            case UPRIME -> this.performMove(Move.U);
            case U2 -> this.performMove(Move.U2);
            case D -> this.performMove(Move.DPRIME);
            case DPRIME -> this.performMove(Move.D);
            case D2 -> this.performMove(Move.D2);
            case F -> this.performMove(Move.FPRIME);
            case FPRIME -> this.performMove(Move.F);
            case F2 -> this.performMove(Move.F2);
            case B -> this.performMove(Move.BPRIME);
            case BPRIME -> this.performMove(Move.B);
            case B2 -> this.performMove(Move.B2);
        }
        ;
    }

    /**
     * Applies a sequence of random moves to the Rubik's Cube to shuffle it.
     *
     * @param times The number of random moves to apply.
     * @return A list of the moves that were applied.
     */
    public List<Move> randomShuffle(int times) {
        List<Move> movesPerformed = new ArrayList<>();
        Random rand = new Random();
        Move[] allMoves = Move.values();
        for (int i = 0; i < times; i++) {
            Move move = allMoves[rand.nextInt(allMoves.length)];
            movesPerformed.add(move);
            this.performMove(move);
        }
        return movesPerformed;
    }

    // Abstract methods for each type of move.
    // These methods provide specific shortcuts for each move on the Rubik's Cube.
    public abstract void f();

    public abstract void fPrime();

    public abstract void f2();

    public abstract void u();

    public abstract void uPrime();

    public abstract void u2();

    public abstract void l();

    public abstract void lPrime();

    public abstract void l2();

    public abstract void r();

    public abstract void rPrime();

    public abstract void r2();

    public abstract void d();

    public abstract void dPrime();

    public abstract void d2();

    public abstract void b();

    public abstract void bPrime();

    public abstract void b2();

    // Abstract methods for corner manipulation.
    // These methods are placeholders for operations related to the cube's corners.
    public abstract String getCornerColorString(int index);

    public abstract int getCornerIndex(int index);

    public abstract int getCornerOrientation(int index);
}
