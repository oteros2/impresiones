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

    public Impresoras(ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion, int limiteImpresiones, BufferedWriter bw, int precio) {
        this.colaDeImpresion = colaDeImpresion;
        this.limiteImpresiones = limiteImpresiones;
        this.contadorImpresiones = 0;
        this.bw = bw;
        this.precio = precio;
    }

    public int getPrecio() {
        return precio;
    }

    @Override
    public void run() {
        while (true) {
            if (contadorImpresiones < limiteImpresiones) {
                //intenta extraer un documento de la cola de impresion
                TrabajoImpresion trabajo = colaDeImpresion.poll();
                //Comprueba que el trabajo que trae de la cola no sea nulo
                if (trabajo != null) {
                    //imprime por pantalla el contenido del archivo en una ventana emergente
                    JOptionPane option = new JOptionPane("", JOptionPane.INFORMATION_MESSAGE);
                    StringBuilder impresion = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(trabajo.getArchivo()))) {
                        bw.write(new Date() + " " + Thread.currentThread().getName() + " está imprimiendo: " + trabajo.getNombreArchivo() + "\n");
                        bw.flush();
                        System.out.println(Thread.currentThread().getName() + " está imprimiendo: " + trabajo.getNombreArchivo());
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            impresion.append(linea + "\n");
                        }
                        option.setMessage(impresion);
                        JDialog dialog = option.createDialog(Thread.currentThread().getName());

                        //Calcula las medidas de la pantalla y las coordenadas para generar las ventanas de forma aleatoria
                        //para que no queden solapadas
                        int screenWidth = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
                        int screenHeight = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
                        int randomX = (int) (Math.random() * (screenWidth - dialog.getWidth()));
                        int randomY = (int) (Math.random() * (screenHeight - dialog.getHeight()));
                        dialog.setLocation(randomX, randomY);

                        //Crea un hilo que se encarga de cerrar las ventanas emergentes
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(10000); //Tiempo en el que se cerraran las ventanas
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                dialog.setVisible(false);
                            }
                        }).start();

                        dialog.setVisible(true);
                        dialog.dispose();
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
                    bw.write(new Date() + " [" + Thread.currentThread().getName() + " ha alcanzado su límite de impresiones (" + limiteImpresiones + "). Esperando para reiniciar...]" + "\n");
                    bw.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("[" + Thread.currentThread().getName() + " ha alcanzado su límite de impresiones (" + limiteImpresiones + "). Esperando para reiniciar...]");

                try {
                    Random random = new Random();
                    int tiempoEspera = (random.nextInt(21) * 1000) + 1;
                    bw.write(new Date() + " Tiempo estimado de espera para  " + Thread.currentThread().getName() + " " + tiempoEspera / 1000 + " segundos" + "\n");
                    bw.flush();
                    System.out.println("Tiempo estimado de espera para  " + Thread.currentThread().getName() + " " + tiempoEspera / 1000 + " segundos");// Genera un número aleatorio entre 0 y 10 (en milisegundos)
                    Thread.sleep(tiempoEspera);
                } catch (InterruptedException | IOException e) {
                    Thread.currentThread().interrupt();
                }

                contadorImpresiones = 0; // Reinicia el contador de impresiones
                try {
                    bw.write(new Date() + " " + Thread.currentThread().getName() + " está lista para imprimir nuevamente." + "\n");
                    bw.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " está lista para imprimir nuevamente.");
            }
        }
    }
}

