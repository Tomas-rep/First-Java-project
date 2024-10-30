package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.exceptions.NoAnimalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowMedicalActsOnAnimal extends Command<Hotel> {

    DoShowMedicalActsOnAnimal(Hotel receiver) {
        super(Label.MEDICAL_ACTS_ON_ANIMAL, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        Form request = new Form();
        try{
            request.addStringField("id_animal", hva.app.animal.Prompt.animalKey());
            request.parse();

            _display.popup(_receiver.showMedicalActsAnimal(request.stringField("id_animal")));
        }catch (NoAnimalKeyException e){
            throw new UnknownAnimalKeyException(e.getkey());
        }

    }

}
