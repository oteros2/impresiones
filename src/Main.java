import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("log/log.txt", true));
        //Creacion de la cola de impresion
        ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion = new ConcurrentLinkedQueue<>();
        Menu.createMenu(colaDeImpresion, bw); //Creacion del menu

        //definir la cantidad de impresoras que vamos a usar
        int numeroImpresoras = 3;
        Impresoras[] impresoras = new Impresoras[numeroImpresoras];
        int[] precios = new int[numeroImpresoras];
        precios[0] = 100;
        precios[1] = 500;
        precios[2] = 300;

        //Crea las impresoras
        for (int i = 0; i<numeroImpresoras;i++){
            impresoras[i] = new Impresoras(colaDeImpresion, (int) Math.floor(Math.random()*(6-2)+1), bw, precios[i]);
        }

        //Crea los hilos y los ejecuta
        for (int i = 0; i < impresoras.length; i++){
            int numeroImpresora = i+1;
            Thread impresora = new Thread(impresoras[i],"Impresora "+numeroImpresora);
            if(impresoras[i].getPrecio() < 200){
                impresora.setPriority(Thread.MAX_PRIORITY);
            } else {
                impresora.setPriority(Thread.MIN_PRIORITY);
            }
            impresora.start();
        }
    }
}


