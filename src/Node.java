import java.util.Vector;

public class Node {
    public Node left;
    public Node right;
    public Vector<Double> avgVec;
    public Vector<Double> dataVec;
    public Vector<Vector<Integer>> Vectors;

    public Node() {
        left = null;
        right = null;
        avgVec = new Vector<Double>();
        dataVec = new Vector<Double>();
        Vectors = new Vector<Vector<Integer>>();
    }
}
