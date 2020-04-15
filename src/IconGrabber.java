import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class IconGrabber {
    long seed;
    Random random;
    public IconGrabber(Random r){
        this.seed =seed;
        random = r;
    }
    public IconGrabber(){

    }
    public String get(icon i){
        int low =1;
        int high = 0;
        int randomNumber;
        switch (i){
            case SmallMountain:
                high = 4;
                randomNumber = low + random.nextInt(high-low+1);
                return "Icons/mountain"+randomNumber+".png";
            case BigMountain:
                high = 2;
                randomNumber = low + random.nextInt(high-low+1);
                return "Icons/mountainBig"+randomNumber+".png";
            case Palmtree:
                //high = 1;
                //randomNumber = low + random.nextInt(high-low+1);
                return "Icons/palm1.png";//+randomNumber+".png";
            case Pinetree:
                high = 3;
                randomNumber = low + random.nextInt(high-low+1);
                return "Icons/pine"+randomNumber+".png";
            case Plaintree:
                high = 3;
                randomNumber = low + random.nextInt(high-low+1);
                return "Icons/tree"+randomNumber+".png";
            case WHALE:
                return "Icons/whale6.png";
            case Compass:
                return "Icons/compass.png";
            case SirLuka:
                return "Icons/SirLuka.png";
            case None:
            default: return null;
        }
    }
//    public static BufferedImage get(icon i){
//        switch (i){
//            case BigMountain: return BigMountain;
//            case SmolMountain: return SmolMountain;
//            case TallTree: return Tree;
//            case AppleTree: return appletree;
//            case PalmTree: return PalmTree;
//            case EverGreen: return evergreen;
//            case Oak: return oak;
//            case None:
//            default: return null;
//        }
//    }
    enum icon{
        BigMountain,SmallMountain,Palmtree,Pinetree,Plaintree,WHALE,None,SirLuka,Compass;
    }
}
