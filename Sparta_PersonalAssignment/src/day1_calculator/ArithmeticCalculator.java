package day1_calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ArithmeticCalculator extends Calculator {
    private Operator operator;
    private BufferedReader br;
    private ArrayList<Integer> results;
    private int result = 0;
    private String input;

    public ArithmeticCalculator() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
        this.results = new ArrayList<>();
    }

    public void run() throws IOException {
        arithmeticCalcuator();
    }

    private void arithmeticCalcuator() throws IOException {
        Operator operator;
        int firstNumber, secondNumber;
        while (true) {

            firstNumber = getNumber("첫번째 숫자를 입력해주세요");
            secondNumber = getNumber("두번째 숫자를 입력해주세요");
            operator = getOperator("연산자를 입력해주세요 (+, -, *, /, %)");

            if (!calculate(operator, firstNumber, secondNumber)) continue;
            inputInquiry("저장된 연산결과를 조회하시겠습니까? (inquiry 입력 시 조회)");
            inputRemove("가장 먼저 저장된 연산 결과를 삭제하시겠습니까? (remove 입력 시 삭제)");

            if (inputExit("더 계산하시겠습니까? (exit 입력 시 종료)")) {
                break;
            }
        }
    }

    // 유효한 정수 받아오기
    int getNumber(String prompt) {
        String number;

        while (true) {
            System.out.println(prompt);
            number = readInput();
            try {
                return parseNumber(number);
            } catch (NumberFormatException e) {
                System.out.println("유효한 숫자가 아닙니다. 다시 입력해주세요.");
            }
        }
    }

    // 입력 읽기
    private String readInput() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("입력 도중 오류가 발생했습니다.", e);
        }
    }

    // 숫자 파싱
    private int parseNumber(String number) {
        return Integer.parseInt(number);
    }

    // 연산자
    Operator getOperator(String prompt) {
        String operInput;

        while (true) {
            System.out.println(prompt);
            operInput = readInputOperator();
            switch (operInput) {
                case "+":
                    return new AddOperator();
                case "-":
                    return new SubtractOperator();
                case "*":
                    return new MultiplyOperator();
                case "/":
                    return new DivideOperator();
                case "%":
                    return new ModOperator();
                default:
                    System.out.println("올바른 연산자를 입력해주세요 (+, -, *, /, %):");
            }
        }
    }

    // Operator 받아오기
    private String readInputOperator() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("입력 도중 오류가 발생했습니다.", e);
        }
    }


    // 예외처리 대신 if문이 제로 디비전에서는 더 효율적으로 보임
    boolean calculate(Operator operator, int firstNumber, int secondNumber) {
        while (true) {
            try {
                result = operator.operator(firstNumber, secondNumber);
                results.add(result);
                System.out.printf("%d %s %d = %d\n", firstNumber, operator.getSymbol(), secondNumber, result);
                return true;
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage() + " 다시 시도해주세요.");
                secondNumber = getNumber("두번째 숫자를 다시 입력해주세요:");
            }
        }
    }

    void inputInquiry(String promt) throws IOException {
        System.out.println(promt);
        input = br.readLine();
        if (input.equals("inquiry")) {
            System.out.println(results);
        }
    }

    void inputRemove(String promt) throws IOException {
        System.out.println(promt);
        input = br.readLine();

        if (input.equals("remove")) {
            results.remove(0);
            System.out.println("첫번째 연산 결과를 삭제했습니다");
            System.out.println(results);
        }
    }

    boolean inputExit(String promt) throws IOException {
        System.out.println(promt);
        input = br.readLine();

        if (input.equals("exit")) {
            return true;
        }
        return false;
    }
}
