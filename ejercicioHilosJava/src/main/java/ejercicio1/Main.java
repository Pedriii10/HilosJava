package ejercicio1;

import java.util.ArrayList;
import java.util.Random;

public class Main{

    static class Hilo extends Thread{

        private String nombre;

        Hilo(String nombre){
            super(nombre);
        }
    }

    public static void main(String[] args){

        ArrayList<Hilo> hilos = new ArrayList<>();

        for (int i = 0; i <= 2; i++) {
            Hilo hilo = new Hilo("hilo" + i);
            hilos.add(hilo);
        }

        hilos.get(0).start();
        hilos.get(1).start();
        hilos.get(2).start();


        try {
            Thread.sleep((long) (Math.random()* 2_000 + 1_000));
            System.out.println(hilos.get(0).getName() + ": Hola Mundo");
            Thread.sleep((long) (Math.random()* 2_000 + 1_000));
            System.out.println(hilos.get(1).getName() + ": Hola Mundo");
            Thread.sleep((long) (Math.random()* 2_000 + 1_000));
            System.out.println(hilos.get(2).getName() + ": Hola Mundo");
        } catch (Exception ex) {

        }
    }

}
