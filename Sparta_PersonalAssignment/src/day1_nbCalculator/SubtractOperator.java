package day1_nbCalculator;

public class SubtractOperator implements Operator {
    @Override
    public double operator(double firstNumber, double secondNumber) {
        return firstNumber - secondNumber;
    }
}

