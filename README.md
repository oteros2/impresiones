Sistema de Gestión de Impresión
Este proyecto es una simulación de un sistema de gestión de impresoras utilizando Java y una interfaz gráfica creada con Swing. Permite a los usuarios cargar archivos, enviarlos a una cola de impresión y gestionar múltiples impresoras simultáneamente.

Características
Gestión de cola de impresión:

Los archivos seleccionados por el usuario son enviados a una cola de impresión gestionada de manera concurrente.
Interfaz gráfica intuitiva:

Ventana con botones para seleccionar archivos, iniciar impresiones y detener impresoras.
Vista de progreso para cada impresora, mostrando el estado actual.
Control de impresoras:

Simulación de impresoras que procesan archivos en paralelo.
Límite configurable de impresiones antes de reiniciar cada impresora.
Los botones para detener impresoras se activan solo cuando la impresión está en curso.
Gestión de logs:

Registro de eventos como inicio y finalización de impresiones en un archivo de log.
Requisitos
Antes de ejecutar el proyecto, asegúrate de tener instalado:

Java 8 o superior
Un entorno de desarrollo como IntelliJ IDEA, Visual Studio Code o similar.
Instalación y Ejecución
Clona este repositorio:
git clone https://github.com/tu-usuario/sistema-gestion-impresion.git
Abre el proyecto en el IDE que utilices: Configura el archivo de log:
Configura el archivo de log: El archivo de log se almacena en la ruta log/log.txt. Asegúrate de que la carpeta log existe o créala manualmente.
Ejecuta la clase principal: La clase principal es Main.java.
Usa la interfaz gráfica: Selecciona los archivos que deseas imprimir. Inicia la impresión y gestiona las impresoras a través de los botones.
