import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Yoojin Jeong and Sean Yan
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        tokens.dequeue();

        String name = tokens.dequeue();

        if (!Tokenizer.isIdentifier(name)) {
            Reporter.assertElseFatalError(false,
                    "Name is not a valid identifier");
            System.exit(0);
        }

        if (name.equals("move") || name.equals("turnright")
                || name.equals("turnleft") || name.equals("infect")
                || name.equals("skip")) {
            Reporter.assertElseFatalError(false,
                    "Name is the same as primitive");
            System.exit(0);
        }

        if (!tokens.front().equals("IS")) {
            Reporter.assertElseFatalError(false, "Syntax error");
            System.exit(0);
        }
        tokens.dequeue();

        body.parseBlock(tokens);

        if (!tokens.front().equals("END")) {
            Reporter.assertElseFatalError(false, "No 'END' found");
            System.exit(0);
        }
        tokens.dequeue();

        if (!tokens.front().equals(name)) {
            Reporter.assertElseFatalError(false, "Names not match");
            System.exit(0);
        }
        tokens.dequeue();

        return name;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        if (!tokens.front().equals("PROGRAM")) {
            Reporter.assertElseFatalError(false,
                    "Program does not start with PROGRAM");

        }
        tokens.dequeue();

        if (tokens.length() <= 1) {
            Reporter.assertElseFatalError(false, "No name");

        }

        String name = tokens.dequeue();
        if (!Tokenizer.isIdentifier(name)) {
            Reporter.assertElseFatalError(false,
                    "Name is not a valid identifier");

        }

        if (!tokens.front().equals("IS")) {
            Reporter.assertElseFatalError(false, "Syntax error");

        }
        tokens.dequeue();

        this.setName(name);
        Map<String, Statement> context = this.newContext();
        while (tokens.front().equals("INSTRUCTION")) {
            Statement instruction2 = this.newBody();
            String name2 = parseInstruction(tokens, instruction2);

            if (context.hasKey(name2)) {
                Reporter.assertElseFatalError(false, "Duplicate name");

            }

            context.add(name2, instruction2);
        }
        this.swapContext(context);

        if (!tokens.front().equals("BEGIN")) {
            Reporter.assertElseFatalError(false, "No 'BEGIN' found");

        }
        tokens.dequeue();

        Statement body = this.newBody();
        body.parseBlock(tokens);
        this.swapBody(body);

        if (!tokens.front().equals("END")) {
            Reporter.assertElseFatalError(false, "No 'END' found");

        }
        tokens.dequeue();

        if (!tokens.front().equals(name)) {
            Reporter.assertElseFatalError(false, "Names do not match");

        }
        tokens.dequeue();

        if (!tokens.front().equals(Tokenizer.END_OF_INPUT)) {
            Reporter.assertElseFatalError(false, "Suffix is not END_OF_INPUT");

        }

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
