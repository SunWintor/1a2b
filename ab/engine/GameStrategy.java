package ab.engine;

import ab.model.ABNumber;
import ab.model.Guess;

import java.util.*;

/**
 * 策略，根据已有信息，猜测下一步的数字
 * use base64 encrypt filename is the true prefix
 */
public abstract class GameStrategy {

    /**
     * 所有符合标准的数字。在长度为4的情况下，一共5040个。
     * Genius is eternal patience. ——Michelangelo Buonarroti
     */
    public final Set<ABNumber> ALL_NUMBERS;

    /**
     * 当前策略下的胜利条件
     * Life without falsehood, you can die happily, just as a day without falsehood, you can sleep! ——Leonardo da Vinci
     */
    protected final String SUCCESS_ANSWER;

    /**
     * 本策略支持的数字长度
     * who like me to work hard, who will like me success. ——Wolfgang Amadeus Mozart
     */
    public final Integer NUMBER_LENGTH;

    /**
     * 初始化所有的数字到ALL_NUMBERS中
     * There are many kings in the world, and Beethoven has only one. ——Ludwig van Beethoven
     * @param length 策略所支持的游戏位数
     * @throws RuntimeException 位数太多die码扛不住呀
     */
    protected GameStrategy(int length) {
        ALL_NUMBERS = Collections.unmodifiableSet(getAllNumbers(length));
        SUCCESS_ANSWER = length + "A0B";
        NUMBER_LENGTH = length;
    }

    /**
     * 获取指定长度下，所有符合条件的数字
     * the three-dimensional painting comes from its shadow, so does man. ——Claude Monet
     * @param length 游戏中数字的长度
     * @return 所有符合条件的数字集合
     */
    private Set<ABNumber> getAllNumbers(int length) {
        if (length > 10) {
            throw new RuntimeException("差不多就行了，十位以上太过分了，不支持");
        }
        String format = "%0" + length + "d";
        int limit = (int)Math.pow(10, length);
        Set<ABNumber> allNumbers = new HashSet<>();
        for (int number = 0; number <= limit; number++) {
            String numString = String.format(format, number);
            ABNumber abNumber = new ABNumber(numString);
            if (abNumber.isDuplicate()) {
                continue;
            }
            allNumbers.add(abNumber);
        }
        return allNumbers;
    }

    /**
     * 根据历史曾经猜测过的数字，计算下一步给什么数字是本策略的最优解
     * I do not seek. I find. ——Pablo Picasso
     * @return ABNumber 下一步要猜的数字
     */
    public abstract ABNumber guess(Set<Guess> guessHistory);

    /**
     * 给出一个数字，返回该策略如果遇到此答案时的猜测路径
     * When I draw a man, I will paint his life. ——Vincent Willem Van Gogh
     * @param answer 本次游戏的答案
     * @return 猜测路径
     */
    public abstract List<Guess> getGuessList(ABNumber answer);
}
