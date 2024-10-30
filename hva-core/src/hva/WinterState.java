package hva;
import java.io.Serializable;
public class WinterState extends SeasonState implements Serializable{
    public WinterState(Season season){
        super(season);
    }
    public void nextSeason(){
        getSeason().setSeason(new SpringState(getSeason()));
    }
    public int getcode(){
        return 3;
    }
}
