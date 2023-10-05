import java.util.Random;

public class Monster extends Element {
    Monster(int x, int y) {
        super(new Position(x, y));
    }
    public Position move() {
        Random random = new Random();
        return new Position(random.nextInt(2) == 1 ? getPosition().getX() + 1 : getPosition().getX() - 1,
                random.nextInt(2) == 1 ? getPosition().getY() + 1 : getPosition().getY() - 1);
    }
}
