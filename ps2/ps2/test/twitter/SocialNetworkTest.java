package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TEST STRATEGY for guessFollowsGraph():
     * Empty list of tweets
     * one tweet with empty string for text
     * one tweet with text without any @-mentioned username
     * one tweet with text with one @mention of the tweet author themselves
     * one tweet with text with one @mentioned user
     * one tweet with text with one @mentioned user mentioned twice
     * one tweet with text with one @mentioned user but with different casemix
     * one tweet with text with two @mentioned users
     * two different tweets from same author with both with the same text with one @mentioned user
     * two different tweets from same author with texts that each mention a different @mentioned user
     * two tweets from different authors
     */

    /*
     * TEST STRATEGY for influencers():
     * Empty graph
     * graph with 1 user, no users followed
     * graph with 1 user, 2  users followed
     * graph with 2 users with resp 2 and 3 followers
     */

  private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");

  private static final Tweet tweet1 = new Tweet(1, "CHedges", "", d1);
  private static final Tweet tweet2 = new Tweet(2, "CHedges", "bla bla bla", d1);
  private static final Tweet tweet3 = new Tweet(3, "CHedges", "@CHEDGES", d1);
  private static final Tweet tweet4 = new Tweet(4, "CHedges", "@NChomsky", d1);
  private static final Tweet tweet5 = new Tweet(5, "CHedges", "@NChomsky @NChomsky", d1);
  private static final Tweet tweet6 = new Tweet(6, "CHedges", "@NChomsky @GOrwell", d1);
  private static final Tweet tweet7 = new Tweet(7, "CHedges", "@NChomsky", d1);
  private static final Tweet tweet8 = new Tweet(8, "CHedges", "@EJarecki", d1);
  private static final Tweet tweet9 = new Tweet(9, "NChomsky", "@CHedges", d1);
  private static final Tweet tweet10 = new Tweet(10, "NChomsky", "@CHedges @chEDGES", d1);


  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false; // make sure assertions are enabled with VM argument: -ea
  }

  @Test
  public void testInfluencersEmpty() {
    Map<String, Set<String>> followsGraph = new HashMap<>();
    List<String> influencers = SocialNetwork.influencers(followsGraph);
    assertTrue("Expected: empty list", influencers.isEmpty());
  }

  @Test
  public void testInfluencers_1UserNoFollowers() {
    final Map<String, Set<String>> graph = new HashMap<>();
    graph.put("CHedges", new HashSet<String>(Arrays.asList()));
    List<String> influencersRanking = SocialNetwork.influencers(graph);
    assertTrue("Expected: list is empty", influencersRanking.isEmpty());
  }

  @Test
  public void testInfluencers_1User2UsersFollowed() {
    final Map<String, Set<String>> graph = new HashMap<>();
    graph.put("CHedges", new HashSet<String>(Arrays.asList("@Chomsky", "@EJarecki")));
    List<String> influencersRanking = SocialNetwork.influencers(graph);
    assertEquals("Expected: list contains 2 usernames", 2, influencersRanking.size());
  }

  @Test
  public void testInfluencers_3UsersWith1or2or3UsersFollowed() {
    final Map<String, Set<String>> graph = new HashMap<>();
    graph.put("CHedges", new HashSet<String>(Arrays.asList("@NChomsky", "@EJarecki")));
    graph.put("NChomsky", new HashSet<String>(Arrays.asList("@EJarecki")));
    graph.put("HZinn", new HashSet<String>(Arrays.asList("@EJarecki", "@NChomsky", "@CHedges")));
    List<String> influencersRanking = SocialNetwork.influencers(graph);
    assertEquals("Expected: list contains 3 usernames", 3, influencersRanking.size());
    assertEquals("Expected: most followed user is @EJarecki", "@EJarecki",
        influencersRanking.get(0));
  }

  @Test
  public void testGuessFollowsGraph_EmptyList() {
    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
    assertTrue("Expected: followsGraph is empty", followsGraph.isEmpty());
  }

  @Test
  public void testGuessFollowsGraph_OneTweetEmptyString() {
    List<Tweet> tweets = Arrays.asList(tweet1);
    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    assertTrue("Expected: graph is empty or contains one user who follows 0 users",
        followsGraph.isEmpty() || (followsGraph.size() == 1 && followsGraph.get("CHedges")
            .isEmpty()));
  }

  @Test
  public void testGuessFollowsGraph_OneTweetNoMentionedUser() {
    List<Tweet> tweets = Arrays.asList(tweet2);
    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    assertTrue("Expected: graph is empty or contains one user who follows 0 users",
        followsGraph.isEmpty() || (followsGraph.size() == 1 && followsGraph.get("CHedges")
            .isEmpty()));
  }

  @Test
  public void testGuessFollowsGraph_OneTweetAuthorMentionsThemselves() {
    List<Tweet> tweets = Arrays.asList(tweet3);
    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    assertTrue("Expected: graph is empty or contains one user who follows 0 users",
        followsGraph.isEmpty() || (followsGraph.size() == 1 && followsGraph.get("CHedges")
            .isEmpty()));
  }

  @Test
  public void testGuessFollowsGraph_OneTweetOneMentionedUser() {
    List<Tweet> tweets = Arrays.asList(tweet4);
    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    assertTrue("Expected: graph contains 1 author who follows 1 user",
        followsGraph.size() == 1 && followsGraph.get("CHedges").size() == 1);
  }

  @Test
  public void testGuessFollowsGraph_OneTweetOneUserMentionedTwice() {
    List<Tweet> tweets = Arrays.asList(tweet5);
    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    assertTrue("Expected: graph contains 1 author who follows 1 user",
        followsGraph.size() == 1 && followsGraph.get("CHedges").size() == 1);
  }

  @Test
  public void testGuessFollowsGraph_OneTweetOneUserMentionedTwiceDifferentLettercase() {
    List<Tweet> tweets = Arrays.asList(tweet10);
    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    assertTrue("Expected: graph contains 1 author who follows 1 user",
        followsGraph.size() == 1 && followsGraph.get("NChomsky").size() == 1);
  }

  @Test
  public void testGuessFollowsGraph_OneTweetTwoMentionedUsers() {
    List<Tweet> tweets = Arrays.asList(tweet6);
    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    assertTrue("Expected: graph contains 1 author who follows 2 users",
        followsGraph.size() == 1 && followsGraph.get("CHedges").size() == 2);
  }

  @Test
  public void testGuessFollowsGraph_TwoTweetsSameAuthorSameMentionedUser() {
    List<Tweet> tweets = Arrays.asList(tweet4, tweet7);
    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    assertTrue("Expected: graph contains 1 author who follows 1 users",
        followsGraph.size() == 1 && followsGraph.get("CHedges").size() == 1);
  }

  @Test
  public void testGuessFollowsGraph_TwoTweetsSameAuthorDifferentMentionedUsers() {
    List<Tweet> tweets = Arrays.asList(tweet7, tweet8);
    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    assertTrue("Expected: graph contains 1 author who follows 2 users",
        followsGraph.size() == 1 && followsGraph.get("CHedges").size() == 2);
  }

  @Test
  public void testGuessFollowsGraph_TwoTweetsDifferentAuthors() {
    List<Tweet> tweets = Arrays.asList(tweet8, tweet9);
    Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
    assertEquals("Expected: graph contains 2 authors", 2,
        followsGraph.size());
  }


    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
