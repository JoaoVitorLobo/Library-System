package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.UserIsActiveException;
import bci.user.userExceptions.InexistentUserIdException;
import bci.user.userExceptions.UserIsAlreadyActiveException;

/**
 * 4.2.5. Settle a fine.
 */
class DoPayFine extends Command<LibraryManager> {

    DoPayFine(LibraryManager receiver) {
        super(Label.PAY_FINE, receiver);
        addIntegerField("id_user", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            _receiver.reactivateUser(integerField("id_user"));
        }
        catch (InexistentUserIdException e) {
                throw new NoSuchUserException(e.getKey());
        }
        catch (UserIsAlreadyActiveException e) {
                throw new UserIsActiveException(e.getKey());
        }
    }

}
