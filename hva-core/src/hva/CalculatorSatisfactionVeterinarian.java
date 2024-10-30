package hva;

public class CalculatorSatisfactionVeterinarian implements CalculatorSatisfaction{
    //Implementation of the calculation method for the satisfaction of a veterinarian
    @Override
    public int satisfactionEmployee(String id_employee, Hotel hotel){
        double satisfaction = 20.0;
        double[] work = {0.0};
        Employee employee = hotel.getEmployees().get(id_employee.toLowerCase());
        hotel.getSpecies().forEach((id_specie, specie) -> {
            int[] n_vet = {0};
            int[] population = {0};
            if(employee.hasThisResponsability(specie.getId())){
                population[0] = specie.getAnimalsOfSpecie().size();
                hotel.getEmployees().forEach((id_employee2, EMPLOYEE) -> {
                    if(EMPLOYEE.getType().equals("VET")){
                        if(EMPLOYEE.hasThisResponsability(specie.getId())){
                            n_vet[0]++;
                        }
                    }
                });
                work[0] += population[0]/n_vet[0] ;
            }
        });
        satisfaction -= work[0];
        return (int) Math.round(satisfaction);
    }
}
