package hva.app.animal;

import java.util.Map;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;

class DoShowAllAnimals extends Command<Hotel> {

    DoShowAllAnimals(Hotel receiver) {
        super(Label.SHOW_ALL_ANIMALS, receiver);
    }

    @Override
    protected final void execute() {
        _receiver.showAllAnimals().forEach((animal) -> _display.popup(animal));
    }

}
