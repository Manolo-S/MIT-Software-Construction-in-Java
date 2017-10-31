package library;


import java.util.Collections;
import java.util.List;

/**
 * BookCopy is a mutable type representing a particular copy of a book that is held in a library's
 * collection.
 */
public class BookCopy {

  final private String title;
  final private List<String> authors;
  final private int year;
  private Condition condition;

  /* Rep invariants:
      BookCopy objects are mutable (Condition is mutable) TODO invariant or not?
      Book title consists of a minimum of one non white space character
      Year must be non negative
      Author list size must be >= 1  //
      Author name consists of a minimum of one non white space character

     Abstraction function:
      represents a book copy that is identified by its title, author list, publication year
      and condition

    Safety from rep exposure:
      all fields are private and final except for the condition field
      the condition field is private and is only accessible through a setter
      fields title and year are resp. string and int so guaranteed immutable
      the authors list is made immutable with the Collections.unmodifiable() method
  */


  public static enum Condition {
    GOOD, DAMAGED
  }

  /**
   * Make a new BookCopy, initially in good condition.
   *
   * @param book the Book of which this is a copy
   */
  public BookCopy(Book book) {
    this.title = book.getTitle();
    this.authors = Collections.unmodifiableList(book.getAuthors()); //TODO make unmodifiable necessary?
    this.year = book.getYear();
    this.condition = Condition.GOOD;
    checkRep();
  }

  // assert the rep invariant
  private void checkRep() {
    assert title != null : "null reference should not be used";
    assert title
        .matches(".*\\S.*") : "Book title consists of a minimum of one non white space character";
    assert year >= 0 : "Year must be non negative";
    assert authors != null : "null reference should not be used";
    assert authors.size() >= 1 : "author list size must be >= 1";
    assert authors.get(0)
        .matches(".*\\S.*") : "Author name consists of a minimum of one non white space character";
    int listSize = authors.size();
    try {
      authors.add("test");
    } catch (UnsupportedOperationException e) {
    }
    assert authors.size() == listSize : "authors list should not be modifiable";
    assert condition != null : "null reference should not be used";
  }

  /**
   * @return the Book of which this is a copy
   */
  public Book getBook() {
    checkRep();
    return new Book(title, authors, year);
  }

  /**
   * @return the condition of this book copy
   */
  public Condition getCondition() {
    checkRep();
    return condition;
  }

  /**
   * Set the condition of a book copy.  This typically happens when a book copy is returned and a
   * librarian inspects it.
   *
   * @param condition the latest condition of the book copy
   */
  public void setCondition(Condition condition) {
    this.condition = condition;
    checkRep();
  }

  /**
   * @return human-readable representation of this book that includes book.toString() and the words
   * "good" or "damaged" depending on its condition
   */
  public String toString() {
    String authorList = "";
    for (String author : authors) {
      authorList += author + " ";
    }
    checkRep();
    return this.title + " " + authors + this.year + " " + condition.toString().toLowerCase();
  }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
