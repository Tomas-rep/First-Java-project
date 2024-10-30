package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.exceptions.NoEmployeeKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowMedicalActsByVeterinarian extends Command<Hotel> {

    DoShowMedicalActsByVeterinarian(Hotel receiver) {
        super(Label.MEDICAL_ACTS_BY_VET, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        Form request = new Form();
        try{
            request.addStringField("id_veterinarian", hva.app.employee.Prompt.employeeKey());
            request.parse();

            _display.popup(_receiver.showMedicalActsVeterinarian(request.stringField("id_veterinarian")));
        }catch (NoEmployeeKeyException e){
            throw new UnknownVeterinarianKeyException(e.getkey());
        }
    }

}
