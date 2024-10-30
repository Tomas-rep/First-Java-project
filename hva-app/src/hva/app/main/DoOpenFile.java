package hva.app.main;

import hva.HotelManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.app.exceptions.FileOpenFailedException;
import hva.exceptions.UnavailableFileException;
import java.io.IOException;

class DoOpenFile extends Command<HotelManager> {
    DoOpenFile(HotelManager receiver) {
        super(Label.OPEN_FILE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            if (_receiver.changed() && Form.confirm(Prompt.saveBeforeExit())) {
                DoSaveFile command = new DoSaveFile(_receiver);
                command.execute();
            }
            _receiver.load(Form.requestString(Prompt.openFile()));
        } catch (UnavailableFileException e) {
            throw new FileOpenFailedException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
