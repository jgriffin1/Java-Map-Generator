import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Map extends JPanel {
    private boolean WIPEEFFECT;
    private int ITERATIONS;
    private float ICONPERCENT;
    private Long seed;
    private BufferedImage mapimage;
    private MapPoint array[][];
    private Random random;
    private ArrayList allInARow;
    private int MAPHEIGHT,MAPWIDTH;
    private int PIXELSHIGH,PIXELSWIDE;
    private int timercounter;
    Timer timer;
    //JFrame mainFrame;
    //private

    public Map(int h, int w){
        //this(String.valueOf(System.currentTimeMillis()),8,false, Toolkit.getDefaultToolkit().getScreenSize().height-100, Toolkit.getDefaultToolkit().getScreenSize().width-100,50);
        this(String.valueOf(System.currentTimeMillis()),8,true, h, w,50);
    }
    public Map(String seed, int ITERATIONS,boolean WIPEEFFECT, int HEIGHT, int WIDTH, float ICONPERCENT){
        this.seed = stringToSeed(seed);
        this.ITERATIONS=ITERATIONS;
        this.WIPEEFFECT=WIPEEFFECT;
        this.MAPHEIGHT=(HEIGHT-(HEIGHT%10));//make a multiple of 10
        this.MAPWIDTH=(WIDTH-(WIDTH%10));
        this.PIXELSHIGH=HEIGHT/10;
        this.PIXELSWIDE=WIDTH/10;
        this.ICONPERCENT = fixIconPercent(ICONPERCENT);
        timer = new Timer(10,null);
        timer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                for(int i =0;i<PIXELSHIGH;i++){
                    if(timercounter>=allInARow.size()){
                        AddIcons();
                        timer.stop();
                        return;
                    }
                    AddMapPixel((MapPoint)allInARow.get(timercounter));
                    timercounter+=1;
                }

            }
        });
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setLayout(new BorderLayout());
        DoAllTheThings();


    }

    public void DoAllTheThings(){
        timercounter= 0;
        BuildMap();
        if(WIPEEFFECT){
            timer.start();
        }else{
            for(int i =allInARow.size()-PIXELSHIGH*PIXELSWIDE;i<allInARow.size();i++){
                AddMapPixel((MapPoint)allInARow.get(i));
            }
            AddIcons();
        }

        //save the most recently created map to a file
        try {
            ImageIO.write(mapimage, "png", new File("Maps/MapWithSeed-"+seed+".png"));
            ImageIO.write(mapimage, "png", new File("MostRecentMap.png"));
        } catch (IOException e) {

        }
    }
    private void BuildMap(){
        allInARow=new ArrayList();
        mapimage = new BufferedImage(MAPWIDTH,MAPHEIGHT,BufferedImage.TYPE_INT_RGB);

        random = new Random(seed);
         array = new MapPoint[PIXELSWIDE][PIXELSHIGH];

        //region make everything random
        for(int x = 0; x<array.length;x++){
            for(int y=0;y<array[0].length;y++){
                int low = -10;
                int high = 10;
                MapPoint mp = new MapPoint(low + random.nextInt(high-low+1),x,y,random);
                allInARow.add(new MapPoint(mp));
                array[x][y]=mp;
                //AddMapPixel(array[x][y]);

            }
        }
        //endregion
        for(int z = 0; z<ITERATIONS;z++){

            //region not random
            ArrayList surroundingHeights = new ArrayList();
            for(int x = 0; x<array.length;x++){
                for(int y=0;y<array[0].length;y++){
                    //get surrounding heights to increase/decrease by one.
                    //
                    //say it looks like this:   5 10 3
                    //                          3 x  5
                    //                          8 -8 3
                    //I will make x +- 1 of the average of those. Given it's possible there won't be something above or below.. but that's why god gave us Try Catch
                    surroundingHeights.clear();
                    for(int i = x-1; i<=x+1;i++){
                        for(int j = y-1;j<=y+1;j++){

                            try{
                                    surroundingHeights.add(array[i][j].getHeight());
                            }catch(Exception e){
                                //when the point is on the edge, there will be an out of bounds exception. we can ignore it when that happens.
                            }
                        }
                    }
                    array[x][y].setHeight(surroundingHeights);
                    //AddMapPixel(array[x][y]);
                    allInARow.add(new MapPoint(array[x][y]));
                }
            }
            //endregion
        }
    }
    private void AddIcons(){
        try{
//
            BufferedImage source = mapimage;
            Graphics g = source.getGraphics();
            for(int x = 0; x<array.length;x+=1){
                for(int y=0;y<array[0].length;y+=1) {
                    try{
                        int low = 0;
                        int high = 100;
                        int randomNumber = low + random.nextInt(high-low+1);
                        if((array[x][y].getIcon() != null)&&(randomNumber>(100-ICONPERCENT))){
                            int low2=-4;
                            int high2=4;
                            int randomNumber2 = low2 + random.nextInt(high2-low2+1);
                            g.drawImage((BufferedImage)ImageIO.read(new File(array[x][y].getIcon())), x*10+5+randomNumber2, y*10-20+randomNumber2, null);
                            repaint();
                        }

                    }catch(Exception ex){

                    }
                }
            }
            IconGrabber i = new  IconGrabber();
            BufferedImage SirLuka = ImageIO.read(new File("Icons/SirLuka.png"));
            BufferedImage Compass = ImageIO.read(new File("Icons/compass.png"));
            Compass=resize(Compass,75,75);
            BufferedImage name = ImageIO.read(new File("Icons/mapname.png"));
            name=resize(name,20,180);
            g.drawImage(SirLuka,10,getMAPHEIGHT()-110,null);
            g.drawImage(Compass,20,50,null);
            g.drawImage(name,60,getMAPHEIGHT()-35,null);

        }catch(Exception e){

        }
    }
    private void AddMapPixel(MapPoint mp){
        for(int x1 = mp.x*10; x1<((mp.x*10)+10);x1++){
            for(int y1=mp.y*10;y1<((mp.y*10)+10);y1++){
                mapimage.setRGB(x1,y1,mp.getColor().getRGB());
            }
        }

        repaint();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapimage, 0, 0, this);
    }
    private static long stringToSeed(String s) {
        try{
            return Long.valueOf(s);
        }catch(Exception e){
            if (s == null) {
                return 0;
            }
            long hash = 0;
            for (char c : s.toCharArray()) {
                hash = 31L*hash + c;
            }
            return hash;
        }

    }
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    public static float fixIconPercent(float f){
        if(f<0){ return 0;
        }else if (f>100) return 100;
        else return f;
    }
    public Long getSeed() {
        return seed;
    }
    public void setSeed(Long seed) {
        this.seed = seed;
        DoAllTheThings();
    }
    public void setSeed(String seed) {
        this.seed = stringToSeed(seed);
        DoAllTheThings();
    }
    public int getMAPHEIGHT() {
        return MAPHEIGHT;
    }
    public void setMAPHEIGHT(String s) {
        int n;
        try{
            n=Integer.parseInt(s);
            n=(n-n%10);
        }catch(Exception e){
            n=600;
        }
        if(n<400){
            n=400;
        }else if(n>2000){
            n=2000;
        }
        this.MAPHEIGHT= n;
        this.PIXELSHIGH=n/10;
        //DoAllTheThings();
    }
    public int getMAPWIDTH() {
        return MAPWIDTH;
    }
    public void setMAPWIDTH(String s) {
        int n;
        try{
            n=Integer.parseInt(s);
            n=(n-n%10);
        }catch(Exception e){
            n=800;
        }
        if(n<400){
            n=400;
        }else if(n>2000){
            n=2000;
        }

        this.MAPWIDTH = n;
        this.PIXELSWIDE=n/10;
        //DoAllTheThings();
    }
    public boolean getWIPEEFFECT() {
        return WIPEEFFECT;
    }
    public void setWIPEEFFECT(boolean WIPEEFFECT) {
        this.WIPEEFFECT = WIPEEFFECT;
        //DoAllTheThings();
    }
    public int getICONPERCENT() {
        return (int)ICONPERCENT;
    }
    public void setICONPERCENT(String s) {
        float n;
        try{
             n= Float.parseFloat(s);
            if(n>100){
                n=100;
            }else if(n<0){
                n=0;
            }
        }catch(Exception e){
            n=0;
        }
        this.ICONPERCENT = n;
        //DoAllTheThings();
    }
    public int getITERATIONS() {
        return ITERATIONS;
    }
    public void setITERATIONS(String s) {
        int n;
        try{
            n=Integer.parseInt(s);
        }catch(Exception e){
            n=8;
        }
        if(n<0){
            n=0;
        }else if (n>50){
            n=50;
        }
        this.ITERATIONS = n;
        //DoAllTheThings();
    }
    public void Generate(String h, String w, boolean wipe,String it,String ic){
        timer.stop();
        this.setMAPHEIGHT(h);
        this.setMAPWIDTH(w);
        this.setWIPEEFFECT(wipe);
        this.setITERATIONS(it);
        this.setICONPERCENT(ic);
        this.setSeed(String.valueOf(System.currentTimeMillis()));
        setPreferredSize(new Dimension(getMAPWIDTH(),getMAPHEIGHT()));
        DoAllTheThings();
    }
    public void Generate(String h, String w, boolean wipe,String it,String ic,String seed){
        timer.stop();
        this.setMAPHEIGHT(h);
        this.setMAPWIDTH(w);
        this.setWIPEEFFECT(wipe);
        this.setITERATIONS(it);
        this.setICONPERCENT(ic);
        this.setSeed(seed);
        setPreferredSize(new Dimension(getMAPWIDTH(),getMAPHEIGHT()));
        DoAllTheThings();
    }
}
