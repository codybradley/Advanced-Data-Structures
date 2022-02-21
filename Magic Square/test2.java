public class test2 {

    public static void main(String[] args) {
        MagicSquare sq;
        int ct;

        sq = new MagicSquare(3);
        for (int i = 1; i <= 5; i++) {
            ct = sq.purelyRandom(10);
            System.out.print("Random " + i + ": ");
            if (ct == -1) {
                System.out.println("Failed to find an n = 3, ct = 10 solution.");
            } else {
                System.out.println("Found n = 3, ct = 10 solution in " + ct + " tries.");
                sq.out();
            }
        }
        System.out.println();

        for (int i = 1; i <= 5; i++) {
            ct = sq.purelyRandom(1000000);
            System.out.print("Random " + i + ": ");
            if (ct == -1) {
                System.out.println("Failed to find an n = 3, ct = 1,000,000 solution.");
            } else {
                System.out.println("Found n = 3, ct = 1,000,000 solution in " + ct + " tries.");
                sq.out();
            }
        }
        System.out.println();

        sq = new MagicSquare(4);
        for (int i = 1; i <= 5; i++) {
            ct = sq.purelyRandom(10);
            System.out.print("Random " + i + ": ");
            if (ct == -1) {
                System.out.println("Failed to find an n = 4, ct = 10 solution.");
            } else {
                System.out.println("Found n = 4, ct = 10 solution in " + ct + " tries.");
                sq.out();
            }
        }
        System.out.println();

        for (int i = 1; i <= 5; i++) {
            ct = sq.purelyRandom(1000000);
            System.out.print("Random " + i + ": ");
            if (ct == -1) {
                System.out.println("Failed to find an n = 4, ct = 1,000,000 solution.");
            } else {
                System.out.println("Found n = 4, ct = 1,000,000 solution in " + ct + " tries.");
                sq.out();
            }
        }
        System.out.println();

        if (sq.rowLastImplemented()) {
            sq = new MagicSquare(3);
            for (int i = 1; i <= 5; i++) {
                ct = sq.endOfRow(10);
                System.out.print("End of row " + i + ": ");
                if (ct == -1) {
                    System.out.println("Failed to find an n = 3, ct = 10 solution.");
                } else {
                    System.out.println("Found n = 3, ct = 10 solution in " + ct + " tries.");
                    sq.out();
                }
            }
            System.out.println();

            for (int i = 1; i <= 5; i++) {
                ct = sq.endOfRow(10000);
                System.out.print("End of row " + i + ": ");
                if (ct == -1) {
                    System.out.println("Failed to find an n = 3, ct = 10,000 solution.");
                } else {
                    System.out.println("Found n = 3, ct = 10,000 solution in " + ct + " tries.");
                    sq.out();
                }
            }
            System.out.println();

            sq = new MagicSquare(4);
            for (int i = 1; i <= 5; i++) {
                ct = sq.endOfRow(10);
                System.out.print("End of row " + i + ": ");
                if (ct == -1) {
                    System.out.println("Failed to find an n = 4, ct = 10 solution.");
                } else {
                    System.out.println("Found n = 4, ct = 10 solution in " + ct + " tries.");
                    sq.out();
                }
            }
            System.out.println();

            for (int i = 1; i <= 5; i++) {
                ct = sq.endOfRow(10000000);
                System.out.print("End of row " + i + ": ");
                if (ct == -1) {
                    System.out.println("Failed to find an n = 4, ct = 10,000,000 solution.");
                } else {
                    System.out.println("Found n = 4, ct = 10,000,000 solution in " + ct + " tries.");
                    sq.out();
                }
            }
            System.out.println();

        } else
            System.out.println("End of row trick not implemented.\n");

        if (sq.pairsImplemented()) {
            sq = new MagicSquare(4);
            for (int i = 1; i <= 5; i++) {
                ct = sq.pairs(10);
                System.out.print("Pairs " + i + ": ");
                if (ct == -1) {
                    System.out.println("Failed to find an n = 4, ct = 10 solution.");
                } else {
                    System.out.println("Found n = 4, ct = 10 solution in " + ct + " tries.");
                    sq.out();
                }
            }
            System.out.println();

            for (int i = 1; i <= 5; i++) {
                ct = sq.pairs(10000000);
                System.out.print("Pairs " + i + ": ");
                if (ct == -1) {
                    System.out.println("Failed to find an n = 4, ct = 10,000,000 solution.");
                } else {
                    System.out.println("Found n = 4, ct = 10,000,000 solution in " + ct + " tries.");
                    sq.out();
                }
            }
        } else
            System.out.println("Pairs trick not implemented.");

        System.out.println("\n" + MagicSquare.myName() + " program has completed.");
    }

}