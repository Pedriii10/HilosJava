package ejercicio4;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        if (args.length != 2) {
            System.out.println("Debes proporcionar la cantidad de hilos y la cantidad de números.");
            return;
        }

        int numThreads = Integer.parseInt(args[0]);
        int numNumbers = Integer.parseInt(args[1]);

        int[] numbers = generateRandomNumbers(numNumbers);

        CompletableFuture<Integer> minFuture = CompletableFuture.supplyAsync(() -> {
            int min = numbers[0];
            for (int i = 1; i < numbers.length; i++) {
                min = Math.min(min, numbers[i]);
            }
            return min;
        });

        CompletableFuture<Integer> maxFuture = CompletableFuture.supplyAsync(() -> {
            int max = numbers[0];
            for (int i = 1; i < numbers.length; i++) {
                max = Math.max(max, numbers[i]);
            }
            return max;
        });

        System.out.println("Total: " + minFuture.get() + " " + maxFuture.get() + " [0, " + (numNumbers - 1) + "]");
    }

    private static int[] generateRandomNumbers(int count) {
        int[] numbers = new int[count];
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            numbers[i] = random.nextInt(2000) - 1000; // Números aleatorios entre -1000 y 1000
        }
        return numbers;
    }
}

