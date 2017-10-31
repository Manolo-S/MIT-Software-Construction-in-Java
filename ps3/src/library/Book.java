package library;

import java.util.Collections;
import java.util.List;

/**
 * Book is an immutable type representing an edition of a book -- not the physical object,
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book {

  final private String title;
  final private List<String> authors;
  final private int year;

  /* Rep invariants:
      Book objects are immutable
      Book title consists of a minimum of one non white space character
      Year must be non negative
      Author list size must be >= 1  //
      Author name consists of a minimum of one non white space character

     Abstraction function:
      represents a book that is uniquely identified by its title, author list and publication year

    Safety from rep exposure:
      all fields are private and final
      fields title and year are resp. string and int so guaranteed immutable
      the authors list is made immutable with the Collections.unmodifiable() method
  */

  /**
   * Make a Book.
   *
   * @param title Title of the book. Must contain at least one non-space character.
   * @param authors Names of the authors of the book.  Must have at least one name, and each name
   * must contain at least one non-space character.
   * @param year Year when this edition was published in the conventional (Common Era) calendar.
   * Must be non negative.
   */

  public Book(String title, List<String> authors, int year) {
    this.title = title;
    this.authors = Collections.unmodifiableList(authors);
    this.year = year;
    checkRep();
  }

  // assert the rep invariants
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
  }

  /**
   * @return the title of this book
   */
  public String getTitle() {
    checkRep();
    return this.title;
  }

  /**
   * @return the authors of this book
   */
  public List<String> getAuthors() {
    checkRep();
    return this.authors;
  }

  /**
   * @return the year that this book was published
   */
  public int getYear() {
    checkRep();
    return this.year;
  }

  /**
   * @return human-readable representation of this book that includes its title, authors, and
   * publication year
   */
  public String toString() {
    String authorList = "";
    for (String author : authors) {
      authorList += author + " ";
    }
    checkRep();
    return this.title + " " + authors + this.year;
  }

  @Override
  public boolean equals(Object thatObject) {
    if (!(thatObject instanceof Book)) {
      return false;
    }
    Book thatBook = (Book) thatObject;
    boolean titlesEqual = this.title == thatBook.getTitle();
    boolean yearEqual = this.year == thatBook.getYear();
    boolean authorlistsLengthEqual = this.authors.size() == thatBook.getAuthors().size();
    boolean authorsEqual = true;
    for (int i = 0; i < this.authors.size(); i++) {
      if (this.authors.get(i) != thatBook.getAuthors().get(i)) {
        authorsEqual = false;
        break;
      }
    }
    return titlesEqual && yearEqual && authorlistsLengthEqual && authorsEqual;
  }

  @Override
  public int hashCode() {
    return (title.length() + authors.size() + year) * 37;
  }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
