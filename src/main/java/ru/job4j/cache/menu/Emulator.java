package ru.job4j.cache.menu;

import ru.job4j.cache.DirFileCache;

import java.util.Scanner;

public class Emulator extends DirFileCache {
    private static final int ADD_DIRECTORY = 1;
    private static final int LOAD_CACHE_DIRECTORY = 2;

    private static final int OUT_CACHE_DIRECTORY = 3;

    public static final String SELECT = "Выберите меню";
    public static final String DIRECTORY = "Введите адрес директории";
    public static final String NAME_FILE = "Введите имя и расширение файла";
    public static final String EXIT = "Конец работы";

    public static final String MENU = """
                Введите 1, для ввода директории.
                Введите 2, чтобы загрузить файлы в кеш.
                Введите 3, чтобы получить файлы из кеша.
                Введите любое другое число для выхода.
            """;

    public Emulator(String cachingDir) {
        super(cachingDir);
    }

    public static void main(String[] args) {
        Emulator emulator = null;
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println(MENU);
            System.out.println(SELECT);
            int userChoice = Integer.parseInt(scanner.nextLine());
            System.out.println(userChoice);
            if (ADD_DIRECTORY == userChoice) {
                System.out.println(DIRECTORY);
                String text = scanner.nextLine();
                emulator = new Emulator(text);
                System.out.println("Кешируемая территория " + text);
            } else if (LOAD_CACHE_DIRECTORY == userChoice) {
                System.out.println(NAME_FILE);
                String text = scanner.nextLine();
                emulator.load(text);
                System.out.println("Файл " + text + " загружен в кеш");

            } else if (OUT_CACHE_DIRECTORY == userChoice) {
                System.out.println(NAME_FILE);
                String text = scanner.nextLine();
                String value = emulator.get(text);
                if (value == null) {
                    System.out.println("Файл не обнаружен");
                } else {
                    System.out.println("Файл " + value);
                }
            } else {
                run = false;
                System.out.println(EXIT);
            }
        }
    }
}

