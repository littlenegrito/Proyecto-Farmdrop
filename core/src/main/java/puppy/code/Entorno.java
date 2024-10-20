/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Entorno {
    private Array<Elemento> elementos;
    private long lastDropTime;
}
 public void crear() {
        elementos.clear();
        crearElemento();
 }
private void crearElemento() {
        Elemento nuevoElemento;
        // Generar aleatoriamente un elemento bueno o malo
        if (MathUtils.random(1, 10) < 5) {
            nuevoElemento = new ElementoPeligroso(new Texture("gotaMala.png"), 300); // elemento peligroso
        } else {
            nuevoElemento = new ElementoBeneficioso(new Texture("gotaBuena.png"), 300); // elemento benéfico
        }
        elementos.add(nuevoElemento);
        lastDropTime = TimeUtils.nanoTime();
 }
public void actualizar(float deltaTime, Tarro tarro) {
        for (int i = 0; i < elementos.size; i++) {
            Elemento elemento = elementos.get(i);
            
            // Mover el elemento
            elemento.moverElemento(deltaTime);

            // Verificar si el elemento fue recogido
            if (elemento.esRecogido(tarro.getArea())) {
                elemento.activarEfecto(tarro);
                elementos.removeIndex(i);  // Eliminar el elemento si fue recogido
                i--;  // Ajustar el índice ya que eliminamos un elemento
            } else if (!elemento.estaEnPantalla()) {
                elemento.desaparecer();
                elementos.removeIndex(i);  // Eliminar el elemento si salió de pantalla
                i--;
            }
        }
    }

    public void dibujarElementos(SpriteBatch batch) {
        for (Elemento elemento : elementos) {
            elemento.dibujar(batch);
        }
    }

    public void pausarElementos() {
        for (Elemento elemento : elementos) {
            elemento.detenerMovimiento();
        }
    }
    public void destruir() {
        // Aquí podrías liberar recursos si es necesario (texturas, sonidos, etc.)
    }
