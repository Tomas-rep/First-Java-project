package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.DuplicateVaccineKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.NoSpecieKeyException;
import hva.exceptions.VaccineExistsException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterVaccine extends Command<Hotel> {

    DoRegisterVaccine(Hotel receiver) {
        super(Label.REGISTER_VACCINE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            Form request = new Form();
            request.addStringField("id", Prompt.vaccineKey());
            request.addStringField("name", Prompt.vaccineName());
            request.addStringField("species", Prompt.listOfSpeciesKeys());
            request.parse();

            _receiver.registerVaccine(new String[] {
                "VACINA",
                request.stringField("id"),
                request.stringField("name"),
                request.stringField("species")
            });
        }catch(VaccineExistsException e){
            throw new DuplicateVaccineKeyException(e.getkey());
        }catch(NoSpecieKeyException e){
            throw new UnknownSpeciesKeyException(e.getkey());
        }
    }

}
