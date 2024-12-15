package com.github.krisbanas.toolbox;

public class ChineseRemainder {
    /**
     * Calculates the modular multiplicative inverse using the extended Euclidean algorithm
     *
     * @param a The number to find inverse for
     * @param m The modulus
     * @return Modular multiplicative inverse
     */
    private static long modInverse(long a, long m) {
        // Handle negative numbers
        a = a % m;
        for (long x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }

    /**
     * Solves the system of linear congruences using Chinese Remainder Theorem
     *
     * @param remainders Array of remainders
     * @param moduli     Array of moduli (must be pairwise coprime)
     * @return Solution to the system of congruences
     */
    public static long[] chineseRemainderTheorem(long[] remainders, long[] moduli) {
        // Validate input
        if (remainders.length != moduli.length) {
            throw new IllegalArgumentException("Remainders and moduli must have the same length");
        }

        // Print incoming equations
        System.out.println("Incoming Equations:");
        for (int i = 0; i < remainders.length; i++) {
            System.out.printf("x ≡ %d (mod %d)%n", remainders[i], moduli[i]);
        }
        System.out.println();

        // Calculate the product of all moduli
        long M = 1;
        for (long modulus : moduli) {
            M *= modulus;
        }

        // Detailed solving process
        System.out.println("Solving Process:");
        System.out.println("Total Modulus (M): " + M);

        // Solve using the CRT formula
        long solution = 0;
        for (int i = 0; i < remainders.length; i++) {
            // Calculate M_i (product of all moduli except the current one)
            long Mi = M / moduli[i];

            // Find modular multiplicative inverse
            long inverse = modInverse(Mi, moduli[i]);

            // Calculate and display detailed steps
            long termContribution = remainders[i] * Mi * inverse;
            solution += termContribution;

            System.out.printf("Iteration %d:%n", i + 1);
            System.out.printf("  Remainder: %d%n", remainders[i]);
            System.out.printf("  Modulus: %d%n", moduli[i]);
            System.out.printf("  M_%d (product of other moduli): %d%n", i + 1, Mi);
            System.out.printf("  Modular Inverse of %d mod %d: %d%n", Mi, moduli[i], inverse);
            System.out.printf("  Term Contribution: %d * %d * %d = %d%n%n",
                    remainders[i], Mi, inverse, termContribution);
        }

        // Calculate final solution
        long finalSolution = solution % M;
        System.out.println("Final Calculation:");
        System.out.printf("Accumulated Solution: %d%n", solution);
        System.out.printf("Final Solution: %d mod %d = %d%n%n", solution, M, finalSolution);

        // Return the solution and the modulus
        return new long[]{finalSolution, M};
    }

    private static void testChineseRemainderTheorem(long[] remainders, long[] moduli) {
        try {
            System.out.println("=== Chinese Remainder Theorem Solution ===");
            long[] result = chineseRemainderTheorem(remainders, moduli);

            System.out.println("Verification:");
            for (int i = 0; i < remainders.length; i++) {
                long verification = result[0] % moduli[i];
                System.out.printf("  %d mod %d = %d (Expected: %d)%n",
                        result[0], moduli[i], verification, remainders[i]);
                if (verification != remainders[i]) {
                    System.out.println("  VERIFICATION FAILED!");
                }
            }

            System.out.println("\nFinal Result:");
            System.out.printf("x = %d (mod %d)%n%n", result[0], result[1]);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // 2020, day 13, part 2
        // 37,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,433,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,x,17,x,19,x,x,x,x,x,x,x,x,x,29,x,593,x,x,x,x,x,x,x,x,x,x,x,x,13
        testChineseRemainderTheorem(new long[]{0, 41 - 27, 433 - 37, 23 - 45, 17 - 54, 19 - 56, 29 - 66, 593 - 68, 13 - 81}, new long[]{37, 41, 433, 23, 17, 19, 29, 593, 13});
    }
}
