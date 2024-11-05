package ro.upt.ac.chiuitter

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ro.upt.ac.chiuitter.presentation.ComposeActivity

class GetChiuitResultContract : ActivityResultContract<Unit, String?>() {

    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(context, ComposeActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        if (resultCode == Activity.RESULT_OK && intent != null) {
            return intent.getStringExtra(EXTRA_TEXT)
        }
        return null
    }

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }
}