import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static int[][] pixels;
    public static int[][] pixelsde;
    public static int width = 0;
    public static int height = 0;
    public static int vheight = 0;
    public static int vwidth = 0;
    public static int num = 0;
    public static Node Root;

    public static Vector<Double> amatrix;
    public static Vector<Data> data = new Vector<Data>();
    public static String[] vv;
    public static Vector<String> vv1 = new Vector<String>();
    public static Vector<String> vv2 = new Vector<String>();

    public static Vector<Vector<Integer>> Vectors = new Vector<Vector<Integer>>();
    public static Vector<Vector<Integer>> decompress = new Vector<Vector<Integer>>();
    public static Vector<Vector<Integer>> decompressedcode = new Vector<Vector<Integer>>();
    //public static int get
    public static File file = new File("C:\\Users\\RS3\\Desktop\\outout.txt");
    public static File file1 = new File("C:\\Users\\RS3\\Desktop\\out.txt");

    public static void main(String[] args) throws IOException {

        readImage("C:\\Users\\ahmedelsayed\\Desktop\\photographer.bmp");
/*

        Scanner in = new Scanner(System.in);
        System.out.println("enter vector height:");
        vheight = in.nextInt();
        System.out.println("enter vector width:");
        vwidth = in.nextInt();

        System.out.println("enter vector number:");
        num = in.nextInt();
        */

       /* for(int x=0;x<20;x++)
        {
            for(int y=0;y<20;y++)
            {

                System.out.println(pixels[y][x]+ " ");

            }
            System.out.println("");
        }
        */


        createVectors();
        Root = new Node();
        Root.dataVec = getAverage(Vectors);
        Root.Vectors = Vectors;


        split(Root, num);

        createTableCodes(Root);
       // System.out.println("/////////////-----" + data.size());
        getCodes();
      //  System.out.println("/////////////-----" + data.size());
        //compress();

        String picture = read();
        vv = picture.split(" ");

        String dic = read1();

        decompress();

        returnPixels();

        System.out.println(decompressedcode.size() + "      ");


    }

    public static void compress() throws IOException {
        String[] compressedCodes = new String[Vectors.size()];
        for (int i = 0; i < Vectors.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                if (data.get(j).Vectors.contains(Vectors.get(i))) {
                    compressedCodes[i] = data.get(j).code;
                    writeinFile(compressedCodes[i]);
                    writeinFile(" ");
                    break;
                }
            }
        }
        for (int i = 0; i < data.size(); i++) {
            String s = "";
            writeinFile1(data.get(i).code);
            //writeinFile1("\n");
            for (int j = 0; j < data.get(i).vector.size(); j++) {
                s += data.get(i).vector.get(j);
                s += " ";
            }
            writeinFile1(s);
        }
    }

    public static void decompress() {
        for (int i = 0; i < vv2.size(); i++) {
            decompress.add(convertToVector(vv2.get(i)));
        }

        for (int i = 0; i < vv.length; i++) {
            int x = vv1.indexOf(vv[i]);
            decompressedcode.add(decompress.get(x));

        }

    }

    public static int[][] readImage(String filePath) {

        File file = new File(filePath);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        width = image.getWidth();
        height = image.getHeight();

        pixels = new int[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xff;

                pixels[y][x] = r;

            }
        }

        return pixels;
    }

    public static void createVectors() {
        for (int i = 0; i < height; i += vheight) {
            for (int j = 0; j < width; j += vwidth) {
                Vectors.add(new Vector<Integer>());
                for (int x = i; x < i + vheight; x++) {
                    for (int y = j; y < j + vwidth; y++) {
                        Vectors.lastElement().add(pixels[x][y]);
                    }
                }
            }
        }
    }

    public static void returnPixels() {
        int totalsize = decompressedcode.size() * decompress.get(0).size();
        int h = (int) Math.sqrt(totalsize);
        int w = (int) Math.sqrt(totalsize);

        int vs = (int) Math.sqrt(decompressedcode.get(0).size());

        System.out.println("++++++    " + vs);


        pixelsde = new int[h][w];
        int d = 0;
        for (int i = 0; i < h; i += vs) {
            for (int j = 0; j < w; j += vs) {
                int coun = 0;
                for (int x = i; x < i + vs; x++) {
                    for (int y = j; y < j + vs; y++) {
                        System.out.println("x:    " + x);
                        System.out.println("y:    " + y);
                        System.out.println("i:    " + i);
                        System.out.println("j:    " + j);
                        System.out.println("d:    " + d);
                        System.out.println("coun:    " + coun);
                        System.out.println(":::::::::::::::::::::::::: ");
                        pixelsde[x][y] = decompressedcode.get(d).get(coun);
                        coun++;
                    }
                }
                d++;
            }
        }

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.print(pixels[i][j] + " ");
                System.out.println("");
                System.out.print(pixelsde[i][j] + " ");

            }
        }

        writeImage(pixelsde, "C:\\Users\\ahmedelsayed\\Desktop\\Decompress.jpg", pixelsde[0].length, pixelsde.length);
        //getImage(pixels);


    }

    public static Vector<Double> getAverage(Vector<Vector<Integer>> Vectors) {
        amatrix = new Vector<Double>();
        for (int i = 0; i < vwidth * vheight; i++) {
            double x = 0.0;
            double y = 0.0;
            for (int j = 0; j < Vectors.size(); j++) {
                x += Vectors.get(j).get(i);
            }
            y = x / Vectors.size();
            amatrix.add(y);
        }
        return amatrix;
    }

    public static void split(Node root, int limit) {
        if (root != null) {
            if (getLeafCount(Root) == limit) {
            } else {
                Node node1 = new Node();
                Node node2 = new Node();
                root.left = node1;
                root.right = node2;

                root.avgVec = getAverage(root.Vectors);


                node1.dataVec = getLowMatrix(root);
                node2.dataVec = getHighMatrix(root);

                separateVectors(root);
                root.left.avgVec = getAverage(root.left.Vectors);
                root.right.avgVec = getAverage(root.right.Vectors);

                split(root.left, limit);
                split(root.right, limit);
            }
        } else {
            System.out.println(" empty tree ");
        }
    }

    public static int getLeafCount(Node node) {
        if (node == null)
            return 0;
        if (node.left == null && node.right == null)
            return 1;
        else
            return getLeafCount(node.left) + getLeafCount(node.right);
    }

    public static Vector<Double> getLowMatrix(Node node) {
        Vector<Double> matrix1 = new Vector<Double>();
        for (int i = 0; i < node.avgVec.size(); i++) {
            double d = node.avgVec.get(i);
            matrix1.add((int) d + 0.0);
        }
        return matrix1;
    }

    public static Vector<Double> getHighMatrix(Node node) {
        Vector<Double> matrix = new Vector<Double>();
        for (int i = 0; i < node.avgVec.size(); i++) {
            double d = node.avgVec.get(i);
            matrix.add((int) d + 1.0);
        }
        return matrix;
    }

    public static void separateVectors(Node root) {
        for (int i = 0; i < root.Vectors.size(); i++) {
            if (getDifferece(root.Vectors.get(i), root.left.dataVec) <
                    getDifferece(root.Vectors.get(i), root.right.dataVec)) {
                root.left.Vectors.add(root.Vectors.get(i));
            } else {
                root.right.Vectors.add(root.Vectors.get(i));
            }

        }
    }

    public static double getDifferece(Vector<Integer> checkVector, Vector<Double> vector) {
        double x = 0;
        for (int i = 0; i < checkVector.size(); i++) {
            x += Math.abs(checkVector.get(i) - vector.get(i));
        }
        return x;
    }

    public static void createTableCodes(Node root) {
        Data d;
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            d = new Data();
            System.out.println("/////////////-----" + root.avgVec);
            d.Vectors = root.Vectors;
            d.Roundvec(root.avgVec);
            data.add(d);
            System.out.println("/////////////-----" + data.size());

        }
        createTableCodes(root.left);
        createTableCodes(root.right);

    }

    public static void getCodes() {
        int size = log2(data.size());
        System.out.println("***************/////////////-----" + data.size());
        for (int i = 0; i < data.size(); i++) {
            System.out.println("/////////////-----" + data.size());
            int code = i;
            int a = 0, count = 0;
            String x = "";
            while (code > 0) {
                a = code % 2;
                if (a == 1) {
                    count++;
                }

                x = a + "" + x;
                code = code / 2;
            }

            //int sizee = the code digits numbmer
            String tmp = "";
            for (int j = 0; j < size - x.length(); j++) {
                tmp += "0";
            }
            tmp += x;

            data.get(i).code = tmp;
        }
    }

    public static int log2(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }

    public static void writeinFile(String content) throws IOException {
        FileWriter fr = new FileWriter(file, true);
        fr.write(content);
        fr.write("\n");
        fr.close();
    }

    public static void writeinFile1(String content) throws IOException {
        FileWriter fr = new FileWriter(file1, true);
        fr.write(content);
        fr.write("\n");
        fr.close();
    }

    public static String read() throws IOException {
        File file = new File("C:\\Users\\ahmedelsayed\\Desktop\\output.txt");
        String total = "";
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            total += sc.nextLine();
        }
        return total;
    }

    public static String read1() throws IOException {
        File file = new File("C:\\Users\\ahmedelsayed\\Desktop\\out.txt");
        String total = "";
        int i = 0;
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            if (i % 2 == 0) {
                vv1.add(sc.nextLine());
            } else {
                vv2.add(sc.nextLine());
            }
            //total += sc.nextLine();
            i++;
        }
        return total;
    }

    public static Vector<Integer> convertToVector(String in) {
        Vector<Integer> vec = new Vector<Integer>();
        String[] arr;
        arr = in.split(" ");
        for (int i = 0; i < arr.length; i++) {
            vec.add(Integer.valueOf(arr[i]));
        }
        return vec;
    }

    public static java.awt.Image getImage(int pi[][]) {
        int w = pi.length;
        int h = pi[0].length;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = (WritableRaster) image.getData();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                raster.setSample(i, j, 0, pi[i][j]);
            }
        }

        File output = new File("C:\\Users\\ahmedelsayed\\Desktop\\check.jpg");
        try {
            ImageIO.write(image, "jpg", output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    public static void writeImage(int[][] pixels,String outputFilePath,int width,int height)
    {
        File fileout=new File(outputFilePath);
        BufferedImage image2=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB );

        for(int x=0;x<width ;x++)
        {
            for(int y=0;y<height;y++)
            {
                image2.setRGB(x,y,(pixels[y][x]<<16)|(pixels[y][x]<<8)|(pixels[y][x]));
            }
        }
        try
        {
            ImageIO.write(image2, "jpg", fileout);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}