package bci;

import bci.creator.Creator;
import bci.creator.InexistentCreatorException;
import bci.exceptions.*;
import bci.notification.Notification;
import bci.request.*;
import bci.request.requesteExceptions.NoRequestFoundException;
import bci.request.requesteExceptions.ReturnDateExceededException;
import bci.request.requesteExceptions.Rule3Exception;
import bci.request.requesteExceptions.RulesException;
import bci.rules.BorrowingRule;
import bci.rules.OrRules;
import bci.search.DefaultStrategy;
import bci.search.SearchStrategy;
import bci.user.*;
import bci.user.userExceptions.EmailEmptyException;
import bci.user.userExceptions.InexistentUserIdException;
import bci.user.userExceptions.NameEmptyException;
import bci.user.userExceptions.UserIsAlreadyActiveException;
import bci.work.Book;
import bci.work.Dvd;
import bci.work.Work;
import bci.work.workExceptions.InexistentWorkIdException;
import bci.work.workExceptions.InvalidQuantityException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



/**
 * Class that represents the library as a whole.
 * Manages users, works, creators, requests, and borrowing rules.
 * Provides functionality for registration, searching, borrowing, and returning works.
 */
class Library implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    /** Indicates if the library has unsaved changes. */
    private transient boolean _changed = false;

    /** Map of creators, key: name of the creator. */
    private Map<String, Creator> _creators = new TreeMap<>();

    /** Map of works, key: work ID. */
    private Map<Integer, Work> _works = new TreeMap<>();

    /** Map of users, key: user ID. */
    private Map<Integer, User> _users = new TreeMap<>();

    /** List of requests. */
    private List<Request> _requests = new ArrayList<>();

    /** List of borrowing rules. */
    private List<BorrowingRule> _rules = new ArrayList<>();

    /** Current date. */
    private int _date = 1;

    /** Next work ID to be used. */
    private int _workId = 1;

    /** Next user ID to be used. */
    private int _userId = 1;

    /** Filename associated with the current library (null if none). */
    private String _filename = null;


    /**
    * Constructor that initializes the library with the specified borrowing rules.
    * @param rules variable number of borrowing rules to be applied
    */
    Library(BorrowingRule... rules) {
      for (BorrowingRule rule : rules)
        _rules.add(rule);
    }

    /**
     * Sets the filename associated with the current library.
     * @param filename
     */
    public void setFilename(String filename) {
        _filename = filename;
        _changed = true;
    }

    /**
     * Checks if the library has unsaved changes.
     * @return true if there are unsaved changes, false otherwise
     */
    public boolean changed() {
        return _changed;
    }

    /**
     * Returns the filename associated with the current library.
     * @return filename
     */
    public String getFilename() {
        return _filename;
    }   
     
    /**
     * Sets changed to the given value.
     * @param changed
     */
    public void setChanged(boolean changed) {
       _changed = changed;
    }

    /**
     * Advances the current date by the given number of days.
     * @param days
     */
    public void advanceDate(int days) {
      if (days > 0) {
        _date += days;
        setChanged(true);
      }
    }

    /**
     * Returns the current date.
     * @return
     */
    public int getDate() {
      return _date;
    }

    /**
     * Registers a DVD in the library.
     * @param title title of the DVD
     * @param director director of the movie
     * @param price price of the DVD
     * @param category category of the movie
     * @param igac IGAC code
     * @param qty quantity of DVDs
     * @return the registered DVD
     */
    public Dvd registerDvd(String title, String director, String price, String category, String igac, String qty) {
      Dvd dvd = new Dvd(_workId, title, category, price, qty, igac);
      Creator searchDirector = _creators.get(director);
      
      if(searchDirector == null) {
        Creator newDirector = registerCreator(director, dvd);
        dvd.setCreator(newDirector);
      }
      else {
        searchDirector.addCreatorWorks(dvd);
        dvd.setCreator(searchDirector);
      }
      _works.put(nextWorkId(), dvd);
      setChanged(true);
      return dvd;
    }

    /**
     * Registers a book in the library.
     * @param title title of the book
     * @param author author(s) of the book (separeted by commas)
     * @param price price of the book
     * @param category category of the book
     * @param isbn ISBN code
     * @param qty quantity of books
     * @return the registered book
     */
    public Book registerBook(String title, String author, String price, String category, String isbn, String qty) {
      Book book = new Book(_workId, title, category, price, qty, isbn);
      String authors_list[] = author.split(",");
      
      for (String s_author: authors_list) {
        Creator searchAuthor = _creators.get(s_author);
        if(searchAuthor == null) {
          Creator new_author = registerCreator(s_author, book);
          book.setCreator(new_author);
        }
        else {
          searchAuthor.addCreatorWorks(book);
          book.setCreator(searchAuthor);
        } 
      }
      _works.put(nextWorkId(), book);
      setChanged(true);
      return book;
    }


    /**
     * Registers a creator in the library.
     * @param name name of the creator
     * @param work work associated with the creator
     * @return the registered creator
     */
    public Creator registerCreator(String name, Work work) {
      Creator creator = new Creator(name);
      creator.addCreatorWorks(work);
      _creators.put(name, creator);
      setChanged(true);
      return creator;
    }


    /**
     * Registers a user in the library.
     * @param name name of the user
     * @param email email of the user
     * @return the ID of the registered user
     * @throws NameEmptyException if the name is empty
     * @throws EmailEmptyException if the email is empty
     */
    public int registerUser(String name, String email) throws NameEmptyException, EmailEmptyException {
      if (name.isEmpty()){
        throw new NameEmptyException();
      }

      if (email.isEmpty()){
        throw new EmailEmptyException();
      }

      User new_User = new User(_userId, name, email);
      _users.put(nextUserId(), new_User);
      setChanged(true);
      return new_User.getId();
    }

    /**
     * Registers an entry in the library based on the type of entry.
     * @param buffer_list array containing the entry details in format: [type, field1, field2, ...]
     * @throws UnrecognizedEntryException if the entry type is not recognized
     * @throws NameEmptyException if user name is empty
     * @throws EmailEmptyException if user email is empty
     */
    public void registry(String[] buffer_list) throws UnrecognizedEntryException, NameEmptyException, EmailEmptyException {
      switch(buffer_list[0]){
        case "DVD":
          registerDvd(buffer_list[1], buffer_list[2], buffer_list[3], buffer_list[4], buffer_list[5], buffer_list[6]);
          break;
        case "BOOK":
          registerBook(buffer_list[1], buffer_list[2], buffer_list[3], buffer_list[4], buffer_list[5], buffer_list[6]);
          break;
        case "USER":
          registerUser(buffer_list[1], buffer_list[2]);
          break;
        default: 
          throw new UnrecognizedEntryException(buffer_list[0]);
      }
    }

    /**
     * Read the text input file at the beginning of the program and populates the
     * instances of the various possible types (books, DVDs, users).
     *
     * @param filename name of the file to load
     * @throws UnrecognizedEntryException if the entry type is not recognized
     * @throws IOException if some I/O error happens while processing the file
     * @throws ImportFileException if some error happens during the processing of the file
     * @throws NameEmptyException if a user name is empty
     * @throws EmailEmptyException if a user email is empty
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException, ImportFileException, NameEmptyException, EmailEmptyException{
      String buffer_list[]; 
      try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
        String line = new String();
        while ((line = in.readLine()) != null){
          buffer_list = line.split(":");
          registry(buffer_list);
        }
      } 
      catch (IOException e) {
        throw new ImportFileException(filename, e);
      }
    }

    /**
     * Returns the next work ID and increments the work ID counter.
     * @return the next work ID
     */
    public int nextWorkId(){
      return _workId++;
    }

    /**
     * Returns the next user ID and increments the user ID counter.
     * @return the next user ID
     */
    public int nextUserId(){
      return _userId++;
    }
  
    /**
     * Returns the work associated with the given ID.
     * @param id ID of the work
     * @return the work associated with the ID
     */
    public Work getWorkByKey(int id) throws InexistentWorkIdException {
      Work work = _works.get(id);
      if (work == null || work.getQty() == 0){
        throw new InexistentWorkIdException(id);
      }
      return _works.get(id);
    }

    /**
     * Returns a map of all works in the library.
     * @return map of works
     */
    public List<Work> getWorks(){
      List <Work> workList = new ArrayList<>();
      for (Work work : _works.values()) {
        if (work.getQty() > 0)
          workList.add(work);
      }
      return workList;

    }

    /**
     * Returns the user associated with the given ID.
     * @param id_user ID of the user
     * @return the user associated with the ID
     */
    public User getUserById(int id_user) throws InexistentUserIdException {
      User user = _users.get(id_user);
      if (user == null){
        throw new InexistentUserIdException(id_user);
      }
      user.checkActive(_date);
      return user;
    }

    /**
     * Returns a map of all users in the library.
     * @return map of users
     */
    public List<User> getUsers() {
      List <User> userList = _users.values().stream().sorted().toList();
      userList.forEach(user -> user.checkActive(_date));
      return userList;
    }

    /**
     * Returns a list of works created by the specified creator.
     * @param creator name of the creator
     * @return list of works by the creator, or null if the creator is not found
     */
    public List<Work> getWorksByCreator(String creator) throws InexistentCreatorException {
      Creator searchCreator = _creators.get(creator);
      if (searchCreator == null) {
        throw new InexistentCreatorException(creator);
      }
      return searchCreator.getWorksByCreator();
    }

    /**
     * Changes the inventory of a work by adding or removing the specified quantity.
     * @param workId ID of the work
     * @param qty quantity to add or remove
     */
    public void changeInventory(int workId, int qty) throws InvalidQuantityException, InexistentWorkIdException {
      Work work = _works.get(workId); // if id exists but qty is 0, it doesnt throw exception
      if (work == null) {
        throw new InexistentWorkIdException(workId);
      }

      if (work.getQty() == 0 && qty > 0) {
        addWorkToCreator(work, work.getCreators());
      }
        
      if (qty + work.stillAvailable() < 0 ) 
        throw new InvalidQuantityException();

      work.addInventory(qty);

      if (work.getQty() == 0) {
        removeWorkFromCreator(work, work.getCreators());
      }
      setChanged(true);
    }


    /**
     * Adds a work to there creators.
     * @param work work to be added
     * @param creators list of creators associated with the work
     */
    public void addWorkToCreator(Work work, List<Creator> creators) {
      for (Creator creator : creators) {
        if (creator.getWorksByCreator().isEmpty()) {
          _creators.put(creator.getName(), creator);
        }
        creator.addCreatorWorks(work);
      }
      setChanged(true);
    }

    /**
     * Removes a work from there creators.
     * @param work work to be removed
     * @param creators list of creators associated with the work
     */
    public void removeWorkFromCreator(Work work, List<Creator> creators) {
      for (Creator creator : creators) {
        creator.removeCreatorWork(work);
        if (creator.getWorksByCreator().isEmpty()) {
          _creators.remove(creator.getName());
        }
      }
      setChanged(true);
    }

    /**
     * Searches for works matching the given term using the default search strategy.
     * @param term search term
     * @return list of works matching the search term
     */
    public List<Work> searchEngine(String term) {
      SearchStrategy strategy = new DefaultStrategy(getWorks(), term);
      return strategy.searchWork();
    }


    /**
     * Reactivate a user by their ID.
     * @param id_user ID of the user to reactivate
     */
    public void reactivateUser(int id_user) throws InexistentUserIdException , UserIsAlreadyActiveException{
      User user = getUserById(id_user);

      if (user.checkActive(_date)){
        throw new UserIsAlreadyActiveException(id_user);
      }

      user.reactivateUser();
      setChanged(true);
    }

    /**
     * Returns the notifications for a user by their ID.
     * @param id_user ID of the user
     * @return list of notifications for the user
     * @throws InexistentUserIdException if the user ID does not exist
     */
    public List<Notification> getUserNotifications(int id_user) throws InexistentUserIdException {
      return getUserById(id_user).getNotifications();
    }

    /**
     * Clears the notifications for a user by their ID.
     * @param id_user ID of the user
     * @throws InexistentUserIdException if the user ID does not exist
     */
    public void clearUserNotifications(int id_user) throws InexistentUserIdException{
      getUserById(id_user).clearNotifications();
      setChanged(true);
    }

    /**
     * Adds a request to the library.
     * @param request the request to add
     */
    public void addRequest(Request request) {
      _requests.add(request);
      request.getWork().addRequestToWork();
      request.getUser().addRequestToUser(request);
      request.getWork().removeUserFromDisponibilityWaitList(request.getUser());
      setChanged(true);
    }

    /**
     * Processes a work request from a user.
     * @param userId ID of the user making the request
     * @param workId ID of the work being requested
     * @return the return date for the requested work
     * @throws InexistentWorkIdException if the work ID does not exist
     * @throws InexistentUserIdException if the user ID does not exist
     * @throws RulesException if borrowing rules are violated
     * @throws Rule3Exception if rule 3 is violated
     */
    public int requestWork(int userId, int workId) throws InexistentWorkIdException, InexistentUserIdException, RulesException, Rule3Exception {
      User user = getUserById(userId);
      Work work = getWorkByKey(workId);
      BorrowingRule rules = new OrRules(_rules);
      if (rules.canBorrow(user, work, _date)) {
        Request newRequest = new Request(user, work, _date);
        addRequest(newRequest);
        return newRequest.getReturnDay();
      }
      else {
        int ruleFaild = rules.getRuleFaild();
        switch(ruleFaild) {
          case 3 -> throw new Rule3Exception();
          default -> throw new RulesException(ruleFaild);
        }
      }
    }

    /**
     * Adds a user to the waitlist for a work.
     * @param id_user ID of the user
     * @param id_work ID of the work
     * @throws InexistentWorkIdException if the work ID does not exist
     * @throws InexistentUserIdException if the user ID does not exist
     */
    public void addUserToWaitList(int id_user, int id_work) throws InexistentUserIdException, InexistentWorkIdException{
      User user = getUserById(id_user);
      Work work = getWorkByKey(id_work);
      work.addUserToDisponibilityWaitList(user);
      setChanged(true);
    }

    /**
     * Removes a request from the library.
     * @param request the request to remove
     */
    public void removeRequest(Request request) {
      _requests.remove(request);
      request.getWork().removeRequestFromWork();
      request.getUser().removeRequest(request, _date);
      setChanged(true);
    }

    /**
     * Processes the return of a requested work by a user.
     * @param userId ID of the user returning the work
     * @param workId ID of the work being returned
     * @throws InexistentWorkIdException if the work ID does not exist
     * @throws InexistentUserIdException if the user ID does not exist
     * @throws NoRequestFoundException if no request is found for the user and work
     * @throws ReturnDateExceededException if the return date has been exceeded
     */
    public void returnRequest(int userId, int workId) throws InexistentWorkIdException, InexistentUserIdException, NoRequestFoundException, ReturnDateExceededException {
      User user = getUserById(userId);
      Work work = getWorkByKey(workId);
      Request request = user.getRequestByWork(work);
      if (request == null) throw new NoRequestFoundException(userId, workId);

      user.addAndCheckHistory(!request.expired(_date)); 

      removeRequest(request);
      
      if (request.expired(_date)) throw new ReturnDateExceededException(userId, workId, request.getFine(_date), request.getFine(_date) + user.getFine());
    }

     
    /**
     * Adds a fine to a user by their ID.
     * @param id_user ID of the user
     * @param fine amount of the fine
     * @throws InexistentUserIdException if the user ID does not exist
     */
    public void addFine(int id_user, int fine) throws InexistentUserIdException {
      User user = getUserById(id_user);
      user.addFine(fine);
      setChanged(true);
    }
}

