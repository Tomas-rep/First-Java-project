package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.app.exceptions.UnknownVaccineKeyException;
import hva.app.exceptions.VeterinarianNotAuthorizedException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.exceptions.NoAnimalKeyException;
import hva.exceptions.NoAuthorizedVetException;
import hva.exceptions.NoEmployeeKeyException;
import hva.exceptions.NoVaccineKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoVaccinateAnimal extends Command<Hotel> {

    DoVaccinateAnimal(Hotel receiver) {
        super(Label.VACCINATE_ANIMAL, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        Form request = new Form();
        try {
            request.addStringField("id_vaccine",Prompt.vaccineKey());
            request.addStringField("id_veterinarian", Prompt.veterinarianKey());
            request.addStringField("id_animal", hva.app.animal.Prompt.animalKey());
            request.parse();

            if (!_receiver.vaccinateAnimal(
                "REGISTO-VACINA",
                request.stringField("id_vaccine"),
                request.stringField("id_veterinarian"),
                request.stringField("id_animal"))) {
                    _display.popup(Message.wrongVaccine(request.stringField("id_vaccine"),
                    request.stringField("id_animal")));
                }

        }catch (NoEmployeeKeyException e) {
            throw new UnknownVeterinarianKeyException(e.getkey());
        }catch (NoAuthorizedVetException e) {
            throw new VeterinarianNotAuthorizedException(e.getkey1(), e.getkey2());
        }catch (NoAnimalKeyException e) {
            throw new UnknownAnimalKeyException(e.getkey());
        }catch (NoVaccineKeyException e) {
            throw new UnknownVaccineKeyException(e.getkey());
        }
    }

}
