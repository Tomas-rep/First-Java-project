package hva;
import java.io.Serializable;
public class Caduca extends Tree implements Serializable {
    public Caduca(String id, String name, int age, int baseClean, int currentSeason){
        super(id, name, age, baseClean, currentSeason);
    }

    @Override
    public String getTypeTree(){
        return "CADUCA";
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
            case 3 : return 0;
            case 0 : return 1;
            case 1 : return 2;
            case 2 : return 5;
            default: return 0;
        }
    }
}
