# Stutager - Aplicació de Gestió i Organització d'Activitats Acadèmiques

> **Mòdul**: Desenvolupament Sostenible d'Aplicacions Mòbils  
> **Cicle**: 2n Curs de CFGS Desenvolupament d'Aplicacions Multiplataforma (DAM)  
> **Activitat**: ACT06 - Menú, Llista, Accions i Accessibilitat  
> **Equip de desenvolupament**: Isha Waheed, Fedor Sokolov, Andrei Azarin Podolchuk  
> **Repositori GitHub**: [https://github.com/ishawaheed/Stutager](https://github.com/ishawaheed/Stutager)

---

## 1. Descripció General del Projecte

**Stutager** és una aplicació nativa d'Android dissenyada per ajudar l'alumnat del cicle a organitzar, planificar i fer el seguiment de les seves activitats acadèmiques (treballs i exàmens) dels diferents mòduls del cicle formativa.

A l'activitat **ACT06** s'ha completat la implementació de la interfície d'usuari, la navegació fluida, el menú principal, l'animació d'inici, el RecyclerView personalitzat amb dades dinàmiques en memòria, els formularis adaptatius de creació i edició amb validació, el diàleg modal d'eliminació i les vistes de filtres i preferències.

---

## 2. Repartiment de Tasques i Fitxers (*One Editor Per File*)

Per mantenir la feina ordenada i evitar conflictes de Git en el repositori compartit, l'equip ha seguit la regla d'un únic editor per fitxer (*One Editor Per File*):

### 👩‍💻 **Isha Waheed** (Navegació, Splash, Toolbar i Menú)
* `SplashActivity.kt` + `activity_splash.xml` + `res/anim/splash_logo_in.xml`: Pantalla d'inici amb l'animació vectorial del logotip Stutager.
* `MainActivity.kt` + `activity_main.xml`: Pantalla resum principal amb navegació per menú i llista.
* `ActivitiesActivity.kt` + `activity_activities_summary.xml`: Vista resum d'activitats.
* `res/menu/menu_stutager.xml` + `res/layout/include_stutager_toolbar.xml`: Menú de navegació i barra superior personalitzada.
* `app/src/main/AndroidManifest.xml`: Configuració del launcher, activitats i temes.
* `res/values/strings_navigation.xml` + `docs/act06_navigation.md`: Recursos i documentació de navegació.

### 👨‍💻 **Fedor Sokolov** (Model de Dades, RecyclerView i Llistes)
* `TaskItem.kt`: Model de dades de les tasques (ID, títol, mòdul, data, estat, prioritat, tipus).
* `TaskRepository.kt`: Repositori centralitzat en memòria/SharedPreferences per consultar (`find`), llistar (`tasks`) i eliminar (`delete`) tasques.
* `TaskAdapter.kt` + `item_task.xml`: RecyclerView Adapter i ViewHolder personalitzat per a la llista de tasques amb distintius de colors.
* `AllTasksActivity.kt` + `activity_all_tasks.xml`: Pantalla de la llista completa de tasques amb scroll i estat buit.
* `res/values/strings_tasks.xml` + `docs/act06_lists.md`: Recursos i documentació de llistes.

### 👨‍💻 **Andrei Azarin Podolchuk** (Formularis, Accions, Filtres, Preferències i Accessibilitat)
* `TaskEditActivity.kt` + `activity_task_edit.xml`: Formulari adaptatiu per a **Creació** (*Nova activitat*) i **Edició** (*Edita l'activitat*) amb validació de camps i notificacions `Toast`.
* `TaskDetailActivity.kt` + `activity_task_detail.xml`: Detall complet de la tasca seleccionada i **pop-up modal d'eliminació** (`MaterialAlertDialogBuilder`) amb icona de paperera vermella i confirmació.
* `FiltersActivity.kt` + `activity_filters.xml`: Cerca i filtres detallats per Mòdul, Tipus, Estat i Prioritat amb accions de *Neteja* i *Aplica*.
* `PreferencesActivity.kt` + `activity_preferences.xml`: Pantalla de configuració amb selecció de Tema (*Clar/Fosc*), Ordre i mostrar completades.
* `res/values/strings_forms.xml`: Recursos modulars amb prefix `form_`.

---

## 3. Resum de les Pantalles de l'Aplicació

1. **Splash Screen (`SplashActivity`)**: Animació vectorial del logotip al centre i transició automàtica cap a la pantalla d'Ajuda o Principal.
2. **Ajuda Inicial (`InitialHelpActivity`)**: Onboarding explicatiu en 3 passos (*Afegeix, Organitza, Actualitza*).
3. **Benvinguda (`WelcomeActivity`)**: Opcions per iniciar sessió, registrar-se o accedir com a convidat.
4. **Inici de sessió (`LoginActivity`)** i **Registre (`RegisterActivity`)**: Formularis d'autenticació amb control de visibilitat de la contrasenya.
5. **Activitats Resum (`MainActivity`)**: Llista d'activitats pròximes, filtres ràpids, accés al menú i botó flotant de creació (+).
6. **Totes les tasques (`AllTasksActivity`)**: Llista completa amb `RecyclerView`, scroll vertical, distintius de colors per estat i accés a Filtres.
7. **Filtres i resultats (`FiltersActivity`)**: Formulari de filtres amb cercador, desplegables arrodonits amb fletxa, selector de tipus (*Tots/Treball/Examen*) i resultats d'exemple.
8. **Detall de l'activitat (`TaskDetailActivity`)**: Informació de la tasca seleccionada carregada dinàmicament des de `TaskRepository`.
9. **Confirmació d'eliminació (Pop-up modal)**: Diàleg personalitzat amb la paperera vermella, títol blau i botons *Cancel·la* i *Elimina*.
10. **Creació d'activitat (`TaskEditActivity`)**: Mode «Nova activitat» amb camps buits, estat Pendent i validació de títol.
11. **Edició d'activitat (`TaskEditActivity`)**: Mode «Edita l'activitat» amb camps pre-omplerts des del repositori i botó «Desa els canvis».
12. **Preferències (`PreferencesActivity`)**: Selecció de Tema (*Clar/Fosc*), Ordre i visibilitat de completades.

---

## 4. Usabilitat, Accessibilitat i Gestió de les Barres del Sistema

* **Accessibilitat**:
  * Etiquetes `android:labelFor` en tots els `TextView` associats als camps d'entrada (`EditText` i `Spinner`).
  * Atributs `android:contentDescription` en botons d'acció i `android:importantForAccessibility="no"` en icones decoratives.
  * Àrees tàctils d'almenys **48dp** o **50dp** a tots els elements interactius.
  * Contrast adequat amb blau marí fosc (`#093B91`) sobre fons blanc/clar.
* **Tractament de les Barres del Sistema (Edge-to-Edge)**:
  * Aplicat `enableEdgeToEdge()` i `ViewCompat.setOnApplyWindowInsetsListener` a totes les Activities conservant els paddings laterals inicials definit a l'XML perquè el contingut no s'encavalqui amb la barra d'estat ni de navegació.
  