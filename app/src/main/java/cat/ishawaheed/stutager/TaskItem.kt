package cat.ishawaheed.stutager

/**
 * Dades d'una tasca que utilitzen les llistes i la pantalla de detall.
 * [id] identifica la tasca encara que canviï de posició a la llista.
 * [priorityIndex] indica la prioritat: 0 baixa, 1 mitjana i 2 alta.
 */
data class TaskItem(
    val title: String,
    val module: String,
    val date: String,
    val status: String,
    val isExam: Boolean,
    val badgeType: BadgeType = BadgeType.BLUE,
    val id: String = "",
    val detailDate: String = date,
    val notes: String = "",
    val priorityIndex: Int = 1
)

/** Colors disponibles per a la insígnia de l'estat d'una tasca. */
enum class BadgeType {
    BLUE, YELLOW, GREEN, RED
}
