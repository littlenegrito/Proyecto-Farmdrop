/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package puppy.code;

/**
 *
 * @author Acer
 */
public interface Habilidad {
    void usarHabilidad(Tarro tarro);
    String obtenerNombre();
    int  obtenerCooldown();
    public float getDuration();
    String obtenerCaracteristica();
    long getLastUsedTime();
}
