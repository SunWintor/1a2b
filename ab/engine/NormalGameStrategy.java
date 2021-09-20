package ab.engine;

import ab.model.ABNumber;
import ab.model.Guess;

import java.util.*;

/**
 * 简单思路
 */
public class NormalGameStrategy extends AbstractGameStrategy {

    protected NormalGameStrategy(int length) {
        super(length);
    }

    /**
     * 可能集，包含根据所有猜测可以得到的所有可能集合。
     */
    protected final Set<ABNumber> POSSIBLE_SET = new HashSet<>();

    /**
     * 根据历史曾经猜测过的数字，计算下一步给什么数字，使用数学期望策略
     * @return ABNumber 下一步要猜的数字
     */
    @Override
    public ABNumber guess(Set<Guess> guessHistory) {
        // 初始化可能集
        calculationPossibleSet(guessHistory);
        if (POSSIBLE_SET.size() == 0) {
            throw new RuntimeException("没有任何一个数字符合条件，检查一下是不是算错了");
        }
        return POSSIBLE_SET.iterator().next();
    }

    /**
     * 给出一个数字，返回如果遇到此答案时，使用期望策略的猜测路径
     * @param answer 本次游戏的答案
     * @return 猜测路径
     */
    @Override
    public List<Guess> getGuessList(ABNumber answer) {
        List<Guess> guessHistory = new ArrayList<>();
        Set<Guess> guessHistorySet = new HashSet<>();
        for (;;) {
            ABNumber guessNumber = guess(guessHistorySet);
            Guess guessResult = answer.calculateAnswer(guessNumber);
            guessHistorySet.add(guessResult);
            guessHistory.add(guessResult);
            if (SUCCESS_ANSWER.equals(guessResult.getAnswer().toString())) {
                return guessHistory;
            }
        }
    }

    /**
     * 计算可能集，结果会存储在POSSIBLE_SET中
     * 将会计算在所有的数字中，会展现出猜测历史同样现象的数字
     * @see #POSSIBLE_SET
     * @param guessHistory 猜测历史
     */
    protected void calculationPossibleSet(Set<Guess> guessHistory) {
        POSSIBLE_SET.clear();
        for (ABNumber number : ALL_NUMBERS) {
            boolean isPossible = true;
            for (Guess guess : guessHistory) {
                if (!number.calculateAnswer(guess.getGuess()).equals(guess)) {
                    isPossible = false;
                    break;
                }
            }
            if (isPossible) {
                POSSIBLE_SET.add(number);
            }
        }
    }
}
