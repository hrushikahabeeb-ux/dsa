import java.util.*;

class HuffmanNode {
    char ch;
    int freq;
    HuffmanNode left, right;

    HuffmanNode(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
    }
}

class HuffmanComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.freq - y.freq;
    }
}

public class HuffmanCoding {

    static Map<Character, String> huffmanCodes = new HashMap<>();

    // Generate Huffman Codes
    public static void generateCodes(HuffmanNode root, String code) {
        if (root == null)
            return;

        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.ch, code);
        }

        generateCodes(root.left, code + "0");
        generateCodes(root.right, code + "1");
    }

    // Encode the text
    public static String encode(String text) {
        StringBuilder encoded = new StringBuilder();

        for (char c : text.toCharArray()) {
            encoded.append(huffmanCodes.get(c));
        }

        return encoded.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the message: ");
        String text = sc.nextLine();

        // Step 1: Calculate frequencies
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        // Step 2: Create priority queue
        PriorityQueue<HuffmanNode> pq =
                new PriorityQueue<>(new HuffmanComparator());

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Step 3: Build Huffman Tree
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();

            HuffmanNode newNode = new HuffmanNode('$',
                    left.freq + right.freq);

            newNode.left = left;
            newNode.right = right;

            pq.add(newNode);
        }

        HuffmanNode root = pq.poll();

        // Step 4: Generate Huffman codes
        generateCodes(root, "");

        // Step 5: Encode message
        String compressed = encode(text);

        // Display results
        System.out.println("\nCharacter Codes:");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.println(entry.getKey()
                    + " : " + entry.getValue());
        }

        System.out.println("\nOriginal Message: " + text);
        System.out.println("Encoded Message: " + compressed);

        int originalSize = text.length() * 8;
        int compressedSize = compressed.length();

        System.out.println("\nOriginal Size: "
                + originalSize + " bits");

        System.out.println("Compressed Size: "
                + compressedSize + " bits");

        System.out.println("Space Saved: "
                + (originalSize - compressedSize)
                + " bits");

        sc.close();
    }
}