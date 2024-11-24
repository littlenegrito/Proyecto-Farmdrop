package puppy.code;

public class HabilidadEscudo implements Habilidad {
    // Instancia única de HabilidadEscudo
    private static HabilidadEscudo instancia;

    // Atributos de la habilidad
    private String nombre = "Escudo";
    private int cooldown = 30; // Cooldown en segundos
    private String caracteristica = "Bloquea el siguiente daño recibido durante 3 segundos.";
    private long lastUsedTime = 0; // Tiempo de la última utilización
    private long duration = 3000; // Duración del escudo en milisegundos
    private boolean isActive = false; // Estado del escudo

    // Constructor privado para evitar instanciación externa
    private HabilidadEscudo() {}

    public static HabilidadEscudo obtenerInstancia() {
        if (instancia == null) {
            instancia = new HabilidadEscudo();
        }
        return instancia; // Retorna la instancia
    }

    @Override
    public void usarHabilidad(Tarro tarro) {
        long currentTime = System.currentTimeMillis();
        
        // Verificar si el cooldown ha pasado
        if (currentTime - lastUsedTime < cooldown * 1000) {
            System.out.println("Habilidad en cooldown. Espera " + ((cooldown * 1000 - (currentTime - lastUsedTime)) / 1000) + " segundos.");
            return;
        }

        // Activar la habilidad
        isActive = true;
        lastUsedTime = currentTime; // Actualizar el tiempo de uso
        System.out.println("Usando habilidad: " + nombre + ". El escudo está activo por " + (duration / 1000) + " segundos.");

        // Crear un nuevo hilo para manejar la duración del escudo
        new Thread(() -> {
            try {
                Thread.sleep(duration); // Espera el tiempo de duración
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isActive = false; // Desactivar el escudo después de la duración
            System.out.println("El escudo ha expirado.");
        }).start();
    }

    public boolean isEscudoActivo() {
        return isActive; // Método para verificar si el escudo está activo
    }

    @Override
    public long getLastUsedTime() {
        return lastUsedTime;
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