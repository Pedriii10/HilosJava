package ejemplo;


public class PruebaHilos {
/*
    static class MiRun implements Runnable
    {
        private String _nombre;
        private int number;
        MiRun(String nombre)
        {
            _nombre=nombre;
        }
        public int getNumber() {
            return number;
        }
        public void run() {
            number=598;
            Thread.currentThread().setName(_nombre);
            String t=Thread.currentThread().getName();
            System.out.println(t+ " Hola");
        }
    }




    public static void main2(String[] args) {

        String t=Thread.currentThread().getName();
        System.out.println(t+" Comienza ....");

        MiRun runna=new MiRun("1234");
        Thread nuevoHilo=new Thread(runna);
        nuevoHilo.start();


        try {
            nuevoHilo.join();

            System.out.println(t+" Numero "+runna.getNumber());

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String t2=Thread.currentThread().getName();
        System.out.println(t2+" Finaliza ....");
    }*/


    static class MiHilo extends Thread
    {
        private int number=0;


        MiHilo(String nombre)
        {
            String t=Thread.currentThread().getName();
            System.out.println(t+" Constructor");


            this.setName(nombre);
        }

        public int getNumber() {
            return number;
        }

        @Override
        public void run() {
            String t=Thread.currentThread().getName();
            System.out.println(t+ " Comienza");

            number=567;


            Thread nuevoHilo=new Thread(new Runnable() {

                @Override
                public void run() {

                    String t2=Thread.currentThread().getName();
                    System.out.println(t2+" ----");
                }
            });

            nuevoHilo.start();

            try {
                Thread.sleep(1_500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                nuevoHilo.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String t2=Thread.currentThread().getName();
            System.out.println(t2+" Fin");
        }

    }


    public static void main(String[] args) {

        String t=Thread.currentThread().getName();
        System.out.println(t+" Comienza ....");

        MiHilo prueba=new MiHilo("1234");
        prueba.start();

		/*try {
			Thread.sleep(10_000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

        try {
            prueba.join();
            System.out.println(t+" Numero "+	prueba.getNumber());

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String t2=Thread.currentThread().getName();
        System.out.println(t2+" Fin ....");


    }

}


