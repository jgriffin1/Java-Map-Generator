import java.awt.*;
import java.util.ArrayList;

public class MapPoint {
    private int height; //from -10 to 10
    int x, y;
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
    public void setHeight(ArrayList<Integer> SurroundingHeights){
        //take the average of surrounding numbers and add/subtract 1 if average is pos/neg. (Make high points higher, low points lower)
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
            case(-4):return new Color(17, 83, 189); //dark blue water
            case(-3):
            case(-2):return new Color(105, 143, 184); //lighter blue
            case(-1):
            case(0):
            case(1):return new Color(233, 196, 140); //tan-ish
            case(2):return new Color(20, 100, 20); //darkish green
            case(3):return new Color(22, 92, 8); //darkish green
            case(4):return new Color(103, 161, 92); //less healthy green
            case(5):return new Color(130, 170, 130); //less healthy green
            case(6):return new Color(169, 181, 168); //green-grey
            case(7):
            case(8):return new Color(139, 151, 138); //green-grey
            case(9):return new Color(100, 100, 100); //gray mountain

            case(10):return new Color(204, 204, 204); //white snow
            default: return new Color(0,0,0);//just black.. idk This isn't suppsoed to be a collor that happens at this point
        }
    }


}
