package hva;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

public class Animal implements Serializable{

    /**Class serial number (serialization). */
    private static final long serialVersionUID = 202407081733L;

    private String _id = "";
    private String _name = "";
    private String _idSpecie = "";
    private List<String> _healthHistory = new ArrayList<String>();
    private String _idHabitat = "";

    
    //Constructor:
    public Animal(String id, String name, String idSpecie,
    String idHabitat){
        _id = id;
        _name = name;
        _idSpecie = idSpecie;
        _idHabitat = idHabitat;
    }


    //Getters:
    public String getId() {return _id;}

    public String getName() {return _name;}

    public String getIdHabitat() {return _idHabitat;}

    public String getIdSpecie() {return _idSpecie;}

    public List<String> getHealthHistory() {return _healthHistory;}


    //Setter:
    public void setIdHabitat(String idHabitat) {_idHabitat = idHabitat;} 
    

    /**
     * @param vaccine
     * @param hotel
     * @return the damage caused by a vaccine to an animal
     */
    public int calculateDamage(Vaccine vaccine, Hotel hotel) {
        String id_specie = this.getIdSpecie();
        String name_specie = hotel.getSpecies().get(id_specie.toLowerCase()).getName();
        //Checks if the vaccine is adequate
        if (vaccine.getSortedSpecies().contains(id_specie)) { 
            return 0;
        }

        int maxDamage = 0;
        //Iterates through every specie of the vaccine and calculates the max damage
        for (Map.Entry<String, Specie> entry : hotel.getSpecies().entrySet()) { 
            Specie specie = entry.getValue();
            String id_specie2 = specie.getId();
            if (vaccine.getSpecies().contains(id_specie2)){
                int damage = maxNameDifference(name_specie, specie.getName());
                maxDamage = Math.max(maxDamage, damage);
                }
            }
        return maxDamage;
    }


    //Methods for the calculation of a vaccination damage to an animal
    public int countCommonCharacters(String species1, String species2) {
        Set<Character> commonCharacters = new HashSet<>();
        //Adds the characters of the first string to a HashSet
        Set<Character> set1 = new HashSet<>();
        for (char c : species1.toCharArray()) {
            set1.add(c);
        }
        //Adds the characters of the second string to a HashSet
        for (char c : species2.toCharArray()) { 
            if (set1.contains(c) && !commonCharacters.contains(c)) {
                commonCharacters.add(c);
            }
        }
        return commonCharacters.size();
    }

    public int maxNameDifference(String species1, String species2) { 
        int length1 = species1.length();
        int length2 = species2.length();

        int commonChars = countCommonCharacters(species1, species2);

        return (Math.max(length1, length2) - commonChars);
    }

    /**
     * @param vaccine
     * @param hotel
     * @return the damage done to the animal, and vaccinates it
     */
    public String vaccinate(Vaccine vaccine, Hotel hotel){
        int damage = calculateDamage(vaccine, hotel);

        if(damage == 0 && vaccine.getSortedSpecies().contains(this.getIdSpecie())){
            _healthHistory.add("NORMAL");
            return("NORMAL");
        }
        else if(damage == 0){
            _healthHistory.add("CONFUSÃO");
            return("CONFUSÃO");
        }
        else if(damage > 0 && damage <= 4){
            _healthHistory.add("ACIDENTE");
            return("ACIDENTE");
        }
        else if(damage >= 5){
            _healthHistory.add("ERRO");
            return("ERRO");
        }
        return "";
    }


    //Returns the health history of the animal with commas between the aplications
    public String showHealthHistory() {
        return String.join(",",_healthHistory);
    }

    public String toString() {
        if(getHealthHistory().size() == 0){
            return "ANIMAL|" + getId() + "|" + getName() + "|" + getIdSpecie() + "|"
            + "VOID" + "|" + getIdHabitat();
        }
        else{
            return "ANIMAL|" + getId() + "|" + getName() + "|" + getIdSpecie() + "|"
            + showHealthHistory() + "|" + getIdHabitat();
        }
    }
}
