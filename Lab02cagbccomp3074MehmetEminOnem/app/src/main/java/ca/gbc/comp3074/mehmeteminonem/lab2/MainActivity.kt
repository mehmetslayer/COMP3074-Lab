package ca.gbc.comp3074.mehmeteminonem.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.gbc.comp3074.mehmeteminonem.lab2.ui.theme.Lab02cagbccomp3074MehmetEminOnemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab02cagbccomp3074MehmetEminOnemTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HelloContent(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun HelloContent(name: String, modifier: Modifier) {
    var count by remember { mutableStateOf(0) }
    var step by remember { mutableStateOf(1) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.wazowski),
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(top = 32.dp)
        )

        Text(
            text = "$count",
            fontSize = 32.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilledTonalButton(
                onClick = {
                    count -= step
                          },
                colors = ButtonDefaults.filledTonalButtonColors(containerColor = Color(0xFF9DDB80))
            ) {
                Text("-")
            }
            FilledTonalButton(
                onClick = {
                    count += step
                          },
                colors = ButtonDefaults.filledTonalButtonColors(containerColor = Color(0xFF9DDB80))
            ) {
                Text("+")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilledTonalButton(
                onClick = {
                    count = 0
                    step = 1
                },
                colors = ButtonDefaults.filledTonalButtonColors(containerColor = Color(0xFFE0355C))
            ) {
                Text("Reset")
            }
            FilledTonalButton(
                onClick = {
                    step = if (step == 1) 2 else 1
                          },
                colors = ButtonDefaults.filledTonalButtonColors(containerColor = Color(0xFF6FCF6F))
            ) {
                Text("Step")
            }
        }
    }
}

@Composable
fun TonalButtonNeg(onClick: () -> Unit) {
    FilledTonalButton(onClick = {
        onClick()
    })
    {
        Text(" - ")
    }
}

@Composable
fun TonalButtonPlus(onClick: () -> Unit) {
    FilledTonalButton(onClick = {
        onClick()
    })
    {
        Text(" + ")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab02cagbccomp3074MehmetEminOnemTheme {
        HelloContent("idk", Modifier)
    }
}