package ejercicio2;

import java.util.ArrayList;

public class Main {
    private static class Metodos {
        private int numero = 0;

        public void incrementar(int contador) {
            for (int i = 0; i < contador; i++) {
                numero++;
            }
        }

        public int getNumero() {
            return numero;
        }
    }

    public static Metodos misMetodos;

    static class MiHilo extends Thread {
        private final int contador;

        MiHilo(int contador) {
            this.contador = contador;
        }

        public void run() {
            misMetodos.incrementar(contador);
        }
    }

    public static void main(String[] args) {
        misMetodos = new Metodos();
        int contador = 5000;

        ArrayList<MiHilo> hilos = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            MiHilo hilo = new MiHilo(contador);
            hilos.add(hilo);
        }

        for (MiHilo hilo : hilos) {
            hilo.start();
        }

        for (MiHilo hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Valor final del contador: " + misMetodos.getNumero());
    }
}
