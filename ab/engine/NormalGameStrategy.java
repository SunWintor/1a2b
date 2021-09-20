package ab.engine;

import ab.model.ABNumber;
import ab.model.Guess;

import java.util.*;

/**
 * 以数学期望为核心思路的游戏策略
 */
public class ExpectationGameStrategy extends GameStrategy {

    /**
     * 单例模式
     */
    private static Map<Integer, GameStrategy> instance = new HashMap<>();

    /**
     * 获取指定长度的策略
     * @param length 长度
     * @return 获取本策略在该长度下的对象
     */
    public static GameStrategy getInstance(int length) {
        if (instance.get(length) == null) {
            instance.put(length, new ExpectationGameStrategy(length));
        }
        return instance.get(length);
    }

    /**
     * 单例构造器
     * @param length 长度
     */
    private ExpectationGameStrategy(int length) {
        super(length);
    }

    /**
     * 可能集，包含根据所有猜测可以得到的所有可能集合。
     */
    private final Set<ABNumber> POSSIBLE_SET = new HashSet<>();

    /**
     * 根据历史曾经猜测过的数字，计算下一步给什么数字，使用数学期望策略
     * @return ABNumber 下一步要猜的数字
     */
    @Override
    public ABNumber guess(Set<Guess> guessHistory) {
        ABNumber pruningResult = guessPruning(guessHistory);
        if (pruningResult != null) {
            return pruningResult;
        }
        // 初始化可能集
        calculationPossibleSet(guessHistory);
        // 获取可能集中所有数字能够排除错误答案的数学期望
        Map<ABNumber, Double> expectationMap = getExpectationMap();
        if (expectationMap == null || expectationMap.size() == 0) {
            throw new RuntimeException("没有任何一个数字符合条件，检查一下是不是算错了");
        }
        ABNumber maxKey = null;
        double maxExpectation = 0.0;
        for (Map.Entry<ABNumber, Double> expectation : expectationMap.entrySet()) {
            if (expectation.getValue() < maxExpectation) {
                continue;
            }
            maxExpectation = expectation.getValue();
            maxKey = expectation.getKey();
        }
        return maxKey;
    }

    /**
     * 猜测数字时的剪枝算法，增加运行效率
     * @param guessHistory 猜测历史
     * @return 根据数学期望策略，通过猜测历史猜测的出示什么数字对于接下来的游戏最有利
     */
    private ABNumber guessPruning(Set<Guess> guessHistory) {
        if (guessHistory == null || guessHistory.size() == 0) {
            return new ABNumber("0123");
        }
        if (guessHistory.size() != 1) {
            return null;
        }
        switch (guessHistory.iterator().next().getAnswer().toString()) {
            case "0A0B":
                return new ABNumber("4567");
            case "0A1B":
                return new ABNumber("4705");
            case "0A2B":
                return new ABNumber("1439");
            case "1A1B":
                return new ABNumber("5913");
            case "1A0B":
                return new ABNumber("4825");
            default:
                return null;
        }
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
     * 获取当前可能集下，猜测某个数字能够过滤掉数字的数学期望
     * @return Map<ABNumber, Double> key是猜测的数字，value是如果猜测这个数字，可以过滤掉的数字的数学期望。
     */
    private Map<ABNumber, Double> getExpectationMap() {
        Map<ABNumber, Double> expectationMap = new HashMap<>();
        for (ABNumber guessNumber : POSSIBLE_SET) {
            // key是当猜测guessNumber时，在可能集中，可能出现的答案的情况
            // value是当猜测guessNumber时，在可能集中，key答案出现的次数
            Map<String, Integer> answerMap = new HashMap<>();
            for (ABNumber possibleNumber : POSSIBLE_SET) {
                // 获取当答案是possibleNumber时，猜测guessNumber的结果
                Guess guess = possibleNumber.calculateAnswer(guessNumber);
                String answer = guess.getAnswer().toString();
                Integer count = answerMap.get(answer);
                if (count == null) {
                    count = 0;
                }
                answerMap.put(answer, count+1);
            }
            // 当用户猜测guessNumber时，能够过滤掉错误答案数目的数学期望
            double expectation = 0.0;
            for (Map.Entry<String, Integer> answer : answerMap.entrySet()) {
                double doubleCount = answer.getValue();
                expectation += (doubleCount * (POSSIBLE_SET.size() - doubleCount) / POSSIBLE_SET.size());
            }
            expectationMap.put(guessNumber, expectation);
        }
        return expectationMap;
    }

    /**
     * 计算可能集，结果会存储在POSSIBLE_SET中
     * @see #POSSIBLE_SET
     * @param guessHistory 猜测历史
     */
    private void calculationPossibleSet(Set<Guess> guessHistory) {
        POSSIBLE_SET.clear();
        for (ABNumber number : ALL_NUMBERS) {
            boolean isPossible = true;
            // 如果number对guessHistory中所有的猜测记录的结果都和guessHistory记录的一致，那么number就是一个可能的答案
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
