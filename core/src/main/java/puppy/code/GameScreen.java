package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Tarro tarro;
    private Entorno entorno;
    private Lluvia lluvia;
    
    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

        // Inicializar sonidos
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        
        System.out.println("Regiones en el atlas:");
        for (TextureAtlas.AtlasRegion region : game.getAtlas().getRegions()) {
            System.out.println(region.name);
        }
        

        // Crear tarro del jugador
        tarro = new Tarro(game.getAtlas().findRegion("basket"), hurtSound);
        
        //TextureRegion gota = game.getAtlas().findRegion("drop");
        //TextureRegion gotaMala = game.getAtlas().findRegion("dropBad");
        
        // Crear el entorno de juego (elementos como frutas, peligros, etc.)
        //lluvia = new Lluvia(gota, gotaMala, dropSound, rainMusic);
        entorno = new Entorno(game.getAtlas(),dropSound, rainMusic);

        // camera
	camera = new OrthographicCamera();
	batch = new SpriteBatch();
        
        // creacion de objetos
        tarro.crear();
        //lluvia.crear();
        entorno.crear();
    }

    @Override
    public void render(float delta) {
        // Limpiar la pantalla
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        //actualizar 
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // Dibujar la puntuación y vidas
        font.draw(batch, "Puntos totales: " + tarro.getPuntos(), 5, camera.viewportHeight - 5);
        font.draw(batch, "Vida : " + tarro.getVidas(), camera.viewportWidth - 130, camera.viewportHeight - 5);
        font.draw(batch, "HighScore : " + game.getHigherScore(), camera.viewportWidth / 2 - 50, camera.viewportHeight - 5);

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
        //lluvia.actualizarDibujoLluvia(batch);
        entorno.actualizarDibujo(batch);

        batch.end();
    }
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }
    @Override
    public void show() {
        //lluvia.continuar();
        entorno.continuar();
    }
    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        //lluvia.pausar();
        entorno.pausar();
        game.setScreen(new PausaScreen(game, this));
    }
    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        tarro.destruir();
        //lluvia.destruir();
        entorno.destruir();
    }

}
