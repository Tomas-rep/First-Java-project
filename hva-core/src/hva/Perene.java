package hva;
import java.io.Serializable;
public class Perene extends Tree implements Serializable {
    
    public Perene(String id, String name, int age, int BaseClean, int currentSeason){
        super(id, name, age, BaseClean, currentSeason);
    }

    @Override
    public String getTypeTree(){
        return "PERENE";
    }

    @Override
    public String getBioCycle(int seasoncode) {
        switch (seasoncode) {
            case 3 : return "SEMFOLHAS";
            case 0 : return "GERARFOLHAS";
            case 1 : return "COMFOLHAS";
            case 2 : return "LARGARFOLHAS";
            default: return "";
        }
    }

    @Override
    public int getSeasonalEffort(int seasoncode) {
        switch (seasoncode) {
            case 3 : return 2;
            case 0 : return 1;
            case 1 : return 1;
            case 2 : return 1;
            default: return 0;
        }
    }

}
