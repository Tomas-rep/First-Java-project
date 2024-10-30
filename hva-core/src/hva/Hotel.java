package hva;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import hva.exceptions.TreeExistsException;
import hva.exceptions.NoHabitatKeyException;
import hva.exceptions.NoResponsibilityKeyException;
import hva.exceptions.NoSpecieKeyException;
import hva.exceptions.NoVaccineKeyException;
import hva.exceptions.AnimalExistsException;
import hva.exceptions.EmployeeExistsException;
import hva.exceptions.HabitatExistsException;
import hva.exceptions.ImportFileException;
import hva.exceptions.MissingFileAssociationException;
import hva.exceptions.NoAnimalKeyException;
import hva.exceptions.NoAuthorizedVetException;
import hva.exceptions.NoEmployeeKeyException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.VaccineExistsException;
import hva.exceptions.UnavailableFileException;


public class Hotel implements Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;


    //Atributes:

    /** Hotel object has been changed. */
    private boolean _changed = false;

    private Map<String, Habitat> _habitats = new HashMap<String, Habitat>();

    private Map<String, Specie> _species = new HashMap<String, Specie>();

    private Map<String, Employee> _employees = new HashMap<String, Employee>();

    private Map<String, Vaccine> _vaccines = new HashMap<String, Vaccine>();

    private List<Vaccination> _vaccinations = new ArrayList<Vaccination>();

    private List<Tree> _tempTrees = new ArrayList<Tree>();

    private Season _season = new Season();

    private int _num_seasons = 0;


    //Getters:

    /**
     * @return all habitats as an unmodifiable collection.
     */
    public Collection<Habitat> getHabitats() {
        return Collections.unmodifiableCollection(_habitats.values());
    }

    public Map<String, Employee> getEmployees() {
        return _employees;
    }

    public Map<String, Specie> getSpecies() {
        return _species;
    }


    /**
     * Set changed.
     */
    public void changed() {
        setChanged(true);
    }

    /**
     * @return changed
     */
    public boolean hasChanged() {
        return _changed;
    }

    /**
     * @param changed
     */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /**
     * @param id_employee
     * @param hotel
     * @return the satisfaction of an employee
     * @throws NoEmployeeKeyException
     */
    public int calculateSatisfactionEmployee(String id_employee, Hotel hotel) throws NoEmployeeKeyException{
        Employee employee = _employees.get(id_employee.toLowerCase());
        if(employee == null){
            throw new NoEmployeeKeyException(id_employee);
        }
        if (employee.getType().equals("TRT")){
            employee.setCalculator(new CalculatorSatisfactionKeeper());
        }
        if (employee.getType().equals("VET")){
            employee.setCalculator(new CalculatorSatisfactionVeterinarian());
        }
        return employee.CalculateSatisfaction(id_employee, this);
    }

    /**
     * @param name
     * @throws ImportFileException
     * Reads from the import file splitting the fields for each "|" it encounters, allowing
     * for easy registry of each class
     */
    public void importFile(String name) throws ImportFileException {
        try (BufferedReader reader = new BufferedReader(new FileReader(name))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                try {
                    registerEntry(fields);   
                } catch (MissingFileAssociationException | UnavailableFileException 
                | UnrecognizedEntryException | HabitatExistsException
                | AnimalExistsException | EmployeeExistsException 
                | VaccineExistsException | NoHabitatKeyException | NoSpecieKeyException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new ImportFileException(name);
        }
    }

    /**
     * @param fields
     * @throws UnrecognizedEntryException
     * @throws MissingFileAssociationException
     * @throws UnavailableFileException
     * @throws UnrecognizedEntryException
     * @throws HabitatExistsException
     * @throws AnimalExistsException
     * @throws EmployeeExistsException
     * Calls for the register function related to the first field (field[0])
     */
    public void registerEntry(String... fields) throws UnrecognizedEntryException, MissingFileAssociationException,
    UnavailableFileException, UnrecognizedEntryException, HabitatExistsException, AnimalExistsException,
    EmployeeExistsException, VaccineExistsException, NoHabitatKeyException, NoSpecieKeyException {
        switch (fields[0]) {
            case "HABITAT" -> registerHabitat(fields);
            case "ESPÉCIE" -> registerSpecie(fields);
            case "ANIMAL" -> registerAnimal(fields);
            case "TRATADOR" -> registerKeeper(fields);
            case "VETERINÁRIO" -> registerVeterinarian(fields);
            case "VACINA" -> registerVaccine(fields);
            case "ÁRVORE" -> registerTree(fields);
            //default -> throw new UnrecognizedEntryException(fields[0]);      FIXME
        }
    }

    /**
     * @return the current season
     */
    public int currentSeason(){
        return _season.getCode();
    }
    
    /**
     * @return advances season
     */
    public int advanceSeason(){
        _season.nextSeason();
        _num_seasons++;
        return currentSeason();
    }

    /**
     * @param fields 0-"HABITAT", 1- id, 2-name, 3 - area
     * @throws HabitatExistsException when there is already an habitat with the same id
     */
    public void registerHabitat(String... fields) throws HabitatExistsException {
        if (_habitats.containsKey(fields[1].toLowerCase())) {
            throw new HabitatExistsException(fields[1]);
        }
        Habitat habitat = new Habitat(fields[1], fields[2], Integer.valueOf(fields[3]));
        if(fields.length > 4 && !fields[4].isEmpty()){
            habitat = new Habitat(fields[1], fields[2], Integer.valueOf(fields[3]), fields[4]);
            String treesIdString = fields [4];
            String [] treeIds = treesIdString.split(",");
            List<Tree> treesToRemove = new ArrayList<Tree>();
            for (String treeId: treeIds) {
                for (Tree tree : _tempTrees) {
                    if (treeId.equals(tree.getId())) {
                        habitat.addCreatedTree(tree);
                        treesToRemove.add(tree);
                    }
                }
            }
            for (Tree treeToRemove : treesToRemove){
                _tempTrees.remove(treeToRemove);
            }
        }
        _habitats.put(fields[1].toLowerCase(),habitat);
    }

    /**
     * copy all ids to a list and sort it. Then add each corresponding habitat to a list
     * @return list of habitats.
    */
    public String showAllHabitats() {
        List<String> ids = new ArrayList<>(_habitats.keySet()); 
        Collections.sort(ids, String.CASE_INSENSITIVE_ORDER);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            Habitat habitat = _habitats.get(id);
            s.append(habitat.toString());  
            List<String> ids_tree = new ArrayList<>(habitat.getTrees().keySet());
            Collections.sort(ids_tree, String.CASE_INSENSITIVE_ORDER);
            for (int j = 0; j < ids_tree.size(); j++) {
                String treeId = ids_tree.get(j);
                Tree tree = habitat.getTrees().get(treeId);
                s.append("\n").append(tree.toString(currentSeason(), _num_seasons));
            }
            if (i != ids.size() - 1) {
                s.append("\n");
            }
        }  
        return s.toString();
    }

    /**
     * sets the area in an habitat to another value
     */
    public void changeHabitatArea(String idHabitat, int area) throws NoHabitatKeyException{
        if (!_habitats.containsKey(idHabitat.toLowerCase())){
            throw new NoHabitatKeyException(idHabitat);
        }
        _habitats.get(idHabitat.toLowerCase()).setArea(area);
    }

    /**
     * @param idAnimal
     * @return the habitat containing the correspondent animal to the id received
     * @throws NoHabitatKeyException
     */
    public Habitat getHabitatWAnimalId(String idAnimal) throws NoAnimalKeyException{
        for (Habitat habitat : _habitats.values()) {
            if (habitat.animalsinHabitat().containsKey((idAnimal.toLowerCase()))) {
                return habitat;
            }
        }
        throw new NoAnimalKeyException(idAnimal);
    }

    /**
     * @param idHabitat
     * @return the habitat based on its id
     * @throws NoHabitatKeyException
     */
    public Habitat getHabitatWHabitatId(String idHabitat) throws NoHabitatKeyException{
        if (_habitats.get(idHabitat.toLowerCase()) == null){
            throw new NoHabitatKeyException(idHabitat);
        }
        return _habitats.get(idHabitat.toLowerCase());
    }

    /**
     * Changes the influence of an habitat on a specie
     * @param id_habitat
     * @param id_specie
     * @param influence
     * @throws NoHabitatKeyException
     * @throws NoSpecieKeyException
     */
    public void changeInfluenceHabitatSpecie(String id_habitat, String id_specie, String influence) throws NoHabitatKeyException, NoSpecieKeyException{
        if(!_species.containsKey(id_specie.toLowerCase())){
            throw new NoSpecieKeyException(id_specie);
        }
        if(!_habitats.containsKey(id_habitat.toLowerCase())){
            throw new NoHabitatKeyException(id_habitat);
        }
        Habitat habitat = _habitats.get(id_habitat.toLowerCase());
        habitat.setNewInfluenceSpecie(id_specie, influence);
    }

    /**
     * @param fields 0 -"ÁRVORE", 1- id, 2- name, 3- age, 4- base cleaning
     * 
     */
    public void registerTree(String... fields) {
        if(fields[5].equals("PERENE")){
            Tree tree = new Perene(fields[1], fields [2], Integer.valueOf(fields[3]), Integer.valueOf(fields[4]), _num_seasons);    
            _tempTrees.add(tree);        
        }
        else if(fields[5].equals("CADUCA")) {
            Tree tree = new Caduca(fields[1], fields [2], Integer.valueOf(fields[3]), Integer.valueOf(fields[4]), _num_seasons);
            _tempTrees.add(tree);
        }
    }
 
    /**
     * @param fields 0- "ANIMAL", 1- id, 2-name, 3- specie id, 4-habitat id
     * @throws AnimalExistsException when there is already an animal with the same id
     */
    public void registerAnimal(String... fields) throws AnimalExistsException, NoHabitatKeyException {
        // enters in each habitat and checks if the animal already exists
        Habitat HABITAT = _habitats.get(fields[4].toLowerCase());
        if (HABITAT == null) {
            throw new NoHabitatKeyException(fields[4]);
        }

        for (Map.Entry<String, Habitat> entry : _habitats.entrySet()) { 
            Habitat habitat = entry.getValue();
            String id_habitat = habitat.getId();

            if (habitat.hasAnimal(fields[1])) {
                throw new AnimalExistsException(fields[1]);
            }
        }
        Animal animal = new Animal(fields[1], fields[2], fields[3], fields [4]);
        Habitat habitat = _habitats.get(fields[4].toLowerCase());
        habitat.addAnimal(fields[1],animal);
        Specie specie = _species.get(fields[3].toLowerCase());
        specie.addAnimal(fields[1], animal);
        changed();
    }

    /**
     * enters in all habitats and copy the animals ids to a list and sort it. Then add each corresponding animal to a list
     * @return list of animals
     */
    public List showAllAnimals(){
        Map<String, Animal> allanimals = new HashMap<String, Animal>();
        _habitats.forEach((id_habitat, habitat) -> 
        allanimals.putAll(habitat.animalsinHabitat()));
        List<String> ids = new ArrayList<String>(allanimals.keySet());
        List<Animal> animals = new ArrayList<Animal>();
        Collections.sort(ids, String.CASE_INSENSITIVE_ORDER);//ignore upper and lower case
        for (String id : ids){
            Animal animal = allanimals.get(id.toLowerCase());
            animals.add(animal);
        }
        return animals;
    }

    /**
     * transfers an animal from one habitat to another
     * @param idAnimal
     * @param idDestinationHabitat
     * @throws NoHabitatKeyException
     * @throws NoAnimalKeyException
     */
    public void transferAnimal(String idAnimal, String idDestinationHabitat) throws NoHabitatKeyException,
     NoAnimalKeyException{

        Animal animal = null; 
        Habitat destinationHabitat = _habitats.get(idDestinationHabitat.toLowerCase());
        int switch1 = 0;
        if (destinationHabitat == null) {
            throw new NoHabitatKeyException(idDestinationHabitat);
        }
        for (Habitat habitat : _habitats.values()){
            if (habitat.hasAnimal(idAnimal)) {
                switch1 = 1;
                animal = habitat.getAnimal(idAnimal);
                habitat.removeAnimal(idAnimal);
                break;
            }
        };
        if (switch1 == 0) {
            throw new NoAnimalKeyException(idAnimal);
        }
        animal.setIdHabitat(idDestinationHabitat);
        destinationHabitat.addAnimal(idAnimal, animal);
    }
    
    /**
     * @param fields 0-"ESPECIE", 1-id, 2-name
     */
    public void registerSpecie(String... fields) {
        Specie specie = new Specie(fields[1], fields[2]);
        _species.put(fields[1].toLowerCase(),specie);
        changed();
    }

    /**
     * @param id
     * @return true if the hotel contains a certain specie
     */
    public boolean hasSpecie(String id){
        if (_species.containsKey(id.toLowerCase())){
            return true;
        }
        return false;
    }

    /**
     * @param fields 0-"TRATADOR", 1-id, 2-name
     * @throws EmployeeExistsException
     */
    public void registerKeeper(String... fields) throws EmployeeExistsException {
        if (_employees.containsKey(fields[1].toLowerCase())) {
            throw new EmployeeExistsException(fields[1]);
        }
        if (fields.length == 4){
            Keeper keeper = new Keeper(fields[1], fields[2], fields[3]);
            _employees.put(fields[1].toLowerCase(),keeper);
        }
        else {
            Keeper keeper = new Keeper(fields[1], fields[2]);
            _employees.put(fields[1].toLowerCase(),keeper);
        }
        changed();
    }

     /**
     * @param fields 0-"VETERINARIO", 1-id, 2-name
     * @throws EmployeeExistsException
     */
    public void registerVeterinarian(String... fields) throws EmployeeExistsException {
        if (_employees.containsKey(fields[1].toLowerCase())) {
            throw new EmployeeExistsException(fields[1]);
        }
        if (fields.length == 4){
            Veterinarian veterinarian = new Veterinarian(fields[1], fields[2], fields[3]);
            _employees.put(fields[1].toLowerCase(),veterinarian);
        }
        else {
            Veterinarian veterinarian = new Veterinarian(fields[1], fields[2]);
            _employees.put(fields[1].toLowerCase(),veterinarian);
        }
        changed();
    }   

    /**
     * @return list of employess
     */
    public List showAllEmployees() {
        List<String> ids = new ArrayList<String>(_employees.keySet());
        List<Employee> employees = new ArrayList<Employee>();
        Collections.sort(ids, String.CASE_INSENSITIVE_ORDER);
        for (String id : ids){
            Employee EMPLOYEE = _employees.get(id);
            employees.add(EMPLOYEE);
        }
        return employees;
    }

    /**
     * Adds a responsability to its respective employee 
     * (habitat id for keeper, or a specie id for veterinarian)
     * @param id_employee
     * @param id_responsability
     * @throws NoResponsibilityKeyException
     * @throws NoEmployeeKeyException
     */
    public void addResponsabilityToEmployee(String id_employee, String id_responsability) 
    throws NoResponsibilityKeyException, NoEmployeeKeyException{
        if (_employees.get(id_employee.toLowerCase()) == null){
            throw new NoEmployeeKeyException(id_employee);
        }
        if (!_employees.get(id_employee.toLowerCase()).hasThisResponsability(id_responsability)){
            if (_employees.get(id_employee.toLowerCase()).getType().equals("VET")){
                if(!_species.containsKey(id_responsability.toLowerCase())){
                    throw new NoResponsibilityKeyException(id_responsability);
                }
            }
            if (_employees.get(id_employee.toLowerCase()).getType().equals("TRT")){
                if(!_habitats.containsKey(id_responsability.toLowerCase())){
                    throw new NoResponsibilityKeyException(id_responsability);
                }
            }

            _employees.get(id_employee.toLowerCase()).addResponsability(id_responsability);
        }
    }

    /**
     * Removes a responsability of its respective employee
     * (habitat id for keeper, or a specie id for veterinarian)
     * @param id_employee
     * @param id_responsability
     * @throws NoResponsibilityKeyException
     * @throws NoEmployeeKeyException
     */
    public void removeResponsabilityToEmployee(String id_employee, String id_responsability) 
    throws NoResponsibilityKeyException, NoEmployeeKeyException{
        if (_employees.get(id_employee.toLowerCase()) == null){
            throw new NoEmployeeKeyException(id_employee);
        }
        if (_employees.get(id_employee.toLowerCase()).hasThisResponsability(id_responsability)){
            if (_employees.get(id_employee.toLowerCase()).getType().equals("VET")){
                if(!_species.containsKey(id_responsability.toLowerCase())){
                    throw new NoResponsibilityKeyException(id_responsability);
                }
            }
            if (_employees.get(id_employee.toLowerCase()).getType().equals("TRT")){
                if(!_habitats.containsKey(id_responsability.toLowerCase())){
                    throw new NoResponsibilityKeyException(id_responsability);
                }
            }

            _employees.get(id_employee.toLowerCase()).removeResponsability(id_responsability);
        }
    }

    /**
     * @param fields 0-"VACINA", 1-id, 2-name, 3-species ids that can be applied to 
     * @throws VaccineExistsException when there is already a vaccine with the same id
     */
    public void registerVaccine(String... fields) throws VaccineExistsException, NoSpecieKeyException{
        for (String id_vaccine : _vaccines.keySet()){
            if (id_vaccine.toLowerCase().equals(fields[1].toLowerCase()))
                throw new VaccineExistsException(fields[1]);
        }
        if (fields.length > 3){    //if the vaccine is created with species
            String[] species = fields[3].split(",");
            for(String id_specie : species){
                if (!_species.containsKey(id_specie.toLowerCase())){
                    throw new NoSpecieKeyException(id_specie);
                }
            }
            Vaccine vaccine = new Vaccine(fields[1], fields[2], fields[3]);
            _vaccines.put(fields[1].toLowerCase(), vaccine);
        }
        else{   //if the vaccine is created without species
            Vaccine vaccine = new Vaccine(fields[1], fields[2]);
            _vaccines.put(fields[1].toLowerCase(), vaccine);
        }
        changed();
    }

    /**
     * copies all vaccines ids to a list and sort it. Then add each corresponding vaccine to a list
     * @return list of vaccines
     */
    public List showAllVaccines(){
        List<String> ids = new ArrayList<String>(_vaccines.keySet());
        List<Vaccine> vaccines = new ArrayList<Vaccine>();
        Collections.sort(ids, String.CASE_INSENSITIVE_ORDER);
        for(String id: ids){
            Vaccine vaccine = _vaccines.get(id.toLowerCase());
            vaccines.add(vaccine);
        }
        return vaccines;
    }

    /**
     * Adds a tree to a specific habitat
     * @param fields
     * @throws TreeExistsException
     * @throws NoHabitatKeyException
     */
    public void addTreeToHabitat(String... fields) throws TreeExistsException, NoHabitatKeyException{
        if(!_habitats.containsKey(fields[1].toLowerCase())){
            throw new NoHabitatKeyException(fields[1]);
        }
        else{
            for (Map.Entry<String, Habitat> entry : _habitats.entrySet()) { 
                Habitat habitat = entry.getValue();
                String id_habitat = habitat.getId();
        
                if (habitat.hasTree(fields[2])) {
                    throw new TreeExistsException(fields[2]);
                }
            }
            _habitats.get(fields[1].toLowerCase()).addTree(fields[6],fields[2],fields[3],Integer.valueOf(fields[4]),Integer.valueOf(fields[5]), _num_seasons);
        }
    }

    /**
     * @param habitatId
     * @param treeId
     * prints the newly added tree
     */
    public String printAddedTree(String habitatId, String treeId) {
        StringBuilder sb = new StringBuilder();
        Habitat habitat = _habitats.get(habitatId.toLowerCase());
        Tree addedTree = habitat.getTree(treeId);
        sb.append(addedTree.toString(currentSeason(), _num_seasons));
        return sb.toString();
    }

    /**
     * @param id_habitat
     * @return the string containing all the trees in a specific habitat
     * @throws NoHabitatKeyException
     */
    public String showAllTreesInHabitat(String id_habitat) throws NoHabitatKeyException{
        if (!_habitats.containsKey(id_habitat.toLowerCase())){
            throw new NoHabitatKeyException(id_habitat);
        }
        else{
            List<String> ids = new ArrayList<String>(_habitats.get(id_habitat.toLowerCase()).getTrees().keySet());
            Collections.sort(ids, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            int i = 1;
            for (String id: ids){
                Tree TREE = _habitats.get(id_habitat.toLowerCase()).getTrees().get(id.toLowerCase());
                sb.append(TREE.toString(currentSeason(), _num_seasons));
                if (i != _habitats.get(id_habitat.toLowerCase()).getTrees().size()) {sb.append("\n");}
                i++;
            }
        return sb.toString();
        }
    }

    /**
     * @return the sum of satisfactions of all animals in the hotel
     */
    public int calcAnimalsSatisfaction() {
        final double[] totalAnimalSatisfaction = {0.0};
        _habitats.forEach((id_habitat,habitat) -> {
            habitat.animalsinHabitat().forEach((id_animal, animal) -> {
                double animalSatisfaction = habitat.animalCalcSatisfaction(id_animal);
                totalAnimalSatisfaction[0] += animalSatisfaction;
            });
        });
        return (int) Math.round(totalAnimalSatisfaction[0]);
    }

        /**
     * @return  the sum of satisfactions of all employees in the hotel
     */
    public int calcGlobalEmployeeSatisfaction(){
        int global = 0;
        for( Map.Entry<String, Employee> entry : _employees.entrySet()){
            Employee employee = entry.getValue();
            if (employee.getType().equals("TRT")){
                employee.setCalculator(new CalculatorSatisfactionKeeper());
            }
            if (employee.getType().equals("VET")){
                employee.setCalculator(new CalculatorSatisfactionVeterinarian());
            }
            global += employee.CalculateSatisfaction(employee.getId(),this);

        }
        return global;
    }

    /**
     * Vaccinates the animal
     * @param fields
     * @return a boolean (true if the vaccine was adequate or false if it wasn't)
     * @throws NoEmployeeKeyException
     * @throws NoAuthorizedVetException
     * @throws NoAnimalKeyException
     * @throws NoVaccineKeyException
     */
    public boolean vaccinateAnimal(String... fields) throws NoEmployeeKeyException, 
    NoAuthorizedVetException, NoAnimalKeyException, NoVaccineKeyException{
        boolean suitable = true;
        if (!_employees.containsKey(fields[2].toLowerCase()) || _employees.get(fields[2].toLowerCase()).getType().equals("TRT"))
            throw new NoEmployeeKeyException(fields[2]);
        Habitat habitatOfAnimal = getHabitatWAnimalId(fields[3]); //Search for animal´s habitat
        Animal animal = habitatOfAnimal.getAnimal(fields[3]); // Find the animal in the Habitat
        String id_specie = animal.getIdSpecie();
        Vaccine vaccine = _vaccines.get(fields[1].toLowerCase());
        if (vaccine == null)
            throw new NoVaccineKeyException(fields[1]);
        if (!_employees.get(fields[2].toLowerCase()).hasThisResponsability(id_specie))
            throw new NoAuthorizedVetException(fields[2], id_specie);
        if (!vaccine.getSpecies().contains(id_specie))
            suitable = false;
        String temp = animal.vaccinate(vaccine, this);
        Vaccination vaccination = new Vaccination(fields[1], fields[3], fields[2], id_specie, temp);
        _vaccinations.add(vaccination);
        vaccine.addApplication();
        return suitable;
    }

    /**
     * @return the string containing all the vaccinations
     */
    public String showAllVaccinations(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < _vaccinations.size(); i++){
            sb.append(_vaccinations.get(i).toString());
            if (i != _vaccinations.size() - 1){
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * @param id_animal
     * @return the string containing all the vaccinations applied to a specific animal
     * @throws NoAnimalKeyException
     */
    public String showMedicalActsAnimal(String id_animal) throws NoAnimalKeyException{
        StringBuilder sb = new StringBuilder();
        if( getHabitatWAnimalId(id_animal) == null){
            throw new NoAnimalKeyException(id_animal);
        }
        for (int i = 0; i < _vaccinations.size(); i++){
            if (_vaccinations.get(i).getIdAnimal().equals(id_animal)){
                sb.append(_vaccinations.get(i).toString());
            }
            if (i != _vaccinations.size() - 1){
                sb.append("\n");
        
            }
        }
        return sb.toString();
    }

    /**
     * @param id_veterinarian
     * @return the string containing all the vaccinations given by a specific veterinarian
     * @throws NoEmployeeKeyException
     */
    public String showMedicalActsVeterinarian(String id_veterinarian) throws NoEmployeeKeyException{
        StringBuilder sb = new StringBuilder();
        Employee employee = _employees.get(id_veterinarian.toLowerCase());
        if( employee == null || employee.getType().equals("TRT")){
            throw new NoEmployeeKeyException(id_veterinarian);
        }
        for (int i = 0; i < _vaccinations.size(); i++){
            if (_vaccinations.get(i).getIdVeterinarian().equals(id_veterinarian)){
                sb.append(_vaccinations.get(i).toString());
            }
            if (i != _vaccinations.size() - 1){
                sb.append("\n");
        
            }
        }
        return sb.toString();
    }

    /**
     * @return the string containing all the vaccinations that were wrongly applied
     */
    public String showWrongVaccinations(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < _vaccinations.size(); i++){
            if (_vaccinations.get(i).getAdequation().equals("ACIDENTE") || 
            _vaccinations.get(i).getAdequation().equals("ERRO")){
                sb.append(_vaccinations.get(i).toString());
                if (i != _vaccinations.size() - 1){
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }


}
