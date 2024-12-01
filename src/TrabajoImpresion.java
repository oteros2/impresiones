import java.io.File;

/**
 * En la clase de trabajo de impresion
 */
public class TrabajoImpresion {
    private final String nombreArchivo; // Nombre del archivo a imprimir
    private final File archivo; // Archivo físico a imprimir
    /**
     * Constructor que inicializa los atributos del trabajo de impresión.
     *
     * @param nombreArchivo Nombre del archivo a imprimir.
     * @param archivo       Archivo físico que se va a imprimir.
     */
    public TrabajoImpresion(String nombreArchivo, File archivo) {
        this.nombreArchivo = nombreArchivo;
        this.archivo = archivo;
    }

    /**
     * @return El nombre del archivo.
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @return El archivo en cuestion.
     */
    public File getArchivo() {
        return archivo;
    }

    /**
     * Representación en formato String del trabajo de impresión.
     *
     * @return Información del trabajo de impresión.
     */
    @Override
    public String toString() {
        return "TrabajoImpresion{" +
                "nombreArchivo='" + nombreArchivo + '\'' +
                ", archivo=" + archivo +
                '}';
    }
}
