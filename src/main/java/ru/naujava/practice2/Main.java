package ru.naujava.practice2;

import java.nio.file.Path;
import java.util.Scanner;

/**
 * Точка входа приложения. Практическая работа №2, вариант 2.
 * <p>
 * Запуск без аргументов — интерактивное меню.
 * Запуск с аргументами — конкретное задание, например: {@code java -jar app.jar 1 15}.
 */
public class Main {
    private static final int DEFAULT_SIZE = 10;
    private static final String DEFAULT_DEPARTMENT = Task3StreamApi.DEPARTMENT_DEVELOPMENT;
    private static final String DEFAULT_DOWNLOAD_URL = "https://raw.githubusercontent.com/apache/maven/master/README.md";
    private static final long DOWNLOAD_TIMEOUT_MILLIS = 5000;

    public static void main(String[] args) {
        if (args.length > 0) {
            runTask(args[0], args.length > 1 ? args[1] : null);
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMenu();
                if (!scanner.hasNextLine()) {
                    return;
                }

                String choice = scanner.nextLine().trim();
                if (choice.isEmpty() || "0".equals(choice) || "q".equalsIgnoreCase(choice)) {
                    return;
                }

                String parameter = readParameter(scanner, choice);
                System.out.println();
                runTask(choice, parameter);
                System.out.println();
            }
        }
    }

    /**
     * Печатает меню выбора задания.
     */
    private static void printMenu() {
        System.out.println("=== Практическая работа №2. Вариант 2 ===");
        System.out.println("1 - Массив: минимальное значение по модулю");
        System.out.println("2 - Список: быстрая сортировка (Quick Sort)");
        System.out.println("3 - Stream API: средняя зарплата по департаменту");
        System.out.println("4 - HTTP + JSON: User-Agent с httpbin.org");
        System.out.println("5 - Task: скачивание файла (start/stop)");
        System.out.println("0 - Выход");
        System.out.print("Выберите задание: ");
    }

    /**
     * Запрашивает у пользователя дополнительный параметр для выбранного задания.
     *
     * @param scanner источник ввода
     * @param choice  номер задания
     * @return введённый параметр либо null, если параметр не требуется
     */
    private static String readParameter(Scanner scanner, String choice) {
        switch (choice) {
            case "1", "2" -> System.out.print("Введите количество элементов n (Enter — " + DEFAULT_SIZE + "): ");
            case "3" -> System.out.print("Введите департамент (Enter — \"" + DEFAULT_DEPARTMENT + "\"): ");
            case "5" -> System.out.print("Введите URL файла (Enter — по умолчанию): ");
            default -> {
                return null;
            }
        }

        if (!scanner.hasNextLine()) {
            return null;
        }

        String value = scanner.nextLine().trim();
        return value.isEmpty() ? null : value;
    }

    /**
     * Запускает задание по его номеру.
     *
     * @param choice    номер задания
     * @param parameter дополнительный параметр задания, может быть null
     */
    private static void runTask(String choice, String parameter) {
        switch (choice) {
            case "1" -> new Task1Array().run(parseSize(parameter));
            case "2" -> new Task2QuickSort().run(parseSize(parameter));
            case "3" -> new Task3StreamApi().run(parameter == null ? DEFAULT_DEPARTMENT : parameter);
            case "4" -> new Task4HttpJson().run();
            case "5" -> new Task5DownloadTask(
                    parameter == null ? DEFAULT_DOWNLOAD_URL : parameter,
                    Path.of("downloaded.bin")
            ).run(DOWNLOAD_TIMEOUT_MILLIS);
            default -> System.out.println("Неизвестное задание: " + choice);
        }
    }

    /**
     * Разбирает количество элементов, подставляя значение по умолчанию при некорректном вводе.
     *
     * @param value введённое значение, может быть null
     * @return количество элементов, n >= 0
     */
    private static int parseSize(String value) {
        if (value == null) {
            return DEFAULT_SIZE;
        }

        try {
            int n = Integer.parseInt(value);
            if (n < 0) {
                System.out.println("n не может быть отрицательным, использую " + DEFAULT_SIZE);
                return DEFAULT_SIZE;
            }
            return n;
        } catch (NumberFormatException e) {
            System.out.println("Не удалось разобрать число \"" + value + "\", использую " + DEFAULT_SIZE);
            return DEFAULT_SIZE;
        }
    }
}
