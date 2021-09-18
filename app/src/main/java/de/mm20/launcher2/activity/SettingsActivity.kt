package de.mm20.launcher2.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import de.mm20.launcher2.R
import de.mm20.launcher2.fragment.PreferencesCalendarFragment
import de.mm20.launcher2.fragment.PreferencesMainFragment
import de.mm20.launcher2.fragment.PreferencesServicesFragment
import de.mm20.launcher2.fragment.PreferencesWeatherFragment
import de.mm20.launcher2.ui.legacy.activity.LauncherActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val fragment = getStartFragment()
            setupActionBar()
            supportFragmentManager
                    .beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit()
        } else if (!savedInstanceState.getBoolean("theme_change")) {
            val fragment = getStartFragment()
            setupActionBar()
            supportFragmentManager
                    .beginTransaction()
                    .replace(android.R.id.content, fragment)
                    .commit()
        }
        findViewById<View>(android.R.id.content)?.setBackgroundColor(getColor(R.color.settings_window_background))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("theme_change", true)
    }

    private fun getStartFragment(): Fragment {
        return when (intent.extras?.getString(FRAGMENT, "")) {
            FRAGMENT_CALENDAR -> PreferencesCalendarFragment()
            FRAGMENT_WEATHER -> PreferencesWeatherFragment()
            FRAGMENT_SERVICES -> PreferencesServicesFragment()
            else -> PreferencesMainFragment()
        }
    }

    private fun setupActionBar() {
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            if (supportFragmentManager.backStackEntryCount == 0) {
                finish()
                startActivity(Intent(this, LauncherActivity::class.java))
            } else {
                supportFragmentManager.popBackStack()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
            startActivity(Intent(this, LauncherActivity::class.java))
        }
    }

    companion object {
        const val RESULT_NEED_RESTART = 0x09
        const val FRAGMENT_WEATHER: String = "weather"
        const val FRAGMENT_CALENDAR: String = "calendar"
        const val FRAGMENT_SERVICES: String = "services"
        const val FRAGMENT: String = "fragment"
    }
}
