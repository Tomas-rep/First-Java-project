package hva.app.habitat;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.Hotel;
import hva.app.exceptions.DuplicateTreeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.NoHabitatKeyException;
import hva.exceptions.TreeExistsException;

class DoAddTreeToHabitat extends Command<Hotel> {

    DoAddTreeToHabitat(Hotel receiver) {
        super(Label.ADD_TREE_TO_HABITAT, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        Form request = new Form();
        Form request_2 = new Form();
        try{

            request.addStringField("id_habitat", Prompt.habitatKey());
            request.addStringField("id", Prompt.treeKey());
            request.addStringField("name", Prompt.treeName());
            request.addStringField("age", Prompt.treeAge());
            request.addStringField("baseClean", Prompt.treeDifficulty());
            request_2.addStringField("type", Prompt.treeType());

            request.parse();
            request_2.parse();

            while (!request_2.stringField("type").equals("PERENE") &&
            !request_2.stringField("type").equals("CADUCA")) {
                request_2.clear();
                request_2.addStringField("type", Prompt.treeType());
                request_2.parse();
            }

            _receiver.addTreeToHabitat(new String[] {
                "ÁRVORE",
                request.stringField("id_habitat"),
                request.stringField("id"),
                request.stringField("name"),
                request.stringField("age"),
                request.stringField("baseClean"),
                request_2.stringField("type")
            });

            _display.popup(_receiver.printAddedTree(request.stringField("id_habitat"),
            request.stringField("id")));

        }catch(TreeExistsException e){
            throw new DuplicateTreeKeyException(e.getkey());
        }catch(NoHabitatKeyException e){
            throw new UnknownHabitatKeyException(e.getkey());
        }

    }
}


