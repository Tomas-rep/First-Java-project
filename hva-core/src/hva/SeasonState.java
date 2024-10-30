package hva;
import java.io.Serializable;
public abstract class SeasonState implements Serializable {
    private Season _season;


    //Constructor:
    public SeasonState(Season season){
        _season = season;
    }


    //Getters:
    public Season getSeason(){
        return _season;
    }

    public abstract int getcode();

    
    //Advances season
    public abstract void nextSeason();

}
