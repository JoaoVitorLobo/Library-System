package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.BorrowingRuleFailedException;
import bci.app.exceptions.NoSuchUserException;
import bci.request.requesteExceptions.Rule3Exception;
import bci.request.requesteExceptions.RulesException;
import bci.user.userExceptions.InexistentUserIdException;
import bci.work.workExceptions.InexistentWorkIdException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.app.exceptions.*;



/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

    DoRequestWork(LibraryManager receiver) {
        super(Label.REQUEST_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _display.popup(Message.workReturnDay(integerField("workId"), _receiver.requestWork(integerField("userId"), integerField("workId")))); 
        } 
        catch (Rule3Exception e) {
            if(Form.confirm(Prompt.returnNotificationPreference()))
            try {
                _receiver.addUserToWaitList(integerField("userId"), integerField("workId"));     
            }
            catch (InexistentUserIdException ex) {
                throw new NoSuchUserException(ex.getKey());
            }
            catch (InexistentWorkIdException ex) {
                throw new NoSuchWorkException(ex.getKey());
            }  
        }
        catch (RulesException e) {
            throw new BorrowingRuleFailedException(integerField("userId"), integerField("workId"), e.getRuleId());
        }
        catch (InexistentUserIdException e) {
            throw new NoSuchUserException(e.getKey());
        }
        catch (InexistentWorkIdException e) {
            throw new NoSuchWorkException(e.getKey());
        }
    }

}
