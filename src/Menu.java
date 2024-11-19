import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Menu {
    static void createMenu(ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion) {
        //marco de la ventana
        JFrame frame = new JFrame("Selector de Fichero");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        Font font = new Font("SansSerif", Font.BOLD, 18);

        //desplegable
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(200, 50));
        comboBox.setFont(font);
        //Añadir a la cola de impresion
        JButton botonanadirALaCola = new JButton("Añadir a la cola");
        botonanadirALaCola.setPreferredSize(new Dimension(300, 50));
        botonanadirALaCola.setFont(font);
        //Area de texto de los archivos en la cola
        JTextArea textAreaCola = new JTextArea();
        textAreaCola.setEditable(false);
        textAreaCola.setBackground(Color.LIGHT_GRAY);
        textAreaCola.setPreferredSize(new Dimension(600, 100));
        textAreaCola.setFont(font);
        textAreaCola.setText("Archivos en la cola de impresión:\n");
        //boton imprimir
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setPreferredSize(new Dimension(300, 50));
        btnImprimir.setFont(font);

        //area de texto
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setPreferredSize(new Dimension(600, 150));
        textArea.setFont(font);

        // Comprueba que el directorio existe
        File directorio = new File("files");
        if (directorio.exists() && directorio.isDirectory()) {
            String[] ficheros = directorio.list();
            //Comrpueba que hay ficheros y añade el nombre al desplegable
            if (ficheros != null) {
                Arrays.sort(ficheros);
                for (String file : ficheros) {
                    comboBox.addItem(file);
                }
            }
        } else {
            textArea.setText("El directorio 'files' no existe.");
        }

        //Accion del boton imprimir
        btnImprimir.addActionListener(e -> {
            //Selecciona el archivo por el nombre
            String seleccionado = (String) comboBox.getSelectedItem();
            if (seleccionado != null) {
                File archivoSeleccionado = new File(directorio, seleccionado);
                if (archivoSeleccionado.exists() && archivoSeleccionado.isFile()) {
                    // Crea un trabajo de impresión y lo añade a la cola
                    TrabajoImpresion trabajo = new TrabajoImpresion(seleccionado, archivoSeleccionado);
                    colaDeImpresion.add(trabajo);
                    textArea.setText("Fichero " + seleccionado + " enviado a la cola de impresión.");
                    System.out.println("Se ha añadido a la cola de impresion: " + trabajo);
                } else {
                    textArea.setText("El archivo seleccionado no existe.");
                }
            } else {
                textArea.setText("No se ha seleccionado ningún fichero.");
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(2,100));
        panel.add(comboBox);
        panel.add(botonanadirALaCola);
        panel.add(textAreaCola);
        panel.add(separator);
        panel.add(btnImprimir);
        panel.add(separator);
        panel.add(textArea);
        frame.add(panel);
        frame.setVisible(true);
    }
}