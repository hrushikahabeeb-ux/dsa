class SegmentTree {

    int[] tree;
    int[] arr;
    int n;

    SegmentTree(int[] input) {
        arr = input;
        n = input.length;

        tree = new int[4 * n];

        build(1, 0, n - 1);
    }

    void build(int node, int start, int end) {

        if (start == end) {
            tree[node] = arr[start];
            return;
        }

        int mid = (start + end) / 2;

        build(2 * node, start, mid);
        build(2 * node + 1, mid + 1, end);

        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    int query(int node, int start, int end, int left, int right) {

        // No overlap
        if (right < start || end < left) {
            return 0;
        }

        // Complete overlap
        if (left <= start && end <= right) {
            return tree[node];
        }

        // Partial overlap
        int mid = (start + end) / 2;

        int leftSum = query(2 * node, start, mid, left, right);
        int rightSum = query(2 * node + 1, mid + 1, end, left, right);

        return leftSum + rightSum;
    }

    void update(int node, int start, int end, int index, int value) {

        if (start == end) {
            arr[index] = value;
            tree[node] = value;
            return;
        }

        int mid = (start + end) / 2;

        if (index <= mid) {
            update(2 * node, start, mid, index, value);
        } else {
            update(2 * node + 1, mid + 1, end, index, value);
        }

        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }

    int rangeSum(int left, int right) {
        return query(1, 0, n - 1, left, right);
    }

    void updateValue(int index, int value) {
        update(1, 0, n - 1, index, value);
    }

    public static void main(String[] args) {

        int[] arr = {1, 3, 5, 7, 9, 11};

        SegmentTree st = new SegmentTree(arr);

        System.out.println("Sum from index 1 to 3:");
        System.out.println(st.rangeSum(1, 3));
        // 3 + 5 + 7 = 15

        st.updateValue(1, 10);
        // array becomes {1, 10, 5, 7, 9, 11}

        System.out.println("After update, sum from index 1 to 3:");
        System.out.println(st.rangeSum(1, 3));
        // 10 + 5 + 7 = 22
    }
}