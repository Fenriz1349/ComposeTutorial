package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    // ajout de l'image pour le message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(RoundedCornerShape(25.dp))
                .border(1.5.dp, color = MaterialTheme.colorScheme.secondary, RoundedCornerShape(25.dp))
        )

        // Espace horizontale entre l'image et la colonne
        Spacer(modifier = Modifier.width(8.dp))
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor est mis à jour graduellement d'une couleur à l'autre
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )
        Column (modifier = Modifier.clickable { isExpanded = !isExpanded }){
            Text(text = msg.author,
                color = MaterialTheme.colorScheme.secondary)
            // Ajout d'un espace vertical entre l'auteur et le message
            Spacer(modifier = Modifier.height(4.dp))
            Surface(shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                // surfaceColor color va changer graduellement de primary à surface
                color = surfaceColor,
                // animateContentSizeva changer la taille de Surface graduellement
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    //si cliké etend tout le message, sinon affiche une ligne
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}
//activation du mode sombre par defaut
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
//previsialisation de la View
@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}
