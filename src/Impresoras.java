import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Clase que simula el comportamiento de una impresora.
 * Implementa Runnable.
 */
class Impresoras implements Runnable {
    private ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion; //Cola compartida de trabajos a imprimir
    int numeroImpresoras; // Número total de impresoras en el sistema
    private int limiteImpresiones;    // Límite de impresiones antes de reiniciar
    private int totalImpresiones = 0;// Contador de impresiones individuales
    private int contadorImpresiones;   // Contador actual de impresiones
    private BufferedWriter bw; // Buffer para registrar eventos en el archivo de log
    private int precio; // Precio asignado a la impresora
    private JButton[] botones; // Botones de control de las impresoras en la interfaz
    JTextArea textArea; // Área de texto para mostrar mensajes de la impresora


    /**
     * Constructor que inicializa los atributos de la impresora.
     *
     * @param colaDeImpresion  Cola compartida de trabajos de impresión.
     * @param limiteImpresiones Límite de impresiones antes de reiniciar.
     * @param bw               Buffer para el archivo de log.
     * @param precio           Precio asociado a la impresora.
     * @param textArea         Área de texto para mostrar información.
     * @param buttons          Botones de control de impresoras.
     * @param numeroImpresoras Número total de impresoras.
     */
    public Impresoras(ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion, int limiteImpresiones, BufferedWriter bw, int precio, JTextArea textArea, JButton[] buttons, int numeroImpresoras, int totalImpresiones) {
        this.colaDeImpresion = colaDeImpresion;
        this.limiteImpresiones = limiteImpresiones;
        this.contadorImpresiones = 0;
        this.bw = bw;
        this.precio = precio;
        this.textArea = textArea;
        this.botones = buttons;
        this.numeroImpresoras = numeroImpresoras;
        this.totalImpresiones = totalImpresiones;
    }

    /**
     * Devuelve el precio asociado a la impresora.
     *
     * @return Precio de la impresora.
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Devuelve el límite de impresiones antes de reiniciar.
     *
     * @return Límite de impresiones.
     */
    public int getLimiteImpresiones() {
        return limiteImpresiones;
    }

    /**
     * Método principal del hilo, que procesa trabajos de impresión de la cola.
     */
    @Override
    public void run() {
        while (!colaDeImpresion.isEmpty()) { // Ejecutar mientras haya trabajos en la cola.
            if (contadorImpresiones < limiteImpresiones) {
                //Intenta extraer un documento de la cola de impresion.
                TrabajoImpresion trabajo = colaDeImpresion.poll();
                //Comprueba que el trabajo que trae de la cola no sea nulo.
                if (trabajo != null) { // Si hay un trabajo válido
                    try {
                        textArea.append("imprimiendo " + trabajo.getNombreArchivo() + "\n");
                        bw.write(new Date() + " " + Thread.currentThread().getName() + " está imprimiendo: " + trabajo.getNombreArchivo() + "\n");
                        bw.flush();// Asegura que los datos se escriban inmediatamente en el archivo.
                        contadorImpresiones++;// Incrementa el contador de impresiones realizadas.
                        totalImpresiones++;// Incrementa el contador de impresiones totales.
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Thread.sleep(1000); // Simula el tiempo de impresión
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } else {// Si no hay trabajos disponibles, espera brevemente antes de intentar nuevamente.
                    try {
                        Thread.sleep(100); // Espera un pequeño intervalo de tiempo si la cola está vacía
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } else {
                try {// Cuando se alcanza el límite de impresiones, la impresora debe "reiniciarse".
                    // Muestra un mensaje en la interfaz.
                    textArea.append("Limite de " + limiteImpresiones + " impresiones alcanzado. Reiniciando..." + "\n");
                    bw.write(new Date() + " [" + Thread.currentThread().getName() + " ha alcanzado su límite de impresiones (" + limiteImpresiones + "). Esperando para reiniciar...]" + "\n");
                    bw.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try { // Simula un reinicio con un tiempo de espera aleatorio
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
                // Notificar que la impresora está lista nuevamente.
                try {
                    textArea.append("Imprimiendo de nuevo..." + "\n");
                    bw.write(new Date() + " " + Thread.currentThread().getName() + " está lista para imprimir nuevamente." + "\n");
                    bw.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            // Si la cola está vacía, notificar que se han terminado los trabajos.
            if (colaDeImpresion.isEmpty()) {
                textArea.append("Impresiones terminadas. \n");
                // Mostrar el numero de impresiones realizadas por la impresora.
                textArea.append("Total de impresiones realizadas: " + totalImpresiones + "\n");
                // Actualizar los botones de control para mostrar que las impresoras están detenidas.
                for (int i = 0; i < numeroImpresoras; i++) {
                    botones[i].setText("Detener Impresora " + (i + 1)); // Actualiza el texto de los botones.
                    botones[i].setBackground(Color.GRAY); // Cambia el color de los botones a gris.
                    botones[i].setEnabled(false); // Desactiva los botones.
                }
            }
        }
    }
}

