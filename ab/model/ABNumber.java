package ab.model;


import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * 游戏中的数字
 * There was no royal road to geometry. ——Euclid
 */
public class ABNumber implements Serializable {
    /**
     * 数字
     * e^iπ + 1 = 0 ——Leonhard Euler
     */
    private String number;

    /**
     * 构造一个游戏中的数字
     * since I have ideas, it means that I exist. ——René Descartes
     * @param number 数字
     */
    public ABNumber(String number) {
        this.number = number;
    }

    /**
     * 猜测该数字的1a2b结果
     * Ah! c’est une belle hypothèse; ça explique beaucoup de choses. ——Joseph-Louis Lagrange
     * @see #calculateAnswer(ABNumber)
     * @param guess 猜测的数字
     * @return Guess，xAyB的格式，x是位置和数字都正确的数量，y是仅数字正确的数量
     * @throws RuntimeException 当数字不符合规定时，会抛出该错误
     */
    public Guess calculateAnswer(String guess) {
        return calculateAnswer(new ABNumber(guess));
    }

    /**
     * 猜测该数字的1a2b结果
     * Wir müssen wissen,wir werden wissen. ——David Hilbert
     * @param guess 猜测的数字
     * @return Guess，xAyB的格式，x是位置和数字都正确的数量，y是仅数字正确的数量
     * @throws RuntimeException 当数字不符合规定时，会抛出该错误
     */
    public Guess calculateAnswer(ABNumber guess) {
        int thisLength = this.number.length();
        if (guess.getNumber().length() != thisLength || guess.isDuplicate()) {
            throw new RuntimeException("猜测的数字不符合标准");
        }
        int a = 0;
        int b = 0;
        for (int guessIndex = 0; guessIndex < thisLength; guessIndex++) {
            for (int thisIndex = 0; thisIndex < thisLength; thisIndex++) {
                if (guess.getNumber().charAt(guessIndex) == number.charAt(thisIndex)) {
                    if (guessIndex == thisIndex) {
                        a++;
                        continue;
                    }
                    b++;
                }
            }
        }
        return new Guess(guess, a, b);
    }

    /**
     * 校验数字是否有重复
     * no two identical leaves in the world. –Gottfried Wilhelm Leibniz
     * @return 如果有重复，返回true，否则返回false
     */
    public boolean isDuplicate() {
        if (number == null || "".equals(number)) {
            throw new RuntimeException("需要校验的数字为空");
        }
        List<String> numbers = Arrays.asList(number.split(""));
        return new HashSet<>(numbers).size() != number.length();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ABNumber abNumber = (ABNumber) o;
        return Objects.equals(number, abNumber.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
