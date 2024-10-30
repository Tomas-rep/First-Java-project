package hva;
import java.io.Serializable;
public class SpringState extends SeasonState implements Serializable {
    public SpringState(Season season){
        super(season);
    }
    public void nextSeason(){
        getSeason().setSeason(new SummerState(getSeason()));
    }

    public int getcode(){
        return 0;
    }
}

