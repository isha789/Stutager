package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ActivitiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities_summary)

        findViewById<CardView>(R.id.cardTask1).setOnClickListener {
            startActivity(Intent(this, TaskDetailActivity::class.java))
        }

        findViewById<FloatingActionButton>(R.id.btnAddActivity).setOnClickListener {
            startActivity(Intent(this, TaskEditActivity::class.java))
        }

        findViewById<FloatingActionButton>(R.id.btnAllTasks).setOnClickListener {
            startActivity(Intent(this, AllTasksActivity::class.java))
        }
    }
}
