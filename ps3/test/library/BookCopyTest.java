package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import library.BookCopy.Condition;
import org.junit.Test;

/**
 * Test suite for BookCopy ADT.
 */
public class BookCopyTest {

    /*
     * Testing strategy
     * ==================
     * 
     * public Book getBook()
     *    testGetBook - returned book equals original that was copied
     *
     * public Condition getCondition()
     *    testGetCondition - returned book condition equals condition that was assigned to book copy
     *
     * public voidSetCondition(Condition condition)
     *    testSetCondition - the condition value that is set is equal to the value that's retrieved
     *                       from the book copy
     *
     * equals()
     *    testEquals_TwoCopies - two copies from the same book should not be equal
     *    testEquals_OneCopyAndExtraReferenceToCopy - these two references should be equal
     *
     * hashCode()
     *    hashCodeTwoCopies - two copies from one book should have different hashCodes
     *
     * toString()
     *    testToString_containsTitle - string contains title
     *    testToString_containsAuthors - string contains all authors
     *    testToString_authorOrder - string contains authors in the same way they are listed in the Book object
     *    testToString_containsYear - string contains publication year
     *    testToString_containsCondition - string contains book condition
     *
     */

  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false; // make sure assertions are enabled with VM argument: -ea
  }

  @Test
  public void testGetBook() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    BookCopy copy = new BookCopy(book);
    Book myCopy = copy.getBook();
    assertTrue(book.equals(myCopy));
  }

  @Test
  public void testGetCondition() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    Condition referenceCondition = Condition.GOOD;
    BookCopy copy = new BookCopy(book);
    Condition condition = copy.getCondition();
    assertTrue(referenceCondition == condition);
  }

  @Test
  public void testSetCondition() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    BookCopy copy = new BookCopy(book);
    copy.setCondition(Condition.DAMAGED);
    assertTrue(copy.getCondition() == Condition.DAMAGED);
  }

  @Test
  public void testEnumCondition() {
    Condition[] values = Condition.values();
    assertTrue(values[0].toString().equals("GOOD"));
    assertTrue(values[1].toString().equals("DAMAGED"));
    assertTrue(values.length == 2);
  }

  @Test
  public void testEquals_twoCopies() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    BookCopy copy1 = new BookCopy(book);
    BookCopy copy2 = new BookCopy(book);
    assertFalse(copy1 == copy2);
  }

  @Test
  public void testEquals_oneCopyAndExtraReferenceToCopy() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    BookCopy copy = new BookCopy(book);
    Object o = copy;
    assertTrue(copy == o);
  }

  @Test
  public void testHashCode_copyAndExtraReferenceToCopy() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    BookCopy copy = new BookCopy(book);
    BookCopy o = copy;
    assertTrue(copy.hashCode() == o.hashCode());
  }

  @Test
  public void testToString_containsTitle() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    BookCopy copy = new BookCopy(book);
    String bookDetails = copy.toString();
    assertTrue(bookDetails.contains("Manufacturing Consent"));
  }

  //covers: contains all authors
  @Test
  public void testToString_containsAuthors() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky", "Herman", "Hedges"),
        1990);
    BookCopy copy = new BookCopy(book);
    String bookDetails = copy.toString();
    assertTrue(bookDetails.contains("Chomsky"));
    assertTrue(bookDetails.contains("Herman"));
    assertTrue(bookDetails.contains("Hedges"));
  }


  //covers: author order is significant
  @Test
  public void testToString_authorOrder() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky", "Herman", "Hedges"),
        1990);
    BookCopy copy = new BookCopy(book);
    String bookDetails = copy.toString();
    String regex = ".*(Chomsky).*(Herman).*(Hedges).*";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(bookDetails);
    matcher.find();
    assertEquals("Chomsky", matcher.group(1));
    assertEquals("Herman", matcher.group(2));
    assertEquals("Hedges", matcher.group(3));
  }

  @Test
  public void testToString_containsYear() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky", "Herman", "Hedges"),
        1990);
    BookCopy copy = new BookCopy(book);
    String bookDetails = copy.toString();
    assertTrue(bookDetails.contains("1990"));
  }

  @Test
  public void testToString_containsCondition() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky", "Herman", "Hedges"),
        1990);
    BookCopy copy = new BookCopy(book);
    String bookDetails = copy.toString();
    assertTrue(bookDetails.contains("good"));
  }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
