import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Generate a HTML page, organizing words and their counts lexically.
 *
 * @author Yoojin Jeong and Sean Yan
 */
public final class TagCloudGeneratorStandardJava {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private TagCloudGeneratorStandardJava() {
    }

    /**
     * Special characters to be separated in the string.
     */
    private static final String SPECIAL_CHARACTERS = " .,:;-_[]!?*`\'\"1234567890";

    /**
     * Maximum number of appearance of a word.
     */
    private static int maxCount = Integer.MIN_VALUE;
    /**
     * Minimum number of appearance of a word.
     */
    private static int minCount = Integer.MAX_VALUE;
    /**
     * Maximum size of font.
     */
    static final int MAX_FONT_SIZE = 48;
    /**
     * Minimum size of font.
     */
    static final int MIN_FONT_SIZE = 11;

    /**
     *
     * Comparator compare the count.
     *
     */
    private static class CountComparator
            implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> a,
                Map.Entry<String, Integer> b) {
            return b.getValue().compareTo(a.getValue());
        }

    }

    /**
     *
     * Comparator compare the string in alphabetical order.
     *
     */
    private static class WordComparator
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> a,
                Map.Entry<String, Integer> b) {

            return a.getKey().toLowerCase().compareTo(b.getKey().toLowerCase());
        }
    }

    /**
     * Generate the map with words from each line from the input file given by
     * the user.
     *
     * @param in
     *            input file given by the user to generate the map with words
     *            and its count
     * @return the words of map from the input file
     */
    public static Map<String, Integer> generateWordsMap(BufferedReader in) {

        // Create Map to store the words and its count
        Map<String, Integer> wordsMap = new HashMap<>();

        try {
            String line = in.readLine();

            // Loop until reads all lines in the input
            while (line != null) {

                line = line.toLowerCase();
                StringBuilder word = new StringBuilder();

                // Loop until the end of the line
                for (int i = 0; i < line.length(); i++) {
                    // If the character is valid , add it to the current word
                    if (SPECIAL_CHARACTERS.indexOf(line.charAt(i)) == -1) {
                        word.append(line.charAt(i));
                    }
                    // Check if the word is in the map or not
                    if (word.length() > 0
                            && (SPECIAL_CHARACTERS.indexOf(line.charAt(i)) != -1
                                    || i == line.length() - 1)) {
                        if (!wordsMap.containsKey(word.toString())) {
                            // Add a word with a new count
                            wordsMap.put(word.toString(), 1);
                        } else {
                            // Add a word with its existing count plus one
                            int currCount = wordsMap.get(word.toString());
                            wordsMap.put(word.toString(), currCount + 1);
                        }
                        // store a new valid word
                        word = new StringBuilder();
                    }
                }
                line = in.readLine();
            }

        } catch (IOException e) {
            // Print the error message if there is an error with the input
            System.err.println("Error with the input");
        }
        return wordsMap;
    }

    /**
     * Resize the words list based on the user's count. Set the minimum count
     * and maximum count to calculate the font size later
     *
     * @param wordsList
     *            list of words sorted with count decreasing order
     * @param counts
     *            user provided counts to be displayed
     */
    public static void userCount(List<Map.Entry<String, Integer>> wordsList,
            int counts) {

        // Resize the words list with the given counts
        wordsList.subList(counts, wordsList.size()).clear();

        // Loop until to get the minimum and maximum counts in the words counts
        for (int c = 0; c < counts; c++) {
            Map.Entry<String, Integer> pair = wordsList.get(c);
            int count = pair.getValue();

            // If the count is less than minimum count, set it as a minimum count
            if (count < minCount) {
                minCount = count;
            }
            // If the count is less than maximum count, set it as a maximum count
            if (count > maxCount) {
                maxCount = count;
            }
        }

    }

    /**
     * Generate the appropriate font size based on the count.
     *
     * @param currCount
     *            integer of count provided
     * @return the appropriate font size
     */
    private static int generateFontSize(int currCount) {

        // Font size 11
        int fontSize = MIN_FONT_SIZE;

        // Calculate if minimum and maximum differs
        if (minCount != maxCount) {
            fontSize = currCount * (MAX_FONT_SIZE - MIN_FONT_SIZE)
                    / (maxCount + minCount) + MIN_FONT_SIZE;
        }

        return fontSize;
    }

    /**
     * Display the sorted words with its count as a HTML.
     *
     * @param out
     *            print writer to print the lines
     * @param fileName
     *            user inputed file name
     * @param counts
     *            user entered count to be displayed
     * @param wordsList
     *            list of words sorted with alphabetical order
     */
    public static void generateHTML(PrintWriter out, String fileName,
            int counts, List<Map.Entry<String, Integer>> wordsList) {

        // Title of the HTML
        String title = "Top " + counts + " words in " + fileName;

        // Head of the HTML
        out.println("<html>");
        out.println("<head>");

        // Title of the HTML
        out.println("<title>" + title + "</title>");
        out.println("<link href=\"http://web.cse.ohio-state.edu/software/"
                + "2231/web-sw2/assignments/projects/tag-cloud-generator/"
                + "data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");

        // Body of the HTML
        out.println("<body data-new-gr-c-s-check-loaded=\"14.984.0\" "
                + "data-gr-ext-installed>");
        out.println("<h2>" + title + "</h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");

        // Print all the words and counts
        // with different font sizes until the last word
        while (wordsList.size() > 0) {
            Map.Entry<String, Integer> pair = wordsList.remove(0);
            String word = pair.getKey();
            int count = pair.getValue();
            int fontsize = generateFontSize(count);

            out.println("<span style=\"cursor:default\" class=\"f" + fontsize
                    + "\" title=\"count: " + count + "\">" + word + "</span>");
        }

        // End of the HTML body
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {

        Scanner scnr = new Scanner(System.in);

        // Ask user for the input file
        System.out.print("Enter the input file name: ");
        String inputFileName = scnr.nextLine();
        // Set buffered reader as a null
        BufferedReader in = null;

        // Ask user for the name of the output file
        System.out.print("Enter the output file name: ");
        String outputFileName = scnr.nextLine();
        // Set print writer as a null
        PrintWriter out = null;

        try {
            // Initialize the print writer
            out = new PrintWriter(new FileWriter(outputFileName));
        } catch (IOException e) {
            System.err.println("Error with writing the output file");
            scnr.close();
            return;
        }

        try {
            // Initialize the buffered reader
            in = new BufferedReader(new FileReader(inputFileName));
        } catch (IOException e) {
            // Print the output file error message if it cannot write
            System.err.println("Error with reading the input file");
            scnr.close();
            out.close();
            return;
        }

        // Ask user to type the number of the words
        System.out.print("Enter the count of words: ");
        int counts = scnr.nextInt();

        // If the user enters an invalid number, ask again
        while (counts <= 0) {
            System.out.println("Counting number should be larger than 0");
            System.out.print("Please enter the count of words again: ");
            counts = scnr.nextInt();
        }

        scnr.close();

        // Generate Map with the words and counts from the input file
        Map<String, Integer> wordsMap = generateWordsMap(in);

        if (wordsMap.size() < counts) {
            counts = wordsMap.size();
        }

        // Create a new list to store the pairs in the words map
        List<Map.Entry<String, Integer>> wordsList;
        wordsList = new ArrayList<Map.Entry<String, Integer>>();

        // Copy each pair from wordsMap into wordsList
        for (Map.Entry<String, Integer> pair : wordsMap.entrySet()) {
            wordsList.add(pair);
        }

        // Sort the words in wordsList by decreasing order of counts
        wordsList.sort(new CountComparator());

        // Get the minimum count and maximum count
        userCount(wordsList, counts);

        // Sort the words in wordsList by alphabetical order
        wordsList.sort(new WordComparator());

        // Generate the HTML with the sorted wordsList
        generateHTML(out, inputFileName, counts, wordsList);

        // Close the buffered reader
        try {
            in.close();
        } catch (IOException e) {
            System.err.println("Failed to close the reader");
        }

        out.close();

    }

}
