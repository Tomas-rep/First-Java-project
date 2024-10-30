package hva;
import java.io.Serializable;
public class FallState extends SeasonState implements Serializable {
    public FallState(Season season){
        super(season);
    }
    public void nextSeason(){
        getSeason().setSeason(new WinterState(getSeason()));
    }
    public int getcode(){
        return 2;
    }
}
