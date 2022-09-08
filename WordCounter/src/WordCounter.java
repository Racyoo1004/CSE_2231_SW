import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Write a Java program that counts word occurrences in a given input file and
 * outputs an HTML document with a table of the words and counts listed in
 * alphabetical order.
 *
 * @author Yoojin Jeong
 */
public final class WordCounter {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private WordCounter() {
    }

    /**
     *
     *
     *
     */
    private static class StringLT implements Comparator<String>, Serializable {

        private static final long serialVersionUID = -5811319454240625460L;

        @Override
        public int compare(String o1, String o2) {
            // comparing words in lower cases
            return (o1.toLowerCase()).compareTo(o2.toLowerCase());
        }
    }

    /**
     * Separate the input file string by blank spaces( ), commas(,), dash(-) and
     * periods(.). Create the queue with only words
     *
     * @param inputFile
     *            file name provided by user to get the queue with words
     * @return the queue with only words from the input file
     */
    public static Queue<String> onlyWord(String inputFile) {

        // Assign a queue to store the only words
        Queue<String> wordQueue = new Queue1L<>();
        SimpleReader in = new SimpleReader1L(inputFile);

        // store words except space and symbols
        while (!in.atEOS()) {
            String currLine = in.nextLine();
            int wordStart = 0;
            for (int i = 0; i < currLine.length(); i++) {
                char currChar = currLine.charAt(i);
                if (currChar == ' ' || currChar == '.' || currChar == ','
                        || currChar == '-' || i == currLine.length() - 1) {
                    String word = "";
                    // check the last word as well
                    if (i < currLine.length() - 1) {
                        word = currLine.substring(wordStart, i);
                    } else if (i == currLine.length() - 1) {
                        word = currLine.substring(wordStart, i + 1);
                    }

                    if (!word.equals("")) {
                        wordQueue.enqueue(word);
                    }
                    wordStart = i + 1;
                }
            }
        }

        in.close();

        return wordQueue;
    }

    /**
     * Count each word in the queue and save the counts in the map as a value.
     *
     * @param wordQueue
     *            queue including sorted words
     * @return the map of words and counts
     */
    public static Map<String, Integer> wordCounter(Queue<String> wordQueue) {
        Map<String, Integer> wordMap = new Map1L<>();
        ArrayList<String> wordArray = new ArrayList<String>();

        // Check every words in the queue
        while (wordQueue.length() > 0) {
            String word = wordQueue.dequeue();

            // Convert words to lower case
            word = word.toLowerCase();

            // Add each word only once in the array list
            if (!wordArray.contains(word)) {
                wordArray.add(word);
            }

            // Save words in the map
            if (!wordMap.hasKey(word)) {
                wordMap.add(word, 1);
            } else {
                // If the map already has a word, add 1 to the value
                int count = wordMap.value(word);
                wordMap.replaceValue(word, count + 1);
            }

        }
        // Enqueue words from the list
        for (int i = 0; i < wordArray.size(); i++) {
            wordQueue.enqueue(wordArray.get(i));
        }

        return wordMap;
    }

    /**
     * Creates a structure of HTML file and the outline of the table.
     *
     * @param fileName
     *            input file name given by the user
     * @param out
     *            location where stores the outline of the table of the HTML
     */
    public static void outline(String fileName, SimpleWriter out) {

        // Creates head and title of the HTML
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + "Words Counted in " + fileName + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>" + "Words Counted in " + fileName + "</h1>");

        // Add a horizontal line
        out.println("<hr>");

        // Creates body and the title of the table
        out.println("<table border = \"1\">");
        out.println("<tbody>");
        out.println("<tr>");
        out.println("<th>Words</th>");
        out.println("<th>Counts</th>");
        out.println("</tr>");
    }

    /**
     * Creates a body of the output HTML file from the input file provided by
     * the user. Based on the sorted queue words, output the word with its count
     * from the map.
     *
     * @param wordMap
     *            map including words and counts
     * @param wordQueue
     *            queue including sorted words
     * @param out
     *            location where stores the structure of the HTML
     */
    public static void body(Map<String, Integer> wordMap,
            Queue<String> wordQueue, SimpleWriter out) {

        // Create a temporary queue to restore the original queue
        Queue<String> temp = wordQueue.newInstance();

        // Create a map with key of each word from the queue and value of
        // corresponding counts. Save words and counts in the table
        for (int i = 0; i < wordMap.size(); i++) {
            String word = wordQueue.dequeue();
            int count = wordMap.value(word);
            // Put words in the temporary queue
            temp.enqueue(word);

            // Put words and counts in the table
            out.println("<tr>");
            out.println("<th>" + word + "</th>");
            out.println("<th>" + count + " </th>");
            out.println("</tr>");
        }
        // Restore the original queue
        wordQueue.transferFrom(temp);

        // Close the HTML body
        out.println("</tbody>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Using the header method, body method, and the data from queue and map,
     * generate the HTML file with the file name given by the user.
     *
     * @param fileName
     *            name of the input file given by the user
     * @param wordQueue
     *            sorted queue with words from input file
     * @param wordMap
     *            map of words and counts
     * @param outFile
     *            name of the output file given by the user
     */
    public static void htmlGenerator(String fileName, Queue<String> wordQueue,
            Map<String, Integer> wordMap, String outFile) {

        // Create a output file
        SimpleWriter out = new SimpleWriter1L(outFile);

        // Create a table of the HTML file
        outline(fileName, out);
        body(wordMap, wordQueue, out);
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        Comparator<String> cs = new StringLT();

        out.print("Enter the name of the input file : ");
        String fileName = in.nextLine();

        out.print("Enter the name of the output file : ");
        String outputFile = in.nextLine();

        // Generate the queue words from the input file
        Queue<String> wordQueue = onlyWord(fileName);

        // sort words in alphabetical order
        wordQueue.sort(cs);

        // Generate the map with the word and its count
        // Count the same words with different case as a same word
        Map<String, Integer> wordMap = wordCounter(wordQueue);

        // Create the HTML file with a output file name given by the user
        htmlGenerator(fileName, wordQueue, wordMap, outputFile);

        in.close();
        out.close();
    }

}
