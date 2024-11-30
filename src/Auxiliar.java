import javax.swing.*;
import java.io.BufferedWriter;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Auxiliar {
    public static void crearImpresoras(int numeroImpresoras, BufferedWriter bw, ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion, JTextArea[] textAreas) {
        //definir la cantidad de impresoras que vamos a usar
        Impresoras[] impresoras = new Impresoras[numeroImpresoras];
        int[] precios = new int[numeroImpresoras];

        //Genera los precios aleatorios
        for (int i = 0; i < precios.length; i++) {
            precios[i] = (int) Math.floor(Math.random() * 451 + 50);
        }

        //Crea las impresoras
        for (int i = 0; i < numeroImpresoras; i++) {
            impresoras[i] = new Impresoras(colaDeImpresion, (int) Math.floor(Math.random() * (6 - 2) + 1), bw, precios[i]);
        }

        //Crea los hilos y los ejecuta
        for (int i = 0; i < impresoras.length; i++) {
            int numeroImpresora = i + 1;
            Thread impresora = new Thread(impresoras[i], "Impresora " + numeroImpresora);
            if (impresoras[i].getPrecio() < 200) {
                impresora.setPriority(Thread.MAX_PRIORITY);
            } else {
                impresora.setPriority(Thread.MIN_PRIORITY);
            }
            impresora.start();
        }
    }
}
