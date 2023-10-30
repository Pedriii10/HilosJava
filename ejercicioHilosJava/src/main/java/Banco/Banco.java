package Banco;

import java.util.ArrayList;
import java.util.Random;


public class Banco {

	private static class CuentaBancaria
	{
		private int numero=0;
		
		private synchronized void mostrarCantidad()
		{
			System.out.println(Thread.currentThread().getName()+": "+numero+"�");
		}
		
		public synchronized void sacarDinero(int cantidad)
		{
			while(numero<cantidad)
			{
				notify();
				System.out.println(Thread.currentThread().getName()+" No puedo sacar "+cantidad+"� porque hay "+numero+"� en la cuenta");
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

				mostrarCantidad();
				numero-=cantidad;
				mostrarCantidad();
		}

		public synchronized void ingresarDinero(int cantidad)
		{
			mostrarCantidad();
			numero+=cantidad;
			mostrarCantidad();
			notify();
		}
		public synchronized int getCantidad() {return numero;}
	}
	
	public static CuentaBancaria miCuenta;

	private static class MiThread extends Thread
	{	
		int diferencia;
		MiThread(int diferencia)
		{
			this.diferencia=diferencia;			
		}
		
		@Override
		public void run()
		{
			try {
				Thread.sleep((int) (Math.random()*10_000+100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(this.diferencia>0)
				miCuenta.ingresarDinero(diferencia);
			else
				miCuenta.sacarDinero(-diferencia);
				
		}
	}

	
	public static void main(String[] args) 
	{
		miCuenta=new CuentaBancaria();
		// TODO Auto-generated method stub
		ArrayList<Thread> hilos=new ArrayList();


		
		int numHilos=10;
		
		
		MiThread nuevoHilo;
		
		
		for(int i=0; i<numHilos; i++)
		{

			nuevoHilo=new MiThread(-((i+1)*100));
			hilos.add(nuevoHilo);
			
			
			nuevoHilo=new MiThread((i+1)*100);
			hilos.add(nuevoHilo);	
		}


		for(Thread hilo:hilos)
			hilo.start();
		
		for(Thread hilo:hilos)
			try {
				hilo.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		System.out.println("El valor de la cuenta es: "+miCuenta.getCantidad());
	}

}