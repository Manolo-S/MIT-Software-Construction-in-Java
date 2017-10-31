package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

  /**
   * Get the time period spanned by tweets.
   *
   * @param tweets list of tweets with distinct ids, not modified by this method.
   * @return a minimum-length time interval that contains the timestamp of every tweet in the list.
   */
  public static Timespan getTimespan(List<Tweet> tweets) {
    Timespan timespan = null;
    if (tweets.isEmpty()) {
      throw new IllegalArgumentException("List is empty");
    }
    if (tweets.size() == 1) {
      Instant start = tweets.get(0).getTimestamp();
      Instant end = tweets.get(0).getTimestamp();
      timespan = new Timespan(start, end);
    } else {
      Comparator<Tweet> compareByTimeStamp = (t1, t2) -> t1.getTimestamp()
          .compareTo(t2.getTimestamp());
      List<Tweet> copyOfTweetsList = new ArrayList<>(tweets);
      copyOfTweetsList.sort(compareByTimeStamp);
      Instant start = copyOfTweetsList.get(0).getTimestamp();
      Instant end = copyOfTweetsList.get(tweets.size() - 1).getTimestamp();
      timespan = new Timespan(start, end);
    }
    return timespan;

  }

  /**
   * @param tweets list of tweets with distinct ids, not modified by this method.
   * @return the set of usernames who are mentioned in the text of the tweets. A username-mention is
   * "@" followed by a Twitter username (as defined by Tweet.getAuthor()'s spec). The
   * username-mention cannot be immediately preceded or followed by any character valid in a Twitter
   * username. For this reason, an email address like bitdiddle@mit.edu does NOT contain a mention
   * of the username mit. Twitter usernames are case-insensitive, and the returned set may include a
   * username at most once.
   */
  public static Set<String> getMentionedUsers(List<Tweet> tweets) {
    String regex = "(?<![_A-Za-z0-9-])@[_A-Za-z0-9-]+";
    Pattern pattern = Pattern.compile(regex);
    Set<String> userNamesLowerCase = new HashSet<>();
    Set<String> userNames = new HashSet<>();
    String userName;
    String userNameLowerCase;
    for (Tweet tweet : tweets) {
      String tweetText = tweet.getText();
      Matcher matcher = pattern.matcher(tweetText);
      while (matcher.find()) {
        userName = matcher.group();
//        System.out.println("user name in tweet: " + userName);
        userNameLowerCase = userName.toLowerCase();
        if (!userNamesLowerCase.contains(userNameLowerCase)) {
          userNames.add(userName);
          userNamesLowerCase.add(userNameLowerCase);
        }
      }
    }
//    System.out.println("usernames set: " + userNames);
    return userNames;
  }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
