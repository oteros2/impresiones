import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion = new ConcurrentLinkedQueue<>();
        Menu.createMenu(colaDeImpresion); //Creacion del menu


        int numeroImpresoras = 3;
        Impresoras[] impresoras = new Impresoras[numeroImpresoras];

        //Crea las impresoras
        for (int i = 0; i<numeroImpresoras;i++){
            impresoras[i] = new Impresoras(colaDeImpresion, (int) Math.floor(Math.random()*(6-2)+1));
        }

        //Crea los hilos y los ejecuta
        for (int i = 0; i < impresoras.length; i++){
            int numeroImpresora = i+1;
            Thread impresora = new Thread(impresoras[i],"Impresora"+numeroImpresora);
            impresora.start();
        }
    }
}


