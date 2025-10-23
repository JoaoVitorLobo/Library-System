package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchCreatorException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.creator.InexistentCreatorException;

/**
 * 4.3.3. Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {

    DoDisplayWorksByCreator(LibraryManager receiver) {
        super(Label.SHOW_WORKS_BY_CREATOR, receiver);
        addStringField("creator", Prompt.creatorId());
    }

    @Override
    protected final void execute() throws CommandException { 
        try{
            _receiver.getWorksByCreator(stringField("creator")).forEach(work -> _display.popup(work));
        }
        catch (InexistentCreatorException e) {
            throw new NoSuchCreatorException(e.getName());
        }
    }

}
