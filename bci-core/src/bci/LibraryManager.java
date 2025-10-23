package bci;

import bci.creator.*;
import bci.exceptions.*;
import bci.notification.Notification;
import bci.request.*;
import bci.request.requesteExceptions.NoRequestFoundException;
import bci.request.requesteExceptions.ReturnDateExceededException;
import bci.request.requesteExceptions.Rule3Exception;
import bci.request.requesteExceptions.RulesException;
import bci.rules.*;
import bci.user.*;
import bci.user.userExceptions.EmailEmptyException;
import bci.user.userExceptions.InexistentUserIdException;
import bci.user.userExceptions.NameEmptyException;
import bci.user.userExceptions.UserIsAlreadyActiveException;
import bci.work.*;
import bci.work.workExceptions.InexistentWorkIdException;
import bci.work.workExceptions.InvalidQuantityException;

import java.io.*;
import java.util.List;

//FIXME maybe import classes

/**
 * The façade class.
 */
public class LibraryManager {

    /** The object doing all the actual work. */
    private Library _library = new Library(new Rule1(), 
                                            new Rule2(), 
                                            new Rule3(), 
                                            new Rule4(), 
                                            new Rule5(), 
                                            new Rule6());

    public void save() throws MissingFileAssociationException, IOException {
        String filename = _library.getFilename(); //get current filename
        if (filename == null) { //if there is no current file
          throw new MissingFileAssociationException(); //throw missing file exception
        }
        if (!changed()) //if nothing changed, there iso no need to save
            return;
        try (var oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) { //open file
            oos.writeObject(_library); //save library
            _library.setChanged(false); //set changed to false
        }
    }

    public void saveAs(String filename) throws MissingFileAssociationException, IOException {
        _library.setFilename(filename);
        save();
    }

    public void load(String filename) throws UnavailableFileException {
        try (var ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
          _library = (Library) ois.readObject();
          _library.setFilename(filename);
          _library.setChanged(false);
        }
        catch (IOException | ClassNotFoundException e) {
          throw new UnavailableFileException(filename);
        }
    }

    /**
     * Read text input file and initializes the current library (which should be empty)
     * with the domain entities representeed in the import file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException if some error happens during the processing of the
     * import file.
     */
    public void importFile(String filename) throws ImportFileException {
      try {
        if (filename != null && !filename.isEmpty())
          _library.importFile(filename);
      } catch (IOException | UnrecognizedEntryException | NameEmptyException | EmailEmptyException e) {
        throw new ImportFileException(filename, e);
      }
    }

    //verify if the library has unsaved changes
    public boolean changed() {
        return _library.changed();
    }

    public void advanceDate(int days) {
      _library.advanceDate(days);
    }

    public int getDate() {
      return _library.getDate();
    } 

    public int registerUser(String name, String email) throws NameEmptyException, EmailEmptyException {
      return _library.registerUser(name, email);
    }

    public Work getWork(int id) throws InexistentWorkIdException {
      return _library.getWorkByKey(id);
    }

    public List<Work> getWorks(){
      return _library.getWorks();
    }

    public User getUserById(int id_user) throws InexistentUserIdException{
      return _library.getUserById(id_user);
    }

    public List<Work> getWorksByCreator(String creator) throws InexistentCreatorException {
      return _library.getWorksByCreator(creator);
    }

    public List<User> getUsers() {
      return _library.getUsers();
    }

    public void changeInventory(int workId, int qty) throws InvalidQuantityException, InexistentWorkIdException {
      _library.changeInventory(workId, qty);
    }

    public List<Work> searchEngine(String term) {
      return _library.searchEngine(term);
    }


    public void reactivateUser(int id_user) throws InexistentUserIdException , UserIsAlreadyActiveException{
      _library.reactivateUser(id_user);
    }
    

    public List<Notification> getUserNotifications(int id_user) throws InexistentUserIdException {
      return _library.getUserNotifications(id_user);
    }

    public void clearUserNotifications(int id_user) throws InexistentUserIdException{
      _library.clearUserNotifications(id_user);
    }

    public int requestWork(int id_user, int id_work) throws InexistentUserIdException, InexistentWorkIdException, RulesException, Rule3Exception{
      return _library.requestWork(id_user, id_work);
    }

    public void addUserToWaitList(int id_user, int id_work) throws InexistentUserIdException, InexistentWorkIdException{
      _library.addUserToWaitList(id_user, id_work);
    }

    public void returnRequest(int id_user, int id_work) throws InexistentUserIdException, InexistentWorkIdException, NoRequestFoundException, ReturnDateExceededException {
      _library.returnRequest(id_user, id_work);
    }

    public void addFine(int id_user, int fine) throws InexistentUserIdException {
      _library.addFine(id_user, fine);
    }
}
