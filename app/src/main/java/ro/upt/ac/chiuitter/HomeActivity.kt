package ro.upt.ac.chiuitter

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class HomeActivity : AppCompatActivity() {

    private val chiuitListState = mutableStateOf(DummyChiuitStore.getAllData())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HomeScreen() }
    }

    @Composable
    private fun HomeScreen() {
        Surface(color = Color.White) {
            Box(modifier = Modifier.fillMaxSize()) {
                // TODO 7: Use a vertical list that composes and displays only the visible items.

                // TODO 8: Make use of Compose DSL to describe the content of the list and make sure
                // to instantiate a [ChiuitListItem] for every item in [chiuitListState.value].


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

    /*
    Defines text sharing/sending *implicit* intent, opens the application chooser menu,
    and starts a new activity which supports sharing/sending text.
     */
    private fun shareChiuit(text: String) {
        val sendIntent = Intent().apply {
            // TODO 1: Configure to support text sending/sharing and then attach the text as intent's extra.


        }

        val intentChooser = Intent.createChooser(sendIntent, "")

        startActivity(intentChooser)
    }

    /*
    Defines an *explicit* intent which will be used to start ComposeActivity.
     */
    private fun composeChiuit() {
        // TODO 2: Create an explicit intent which points to ComposeActivity.


        // TODO 3: Start a new activity with the previously defined intent.
        // We start a new activity that we expect to return the acquired text as the result.

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            COMPOSE_REQUEST_CODE -> if (resultCode == RESULT_OK) extractText(data)
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun extractText(data: Intent?) {
        data?.let {
            // TODO 5: Extract the text from result intent.


            // TODO 6: Check if text is not null or empty, then set the new "chiuitText".


            // TODO 9: Instantiate a new chiuit object then add it to [chiuitListState.value].
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun DefaultPreview() {
        HomeScreen()
    }

    companion object {
        const val COMPOSE_REQUEST_CODE = 1213
    }

}
