import java.awt.*;
import java.util.ArrayList;

public class MapPoint {
    private int height; //from -10 to 10
    int x, y;
    /*
    my current idea is that the height will determine the color etc.
    Perhaps it generates in such a way that higher heights = whiter (snow) middleish = green "grass" lower = dark green/brown/water (idk... this is just a theory)
    From point to point, it can vary either 1-3 or something. Maybe if there's a few decreasing in a row, it'll be more likely to remain that way... we'll see what happens. `\_(^_^)_/`
     */
    public MapPoint(){

    }
    public MapPoint(int height, int x, int y){
        this.height = height;
        this.x = x;
        this.y = y;
    }
    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public void setHeight(ArrayList<Integer> SurroundingHeights, int AmountToMoveBy){
        //take the average of surrounding nubmers and add/subtract 1 or 0
        float average = 0;
        int counter = 0;
        for(int x : SurroundingHeights){
            average+=x;
            counter+=1;
        }
        average=average/counter;
        if(average<0){
            height= (int) (average - 1);
        }else if (average>0){
            height = (int)(average + 1);
        }
        //height = (int)(average) + AmountToMoveBy;

        //can't be higher than 10 or less than -10
        if(height>10) {
            height = 10;
        }else if(height<-10){
            height = -10;
        }

    }
    public Color getColor(){
        switch(height){
            //in the future, we could probably change this so that water level is higher/lower
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
