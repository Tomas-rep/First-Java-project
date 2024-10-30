package hva;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Specie implements Serializable {

    /**Class serial number (serialization). */
    private static final long serialVersionUID = 202407081733L;    

    private String _id;
    private String _name;
    private Map<String, Animal> _animals = new TreeMap<String, Animal>();


    //Constructor:
    public Specie(String id, String name){
        _id = id;
        _name = name;
    }


    //Getters:
    public String getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public Map<String,Animal> getAnimalsOfSpecie() {
        return _animals;
    }


    //Adds an animal to a list of animals with only its specific species
    public void addAnimal(String id_animal, Animal animal){
        _animals.put(id_animal.toLowerCase(), animal);
    }
}
