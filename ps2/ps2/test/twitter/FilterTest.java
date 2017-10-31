package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class FilterTest {

    /*
     * TEST STRATEGY for writtenBy()
     * empty list of tweets
     * one tweet not by searched for author
     * two tweets not by searched for author
     * one tweet by searched for author
     * two tweets by searched for author
     * three tweets, two by searched for and one by not searched for author
     * Tweet list not changed (number and order of tweets not changed)
     */

    /*
     * TEST STRATEGY for inTimeSpan()
     * empty list of tweets
     * one tweet before timespan
     * one tweet after timespan
     * one tweet within timespan
     * two tweets one before and one after timespan
     * two tweets within timespan
     * three tweets, two within timespan and one outside
     * one tweet with timestamp equal to starttime of timespan
     * one tweet with timestamp equal to endtime of timespan
     * Tweet list not changed (number and order of tweets not changed)
     */

    /*
     * TEST STRATEGY for containing()
     * empty list of tweets
     * one tweet with empty string
     * one tweet with string only containing single whitespace TODO: use test or not?
     * one tweet with 1 word, no whitespaces at the start or the end, search word in tweet
     * one tweet with 1 word, no whitespaces at the start or the end, search word NOT in tweet
     * one tweet with 1 word, with a whitespace at the start, search word in tweet
     * one tweet with 1 word, with a whitespace at the end, search word in tweet
     * one tweet with 1 word, with a whitespace at start and at the end, search word in tweet
     * one tweet with 1 word in lowercase, code should find tweet with same word in mixed case, search word in tweet
     * one tweet with 1 word that is equal to the search word plus an extra letter at the start and end
     * one tweet with 3 words separated by whitespace, search word in tweet
     * one tweet with 2 times the same word separated by whitespace, search word in tweet
     * three tweets: each containing the same word, search word in every tweet
     * Tweet list not changed (number and order of tweets not changed)
     */


  private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
  private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
  private static final Instant instantBeforeTimespan = Instant.parse("2016-02-17T09:00:00Z");
  private static final Instant instantAfterTimespan = Instant.parse("2016-02-17T21:00:00Z");
  private static final Instant instantStartOfTimespan = Instant.parse("2016-02-17T10:00:00Z");
  private static final Instant instantEndOfTimespan = Instant.parse("2016-02-17T20:00:00Z");
  private static final Instant instantWithinTimespan_1 = Instant.parse("2016-02-17T15:00:00Z");
  private static final Instant instantWithinTimespan_2 = Instant.parse("2016-02-17T18:00:00Z");

  private static final Tweet tweet1 = new Tweet(1, "CHedges",
      "War is a force that gives us meaning", d1);
  private static final Tweet tweet2 = new Tweet(2, "CHedges", "American fascists", d2);
  private static final Tweet tweet3 = new Tweet(3, "NChomsky", "Understanding power", d2);
  private static final Tweet tweet4 = new Tweet(4, "NChomsky", "Requiem for the American Dream", d2);
  private static final Tweet tweet5 = new Tweet(5, "x1", "lorem ipsum", instantBeforeTimespan);
  private static final Tweet tweet6 = new Tweet(6, "x2", "lorem ipsum", instantAfterTimespan);
  private static final Tweet tweet7 = new Tweet(7, "Alpha", "lorem ipsum", instantStartOfTimespan);
  private static final Tweet tweet8 = new Tweet(8, "Omega", "lorem ipsum", instantEndOfTimespan);
  private static final Tweet tweet9 = new Tweet(9, "Tau", "lorem ipsum", instantWithinTimespan_1);
  private static final Tweet tweet10 = new Tweet(10, "Mu", "lorem ipsum", instantWithinTimespan_2);
  private static final Tweet tweet11 = new Tweet(11, "x", "", d1);
  private static final Tweet tweet12 = new Tweet(12, "x", "orange", d1);
  private static final Tweet tweet13 = new Tweet(13, "x", " orange", d1);
  private static final Tweet tweet14 = new Tweet(14, "x", "orange ", d1);
  private static final Tweet tweet15 = new Tweet(15, "x", " orange ", d1);
  private static final Tweet tweet16 = new Tweet(16, "x", "oRanGE", d1);
  private static final Tweet tweet17 = new Tweet(17, "x", "pear orange banana", d1);
  private static final Tweet tweet18 = new Tweet(18, "x", "orange orange", d1);
  private static final Tweet tweet19 = new Tweet(19, "x", "orange", d1);
  private static final Tweet tweet20 = new Tweet(20, "x", "orange", d1);
  private static final Tweet tweet21 = new Tweet(21, "x", "orange", d1);
  private static final Tweet tweet22 = new Tweet(22, "x", "XorangeX", d1);




  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false; // make sure assertions are enabled with VM argument: -ea
  }

  @Test
  public void testContainingEmptyListOfTweets(){
    List<String> words = Arrays.asList("CHedges");
    List<Tweet> tweets = new ArrayList<>();
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertTrue("Expected: list is empty", filteredTweets.isEmpty());
  }

  @Test
  public void testContainingOneTweetEmptyString(){
    List<String> words = Arrays.asList("orange");
    List<Tweet> tweets = Arrays.asList(tweet11);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertTrue("Expected: list is empty", filteredTweets.isEmpty());
  }

  @Test
  public void testContainingOneTweetSingleWordNoSpaces_1(){
    List<String> words = Arrays.asList("orange");
    List<Tweet> tweets = Arrays.asList(tweet12);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertEquals("Expected: list contains 1 tweet", 1, filteredTweets.size());
  }

  @Test
  public void testContainingOneTweetSingleWordNoSpaces_2(){
    List<String> words = Arrays.asList("apple");
    List<Tweet> tweets = Arrays.asList(tweet12);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertTrue("Expected: list is empty", true);
  }

  @Test
  public void testContainingOneTweetSingleWordSpaceAtStart(){
    List<String> words = Arrays.asList("orange");
    List<Tweet> tweets = Arrays.asList(tweet13);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertEquals("Expected: list contains 1 tweet", 1, filteredTweets.size());
  }

  @Test
  public void testContainingOneTweetSingleWordSpaceAtEnd(){
    List<String> words = Arrays.asList("orange");
    List<Tweet> tweets = Arrays.asList(tweet14);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertEquals("Expected: list contains 1 tweet", 1, filteredTweets.size());
  }

  @Test
  public void testContainingOneTweetSingleWordSpaceAtSTartAndEnd(){
    List<String> words = Arrays.asList("orange");
    List<Tweet> tweets = Arrays.asList(tweet15);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertEquals("Expected: list contains 1 tweet", 1, filteredTweets.size());
  }

  @Test
  public void testContainingOneTweetSingleWordMixedCase(){
    List<String> words = Arrays.asList("orange");
    List<Tweet> tweets = Arrays.asList(tweet16);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertEquals("Expected: list contains 1 tweet", 1, filteredTweets.size());
  }

  @Test
  public void testContainingOneTweetSingleWord_1(){
    List<String> words = Arrays.asList("orange");
    List<Tweet> tweets = Arrays.asList(tweet17);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertEquals("Expected: list contains 1 tweet", 1, filteredTweets.size());
  }

  @Test
  public void testContainingOneTweetSingleWord_2(){
    List<String> words = Arrays.asList("orange");
    List<Tweet> tweets = Arrays.asList(tweet22);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertTrue("Expected: list is empty", true);
  }

  @Test
  public void testContainingOneTweetTwoEqualWords(){
    List<String> words = Arrays.asList("orange");
    List<Tweet> tweets = Arrays.asList(tweet18);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertEquals("Expected: list contains 1 tweet", 1, filteredTweets.size());
  }

  @Test
  public void testContainingThreeTweetsWithSameWord(){
    List<String> words = Arrays.asList("orange");
    List<Tweet> tweets = Arrays.asList(tweet19, tweet20, tweet21);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertEquals("Expected: list contains 3 tweets", 3, filteredTweets.size());
    assertEquals("Expected: tweets are equal", tweets.get(0).getId(), filteredTweets.get(0).getId());
    assertEquals("Expected: tweets are equal", tweets.get(1).getId(), filteredTweets.get(1).getId());
    assertEquals("Expected: tweets are equal", tweets.get(2).getId(), filteredTweets.get(2).getId());
  }

  @Test
  public void testContainingTweetListNotChanged() {
    List<String> words = Arrays.asList("orange");
    List<Tweet> tweets = Arrays.asList(tweet19, tweet20, tweet21);
    List<Tweet> referenceTweetList = Arrays.asList(tweet19, tweet20, tweet21);
    List<Tweet> filteredTweets = Filter.containing(tweets, words);
    assertEquals("Expected: Tweet listsize not changed", 3, tweets.size());
    assertEquals("Tweets are equal", referenceTweetList.get(0), tweets.get(0));
    assertEquals("Tweets are equal", referenceTweetList.get(1), tweets.get(1));
    assertEquals("Tweets are equal", referenceTweetList.get(2), tweets.get(2));
  }



  //================================================================================================
  @Test
  public void testInTimespanEmptyListOfTweets(){
   Timespan timespan = new Timespan(d1, d2);
   List<Tweet> tweets = new ArrayList<>();
   List<Tweet> tweetsInTimespan = Filter.inTimespan(tweets, timespan);
   assertTrue("expected: list is empty", tweetsInTimespan.isEmpty());
  }

  @Test
  public void testInTimespanOneTweetBeforeTimespan(){
   Timespan timespan = new Timespan(instantStartOfTimespan, instantEndOfTimespan);
   List<Tweet> tweets = Arrays.asList(tweet5);
   List<Tweet> tweetsInTimespan = Filter.inTimespan(tweets, timespan);
   assertTrue("Expected: list is empty", tweetsInTimespan.isEmpty());
  }

  @Test
  public void testInTimespanOneTweetAfterTimespan(){
    Timespan timespan = new Timespan(instantStartOfTimespan, instantEndOfTimespan);
    List<Tweet> tweets = Arrays.asList(tweet6);
    List<Tweet> tweetsInTimespan = Filter.inTimespan(tweets, timespan);
    assertTrue("Expected: list is empty", tweetsInTimespan.isEmpty());
  }

  @Test
  public void testInTimespanOneTweetWithinTimespan(){
    Timespan timespan = new Timespan(instantStartOfTimespan, instantEndOfTimespan);
    List<Tweet> tweets = Arrays.asList(tweet9);
    List<Tweet> tweetsInTimespan = Filter.inTimespan(tweets, timespan);
    assertEquals("Expected: list contains 1 tweet", 1, tweetsInTimespan.size());
  }

  @Test
  public void testInTimespanTweetsBeforeAndAfterTimespan(){
    Timespan timespan = new Timespan(instantStartOfTimespan, instantEndOfTimespan);
    List<Tweet> tweets = Arrays.asList(tweet5, tweet6);
    List<Tweet> tweetsInTimespan = Filter.inTimespan(tweets, timespan);
    assertTrue("Expected: list is empty",tweetsInTimespan.isEmpty());
  }

  @Test
  public void testInTimespanTwoTweetsWithinTimespan(){
    Timespan timespan = new Timespan(instantStartOfTimespan, instantEndOfTimespan);
    List<Tweet> tweets = Arrays.asList(tweet9, tweet10);
    List<Tweet> tweetsInTimespan = Filter.inTimespan(tweets, timespan);
    assertEquals("Expected: list contains 2 tweets", 2, tweetsInTimespan.size());
    assertEquals("Expected: list contains tweet 9", tweets.get(0).getId(), tweetsInTimespan.get(0).getId());
    assertEquals("Expected: list contains tweet 10", tweets.get(1).getId(), tweetsInTimespan.get(1).getId());
  }

  @Test
  public void testInTimespanTwoTweetsWithinTimespanOneBefore(){
    Timespan timespan = new Timespan(instantStartOfTimespan, instantEndOfTimespan);
    List<Tweet> tweets = Arrays.asList(tweet9, tweet10, tweet5);
    List<Tweet> tweetsInTimespan = Filter.inTimespan(tweets, timespan);
    assertEquals("Expected: list contains 2 tweets", 2, tweetsInTimespan.size());
    assertEquals("Expected: list contains tweet 9", tweets.get(0).getId(), tweetsInTimespan.get(0).getId());
    assertEquals("Expected: list contains tweet 10", tweets.get(1).getId(), tweetsInTimespan.get(1).getId());
  }

  @Test
  public void testInTimespanOneTweetAtStartOfTimespan(){
    Timespan timespan = new Timespan(instantStartOfTimespan, instantEndOfTimespan);
    List<Tweet> tweets = Arrays.asList(tweet7);
    List<Tweet> tweetsInTimespan = Filter.inTimespan(tweets, timespan);
    assertEquals("Expected: list contains 1 tweet", 1, tweetsInTimespan.size());
  }

  @Test
  public void testInTimespanOneTweetAtEndOfTimespan(){
    Timespan timespan = new Timespan(instantStartOfTimespan, instantEndOfTimespan);
    List<Tweet> tweets = Arrays.asList(tweet8);
    List<Tweet> tweetsInTimespan = Filter.inTimespan(tweets, timespan);
    assertEquals("Expected: list contains 1 tweet", 1, tweetsInTimespan.size());
  }

  @Test
  public void testInTimespanTweetListNotChanged() {
    Timespan timespan = new Timespan(instantStartOfTimespan, instantEndOfTimespan);
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet7, tweet5, tweet10));
    List<Tweet> referenceTweetList = new ArrayList<>(Arrays.asList(tweet7, tweet5, tweet10));
    List<Tweet> tweetsInTimespan = Filter.inTimespan(tweets, timespan);
    assertEquals("Tweet list size not changed", referenceTweetList.size(), tweets.size());
    assertEquals("Tweets are equal", referenceTweetList.get(0), tweets.get(0));
    assertEquals("Tweets are equal", referenceTweetList.get(1), tweets.get(1));
    assertEquals("Tweets are equal", referenceTweetList.get(2), tweets.get(2));
  }

  //=======================================================================================================

  @Test
  public void testWrittenByEmptyTweetList() {
    List<Tweet> tweets = Arrays.asList();
    List<Tweet> writtenBy = Filter.writtenBy(tweets, "CHedges");
    assertTrue("Tweet list is empty", writtenBy.isEmpty());
  }

  @Test
  public void testWrittenByOneTweetNotByAuthor() {
    List<Tweet> tweets = Arrays.asList(tweet3);
    List<Tweet> writtenBy = Filter.writtenBy(tweets, "CHedges");
    assertTrue("Tweet list is empty", writtenBy.isEmpty());
  }

  @Test
  public void testWrittenByTwoTweetsNotByAuthor() {
    List<Tweet> tweets = Arrays.asList(tweet3, tweet4);
    List<Tweet> writtenBy = Filter.writtenBy(tweets, "CHedges");
    assertTrue("Tweet list is empty", writtenBy.isEmpty());
  }

  @Test
  public void testWrittenByOneTweetByAuthor() {
    List<Tweet> tweets = Arrays.asList(tweet1);
    List<Tweet> writtenBy = Filter.writtenBy(tweets, "CHedges");
    assertEquals("Tweet list contains 1 tweet", 1, writtenBy.size());
    assertEquals("Tweet is by searched for author", "CHedges", writtenBy.get(0).getAuthor());
  }

  @Test
  public void testWrittenByTwoTweetsByAuthor() {
    List<Tweet> tweets = Arrays.asList(tweet1, tweet2);
    List<Tweet> writtenBy = Filter.writtenBy(tweets, "CHedges");
    assertEquals("Tweet list contains 2 tweets", 2, writtenBy.size());
    assertEquals("Tweet is by searched for author", "CHedges", writtenBy.get(0).getAuthor());
    assertEquals("Tweet is by searched for author", "CHedges", writtenBy.get(1).getAuthor());
  }

  @Test
  public void testWrittenByThreeTweetsTwoByAuthorOneNotByAuthor() {
    List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3);
    List<Tweet> writtenBy = Filter.writtenBy(tweets, "CHedges");
    assertEquals("Tweet list contains 2 tweets", 2, writtenBy.size());
    assertTrue("Tweet list contains tweet1", writtenBy.contains(tweet1));
    assertTrue("Tweet list contains tweet2", writtenBy.contains(tweet2));
  }

  @Test
  public void testWrittenByTweetListNotModfified() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet3, tweet1, tweet4, tweet2));
    List<Tweet> referenceTweetList = new ArrayList<>(Arrays.asList(tweet3, tweet1, tweet4, tweet2));
    List<Tweet> userNames = Filter.writtenBy(tweets, "CHedges");
    assertEquals("Tweet list size not changed", referenceTweetList.size(), tweets.size());
    assertEquals("Tweets are equal", referenceTweetList.get(0), tweets.get(0));
    assertEquals("Tweets are equal", referenceTweetList.get(1), tweets.get(1));
    assertEquals("Tweets are equal", referenceTweetList.get(2), tweets.get(2));
    assertEquals("Tweets are equal", referenceTweetList.get(3), tweets.get(3));
  }

  //==================================================================================================



    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
