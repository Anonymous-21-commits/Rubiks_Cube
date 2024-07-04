import java.util.Arrays;
import java.util.Objects;

public class RubiksCube3D extends RubiksCube {
    private static final int ROW = 3;
    private static final int COL = 3;
    private final char[][][] cube = new char[6][3][3];

    private void swap(char[][] face, int r1, int c1, int r2, int c2) {
        char temp = face[r1][c1];
        face[r1][c1] = face[r2][c2];
        face[r2][c2] = temp;
    }

    public void rotateFace(int faceIndex) {
        for (int row = 0; row < ROW; row++) {
            for (int col = row; col < 3; col++) {
                swap(cube[faceIndex], row, col, col, row);
            }
        }
        // Reverse every row
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL / 2; col++) {
                swap(cube[faceIndex], row, col, row, COL - 1 - col);
            }
        }
    }

    private int getIndex(Face face) {
        return switch (face) {
            case UP -> 0;
            case LEFT -> 1;
            case FRONT -> 2;
            case RIGHT -> 3;
            case BOTTOM -> 4; // Assuming "BOTTOM" corresponds to "BACK" in your context
            case DOWN -> 5;
        };
    }

    @Override
    public Color getColor(Face face, int row, int col) {
        int faceIndex = getIndex(face);
        char colorChar = cube[faceIndex][row][col];
        return switch (colorChar) {
            case 'W' -> Color.WHITE;
            case 'B' -> Color.BLUE;
            case 'Y' -> Color.YELLOW;
            case 'R' -> Color.RED;
            case 'G' -> Color.GREEN;
            case 'O' -> Color.ORANGE;
            default -> throw new IllegalArgumentException("Unexpected color: " + colorChar);
        };
    }

    private Color solvedConfig(Face face) {
        return switch (face) {
            case UP -> Color.WHITE;
            case LEFT -> Color.GREEN;
            case FRONT -> Color.RED;
            case RIGHT -> Color.BLUE;
            case BOTTOM -> Color.ORANGE;
            case DOWN -> Color.YELLOW;
        };
    }

    @Override
    public boolean isSolved() {
        for (Face face : Face.values()) {
            for (int row = 0; row < ROW; row++) {
                for (int col = 0; col < COL; col++) {
                    if (getColor(face, row, col) != solvedConfig(face)) return false;
                }
            }
        }
        return true;
    }

    @Override
    public void performMove(Move move) {
        switch (move) {
            case F -> f();
            case FPRIME -> fPrime();
            case F2 -> f2();
            case B -> b();
            case BPRIME -> bPrime();
            case B2 -> b2();
            case U -> u();
            case UPRIME -> uPrime();
            case U2 -> u2();
            case D -> d();
            case DPRIME -> dPrime();
            case D2 -> d2();
            case L -> l();
            case LPRIME -> lPrime();
            case L2 -> l2();
            case R -> r();
            case RPRIME -> rPrime();
            case R2 -> r2();
        }
    }

    @Override
    public void f() {
        this.rotateFace(getIndex(Face.FRONT));
        char[] upFaceLastRow = new char[3];
        for (int index = 0; index < 3; index++) {
            upFaceLastRow[index] = cube[getIndex(Face.UP)][2][index];
            cube[getIndex(Face.UP)][2][index] = cube[getIndex(Face.LEFT)][2 - index][2];
        }
        char[] rightFaceFirstCol = new char[3];
        for (int index = 0; index < 3; index++) {
            rightFaceFirstCol[index] = cube[getIndex(Face.RIGHT)][index][0];
            cube[getIndex(Face.RIGHT)][index][0] = upFaceLastRow[index];
        }
        char[] downFirstRow = new char[3];
        for (int index = 0; index < 3; index++) {
            downFirstRow[index] = cube[getIndex(Face.DOWN)][0][index];
            cube[getIndex(Face.DOWN)][0][index] = rightFaceFirstCol[2 - index];
        }
        for (int index = 0; index < 3; index++) {
            cube[getIndex(Face.LEFT)][index][2] = downFirstRow[index];
        }
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

    public void b() {
        this.rotateFace(getIndex(Face.BOTTOM));
        char[] upFaceFirstRow = new char[3];
        for (int index = 0; index < 3; index++) {
            upFaceFirstRow[index] = cube[getIndex(Face.UP)][0][index];
            cube[getIndex(Face.UP)][0][index] = cube[getIndex(Face.RIGHT)][index][2];
        }
        char[] leftFaceFirstCol = new char[3];
        for (int index = 0; index < 3; index++) {
            leftFaceFirstCol[index] = cube[getIndex(Face.LEFT)][index][0];
            cube[getIndex(Face.LEFT)][index][0] = upFaceFirstRow[2 - index];
        }
        char[] downLastRow = new char[3];
        for (int index = 0; index < 3; index++) {
            downLastRow[index] = cube[getIndex(Face.DOWN)][2][index];
            cube[getIndex(Face.DOWN)][2][index] = leftFaceFirstCol[index];
        }
        for (int index = 0; index < 3; index++) {
            cube[getIndex(Face.RIGHT)][index][2] = downLastRow[2 - index];
        }
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

    public void u() {
        this.rotateFace(getIndex(Face.UP));
        char[] frontFirstRow = new char[3];
        for (int index = 0; index < 3; index++) {
            frontFirstRow[index] = cube[getIndex(Face.FRONT)][0][index];
            cube[getIndex(Face.FRONT)][0][index] = cube[getIndex(Face.RIGHT)][0][index];
        }
        char[] backFirstRow = new char[3];
        for (int index = 0; index < 3; index++) {
            backFirstRow[index] = cube[getIndex(Face.BOTTOM)][0][index];
            cube[getIndex(Face.BOTTOM)][0][index] = frontFirstRow[index];
        }
        char[] leftFirstRow = new char[3];
        for (int index = 0; index < 3; index++) {
            leftFirstRow[index] = cube[getIndex(Face.LEFT)][0][index];
            cube[getIndex(Face.LEFT)][0][index] = backFirstRow[index];
        }
        for (int index = 0; index < 3; index++) {
            cube[getIndex(Face.RIGHT)][0][index] = leftFirstRow[index];
        }
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

    public void d() {
        this.rotateFace(getIndex(Face.DOWN));
        char[] frontLastRow = new char[3];
        for (int index = 0; index < 3; index++) {
            frontLastRow[index] = cube[getIndex(Face.FRONT)][2][index];
            cube[getIndex(Face.FRONT)][2][index] = cube[getIndex(Face.LEFT)][2][index];
        }
        char[] backLastRow = new char[3];
        for (int index = 0; index < 3; index++) {
            backLastRow[index] = cube[getIndex(Face.BOTTOM)][2][index];
            cube[getIndex(Face.BOTTOM)][2][index] = frontLastRow[index];
        }
        char[] rightLastRow = new char[3];
        for (int index = 0; index < 3; index++) {
            rightLastRow[index] = cube[getIndex(Face.RIGHT)][2][index];
            cube[getIndex(Face.RIGHT)][2][index] = backLastRow[index];
        }
        for (int index = 0; index < 3; index++) {
            cube[getIndex(Face.LEFT)][2][index] = rightLastRow[index];
        }
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

    public void l() {
        this.rotateFace(getIndex(Face.LEFT));
        char[] upFirstCol = new char[3];
        for (int index = 0; index < 3; index++) {
            upFirstCol[index] = cube[getIndex(Face.UP)][index][0];
            cube[getIndex(Face.UP)][index][0] = cube[getIndex(Face.FRONT)][index][0];
        }
        char[] downFirstCol = new char[3];
        for (int index = 0; index < 3; index++) {
            downFirstCol[index] = cube[getIndex(Face.DOWN)][index][0];
            cube[getIndex(Face.DOWN)][index][0] = upFirstCol[index];
        }
        char[] backFirstCol = new char[3];
        for (int index = 0; index < 3; index++) {
            backFirstCol[index] = cube[getIndex(Face.BOTTOM)][index][0];
            cube[getIndex(Face.BOTTOM)][index][0] = downFirstCol[2 - index];
        }
        for (int index = 0; index < 3; index++) {
            cube[getIndex(Face.FRONT)][index][0] = backFirstCol[2 - index];
        }
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

    public void r() {
        this.rotateFace(getIndex(Face.RIGHT));
        char[] upLastCol = new char[3];
        for (int index = 0; index < 3; index++) {
            upLastCol[index] = cube[getIndex(Face.UP)][index][2];
            cube[getIndex(Face.UP)][index][2] = cube[getIndex(Face.FRONT)][index][2];
        }
        char[] downLastCol = new char[3];
        for (int index = 0; index < 3; index++) {
            downLastCol[index] = cube[getIndex(Face.DOWN)][index][2];
            cube[getIndex(Face.DOWN)][index][2] = upLastCol[index];
        }
        char[] backLastCol = new char[3];
        for (int index = 0; index < 3; index++) {
            backLastCol[index] = cube[getIndex(Face.BOTTOM)][index][2];
            cube[getIndex(Face.BOTTOM)][index][2] = downLastCol[2 - index];
        }
        for (int index = 0; index < 3; index++) {
            cube[getIndex(Face.FRONT)][index][2] = backLastCol[2 - index];
        }
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

    public String getCornerColorString(int ind) {
        String str = "";
        switch (ind) {
            case 0: // UFR
                str += getColorLetter(getColor(Face.UP, 2, 2));
                str += getColorLetter(getColor(Face.FRONT, 0, 2));
                str += getColorLetter(getColor(Face.RIGHT, 0, 0));
                break;
            case 1: // UFL
                str += getColorLetter(getColor(Face.UP, 2, 0));
                str += getColorLetter(getColor(Face.FRONT, 0, 0));
                str += getColorLetter(getColor(Face.LEFT, 0, 2));
                break;
            case 2: // UBL
                str += getColorLetter(getColor(Face.UP, 0, 0));
                str += getColorLetter(getColor(Face.BOTTOM, 0, 2));
                str += getColorLetter(getColor(Face.LEFT, 0, 0));
                break;
            case 3: // UBR
                str += getColorLetter(getColor(Face.UP, 0, 2));
                str += getColorLetter(getColor(Face.BOTTOM, 0, 0));
                str += getColorLetter(getColor(Face.RIGHT, 0, 2));
                break;
            case 4: // DFR
                str += getColorLetter(getColor(Face.DOWN, 0, 2));
                str += getColorLetter(getColor(Face.FRONT, 2, 2));
                str += getColorLetter(getColor(Face.RIGHT, 2, 0));
                break;
            case 5: // DFL
                str += getColorLetter(getColor(Face.DOWN, 0, 0));
                str += getColorLetter(getColor(Face.FRONT, 2, 0));
                str += getColorLetter(getColor(Face.LEFT, 2, 2));
                break;
            case 6: // DBR
                str += getColorLetter(getColor(Face.DOWN, 2, 2));
                str += getColorLetter(getColor(Face.BOTTOM, 2, 0));
                str += getColorLetter(getColor(Face.RIGHT, 2, 2));
                break;
            case 7: // DBL
                str += getColorLetter(getColor(Face.DOWN, 2, 0));
                str += getColorLetter(getColor(Face.BOTTOM, 2, 2));
                str += getColorLetter(getColor(Face.LEFT, 2, 0));
                break;
        }
        return str;
    }

    public int getCornerIndex(int ind) {
        String corner = getCornerColorString(ind);
        int ret = 0;

        for (char c : corner.toCharArray()) {
            if (c == 'W' || c == 'Y') {
                if (c == 'Y') {
                    ret |= (1 << 2);
                }
                break;
            }
        }

        for (char c : corner.toCharArray()) {
            if (c == 'R' || c == 'O') {
                if (c == 'O') {
                    ret |= (1 << 1);
                }
                break;
            }
        }

        for (char c : corner.toCharArray()) {
            if (c == 'B' || c == 'G') {
                if (c == 'G') {
                    ret |= (1 << 0);
                }
                break;
            }
        }

        return ret;
    }

    public int getCornerOrientation(int ind) {
        String corner = getCornerColorString(ind);
        String actualStr = "";

        for (char c : corner.toCharArray()) {
            if (c == 'W' || c == 'Y') {
                actualStr += c;
                break;
            }
        }

        if (corner.charAt(1) == actualStr.charAt(0)) {
            return 1;
        } else if (corner.charAt(2) == actualStr.charAt(0)) {
            return 2;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RubiksCube3D that = (RubiksCube3D) o;
        return Objects.deepEquals(cube, that.cube);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(cube);
    }

    @Override
    public String toString() {
        return "RubiksCube3D{" +
                "cube=" + Arrays.toString(cube) +
                '}';
    }
}
