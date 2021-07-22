package apm1001;

import java.util.Locale;
import edu.duke.URLResource;

/**
 * This class has one field of URLResource from
 * edu.duke.URLResource package that store the url of
 * html page
 * There are methods to find the youtube links and test it
 */

class YoutubeLinksParser {

    URLResource url = new URLResource("https://www.dukelearntoprogram.com//course2/data/manylinks.html");

    int findWord(String word, String searchingWord) {
        int startPos = word.indexOf(searchingWord);
        if (startPos == -1) {
            return -1;
        } else return startPos;

    }

    String findYoutubeLink(String mainWord){
        String word = mainWord.toLowerCase();
        int youtubePos = findWord(word, "youtube.com");
        if(youtubePos != -1) {
            int startPos = word.lastIndexOf("\"", youtubePos) + 1;
            int endPos = word.indexOf("\"", youtubePos+11);
            String youtubeLink = mainWord.substring(startPos, endPos);
            return youtubeLink;
        }
        return "";
    }

    void testYoutubeLinkSearcher() {
        for (String mainWord : url.words()) {
            String youtubeLink = findYoutubeLink(mainWord);
            if(youtubeLink.isEmpty()) {
                continue;
            } else {
                System.out.println(youtubeLink);
            }
        }
    }

}


public class Main {

    public static void main(String[] args) {
	// write your code here
        YoutubeLinksParser youtube = new YoutubeLinksParser();
        youtube.testYoutubeLinkSearcher();
    }
}

