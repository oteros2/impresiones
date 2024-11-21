import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

class Impresoras implements Runnable {
    private ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion;
    private int limiteImpresiones;    // Límite de impresiones antes de reiniciar
    private int contadorImpresiones;   // Contador actual de impresiones

    public Impresoras(ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion, int limiteImpresiones) {
        this.colaDeImpresion = colaDeImpresion;
        this.limiteImpresiones = limiteImpresiones;
        this.contadorImpresiones = 0;
    }

    @Override
    public void run() {
        Random random = new Random();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        while (true) {
            if (contadorImpresiones < limiteImpresiones) {
                TrabajoImpresion trabajo = colaDeImpresion.poll(); //intenta extraer un documentode la cola y en caso contrario espera un breve tiempo
                if (trabajo != null) {
                    try (BufferedReader br = new BufferedReader(new FileReader(trabajo.getArchivo()))) {
                        System.out.println(Thread.currentThread().getName() + " está imprimiendo: " + trabajo.getNombreArchivo());
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            int width = screenSize.width;
                            int height = screenSize.height;
                            int x = random.nextInt(width - 300);
                            int y = random.nextInt(height - 200);
                            JFrame frame = new JFrame();
                            frame.setUndecorated(true);
                            frame.setBounds(x, y, 1, 1);
                            frame.setVisible(true);
                            JOptionPane.showMessageDialog(frame, Thread.currentThread().getName() + ": " + linea);
                        }
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
                System.out.println("["+Thread.currentThread().getName() + " ha alcanzado su límite de impresiones (" + limiteImpresiones + "). Esperando para reiniciar...]");

                try {
                    int tiempoEspera = random.nextInt(11) * 1000;
                    System.out.println("Tiempo estimado de espera "+tiempoEspera/1000+" s");// Genera un número aleatorio entre 0 y 10 (en milisegundos)
                    Thread.sleep(tiempoEspera);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                contadorImpresiones = 0; // Reinicia el contador de impresiones
                System.out.println(Thread.currentThread().getName() + " está lista para imprimir nuevamente.");
            }
        }
    }
}