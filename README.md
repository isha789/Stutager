# Stutager

Stutager és una aplicació Android per organitzar els treballs i els exàmens dels diferents mòduls. Reunim les activitats en una mateixa interfície, amb informació sobre la data, l’estat, la prioritat i el tipus de tasca.

Desenvolupem el projecte amb Kotlin i layouts XML dins de l’assignatura de Desenvolupament Sostenible d’Aplicacions Mòbils, a DAM2A de l’Institut TIC de Barcelona, durant el curs 2026–2027.

**Equip:** Isha Waheed, Fedor Sokolov i Andrei Azarin Podolchuk.

## Documentació del codi

La documentació es genera en format HTML amb Dokka a partir dels comentaris KDoc del codi Kotlin.

[Obrir la documentació HTML](app/build/docs/html/index.html)

## Estat actual

Hem passat del prototip de pantalles a una aplicació amb navegació, un logo propi, una pantalla inicial animada i llistes personalitzades amb dades de prova.

La versió actual és un prototip funcional de navegació i interacció. Algunes accions ja modifiquen dades i d’altres només mostren el comportament previst amb missatges de demostració.

| Funcionalitat | Comportament actual |
| --- | --- |
| Pantalla inicial | Mostra el logo Academic Milestone amb una animació d’aparició i escala. |
| Ajuda i benvinguda | Presenten l’app i permeten seguir el recorregut d’entrada. |
| Inici de sessió i registre | Mostren formularis i accessos de navegació; no autentiquen usuaris. |
| Menú | Obre el resum, totes les tasques, preferències i ajuda. |
| Llistes | Mostren tasques de prova amb RecyclerView, icones i insígnies d’estat. |
| Detall | Carrega la informació de la tasca seleccionada mitjançant el seu ID. |
| Creació i edició | Validen que el títol no sigui buit i mostren un Toast; no desen les dades. |
| Eliminació | Demana confirmació, elimina la tasca de les llistes i conserva aquesta eliminació entre obertures. |
| Filtres | Permeten seleccionar criteris i netejar els camps; Aplica mostra un Toast, però no filtra la llista. |
| Preferències | Mostren opcions de tema, ordre i visibilitat; Desa no conserva ni aplica les seleccions. |

## Tecnologies

- Kotlin per a la lògica de les pantalles.
- XML per als layouts, textos, colors, temes i recursos gràfics.
- AndroidX, AppCompat i components Material.
- RecyclerView per a les llistes personalitzades.
- SharedPreferences per conservar els identificadors de les tasques eliminades.
- Android Studio i Gradle per desenvolupar i compilar.
- Git i GitHub per compartir els canvis de l’equip.

No utilitzem una base de dades ni un servidor en aquesta versió.

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
| Resum | `MainActivity` | Mostra tasques i accessos al menú, al detall i a la creació. |
| Totes les tasques | `AllTasksActivity` | Mostra la llista general, el recompte i l’estat buit. |
| Detall | `TaskDetailActivity` | Mostra les dades i dona accés a editar o eliminar. |
| Formulari | `TaskEditActivity` | Comparteix els modes de creació i edició. |
| Filtres | `FiltersActivity` | Presenta la cerca, els selectors i els resultats d’exemple. |
| Preferències | `PreferencesActivity` | Presenta les opcions de configuració. |

El projecte també conserva `ActivitiesActivity` i `activity_activities_summary.xml`, una altra vista de resum. El recorregut principal de benvinguda i login utilitza `MainActivity`.

Obrim les pantalles amb Intents explícits. Per consultar o editar una tasca passem el seu ID amb `TaskRepository.EXTRA_TASK_ID`. L’extra `is_edit` diferencia els dos modes del formulari. Els botons de retorn tanquen la pantalla amb `finish()`.

### Logo i splash

El logo escollit pel grup és **Academic Milestone**, format per tres barres i una marca de verificació.

`SplashActivity` és el launcher del Manifest. Utilitza `activity_splash.xml`, `ic_stutager_logo.xml` i `splash_logo_in.xml`. L’animació combina aparició i escala del logo. Després d’un retard programat de 1,8 segons obre l’ajuda i es tanca per evitar tornar al splash amb el botó enrere.

### Menú

Les pantalles de llistes obren un `PopupMenu` des de la icona de menú. Inflem `res/menu/menu_stutager.xml` i un listener identifica l’opció seleccionada per obrir el resum, totes les tasques, preferències o ajuda.

El fitxer `include_stutager_toolbar.xml` existeix, però en la versió documentada no està integrat a les pantalles. El menú desplegable sí que té listeners de navegació; no donem per implementada una Toolbar compartida.

## Organització del projecte

Les classes Kotlin es troben a `app/src/main/java/cat/ishawaheed/stutager/`.

| Fitxer o carpeta | Responsabilitat |
| --- | --- |
| Activities | Gestionen la pantalla, els clics i la navegació. |
| `TaskItem.kt` | Defineix la `data class TaskItem` i l’enumeració `BadgeType`. |
| `TaskRepository.kt` | Proporciona dades de prova, cerca per ID i conserva les eliminacions. |
| `TaskAdapter.kt` | Connecta les tasques amb les files del RecyclerView; inclou el ViewHolder. |
| `WindowInsetsSupport.kt` | Agrupa funcions d’ajuda per a insets i adaptació del text i de la distribució. |
| `res/layout/` | Conté els layouts de les pantalles i `item_task.xml`. |
| `res/drawable/` | Conté icones, el logo, fons i insígnies. |
| `res/values/` | Conté textos, arrays, plurals, colors i temes. |
| `res/menu/` | Defineix les opcions del menú. |
| `res/anim/` | Defineix l’animació del splash. |
| `AndroidManifest.xml` | Registra les Activities, els temes i l’entrada de l’app. |

## Dades i llistes

### Model i repositori

`TaskItem` conté el títol, el mòdul, les dates de presentació, l’estat, el tipus, la prioritat, les notes i l’ID. Aquest identificador permet obrir la tasca correcta encara que canviï de posició.

`TaskRepository` construeix les dades de prova amb els recursos de text. `tasks()` retorna les tasques que no s’han eliminat, `find(id)` en busca una i `delete(id)` desa l’ID eliminat a SharedPreferences.

Només es conserven els identificadors eliminats, no totes les dades de les tasques. En la versió actual, el paràmetre `summary` encara no aplica `SUMMARY_IDS`: el resum i la llista general obtenen el mateix conjunt de tasques visibles.

### Adapter i ViewHolder

`MainActivity` i `AllTasksActivity` configuren un RecyclerView amb `LinearLayoutManager` vertical.

A `TaskAdapter`:

- `onCreateViewHolder()` infla `item_task.xml` i crea el Holder.
- `onBindViewHolder()` mostra els textos, la icona de treball o examen i la insígnia d’estat.
- `getItemCount()` retorna el nombre de tasques.
- `updateTasks()` copia la llista rebuda i crida `notifyDataSetChanged()` per actualitzar-la. És una solució senzilla per a les dades de prova.

`TaskViewHolder` guarda les referències als components de cada fila perquè es puguin reutilitzar durant el scroll.

El callback `onItemClick(task)` comunica la selecció a l’Activity, que obre el detall amb l’ID. Les llistes es tornen a consultar a `onResume()`. La pantalla general també actualitza el recompte i mostra un missatge si no queden tasques.

## Formularis i accions

### Crear i editar

El formulari comparteix layout entre els dos modes. En editar, busca la tasca per ID i omple els camps. La selecció del mòdul distingeix DSAM, Programació, Accés a dades i Digitalització.

Desar comprova que el títol, sense espais als extrems, no sigui buit. Si és vàlid, mostra un Toast i tanca el formulari. Tot i el text dels missatges de confirmació actuals, aquesta acció encara no crea ni actualitza dades.

### Eliminar

El detall construeix un diàleg amb `MaterialAlertDialogBuilder` que mostra el nom de la tasca. Cancel·la tanca el diàleg. Elimina crida el repositori, mostra un Toast, escriu un Log i tanca el detall.

En tornar a la llista, la tasca ja no es mostra. Aquesta eliminació es conserva entre obertures de l’app. També es guarda si el diàleg estava obert en recrear el detall.

### Filtres i preferències

Neteja reinicia els camps dels filtres i mostra un Toast. Aplica mostra un missatge i tanca la pantalla, sense aplicar els criteris a les llistes. Les dues targetes de resultats són exemples que s’oculten si s’ha eliminat la tasca corresponent.

Les preferències permeten seleccionar tema, ordre i visibilitat de completades, però Desa només mostra un Toast i tanca la pantalla. No hi ha persistència ni aplicació global d’aquestes seleccions.

## Usabilitat i accessibilitat

Hem incorporat mesures al codi per facilitar la interacció:

- Estats expressats amb text i color.
- Descripcions d’accions i del tipus de tasca.
- Acció accessible per obrir una tasca i agrupació de les files per al lector de pantalla.
- Icones decoratives excloses de la lectura quan correspon.
- Títol accessible del diàleg d’eliminació.
- Ajustos de selectors i de distribució per a text ampliat.
- Tractament d’insets de barres del sistema i del teclat en diferents pantalles.

La implementació d’insets varia entre pantalles. Cal comprovar en un dispositiu les àrees tàctils, les etiquetes, el contrast, el text ampliat i la lectura amb TalkBack; no donem aquestes proves per superades només perquè hi hagi atributs d’accessibilitat.

## Equip i treball compartit

| Membre | Àrea assignada |
| --- | --- |
| Isha Waheed | Splash, navegació, menú, resum i configuració de les Activities. |
| Fedor Sokolov | Model, repositori, Adapter, ViewHolder i llista general. |
| Andrei Azarin Podolchuk | Formularis, detall, confirmació d’eliminació, filtres i preferències. |

Coordinem els canvis dels fitxers compartits per evitar conflictes. Les correccions pendents del repositori o de la navegació s’han d’acordar amb el membre responsable.
