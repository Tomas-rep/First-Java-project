package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.NoResponsibilityException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.NoEmployeeKeyException;
import hva.exceptions.NoResponsibilityKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRemoveResponsibility extends Command<Hotel> {

    DoRemoveResponsibility(Hotel receiver) {
        super(Label.REMOVE_RESPONSABILITY, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        Form request = new Form();
        try{
            request.addStringField("id_employee", Prompt.employeeKey());
            request.addStringField("id_responsibility", Prompt.responsibilityKey());
            request.parse();

            _receiver.removeResponsabilityToEmployee(request.stringField("id_employee"), request.stringField("id_responsibility"));
        }catch (NoEmployeeKeyException e){
            throw new UnknownEmployeeKeyException(e.getkey());
        }catch (NoResponsibilityKeyException e){
            throw new NoResponsibilityException(request.stringField("id_employee"), request.stringField("id_responsibility"));
        }
    }

}
