import java.util.Vector;

public class Data {
    public String code;
    public Vector<Integer> vector;
    public Vector<Vector<Integer>> Vectors;

    public Data() {
        code = "";
        vector = new Vector<Integer>();
        Vectors = new Vector<Vector<Integer>>();//

    }
    public void Roundvec(Vector<Double> vec)
    {
        for (int i=0; i<vec.size(); i++)
        {
            double z1 = vec.get(i);
            int z = (int)z1;
            vector.add(z);

        }

    }

}
