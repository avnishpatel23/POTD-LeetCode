// 2071. Maximum Number of Tasks You Can Assign

import java.util.*;

class Solution {
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        Arrays.sort(tasks);
        Arrays.sort(workers);

        int left = 0, right = Math.min(tasks.length, workers.length);
        int result = 0;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (canAssign(tasks, workers, pills, strength, mid)) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    private boolean canAssign(int[] tasks, int[] workers, int pills, int strength, int k) {
        // Hardest k tasks
        Deque<Integer> taskList = new ArrayDeque<>();
        for (int i = k - 1; i >= 0; i--) {
            taskList.add(tasks[i]);
        }

        // Strongest k workers (as TreeSet for efficient access)
        TreeMap<Integer, Integer> workerMap = new TreeMap<>();
        for (int i = workers.length - k; i < workers.length; i++) {
            workerMap.put(workers[i], workerMap.getOrDefault(workers[i], 0) + 1);
        }

        int pillsLeft = pills;

        while (!taskList.isEmpty()) {
            int task = taskList.pollFirst();

            // Try without pill
            Integer noPillWorker = workerMap.ceilingKey(task);
            if (noPillWorker != null) {
                removeFromMap(workerMap, noPillWorker);
                continue;
            }

            // Try with pill
            if (pillsLeft == 0) return false;

            Integer withPillWorker = workerMap.ceilingKey(task - strength);
            if (withPillWorker != null) {
                removeFromMap(workerMap, withPillWorker);
                pillsLeft--;
            } else {
                return false;
            }
        }

        return true;
    }

    private void removeFromMap(TreeMap<Integer, Integer> map, int key) {
        if (map.get(key) == 1) {
            map.remove(key);
        } else {
            map.put(key, map.get(key) - 1);
        }
    }
}
