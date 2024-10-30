package hva;
import java.io.Serializable;
public class Season implements Serializable {
    private SeasonState _seasonState = new SpringState(this);


    //Getter:
    public int getCode(){
        return _seasonState.getcode();
    }

    
    //Setter:
    public void setSeason(SeasonState seasonState){
        _seasonState = seasonState;
    }


    //Advances Season
    public void nextSeason(){
        _seasonState.nextSeason();
    }

}
