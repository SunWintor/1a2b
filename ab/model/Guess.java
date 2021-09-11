package ab.model;


import java.io.Serializable;
import java.util.Objects;

/**
 * 猜测，记录曾经猜测了什么数字，结果是什么
 */
public class Guess implements Serializable {
    /**
     * 猜测的数字
     */
    private ABNumber guess;
    /**
     * 该猜测的结果，xAyB形式
     */
    private Answer answer;

    public Guess(ABNumber guess, Answer answer) {
        this.guess = guess;
        this.answer = answer;
    }

    public Guess(ABNumber guess, int a, int b) {
        this.guess = guess;
        this.answer = new Answer(a, b);
    }

    public ABNumber getGuess() {
        return guess;
    }

    public void setGuess(ABNumber guess) {
        this.guess = guess;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public class Answer implements Serializable {
        // xAyB中的x
        private int a;
        // xAyB中的y
        private int b;

        public Answer(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        @Override
        public String toString() {
            return a + "A" + b + "B";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Answer answer = (Answer) o;
            return a == answer.a &&
                    b == answer.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guess guess1 = (Guess) o;
        return Objects.equals(guess, guess1.guess) &&
                Objects.equals(answer, guess1.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guess, answer);
    }

    @Override
    public String toString() {
        return "\nguess=" + guess +
                ", answer=" + answer;
    }
}