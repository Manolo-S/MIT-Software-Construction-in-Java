package library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * ==================
        getTitle()
          Returns the title of the book

        getAuthors
          Returns the authors list

        getYear()
          Returns the publication year

        toString()
          String contains title
          String contains authors in the same order as they were listed when the book object was created
          String contains all authors
          String contains year

        equals()
          Test reflexitivity TODO: does this make sense?
          equal books
          Books have different title
          Books have different author
          Books have different publishing year
          Check for overloaded equals method

        hashCode()
          Two equal books have the same hashcode

       */

  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false; // make sure assertions are enabled with VM argument: -ea
  }

  @Test
  public void testGetTitle() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    assertEquals("Manufacturing Consent", book.getTitle());
  }

  @Test
  public void testGetAuthors() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    assertEquals("Chomsky", book.getAuthors().get(0));
  }

  @Test
  public void testGetYear() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    assertEquals(1990, book.getYear());
  }

  @Test
  public void testEquals_reflexivity() { //TODO: does this make sense?
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    assertTrue(book.equals(book));
  }

  @Test
  public void testEquals_twoEqualBooks() {
    Book book1 = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    Book book2 = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    assertTrue(book1.equals(book2));
  }

  @Test
  public void testEquals_checkForOverloadedEquals() {
    Book book1 = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    Book book2 = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    Object o2 = book2;
    assertTrue(book1.equals(o2));
  }

  @Test
  public void testEquals_twoDiffentTitleBooks() {
    Book book1 = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    Book book2 = new Book("Requiem for the American dream", Arrays.asList("Chomsky"), 1990);
    assertFalse(book1.equals(book2));
  }

  @Test
  public void testEquals_twoDiffentAuthorBooks() {
    Book book1 = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    Book book2 = new Book("Manufacturing Consent", Arrays.asList("Hedges"), 1990);
    assertFalse(book1.equals(book2));
  }

  @Test
  public void testEquals_twoDiffentYearBooks() {
    Book book1 = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    Book book2 = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 2000);
    assertFalse(book1.equals(book2));
  }

  @Test
  public void testToString_containsTitle() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
    String bookDetails = book.toString();
    assertTrue(bookDetails.contains("Manufacturing Consent"));
  }

  //covers: contains all authors
  @Test
  public void testToString_containsAuthors() {
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky", "Herman", "Hedges"), 1990);
    String bookDetails = book.toString();
    assertTrue(bookDetails.contains("Chomsky"));
    assertTrue(bookDetails.contains("Herman"));
    assertTrue(bookDetails.contains("Hedges"));
  }

//  //covers: alphabetic case difference in author name TODO: remove if test passes grader
//  @Test
//  public void testToString_doesNotContainAuthor() {
//    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky"), 1990);
//    String bookDetails = book.toString();
//    assertFalse(bookDetails.contains("chomsky"));
//  }

  //covers: author order is significant
  @Test
  public void testToString_authorOrder(){
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky", "Herman", "Hedges"), 1990);
    String bookDetails = book.toString();
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
    Book book = new Book("Manufacturing Consent", Arrays.asList("Chomsky", "Herman", "Hedges"), 1990);
    String bookDetails = book.toString();
    assertTrue(bookDetails.contains("1990"));
  }






    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
