package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.NoHabitatKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoChangeHabitatArea extends Command<Hotel> {

    DoChangeHabitatArea(Hotel receiver) {
        super(Label.CHANGE_HABITAT_AREA, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try {
            Form request = new Form();

            request.addStringField("id_habitat", Prompt.habitatKey());
            request.addIntegerField("new_area", Prompt.habitatArea());
            request.parse();

            _receiver.changeHabitatArea(request.stringField("id_habitat"),request.integerField("new_area"));
        } catch (NoHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getkey());
        }
    }

}
