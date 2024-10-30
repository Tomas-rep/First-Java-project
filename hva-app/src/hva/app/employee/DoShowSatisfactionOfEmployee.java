package hva.app.employee;

import hva.Hotel;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.NoEmployeeKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowSatisfactionOfEmployee extends Command<Hotel> {

    DoShowSatisfactionOfEmployee(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_EMPLOYEE, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        Form request = new Form();
        try{
            request.addStringField("id_employee", Prompt.employeeKey());
            request.parse();

            _display.popup(_receiver.calculateSatisfactionEmployee(request.stringField("id_employee"), _receiver));
        }catch (NoEmployeeKeyException e){
            throw new UnknownEmployeeKeyException(e.getkey());
        }
    }

}
