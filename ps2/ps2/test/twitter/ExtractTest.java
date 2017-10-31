package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     *
     * Partition the inputs as follows:
     * Number of Tweets in a list: 1, 2 or 5
     * Timestamps of tweets: (1) all different, (2) all the same, (3) two the same and the rest different
     * List order based on timestamp: ascending, descending, not ascending nor descending
     */


  /* TEST STRATEGY for getMentionedUsers()
   * some of these may be tested more than once  with different tweet(s)
   * No usernames in tweets
   * 1 username in tweet
   * 1 username mentioned twice in Tweet
   * 2 usernames in tweet
   * Two tweets containing same username but different case uppercase combination
   * Two tweets containing same usernames but different case uppercase combination
   * Tweet list not modified (number and order of tweets not changed)
   */

  private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
  private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
  private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
  private static final Instant d4 = Instant.parse("2016-02-17T13:00:00Z");
  private static final Instant d5 = Instant.parse("2016-02-17T14:00:00Z");
  private static final Instant d6 = Instant.parse("2016-02-17T10:00:00Z"); //same timestamp as d1
  private static final Instant instant = Instant.parse("2016-02-17T14:00:00Z");

  private static final Tweet tweet1 = new Tweet(1, "user1", "Tweet text 1", d1);
  private static final Tweet tweet2 = new Tweet(2, "user2", "Tweet text 2", d2);
  private static final Tweet tweet3 = new Tweet(3, "user3", "Tweet text 3", d3);
  private static final Tweet tweet4 = new Tweet(4, "user4", "Tweet text 4", d4);
  private static final Tweet tweet5 = new Tweet(5, "user5", "Tweet text 5", d5);
  private static final Tweet tweet6 = new Tweet(6, "user6", "Tweet text 6", d6);
  private static final Tweet tweet7 = new Tweet(7, "user7", "@Chedges", instant);
  private static final Tweet tweet8 = new Tweet(8, "user8", "@cHEDGES", instant);
  private static final Tweet tweet9 = new Tweet(9, "user9", "@cHEDGES @nCHOMSKY", instant);
  private static final Tweet tweet10 = new Tweet(10, "user10", "@Chedges @Nchomsky", instant);
  private static final Tweet tweet11 = new Tweet(11, "user11", "abc@nl blabla", instant);
  private static final Tweet tweet12 = new Tweet(12, "user12", " @CHedges ", instant);
  private static final Tweet tweet13 = new Tweet(13, "user13", "%@CHedges#", instant);
  private static final Tweet tweet14 = new Tweet(14, "user14", "@CHedges @cHedges", instant);
  private static final Tweet tweet15 = new Tweet(15, "user15", "", instant);


  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false; // make sure assertions are enabled with VM argument: -ea
  }

  @Test
  public void testGetMentionedUsersNoUserNameinTweet_1() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet15));
    Set<String> userNames = Extract.getMentionedUsers(tweets);
    assertEquals("expected: No usernames found in tweet", true, userNames.isEmpty());
  }

  @Test
  public void testGetMentionedUsersNoUserNameinTweet_2() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet11));
    Set<String> userNames = Extract.getMentionedUsers(tweets);
    assertEquals("expected: No usernames found in tweet", true, userNames.isEmpty());
  }

  @Test
  public void testGetMentionedUsersOneUsernameInTweet_1() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet12));
    Set<String> userNames = Extract.getMentionedUsers(tweets);
    assertEquals("expected: One username found in tweet", 1, userNames.size());
  }

  @Test
  public void testGetMentionedUsersOneUsernameInTweet_2() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet13));
    Set<String> userNames = Extract.getMentionedUsers(tweets);
    assertEquals("expected: One username found in tweet", 1, userNames.size());
  }

  @Test
  public void testGetMentionedUsersOneUsernameInTweet_3() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet7));
    Set<String> userNames = Extract.getMentionedUsers(tweets);
    assertEquals("expected: One username found in tweet", 1, userNames.size());
  }

  @Test
  public void testGetMentionedUsersOneUsernameTwiceInTweet() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet14));
    Set<String> userNames = Extract.getMentionedUsers(tweets);
    assertEquals("expected: One username found in tweet", 1, userNames.size());
  }

  @Test
  public void testGetMentionedUsersTwoUsernamesInTweet() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet10));
    Set<String> userNames = Extract.getMentionedUsers(tweets);
    assertEquals("expected: Two usernames found in tweet", 2, userNames.size());
  }

  @Test
  public void testGetMentionedUsersTwoTweetsSameUserDifferentLetterCaseCombination() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet7, tweet8));
    Set<String> userNames = Extract.getMentionedUsers(tweets);
    assertEquals("expected: One username found in tweets", 1, userNames.size());
  }

  @Test
  public void testGetMentionedUsersTwoTweetsSameUsersDifferentLetterCaseCombination() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet9, tweet10));
    Set<String> userNames = Extract.getMentionedUsers(tweets);
    assertEquals("expected: 2 usernames found in tweets", 2, userNames.size());
  }

  @Test
  public void testGetMentionedUsersTweetListNotModfified() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet6, tweet5, tweet1));
    List<Tweet> referenceTweetList = Arrays.asList(tweet6, tweet5, tweet1);
    Set<String> userNames = Extract.getMentionedUsers(tweets);
    assertEquals("expected: Tweet list size not changed", referenceTweetList.size(), tweets.size());
    assertEquals("expected: Tweets are equal", referenceTweetList.get(0), tweets.get(0));
    assertEquals("expected: Tweets are equal", referenceTweetList.get(1), tweets.get(1));
    assertEquals("expected: Tweets are equal", referenceTweetList.get(2), tweets.get(2));
  }

  //===================================================================================================

    @Test
  public void testGetTimespanOneTweet() {
    Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));

    assertEquals("expected start", d1, timespan.getStart());
    assertEquals("expected end", d1, timespan.getEnd());
  }


  @Test
  public void testGetTimespanTwoTweets() {
    Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));

    assertEquals("expected start", d1, timespan.getStart());
    assertEquals("expected end", d2, timespan.getEnd());
  }


  @Test
  public void testGetTimespanTwoTweetsSameTimestamp() {
    Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet6));

    assertEquals("expected start", d6, timespan.getStart());
    assertEquals("expected end", d1, timespan.getEnd());
  }

  @Test
  public void testGetTimeSpanTwoTweetsAscendingTimestamps() {
    Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
    assertEquals("expected start", d1, timespan.getStart());
    assertEquals("expected end", d2, timespan.getEnd());
  }

  @Test
  public void testGetTimeSpanTwoTweetsDescendingTimestamps() {
    Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet1));
    assertEquals("expected start", d1, timespan.getStart());
    assertEquals("expected end", d2, timespan.getEnd());
  }

  @Test
  public void testGetTimespanFiveTweetsDifferentAscendingTimestamps() {
    Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
    assertEquals("expected start", d1, timespan.getStart());
    assertEquals("expected end", d5, timespan.getEnd());
  }

  @Test
  public void testGetTimespanFiveTweetsDifferentDescendingTimestamps() {
    Timespan timespan = Extract.getTimespan(Arrays.asList(tweet5, tweet4, tweet3, tweet2, tweet1));
    assertEquals("expected start", d1, timespan.getStart());
    assertEquals("expected end", d5, timespan.getEnd());
  }

  @Test
  public void testGetTimespanFiveTweetsDifferentOutOfOrderTimestamps() {
    Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet5, tweet1, tweet3, tweet4));
    assertEquals("expected start", d1, timespan.getStart());
    assertEquals("expected end", d5, timespan.getEnd());
  }

  @Test
  public void testGetTimespanFiveTweetsTwoTimestampsEqual() {
    Timespan timespan = Extract.getTimespan(Arrays.asList(tweet6, tweet5, tweet1, tweet3, tweet4));
    assertEquals("expected start", d1, timespan.getStart());
    assertEquals("expected end", d5, timespan.getEnd());
  }

  @Test
  public void testGetTimespanTweetListNotModfified() {
    List<Tweet> tweets = new ArrayList<>(Arrays.asList(tweet6, tweet5, tweet1));
    List<Tweet> referenceTweetList = Arrays.asList(tweet6, tweet5, tweet1);
    Timespan timespan = Extract.getTimespan(tweets);
    assertEquals("don't modify tweet list size", referenceTweetList.size(), tweets.size());
    assertEquals(referenceTweetList.get(0), tweets.get(0));
    assertEquals(referenceTweetList.get(1), tweets.get(1));
    assertEquals(referenceTweetList.get(2), tweets.get(2));
  }

    @Test
  public void testGetMentionedUsersNoMention() {
    Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));

    assertTrue("expected empty set", mentionedUsers.isEmpty());
  }



    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
