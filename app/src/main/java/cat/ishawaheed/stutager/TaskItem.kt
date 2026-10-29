package cat.ishawaheed.stutager

data class TaskItem(
    val title: String,
    val module: String,
    val date: String,
    val status: String,
    val isExam: Boolean,
    val badgeType: BadgeType = BadgeType.BLUE
)

enum class BadgeType {
    BLUE, YELLOW, GREEN, RED
}
