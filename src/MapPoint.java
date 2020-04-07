import java.awt.*;

public class MapPoint {
    private int height; //from -10 to 10
    /*
    my current idea is that the height will determine the color etc.
    Perhaps it generates in such a way that higher heights = whiter (snow) middleish = green "grass" lower = dark green/brown/water (idk... this is just a theory)
    From point to point, it can vary either 1-3 or something. Maybe if there's a trend, it'll remain that way... we'll see what happens. `\_(^_^)_/`
     */
    public MapPoint(){

    }
    public MapPoint(int height){
        this.height = height;
    }
    public int getHeight(){
        return height;
    }
    public Color getColor(){
        switch(height){
            //-10 to -3 = darkblue
            case(-10):
            case(-9):
            case(-8):
            case(-7):
            case(-6):
            case(-5):
            case(-4):
                return new Color(17, 83, 189); //dark blue water
            case(-3):
            case(-2):
                return new Color(105, 143, 184); //lighter blue

            case(-1):
                return new Color(233, 196, 140); //tan-ish

            case(0):
            case(1):
                return new Color(22, 92, 8); //darkish green
            case(2):
            case(3):
                return new Color(103, 161, 92); //less healthy green
            case(4):
            case(5):
            case(6):
                return new Color(169, 181, 168); //green-grey
            case(7):
            case(8):
            case(9):
                return new Color(63, 69, 62); //gray mountain
            case(10):
                return new Color(204, 204, 204); //white snow
            default:
                return new Color(0,0,0);//just black.. idk This isn't suppsoed to be a collor that happens at this point
        }
    }


}
