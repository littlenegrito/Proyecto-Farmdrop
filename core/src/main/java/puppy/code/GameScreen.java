package puppy.code;

import com.badlogic.gdx.Gdx;
import java.util.HashMap;
import java.util.Map;
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
    private Sound hurtSound;
    private Sound dropSound;
    private Music rainMusic;
    private Texture backgroundTexture;
    private Texture bannerTexture;
    private TextureRegion difficultyIcon, healingIcon;
    private TextureRegion healIcon, healIconDeactivated, dashIcon,dashIconDeactivated, shieldIcon,shieldIconDeactivated, boostIcon, boostIconDeactivated;
    private Map<Integer, Long> cooldowns = new HashMap<>();
    private boolean mostrarDificultadIcon = false;
    private long tiempoInicioDificultadIcon = 0;
    private boolean mostrarHealIcon = false;
    private long tiempoInicioHealIcon = 0;
    private static final long DURACION_ICONO = 1000;
    private static final long DURACION_ICONO_DIFICULTAD = 1750;
    
    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        
        backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
        bannerTexture = new Texture(Gdx.files.internal("banner.png"));

        // Inicializar sonidos
        cargarElementos();
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
            // Verificar si la dificultad aumentó y mostrar ícono
            if (entorno.actualizarDificultad(tarro.getPuntos())) {
                mostrarDificultadIcon = true;
                tiempoInicioDificultadIcon = System.currentTimeMillis(); // Registrar el tiempo actual
            }
            
            if (mostrarDificultadIcon) {
               if(!drawIconDuration(batch, difficultyIcon, tiempoInicioDificultadIcon, DURACION_ICONO_DIFICULTAD, 1920 / 2, 1080 / 2, 2.5f)){
                   mostrarDificultadIcon = false;
               }
            }
        }
        // Manejar entrada de habilidades
        handleInput();
        if (mostrarHealIcon) {
            if (!drawIconDuration(batch, healingIcon, tiempoInicioHealIcon, DURACION_ICONO, 1920 / 2, (1080 / 4), 5.0f)) {
                mostrarHealIcon = false; // Después de que la duración pase, ponerlo en false
            }
        }
        //if (mostrarBoostIcon) drawIconWDuration(batch, boostIcon, tiempoInicioBoostIcon, DURACION_ICONO_MS, 1920 / 2 + 100, 1080 / 2);
        //if (mostrarShieldIcon) drawIconDuration(batch, shieldIcon, tiempoInicioShieldIcon, DURACION_ICONO_MS, 1920 / 2, 1080 / 2 - 100);
        // Dibujar el tarro y los elementos
        tarro.dibujar(batch);
        entorno.actualizarDibujo(batch);
        
        batch.draw(bannerTexture, 0, -20, 1920, 1080);
        font.getData().setScale(2.25f, 2.25f); // Cambiar tamaño del texto
        
         // Determinar y dibujar los íconos de habilidades (normales o desactivados)
        long currentTime = System.currentTimeMillis();
        // Dibujar los iconos
        drawSkillIcons(currentTime);
        // Mostrar cooldown de la habilidad activa
        if (tarro.getHabilidadActual() != null && currentTime - tarro.getHabilidadActual().getLastUsedTime() < tarro.getHabilidadActual().obtenerCooldown() * 1000) {
            float timeRemaining = tarro.getHabilidadActual().obtenerCooldown() - (currentTime - tarro.getHabilidadActual().getLastUsedTime()) / 1000.0f;
            font.draw(batch, "Cooldown: " + Math.ceil(timeRemaining) + "s", 250, camera.viewportHeight - 125);
        }
      
        dibujarTexto();
        batch.end();
    }
   // Nueva función para manejar el dibujo de íconos de habilidades
    private void drawSkillIcons(long currentTime) {
        drawSingleSkillIcon(batch, dashIcon, dashIconDeactivated, currentTime, cooldowns.getOrDefault(Input.Keys.Q, 0L), 805, HabilidadDash.obtenerInstancia().obtenerCooldown());
        drawSingleSkillIcon(batch, boostIcon, boostIconDeactivated, currentTime, cooldowns.getOrDefault(Input.Keys.W, 0L), 970, HabilidadBoostPuntaje.obtenerInstancia().obtenerCooldown());
        drawSingleSkillIcon(batch, healIcon, healIconDeactivated, currentTime, cooldowns.getOrDefault(Input.Keys.E, 0L), 1135, HabilidadCurar.obtenerInstancia().obtenerCooldown());
        drawSingleSkillIcon(batch, shieldIcon, shieldIconDeactivated, currentTime, cooldowns.getOrDefault(Input.Keys.R, 0L), 1300, HabilidadEscudo.obtenerInstancia().obtenerCooldown());
    }
   private void drawSingleSkillIcon(SpriteBatch batch, TextureRegion activeIcon, TextureRegion deactivatedIcon, long currentTime, long lastUsedTime, int xPosition, float cooldownDuration) {
        boolean isOnCooldown = (currentTime - lastUsedTime) < cooldownDuration * 1000;
        int iconWidth = 150;
        int iconHeight = 150;
        float yPosition = camera.viewportHeight - 155;
        batch.draw(isOnCooldown ? deactivatedIcon : activeIcon, xPosition, yPosition, iconWidth, iconHeight);
    }
   private boolean drawIconDuration(SpriteBatch batch, TextureRegion icon, long tiempoInicio, long duracion, int xPosition, int yPosition, float scale) {
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
        if (tiempoTranscurrido < duracion) {
            // Ajustar el tamaño del ícono dependiendo del scaleFactor
            float iconWidth = icon.getRegionWidth() / scale;
            float iconHeight = icon.getRegionHeight() / scale;

            // Dibujar el ícono centrado en la posición dada
            batch.draw(icon, xPosition - iconWidth / 2, yPosition - iconHeight / 2, iconWidth, iconHeight);
            return true;
        }
        return false;
    }
    // Función para manejar entrada de habilidades
    private void handleInput(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) { // Instancia de DASH
            tarro.establecerHabilidad(HabilidadDash.obtenerInstancia());
            cooldowns.put(Input.Keys.Q, System.currentTimeMillis()); 
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                ((HabilidadDash) tarro.getHabilidadActual()).setDashDirection(-1);
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                ((HabilidadDash) tarro.getHabilidadActual()).setDashDirection(1);
            }
            tarro.getHabilidadActual().usarHabilidad(tarro);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) { // Instancia de BOOST
            tarro.establecerHabilidad(HabilidadBoostPuntaje.obtenerInstancia());
            tarro.usarHabilidad();
            cooldowns.put(Input.Keys.W, System.currentTimeMillis());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) { // Instancia de HEAL
            tarro.establecerHabilidad(HabilidadCurar.obtenerInstancia());
            tarro.usarHabilidad();
            cooldowns.put(Input.Keys.E, System.currentTimeMillis());
            
            // Activar la visualización del ícono de curación
            mostrarHealIcon = true;
            tiempoInicioHealIcon = System.currentTimeMillis();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { // Instancia de SHIELD
            tarro.establecerHabilidad(HabilidadEscudo.obtenerInstancia());
            tarro.usarHabilidad();
            cooldowns.put(Input.Keys.R, System.currentTimeMillis());
        }
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
    private void dibujarTexto(){
        // Mostrar teclas de habilidades
        font.draw(batch, "Q", 865, camera.viewportHeight - 160);
        font.draw(batch, "W", 1030, camera.viewportHeight - 160);
        font.draw(batch, "E", 1195, camera.viewportHeight - 160);
        font.draw(batch, "R", 1365, camera.viewportHeight - 160);
        
        // Dibujar la puntuación y vidas
        font.draw(batch, "" + tarro.getPuntos(), 200, camera.viewportHeight - 37);
        font.draw(batch, "" + tarro.getVidas(), camera.viewportWidth - 160, camera.viewportHeight - 37);
        font.draw(batch, "" + game.getHigherScore(), 620, camera.viewportHeight - 37);
    }
    private void cargarElementos(){
        System.out.println("Regiones en el atlas:");
        for (TextureAtlas.AtlasRegion region : game.getAtlas().getRegions()) {
            System.out.println(region.name);
        }
        hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.MP3"));
        difficultyIcon = game.getAtlas().findRegion("difficulty icon");
        healingIcon = game.getAtlas().findRegion("healing icon");
        healIcon = game.getAtlas().findRegion("heal icon");
        healIconDeactivated = game.getAtlas().findRegion("health icon deactivated");
        dashIcon = game.getAtlas().findRegion("dash icon");
        dashIconDeactivated = game.getAtlas().findRegion("dash icon deactivated");
        shieldIcon = game.getAtlas().findRegion("shield icon");
        shieldIconDeactivated = game.getAtlas().findRegion("shield icon deactivated");
        boostIcon = game.getAtlas().findRegion("boost icon");
        boostIconDeactivated = game.getAtlas().findRegion("boost icon deactivated");
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

