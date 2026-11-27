package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

/** Allotja el resum i la llista general i manté visible la navegació principal. */
class MainActivity : AppCompatActivity() {
    private lateinit var primaryTabs: MaterialButtonToggleGroup
    private val backToSummary = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            showSection(allTasks = false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applySystemBarInsets()
        onBackPressedDispatcher.addCallback(this, backToSummary)

        val toolbar = findViewById<Toolbar>(R.id.mainToolbar)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        toolbar.inflateMenu(R.menu.menu_stutager)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_preferences -> startActivity(Intent(this, PreferencesActivity::class.java))
                R.id.menu_help -> startActivity(Intent(this, InitialHelpActivity::class.java)
                    .putExtra(InitialHelpActivity.EXTRA_FROM_MENU, true))
                R.id.menu_account -> startActivity(Intent(this, WelcomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))
                else -> return@setOnMenuItemClickListener false
            }
            true
        }

        primaryTabs = findViewById<MaterialButtonToggleGroup>(R.id.primaryTabs)
        primaryTabs.allowLabelWrapping()
        findViewById<MaterialButton>(R.id.btnSummary).setOnClickListener { showSection(allTasks = false) }
        findViewById<MaterialButton>(R.id.btnAllTasks).setOnClickListener { showSection(allTasks = true) }

        if (savedInstanceState == null) showSection(allTasks = false)
        else updatePrimarySelection()
    }

    /** Canvia de secció sense afegir-la a l'historial de Fragments. */
    private fun showSection(allTasks: Boolean) {
        val current = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if ((allTasks && current is AllTasksFragment) || (!allTasks && current is SummaryFragment)) return

        val next = if (allTasks) AllTasksFragment() else SummaryFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, next)
            .commitNow()
        updatePrimarySelection()
    }

    private fun updatePrimarySelection() {
        val allTasks = supportFragmentManager.findFragmentById(R.id.fragmentContainer) is AllTasksFragment
        primaryTabs.check(if (allTasks) R.id.btnAllTasks else R.id.btnSummary)
        backToSummary.isEnabled = allTasks
    }
}
