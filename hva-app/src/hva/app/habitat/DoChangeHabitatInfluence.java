package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.NoHabitatKeyException;
import hva.exceptions.NoSpecieKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoChangeHabitatInfluence extends Command<Hotel> {

    DoChangeHabitatInfluence(Hotel receiver) {
        super(Label.CHANGE_HABITAT_INFLUENCE, receiver);
        //FIXME add command fields if needed
    }

    @Override
    protected void execute() throws CommandException {
        Form request = new Form();
        try{
            request.addStringField("id_habitat", Prompt.habitatKey());
            request.addStringField("id_specie", hva.app.animal.Prompt.speciesKey());
            request.addOptionField("influence", Prompt.habitatInfluence(), "POS","NEG", "NEU");
            request.parse();

            _receiver.changeInfluenceHabitatSpecie(request.stringField("id_habitat"), request.stringField("id_specie"), 
            request.stringField("influence"));
        }catch (NoSpecieKeyException e){
            throw new UnknownSpeciesKeyException(e.getkey());
        }catch (NoHabitatKeyException e){
            throw new UnknownHabitatKeyException(e.getkey());
        }
    }

}
