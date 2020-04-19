import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
/*
 * Created by JFormDesigner on Wed Dec 18 10:01:15 EET 2019
 */



/**
 * @author mohamed ALAA ELDIN ELKILANY
 */
public class GUI extends JFrame {
    public GUI() {
        initComponents();
    }
    public static void main(String[] args) throws IOException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    private void btncompressActionPerformed(ActionEvent e) throws IOException {

        String width = txtwidth.getText();
        String height = txtheigt.getText();
        String code = txtcode.getText();
      //  String name = txtimage.getText();
        Main.readImage("C:\\Users\\RS3\\Desktop\\test.jpg");
        Main.vwidth =Integer.parseInt( width);
        Main.vheight =Integer.parseInt( height);
        Main.num =Integer.parseInt( code);
        Main.createVectors();
        Main.Root = new Node();
        Main.Root.dataVec = Main.getAverage(Main.Vectors);
        Main.Root.Vectors = Main.Vectors;

        Main.split(Main.Root, Main.num);
        Main.createTableCodes(Main.Root);
        Main.getCodes();
        Main.compress();
     }

     private void btndecompressActionPerformed(ActionEvent e) throws IOException {
         String width = txtwidth.getText();
         String height = txtheigt.getText();
         String code = txtcode.getText();
         Main.vwidth =Integer.parseInt( width);
         Main.vheight =Integer.parseInt( height);
         Main.num =Integer.parseInt( code);

         String picture = Main.read();
         Main.vv = picture.split(" ");

         String dic = Main.read1();

         Main.decompress();

         Main.returnPixels();
     }

    private void initComponents() {
        /*
         JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
         Generated using JFormDesigner Evaluation license - mohamed ALAA ELDIN ELKILANY
        */
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        btncompress = new JButton();
        btndecompress = new JButton();
        scrollPane1 = new JScrollPane();
        txtwidth = new JTextArea();
        txtheigt = new JTextArea();
        txtcode = new JTextArea();
        txtimage = new JTextArea();
        label4 = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("vector width");
        contentPane.add(label1);
        label1.setBounds(25, 15, 100, 30);

        //---- label2 ----
        label2.setText("vector height");
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(25, 65), label2.getPreferredSize()));

        //---- label3 ----
        label3.setText("code book");
        contentPane.add(label3);
        label3.setBounds(25, 105, 80, 25);

        //---- btncompress ----
        btncompress.setText("compress");
        btncompress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btncompressActionPerformed(e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        contentPane.add(btncompress);
        btncompress.setBounds(new Rectangle(new Point(80, 230), btncompress.getPreferredSize()));

        //---- btndecompress ----
        btndecompress.setText("decompres");
        btndecompress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btndecompressActionPerformed(e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        contentPane.add(btndecompress);
        btndecompress.setBounds(new Rectangle(new Point(230, 230), btndecompress.getPreferredSize()));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(txtwidth);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(125, 20, 145, 25);
        contentPane.add(txtheigt);
        txtheigt.setBounds(125, 60, 145, 25);
        contentPane.add(txtcode);
        txtcode.setBounds(125, 105, 145, 25);
        contentPane.add(txtimage);
        txtimage.setBounds(125, 145, 145, 25);

        //---- label4 ----
        label4.setText("image name");
        contentPane.add(label4);
        label4.setBounds(25, 145, 80, 25);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        //        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - mohamed ALAA ELDIN ELKILANY
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JButton btncompress;
    private JButton btndecompress;
    private JScrollPane scrollPane1;
    private JTextArea txtwidth;
    private JTextArea txtheigt;
    private JTextArea txtcode;
    private JTextArea txtimage;
    private JLabel label4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
