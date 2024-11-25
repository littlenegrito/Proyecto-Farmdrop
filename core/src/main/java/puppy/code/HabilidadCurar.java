package puppy.code;

public class HabilidadCurar implements Habilidad {
    // Instancia única de HabilidadCurar
    private static HabilidadCurar instancia;

    // Atributos de la habilidad
    private String nombre = "Curación";
    private int cooldown = 10; // Cooldown en segundos
    private float duracion = 5.0f;
    private String caracteristica = "Recupera una cantidad de vida al tarro.";
    private int cantidadCuracion = 20; // Cantidad de vida que se recupera
    private long lastUsedTime = 0; // Tiempo en que se utilizó la habilidad por última vez

    // Constructor privado para evitar instanciación externa
    private HabilidadCurar() {}
    
    public static HabilidadCurar obtenerInstancia() {
        // Si la instancia es nula, se crea una nueva
        if (instancia == null) {
            instancia = new HabilidadCurar();
        }
        return instancia; // Retorna la instancia
    }

    @Override
    public void usarHabilidad(Tarro tarro) {
        // Lógica para curar al tarro
        int vidaActual = tarro.getVidas();
        int nuevaVida = vidaActual + cantidadCuracion;

        // Limitar la vida máxima a 100
        if (nuevaVida > 100) {
            nuevaVida = 100; // Asegurarse de que no exceda la vida máxima
        }

        tarro.sumarVida(nuevaVida - vidaActual); // Actualizar la vida del tarro
        System.out.println("Usando habilidad: " + nombre + ", recuperando " + cantidadCuracion + " de vida.");
    }
    @Override
    public long getLastUsedTime() {
        return lastUsedTime;
    }
    @Override
    public float getDuration() {
        return duracion;
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