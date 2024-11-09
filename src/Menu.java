import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class Menu {
    static void createMenu() {
        //marco de la ventana
        JFrame frame = new JFrame("Selector de Fichero");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        Font font = new Font("SansSerif", Font.BOLD, 18);

        //desplegable
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(500, 50));
        comboBox.setFont(font);

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
        btnImprimir.addActionListener(e -> {
            String seleccionado = (String) comboBox.getSelectedItem();
            if (seleccionado != null) {
                textArea.setText("Fichero " + seleccionado + " enviado a la cola de impresión");
            } else {
                textArea.setText("No se ha seleccionado ningún fichero.");
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(2,100));
        panel.add(comboBox);
        panel.add(separator);
        panel.add(btnImprimir);
        panel.add(separator);
        panel.add(textArea);
        frame.add(panel);
        frame.setVisible(true);
    }
}