import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Map extends JFrame {
    final int HEIGHT = 600;
    final int WIDTH = HEIGHT; //keeping it a square for now
    final int mappointsize = 10; //width and height of each block
    Long seed;
    BufferedImage mapimage;
    public Map(String seed){
        this.seed = stringToSeed(seed);
        setTitle("Map");
        setSize(WIDTH, HEIGHT);

        BuildMap();
        //add(this);
        //MainFrame.pack();
        setVisible(true);

        //save the most recently created map to a file
        try {
            ImageIO.write(mapimage, "png", new File("MapWithSeed-"+seed+".png"));
        } catch (IOException e) {

        }
    }
    private void BuildMap(){
        mapimage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

        Random random = new Random(seed);
        MapPoint array[][] = new MapPoint[60][60];

        for(int x = 0; x<array.length;x++){
            for(int y=0;y<array[0].length;y++){
                //try to get surrounding heights. increase/decrease by one.

                //for test, just set to random numbers:
                int low = -10;
                int high = 10;

                array[x][y]=new MapPoint(low + random.nextInt(high-low+1));

                //put array into buffered image
                //each mappoint will be 10x10 pixels
                for(int x1 = x*10; x1<((x*10)+10);x1++){
                    for(int y1=y*10;y1<((y*10)+10);y1++){
                        mapimage.setRGB(x1,y1,array[x][y].getColor().getRGB());
                    }
                }
            }
        }
    }
    public void paint(Graphics g) {
        g.drawImage(mapimage, 0, 0, this);
    }

    static long stringToSeed(String s) {
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
