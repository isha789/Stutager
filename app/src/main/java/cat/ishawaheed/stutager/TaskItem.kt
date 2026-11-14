package cat.ishawaheed.stutager

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

enum class BadgeType {
    BLUE, YELLOW, GREEN, RED
}
