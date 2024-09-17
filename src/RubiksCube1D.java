import java.util.Arrays;

public class RubiksCube1D extends RubiksCube {

    // Cube representation as a 1D array
    private final char[] cube = new char[54];

    // Constructor to initialize the cube with default colors
    public RubiksCube1D() {
        for (int i = 0; i < 6; i++) {
            char colorLetter = getColorLetter(Color.values()[i]);
            for (int j = 0; j < 9; j++) {
                cube[i * 9 + j] = colorLetter;
            }
        }
    }

    // Method to get the flattened index for a face, row, and column
    private static int getIndex(int ind, int row, int col) {
        return (ind * 9) + (row * 3) + col;
    }

    // Method to rotate a face 90 degrees clockwise
    private void rotateFace(int ind) {
        char[] tempArr = new char[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tempArr[i * 3 + j] = cube[getIndex(ind, i, j)];
            }
        }
        for (int i = 0; i < 3; i++) cube[getIndex(ind, 0, i)] = tempArr[getIndex(0, 2 - i, 0)];
        for (int i = 0; i < 3; i++) cube[getIndex(ind, i, 2)] = tempArr[getIndex(0, 0, i)];
        for (int i = 0; i < 3; i++) cube[getIndex(ind, 2, 2 - i)] = tempArr[getIndex(0, i, 2)];
        for (int i = 0; i < 3; i++) cube[getIndex(ind, 2 - i, 0)] = tempArr[getIndex(0, 2, 2 - i)];
    }

    // Helper method to convert a COLOR enum to its char representation

    // Method to get the color of a specific face, row, and column
    public Color getColor(Face face, int row, int col) {
        char color = cube[getIndex(face.ordinal(), row, col)];
        return switch (color) {
            case 'B' -> Color.BLUE;
            case 'R' -> Color.RED;
            case 'G' -> Color.GREEN;
            case 'O' -> Color.ORANGE;
            case 'Y' -> Color.YELLOW;
            default -> Color.WHITE;
        };
    }

    // Method to check if the cube is solved
    public boolean isSolved() {
        for (int i = 0; i < 6; i++) {
            char faceColor = getColorLetter(Color.values()[i]);
            for (int j = 0; j < 9; j++) {
                if (cube[getIndex(i, j / 3, j % 3)] != faceColor) return false;
            }
        }
        return true;
    }

    // Rotation methods

    public void u() {
        rotateFace(0);

        char[] tempArr = new char[3];
        for (int i = 0; i < 3; i++) tempArr[i] = cube[getIndex(4, 0, 2 - i)];
        for (int i = 0; i < 3; i++) cube[getIndex(4, 0, 2 - i)] = cube[getIndex(1, 0, 2 - i)];
        for (int i = 0; i < 3; i++) cube[getIndex(1, 0, 2 - i)] = cube[getIndex(2, 0, 2 - i)];
        for (int i = 0; i < 3; i++) cube[getIndex(2, 0, 2 - i)] = cube[getIndex(3, 0, 2 - i)];
        for (int i = 0; i < 3; i++) cube[getIndex(3, 0, 2 - i)] = tempArr[i];
    }



    public void uPrime() {
        u();
        u();
        u();
    }

    public void u2() {
        u();
        u();
    }

    public void l() {
        rotateFace(1);

        char[] tempArr = new char[3];
        for (int i = 0; i < 3; i++) tempArr[i] = cube[getIndex(0, i, 0)];
        for (int i = 0; i < 3; i++) cube[getIndex(0, i, 0)] = cube[getIndex(4, 2 - i, 2)];
        for (int i = 0; i < 3; i++) cube[getIndex(4, 2 - i, 2)] = cube[getIndex(5, i, 0)];
        for (int i = 0; i < 3; i++) cube[getIndex(5, i, 0)] = cube[getIndex(2, i, 0)];
        for (int i = 0; i < 3; i++) cube[getIndex(2, i, 0)] = tempArr[i];
    }

    public void lPrime() {
        l();
        l();
        l();
    }

    public void l2() {
        l();
        l();
    }

    public void f() {
        rotateFace(2);

        char[] tempArr = new char[3];
        for (int i = 0; i < 3; i++) tempArr[i] = cube[getIndex(0, 2, i)];
        for (int i = 0; i < 3; i++) cube[getIndex(0, 2, i)] = cube[getIndex(1, 2 - i, 2)];
        for (int i = 0; i < 3; i++) cube[getIndex(1, 2 - i, 2)] = cube[getIndex(5, 0, 2 - i)];
        for (int i = 0; i < 3; i++) cube[getIndex(5, 0, 2 - i)] = cube[getIndex(3, i, 0)];
        for (int i = 0; i < 3; i++) cube[getIndex(3, i, 0)] = tempArr[i];
    }

    public void fPrime() {
        f();
        f();
        f();
    }

    public void f2() {
        f();
        f();
    }

    public void r() {
        rotateFace(3);

        char[] tempArr = new char[3];
        for (int i = 0; i < 3; i++) tempArr[i] = cube[getIndex(0, 2 - i, 2)];
        for (int i = 0; i < 3; i++) cube[getIndex(0, 2 - i, 2)] = cube[getIndex(2, 2 - i, 2)];
        for (int i = 0; i < 3; i++) cube[getIndex(2, 2 - i, 2)] = cube[getIndex(5, 2 - i, 2)];
        for (int i = 0; i < 3; i++) cube[getIndex(5, 2 - i, 2)] = cube[getIndex(4, i, 0)];
        for (int i = 0; i < 3; i++) cube[getIndex(4, i, 0)] = tempArr[i];
    }

    public void rPrime() {
        r();
        r();
        r();
    }

    public void r2() {
        r();
        r();
    }

    public void b() {
        rotateFace(4);

        char[] tempArr = new char[3];
        for (int i = 0; i < 3; i++) tempArr[i] = cube[getIndex(0, 0, 2 - i)];
        for (int i = 0; i < 3; i++) cube[getIndex(0, 0, 2 - i)] = cube[getIndex(3, 2 - i, 2)];
        for (int i = 0; i < 3; i++) cube[getIndex(3, 2 - i, 2)] = cube[getIndex(5, 2, i)];
        for (int i = 0; i < 3; i++) cube[getIndex(5, 2, i)] = cube[getIndex(1, i, 0)];
        for (int i = 0; i < 3; i++) cube[getIndex(1, i, 0)] = tempArr[i];
    }

    public void bPrime() {
        b();
        b();
        b();
    }

    public void b2() {
        b();
        b();
    }

    public void d() {
        rotateFace(5);

        char[] tempArr = new char[3];
        for (int i = 0; i < 3; i++) tempArr[i] = cube[getIndex(2, 2, i)];
        for (int i = 0; i < 3; i++) cube[getIndex(2, 2, i)] = cube[getIndex(1, 2, i)];
        for (int i = 0; i < 3; i++) cube[getIndex(1, 2, i)] = cube[getIndex(4, 2, i)];
        for (int i = 0; i < 3; i++) cube[getIndex(4, 2, i)] = cube[getIndex(3, 2, i)];
        for (int i = 0; i < 3; i++) cube[getIndex(3, 2, i)] = tempArr[i];
    }

    public void dPrime() {
        d();
        d();
        d();
    }

    public void d2() {
        d();
        d();
    }

    // Overriding equals method to compare two cubes
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RubiksCube1D other = (RubiksCube1D) obj;
        for (int i = 0; i < 54; i++) {
            if (cube[i] != other.cube[i]) return false;
        }
        return true;
    }

    // Overriding hashCode method
    @Override
    public int hashCode() {
        return Arrays.hashCode(cube);
    }
}
