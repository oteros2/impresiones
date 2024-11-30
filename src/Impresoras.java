import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

class Impresoras implements Runnable {
    private ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion;
    private int limiteImpresiones;    // Límite de impresiones antes de reiniciar
    private int contadorImpresiones;   // Contador actual de impresiones
    private BufferedWriter bw;
    private int precio;
    JTextArea textArea;

    public Impresoras(ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion, int limiteImpresiones, BufferedWriter bw, int precio, JTextArea textArea) {
        this.colaDeImpresion = colaDeImpresion;
        this.limiteImpresiones = limiteImpresiones;
        this.contadorImpresiones = 0;
        this.bw = bw;
        this.precio = precio;
        this.textArea = textArea;
    }

    public int getPrecio() {
        return precio;
    }

    @Override
    public void run() {
        while (!colaDeImpresion.isEmpty()) {
            if (contadorImpresiones < limiteImpresiones) {
                //intenta extraer un documento de la cola de impresion
                TrabajoImpresion trabajo = colaDeImpresion.poll();
                //Comprueba que el trabajo que trae de la cola no sea nulo
                if (trabajo != null) {
                    //imprime por pantalla el contenido del archivo en una ventana emergente
                    JOptionPane option = new JOptionPane("", JOptionPane.INFORMATION_MESSAGE);
                    StringBuilder impresion = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(trabajo.getArchivo()))) {
                        textArea.append("imprimiendo " + trabajo.getNombreArchivo() + "\n");
                        bw.write(new Date() + " " + Thread.currentThread().getName() + " está imprimiendo: " + trabajo.getNombreArchivo() + "\n");
                        bw.flush();
                        contadorImpresiones++;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Thread.sleep(1000); // Simula el tiempo de impresión
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    try {
                        Thread.sleep(100); // Espera un pequeño intervalo de tiempo si la cola está vacía
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } else {
                try {
                    textArea.append("Limite de " + limiteImpresiones + " impresiones alcanzado. Reiniciando..." + "\n");
                    bw.write(new Date() + " [" + Thread.currentThread().getName() + " ha alcanzado su límite de impresiones (" + limiteImpresiones + "). Esperando para reiniciar...]" + "\n");
                    bw.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Random random = new Random();
                    int tiempoEspera = (random.nextInt(21) * 1000) + 1;
                    textArea.append("Tiempo estimado de espera " + tiempoEspera / 1000 + " segundos" + "\n");
                    bw.write(new Date() + " Tiempo estimado de espera para  " + Thread.currentThread().getName() + " " + tiempoEspera / 1000 + " segundos" + "\n");
                    bw.flush();
                    Thread.sleep(tiempoEspera);
                } catch (InterruptedException | IOException e) {
                    Thread.currentThread().interrupt();
                }
                contadorImpresiones = 0; // Reinicia el contador de impresiones
                try {
                    textArea.append("Imprimiendo de nuevo..." + "\n");
                    bw.write(new Date() + " " + Thread.currentThread().getName() + " está lista para imprimir nuevamente." + "\n");
                    bw.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (colaDeImpresion.isEmpty()) {
                textArea.append("Impresiones terminadas. \n");
            }
        }
    }
}

