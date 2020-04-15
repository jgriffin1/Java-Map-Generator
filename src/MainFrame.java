import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends  JFrame implements ActionListener {
    private JTextArea txtSeed;
    private JTextArea txtHeight;
    private JTextArea txtWidth;
    private JTextArea txtIconPercent;
    private JTextArea txtIterations;

    private JCheckBox chkWipe;

    private JButton btnCreate;
    private JButton btnRandomCreate;
    private JButton btnQuit;

    private JLabel lblSeed;
    private JLabel lblHeight;
    private JLabel lblWidth;
    private JLabel lblWipe;
    private JLabel lblIconPercent;
    private JLabel lblIterations;

    Map map;

    //final static int MAPHEIGHT = 600;
    //final static int MAPWIDTH = 800; //keeping it a square for now
    final static int SIDEBARSIZE=200;
    public MainFrame(){
        setTitle("Map");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setResizable(false);
        map = new  Map(800,2300);
        add(map,BorderLayout.WEST);
        //setSize(800,625);

        //setSize(getWidth()+SIDEBARSIZE,getHeight());

        JPanel pnlUI = new JPanel(new GridLayout(0,2));
        lblSeed=new JLabel("Seed:",SwingConstants.RIGHT);
        txtSeed=new JTextArea(String.valueOf(map.getSeed()));
        txtSeed.setBorder(new EmptyBorder(0, 0, 10, 0));

        lblHeight = new JLabel("Height:",SwingConstants.RIGHT);
        txtHeight = new JTextArea(String.valueOf(map.getMAPHEIGHT()));
        lblWidth = new JLabel("Width:",SwingConstants.RIGHT);
        txtWidth = new JTextArea(String.valueOf(map.getMAPWIDTH()));
        //lblWipe = new JLabel("Wipe Effect:");
        chkWipe = new JCheckBox("Wipe Effect");
        chkWipe.setHorizontalAlignment(JLabel.RIGHT);
        chkWipe.setHorizontalTextPosition(SwingConstants.LEFT);
        chkWipe.setSelected(map.getWIPEEFFECT());
        lblIconPercent = new JLabel("Percent Of Icons to show:",SwingConstants.RIGHT);
        txtIconPercent = new JTextArea(String.valueOf(map.getICONPERCENT()));
        lblIterations = new JLabel("Number of Iterations:",SwingConstants.RIGHT);
        txtIterations = new JTextArea(String.valueOf(map.getITERATIONS()));
        btnCreate = new JButton("Generate Custom Map");
        btnRandomCreate = new JButton("Generate Random Map");
        btnQuit = new JButton("Quit");
        btnCreate.addActionListener(this);
        btnQuit.addActionListener(this);
        btnRandomCreate.addActionListener(this);
        pnlUI.add(lblSeed);
        pnlUI.add(txtSeed);
        pnlUI.add(lblHeight);
        pnlUI.add(txtHeight);
        pnlUI.add(lblWidth);
        pnlUI.add(txtWidth);
        pnlUI.add(lblIconPercent);
        pnlUI.add(txtIconPercent);
        pnlUI.add(lblIterations);
        pnlUI.add(txtIterations);
        pnlUI.add(chkWipe);
        pnlUI.add(btnCreate);
        pnlUI.add(btnRandomCreate);
        pnlUI.add(btnQuit);
        add(pnlUI,BorderLayout.SOUTH);
        pack();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String resultStr;
        String str = String.valueOf(e.getActionCommand());
        if (str.equals("Generate Random Map")) {
            map.Generate(txtHeight.getText(), txtWidth.getText(), chkWipe.isSelected(), txtIterations.getText(), txtIconPercent.getText());
        }else if(str.equals("Generate Custom Map")){
            map.Generate(txtHeight.getText(), txtWidth.getText(), chkWipe.isSelected(), txtIterations.getText(), txtIconPercent.getText(),txtSeed.getText());
        }else if(str.equals("Quit")){
            System.exit(0);
        }
        UpdateTextBoxes();
        pack();
    }
    private void UpdateTextBoxes(){
        txtIconPercent.setText(String.valueOf(map.getICONPERCENT()));
        txtIterations.setText(String.valueOf(map.getITERATIONS()));
        txtWidth.setText(String.valueOf(map.getMAPWIDTH()));
        txtHeight.setText(String.valueOf(map.getMAPHEIGHT()));
        txtSeed.setText(String.valueOf(map.getSeed()));
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
