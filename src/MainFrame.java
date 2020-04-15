import javax.swing.*;
        import java.awt.*;

public class MainFrame extends  JFrame {
    private JTextField txtSeed;
    private JButton btnCreate;
    private JButton btnQuit;

    final static int MAPHEIGHT = 600;
    final static int MAPWIDTH = 800; //keeping it a square for now
    final static int SIDEBARSIZE=200;
    public MainFrame(){
        setTitle("Map");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Map(MAPHEIGHT,MAPWIDTH));
        //setSize(800,625);
        pack();
        setSize(getWidth()+SIDEBARSIZE,getHeight());

        //JPanel pnlButtons = new JPanel();
        //txtSeed=new JTextField();


    }

    public static void main(String[]args){
        //Map testmap = new Map("test");
        //Map testmap = new Map(String.valueOf(System.currentTimeMillis()));
        // randomSeed = String.valueOf(System.currentTimeMillis());
        MainFrame mf = new MainFrame();
        //JPanel jp = new Map(randomSeed,6,false, 600,600);
        //mf.getContentPane().add(jp);
        //f.setSize(WIDTH+SIDEBARSIZE, HEIGHT);

    }
}
