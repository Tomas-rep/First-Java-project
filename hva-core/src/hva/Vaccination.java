package hva;

public class Vaccination {
    private String _idvaccine;
    private String _idanimal;
    private String _idveterinarian;
    private String _idspecies;
    private String _adequation;


    //Constructor:
    public Vaccination(String idvaccine, String idanimal, String idveteranian, String idspecies, String adequation){
        _idvaccine = idvaccine;
        _idanimal = idanimal;
        _idveterinarian = idveteranian;
        _idspecies = idspecies;
        _adequation = adequation;
    }


    //Getters:
    public String getIdVaccine(){
        return _idvaccine;
    }

    public String getIdAnimal(){
        return _idanimal;
    }

    public String getIdVeterinarian(){
        return _idveterinarian;
    }

    public String getIdSpecies(){
        return _idspecies;
    }

    public String getAdequation(){
        return _adequation;
    }

    
    public String toString(){
        return "REGISTO-VACINA|" + getIdVaccine() + "|" + getIdVeterinarian() + "|" + getIdSpecies(); 
    }
}
