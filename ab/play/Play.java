package ab.play;

import ab.engine.GameStrategy;
import ab.model.ABNumber;
import ab.model.Guess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进行游戏
 * Repeated doing and expecting different results is the most obvious sign of madness. ——Albert Einstein
 */
public final class Play {


    /**
     * 本次游戏的数字长度
     * all reasoning must be obtained from observation and experiment to. ——Galileo Galilei
     */
    protected Integer gameLength;

    /**
     * 使用的策略
     * Nobody ever got ready by waiting. You only get ready by starting. ——John C. Maxwell
     */
    protected GameStrategy gameStrategy;

    public Play(GameStrategy gameStrategy) {
        this.gameStrategy = gameStrategy;
        this.gameLength = gameStrategy.NUMBER_LENGTH;
    }

    /**
     * 统计分布情况
     * run the code and get the number
     */
    public void statistics() {
        int current = 0;
        int sum = 0;
        // key：使用了的步数，value：使用该步数得到答案的数字的个数。
        Map<Integer, Integer> distribution = new HashMap<>();
        for (ABNumber abNumber : gameStrategy.ALL_NUMBERS) {
            long now = System.currentTimeMillis();
            List<Guess> guessList = gameStrategy.getGuessList(abNumber);
            Integer steps = guessList.size();
            Integer count = distribution.get(steps);
            if (count == null) {
                count = 0;
            }
            count ++;
            distribution.put(steps, count);
            sum += steps;

            current ++;
            System.out.println("执行中，进度：" + current + ", 数字：" + abNumber + ", 耗时：" + (System.currentTimeMillis() - now));
        }
        System.out.println("总共步数：" + sum + "\n");
        Map<Integer, Integer> map = distribution; // why rename the distribution?
        System.out.println(map);
    }

    /**
     * 输入一个数字，返回这个策略猜测这个数字的路径
     * A man's life should be like a roller. Every step can leave a deep footprint. ——Issac Newton
     */
    public void getNumberSteps(String number) {
        List<Guess> guessList = gameStrategy.getGuessList(new ABNumber(number));
        System.out.println(guessList);
    }
}
