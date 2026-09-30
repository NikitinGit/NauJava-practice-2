package ru.naujava.practice2;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 5. Задание №5. Реализация интерфейса "Task".
 * <p>
 * Необходимо реализовать интерфейс Task (см. Приложение Г) в соответствии
 * со своим вариантом задания.
 * <p>
 * Вариант 2. Реализуйте интерфейс "Task" для скачивания указанного файла из интернета.
 * Ссылку на файл можно взять произвольную. При вызове метода start() начните скачивание,
 * а при вызове метода stop() прекратите. Если скачивание не завершено, то необходимо
 * очистить занятые ресурсы. Для выполнения задания рекомендуется использовать классы
 * "java.io.BufferedInputStream" и "java.io.FileOutputStream".
 */
public class Task5DownloadTask implements Task {

    private static final int BUFFER_SIZE = 8 * 1024;
    private static final int CONNECT_TIMEOUT_MILLIS = 10_000;
    private static final int READ_TIMEOUT_MILLIS = 30_000;

    private final String url;
    private final Path target;

    private volatile boolean running;
    private volatile InputStream input;
    private Thread worker;

    /**
     * @param url    адрес скачиваемого файла
     * @param target путь, по которому будет сохранён файл
     */
    public Task5DownloadTask(String url, Path target) {
        this.url = url;
        this.target = target;
    }

    @Override
    public void start() {
        if (running) {
            System.out.println("Скачивание уже запущено");
            return;
        }

        running = true;
        worker = new Thread(this::download, "download-task");
        worker.start();
        System.out.println("Скачивание запущено: " + url + " -> " + target.toAbsolutePath());
    }

    @Override
    public void stop() {
        Thread current = worker;
        if (current == null) {
            return;
        }

        running = false;
        // Прерывание не разблокирует чтение из сокета, поэтому закрываем поток принудительно.
        closeInput();

        try {
            current.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        worker = null;
        System.out.println("Задача остановлена");
    }

    /**
     * Тело загрузки: читает поток блоками и пишет их в файл,
     * прерываясь по флагу running. При незавершённой загрузке файл удаляется.
     */
    private void download() {
        boolean completed = false;
        long total = 0;

        try {
            URLConnection connection = URI.create(url).toURL().openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            connection.setReadTimeout(READ_TIMEOUT_MILLIS);

            try (InputStream in = new BufferedInputStream(connection.getInputStream());
                 FileOutputStream out = new FileOutputStream(target.toFile())) {
                input = in;

                byte[] buffer = new byte[BUFFER_SIZE];
                int read;
                while (running && (read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                    total += read;
                }

                completed = running;
            }
        } catch (IOException e) {
            // Поток, закрытый из stop(), тоже приводит сюда — это штатное прерывание.
            if (running) {
                System.out.println("Ошибка скачивания: " + e.getMessage());
            }
        } finally {
            input = null;
            if (completed) {
                System.out.println("Скачивание завершено, получено байт: " + total);
            } else {
                System.out.println("Скачивание прервано, получено байт: " + total);
                cleanup();
            }
            running = false;
        }
    }

    /**
     * Закрывает входной поток, чтобы разблокировать чтение в рабочем потоке.
     */
    private void closeInput() {
        InputStream in = input;
        if (in == null) {
            return;
        }

        try {
            in.close();
        } catch (IOException e) {
            // Поток уже закрыт — прерывать остановку задачи из-за этого не нужно.
        }
    }

    /**
     * Удаляет частично скачанный файл.
     */
    private void cleanup() {
        try {
            if (Files.deleteIfExists(target)) {
                System.out.println("Частично скачанный файл удалён: " + target.toAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Не удалось удалить файл " + target.toAbsolutePath() + ": " + e.getMessage());
        }
    }

    /**
     * Точка входа задания: запускает скачивание и ждёт его завершения
     * не дольше указанного времени, после чего останавливает задачу.
     *
     * @param timeoutMillis максимальное время ожидания, мс
     */
    public void run(long timeoutMillis) {
        start();
        try {
            Thread current = worker;
            if (current != null) {
                current.join(timeoutMillis);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        stop();
    }
}
