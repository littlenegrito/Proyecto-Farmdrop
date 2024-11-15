  # FarmDrop

## Descripción del Proyecto:

Farm Drop es un juego de recolección de recursos en el que el jugador controla a una granjera que se desplaza lateralmente para recolectar frutas y vegetales, mientras esquiva comidas chatarra y basura. A medida que el juego avanza, la dificultad aumenta gradualmente con la aparición de más peligros y una mayor velocidad de caída. El objetivo es acumular suficiente puntuación para completar cada nivel y progresar, manteniendo la barra de vida activa para no perder el progreso.

### Objetivo del Juego:

1. Alcanzar el puntaje necesario para avanzar de nivel.
2. Evitar la comida chatarra y basura que reducen la vida del jugador.
3. Mantener la barra de vida sobre cero para completar niveles y alcanzar el mayor puntaje posible.

### Mecánicas Principales

1. Movimiento Lateral: Mueve la granjera de izquierda a derecha con las teclas de dirección para recolectar recursos que caen desde la parte superior de la pantalla.
2. Contador de Puntuación: Recolectar frutas y vegetales suma puntos que avanzan el progreso del nivel.
3. Barra de Vida: Disminuye al recolectar alimentos dañinos (comida chatarra o basura). Cuando la vida llega a cero, el nivel se reinicia.
4. Alimentos y Peligros:
   
   Alimentos: Frutas y vegetales que aumentan el puntaje y, en algunos casos, restauran algo de vida.
   
   Peligros: Comida chatarra y basura que reducen la vida al ser recolectados. La frecuencia y velocidad de aparición de estos elementos aumenta en niveles más avanzados.
5. Progreso y Dificultad Dinámica: Cada nivel incrementa la dificultad con mayor frecuencia y velocidad de aparición de peligros y mayor puntaje necesario para avanzar.

### Controles

Movimiento: Usa las flechas de dirección ← y → para mover a la granjera.

Habilidades (No implementadas en esta versión):
  1. Tecla Q: Habilidad 1
  2. Tecla W: Habilidad 2
  3. Tecla E: Habilidad 3
  4. Tecla R: Habilidad 4

## Estructura del Proyecto
Arquitectura General
El código sigue una estructura de clases para organizar los elementos y funcionalidades del juego:

Clases Base y Herencia:

1. Alimentos: Clase abstracta para los elementos recolectables que benefician al jugador (Fruta, Vegetal). Permite definir comportamientos y efectos según el tipo de recurso.
2. Peligros: Clase abstracta para elementos dañinos que reducen la vida (Comida chatarra y Basura).
   
### Elementos del Juego:

1. Entorno: Clase encargada de generar y gestionar los objetos en caída, adaptando los elementos y su velocidad según el nivel.
2. Interfaz de Usuario: Incluye el puntaje, barra de vida y otras métricas visibles en pantalla.
   
### Gestión de Pantallas:

-MainMenuScreen, GameScreen y GameOverScreen: Clases que gestionan el menú principal, la pantalla de juego y el game over, respectivamente.

### Dificultad y Progresión:

La dificultad aumenta automáticamente conforme se avanza de nivel. Esto incluye la velocidad de los objetos en caída y el puntaje necesario para progresar.

### TexturePacker: 

Herramienta para manejar texturas externas. Permite organizar y adaptar los gráficos de objetos según los diferentes niveles y configuraciones de diseño.

### Interfaz de Audio:

El juego incluye música de fondo y efectos de sonido (SFX) para acompañar acciones específicas, como la recolección de recursos o el daño recibido.

## Futuras Implementaciones

1. Habilidades: Se planean habilidades como esquivar, boost de velocidad, y aumento del alcance de la canasta.
2. Progresión Persistente: Guardar el progreso entre intentos.
3. Diseño Visual Final: Implementación de texturas y gráficos personalizados con estilo caricaturesco para mayor inmersión.  

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
