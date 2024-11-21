import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Menu {
    static void createMenu(ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion) {
        JFrame frame = new JFrame("Selector de Fichero");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);  // Tamaño reducido
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        Font font = new Font("SansSerif", Font.BOLD, 18);

        List<File> archivosSeleccionados = new ArrayList<>();

        // Crear botón de selección de archivos
        JButton btnSeleccionar = new JButton("Seleccionar Archivos");
        btnSeleccionar.setPreferredSize(new Dimension(300, 50));
        btnSeleccionar.setFont(font);

        // Crear botón de imprimir
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setPreferredSize(new Dimension(300, 50));
        btnImprimir.setFont(font);

        // Crear área de texto
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setFont(font);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 150));

        scrollTextAraCola.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollTextAraCola.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //boton imprimir
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setPreferredSize(new Dimension(300, 50));
        btnImprimir.setFont(font);

        //area de texto
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setFont(font);
        // Barras de deslizamiento para el text area
        JScrollPane scrollTextArea = new JScrollPane(textArea);
        scrollTextArea.setPreferredSize(new Dimension(600, 150));
        scrollTextArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollTextArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //Accion del boton añadir a la cola
        ArrayList<File> Ficherosencola = new ArrayList<>();
        botonanadirALaCola.addActionListener(e -> {
            //FileChooser
            JFileChooser selectordearchivos = new JFileChooser();
            selectordearchivos.setDialogTitle("Selecciona los archivos que quieran ser añadidos a la cola");
            selectordearchivos.setMultiSelectionEnabled(true);
            selectordearchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int resultado = selectordearchivos.showOpenDialog(frame);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File[] archivosElegidos = selectordearchivos.getSelectedFiles();
                for (File archivo : archivosElegidos) {
                    if (archivo.exists()) {
                        Ficherosencola.add(archivo);
                        textAreaCola.append("-" + archivo.getName() + "\n");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Algun archivo seleccionado no es valido");
                    }
                }
            }
        });
        //Accion del boton imprimir
        btnImprimir.addActionListener(e -> {
            if (!Ficherosencola.isEmpty()) {
                for (File archivo : Ficherosencola) {
                    if (archivo.exists() && archivo.isFile()) {
                        TrabajoImpresion trabajo = new TrabajoImpresion(archivo.getName(), archivo);
                        colaDeImpresion.add(trabajo);
                        textArea.append("Se ha añadido a la cola de impresión: " + trabajo + "\n");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No hay archivos en la lista para imprimir.");
            }
            JOptionPane.showMessageDialog(frame, "Todos los archivos se han enviado a la cola de impresión.");
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(2, 100));
        panel.add(titulo);
        panel.add(botonanadirALaCola);
        panel.add(scrollTextAraCola);
        panel.add(separator);
        panel.add(btnImprimir);
        panel.add(separator);
        panel.add(scrollTextArea);
        frame.add(panel);
        frame.setVisible(true);
    }
}