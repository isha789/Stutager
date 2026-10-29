package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicia sessió button -> opens LoginActivity (Page 3)
        findViewById<Button>(R.id.btnIniciaSessio)?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Registra't button
        findViewById<Button>(R.id.btnRegistrat)?.setOnClickListener {
            Toast.makeText(this, "S'ha clicat Registra't", Toast.LENGTH_SHORT).show()
        }

        // Continua com a convidat button -> opens MainActivity (Page 4)
        findViewById<LinearLayout>(R.id.btnConvidat)?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
