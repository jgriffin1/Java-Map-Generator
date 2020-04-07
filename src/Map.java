import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Map extends JFrame {
    final boolean WIPEEFFECT = false;
    final int ITERATIONS = 6;
    final int HEIGHT = 600;
    final int WIDTH = HEIGHT; //keeping it a square for now
    Long seed;
    BufferedImage mapimage;

    public Map(String seed){
        this.seed = stringToSeed(seed);
        setTitle("Map");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        BuildMap();

        //save the most recently created map to a file
        try {
            ImageIO.write(mapimage, "png", new File("Maps/MapWithSeed-"+seed+".png"));
            ImageIO.write(mapimage, "png", new File("MostRecentMap.png"));
        } catch (IOException e) {

        }
    }
    private void BuildMap(){
        mapimage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

        Random random = new Random(seed);
        MapPoint array[][] = new MapPoint[60][60];

        //region make everything random
        for(int x = 0; x<array.length;x++){
            for(int y=0;y<array[0].length;y++){
                int low = -10;
                int high = 10;
                array[x][y]=new MapPoint(low + random.nextInt(high-low+1),x,y);
                AddMapPixel(array[x][y]);

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
                    int low = -1;
                    int high = 1;
                    array[x][y].setHeight(surroundingHeights);
                    AddMapPixel(array[x][y]);
                }
            }
            //endregion
        }
    }

    private void AddMapPixel(MapPoint mp){
        for(int x1 = mp.x*10; x1<((mp.x*10)+10);x1++){
            for(int y1=mp.y*10;y1<((mp.y*10)+10);y1++){
                mapimage.setRGB(x1,y1,mp.getColor().getRGB());
            }
        }
        repaint();
        if(WIPEEFFECT){
            try{
                Thread.sleep(1);
            }catch(Exception e){
            }
        }
    }
    public void paint(Graphics g) {
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
}
