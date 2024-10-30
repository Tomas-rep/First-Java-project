package hva;

import java.io.Serializable;

public abstract class Tree implements Serializable{

    /**Class serial number (serialization). */
    private static final long serialVersionUID = 202407081733L;

    private String _id = "";
    private String _name = "";
    private int _age;
    private int _BaseClean;
    private String _BioCycle;
    private int _firstSeason = 0; //know in wich season the tree was created


    //Constructor
    public Tree(String id, String name, int age, int BaseClean, int firstSeason){
        _id = id;
        _name = name;
        _age = age;
        _BaseClean = BaseClean;
        _firstSeason = firstSeason;
    }


    //Getters:
    public String getId(){return _id;}

    public String getName(){return _name;}

    public int getAge(){return _age;}

    public double calcAge(int currentSeason) {
        return (getAge() + (Math.floor((currentSeason - getFirstSeason()) / 4)));
    }

    public int getBaseClean(){return _BaseClean;}

    public abstract String getTypeTree();

    public abstract String getBioCycle(int seasoncode);

    public abstract int getSeasonalEffort(int seasoncode);

    public int getFirstSeason() {return _firstSeason;};


    public String toString(int codeSeason, int current_num_Season){
        return "ÁRVORE" + "|" + getId() + "|" + getName() + "|" + (int) calcAge(current_num_Season) + "|" + getBaseClean() + "|" + getTypeTree() + "|" + getBioCycle(codeSeason);
    }
}
