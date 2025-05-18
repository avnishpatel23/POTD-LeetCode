// 1931. Painting a Grid With Three Different Colors

class Solution {
    private int gridHeight;

    public int colorTheGrid(int m, int n) {
        this.gridHeight = m;
        final int MOD = (int) 1e9 + 7;
        int maxStateValue = (int) Math.pow(3, m);
        Set<Integer> validStates = new HashSet<>();
        int[] dpStateCounts = new int[maxStateValue];

        for (int state = 0; state < maxStateValue; ++state) {
            if (isValidState(state)) {
                validStates.add(state);
                dpStateCounts[state] = 1;
            }
        }

        Map<Integer, List<Integer>> validTransitions = new HashMap<>();

        for (int i : validStates) {
            for (int j : validStates) {
                if (isTransitionValid(i, j)) {
                    validTransitions.computeIfAbsent(i, k -> new ArrayList<>()).add(j);
                }
            }
        }

        for (int column = 1; column < n; ++column) {
            int[] nextStateCounts = new int[maxStateValue];
            for (int currentState : validStates) {
                for (int nextState : validTransitions.getOrDefault(currentState, List.of())) {
                    nextStateCounts[currentState] = (nextStateCounts[currentState] + dpStateCounts[nextState]) % MOD;
                }
            }
            dpStateCounts = nextStateCounts;
        }

        int answer = 0;
        for (int count : dpStateCounts) {
            answer = (answer + count) % MOD;
        }
        return answer;
    }

    private boolean isValidState(int state) {
        int lastColor = -1;
        for (int i = 0; i < gridHeight; ++i) {
            int currentColor = state % 3;
            if (currentColor == lastColor) {
                return false;
            }
            lastColor = currentColor;
            state /= 3;
        }
        return true;
    }

    private boolean isTransitionValid(int stateOne, int stateTwo) {
        for (int i = 0; i < gridHeight; ++i) {
            if (stateOne % 3 == stateTwo % 3) {
                return false;
            }
            stateOne /= 3;
            stateTwo /= 3;
        }
        return true;
    }
}
