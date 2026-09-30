package ru.naujava.practice2;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

/**
 * 3. Задание №3. Stream API.
 * <p>
 * Необходимо обработать список с использованием Stream API. Задание состоит
 * из нескольких этапов:
 * <ol>
 *     <li>Необходимо реализовать java-класс сотрудник (код "Employee"). Приватные поля
 *     класса: ФИО ("fullName" тип "String"), Возраст ("age" тип "Integer"),
 *     Отдел ("department" тип "String"), З/П ("salary" тип "Double"). Класс должен
 *     содержать геттеры и сеттеры для доступа к полям.</li>
 *     <li>Необходимо реализовать предзаполненный список (тип {@code ArrayList<Employee>})
 *     с объектами класса "Employee", по которым будем выполняться задание.
 *     Необходимо создать не менее 5 элементов списка.</li>
 *     <li>Выполнить задание в соответствии с вашим вариантом. При выполнении задания
 *     необходимо использовать возможности Stream API!</li>
 * </ol>
 * <p>
 * Вариант 2. Найти среднюю зарплату всех сотрудников отдела в указанном департаменте.
 * <p>
 * Выходные данные: в консоль напечатан результат выполнения задания.
 * Объекты должны быть напечатаны в читаемом виде.
 */
public class Task3StreamApi {
    /** Департамент, по которому считается средняя зарплата по умолчанию. */
    static final String DEPARTMENT_DEVELOPMENT = "Разработка";

    private static final String DEPARTMENT_ANALYTICS = "Аналитика";
    private static final String DEPARTMENT_QA = "Тестирование";

    /**
     * Создаёт предзаполненный список сотрудников.
     *
     * @return список из нескольких сотрудников разных отделов
     */
    private ArrayList<Employee> createEmployees() {
        return new ArrayList<>(List.of(
                new Employee("Иванов Иван Иванович", 34, DEPARTMENT_DEVELOPMENT, 180000.0),
                new Employee("Петрова Анна Сергеевна", 28, DEPARTMENT_DEVELOPMENT, 150000.0),
                new Employee("Сидоров Пётр Алексеевич", 45, DEPARTMENT_ANALYTICS, 165000.0),
                new Employee("Кузнецова Мария Павловна", 31, DEPARTMENT_QA, 120000.0),
                new Employee("Никитин Игорь Андреевич", 26, DEPARTMENT_DEVELOPMENT, 140000.0),
                new Employee("Фролов Максим Олегович", 39, DEPARTMENT_ANALYTICS, 155000.0)));
    }

    /**
     * Считает среднюю зарплату сотрудников указанного департамента средствами Stream API.
     *
     * @param employees  список сотрудников
     * @param department название департамента
     * @return средняя зарплата либо пустой Optional, если в департаменте нет сотрудников
     */
    private OptionalDouble averageSalaryByDepartment(List<Employee> employees, String department) {
        return employees.stream()
                .filter(employee -> employee.getDepartment().equals(department))
                .mapToDouble(Employee::getSalary)
                .average();
    }

    /**
     * Точка входа задания: печатает список сотрудников и среднюю зарплату по департаменту.
     *
     * @param department название департамента
     */
    public void run(String department) {
        List<Employee> employees = createEmployees();

        System.out.println("Список сотрудников:");
        employees.forEach(employee -> System.out.println("  " + employee));

        OptionalDouble average = averageSalaryByDepartment(employees, department);
        if (average.isPresent()) {
            System.out.printf("Средняя зарплата в департаменте \"%s\": %.2f%n", department, average.getAsDouble());
        } else {
            System.out.println("В департаменте \"" + department + "\" сотрудники не найдены");
        }
    }
}
