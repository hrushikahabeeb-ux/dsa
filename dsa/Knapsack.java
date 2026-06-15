public class Knapsack {

    public static void main(String[] args) {

        // Assigned values
        int[] weights = {10, 20, 30};
        int[] values = {60, 100, 120};
        int capacity = 50;

        int n = values.length;

        // DP Table
        int[][] dp = new int[n + 1][capacity + 1];

        // Fill DP Table
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {

                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(
                            values[i - 1] + dp[i - 1][w - weights[i - 1]],
                            dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Print Tabulation Table
        System.out.println("Capacity ->\t0\t10\t20\t30\t40\t50");

        for (int i = 0; i <= n; i++) {
            System.out.print("Item " + i + " ->\t");

            for (int w = 0; w <= capacity; w += 10) {
                System.out.print(dp[i][w] + "\t");
            }

            System.out.println();
        }

        System.out.println("\nMaximum Value = " + dp[n][capacity]);
    }
}