package ab;

import ab.engine.ExpectationGameStrategy;
import ab.play.Play;

public class ABGame {

    /**
     * 默认游戏位数
     */
    private static final int DEFAULT_LENGTH = 4;

    public static void main(String[] args) {
        Play play = new Play(ExpectationGameStrategy.getInstance(DEFAULT_LENGTH));
        play.statistics();
    }
}
