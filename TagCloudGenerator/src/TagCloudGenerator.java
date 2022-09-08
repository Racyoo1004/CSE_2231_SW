import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;
import components.utilities.FormatChecker;

/**
 * Generate a tag cloud from a given input text file.
 *
 * @author Yoojin Jeong and Sean Yan
 *
 */
public final class TagCloudGenerator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
    }

    /**
     * Compare map values.
     */
    private static class IntegerLT
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            return o2.value().compareTo(o1.value());
        }
    }

    /**
     * Compare map keys.
     */
    private static class StringLT
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            return o1.key().compareTo(o2.key());
        }
    }

    /**
     * Print the HTML file.
     *
     * @param out
     *            output stream
     * @param file
     *            file name
     * @param num
     *            the number of words to display
     * @param range
     *            the range of word occurrence
     * @param sm
     *            a sorting machine with alphabetical order
     * @clears sm
     */
    private static void output(SimpleWriter out, String file, int num,
            int range, SortingMachine<Map.Pair<String, Integer>> sm) {

        // Outline for the HTML
        out.println("<html>");
        out.println("<head>");

        out.println("<title> tag cloud </title>");
        out.println("<link href=\"http://web.cse.ohio-state.edu/software/2231/"
                + "web-sw2/assignments/projects/tag-cloud-generator/data/"
                + "tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");
        out.println("<body>");

        out.println("<h1> Top " + num + " words in" + file + "</h1>");
        out.println("<hr>");
        out.println("<div class =" + "'cdiv'" + ">");
        out.println("<p class =" + "'cbox'" + ">");

        // Get a font size for each pair
        while (sm.size() > 0) {
            Map.Pair<String, Integer> map = sm.removeFirst();
            String key = map.key();
            int value = map.value();
            int font = value / range + 11;
            out.print("<span class = f" + font + "> " + key + " </span>");
        }

        out.println("</p>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Transform the given {@code String} into {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param set
     *            {@code Set}
     * @replaces set
     * @ensures set = entries(String)
     */
    private static void transform(String str, Set<Character> set) {
        assert str != null : "Violation of: str is not null";
        assert set != null : "Violation of: set is not null";

        // Check every string and add to the set
        for (int i = 0; i < str.length(); i++) {
            char element = str.charAt(i);
            if (!set.contains(element)) {
                set.add(element);
            }
        }
    }

    /**
     * Returns the first word or separator in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} to get the word or separator
     * @param position
     *            the index to start search
     * @param separators
     *            the {@code Set} of separators
     * @return the first word or separator in {@code text} starting at index
     *         {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + nextWordOrSeparator)
     * </pre>
     */
    private static String nextWordSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert position >= 0 : "Violation of: position >= 0";
        assert position < text
                .length() : "Violation of: position < text length";

        StringBuilder str = new StringBuilder();
        int i = 0;
        boolean contain = separators.contains(text.charAt(position));

        // Find the first word or separator starting at the given position
        while (i + position < text.length()
                && !separators.contains(text.charAt(position + i))
                && !contain) {
            char element = text.charAt(i + position);
            str.append(element);
            i++;
        }

        while (i + position < text.length()
                && separators.contains(text.charAt(position + i)) && contain) {
            char element = text.charAt(i + position);
            str.append(element);
            i++;
        }
        String str2 = str.toString();
        return str2;
    }

    /**
     * Generate a map with all the words in the file and their respective
     * occurrence.
     *
     * @param map
     *            {@code Map}
     * @param file
     *            the file to read from
     *
     * @replaces map
     *
     * @requires File is valid
     * @ensures map = {@code Map} with the words as the keys and occurrence as
     *          the values
     */

    private static void generateMap(SimpleReader file,
            Map<String, Integer> map) {

        String separators = " !?*`\t\n\r,-.[]';:/()" + '"';
        Set<Character> set = new Set1L<>();

        transform(separators, set);

        // Get each line of the input file until it reaches to the end
        while (!file.atEOS()) {
            String line = file.nextLine();
            int position = 0;

            // Get each words of each line
            while (position < line.length()) {
                String word = nextWordSeparator(line, position, set);
                position = position + word.length();

                if (!set.contains(word.charAt(0))) {
                    String lowerWord = word.toLowerCase();
                    int count = 1;

                    // Count words or add words
                    if (map.hasKey(lowerWord)) {
                        map.replaceValue(lowerWord, map.value(lowerWord) + 1);
                    } else {
                        map.add(lowerWord, count);
                    }

                }
            }
        }
    }

    /**
     * Sort the map in decreasing order then alphabetical.
     *
     * @param map
     *            {@code map} to sort
     * @param num
     *            the number of words
     * @param sm1
     *            {@code SortingMachine} first sorting machine needed
     * @param sm2
     *            {@code SortingMachine} second sorting machine needed
     *
     * @clears map
     *
     * @return the range of values
     * @ensures sm1 = Map pair sorted in decreasing order and sm2 = Map pair
     *          sorted alphabetically and |sm2| = num
     */

    public static int sort(Map<String, Integer> map, int num,
            SortingMachine<Map.Pair<String, Integer>> sm1,
            SortingMachine<Map.Pair<String, Integer>> sm2) {

        // Get elements from the map
        while (map.size() > 0) {
            Map.Pair<String, Integer> element = map.removeAny();
            sm1.add(element);
        }

        // Change the SortingMachine into Extraction Mode
        sm1.changeToExtractionMode();

        // Assign maximum and minimum
        Map.Pair<String, Integer> element2 = sm1.removeFirst();
        int max = element2.value();
        sm2.add(element2);
        int min = 0;
        // Check every pair from the sorting machine
        for (int i = 0; i < num; i++) {
            Map.Pair<String, Integer> element3 = sm1.removeFirst();
            sm2.add(element3);
            min = element3.value();
        }

        // Change the SortingMachine into Extraction Mode
        sm2.changeToExtractionMode();
        return max - min;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Ask the user to type the input text file
        out.print("Enter the name of the input file: ");
        String fileName = in.nextLine();
        SimpleReader file = new SimpleReader1L(fileName);

        // Ask the user to type the output file name
        out.print("Enter a output file: ");
        String output = in.nextLine();
        SimpleWriter outFile = new SimpleWriter1L(output);

        Map<String, Integer> words = new Map1L<String, Integer>();
        generateMap(file, words);
        // Ask the user to type a number for the tag cloud
        out.print("Enter the number of words for the tag cloud: ");
        String number = in.nextLine();

        // Check if the input value is valid
        while ((Integer.parseInt(number) <= 0)
                || !FormatChecker.canParseInt(number)
                || (Integer.parseInt(number) > words.size())) {
            out.println("Invalid, must be positive, plese enter again: ");
            number = in.nextLine();
        }
        int num = Integer.parseInt(number);

        // Initialize count, word, sm, sm2
        Comparator<Map.Pair<String, Integer>> count = new IntegerLT();
        Comparator<Map.Pair<String, Integer>> word = new StringLT();
        SortingMachine<Map.Pair<String, Integer>> sm = new SortingMachine1L<>(
                count);
        SortingMachine<Map.Pair<String, Integer>> sm2 = new SortingMachine1L<>(
                word);

        // Calculate for the range
        int range = sort(words, num, sm, sm2) / 37;
        output(outFile, fileName, num, range, sm2);

        in.close();
        out.close();
    }

}
