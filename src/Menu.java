import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Menu {

    static void createMenu(ConcurrentLinkedQueue<TrabajoImpresion> colaDeImpresion, BufferedWriter bw, int numeroImpresoras) {
        //definir la cantidad de hilos que vamos a usar
        Thread[] impresoraHilos = new Thread[numeroImpresoras];
        //definir la cantidad de impresoras que vamos a usar
        Impresoras[] impresoras = new Impresoras[numeroImpresoras];
        // Crear el marco de la ventana
        JFrame frame = new JFrame("Selector de Fichero");
        JTextArea[] textAreas = new JTextArea[numeroImpresoras];
        JButton[] buttons = new JButton[numeroImpresoras];
        Font font = new Font("SansSerif", Font.BOLD, 15);
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
        scrollPane.setPreferredSize(new Dimension(500, 150));
        // Crear el panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        // Crear el panel superior con los botones seleccionar archivo e imprimir
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelSuperior.add(btnSeleccionar);
        panelSuperior.add(btnImprimir);

        // Crear el panel central con el textarea principal
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setPreferredSize(new Dimension(800, 200)); // ajustar el tamaño del panel
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        // Crear el panel inferior con los textareas de las impresoras y los botones de detener
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());
        panelInferior.setPreferredSize(new Dimension(800, 600));

        JPanel panelImpresoras = new JPanel();
        panelImpresoras.setLayout(new GridLayout(1, numeroImpresoras)); // 1 fila y numeroImpresoras columnas

        for (int i = 0; i < numeroImpresoras; i++) {
            JPanel panelImpresora = new JPanel();
            panelImpresora.setLayout(new BorderLayout());
            textAreas[i] = new JTextArea();
            textAreas[i].setEditable(false);
            textAreas[i].setBackground(Color.LIGHT_GRAY);
            textAreas[i].setFont(font);
            JScrollPane scrollPane2 = new JScrollPane(textAreas[i]);
            scrollPane2.setPreferredSize(new Dimension(500, 150));
            textAreas[i].append("Impresora " + (i + 1) + "\n");
            panelImpresora.add(scrollPane2, BorderLayout.CENTER);

            buttons[i] = new JButton("Detener Impresora " + (i + 1));
            buttons[i].setPreferredSize(new Dimension(100, 50));
            buttons[i].setFont(font);
            buttons[i].setBackground(Color.RED);
            panelImpresora.add(buttons[i], BorderLayout.SOUTH);
            panelImpresoras.add(panelImpresora);
        }

        panelInferior.add(panelImpresoras, BorderLayout.CENTER);

        // Agregar los paneles al panel principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        // Añadir el panel principal al marco
        frame.add(panelPrincipal);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

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
                }
                archivosSeleccionados.clear(); // Limpiar la lista temporal tras enviar los archivos a la cola
                int[] precios = new int[numeroImpresoras];

                //Genera los precios aleatorios
                for (int i = 0; i < precios.length; i++) {
                    precios[i] = (int) Math.floor(Math.random() * 451 + 50);
                }

                //Crea las impresoras
                for (int i = 0; i < numeroImpresoras; i++) {
                    impresoras[i] = new Impresoras(colaDeImpresion, (int) Math.floor(Math.random() * (6 - 2) + 1), bw, precios[i], textAreas[i]);
                }

                //Crea los hilos y los ejecuta
                for (int i = 0; i < impresoraHilos.length; i++) {
                    int numeroImpresora = i + 1;
                    impresoraHilos[i] = new Thread(impresoras[i], "Impresora " + numeroImpresora);
                    if (impresoras[i].getPrecio() < 200) {
                        impresoraHilos[i].setPriority(Thread.MAX_PRIORITY);
                    } else {
                        impresoraHilos[i].setPriority(Thread.MIN_PRIORITY);
                    }
                    impresoraHilos[i].start();
                    buttons[i].setBackground(Color.GREEN);
                }
            } else {
                textArea.append("No hay archivos seleccionados para imprimir.\n");
            }
        });
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].addActionListener(e -> {
                if (impresoraHilos[index].isAlive()) {
                    impresoraHilos[index].interrupt();
                    textAreas[index].append("Impresora " + buttons[index].getText() + " ha sido detenida.\n");
                    buttons[index].setBackground(Color.RED);
                } else {
                    impresoraHilos[index] = new Thread(impresoras[index], "Impresora " + (index + 1));
                    impresoraHilos[index].start();
                    textAreas[index].append("Impresora " + buttons[index].getText() + " ha sido iniciada.\n");
                    buttons[index].setBackground(Color.GREEN);
                }
            });
        }

    }
}