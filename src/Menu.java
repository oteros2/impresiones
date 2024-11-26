import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Menu {
    static void createMenu(ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion, BufferedWriter bw) {
        // Crear el marco de la ventana
        JFrame frame = new JFrame("Selector de Fichero");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        Font font = new Font("SansSerif", Font.BOLD, 18);

        // Crear lista temporal para almacenar archivos seleccionados
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

        // Acción del botón "Seleccionar Archivos"
        btnSeleccionar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            // Permite selección múltiple
            fileChooser.setMultiSelectionEnabled(true);
            // Permite la selección solo de archivos
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                //Crea una array donde almacena los ficheros seleccionados
                File[] selectedFiles = fileChooser.getSelectedFiles();
                archivosSeleccionados.clear(); // Limpiar la lista temporal
                textArea.setText(""); // Limpiar el área de texto
                for (File file : selectedFiles) {
                    archivosSeleccionados.add(file); // Almacenar en la lista temporal
                    textArea.append("Archivo seleccionado: " + file.getName() + "\n");
                }
            } else {
                textArea.setText("Selección cancelada.\n");
            }
        });

        // Acción del botón "Imprimir"
        btnImprimir.addActionListener(e -> {
            if (!archivosSeleccionados.isEmpty()) {
                for (File archivo : archivosSeleccionados) {
                    // Enviar a la cola
                    colaDeImpresion.add(new TrabajoImpresion(archivo.getName(), archivo));
                    textArea.append("Archivo enviado a la cola de impresión: " + archivo.getName() + "\n");
                    try {
                        bw.write(new Date() + " Enviado a impresión: " + archivo.getAbsolutePath() + "\n");
                        bw.flush();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("Enviado a impresión: " + archivo.getAbsolutePath());
                }
                archivosSeleccionados.clear(); // Limpiar la lista temporal tras enviar los archivos a la cola
            } else {
                textArea.append("No hay archivos seleccionados para imprimir.\n");
            }
        });

        // Crear el panel y añadir los componentes
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.add(btnSeleccionar);
        panel.add(btnImprimir);
        panel.add(scrollPane);

        // Añadir el panel al marco
        frame.add(panel);
        frame.setVisible(true);
    }
}