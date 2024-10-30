package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.NoHabitatKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowAllTreesInHabitat extends Command<Hotel> {

    DoShowAllTreesInHabitat(Hotel receiver) {
        super(Label.SHOW_TREES_IN_HABITAT, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try{
            Form request = new Form();
            request.addStringField("id_habitat", Prompt.habitatKey());
            request.parse();
            _display.popup(_receiver.showAllTreesInHabitat(request.stringField("id_habitat")));
        }catch(NoHabitatKeyException e){
            throw new UnknownHabitatKeyException(e.getkey());
        }
    }

}
