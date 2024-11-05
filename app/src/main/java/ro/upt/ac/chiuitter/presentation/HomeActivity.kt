package ro.upt.ac.chiuitter.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ro.upt.ac.chiuitter.R
import ro.upt.ac.chiuitter.data.database.ChiuitDbStore
import ro.upt.ac.chiuitter.data.database.RoomDatabase
import ro.upt.ac.chiuitter.domain.Chiuit
import ro.upt.ac.chiuitter.GetChiuitResultContract

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    /**
     * Take a moment to observe how an activity registers for an activity result using the
     * ActivityResultContract API.
     */
    private val getChiuitLauncher = registerForActivityResult(GetChiuitResultContract()) { result ->
        setChiuitText(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = HomeViewModel(ChiuitDbStore(RoomDatabase.getDb(this)))
        setContent { HomeScreen(viewModel) }
    }

    @Composable
    private fun HomeScreen(viewModel: HomeViewModel) {
        val chiuitListState = viewModel.chiuitListState.collectAsState()

        Surface(color = Color.White) {
            Box(modifier = Modifier.fillMaxSize()) {
                // TODO 7: Use a vertical list that composes and displays only the visible items.

                // TODO 8: Make use of Compose DSL to describe the content of the list and make sure
                // to instantiate a [ChiuitListItem] for every item in [chiuitListState.value].
                LazyColumn {
                    items(chiuitListState.value) { chiuit ->
                        ChiuitListItem(chiuit)
                    }
                }

                AddFloatingButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                )
            }
        }
    }

    @Composable
    private fun ChiuitListItem(chiuit: Chiuit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(8.dp),
                    text = chiuit.description,
                )
                Button(
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(8.dp),
                    onClick = { shareChiuit(chiuit.description) }) {
                    Icon(
                        Icons.Filled.Send,
                        stringResource(R.string.send_action_icon_content_description)
                    )
                }
                // TODO 12: Add a new button that has the purpose to delete a chiuit.
            }
        }
    }

    @Composable
    private fun AddFloatingButton(modifier: Modifier) {
        FloatingActionButton(
            modifier = modifier,
            onClick = { composeChiuit() },
        ) {
            Icon(
                Icons.Filled.Edit,
                stringResource(R.string.edit_action_icon_content_description)
            )
        }
    }

    /**
     * Defines text sharing/sending *implicit* intent, opens the application chooser menu,
     * and starts a new activity which supports sharing/sending text.
     */
    private fun shareChiuit(chiuitText: String) {
        // TODO 1: Configure shareIntent to support text sending and set the text extra to chiuitText.
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, chiuitText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    /**
     * Launches the composing activity using the previously defined activity launcher.
     */
    private fun composeChiuit() {
        // TODO 3: Start the ComposeActivity using getChiuitLauncher.
        getChiuitLauncher.launch()
    }

    /**
     * Checks and adds the new text value into the DB.
     */
    private fun setChiuitText(resultText: String?) {
        // TODO 9': Check if text is not null or empty then delegate the addition to the [viewModel].
        if (!resultText.isNullOrEmpty()) {
            viewModel.addChiuit(resultText)
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        HomeScreen(viewModel)
    }

}
