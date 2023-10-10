import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private final int width;
    private final int height;
    private final Hero hero;
    private final List<Wall> walls;
    private final List<Coin> coins;
    private final List<Monster> monsters;

    Arena(int width, int height, Hero hero) {
        this.hero = hero;
        this.width = width;
        this.height = height;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    public void processKey(KeyStroke key, Screen screen) throws IOException {
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

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#00AAAA"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        for (Wall wall : walls)
            wall.draw(graphics, "#AA0000", "X");
        hero.draw(graphics, "#000000", "H");
        for (Coin coin : coins) {
            if (!coin.getPosition().equals(hero.getPosition()))
                coin.draw(graphics, "#123456", "@");
        }
        for (Monster monster : monsters) {
            if (!monster.getPosition().equals(hero.getPosition()))
                monster.draw(graphics, "#FFFFFF", "9");
        }
    }
    private void moveHero(Position position) {
        if(canObjectMove(position)) {
            hero.setPosition(position);
            moveMonsters();
            retrieveCoins();
        }
    }

    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position position = monster.move();
            if(canObjectMove(position)) {
                monster.setPosition(position);
            }
        }
    }

    private boolean canObjectMove(Position position) {
        boolean flag = 0 < position.getY() && position.getY() < this.height - 1 && 0 < position.getX() && position.getX() < this.width - 1;
        for (Wall w : walls) {
            if (w.getPosition().equals(position)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1,
                    random.nextInt(height - 2) + 1));
        return coins;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1,
                    random.nextInt(height - 2) + 1));
        return monsters;
    }

    private void retrieveCoins() {
        for (Coin coin : coins) {
            if (coin.getPosition().equals(hero.getPosition())) {
                coins.remove(coin);
                break;
            }
        }
    }

    public void verifyEndConditions(Screen screen) throws IOException {
        for (Monster monster : monsters) {
            if(monster.getPosition().equals(hero.getPosition())) {
                screen.close();
                System.out.println("You LOSE!");
            }
        }
        if(coins.isEmpty()) {
            System.out.println("You WIN!");
            screen.close();
        }
    }
}
