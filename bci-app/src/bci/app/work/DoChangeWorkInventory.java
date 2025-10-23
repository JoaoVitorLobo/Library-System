package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import bci.work.workExceptions.InexistentWorkIdException;
import bci.work.workExceptions.InvalidQuantityException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * 4.3.4. Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {

    DoChangeWorkInventory(LibraryManager receiver) {
        super(Label.CHANGE_WORK_INVENTORY, receiver);
        addIntegerField("id", Prompt.workId());
        addIntegerField("qty",Prompt.amountToUpdate());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.changeInventory(integerField("id"), integerField("qty"));
        }
        catch (InvalidQuantityException e) {
            _display.popup(Message.notEnoughInventory(integerField("id"), integerField("qty")));
        }
        catch (InexistentWorkIdException e) {
            throw new NoSuchWorkException(e.getKey());
        }
    }
}
