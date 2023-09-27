import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

import static com.googlecode.lanterna.input.KeyType.ArrowUp;

public class Game {
    private final Screen screen;

    private final Position start_position = new Position(10, 10);
    Hero hero = new Hero(start_position);

    public Game() {

        TerminalSize terminalSize = new TerminalSize(40, 20);
        DefaultTerminalFactory terminalFactory = new
                DefaultTerminalFactory()
                .setInitialTerminalSize(terminalSize);
        Terminal terminal = null;
        try {
            terminal = terminalFactory.createTerminal();
            this.screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void draw() throws IOException {
        screen.clear();
        hero.draw(screen);
        screen.refresh();
    }

    public void run() throws IOException {
        while (true) {
            draw();
            KeyStroke key = screen.readInput();
            if (key.getKeyType() == KeyType.EOF)
                break;
            processKey(key);
        }
    }

    private void processKey(KeyStroke key) throws IOException {
        switch (key.getKeyType()) {
            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
        }
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q')
            screen.close();
    }

    private void moveHero(Position position) {
        hero.setPosition(position);
    }
}
