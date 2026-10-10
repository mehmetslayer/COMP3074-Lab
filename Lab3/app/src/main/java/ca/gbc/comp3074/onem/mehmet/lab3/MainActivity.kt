package ca.gbc.comp3074.onem.mehmet.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.gbc.comp3074.onem.mehmet.lab3.ui.theme.Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Conversation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun MessageCard(author: String, body: String) {
    Row(modifier = Modifier.padding(8.dp)) {
        Surface(
            modifier = Modifier.size(52.dp),
            shape = CircleShape,
            color = Color(0xFFF2F2F2),
            border = BorderStroke(2.dp, Color.Red)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Aurora Players",
                tint = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
        }

        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = author, color = Color.DarkGray)
            Surface(
                modifier = Modifier.padding(top = 4.dp),
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 2.dp,
                color = Color.White
            ) {
                Text(
                    text = body,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

// Conversation list
@Composable
fun Conversation(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFDF7FF))
    ) {
        items(3) {
            // Every time there's 4 messages show up * ( 3 )
            Column {
                MessageCard("XANTARES", "GOOD PLAY!")
                MessageCard("JIMIIIIIII", "WHERE ARE YOU")
                MessageCard("WOXIC", "Rush b ")
                MessageCard("Wicadia", "You're a starboy!")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConversationPreview() {
    Lab3Theme {
        Conversation()
    }
}