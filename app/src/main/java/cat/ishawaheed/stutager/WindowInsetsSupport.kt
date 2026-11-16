package cat.ishawaheed.stutager

import android.view.View
import android.view.ViewGroup
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

/** Keeps XML content clear of system bars and the on-screen keyboard. */
fun AppCompatActivity.applySystemBarInsets() {
    enableEdgeToEdge()
    val root = findViewById<View>(android.R.id.content)
    val left = root.paddingLeft
    val top = root.paddingTop
    val right = root.paddingRight
    val bottom = root.paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
        val safe = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
        view.setPadding(left + safe.left, top + safe.top, right + safe.right, bottom + safe.bottom)
        insets
    }
}

/** ToggleGroup forces single-line labels during inflation; allow user-scaled text to wrap. */
fun MaterialButtonToggleGroup.allowLabelWrapping() {
    for (i in 0 until childCount) {
        (getChildAt(i) as? MaterialButton)?.apply {
            isSingleLine = false
            maxLines = 3
            ellipsize = null
            if (resources.configuration.fontScale >= 1.5f) {
                val inset = (8 * resources.displayMetrics.density).toInt()
                setPaddingRelative(paddingStart.coerceAtMost(inset), paddingTop,
                    paddingEnd.coerceAtMost(inset), paddingBottom)
            }
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }
}

/** Preserve the original tab widths unless their complete labels no longer fit. */
fun LinearLayout.fitTabLabels() {
    doOnLayout {
        val labels = (0 until childCount).mapNotNull { getChildAt(it) as? TextView }
        val required = labels.sumOf { label ->
            val margins = label.layoutParams as LinearLayout.LayoutParams
            (label.paint.measureText(label.text.toString()) + label.compoundPaddingLeft +
                label.compoundPaddingRight + margins.leftMargin + margins.rightMargin).toDouble()
        }
        if (required > width - paddingLeft - paddingRight) {
            labels.forEach { label ->
                val inset = (4 * resources.displayMetrics.density).toInt()
                label.setPaddingRelative(label.paddingStart.coerceAtMost(inset), label.paddingTop,
                    label.paddingEnd.coerceAtMost(inset), label.paddingBottom)
                label.layoutParams = (label.layoutParams as LinearLayout.LayoutParams).apply {
                    width = 0
                    weight = 1f
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }
            }
        }
    }
}

/** Keep result metadata visible when enlarged text leaves no space beside the badge. */
fun LinearLayout.stackResultWhenConstrained() {
    if (resources.configuration.fontScale < 1.5f && resources.configuration.screenWidthDp >= 360) return
    orientation = LinearLayout.VERTICAL
    for (i in 0 until childCount) {
        getChildAt(i).layoutParams = (getChildAt(i).layoutParams as LinearLayout.LayoutParams).apply {
            weight = 0f
            if (i == 1) width = ViewGroup.LayoutParams.MATCH_PARENT
            if (i == childCount - 1) gravity = Gravity.END
        }
    }
}
