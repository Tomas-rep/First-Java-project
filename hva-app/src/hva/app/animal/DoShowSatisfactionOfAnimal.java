package hva.app.animal;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.exceptions.NoAnimalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowSatisfactionOfAnimal extends Command<Hotel> {

    DoShowSatisfactionOfAnimal(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_ANIMAL, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            Form request = new Form();

            request.addStringField("id", Prompt.animalKey());
            request.parse();

            _display.popup(_receiver.getHabitatWAnimalId(request.stringField("id")).animalCalcSatisfaction(request.stringField("id")));

        } catch (NoAnimalKeyException e) {
            throw new UnknownAnimalKeyException(e.getkey());
        }
    }

}
