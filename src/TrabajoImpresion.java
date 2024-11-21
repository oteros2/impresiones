import java.io.File;

public class TrabajoImpresion {
    private final String nombreArchivo;
    private final File archivo;

    public TrabajoImpresion(String nombreArchivo, File archivo) {
        this.nombreArchivo = nombreArchivo;
        this.archivo = archivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public File getArchivo() {
        return archivo;
    }

    @Override
    public String toString() {
        return "TrabajoImpresion{" +
                "nombreArchivo='" + nombreArchivo + '\'' +
                ", archivo=" + archivo +
                '}';
    }
}
