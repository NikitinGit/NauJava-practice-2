package ru.naujava.practice2;

/**
 * 3. Задание №3. Stream API. Класс сотрудника "Employee".
 * <p>
 * Приватные поля класса: ФИО ("fullName" тип "String"), Возраст ("age" тип "Integer"),
 * Отдел ("department" тип "String"), З/П ("salary" тип "Double"). Класс должен содержать
 * геттеры и сеттеры для доступа к полям.
 * <p>
 * Вариант 2. Найти среднюю зарплату всех сотрудников отдела в указанном департаменте.
 *
 * @see Task3StreamApi
 */
public class Employee {
    private String fullName;
    private Integer age;
    private String department;
    private Double salary;

    public Employee(String fullName, Integer age, String department, Double salary) {
        this.fullName = fullName;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{fullName='" + fullName + "', age=" + age
                + ", department='" + department + "', salary=" + salary + '}';
    }
}
