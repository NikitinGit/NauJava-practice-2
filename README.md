# NauJava. Практическая работа №2 — Java, Maven

Практическая работа №2 курса Naumen: основы Java, сборка проекта с помощью Maven.

**Вариант 2.**

## Требования

- Java 21
- Maven 3.9+

## Задания и их реализация

| № | Задание (вариант 2) | Класс |
|---|---------------------|-------|
| 1 | Работа с массивом: найти минимальное значение по модулю | [`Task1Array`](src/main/java/ru/naujava/practice2/Task1Array.java) |
| 2 | Работа со списками: быстрая сортировка (Quick Sort) вручную | [`Task2QuickSort`](src/main/java/ru/naujava/practice2/Task2QuickSort.java) |
| 3 | Stream API: средняя зарплата сотрудников указанного департамента | [`Task3StreamApi`](src/main/java/ru/naujava/practice2/Task3StreamApi.java), [`Employee`](src/main/java/ru/naujava/practice2/Employee.java) |
| 4 | HTTP клиент и JSON: вывести User-Agent с `https://httpbin.org/user-agent` | [`Task4HttpJson`](src/main/java/ru/naujava/practice2/Task4HttpJson.java) |
| 5 | Реализация интерфейса `Task`: скачивание файла из интернета | [`Task5DownloadTask`](src/main/java/ru/naujava/practice2/Task5DownloadTask.java), [`Task`](src/main/java/ru/naujava/practice2/Task.java) |

## Сборка

```bash
mvn clean package
```

Собирается исполняемый jar со всеми зависимостями: `target/naujava-practice-2.jar`.

## Запуск

Интерактивное меню:

```bash
java -jar target/naujava-practice-2.jar
```

Запуск конкретного задания без меню:

```bash
java -jar target/naujava-practice-2.jar 1 15          # задание 1, массив из 15 элементов
java -jar target/naujava-practice-2.jar 2 12          # задание 2, список из 12 элементов
java -jar target/naujava-practice-2.jar 3 Аналитика   # задание 3, департамент "Аналитика"
java -jar target/naujava-practice-2.jar 4             # задание 4
java -jar target/naujava-practice-2.jar 5             # задание 5
```

## Зависимости

- [Jackson Databind](https://github.com/FasterXML/jackson-databind) — разбор JSON в задании №4.

## Задание №5: демонстрация start/stop

По умолчанию скачивается небольшой файл и загрузка успевает завершиться:

```bash
java -jar target/naujava-practice-2.jar 5
```

Чтобы увидеть прерывание по `stop()` и очистку ресурсов, достаточно указать
файл, который не успевает скачаться за отведённые 5 секунд:

```bash
java -jar target/naujava-practice-2.jar 5 https://httpbin.org/bytes/262144
```

Незавершённая загрузка удаляется, поток закрывается.
