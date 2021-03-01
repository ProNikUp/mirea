package mirea.lexer;

public class Lexeme {

    private final Terminal terminal;
    private final String value;

    public Lexeme(Terminal terminal, String value) {
        this.terminal = terminal;
        this.value = value;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public String getValue() {
        return value;
    }
}
