package it.polito.library;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class LibraryManager {

	private Map<String, Book> books = new HashMap<>();
	private SortedMap<String, List<String>> titles = new TreeMap<>();
	private Map<String, Reader> readers = new HashMap<>();
	private Map<String, Rental> rentals = new HashMap<>();
	private List<String> donated = new ArrayList<>();
	private int bookid = 1000;
	private int readerid = 1000;
    // R1: Readers and Books 
    
    /**
	 * adds a book to the library archive
	 * The method can be invoked multiple times.
	 * If a book with the same title is already present,
	 * it increases the number of copies available for the book
	 * 
	 * @param title the title of the added book
	 * @return the ID of the book added 
	 */
    public String addBook(String title) {
		String id = bookid + "";
		Book b = new Book(title, id);
		if(titles.containsKey(title)){
			titles.get(title).add(id);
		}else{
			List<String> ids = new ArrayList();
			ids.add(id);
			titles.put(title,ids);
		}
		books.put(id, b);
		
		bookid++;
        return id;
    }
    
    /**
	 * Returns the book titles available in the library
	 * sorted alphabetically, each one linked to the
	 * number of copies available for that title.
	 * 
	 * @return a map of the titles liked to the number of available copies
	 */
    public SortedMap<String, Integer> getTitles() {   
		SortedMap<String, Integer> titlenums = new TreeMap();
		for(Map.Entry<String, List<String>> entry : titles.entrySet()){
			titlenums.put(entry.getKey(), entry.getValue().size());
		}
        return titlenums;
    }

	
    
    /**
	 * Returns the books available in the library
	 * 
	 * @return a set of the titles liked to the number of available copies
	 */
    public Set<String> getBooks() {    	    	
        return books.keySet().stream()
			.collect(Collectors.toSet());
    }
    
    /**
	 * Adds a new reader
	 * 
	 * @param name first name of the reader
	 * @param surname last name of the reader
	 */
    public void addReader(String name, String surname) {
		String id = readerid + "";
		Reader reader = new Reader(id, name, surname);
		readers.put(id, reader);
		readerid++;
    }
    
    
    /**
	 * Returns the reader name associated to a unique reader ID
	 * 
	 * @param readerID the unique reader ID
	 * @return the reader name
	 * @throws LibException if the readerID is not present in the archive
	 */
    public String getReaderName(String readerID) throws LibException {
		if(!readers.containsKey(readerID)){
			throw new LibException();
		}
        return readers.get(readerID).getName();
    }    
    
    
    // R2: Rentals Management
    
    
    /**
	 * Retrieves the bookID of a copy of a book if available
	 * 
	 * @param bookTitle the title of the book
	 * @return the unique book ID of a copy of the book or the message "Not available"
	 * @throws LibException  an exception if the book is not present in the archive
	 */
    public String getAvailableBook(String bookTitle) throws LibException {
		if(!titles.containsKey(bookTitle)){
			throw new LibException();
		}

        return books.values().stream()
			.filter(x -> x.getTitle().equals(bookTitle))
			.filter(book -> !book.getStatus())
			.map(Book::getID)
			.findFirst()
			.orElse("Not available")
			;
    }   

    /**
	 * Starts a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param startingDate the starting date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is already renting a book, or if the book copy is already rented
	 */
	public void startRental(String bookID, String readerID, String startingDate) throws LibException {
		if(!books.containsKey(bookID) || !readers.containsKey(readerID)){
			throw new LibException();
		}
		if(books.get(bookID).getStatus()||readers.get(readerID).getReaderStatus()){
			throw new LibException();
		}

		Rental rental = new Rental(readerID, bookID, startingDate);
		rentals.put(readerID, rental);
		books.get(bookID).SetRented();
		books.get(bookID).setEverRented();
		
		readers.get(readerID).SetRenting();
		readers.get(readerID).incrementRentals();
		

    }
    
	/**
	 * Ends a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param endingDate the ending date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is not renting a book, or if the book copy is not rented
	 */
    public void endRental(String bookID, String readerID, String endingDate) throws LibException {
		if(!books.containsKey(bookID) || !readers.containsKey(readerID)){
			throw new LibException();
		}
		if(books.get(bookID).getStatus()==false|| readers.get(readerID).getReaderStatus()==false){
			throw new LibException();
		}

		rentals.get(readerID).setEndingDate(endingDate);
		
		books.get(bookID).SetNotRented();
		readers.get(readerID).SetNotRenting();

    }
    
    
   /**
	* Retrieves the list of readers that rented a specific book.
	* It takes a unique book ID as input, and returns the readers' reader IDs and the starting and ending dates of each rental
	* 
	* @param bookID the unique book ID of the book copy
	* @return the map linking reader IDs with rentals starting and ending dates
	* @throws LibException  an exception if the book copy or the reader are not present in the archive,
	* if the reader is not renting a book, or if the book copy is not rented
	*/
    public SortedMap<String, String> getRentals(String bookID) throws LibException {
		if(!books.containsKey(bookID)){
			throw new LibException();
		}
		if(books.get(bookID).getStatus()){
			throw new LibException();
		}
		
		
		return rentals.values().stream()
			.filter(x -> x.getBookID().equals(bookID))
			.collect(Collectors.toMap(
				x -> x.getReaderID(),
				x -> x.toString(),
				(existing, replacement)->existing,
				TreeMap::new));
        
    }
    
    
    // R3: Book Donations
    
    /**
	* Collects books donated to the library.
	* 
	* @param donatedTitles It takes in input book titles in the format "First title,Second title"
	*/
    public void receiveDonation(String donatedTitles) {
		String[] titlenames = donatedTitles.split(",");
		for(String title : titlenames){
			String id = bookid + "";
			Book b = new Book(title, id);
			if(titles.containsKey(title)){
				titles.get(title).add(id);
			}else{
				List<String> ids = new ArrayList();
				ids.add(id);
				titles.put(title,ids);
			}
			books.put(id, b);
			
			bookid++;
		}
    }
    
    // R4: Archive Management

    /**
	* Retrieves all the active rentals.
	* 
	* @return the map linking reader IDs with their active rentals

	*/
    public Map<String, String> getOngoingRentals() {
		
        return rentals.values().stream()
			.filter(Rental::getStatus)
			.collect(Collectors.toMap(
				x->x.getReaderID(),
				x->x.getBookID()
			));
    }
    
    /**
	* Removes from the archives all book copies, independently of the title, that were never rented.
	* 
	*/
    public void removeBooks() {
		books.entrySet().removeIf(entry -> !entry.getValue().everRented());
    }
    	
    // R5: Stats
    
    /**
	* Finds the reader with the highest number of rentals
	* and returns their unique ID.
	* 
	* @return the uniqueID of the reader with the highest number of rentals
	*/
    public String findBookWorm() {

        return readers.values().stream()
			.sorted(Comparator.comparingInt(Reader::getNumRentals).reversed())
			.map(Reader::getID)
			.collect(Collectors.toList()).get(0);
    }
    
    /**
	* Returns the total number of rentals by title. 
	* 
	* @return the map linking a title with the number of rentals
	*/
    public Map<String,Integer> rentalCounts() {
        return rentals.values().stream()
            .collect(Collectors.groupingBy(
                rental -> books.get(rental.getBookID()).getTitle(), // Group by Book ID
                Collectors.summingInt(r -> 1) // Count occurrences
            ));
    }

}
