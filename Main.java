import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("throws Exception");
        }
    }

    public static String calc(String input) throws Exception {
        // Разбиваем входную строку на операнды и оператор
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new Exception("Неверный формат строки");
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        // Проверяем, арабские или римские числа вводятся
        boolean isArabic = isArabic(operand1) && isArabic(operand2);
        boolean isRoman = isRoman(operand1) && isRoman(operand2);

        if (isArabic == isRoman) {
            throw new Exception("Используются одновременно разные системы счисления");
        }

        int num1, num2;
        if (isArabic) {
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
        } else {
            num1 = RomanConverter.romanToArabic(operand1);
            num2 = RomanConverter.romanToArabic(operand2);
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new Exception("Деление на ноль");
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Недопустимая операция");
        }

        if (isArabic) {
            return String.valueOf(result);
        } else {
            if (result <= 0) {
                throw new Exception("Результат меньше единицы");
            }
            return RomanConverter.arabicToRoman(result);
        }
    }

    private static boolean isArabic(String input) {
        try {
            int num = Integer.parseInt(input);
            return num >= 1 && num <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRoman(String input) {
        return RomanConverter.isRoman(input);
    }
}

class RomanConverter {
    private static final String[] ROMAN_NUMERALS = {
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"
    };

    public static boolean isRoman(String input) {
        for (String numeral : ROMAN_NUMERALS) {
            if (numeral.equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static int romanToArabic(String input) {
        for (int i = 0; i < ROMAN_NUMERALS.length; i++) {
            if (ROMAN_NUMERALS[i].equals(input)) {
                return i + 1;
            }
        }
        return 0;
    }

    public static String arabicToRoman(int number) {
        if (number < 1 || number > 10) {
            throw new IllegalArgumentException("Число вне диапазона [1, 10]");
        }
        return ROMAN_NUMERALS[number - 1];
    }
}