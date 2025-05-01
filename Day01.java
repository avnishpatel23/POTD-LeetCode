// 1295. Find Numbers with Even Number of Digits

public class Solution {
    public int findNumbers(int[] nums) {
        int count = 0;
        for (int num : nums) {
            if (numDigits(num) % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    private int numDigits(int num) {
        int digits = 0;
        while (num != 0) {
            num /= 10;
            digits++;
        }
        return digits;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.findNumbers(new int[]{12, 345, 2, 6, 7896})); // Output: 2
        System.out.println(sol.findNumbers(new int[]{555, 901, 482, 1771})); // Output: 1
    }
}