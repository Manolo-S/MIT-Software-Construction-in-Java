package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import library.BookCopy.Condition;
import org.junit.Test;

/**
 * Test suite for Library ADT.
 */
//@RunWith(Parameterized.class)
public class LibraryTest {

    /*
     * Note: all the tests you write here must be runnable against any
     * Library class that follows the spec.  JUnit will automatically
     * run these tests against both SmallLibrary and BigLibrary.
     */

  /**
   * Implementation classes for the Library ADT.
   * JUnit runs this test suite once for each class name in the returned array.
   *
   * @return array of Java class names, including their full package prefix
   */
//  @Parameters(name = "{0}")
//  public static Object[] allImplementationClassNames() {
//    return new Object[]{
//        "library.SmallLibrary",
//        "library.BigLibrary"
//    };
//  }

  /**
   * Implementation class being tested on this run of the test suite.
   * JUnit sets this variable automatically as it iterates through the array returned
   * by allImplementationClassNames.
   */
//  @Parameter
//  public String implementationClassName;

  /**
   * @return a fresh instance of a Library, constructed from the implementation class specified by
   * implementationClassName.
   */
  public Library makeLibrary() {
    return new SmallLibrary();
  }
//
//  public Library makeLibrary() {
//    try {
//      Class<?> cls = Class.forName(implementationClassName);
//      return (Library) cls.newInstance();
//    } catch (ClassNotFoundException e) {
//      throw new RuntimeException(e);
//    } catch (InstantiationException e) {
//      throw new RuntimeException(e);
//    } catch (IllegalAccessException e) {
//      throw new RuntimeException(e);
//    }
//  }
    
    
    /*
     * Testing strategy
     * ==================
     * public BookCopy buy(Book book)
     *    testBuy_buyOneBook
     *
     * public void checkout(Bookcopy copy)
     *    testCheckout_checkOutOneBook
     *
     * public void checkin(BookCopy copy)
     *    testCheckin_checkInOneBook
     *
     * public boolean isAvailable(BookCopy copy)
     *    testIsAvailable
     *
     * public Set<BookCopy> allCopies(Book book)
     *    testAllCopies_3Copies2Available1NotAvailable
     *
     * public Set<BookCopy> availableCopies(Book book)
     *    testAvailableCopies
     *
     * public List<Book> find(String query)
     *    testFind_exactTitle
     *    testFind_exactAuthor
     *    testFind_resultsDescendingByYear
     *
     * public void lose(BookCopy copy)
     *    testLose_OneCopy
     *
     */

  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false; // make sure assertions are enabled with VM argument: -ea
  }

  @Test
  public void testBuy_buyOneBook() {
    Library library = makeLibrary();
    Book book = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1949);

    BookCopy copyInLibrary = library.buy(book);

    assertTrue(copyInLibrary.getCondition() == Condition.GOOD);
    assertTrue(library.isAvailable(copyInLibrary));
  }

  @Test
  public void testCheckout_checkoutOneBook() {
    Library library = makeLibrary();
    Book book = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1949);
    BookCopy copy = library.buy(book);

    library.checkout(copy);

    assertFalse(library.isAvailable(copy));
  }

  @Test
  public void testCheckin_checkInOneBook() {
    Library library = makeLibrary();
    Book book = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1949);
    BookCopy copy = library.buy(book);
    library.checkout(copy);

    library.checkin(copy);

    assertTrue(library.isAvailable(copy));
  }

  @Test
  public void testIsAvailable() {
    Library library = makeLibrary();
    Book book = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1949);
    BookCopy copy = library.buy(book);

    assertTrue(library.isAvailable(copy));
  }

  @Test
  public void testAllCopies_3Copies2Available1NotAvailable() {
    Library library = makeLibrary();
    Book book = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1949);
    BookCopy copy1 = library.buy(book);
    BookCopy copy2 = library.buy(book);
    BookCopy copy3 = library.buy(book);
    library.checkout(copy1);

    Set<BookCopy> books = library.allCopies(book);
    assertEquals(3, books.size());
  }

  @Test
  public void testAvailableCopies(){
    Library library = makeLibrary();
    Book book = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1949);
    BookCopy copy1 = library.buy(book);
    BookCopy copy2 = library.buy(book);
    BookCopy copy3 = library.buy(book);
    library.checkout(copy1);

    Set<BookCopy> books = library.availableCopies(book);
    assertEquals(books.size(), 2);
  }

  @Test
  public void testFind_exactTitle() {
    Library library = makeLibrary();
    Book book1 = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1949);
    Book book2 = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1952);
    Book book3 = new Book("Sapiens", Arrays.asList("Harari"), 2011);
    Book book4 = new Book("Homo Deus", Arrays.asList("Harari"), 2017);
    library.buy(book1);
    library.buy(book2);
    library.buy(book3);
    library.buy(book4);

    List<Book> books = library.find("Nineteen Eighty-Four");

    assertEquals(books.size(), 2);
    assertEquals(books.get(0).getTitle(), "Nineteen Eighty-Four");
    assertEquals(books.get(1).getTitle(), "Nineteen Eighty-Four");
  }

  @Test
  public void testFind_exactAuthor() {
    Library library = makeLibrary();
    Book book1 = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1949);
    Book book2 = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1952);
    Book book3 = new Book("Sapiens", Arrays.asList("Harari"), 2011);
    Book book4 = new Book("Homo Deus", Arrays.asList("Harari"), 2017);
    library.buy(book1);
    library.buy(book2);
    library.buy(book3);
    library.buy(book4);

    List<Book> books = library.find("Orwell");

    assertEquals(books.size(), 2);
    assertEquals(books.get(0).getAuthors().get(0), "Orwell");
    assertEquals(books.get(1).getAuthors().get(0), "Orwell");
  }

  @Test
  public void testFind_resultsDescendingByYear() {
    Library library = makeLibrary();
    Book book1 = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1949);
    Book book2 = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1952);
    Book book3 = new Book("Sapiens", Arrays.asList("Harari"), 2011);
    Book book4 = new Book("Homo Deus", Arrays.asList("Harari"), 2017);
    library.buy(book1);
    library.buy(book2);
    library.buy(book3);
    library.buy(book4);

    List<Book> books = library.find("Nineteen Eighty-Four");

    assertEquals(books.size(), 2);
    assertEquals(books.get(0).getYear(), 1952);
    assertEquals(books.get(1).getYear(), 1949);
  }


  @Test
  public void testLose_OneCopy() {
    Library library = makeLibrary();
    Book book = new Book("Nineteen Eighty-Four", Arrays.asList("Orwell"), 1949);
    BookCopy copy = library.buy(book);
    library.buy(book);
    library.checkout(copy);

    library.lose(copy);

    assertEquals(1, library.allCopies(book).size());
  }










































    

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
