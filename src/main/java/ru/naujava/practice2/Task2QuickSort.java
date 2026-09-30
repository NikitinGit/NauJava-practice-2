package ru.naujava.practice2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 2. Задание №2. Работа со списками.
 * <p>
 * Заполните список (тип {@code ArrayList<Double>}) случайным числами и отсортируйте его.
 * Метод сортировки выбирается в соответствии с вашим вариантом. Сортировку необходимо
 * реализовать вручную (не использовать встроенные возможности Java или сторонних библиотек).
 * <p>
 * Вариант 2. Быстрая сортировка (Quick Sort).
 * <p>
 * Входные данные: количество элементов в формируемом массиве n [0, ∞).
 * <p>
 * Выходные данные: в консоль напечатаны исходный и отсортированный списки.
 */
public class Task2QuickSort {

    private static final double BOUND = 100.0;

    /**
     * Заполняет список случайными вещественными числами из диапазона [-BOUND, BOUND).
     *
     * @param n количество элементов, n >= 0
     * @return заполненный список
     */
    private ArrayList<Double> fillRandom(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Количество элементов не может быть отрицательным: " + n);
        }

        Random random = new Random();
        ArrayList<Double> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add(Math.round(random.nextDouble(-BOUND, BOUND) * 100.0) / 100.0);
        }

        return list;
    }

    /**
     * Сортирует список по возрастанию быстрой сортировкой (in-place).
     *
     * @param list сортируемый список
     */
    private void quickSort(List<Double> list) {
        quickSort(list, 0, list.size() - 1);
    }

    /**
     * Рекурсивная часть быстрой сортировки для отрезка [low, high].
     *
     * @param list сортируемый список
     * @param low  левая граница отрезка
     * @param high правая граница отрезка
     */
    private void quickSort(List<Double> list, int low, int high) {
        if (low >= high) {
            return;
        }

        int pivotIndex = partition(list, low, high);
        quickSort(list, low, pivotIndex - 1);
        quickSort(list, pivotIndex + 1, high);
    }

    /**
     * Разбиение по схеме Ломуто: опорным выбирается последний элемент отрезка.
     *
     * @param list сортируемый список
     * @param low  левая граница отрезка
     * @param high правая граница отрезка
     * @return итоговая позиция опорного элемента
     */
    private int partition(List<Double> list, int low, int high) {
        double pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j) <= pivot) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }

    /**
     * Меняет местами два элемента списка.
     *
     * @param list список
     * @param i    индекс первого элемента
     * @param j    индекс второго элемента
     */
    private void swap(List<Double> list, int i, int j) {
        Double tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    /**
     * Точка входа задания: формирует список, сортирует его и печатает результат.
     *
     * @param n количество элементов списка
     */
    public void run(int n) {
        ArrayList<Double> list = fillRandom(n);
        System.out.println("Исходный список:        " + list);

        quickSort(list);
        System.out.println("Отсортированный список: " + list);
    }
}
