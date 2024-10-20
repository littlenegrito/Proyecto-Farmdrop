/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Peligros implements Elemento {
     protected int daño;
     protected Texture textura;
     protected Rectangle area;
     protected int velocidad;

    public Peligros(Texture textura, int velocidad) {
        this.textura = textura;
        this.velocidad = velocidad;
        this.area = new Rectangle(MathUtils.random(0, 800 - 64), 480, 64, 64);
    }

    @Override
    public void activarEfecto(Tarro tarro) {
        //tarro.dañar(daño);
    }
    @Override
    public void moverElemento(float deltaTime) {
        area.y -= velocidad * deltaTime;
    }

    @Override
    public boolean esRecogido(Rectangle tarroArea) {
        return area.overlaps(tarroArea);
    }

    @Override
    public boolean estaEnPantalla() {
        return area.y + 64 >= 0;
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }

    @Override
    public void desaparecer() {
        // Puedes implementar la lógica de eliminar o reciclar el elemento si es necesario.
    }

    @Override
    public void detenerMovimiento() {
        // Implementar si necesitas pausar el movimiento
    }
}
