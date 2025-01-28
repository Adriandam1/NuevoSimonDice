### 1. **Inicio del Juego (UI inicia el flujo)**

- **UI**: La pantalla de inicio muestra el estado inicial del juego y puede mostrar un botón para comenzar.
- **`MyViewModel`**: Cuando el usuario pulsa el botón de inicio, se activa el método `setRandom()` del `ViewModel`.
    - **`setRandom()`** genera un número aleatorio que representa el color en la secuencia que el jugador debe seguir.
    - El número generado se agrega a la lista de secuencias (`secuencia`).
    - El estado del juego se actualiza a **`ADIVINANDO`** (indicando que ahora el jugador debe adivinar la secuencia).

      ```kotlin
      viewModel.setRandom()
      ```

### 2. **Generación de Secuencia (Colores parpadean)**

- **UI**: El **`ViewModel`** invoca el método `cambiosColores()`, que es responsable de mostrar la secuencia de colores de forma intermitente (simulando el parpadeo).
    - Se ejecuta una corutina que usa `delay()` para hacer que cada color parpadee en la pantalla antes de que el usuario comience a ingresar su secuencia.
    - Cada color parpadea uno tras otro, mostrando visualmente los colores que el jugador debe recordar.

      ```kotlin
      viewModel.cambiosColores()
      ```

- **`MyViewModel`**: Llama a los métodos de color (`getColorRed()`, `getColorGreen()`, etc.) para obtener los colores y hacer que parpadeen en la pantalla.

### 3. **Interacción del Jugador (Ingresar Secuencia)**

- **UI**: El usuario debe presionar los botones correspondientes a los colores en la secuencia mostrada. A medida que el usuario hace clic, se almacena la selección en el **`ViewModel`**.
    - Cada vez que el jugador presiona un color, se llama al método **`addColor()`** en el **`ViewModel`**.

      ```kotlin
      viewModel.addColor(color)
      ```

- **`MyViewModel`**: El método `addColor()` agrega el color presionado a la lista de colores ingresados por el jugador y luego llama al método `winOrLose()` para comprobar si la secuencia es correcta.

### 4. **Verificación de Respuesta (Comprobar si la Secuencia es Correcta)**

- **`MyViewModel`**: El método `winOrLose()` se encarga de comparar la secuencia generada con la secuencia ingresada por el jugador.
    - Si la secuencia es correcta, se llama al método `onWin()`.
    - Si la secuencia es incorrecta, se llama al método `onLose()`.

      ```kotlin
      if (secuenciaCorrecta) {
          onWin()
      } else {
          onLose()
      }
      ```

### 5. **Resultados de la Verificación (Victoria o Derrota)**

- **`MyViewModel`**: Dependiendo del resultado de la verificación:
    - Si el jugador ganó, se incrementan las **`rondas`** y **`aciertos`** y se actualiza el **`record`** si es necesario.
    - Si el jugador perdió, se resetean las rondas y los aciertos, y el juego vuelve a un estado inicial.

      ```kotlin
      if (jugadorGano) {
          incrementAciertos()
          incrementRondas()
          incrementRecord()
      } else {
          restartValues()
      }
      ```

- **UI**: El **`ViewModel`** actualiza los **LiveData** (por ejemplo, `aciertosLiveData`, `rondasLiveData` y `recordLiveData`), y la UI observa esos cambios para actualizar la pantalla y mostrar el estado del juego.

### 6. **Ciclo Continuo (Repetir el Juego)**

- **UI**: Después de que el jugador haya ganado o perdido, puede elegir repetir el juego. Esto se maneja por medio de un botón "Reiniciar" que invoca el método **`setRandom()`** nuevamente para generar una nueva secuencia y empezar otro ciclo del juego.

    ```kotlin
    viewModel.setRandom()  // Para reiniciar el juego
    ```

- **`MyViewModel`**: Vuelve a generar una nueva secuencia de colores y el estado del juego pasa nuevamente a **`ADIVINANDO`**.

---

### Resumen del Flujo

1. **UI (Inicio)**: El jugador inicia el juego desde la UI.
2. **`MyViewModel`**: Genera la secuencia aleatoria de colores y maneja el estado del juego.
3. **UI (Mostrar Colores)**: La UI muestra la secuencia de colores que el jugador debe memorizar.
4. **UI (Entrada del Jugador)**: El jugador ingresa su secuencia y el **`ViewModel`** la verifica.
5. **`MyViewModel`**: Compara la secuencia y actualiza las estadísticas (aciertos, rondas, record).
6. **UI (Resultado)**: La UI muestra si el jugador ganó o perdió.
7. **Reiniciar**: El jugador puede reiniciar el juego, repitiendo el ciclo.

Este flujo asegura que la interfaz de usuario esté constantemente sincronizada con la lógica del juego a través de **`LiveData`** y **`ViewModel`**, gestionando el estado y la interacción del usuario de forma eficiente.

---------------------------------------------------------------------------------------------------------------------------------------------------

# Explicación DETALLADA del `MyViewModel`

El `MyViewModel` es una clase que gestiona la lógica del juego "Simón Dice" utilizando **LiveData** para interactuar con la UI de manera reactiva. Esta clase contiene varias funcionalidades, como la generación de secuencias aleatorias de colores, la verificación de respuestas y la actualización de puntuaciones.

### Variables LiveData

1. **`estadoLiveData`**:
    - Almacena el estado actual del juego, inicializándose en `Estados.INICIO`.

2. **`recordLiveData`**, **`rondasLiveData`**, **`aciertosLiveData`**:
    - Almacenan los valores de `record`, `rondas` y `aciertos` respectivamente.

3. **`colorRojoLiveData`**, **`colorVerdeLiveData`**, **`colorAzulLiveData`**, **`colorAmarilloLiveData`**:
    - Son variables para almacenar los colores de la secuencia que debe seguir el jugador.

### Métodos Principales

1. **`incrementAciertos()`**, **`incrementRondas()`**, **`incrementRecord()`**:
    - Incrementan los valores de aciertos, rondas y el record, respectivamente. También actualizan los `LiveData`.

2. **`restartValues()`**:
    - Resetea los valores de aciertos y rondas.

3. **`setRandom()`**:
    - Genera un número aleatorio entre 1 y 4, representando los colores. Este número se añade a la lista de secuencias y cambia el estado del juego a `ADIVINANDO`.

4. **`addColor()`**:
    - Añade un color a la lista de colores seleccionados por el usuario y comprueba si la secuencia es correcta con `winOrLose()`.

5. **`winOrLose()`**:
    - Verifica si la secuencia ingresada por el jugador coincide con la secuencia generada aleatoriamente.
    - Si la secuencia es correcta, llama a `onWin()`.
    - Si la secuencia es incorrecta, llama a `onLose()`.

6. **`onWin()`** y **`onLose()`**:
    - En caso de victoria, se incrementan los valores de aciertos, rondas y se actualiza el record.
    - En caso de derrota, se resetean los valores.

7. **`cambiosColores()`**:
    - Es responsable de hacer que los colores parpadeen según la secuencia generada. Usa **corutinas** (`viewModelScope.launch`) para hacer la animación con **`delay()`**.

### Color Management

- Los métodos `getColorRed()`, `getColorGreen()`, `getColorBlue()` y `getColorYellow()` devuelven los colores normales para cada uno de los colores en la secuencia del juego.

### Explicación Adicional de los Métodos

- **Corutinas y Animaciones**:
    - Dentro de `cambiosColores()`, se utiliza **`delay()`** para simular el parpadeo de los colores. Por ejemplo, cuando el número aleatorio es `1` (representando el color rojo), el color cambia a un tono más brillante y luego vuelve a su color normal después de medio segundo.

- **Control de Estado**:
    - La variable `estadoLiveData` controla en qué fase está el juego (por ejemplo, `INICIO` o `ADIVINANDO`).

### Variables Globales

- **Datos (record, rondas, aciertos)**:
    - `record`, `rondas` y `aciertos` son variables globales que se actualizan y se mantienen sincronizadas con el UI a través de `LiveData`.

### Resumen

En resumen, este `ViewModel` gestiona el estado del juego "Simón Dice", controlando la secuencia de colores, la verificación de respuestas y el registro de puntuaciones. Además, al usar **LiveData** y **corutinas**, se asegura de que los cambios de estado y las actualizaciones de la UI se manejen de manera eficiente y sin bloquear el hilo principal.

