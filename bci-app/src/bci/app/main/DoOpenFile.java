package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.app.exceptions.*;
import bci.exceptions.UnavailableFileException;

/**
 * §4.1.1 Open and load files.
 */
class DoOpenFile extends Command<LibraryManager> {

    DoOpenFile(LibraryManager receiver) {
        super(Label.OPEN_FILE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            if (_receiver.changed() && Form.confirm(Prompt.saveBeforeExit())) {
                var doSaveFile = new DoSaveFile(_receiver);
                doSaveFile.execute();
            }
            _receiver.load(Form.requestString(Prompt.openFile()));
        } 
        catch (UnavailableFileException e) {
            throw new FileOpenFailedException(e);
        }
    }

}
