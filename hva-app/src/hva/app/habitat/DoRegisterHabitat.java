package hva.app.habitat;

import hva.Hotel;

import hva.app.exceptions.DuplicateHabitatKeyException;
import hva.exceptions.HabitatExistsException;
import hva.exceptions.UnrecognizedEntryException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRegisterHabitat extends Command<Hotel> {

    DoRegisterHabitat(Hotel receiver) {
        super(Label.REGISTER_HABITAT, receiver);
        //FIXME add command fields if needed
    }

    @Override
    protected void execute() throws CommandException {
        try{
            Form request = new Form();
            request.addStringField("id", Prompt.habitatKey());
            request.addStringField("name", Prompt.habitatName());
            request.addStringField("area", Prompt.habitatArea());
            request.parse();

            _receiver.registerHabitat(new String[] {
                "HABITAT",
                request.stringField("id"),
                request.stringField("name"),
                request.stringField("area")
            });
        } catch(HabitatExistsException e){
            throw new DuplicateHabitatKeyException(e.getkey());
        }
    }

}
