package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SmallLibrary represents a small collection of books, like a single person's home collection.
 */
public class SmallLibrary implements Library {

  // rep
  private Set<BookCopy> inLibrary;
  private Set<BookCopy> checkedOut;

  // rep invariant:
  //    the intersection of inLibrary and checkedOut is the empty set
  //
  // abstraction function:
  //    represents the collection of books inLibrary union checkedOut,
  //      where if a book copy is in inLibrary then it is available,
  //      and if a copy is in checkedOut then it is checked out

  // TODO: safety from rep exposure argument

  public SmallLibrary() {
    inLibrary = new HashSet<>();
    checkedOut = new HashSet<>();
  }

  // assert the rep invariant
  private void checkRep() {
    Set<BookCopy> inLibraryDuplicate = new HashSet<>();
    inLibraryDuplicate.addAll(inLibrary);
    inLibraryDuplicate.retainAll(checkedOut);
    assert inLibraryDuplicate.isEmpty() : "inLibrary and checkedOut intersection should be empty";
  }

  @Override
  public BookCopy buy(Book book) {
    BookCopy copy = new BookCopy(book);
    inLibrary.add(copy);
    checkRep();
    return copy;
  }

  @Override
  public void checkout(BookCopy copy) {
    if (inLibrary.contains(copy)) {
      inLibrary.remove(copy);
      checkedOut.add(copy);
    }
    checkRep();
  }

  @Override
  public void checkin(BookCopy copy) {
    if (checkedOut.contains(copy)) {
      checkedOut.remove(copy);
      inLibrary.add(copy);
    }
    checkRep();
  }

  @Override
  public boolean isAvailable(BookCopy copy) {
    checkRep();
    return inLibrary.contains(copy);
  }

  @Override
  public Set<BookCopy> allCopies(Book book) {
    Set<BookCopy> allCopies = new HashSet<>();
    for (BookCopy c : inLibrary) {
      if (c.toString().contains(book.getTitle()) && c.toString().contains("" + book.getYear())) {
        allCopies.add(c);
      }
    }
    for (BookCopy c : checkedOut) {
      if (c.toString().contains(book.getTitle()) && c.toString().contains("" + book.getYear())) {
        allCopies.add(c);
      }
    }
    checkRep();
    return allCopies;
  }

  @Override
  public Set<BookCopy> availableCopies(Book book) {
    Set<BookCopy> allCopies = new HashSet<>();
    for (BookCopy bookCopy : inLibrary) {
      if (book.toString().contains(book.getTitle()) && book.toString().contains("" + book.getYear())) {
        allCopies.add(bookCopy);
      }
    }
    checkRep();
    return allCopies;
  }

  @Override
  public List<Book> find(String query) {
    List<Book> books = new ArrayList<>();
    for (BookCopy copy : inLibrary) {
      if (copy.toString().contains(query) && !books.contains(copy.getBook())) {
        books.add(copy.getBook());
      }
    }
    for (BookCopy copy : checkedOut) {
      if (copy.toString().contains(query) && !books.contains(copy.getBook())) {
        books.add(copy.getBook());
      }
    }

    Comparator<Book> byYear = (b1, b2) -> b2.getYear() - b1.getYear();
    Collections.sort(books, byYear);
    System.out.println(books);
    return books;
  }

  @Override
  public void lose(BookCopy copy) {
    for (BookCopy bookCopy : inLibrary) {
      if (copy == bookCopy) {
        inLibrary.remove(copy);
        return;
      }
    }

    for (BookCopy bookCopy : checkedOut) {
      if (copy == bookCopy) {
        checkedOut.remove(copy);
        return;
      }
    }
  }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
