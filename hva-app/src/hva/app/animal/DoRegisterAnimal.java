package hva.app.animal;

import hva.Hotel;

import hva.app.exceptions.DuplicateAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.AnimalExistsException;
import hva.exceptions.NoHabitatKeyException;
import hva.exceptions.UnrecognizedEntryException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoRegisterAnimal extends Command<Hotel> {

    DoRegisterAnimal(Hotel receiver) {
        super(Label.REGISTER_ANIMAL, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            Form request = new Form();
            Form request_2 = new Form();
            request.addStringField("id", Prompt.animalKey());
            request.addStringField("name", Prompt.animalName());
            request.addStringField("Species id", Prompt.speciesKey());
            request.addStringField("Habitat id", hva.app.habitat.Prompt.habitatKey());
            request.parse();

            // If the species doesnt exist already, it requests the name of the specie
            // and registers it with the respective id and name
            if (!_receiver.hasSpecie(request.stringField("Species id"))) {
                request_2.addStringField("Species name", Prompt.speciesName());
                request_2.parse();
                _receiver.registerSpecie(new String[] {"ESPÉCIE",
                    request.stringField("Species id"),
                    request_2.stringField("Species name")
                });
            }

            _receiver.registerAnimal(new String[] {
                "ANIMAL",
                request.stringField("id"),
                request.stringField("name"),
                request.stringField("Species id"),
                request.stringField("Habitat id")
            });
        } catch (NoHabitatKeyException e) {
            throw new UnknownHabitatKeyException(e.getkey());
        } catch (AnimalExistsException e) {
            throw new DuplicateAnimalKeyException(e.getkey());
        }
    }

}
