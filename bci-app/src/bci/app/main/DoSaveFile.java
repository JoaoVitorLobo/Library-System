package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.forms.Form;
import java.io.IOException;
import bci.exceptions.MissingFileAssociationException;

/**
 * §4.1.1 Open and load files.
 */
class DoSaveFile extends Command<LibraryManager> {

    DoSaveFile(LibraryManager receiver) {
        super(Label.SAVE_FILE, receiver);
    }

    @Override
    protected final void execute() {
        try {
			_receiver.save();
		} 
        catch (MissingFileAssociationException e_) {
			try {
				_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
			} 
            catch (MissingFileAssociationException | IOException e) {
				e.printStackTrace(); 
			}
		} 
        catch (IOException e_) {
			e_.printStackTrace();
		}
    }

}
