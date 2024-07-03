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
        //reverse every row
        for (int row = 0; row < ROW; row++) {
            swap(cube[faceIndex], row, 0, row, COL - 1);
        }
    }

    private int getIndex(Face face) {
        // Use a switch statement to return the specific index for each Face enum
        return switch (face) {
            case UP -> 0;
            case LEFT -> 1;
            case FRONT -> 2;
            case RIGHT -> 3;
            case BOTTOM -> // Assuming "BOTTOM" is meant to correspond to "BACK" in the description
                    4;
            case DOWN -> 5;
        };
    }

    @Override
    public Color getColor(Face face, int row, int col) {
        int faceIndex = getIndex(face); // Get the index for the given face
        char colorChar = cube[faceIndex][row][col]; // Retrieve the color character from the cube array

        // Convert the character to the corresponding Color enum
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
            case UP -> Color.WHITE;  // Up face should be White
            case LEFT -> Color.GREEN;  // Left face should be Green
            case FRONT -> Color.RED;    // Front face should be Red
            case RIGHT -> Color.BLUE;   // Right face should be Blue
            case BOTTOM -> Color.ORANGE; // Bottom face should be Orange (assuming BOTTOM means BACK here)
            case DOWN -> Color.YELLOW; // Down face should be Yellow
        };
    }


    @Override
    public boolean isSolved() {
        for (Face face : Face.values()) {
            for (int row = 0; row < ROW; row++) {
                for (int col = 0; col < COL; col++) {
                    if (getColor(face, row, col) == solvedConfig(face)) continue;
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public RubiksCube performMove(Move move) {
        return switch (move) {
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
        };
    }


    @Override
    public RubiksCube f() {
        return null;
    }

    @Override
    public RubiksCube fPrime() {
        return null;
    }

    @Override
    public RubiksCube f2() {
        return null;
    }

    @Override
    public RubiksCube u() {
        return null;
    }

    @Override
    public RubiksCube uPrime() {
        return null;
    }

    @Override
    public RubiksCube u2() {
        return null;
    }

    @Override
    public RubiksCube l() {
        return null;
    }

    @Override
    public RubiksCube lPrime() {
        return null;
    }

    @Override
    public RubiksCube l2() {
        return null;
    }

    @Override
    public RubiksCube r() {
        return null;
    }

    @Override
    public RubiksCube rPrime() {
        return null;
    }

    @Override
    public RubiksCube r2() {
        return null;
    }

    @Override
    public RubiksCube d() {
        return null;
    }

    @Override
    public RubiksCube dPrime() {
        return null;
    }

    @Override
    public RubiksCube d2() {
        return null;
    }

    @Override
    public RubiksCube b() {
        return null;
    }

    @Override
    public RubiksCube bPrime() {
        return null;
    }

    @Override
    public RubiksCube b2() {
        return null;
    }

    @Override
    public String getCornerColorString(int index) {
        return "";
    }

    @Override
    public int getCornerIndex(int index) {
        return 0;
    }

    @Override
    public int getCornerOrientation(int index) {
        return 0;
    }
}
