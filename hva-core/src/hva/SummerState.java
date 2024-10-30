package hva;
import java.io.Serializable;
public class SummerState extends SeasonState implements Serializable {
    public SummerState(Season season){
        super(season);
    }
    public void nextSeason(){
        getSeason().setSeason(new FallState(getSeason()));
    }
    public int getcode(){
        return 1;
    }
}
