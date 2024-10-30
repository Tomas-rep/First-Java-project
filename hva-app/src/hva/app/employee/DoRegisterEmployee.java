package hva.app.employee;

import hva.Hotel;

import hva.app.exceptions.DuplicateEmployeeKeyException;
import hva.exceptions.EmployeeExistsException;
import java.lang.ref.Cleaner;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterEmployee extends Command<Hotel> {

    DoRegisterEmployee(Hotel receiver) {
        super(Label.REGISTER_EMPLOYEE, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try{
            Form request = new Form();
            request.addStringField("id", Prompt.employeeKey());
            request.addStringField("name", Prompt.employeeName());
            request.addOptionField("type", Prompt.employeeType(), "VET", "TRT");
            request.parse();

            switch (request.stringField("type")) {
                case "TRT" -> _receiver.registerKeeper(new String [] {
                    "KEEPER",
                    request.stringField("id"),
                    request.stringField("name")
                });
                case "VET" -> _receiver.registerVeterinarian(new String [] {
                    "VETERINARIAN",
                    request.stringField("id"),
                    request.stringField("name")
                });
            }
            
        } catch (EmployeeExistsException e) {
            throw new DuplicateEmployeeKeyException(e.getkey());
        }
    }

}
