package puppy.code;

public class HabilidadDash implements Habilidad {
    private static HabilidadDash instancia;
    private String nombre = "Dash";
    private int cooldown = 3; // Cooldown en segundos
    private String caracteristica = "Permite al tarro esquivar peligros rápidamente.";

    private HabilidadDash() {}

    public static HabilidadDash obtenerInstancia() {
        if (instancia == null) {
            instancia = new HabilidadDash();
        }
        return instancia;
    }

    @Override
    public void usarHabilidad(Tarro tarro) {
        // Lógica para realizar el dash
        int dashDistance = 100; // Distancia del dash
        if (tarro.getVelx() > 0) { // Si se mueve hacia la derecha
            tarro.getArea().x += dashDistance; // Mover hacia la derecha
        } else { // Si se mueve hacia la izquierda
            tarro.getArea().x -= dashDistance; // Mover hacia la izquierda
        }
        System.out.println("Usando habilidad: " + nombre);
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
    public String obtenerCaracteristica() {
        return caracteristica;
    }
    
}
