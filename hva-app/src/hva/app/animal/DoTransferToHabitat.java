package hva.app.animal;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.NoAnimalKeyException;
import hva.exceptions.NoHabitatKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoTransferToHabitat extends Command<Hotel> {

    DoTransferToHabitat(Hotel hotel) {
        super(Label.TRANSFER_ANIMAL_TO_HABITAT, hotel);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            Form request = new Form();

            request.addStringField("id", Prompt.animalKey());
            request.addStringField("id_habitat", hva.app.habitat.Prompt.habitatKey());
            request.parse();

            _receiver.transferAnimal(request.stringField("id"),
            request.stringField("id_habitat"));
        } catch (NoHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getkey());
        } catch (NoAnimalKeyException e) {
            throw new UnknownAnimalKeyException(e.getkey());
        }   
    }

}
