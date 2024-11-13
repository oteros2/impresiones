public class TrabajoImpresion {
    private String nombreDocumento;

    public TrabajoImpresion(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    @Override
    public String toString() {
        return "Trabajo de impresión: " + nombreDocumento;
    }
}
