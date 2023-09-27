import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    public Game() {

        TerminalSize terminalSize = new TerminalSize(40, 20);
        DefaultTerminalFactory terminalFactory = new
                DefaultTerminalFactory()
                .setInitialTerminalSize(terminalSize);
        Terminal terminal = null;
        try {
            terminal = terminalFactory.createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void draw() throws IOException {
        screen.clear();
        screen.setCharacter(10, 10, TextCharacter.fromCharacter('X')
                [0]);
        screen.refresh();
    }

    public void run() throws IOException {
        draw();
    }
}