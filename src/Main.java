public class Main {
    public static void main(String[] args){
        Menu.createMenu();
        ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion = new ConcurrentLinkedQueue<>();
        int limiteImpresiones = 5;
        Thread impresora1 = new Thread(new Impresoras(colaDeImpresion, limiteImpresiones), "Impresora 1");
        Thread impresora2 = new Thread(new Impresoras(colaDeImpresion, limiteImpresiones), "Impresora 2");
        Thread impresora3 = new Thread(new Impresoras(colaDeImpresion, limiteImpresiones), "Impresora 3");

        impresora1.start();
        impresora2.start();
        impresora3.start();
    }
}

