package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import bci.work.workExceptions.InexistentWorkIdException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * 4.3.1. Display work.
 */
class DoDisplayWork extends Command<LibraryManager> {

    DoDisplayWork(LibraryManager receiver) {
        super(Label.SHOW_WORK, receiver);
        addIntegerField("id", Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            _display.popup(_receiver.getWork(integerField("id")));
        }
        catch (InexistentWorkIdException e) {
            throw new NoSuchWorkException(e.getKey());
        }
    }
}
