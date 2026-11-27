# Stutager

Stutager és una aplicació Android per organitzar els treballs i els exàmens dels diferents mòduls. Reunim les activitats en una mateixa interfície, amb informació sobre la data, l’estat, la prioritat i el tipus de tasca.

Desenvolupem el projecte amb Kotlin i layouts XML dins de l’assignatura de Desenvolupament Sostenible d’Aplicacions Mòbils, a DAM2A de l’Institut TIC de Barcelona, durant el curs 2026–2027.

**Equip:** Isha Waheed, Fedor Sokolov i Andrei Azarin Podolchuk.

## Documentació del codi

La referència HTML de Dokka es genera a partir dels comentaris KDoc del codi Kotlin. El README descriu el comportament de la versió actual.

[Obrir la documentació HTML](https://isha789.github.io/Stutager/)

## Estat actual

Hem passat del prototip de pantalles a una aplicació amb navegació, un logo propi, una pantalla inicial animada i llistes personalitzades amb dades de prova.

La versió actual permet crear, editar i eliminar tasques. Les dues llistes comparteixen les dades durant el procés de l’aplicació.

| Funcionalitat | Comportament actual |
| Inici de sessió i registre | Formularis amb validació desacoblada en temps real (nom, correu, contrasenya i confirmació) mitjançant ViewModels i LiveData. |
| Pantalla inicial | Mostra el logo Academic Milestone amb una animació d’aparició i escala. |
| Ajuda i benvinguda | Presenten l’app i permeten seguir el recorregut d’entrada. |
| Inici de sessió i registre | Mostren formularis i accessos de navegació; no autentiquen usuaris. |
| Menú | Mostra dues seccions principals i un menú amb preferències, ajuda i accés al compte. |
| Llistes | Inicialment, el resum mostra tres tasques de prova i la llista general en mostra set. Les tasques noves apareixen a l’inici de totes dues llistes. Cada llista actualitza el recompte i mostra un missatge quan queda buida. |
| Detall | Carrega la informació de la tasca seleccionada mitjançant el seu ID. |
| Creació i edició | Comproven que hi hagi un títol i actualitzen la llista en memòria. Un Toast confirma el resultat. |
| Eliminació | Demana confirmació i treu la tasca de les llistes. Les eliminacions de tasques d’exemple es conserven entre processos. |
| Filtres | Permeten seleccionar criteris i netejar els camps. Aplica mostra un Toast de demostració. |
| Preferències | Permeten provar les opcions de tema, ordre i visibilitat. Desa mostra un Toast de demostració. |

## Tecnologies

- Kotlin per a la lògica de les pantalles.
- XML per als layouts, textos, colors, temes i recursos gràfics.
- AndroidX, AppCompat i components Material.
- RecyclerView per a les llistes personalitzades.
- SharedPreferences per conservar els identificadors de les tasques d’exemple eliminades.
- Android Studio i Gradle per desenvolupar i compilar.
- Git i GitHub per compartir els canvis de l’equip.

No utilitzem una base de dades ni un servidor en aquesta versió.

## Compilació local

Amb el SDK d’Android 37 i el JDK del projecte configurats, executa `./gradlew assembleDebug` (`gradlew.bat assembleDebug` al Windows).

Si la compilació acaba correctament, l’APK de depuració es genera a `app/build/outputs/apk/debug/app-debug.apk`.

## Pantalles i navegació

El recorregut inicial és:

**Splash → Ajuda inicial → Benvinguda → Accés com a convidat o inici de sessió → Resum d’activitats.**

| Pantalla | Classe principal | Funció |
| --- | --- | --- |
| Inici animat | `SplashActivity` | Mostra el logo i obre l’ajuda inicial. |
| Ajuda | `InitialHelpActivity` | Explica els passos bàsics per utilitzar l’app. |
| Benvinguda | `WelcomeActivity` | Dona accés al login, registre i mode convidat. |
| Login | `LoginActivity` | Presenta el formulari d’entrada i el control de visibilitat de la contrasenya. |
| Registre | `RegisterActivity` | Presenta el formulari per crear un compte. |
| Resum | `SummaryFragment` | Mostra fins a tres tasques, el recompte i l’estat buit. |
| Totes les tasques | `AllTasksFragment` | Mostra la llista general i l’accés als filtres. |
| Detall | `TaskDetailActivity` | Mostra les dades i dona accés a editar o eliminar. |
| Formulari | `TaskEditActivity` | Comparteix els modes de creació i edició. |
| Filtres | `FiltersActivity` | Presenta la cerca, els selectors i els resultats d’exemple. |
| Preferències | `PreferencesActivity` | Presenta les opcions de configuració. |

Obrim les pantalles secundàries amb Intents explícits. Per consultar o editar una tasca passem el seu ID amb `TaskRepository.EXTRA_TASK_ID`. L’extra `is_edit` diferencia els dos modes del formulari. Si falta l’ID o la tasca ja no està disponible, la pantalla mostra un avís i torna enrere.

### Logo i splash

El logo escollit pel grup és **Academic Milestone**, format per tres barres i una marca de verificació.

`SplashActivity` és el launcher del Manifest. Utilitza `activity_splash.xml`, `ic_stutager_logo.xml` i `splash_logo_in.xml`. L’animació combina aparició i escala del logo. Després d’1,8 segons en primer pla obre l’ajuda i es tanca. Si l’Activity es posa en pausa abans del final, es cancel·la el pas a l’ajuda.

### Activity, Fragments i menú

`MainActivity` allotja els dos Fragments. La Toolbar amb el nom Stutager i els botons **Activitats** i **Totes les tasques** es mantenen visibles. El menú de la Toolbar conté **Preferències**, **Ajuda** i **Inicia sessió o registra’t**. Cada opció obre l’Activity corresponent.

El `FragmentContainerView` conté la secció seleccionada. Fem servir `supportFragmentManager` i una transacció amb `replace()` i `commitNow()` per canviar-la. La transacció s’aplica abans d’actualitzar el botó actiu. Si la secció ja està oberta, es conserva el seu Fragment. Quan Android recrea l’Activity, el FragmentManager restaura la secció i l’Activity marca el botó corresponent.

Les seccions principals no s’afegeixen a l’historial de Fragments. Enrere des de la llista general torna al resum. Enrere des del resum torna a la pantalla anterior.

## Organització del projecte

Les classes Kotlin es troben a `app/src/main/java/cat/ishawaheed/stutager/`.

| Fitxer o carpeta | Responsabilitat |
| --- | --- |
| `MainActivity.kt` | Allotja la Toolbar, els botons principals i el contenidor de Fragments. |
| `SummaryFragment.kt` i `AllTasksFragment.kt` | Gestionen el contingut, la selecció i el RecyclerView de cada secció. |
| Altres Activities | Gestionen el detall, el formulari i la resta de pantalles secundàries. |
| `TaskItem.kt` | Defineix la `data class TaskItem` i l’enumeració `BadgeType`. |
| `TaskRepository.kt` | Comparteix una llista mutable amb les operacions de cerca, alta, edició i eliminació. |
| `TaskAdapter.kt` | Connecta les tasques amb les files del RecyclerView; inclou el ViewHolder. |
| `WindowInsetsSupport.kt` | Agrupa funcions d’ajuda per a insets i adaptació del text i de la distribució. |
| `res/layout/` | Conté els layouts de les pantalles i `item_task.xml`. |
| `res/drawable/` | Conté icones, el logo, fons i insígnies. |
| `res/values/` | Conté textos, arrays, plurals, colors i temes. |
| `res/menu/` | Defineix les opcions del menú. |
| `res/anim/` | Defineix l’animació del splash. |
| `AndroidManifest.xml` | Registra les Activities, els temes i l’entrada de l’app. |
| `AuthValidator.kt` | Engine pur en Kotlin per a la validació de noms, correus i contrasenyes. |
| `LoginViewModel.kt` i `RegisterViewModel.kt` | Gestió d'estat i publicació de LiveData per als formularis d'accés. |
| `LoginUiState.kt` i `RegisterUiState.kt` | Estats immutables de la interfície d'usuari per a Login i Registre. |

## Dades i llistes

### Model i repositori

`TaskItem` conté el títol, el mòdul, les dates de presentació, l’estat, el tipus, la prioritat, les notes i l’ID. Aquest identificador permet obrir la tasca correcta encara que canviï de posició.

`TaskRepository` és un `object` amb una sola `MutableList<TaskItem>`. La primera consulta carrega les dades d’exemple. `tasks()` retorna una còpia per mostrar i `find()` busca la tasca pel seu ID. Amb `summary = true` es mostren fins a tres tasques. Primer hi apareixen les més noves i les places lliures s’omplen amb les tasques de `SUMMARY_IDS` que encara existeixen.

Les tasques noves i les edicions duren mentre viu el procés. Girar la pantalla o obrir una altra Activity conserva la llista compartida. Quan el procés es torna a iniciar, es perden les tasques noves i les edicions. SharedPreferences desa els ID de les tasques d’exemple eliminades perquè no es tornin a carregar.

### Adapter i ViewHolder

Cada Fragment infla el seu XML a `onCreateView()` i configura el RecyclerView vertical i el seu Adapter a `onViewCreated()`.

A `TaskAdapter`:

- `onCreateViewHolder()` infla `item_task.xml` i crea el Holder.
- `onBindViewHolder()` mostra els textos, la icona de treball o examen i la insígnia d’estat.
- `getItemCount()` retorna el nombre de tasques.
- `updateTasks()` copia la llista rebuda i crida `notifyDataSetChanged()` per actualitzar-la. És una solució senzilla per a les dades de prova.

`TaskViewHolder` guarda les referències als components de cada fila perquè es puguin reutilitzar durant el scroll.

El callback `onItemClick(task)` comunica la selecció al Fragment, que obre el detall amb l’ID. A `onResume()` cada Fragment consulta les dades, actualitza el mateix Adapter i el recompte, i mostra un missatge quan la llista és buida. A `onDestroyView()` desconnecta l’Adapter i allibera la referència.

## Formularis i accions

### Crear i editar

El formulari comparteix layout entre els dos modes. En editar, busca la tasca per ID i omple els camps amb les seves dades. Manté les notes buides quan la tasca no en té. La data és un camp de text en format `dd/mm/aaaa`.

Desar comprova que el títol, sense espais als extrems, no sigui buit. Quan és vàlid, el formulari construeix un `TaskItem` amb els camps seleccionats i escrits. En crear, el repositori assigna un ID únic durant el procés i afegeix la tasca a l’inici de la llista, de manera que també es veu al resum. En editar, `indexOfFirst()` troba la posició pel seu ID i l’element se substitueix al mateix lloc. El Toast confirma el resultat i el detall torna a llegir la tasca quan es tanca el formulari.

### Eliminar

El detall construeix un diàleg amb `MaterialAlertDialogBuilder` que mostra el nom de la tasca. Cancel·la tanca el diàleg. Elimina crida el repositori, mostra un Toast, escriu un Log i tanca el detall.

En tornar a la llista, la tasca ja no es mostra. L’eliminació queda registrada a SharedPreferences quan la tasca és d’exemple, encara que s’hagi editat durant el procés. Si es recrea el detall amb el diàleg obert, es torna a mostrar la confirmació.

### Filtres i preferències

Neteja restableix els camps dels filtres i mostra un Toast. Aplica mostra un missatge i tanca la pantalla. Les dues targetes de resultats són exemples que s’oculten si s’ha eliminat la tasca corresponent. La cerca i les pestanyes de tipus de les pantalles de llistes formen part de la maqueta.

Les preferències permeten provar els selectors de tema, ordre i visibilitat de completades. Desa mostra un Toast de demostració i tanca la pantalla. El tema de l’aplicació segueix el mode del sistema.

## Validació i arquitectura d'accés (MVVM i LiveData)

Els formularis d'Inici de sessió i Registre utilitzen l'arquitectura **MVVM (Model-View-ViewModel)** amb **LiveData**, desacoblant completament la interfície d'usuari de la lògica de validació de dades.

### Motor de Validació (`AuthValidator`)

La classe `AuthValidator` actua com el motor de validació pur en Kotlin, sense dependències del framework d'Android:

- **Nom complet:** Obligatori, entre 2 i 80 caràcters després de la normalització `.trim()`.
- **Correu electrònic:** Màxim 254 caràcters, `.trim()` als extrems i verificació de sintaxi estàndard amb l'expressió regular `^[^\\s@]+@[^\\s@.]+(?:\\.[^\\s@.]+)+$`.
- **Contrasenya:** Entre 8 i 128 caràcters, amb l'obligació de incloure almenys una lletra (`isLetter()`) i almenys un dígit (`isDigit()`). No s'aplica `.trim()` a les contrasenyes per no alterar-ne la longitud real.
- **Confirmació de contrasenya:** Coincidència exacta (sensible a majúscules/minúscules) amb la contrasenya original.
- **Ordre de precedència d'errors:** Avalua les regles en l'ordre estricte definit per l'enum `Error` (`REQUIRED`, `TOO_SHORT`, `TOO_LONG`, `INVALID_EMAIL`, `MISSING_LETTER`, `MISSING_DIGIT`, `MISMATCH`).
- **Resultats agregats:** Retorna objectes `LoginResult` i `RegistrationResult` amb la propietat `isValid`.

### Arquitectura de Pantalles i Observabilitat

- **`LoginViewModel` i `RegisterViewModel`**: Mantenen un `MutableLiveData` privat i exposen un `LiveData` públic immutable (`uiState`). En rebre les dades de la vista, avaluen `AuthValidator` i publiquen el nou estat d'errors o de confirmació.
- **`LoginActivity` i `RegisterActivity`**: Observen `uiState` amb `.observe(this)`, mostren els missatges d'error localitzats a les vistes o els netegen quan es corregeixen. Quan la validació és correcta (`submissionAccepted == true`), realitzen la navegació corresponent.

### Limitació d'Autenticació
La validació d'aquests formularis s'executa localment en temps d'execució als ViewModels. No s'ha implementat un servei d'autenticació real ni persistència de comptes d'usuari a base de dades.

## Usabilitat i accessibilitat

Hem incorporat mesures al codi per facilitar la interacció:

- Estats expressats amb text i color.
- Descripcions d’accions i del tipus de tasca.
- Acció accessible per obrir una tasca i agrupació de les files per al lector de pantalla.
- Icones decoratives excloses de la lectura quan correspon.
- Títol accessible del diàleg d’eliminació.
- Ajustos de selectors i de l’amplada de l’etiqueta de prioritat per a text ampliat.
- Tractament d’insets de les barres del sistema i del teclat al formulari i a les pantalles principals.

La implementació d’insets varia entre pantalles. Cal comprovar en un dispositiu les àrees tàctils, les etiquetes, el contrast, el text ampliat i la lectura amb TalkBack; no donem aquestes proves per superades només perquè hi hagi atributs d’accessibilitat.

## Equip i treball compartit

| Membre | Àrea assignada |
| --- | --- |
| Isha Waheed | Splash, navegació, menú, resum i configuració de les Activities. |
| Fedor Sokolov | Model, repositori, Adapter, ViewHolder i llista general. |
| Andrei Azarin Podolchuk | Formularis, detall, confirmació d’eliminació, filtres i preferències. |



Coordinem els canvis dels fitxers compartits per evitar conflictes. Les correccions pendents del repositori o de la navegació s’han d’acordar amb el membre responsable.
