
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This program sets up a library system, allowing the admin/borrower to
 * 1. Create Borrowers
 * 2. Search Borrowers
 * 3. Display all Borrowers
 * 4. Borrow a book
 * 5. Return a book
 * 6. Add a book to the library
 * 7. Display all books in the library
 * 
 * It was developed over several days independently and collaboratively during study groups.
 * It covers most topics of semester 1 of the Programming module, and was used as a method of
 * practice for the final assessment.
 * 
 * author @JoshuaBeatty
 *
 */

public class LibrarySystem {

	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		// Array of Options
		String options[] = { 	"What would you like to do? ",
								"1. Create Borrower", 
								"2. Display a Borrower", 
								"3. Display Borrowers and Books on Loan",
								"---------------------------",
								"4. Borrow a Book",
								"5. Return a Book",
								"6. Add book to Library",
								"7. Display books in Library",
								"---------------------------",
								"10. Quit"};
		
		// Array List for all Library Users
		ArrayList<Borrower> listOfBorrowers = new ArrayList<Borrower>();
		
		// Method calls creates an array containing two example Book objects 
		Book[] library = addBooks();
		
		// Boolean variable false until user wants to quit program
		boolean finished = false;
		
		// Do While loop kicks off the program in the console
		do {
			// Display Menu
			displayMenu(options);
			
			// Get Valid Option
			int choice = getUserChoice();
			
			// Switch decides functionality to run based on users input
			switch(choice) {
			case 1	:	// Case 1 - Create Borrower
						CreateBorrowerProcess(listOfBorrowers);
						break;
						
			case 2	:	// Case 2 - Search for a Specific Borrower
						DisplayBorrowerProcess(listOfBorrowers);
						break;
						
			case 3	:	// Case 3 - Display All Borrowers and their Details (including books)
						DisplayAllBorrowersProcess(listOfBorrowers);
						break;
			case 4	:	// Case 4 - Borrow a Book
						BorrowBookProcess(listOfBorrowers, library);
						break;
						
			case 5  : 	// Case 5 - Return a Book
						ReturnBookProcess(listOfBorrowers, library);
						break;
						
			case 6	:	// Case 6 - Add a Book to Library
						library = AddBookToLibraryProcess(library);
						break;
						
			case 7	: 	// Case 7 - Display Library
						DisplayAllBooksProcess(library);
						break;
						
			case 10	:	finished = true;
						break;
			
			default	:	System.out.println("Error - Invalid Choice");
			}
		}
		while( !finished );
		System.out.println("\nGoodbye!");
	}
	
	/**
	 * Method called when a specific borrower is chosen and the borrower's details need to be displayed
	 * @param b takes in selected borrower to display the specific detils of
	 */
	static void displayBorrower(Borrower b) {
		System.out.println("\nLibrary Member Details");
		System.out.println("======================");
		
		System.out.println("\t"+b.toString()+"\n");
		
		System.out.println("Books on Loan");
		System.out.println("=============");
		System.out.println( "\t"+b.getAllBooks() );
		System.out.println();
	}
	
	/**
	 * This method is called when the program is required to return to a "main menu".
	 * The method will output a sweet menu, displaying all the functionality to the user.
	 * To list the user's options, it accesses each element in the options array
	 * @param data
	 */
	static void displayMenu(String options[]) {
		System.out.println("\n=================");
		System.out.println("=== Main Menu ===");
		System.out.println("=================\n");
		
		for(String option: options) {
			System.out.println(option);
		}
	}

	/**
	 * Method called when user input is required. Method also validates that, and only returns an integer
	 * @return integer representing the option selected
	 */
	static int getUserChoice() {
		do{
			System.out.println("Enter choice:");
			if(input.hasNextInt()) {
				int choice = input.nextInt();
				input.nextLine();
				System.out.println();
				return choice;
			}else {
				System.out.println("Error: Invalid Option! Must be an integer");
				System.out.println();
			}
		}while(true);
	}
	
	
	/**
	 * Method used to create a new Borrower Object. User input is set as instance variables
	 * @return Returns a new Borrower Object
	 */
	static Borrower createBorrower() {	
		System.out.println("Enter New Id: ");
		String id = input.nextLine();
		System.out.println("Enter Name:");
		String name = input.nextLine();
		System.out.println("Enter Address:");
		String add = input.nextLine();
		Borrower b = new Borrower(id,name,add);
		return b;
	}
	
	
	/**
	 * Method used to output a list of borrowers to select from. Only outputs their name and ID, unlike
	 * the similar method "displayAllBorrowers" which outputs all of the borrower details
	 * @param listOfBorrowers Takes in the list of borrowers
	 */
	static void displayAllBorrowersBrief(ArrayList<Borrower> listOfBorrowers) {
		int count = 1;
		for(Borrower eachBorrower: listOfBorrowers) {
			System.out.println(count + ". Name: " + eachBorrower.getName());
			count++;
		}
	}
	
	
	/**
	 * Method used to output a list of borrowers to select from. Only outputs their name and ID, unlike
	 * the similar method "displayAllBorrowers" which outputs all of the borrower details
	 * @param listOfBorrowers Takes in the list of borrowers
	 */
	static ArrayList<Book> displayAllBooksBrief(Book[] library) {
		// Defines a count variable so user can select a book using a normal number system
		int countOfAvailableBooks = 1;
		
		// Defines an array list to record all book objects which are available for borrowing
		ArrayList<Book> booksAvailable = new ArrayList<Book>();
		
		// Populates the array list with all books available - we know there will be at
		// least one book because we passed validation
		for(Book eachBook: library) {
			if(eachBook.isAvailable()) {
				booksAvailable.add(eachBook);
			}
		}
		
		for(Book eachAvailableBook: booksAvailable) {
			System.out.println(countOfAvailableBooks + ". ID: " + eachAvailableBook.getId() + " ISBN: " +
					eachAvailableBook.getIsbn() + " Title: " + eachAvailableBook.getTitle());
			countOfAvailableBooks++;
		}
		
		return booksAvailable;
		
		
	}
	
	
	/**
	 * Method used to output a list of books to select from. Only outputs their ISBN and Title.
	 * @param selectedBorrower Uses selected user object to get their books on loan
	 */
	static void displayAllBooksBorrowed(Borrower selectedBorrower) {
		ArrayList<Book> booksBorrowed = selectedBorrower.getAllBooks();
		int countOfBorrowedBooks = 1;
		for(Book eachBook: booksBorrowed) {
			System.out.println(countOfBorrowedBooks + ". ID: " + eachBook.getId() + " Title: " + eachBook.getTitle());
		}
	}
	
	
	/**
	 * Method used to output a list of borrowers, and all of their details
	 * @param listOfBorrowers Takes in the list of borrowers
	 */
	static void displayAllBorrowers(ArrayList<Borrower> listOfBorrowers) {
		
		// For loop iterates through each Borrower object in the list of borrowers
		for(Borrower eachBorrower: listOfBorrowers) {
			System.out.println("===========================");
			System.out.println("ID: " + eachBorrower.getId());
			System.out.println("Name: " + eachBorrower.getName());
			System.out.println("Address: " + eachBorrower.getAddress());
			System.out.println("-------Books on Loan-------");
			
			for(Book eachBook: eachBorrower.getAllBooks()) {
				// Nested for loop iterates through and prints all book details
				// on all book on loan of the current borrower object
				System.out.println("ISBN: " + eachBook.getIsbn());
				System.out.println("Title: " + eachBook.getTitle());
				System.out.println("Author: " + eachBook.getAuthor());
				System.out.println("---------------------------");
			}
			System.out.println("===========================");
		}
	}
	
	
	/**
	 * Method is used to create two example books
	 * @return Array of 2 Example Books
	 */
	static Book[] addBooks() {
		Book theBooks[] = new Book[2];
		theBooks[0] = new Book(23, "12-12-12", "The Cosmos", "Sagan");
		theBooks[1] = new Book(24, "45-12-14", "The Sky at Night", "Moore");
		return theBooks;
	}
	
	
	/**
	 * Method used to display a selection of Books available to borrow. Gets a selected book from list
	 * @param data Array of books on Library System
	 * @return Returns a selected books index from the library array
	 */
	static Book chooseBook(Book[] data) {
		Book pick = null;
		System.out.println("Book List");
		System.out.println("=========\n");
		
		// For loop outputs each book as an option for selection
		for(int index=0;index<data.length;index++) {
			Book bk1 = data[index];
			System.out.println((index+1) +". "+ bk1);
		}
		System.out.println();
		int choice = getUserChoice();
		pick = data[choice-1];
		return pick;
	}
	
	
	/**
	 * Method used to create a new Book Object. User input is set as instance variables
	 * @return Returns a new Book Object
	 */
	static Book createBook() {
		System.out.println("Enter New ID: ");
		int id = input.nextInt();
		input.nextLine(); 	// needed to stop the poo poo hitting the high velocity propeller 
		System.out.println("Enter ISBN:");
		String isbn = input.nextLine();
		System.out.println("Enter Title:");
		String title = input.nextLine();
		System.out.println("Enter Author:");
		String author = input.nextLine();
		Book newBook = new Book(id,isbn,title,author);
		return newBook;
	}
	
	
	
	
	
	
	// Methods Below are called using the menu switch. They store the option processes,
	// instead of cluttering the switch.
	
	/**
	 * Method called upon when "Create new Borrower" option selected
	 * @param listOfBorrowers array list of borrowers on the Library System
	 */
	static void CreateBorrowerProcess(ArrayList<Borrower> listOfBorrowers) {
		System.out.println("=== Create Borrower ===");
		// Step 1: Create Borrower Object
		Borrower newBorrower = createBorrower();
		
		// Step 2: Add new Borrower object to ArrayList of Borrowers
		listOfBorrowers.add(newBorrower);
		return;
	}
	
	/**
	 * Method called upon when "Display Borrower" option selected
	 * @param listOfBorrowers array list of borrowers on the Library System
	 */
	static void DisplayBorrowerProcess(ArrayList<Borrower> listOfBorrowers) {
		System.out.println("=== Search Borrower ===");
		
		// Step 1: Check to see if there is a Borrower to search for
		if(!listOfBorrowers.isEmpty()) {
			
			// Step 2: Assuming there is a borrower to search for, we output the ID's and Name's
			System.out.println("Select a Borrower;");
			displayAllBorrowersBrief(listOfBorrowers);
			
			// Step 3: Get selected user
			int theChosenOneIndex = getUserChoice();
			
			// Step 4: Get selected user using selected integer
			Borrower theChosenOne = listOfBorrowers.get(theChosenOneIndex-1);
			
			// Step 5: Get selected user's details
			System.out.println(theChosenOne.toString());
			System.out.println("===========================");
			
		}else {
			System.out.println("Error: No Borrowers on the system!");
		}
	}
	
	/**
	 * Method called upon when "Display All Borrowers" option selected
	 * @param listOfBorrowers array list of borrowers on the Library System
	 */
	static void DisplayAllBorrowersProcess(ArrayList<Borrower> listOfBorrowers) {
		System.out.println("=== Display Borrowers ===");
		
		// Step 1: Check to see if there is a Borrower to display
		if(!listOfBorrowers.isEmpty()) {
			
			// Step 2: Displays all Borrower's details
			displayAllBorrowers(listOfBorrowers);
		}else {
			System.out.println("Error: No Borrowers on the system!");
		}
	}

	/**
	 * Method called upon when "Borrow Book" option selected
	 * @param listOfBorrowers array list of borrowers on the Library System
	 * @param library array of books on the Library System
	 */
	static void BorrowBookProcess(ArrayList<Borrower> listOfBorrowers, Book[] library) {
		System.out.println("=== Borrow Book ===");
		
		// Step 1: Check to see if there is a borrower to borrow a book
		if(!listOfBorrowers.isEmpty()) {
			
			// Step 2: Check to see if there is at least one book available to borrow
			boolean noBooksAvailable = true;
			for(Book eachBook: library) {
				if(eachBook.isAvailable()) {
					noBooksAvailable = false;
				}
			}
			
			if(!noBooksAvailable) {
				
				// Step 3: Output a selection of borrowers
				System.out.println("Select a Borrower;");
				displayAllBorrowersBrief(listOfBorrowers);
				
				// Step 4: Get selected borrower index
				int theChosenOneIndex = getUserChoice();
				
				// Step 5: Get selected borrower object
				Borrower theChosenOne = listOfBorrowers.get(theChosenOneIndex-1);
				
				// Step 6: Get an array list of available books and display them to user
				System.out.println("Select a Book;");
				ArrayList<Book> booksAvailable = displayAllBooksBrief(library);
				
				// Step 7: Get selected book object index
				int theChosenBookIndex = getUserChoice();
				
				// Step 8: Get selected book object
				Book theChosenBook = booksAvailable.get(theChosenBookIndex-1);
				
				// Step 9: Borrow Book using Borrow method in Borrower class
				theChosenOne.borrowBook(theChosenBook);
				
				// Step 10: Change state of availability for the Book
				theChosenBook.setAvailable(false);
				
			}else {
				System.out.println("Error: No Books Available!");
			}
		}else {
			System.out.println("Error: No Borrowers on the system!");
		}
	}

	/**
	 * Method called upon when "Return Book" option selected
	 * @param listOfBorrowers array list of borrowers on the Library System
	 * @param library array of books on the Library System
	 */
	static void ReturnBookProcess(ArrayList<Borrower> listOfBorrowers, Book[] library) {
		System.out.println("=== Return Book ===");
		
		// Step 1: Check to see if there is a borrower to return a book
		if(!listOfBorrowers.isEmpty()) {
			
			// Step 2: Check to see if there is at least 1 book on loan
			boolean noBooksOnLoan = true;
			for(Book eachBook: library) {
				if(!eachBook.isAvailable()) {
					noBooksOnLoan = false;
				}
			}
			
			if(!noBooksOnLoan) {
				
				// Step 3: Output all borrowers in short
				displayAllBorrowersBrief(listOfBorrowers);
				
				// Step 3: Get selected user
				int theChosenOneIndex = getUserChoice();
				
				// Step 4: Get selected user using selected integer
				Borrower theChosenOne = listOfBorrowers.get(theChosenOneIndex-1);
				
				// Step 5: Output all books to return
				displayAllBooksBorrowed(theChosenOne);
				
				// Step 6: Get selected book index
				int theChosenBookIndex = getUserChoice();
				
				// Step 7: Get selected book object
				Book bookToReturn = theChosenOne.getAllBooks().get(theChosenBookIndex-1);
				
				// Step 8: Borrow Book using Borrow method in Borrower class
				boolean successful = theChosenOne.returnBook(bookToReturn);
				
				// Step 9: Check book was successfully returned
				if(successful) {
					System.out.println("Successfully returned book");
					System.out.println("Book: " + bookToReturn.getId() + " " + bookToReturn.getTitle());
					System.out.println("User: " + theChosenOne.getId() + " " + theChosenOne.getName());
				}else {
					System.out.println("Error: User (" + theChosenOne.getName() + ") did not have");
					System.out.println("Book: " + bookToReturn.getId() + " " + bookToReturn.getTitle());
					System.out.println("on loan!");
				}
		
			}else {
				System.out.println("Error: No Books on Loan!");
			}
		}else {
			System.out.println("Error: No Borrowers on the system!");
		}
	}

	/**
	 * Method creates a new Book object and adds it to the array of books
	 * @param library array of books to add new book object to
	 */
	static Book[] AddBookToLibraryProcess(Book[] library) {
		System.out.println("=== Add Book ===");
		
		// Step 1: Create new Book Object
		Book newBook = createBook();
		
		// Step 2: Add new Book object to new Array of Books
		Book[] newLibrary = Arrays.copyOf(library, library.length+1);
		newLibrary[newLibrary.length-1] = newBook;
		
		// Step 3: return new library array
		return newLibrary;
	}

	static void DisplayAllBooksProcess(Book[] library) {
		System.out.println("\n--------- Library ---------");
		String isAvailable;
		
		for(Book eachBook: library) {
			System.out.println("ID: " + eachBook.getId());
			System.out.println("ISBN: " + eachBook.getIsbn());
			System.out.println("Title: " + eachBook.getTitle());
			System.out.println("Author: " + eachBook.getAuthor());
			
			if(eachBook.isAvailable()) {
				isAvailable = "Yes";
			}else {
				isAvailable = "No";
			}
			System.out.println("Available: " + isAvailable);
			System.out.println("---------------------------");
			
		}
	}

}

