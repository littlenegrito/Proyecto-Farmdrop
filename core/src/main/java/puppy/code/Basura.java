/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;

public class Basura extends Peligros {
    public Basura() {
        super(100); // Instakill
    }

    @Override
    public void activarEfecto(Tarro tarro) {
        //tarro.instakill(); // Si hay un método específico para el instakill
    }
}
