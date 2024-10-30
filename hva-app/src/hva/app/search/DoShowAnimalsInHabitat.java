package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.NoHabitatKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAnimalsInHabitat extends Command<Hotel> {

    DoShowAnimalsInHabitat(Hotel receiver) {
        super(Label.ANIMALS_IN_HABITAT, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        Form request = new Form();
        try{

            request.addStringField("id_habitat", hva.app.habitat.Prompt.habitatKey());
            request.parse();

            _display.popup(_receiver.getHabitatWHabitatId(request.stringField("id_habitat")).showAllAnimals());
        }catch(NoHabitatKeyException e){
            throw new UnknownHabitatKeyException(e.getkey());
        }
    }

}
