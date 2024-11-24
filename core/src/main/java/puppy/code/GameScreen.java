package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen, InputProcessor {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Tarro tarro;
    private Entorno entorno;
    private Lluvia lluvia;
    private Texture backgroundTexture;
    private Texture bannerTexture;
    
    
    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        
        backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
        bannerTexture = new Texture(Gdx.files.internal("banner.png"));

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
  
        // Crear el entorno de juego (elementos como frutas, peligros, etc.)
        entorno = new Entorno(game.getAtlas(),dropSound, rainMusic);

        // camera
	camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        batch = new SpriteBatch();
    
        tarro.crear();
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
        
         // Dibujar el fondo antes de otros elementos
        batch.draw(backgroundTexture, 0, 0, 1920, 1080);
        

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
            // Llamar a actualizarDificultad con los puntos actuales del tarro
            entorno.actualizarDificultad(tarro.getPuntos());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            tarro.establecerHabilidad(HabilidadDash.obtenerInstancia());
        
            // Determinar la dirección del Dash
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                ((HabilidadDash) tarro.getHabilidadActual()).setDashDirection(-1); // Mueve a la izquierda
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                ((HabilidadDash) tarro.getHabilidadActual()).setDashDirection(1);  // Mueve a la derecha
            }
        
            tarro.getHabilidadActual().usarHabilidad(tarro); // Usar la habilidad
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            tarro.establecerHabilidad(HabilidadCurar.obtenerInstancia()); // Establecer la habilidad de curar
            tarro.usarHabilidad(); 
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            tarro.establecerHabilidad(HabilidadBoostPuntaje.obtenerInstancia()); // Establecer la habilidad Boost de Puntaje
            tarro.usarHabilidad();
        }
        
        // Mostrar cooldown de la habilidad activa
        long currentTime = System.currentTimeMillis();
        if (tarro.getHabilidadActual() != null && currentTime - tarro.getHabilidadActual().getLastUsedTime() < tarro.getHabilidadActual().obtenerCooldown() * 1000) {
            float timeRemaining = tarro.getHabilidadActual().obtenerCooldown() - (currentTime - tarro.getHabilidadActual().getLastUsedTime()) / 1000.0f;
            font.draw(batch, "Cooldown: " + Math.ceil(timeRemaining) + "s", 5, camera.viewportHeight - 25);
        }
    
        // Dibujar el tarro y los elementos
        tarro.dibujar(batch);
        entorno.actualizarDibujo(batch);
        
        batch.draw(bannerTexture, 0, -20, 1920, 1080);
        
        font.getData().setScale(2.25f, 2.25f); // Cambiar tamaño del texto
        
        // Dibujar la puntuación y vidas
        font.draw(batch, "" + tarro.getPuntos(), 200, camera.viewportHeight - 37);
        font.draw(batch, "" + tarro.getVidas(), camera.viewportWidth - 160, camera.viewportHeight - 37);
        font.draw(batch, "" + game.getHigherScore(), 620, camera.viewportHeight - 37);

        batch.end();
    }
    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }
    @Override
    public void show() {
        entorno.continuar();
        Gdx.input.setInputProcessor(this);  // Activa el InputProcessor de la pantalla de juego
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
        backgroundTexture.dispose();
        bannerTexture.dispose();
    }
    
        // Métodos no utilizados de InputProcessor
        @Override public boolean keyDown(int keycode) { return false; }
        @Override public boolean keyUp(int keycode) { return false; }
        @Override public boolean keyTyped(char character) { return false; }
        @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
        @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
        @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
        @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
        @Override public boolean scrolled(float amountX, float amountY) { return false; }
        @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }

}

