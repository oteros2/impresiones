import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class Menu {
    static void createMenu() {
        //marco de la ventana
        JFrame frame = new JFrame("Selector de Fichero");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 400);
        frame.setLocationRelativeTo(null);

        //desplegable
        JComboBox<String> comboBox = new JComboBox<>();

        //boton imprimir
        JButton btnImprimir = new JButton("Imprimir");

        //area de texto
        JTextArea textArea = new JTextArea(3, 30);
        textArea.setEditable(false);
        textArea.setBackground(Color.LIGHT_GRAY);

        //directorio de ficheros
        File directorio = new File("files");
        if (directorio.exists() && directorio.isDirectory()) {
            String[] ficheros = directorio.list();

            if (ficheros != null) {
                Arrays.sort(ficheros);
                for (String file : ficheros) {
                    comboBox.addItem(file);
                }
            }
        }

        // Acción del botón "Imprimir"
        btnImprimir.addActionListener( e -> {
            String seleccionado = (String) comboBox.getSelectedItem();
            if (seleccionado != null) {
                textArea.setText("Fichero " + seleccionado + " enviado a la cola de impresión");
            } else {
                textArea.setText("No se ha seleccionado ningún fichero.");
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(comboBox);
        panel.add(btnImprimir);
        panel.add(new JScrollPane(textArea));
        frame.add(panel);
        frame.setVisible(true);
    }
}