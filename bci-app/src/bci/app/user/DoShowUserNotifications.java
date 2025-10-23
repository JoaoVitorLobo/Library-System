package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.user.userExceptions.InexistentUserIdException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

    DoShowUserNotifications(LibraryManager receiver) {
        super(Label.SHOW_USER_NOTIFICATIONS, receiver);
        addIntegerField("id_user", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.getUserNotifications(integerField("id_user")).forEach(n -> _display.popup(n.toString()));
            _receiver.clearUserNotifications(integerField("id_user"));
        }
        catch (InexistentUserIdException e) {
            throw new NoSuchUserException(e.getKey());
        }
    }

}
