package hva;

import java.util.Map;
import java.util.TreeMap;

import hva.exceptions.NoAnimalKeyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.io.Serializable;


public class Habitat implements Serializable{
    /**Class serial number (serialization). */
    private static final long serialVersionUID = 202407081733L;

    private String _id = "";
    private String _name = "";
    private int _area;
    private Map<String, Tree> _trees = new HashMap<String, Tree>(); 
    private List<String> _temptrees = new ArrayList<String>();
    private Map<String, Animal> _animals = new HashMap<String, Animal>();
    private Map<String, String> _influenceInSpecies = new HashMap<String, String>();

    
    //Constructors:
    public Habitat(String id, String name, int area, String trees){
        _id = id;
        _name = name;
        _area = area;
        if (trees != "\n" || trees != ""){
            String [] fields = trees.split(",");
            for (int i = 0; i > fields.length; i++){
                _temptrees.add(fields[i]);
            }

        }
    }

    public Habitat(String id, String name, int area){
        _id = id;
        _name = name;
        _area = area;
    }


    //Getters:
    public String getId() {return _id;}

    public String getName() {return _name;}

    public int getArea() {return _area;}

    public Map<String, Tree> getTrees() {return _trees;}

    public Map<String, Tree> getTreesValues() {return _trees;}

    public int getNumTrees() {return _trees.size();}

    public Tree getTree(String treeId) {return _trees.get(treeId.toLowerCase());}

    public Map<String, String> getInfluenceSpecies() {return _influenceInSpecies;}

    public Animal getAnimal(String idAnimal) {
        return _animals.get(idAnimal.toLowerCase());
    }

    public int getSizeOfAnimals(){
        return _animals.size();
    }


    //Setters:
    public void setArea(int area) {_area = area;}

    public void setNewInfluenceSpecie(String id_specie, String influence) {_influenceInSpecies.put(id_specie.toLowerCase(), influence);}


    public boolean hasAnimal(String id){
        if (_animals.containsKey(id.toLowerCase())){
            return true;
        }
        return false;
    }

    public boolean hasTree(String id){
        if (_trees.containsKey(id.toLowerCase())){
            return true;
        }
        return false;
    }

    public boolean hasTree2(String id){
        if (_temptrees.contains(id.toLowerCase())){
            return true;
        }
        return false;
    }

    public void addAnimal(String id, Animal animal){
        _animals.put(id.toLowerCase(), animal);
    }

    public void removeAnimal(String id) {
        _animals.remove(id.toLowerCase());
    }

    public void addCreatedTree(Tree tree){
        _trees.put(tree.getId().toLowerCase(), tree);
    }

    public void  addTree(String type,String id, String name, int age, int baseClean, int currentSeason){
        switch(type){
            case "PERENE" -> {
                Perene tree = new Perene(id,name,age,baseClean, currentSeason);
                _trees.put(id.toLowerCase(), tree);
            }
            case "CADUCA" -> {
                Caduca tree = new Caduca(id,name,age,baseClean, currentSeason);
                _trees.put(id.toLowerCase(), tree);
            }
        }
    }

    public String toString() {
        return "HABITAT|" + getId() + "|" + getName() + "|" + getArea() + "|" + getNumTrees();
    }
    
    public Map<String,Animal> animalsinHabitat(){
        return _animals;
    }

    /**
     * @return the list of all the amimals in habitat
     */
    public List showAllAnimals(){
        Map<String, Animal> allanimals = new HashMap<String, Animal>();
        allanimals.putAll(this.animalsinHabitat());
        List<String> ids = new ArrayList<String>(allanimals.keySet());
        List<Animal> animals = new ArrayList<Animal>();
        Collections.sort(ids, String.CASE_INSENSITIVE_ORDER);
        for (String id : ids){
            Animal animal = allanimals.get(id);
            animals.add(animal);
        }
        return animals;
    }

    /**
     * @param idAnimal
     * @return the satisfaction of the an animal
     */
    public int animalCalcSatisfaction(String idAnimal) {      
        double satisfaction = 0.0;
        Animal animal = _animals.get(idAnimal.toLowerCase());
        int equalNumSpecies = countSameSpecies(animal);
        int differentNumSpecies = countDifferentSpecies(animal);
        int population = _animals.size();
        int adequation = calcAdequacy(animal);

        satisfaction = 20 + 3 * equalNumSpecies - 2 * differentNumSpecies + getArea() / population + adequation;
        return (int) satisfaction;
    }


    //Methods for the calculation of an animal's satisfaction:
    public int countSameSpecies(Animal animal) {
        int count = 0;
        for (Animal ANIMAL : _animals.values()) {
            if (!ANIMAL.equals(animal) && ANIMAL.getIdSpecie().equals(animal.getIdSpecie())) {
                count++;
            }
        }
        return count;
    }

    public int countDifferentSpecies(Animal animal) {
        int count = 0;
        for (Animal ANIMAL : _animals.values()) {
            if (!ANIMAL.getIdSpecie().equals(animal.getIdSpecie())) {
                count++;
            }
        }
        return count;
    }

    public int calcAdequacy(Animal animal) {
        String influence = _influenceInSpecies.get(animal.getIdSpecie().toLowerCase());
        if (influence != null) {
            switch (influence) {
                case "POS":
                    return 20;
                case "NEG":
                    return -20;
                case "NEU":
                    return 0;
                default:
                    return 0;
            }
        }
        else{
             return 0;
        }
    }
}