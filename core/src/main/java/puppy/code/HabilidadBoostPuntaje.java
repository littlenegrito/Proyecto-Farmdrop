package puppy.code;

public class HabilidadBoostPuntaje implements Habilidad {
    // Instancia única de HabilidadBoostPuntaje
    private static HabilidadBoostPuntaje instancia;

    // Atributos de la habilidad
    private String nombre = "Boost de Puntaje";
    private int cooldown = 10; // Cooldown en segundos
    private String caracteristica = "Aumenta temporalmente los puntos obtenidos.";
    private float duracion = 5.0f; // Duración del aumento en segundos
    private float factorAumento = 2.0f; // Factor por el que se aumentarán los puntos

    // Constructor privado para evitar instanciación externa
    private HabilidadBoostPuntaje() {}
    public static HabilidadBoostPuntaje obtenerInstancia() {
        // Si la instancia es nula, se crea una nueva
        if (instancia == null) {
            instancia = new HabilidadBoostPuntaje();
        }
        return instancia; // Retorna la instancia
    }

    @Override
    public void usarHabilidad(Tarro tarro) {
        // Lógica para aumentar el puntaje
        System.out.println("Usando habilidad: " + nombre + ", los puntos obtenidos se multiplicarán por " + factorAumento + " durante " + duracion + " segundos.");

        // Aumentar el puntaje temporalmente
        tarro.setFactorPuntaje(factorAumento); // Método que debes implementar en Tarro

        // Aquí puedes usar un temporizador para revertir el aumento después de la duración
        new Thread(() -> {
            try {
                Thread.sleep((long) (duracion * 1000)); // Espera el tiempo de duración
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tarro.resetFactorPuntaje(); // Restaura el factor de puntaje original
            System.out.println("El boost de puntaje ha terminado.");
        }).start();
    }

    @Override
    public String obtenerNombre() {
        return nombre; // Retorna el nombre de la habilidad
    }

    @Override
    public int obtenerCooldown() {
        return cooldown; // Retorna el cooldown de la habilidad
    }

    @Override
    public String obtenerCaracteristica() {
        return caracteristica; // Retorna la característica de la habilidad
    }
}
