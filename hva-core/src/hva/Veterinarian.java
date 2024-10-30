package hva;

import java.util.ArrayList;
import java.util.List;

public class Veterinarian extends Employee {

    private String _type = "VET";


    //Constructors:
    public Veterinarian(String id, String name){
        super(id,name);
    }

    public Veterinarian(String id, String name, String responsabilities){
        super(id, name, responsabilities);
    }


    //Getter
    @Override
    public String getType() {
        return _type;
    }

    @Override
    public void addResponsability(String id_specie) {
        super.addResponsability(id_specie);
    }

    public String toString() {
        if (hasResponsibilities()) {
            return getType() + "|" + getId() + "|" + getName() + "|" + getResponsabilities();
        }
        return getType() + "|" + getId() + "|" + getName();
    }
}