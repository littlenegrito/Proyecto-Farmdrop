package puppy.code;

public class HabilidadDash implements Habilidad {
    private static HabilidadDash instancia;
    private String nombre = "Dash";
    private int cooldown = 3; // Cooldown en segundos
    private String caracteristica = "Permite al tarro esquivar peligros rápidamente.";
    private long lastUsedTime = 0; // Para controlar el cooldown
    private int dashDirection;

    private HabilidadDash() {}

    public static HabilidadDash obtenerInstancia() {
        if (instancia == null) {
            instancia = new HabilidadDash();
        }
        return instancia;
    }

    @Override
    public void usarHabilidad(Tarro tarro) {
        long currentTime = System.currentTimeMillis();
        
        // Verificar si el cooldown ha pasado
        if (currentTime - lastUsedTime < cooldown * 1000) {
            System.out.println("Habilidad en cooldown. Espera " + ((cooldown * 1000 - (currentTime - lastUsedTime)) / 1000) + " segundos.");
            return;
        }

        // Lógica para realizar el dash
        int dashDistance = 200; // Distancia del dash
        if (dashDirection == 1) {
            tarro.getArea().x += dashDistance; // Mover hacia la derecha
        } else if (dashDirection == -1) {
            tarro.getArea().x -= dashDistance; // Mover hacia la izquierda
        }

        lastUsedTime = currentTime; // Actualizar el tiempo de uso
        System.out.println("Usando habilidad: " + nombre);
    }
    public void setDashDirection(int direction) {
        this.dashDirection = direction; // 1 para derecha, -1 para izquierda
    }
    @Override
    public long getLastUsedTime() {
        return lastUsedTime;
    }

    @Override
    public String obtenerNombre() {
        return nombre;
    }

    @Override
    public int obtenerCooldown() {
        return cooldown;
    }
    @Override
    public float getDuration() {
        return 0;
    }

    @Override
    public String obtenerCaracteristica() {
        return caracteristica;
    }
}