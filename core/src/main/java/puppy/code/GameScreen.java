package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Tarro tarro;
    private Entorno entorno;
    
    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

        // Inicializar sonidos
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // Crear tarro del jugador
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), hurtSound);
        tarro.crear();

        // Crear el entorno de juego (elementos como frutas, peligros, etc.)
        entorno = new Entorno(tarro, dropSound, rainMusic);
        entorno.crear();

        // Configurar la cámara
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        // Limpiar la pantalla
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibujar la puntuación y vidas
        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 670, 475);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

        if (!tarro.estaHerido()) {
            // Actualizar movimiento del tarro
            tarro.actualizarMovimiento();

            // Actualizar el movimiento de los elementos en el entorno
            if (!entorno.actualizarMovimiento(tarro)) {
                if (game.getHigherScore() < tarro.getPuntos()) {
                    game.setHigherScore(tarro.getPuntos());
                }
                game.setScreen(new GameOverScreen(game));
                dispose();
            }
        }

        // Dibujar el tarro y los elementos
        tarro.dibujar(batch);
        entorno.actualizarDibujo(batch);

        batch.end();
    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void show() {
        entorno.continuar();
    }
    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        entorno.pausar();
        game.setScreen(new PausaScreen(game, this));
    }
    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        tarro.destruir();
        entorno.destruir();
    }

}
