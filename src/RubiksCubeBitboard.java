import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RubiksCubeBitboard extends RubiksCube {

    private final long[] bitboard = new long[6];
    private final long[] solvedSideConfig = new long[6];

    private static final int[][] arr = {
            {0, 1, 2},
            {7, 8, 3},
            {6, 5, 4}
    };

    private static final long ONE_8 = (1L << 8) - 1;
    private static final long ONE_24 = (1L << 24) - 1;

    public RubiksCubeBitboard() {
        for (int side = 0; side < 6; side++) {
            long clr = 1L << side;
            bitboard[side] = 0;
            for (int faceIdx = 0; faceIdx < 8; faceIdx++) {
                bitboard[side] |= clr << (8 * faceIdx);
            }
            solvedSideConfig[side] = bitboard[side];
        }
    }

    @Override
    public Color getColor(Face face, int row, int col) {
        int idx = arr[row][col];
        if (idx == 8) return Color.values()[face.ordinal()];

        long side = bitboard[face.ordinal()];
        long color = (side >> (8 * idx)) & ONE_8;

        int bitPos = 0;
        while (color != 0) {
            color >>= 1;
            bitPos++;
        }
        return Color.values()[bitPos - 1];
    }

    @Override
    public boolean isSolved() {
        for (int i = 0; i < 6; i++) {
            if (solvedSideConfig[i] != bitboard[i]) return false;
        }
        return true;
    }

    private void rotateFace(int ind) {
        long side = bitboard[ind];
        side = side >> (8 * 6);
        bitboard[ind] = (bitboard[ind] << 16) | (side);
    }

    private void rotateSide(int s1, int s1_1, int s1_2, int s1_3, int s2, int s2_1, int s2_2, int s2_3) {
        long clr1 = (bitboard[s2] & (ONE_8 << (8 * s2_1))) >> (8 * s2_1);
        long clr2 = (bitboard[s2] & (ONE_8 << (8 * s2_2))) >> (8 * s2_2);
        long clr3 = (bitboard[s2] & (ONE_8 << (8 * s2_3))) >> (8 * s2_3);

        bitboard[s1] = (bitboard[s1] & ~(ONE_8 << (8 * s1_1))) | (clr1 << (8 * s1_1));
        bitboard[s1] = (bitboard[s1] & ~(ONE_8 << (8 * s1_2))) | (clr2 << (8 * s1_2));
        bitboard[s1] = (bitboard[s1] & ~(ONE_8 << (8 * s1_3))) | (clr3 << (8 * s1_3));
    }

    @Override
    public void u() {
        this.rotateFace(Face.UP.ordinal());
        long temp = bitboard[Face.FRONT.ordinal()] & ONE_24;
        bitboard[Face.FRONT.ordinal()] = (bitboard[Face.FRONT.ordinal()] & ~ONE_24) | (bitboard[Face.RIGHT.ordinal()] & ONE_24);
        bitboard[Face.RIGHT.ordinal()] = (bitboard[Face.RIGHT.ordinal()] & ~ONE_24) | (bitboard[Face.BOTTOM.ordinal()] & ONE_24);
        bitboard[Face.BOTTOM.ordinal()] = (bitboard[Face.BOTTOM.ordinal()] & ~ONE_24) | (bitboard[Face.LEFT.ordinal()] & ONE_24);
        bitboard[Face.LEFT.ordinal()] = (bitboard[Face.LEFT.ordinal()] & ~ONE_24) | temp;
    }

    @Override
    public void uPrime() {
        u();
        u();
        u();
    }

    @Override
    public void u2() {
        u();
        u();
    }

    @Override
    public void l() {
        this.rotateFace(Face.LEFT.ordinal());

        long clr1 = (bitboard[Face.FRONT.ordinal()] & (ONE_8 << (8 * 0))) >> (8 * 0);
        long clr2 = (bitboard[Face.FRONT.ordinal()] & (ONE_8 << (8 * 6))) >> (8 * 6);
        long clr3 = (bitboard[Face.FRONT.ordinal()] & (ONE_8 << (8 * 7))) >> (8 * 7);

        this.rotateSide(Face.FRONT.ordinal(), 0, 7, 6, Face.UP.ordinal(), 0, 7, 6);
        this.rotateSide(Face.UP.ordinal(), 0, 7, 6, Face.BOTTOM.ordinal(), 4, 3, 2);
        this.rotateSide(Face.BOTTOM.ordinal(), 4, 3, 2, Face.DOWN.ordinal(), 0, 7, 6);

        bitboard[Face.DOWN.ordinal()] = (bitboard[Face.DOWN.ordinal()] & ~(ONE_8 << (8 * 0))) | (clr1 << (8 * 0));
        bitboard[Face.DOWN.ordinal()] = (bitboard[Face.DOWN.ordinal()] & ~(ONE_8 << (8 * 6))) | (clr2 << (8 * 6));
        bitboard[Face.DOWN.ordinal()] = (bitboard[Face.DOWN.ordinal()] & ~(ONE_8 << (8 * 7))) | (clr3 << (8 * 7));

    }

    @Override
    public void lPrime() {
        l();
        l();
        l();
    }

    @Override
    public void l2() {
        l();
        l();
    }

    @Override
    public void f() {
        this.rotateFace(Face.FRONT.ordinal());

        long clr1 = (bitboard[Face.UP.ordinal()] & (ONE_8 << (8 * 4))) >> (8 * 4);
        long clr2 = (bitboard[Face.UP.ordinal()] & (ONE_8 << (8 * 5))) >> (8 * 5);
        long clr3 = (bitboard[Face.UP.ordinal()] & (ONE_8 << (8 * 6))) >> (8 * 6);

        this.rotateSide(Face.UP.ordinal(), 4, 5, 6, Face.LEFT.ordinal(), 2, 3, 4);
        this.rotateSide(Face.LEFT.ordinal(), 2, 3, 4, Face.DOWN.ordinal(), 0, 1, 2);
        this.rotateSide(Face.DOWN.ordinal(), 0, 1, 2, Face.RIGHT.ordinal(), 6, 7, 0);

        bitboard[Face.RIGHT.ordinal()] = (bitboard[Face.RIGHT.ordinal()] & ~(ONE_8 << (8 * 6))) | (clr1 << (8 * 6));
        bitboard[Face.RIGHT.ordinal()] = (bitboard[Face.RIGHT.ordinal()] & ~(ONE_8 << (8 * 7))) | (clr2 << (8 * 7));
        bitboard[Face.RIGHT.ordinal()] = (bitboard[Face.RIGHT.ordinal()] & ~(ONE_8 << (8 * 0))) | (clr3 << (8 * 0));

    }

    @Override
    public void fPrime() {
        f();
        f();
        f();
    }

    @Override
    public void f2() {
        this.f();
        f();
    }

    @Override
    public void r() {
        this.rotateFace(Face.RIGHT.ordinal());

        long clr1 = (bitboard[Face.UP.ordinal()] & (ONE_8 << (8 * 2))) >> (8 * 2);
        long clr2 = (bitboard[Face.UP.ordinal()] & (ONE_8 << (8 * 3))) >> (8 * 3);
        long clr3 = (bitboard[Face.UP.ordinal()] & (ONE_8 << (8 * 4))) >> (8 * 4);

        this.rotateSide(Face.UP.ordinal(), 2, 3, 4, Face.FRONT.ordinal(), 2, 3, 4);
        this.rotateSide(Face.FRONT.ordinal(), 2, 3, 4, Face.DOWN.ordinal(), 2, 3, 4);
        this.rotateSide(Face.DOWN.ordinal(), 2, 3, 4, Face.BOTTOM.ordinal(), 7, 6, 0);

        bitboard[Face.BOTTOM.ordinal()] = (bitboard[Face.BOTTOM.ordinal()] & ~(ONE_8 << (8 * 7))) | (clr1 << (8 * 7));
        bitboard[Face.BOTTOM.ordinal()] = (bitboard[Face.BOTTOM.ordinal()] & ~(ONE_8 << (8 * 6))) | (clr2 << (8 * 6));
        bitboard[Face.BOTTOM.ordinal()] = (bitboard[Face.BOTTOM.ordinal()] & ~(ONE_8 << (8 * 0))) | (clr3 << (8 * 0));
    }

    @Override
    public void rPrime() {
        r();
        r();
        r();
    }

    @Override
    public void r2() {
        this.r();
        r();
    }

    @Override
    public void d() {
        this.rotateFace(Face.DOWN.ordinal());

        // Define constants for masks and shifts
        final long MASK_24_BITS = 0xFFFFFFL; // 24-bit mask
        final int ROW_SHIFT = 48; // Shift to align with the correct 24-bit row (6 rows of 8 bits)

        // Create the masks without causing overflow
        long mask = 0xFFFFFFL; // Initial 24-bit mask
        long clearMask = mask << ROW_SHIFT; // Shifted mask
        long fullClearMask = ~(clearMask); // Negate to get the mask to clear the bits

        // Extract the 24-bit rows for each face
        long tempFrontRow = (bitboard[Face.FRONT.ordinal()] >> ROW_SHIFT) & MASK_24_BITS;
        long leftRow = (bitboard[Face.LEFT.ordinal()] >> ROW_SHIFT) & MASK_24_BITS;
        long bottomRow = (bitboard[Face.BOTTOM.ordinal()] >> ROW_SHIFT) & MASK_24_BITS;
        long rightRow = (bitboard[Face.RIGHT.ordinal()] >> ROW_SHIFT) & MASK_24_BITS;

        // Clear the rows on the target faces using the computed clear mask
        bitboard[Face.FRONT.ordinal()] &= fullClearMask; // Clear the 24 bits on the front face
        bitboard[Face.LEFT.ordinal()] &= fullClearMask;  // Clear the 24 bits on the left face
        bitboard[Face.BOTTOM.ordinal()] &= fullClearMask; // Clear the 24 bits on the bottom face
        bitboard[Face.RIGHT.ordinal()] &= fullClearMask;  // Clear the 24 bits on the right face

        // Set the new rows in the target faces
        bitboard[Face.FRONT.ordinal()] |= (leftRow << ROW_SHIFT);
        bitboard[Face.LEFT.ordinal()] |= (bottomRow << ROW_SHIFT);
        bitboard[Face.BOTTOM.ordinal()] |= (rightRow << ROW_SHIFT);
        bitboard[Face.RIGHT.ordinal()] |= (tempFrontRow << ROW_SHIFT);
    }


    @Override
    public void dPrime() {
        this.d();
        d();
        d();
    }

    @Override
    public void d2() {
        this.d();
        d();
    }

    @Override
    public void b() {
        this.rotateFace(Face.BOTTOM.ordinal());

        long clr1 = (bitboard[Face.UP.ordinal()] & (ONE_8 << (8 * 0))) >> (8 * 0);
        long clr2 = (bitboard[Face.UP.ordinal()] & (ONE_8 << (8 * 7))) >> (8 * 7);
        long clr3 = (bitboard[Face.UP.ordinal()] & (ONE_8 << (8 * 6))) >> (8 * 6);

        this.rotateSide(Face.UP.ordinal(), 0, 7, 6, Face.RIGHT.ordinal(), 4, 5, 6);
        this.rotateSide(Face.RIGHT.ordinal(), 4, 5, 6, Face.DOWN.ordinal(), 4, 5, 6);
        this.rotateSide(Face.DOWN.ordinal(), 4, 5, 6, Face.LEFT.ordinal(), 0, 1, 2);

        bitboard[Face.LEFT.ordinal()] = (bitboard[Face.LEFT.ordinal()] & ~(ONE_8 << (8 * 0))) | (clr1 << (8 * 0));
        bitboard[Face.LEFT.ordinal()] = (bitboard[Face.LEFT.ordinal()] & ~(ONE_8 << (8 * 1))) | (clr2 << (8 * 1));
        bitboard[Face.LEFT.ordinal()] = (bitboard[Face.LEFT.ordinal()] & ~(ONE_8 << (8 * 2))) | (clr3 << (8 * 2));
    }

    @Override
    public void bPrime() {
        this.b();
        b();
        b();
    }

    @Override
    public void b2() {
        this.b();
        b();
    }


    public void move(String move) {
        switch (move) {
            case "U" -> this.u();
            case "U'" -> this.uPrime();
            case "U2" -> this.u2();
            case "L" -> this.l();
            case "L'" -> this.lPrime();
            case "L2" -> this.l2();
            case "F" -> this.f();
            case "F'" -> this.fPrime();
            case "F2" -> this.f2();
            case "R" -> this.r();
            case "R'" -> this.rPrime();
            case "R2" -> this.r2();
            case "B" -> this.b();
            case "B'" -> this.bPrime();
            case "B2" -> this.b2();
            case "D" -> this.d();
            case "D'" -> this.dPrime();
            case "D2" -> this.d2();
        }
    }

    public List<String> scramble(int times) {
        List<String> moves = new ArrayList<>();
        String[] possibleMoves = {"U", "U'", "U2", "L", "L'", "L2", "F", "F'", "F2", "R", "R'", "R2", "B", "B'", "B2", "D", "D'", "D2"};
        Random rand = new Random();
        for (int i = 0; i < times; i++) {
            String move = possibleMoves[rand.nextInt(possibleMoves.length)];
            this.move(move);
            moves.add(move);
        }
        return moves;
    }
}
