package hva;

public class CalculatorSatisfactionKeeper implements CalculatorSatisfaction {
    //Implementation of the calculation method for the satisfaction of a keeper
    @Override
    public int satisfactionEmployee(String id_employee, Hotel hotel){
        double satisfaction = 300.0;
        double work = 0.0;
        double work_habitat = 0.0;
        Employee employee  = hotel.getEmployees().get(id_employee.toLowerCase());
        for (Habitat habitat : hotel.getHabitats()){
            if (employee.hasThisResponsability(habitat.getId())){
                int[] n_keepers_habitat = {0};
                int area = habitat.getArea();
                int population = habitat.getSizeOfAnimals();
                double[] cleaning_effort = {0.0};
                habitat.getTrees().forEach((id_tree, tree) -> {
                    cleaning_effort[0] = tree.getBaseClean() * tree.getSeasonalEffort(hotel.currentSeason()) 
                        * Math.log(tree.getAge() + 1);
                });
                work_habitat = area + 3*population + cleaning_effort[0];
                hotel.getEmployees().forEach((id_employee2, EMPLOYEE) -> {
                    if (EMPLOYEE.getType().equals("TRT")){
                        if(EMPLOYEE.hasThisResponsability(habitat.getId())){
                            n_keepers_habitat[0]++;   
                        }    
                    }
                });
                work += work_habitat/n_keepers_habitat[0];
            }
        }
        satisfaction -= work;
        return (int) Math.round(satisfaction);
    }
}
