package ru.naujava.practice2;

import java.util.Arrays;
import java.util.Random;

/**
 * 1. Задание №1. Работа с массивом.
 * <p>
 * Заполните массив (тип элементов "int") случайными числами и выполните задание
 * в соответствии со своим вариантом.
 * <p>
 * Вариант 2. Найти минимальное значение по модулю в массиве.
 * <p>
 * Входные данные: количество элементов в формируемом массиве n [0, ∞).
 * <p>
 * Выходные данные: в консоль напечатаны массив и результат поиска
 * в соответствии с заданием.
 */
public class Task1Array {

    private static final int MIN_VALUE = -100;
    private static final int MAX_VALUE = 100;

    /**
     * Заполняет массив случайными числами в диапазоне [MIN_VALUE, MAX_VALUE].
     *
     * @param n количество элементов массива, n >= 0
     * @return заполненный массив
     */
    private int[] fillRandom(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Количество элементов не может быть отрицательным: " + n);
        }

        Random random = new Random();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(MIN_VALUE, MAX_VALUE + 1);
        }

        return array;
    }

    /**
     * Ищет элемент с минимальным абсолютным значением.
     *
     * @param array исходный массив
     * @return элемент массива, минимальный по модулю
     * @throws IllegalArgumentException если массив пуст
     */
    private int findMinByAbsoluteValue(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Массив пуст, искать нечего");
        }

        int result = array[0];
        for (int value : array) {
            if (Math.abs(value) < Math.abs(result)) {
                result = value;
            }
        }

        return result;
    }

    /**
     * Точка входа задания: формирует массив и печатает результат поиска.
     *
     * @param n количество элементов массива
     */
    public void run(int n) {
        int[] array = fillRandom(n);
        System.out.println("Массив: " + Arrays.toString(array));

        if (array.length == 0) {
            System.out.println("Массив пуст: минимальное по модулю значение не определено");
            return;
        }

        int min = findMinByAbsoluteValue(array);
        System.out.println("Минимальное по модулю значение: " + min + " (|" + min + "| = " + Math.abs(min) + ")");
    }
}
