import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static void main(String[] args) {
        //Menu.createMenu(); Implementación realizada a posterior sobre este codigo mejorado
        ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion = new ConcurrentLinkedQueue<>();
        int limiteImpresiones = 5;
        Thread impresora1 = new Thread(new Impresoras(colaDeImpresion, limiteImpresiones), "Impresora 1");
        Thread impresora2 = new Thread(new Impresoras(colaDeImpresion, limiteImpresiones), "Impresora 2");
        Thread impresora3 = new Thread(new Impresoras(colaDeImpresion, limiteImpresiones), "Impresora 3");

        impresora1.start();
        impresora2.start();
        impresora3.start();

        Thread generadorDeTrabajos = new Thread(() -> {
            int contador = 1;
            while (true) {
                try {
                    Thread.sleep(2000);
                    String nombreDocumento = "Documento" + contador + ".pdf";
                    TrabajoImpresion nuevoTrabajo = new TrabajoImpresion(nombreDocumento);
                    colaDeImpresion.add(nuevoTrabajo);
                    System.out.println("Se ha añadido un nuevo trabajo en la cola: " + nuevoTrabajo);
                    contador++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        generadorDeTrabajos.start();
    }
}

