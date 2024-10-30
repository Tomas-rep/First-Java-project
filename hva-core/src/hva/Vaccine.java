package hva;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Vaccine implements Serializable{

    private String _id = "";
    private String _name = "";
    private int _numberOfApp = 0;
    private List<String> _species = new ArrayList<String>();

    /**Class serial number (serialization). */
    private static final long serialVersionUID = 202407081733L;    
    

    //Constructors:
    public Vaccine(String id, String name, String species){
        _id = id;
        _name = name;
        //Splits the input of the species string in vaccine and
        //adds them into the _species arrayList that contains
        //the species ids
        if (species != "\n" || species != ""){
            String [] fields = species.split(",");
            for (int i = 0; i < fields.length; i++){
                _species.add(fields[i]);
            }
        }
    }

    public Vaccine(String id, String name){
        _id = id;
        _name = name;
    }


    //Getters
    public String getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public String getSpecies(){
        return String.join(",",getSortedSpecies());
    }

    /**
     * @return list of species
     */
    public List<String> getSortedSpecies() {
        List<String> ids = new ArrayList<String>(_species);
        Collections.sort(ids, String.CASE_INSENSITIVE_ORDER);
        return ids;
    }

    public int getApplications(){
        return _numberOfApp;
    }

    //Adds 1 to the counter of applications of this specific vaccine
    public void addApplication(){
        _numberOfApp++;
    }

    
    public String toString(){
        String species = getSpecies();
        if (species.isEmpty()){
            String string = "VACINA|" + getId() + "|" + getName() + "|" + getApplications();
            return string;
        }
        else{
            String string = "VACINA|" + getId() + "|" + getName() + "|" + getApplications() + "|" + getSpecies();
            return string;
        }
    }
}
