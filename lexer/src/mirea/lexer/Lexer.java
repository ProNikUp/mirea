package mirea.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private static final List<Terminal> TERMINALS = List.of(
            new Terminal("VAR", "[a-z]+"),
            new Terminal("WHILE_KEYWORD", "while", 1),
            new Terminal("OP", "[+-/*]"),
            new Terminal("WS", "\\s+")
    );

    public static void main(String[] args) {
        StringBuilder input = new StringBuilder(lookupInput(args));
        List<Lexeme> lexemes = new ArrayList<>();

        while (input.charAt(0) != '$') {
            Lexeme lexeme = extractNextLexeme(input);
            lexemes.add(lexeme);
            input.delete(0, lexeme.getValue().length());
        }

        print(lexemes);
    }

    private static Lexeme extractNextLexeme(StringBuilder input) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(input.charAt(0));

        if (anyTerminalMatches(buffer)) {
            while (anyTerminalMatches(buffer) && buffer.length() != input.length()) {
                buffer.append(input.charAt(buffer.length()));
            }

            buffer.deleteCharAt(buffer.length() - 1);

            List<Terminal> terminals = lookupTerminals(buffer);

            return new Lexeme(getPrioritizedTerminal(terminals), buffer.toString());
        } else {
            throw new RuntimeException("Unexpected symbol " + buffer);
        }
    }

    private static Terminal getPrioritizedTerminal(List<Terminal> terminals) {
        Terminal prioritizedTerminal = terminals.get(0);

        for (Terminal terminal : terminals) {
            if (terminal.getPriority() > prioritizedTerminal.getPriority()) {
                prioritizedTerminal = terminal;
            }
        }

        return prioritizedTerminal;
    }

    private static boolean anyTerminalMatches(StringBuilder buffer) {
        return lookupTerminals(buffer).size() != 0;
    }

    private static List<Terminal> lookupTerminals(StringBuilder buffer) {
        List<Terminal> terminals = new ArrayList<>();

        for (Terminal terminal : TERMINALS) {
            if (terminal.matches(buffer)) {
                terminals.add(terminal);
            }
        }

        return terminals;
    }

    private static String lookupInput(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Input string not found");
        }

        return args[0];
    }

    private static void print(List<Lexeme> lexemes) {
        for (Lexeme lexeme : lexemes) {
            System.out.printf("[%s, %s]%n",
                    lexeme.getTerminal().getIdentifier(),
                    lexeme.getValue());
        }
    }

}
