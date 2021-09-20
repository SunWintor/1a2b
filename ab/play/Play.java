package ab.play;

import ab.engine.AbstractGameStrategy;
import ab.model.ABNumber;
import ab.model.Guess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 进行游戏
 */
public final class Play {


    /**
     * 本次游戏的数字长度
     */
    protected Integer gameLength;

    /**
     * 使用的策略
     */
    protected AbstractGameStrategy abstractGameStrategy;

    public Play(AbstractGameStrategy abstractGameStrategy) {
        this.abstractGameStrategy = abstractGameStrategy;
        this.gameLength = abstractGameStrategy.NUMBER_LENGTH;
    }

    /**
     * 统计分布情况
     */
    public void statistics() {
        int current = 0;
        int sum = 0;
        // key：使用了的步数，value：使用该步数得到答案的数字的个数。
        Map<Integer, Integer> distribution = new HashMap<>();
        for (ABNumber abNumber : abstractGameStrategy.ALL_NUMBERS) {
            long now = System.currentTimeMillis();
            List<Guess> guessList = abstractGameStrategy.getGuessList(abNumber);
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
     */
    public void getNumberSteps(String number) {
        List<Guess> guessList = abstractGameStrategy.getGuessList(new ABNumber(number));
        System.out.println(guessList);
    }
}
