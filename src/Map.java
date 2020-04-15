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
    boolean WIPEEFFECT;
    int ITERATIONS;
    float ICONPERCENT;
    Long seed;
    BufferedImage mapimage;
    MapPoint array[][];
    Random random;
    ArrayList allInARow;

    int MAPHEIGHT,MAPWIDTH;
    int PIXELSHIGH,PIXELSWIDE;
    //JFrame mainFrame;
    //private
    public Map(int h, int w){
        //this(String.valueOf(System.currentTimeMillis()),8,false, Toolkit.getDefaultToolkit().getScreenSize().height-100, Toolkit.getDefaultToolkit().getScreenSize().width-100,50);
        this(String.valueOf(System.currentTimeMillis()),8,false, h, w,50);
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

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setLayout(new BorderLayout());
        DoAllTheThings();


    }
    public static float fixIconPercent(float f){
        if(f<0){ return 0;
        }else if (f>100) return 100;
        else return f;
    }
    int test1 = 0;
    public void DoAllTheThings(){
        BuildMap();
        if(WIPEEFFECT){
            Timer timer = new Timer(10,null);
            timer.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    for(int i =0;i<PIXELSHIGH;i++){
                        if(test1>=allInARow.size()){
                            AddIcons();
                            timer.stop();
                            return;
                        }
                        AddMapPixel((MapPoint)allInARow.get(test1));
                        test1+=1;
                    }

                }
            });
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
            g.drawImage(SirLuka,10,490,null);
            g.drawImage(Compass,20,50,null);
            g.drawImage(name,60,565,null);

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
        if (s == null) {
            return 0;
        }
        long hash = 0;
        for (char c : s.toCharArray()) {
            hash = 31L*hash + c;
        }
        return hash;
    }
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
