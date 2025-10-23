package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.user.userExceptions.InexistentUserIdException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.2. Show specific user.
 */
class DoShowUser extends Command<LibraryManager> {

    DoShowUser(LibraryManager receiver) {
        super(Label.SHOW_USER, receiver);
        addIntegerField("id_user", Prompt.userId());
        
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _display.popup(_receiver.getUserById(integerField("id_user")));
        }
        catch (InexistentUserIdException e) {
            throw new NoSuchUserException(e.getKey());
        }
        
    }

}
