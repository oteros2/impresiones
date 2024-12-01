import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static void main(String[] args) throws IOException {
        // Creamos el archivo de registro donde se guardarán las operaciones realizadas
        BufferedWriter bw = new BufferedWriter(new FileWriter("log/log.txt", true));
        // Número de impresoras disponibles en el sistema
        final int NUM_IMPRESORAS = 3;
        //Creacion de la cola de impresion
        ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion = new ConcurrentLinkedQueue<>();
        Menu.createMenu(colaDeImpresion, bw, NUM_IMPRESORAS); //Creacion del menu
    }
}