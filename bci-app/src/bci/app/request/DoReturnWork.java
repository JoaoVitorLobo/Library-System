package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.WorkNotBorrowedByUserException;
import bci.request.requesteExceptions.NoRequestFoundException;
import bci.request.requesteExceptions.ReturnDateExceededException;
import bci.user.userExceptions.InexistentUserIdException;
import bci.user.userExceptions.UserIsAlreadyActiveException;
import bci.work.workExceptions.InexistentWorkIdException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

    DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.returnRequest(integerField("userId"), integerField("workId"));
        }
        catch(NoRequestFoundException e) {
            throw new WorkNotBorrowedByUserException(e.getWorkId(), e.getUserId());
        }
        catch(ReturnDateExceededException e) {
            try {
                _display.popup(Message.showFine(e.getUserId(), e.getTotalFine()));
                if (!Form.confirm(Prompt.finePaymentChoice())) {
                    _receiver.addFine(e.getUserId(), e.getFine());
                }
                else {
                    _receiver.reactivateUser(e.getUserId());
                }
            }
            catch (InexistentUserIdException ex) {
                throw new NoSuchUserException(ex.getKey());
            }
            catch(UserIsAlreadyActiveException ex) {
                // This should never happen
            }
            
        }
        catch(InexistentUserIdException e) {
            throw new NoSuchUserException(e.getKey());
        }
        catch(InexistentWorkIdException e) {
            throw new NoSuchWorkException(e.getKey());
        }
    }

}
