package ejercicio3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Debe proporcionar una lista de nombres de archivos como argumentos.");
            return;
        }

        List<String> filenames = new ArrayList<>();
        for (String arg : args) {
            filenames.add(arg);
        }

        // Versión secuencial
        long startTime = System.currentTimeMillis();
        contarLineasCaracteresSecuencial(filenames);
        long endTime = System.currentTimeMillis();
        System.out.println("Tiempo en la versión secuencial: " + (endTime - startTime) + " ms");

        // Versión paralela
        startTime = System.currentTimeMillis();
        contarLineasCaracteresParalelo(filenames);
        endTime = System.currentTimeMillis();
        System.out.println("Tiempo en la versión paralela: " + (endTime - startTime) + " ms");
    }

    private static void contarLineasCaracteresSecuencial(List<String> filenames) {
        int totalLineas = 0;
        int totalCaracteres = 0;

        for (String filename : filenames) {
            int lineas = 0;
            int caracteres = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    lineas++;
                    caracteres += linea.length();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            totalLineas += lineas;
            totalCaracteres += caracteres;

            System.out.println(filename + ": " + lineas + " líneas con " + caracteres + " caracteres");
        }

        System.out.println("Procesados " + filenames.size() + " ficheros en la versión secuencial");
        System.out.println("Total: " + totalLineas + " líneas con " + totalCaracteres + " caracteres");
    }

    private static void contarLineasCaracteresParalelo(List<String> filenames) {
        AtomicInteger totalLineas = new AtomicInteger();
        AtomicInteger totalCaracteres = new AtomicInteger();

        List<Thread> threads = new ArrayList<>();

        for (String filename : filenames) {
            Thread thread = new Thread(() -> {
                int lineas = 0;
                int caracteres = 0;

                try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        lineas++;
                        caracteres += linea.length();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                synchronized (lock) {
                    totalLineas.addAndGet(lineas);
                    totalCaracteres.addAndGet(caracteres);
                    System.out.println(filename + ": " + lineas + " líneas con " + caracteres + " caracteres");
                }
            });

            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Procesados " + filenames.size() + " ficheros en la versión paralela");
        System.out.println("Total: " + totalLineas + " líneas con " + totalCaracteres + " caracteres");
    }
}
