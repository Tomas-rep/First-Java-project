package hva;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Employee implements Serializable{

    /**Class serial number (serialization). */
    private static final long serialVersionUID = 202407081733L;

    private String _id = "";
    private String _name = "";
    private List<String> _responsability = new ArrayList<String>();
    private CalculatorSatisfaction _calculator;


    //Constructors:
    public Employee(String id,String name) {
        _id = id;
        _name = name;
    }

    public Employee(String id, String name, String responsabilities){
        _id = id;
        _name = name;
        String[] fields = responsabilities.split(",");
        for (int i = 0; i < fields.length; i++){
            addResponsability(fields[i]);
        }
    }


    //Getters:
    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public abstract String getType();

    public String getResponsabilities() {
        Collections.sort(_responsability, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for(String responsability : _responsability){
            sb.append(responsability);
            if (i!= _responsability.size()){
                sb.append(",");
            }
            i++;
        }
        return sb.toString();
    }

    //Setters:
    public void setId(String id) {
        _id = id;
    }

    public void setName(String name) {
        _name = name;
    }

    public void setCalculator(CalculatorSatisfaction calculator){
        _calculator = calculator;
    }

    //Responsability related methods:
    public boolean hasThisResponsability(String id_responsability){
        return _responsability.contains(id_responsability);
    }

    public boolean hasResponsibilities(){
        return (_responsability.size() != 0);
    }
    public void addResponsability(String id_responsability){
        _responsability.add(id_responsability);
    }

    public void removeResponsability(String id_responsability){
        _responsability.remove(id_responsability);
    }

    /**
     * @param id_employee
     * @param hotel
     * @return the satiscation of the employee
     */
    public int CalculateSatisfaction(String id_employee, Hotel hotel){
        return _calculator.satisfactionEmployee(id_employee, hotel);
    }
}
