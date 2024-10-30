package hva;

public class Keeper extends Employee {

    private String _type = "TRT";

    //Constructors:
    public Keeper(String id, String name){
        super(id, name);
    }

    public Keeper(String id, String name, String responsabilities){
        super(id, name, responsabilities);
    }
    

    //Getter:
    @Override
    public String getType() {
        return _type;
    }


    @Override
    public void addResponsability(String id_habitat) {
        super.addResponsability(id_habitat);
    }


    public String toString() {
        if (hasResponsibilities()) {
            return getType() + "|" + getId() + "|" + getName() + "|" + getResponsabilities();
        }
        return getType() + "|" + getId() + "|" + getName();
    }
}
