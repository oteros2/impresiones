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

        //Crea las impresoras
        for (int i = 0; i<numeroImpresoras;i++){
            impresoras[i] = new Impresoras(colaDeImpresion, (int) Math.floor(Math.random()*(6-2)+1), bw);
        }

        //Crea los hilos y los ejecuta
        for (int i = 0; i < impresoras.length; i++){
            int numeroImpresora = i+1;
            Thread impresora = new Thread(impresoras[i],"Impresora"+numeroImpresora);
            impresora.start();
        }
    }
}


