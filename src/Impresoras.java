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
        while (true) {
            if (contadorImpresiones < limiteImpresiones) {
                TrabajoImpresion trabajo = colaDeImpresion.poll(); //intenta extraer un documentode la cola y en caso contrario espera un breve tiempo
                if (trabajo != null) {
                    System.out.println(Thread.currentThread().getName() + " está imprimiendo: " + trabajo);
                    contadorImpresiones++;

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
                    Thread.sleep(5000); // Espera antes de reiniciar el contador
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                contadorImpresiones = 0; // Reinicia el contador de impresiones
                System.out.println(Thread.currentThread().getName() + " está lista para imprimir nuevamente.");
            }
        }
    }
}