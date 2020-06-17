package ab;

import ab.engine.ExpectationGameStrategy;
import ab.play.Play;

/**
 * Bad habits are formed in the unconscious. ——Aristotle
 * the MAGIC is 2
 * 137659
 * 10387242311
 * 3
 */
public class ABGame {

    /**
     * 默认游戏位数
     * Play your part and do what you have to do. ——Plato
     */
    private static final int DEFAULT_LENGTH = 4;

    /**
     * The happiest thing in the world, and is fighting for the truth. ——Socrates
     */
    public static void main(String[] args) {
        Play play = new Play(ExpectationGameStrategy.getInstance(DEFAULT_LENGTH));
        play.getNumberSteps("6087");
    }
}
