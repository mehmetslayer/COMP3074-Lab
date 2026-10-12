package ca.gbc.comp3074.Onem_Mehmet.Assignment1
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ca.gbc.comp3074.Onem_Mehmet.Assignment1.ui.theme.Assignment1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    var numberOfWork by remember { mutableStateOf("") }
    var hourlyRate by remember { mutableStateOf("") }
    var totalPay by remember { mutableDoubleStateOf(0.0) }
    var overtimePay by remember { mutableDoubleStateOf(0.0) }
    var tax by remember { mutableDoubleStateOf(0.0) }
    var taxRateText by remember { mutableStateOf("") }
    var pay by remember { mutableDoubleStateOf(0.0) }
    val context = LocalContext.current


    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = numberOfWork,
            onValueChange = { numberOfWork = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text("Number of hours worked") }

        )
        Text("You typed: $numberOfWork")

        TextField(
            value = hourlyRate,
            onValueChange = { hourlyRate = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text("Hourly rate") }
        )
        Text("You typed: $hourlyRate")

        TextField(
            value = taxRateText,
            onValueChange = { taxRateText = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text("Tax Rate (e.g. 0.11)") }
        )
        Text("You typed: $taxRateText")



        Button(onClick = {
            // If the input is invalid, toDoubleOrNull() returns null and 0.0 is used instead, so the app won't crash
            // even with the decimal keyboard, the user can still enter invalid input
            val numberOfW = numberOfWork.toDoubleOrNull() ?: 0.0
            val hourRate = hourlyRate.toDoubleOrNull() ?: 0.0
            val taxRate = taxRateText.toDoubleOrNull() ?: 0.0


            if (numberOfW <= 40) {
                pay = numberOfW * hourRate
                overtimePay = 0.0

            } else {
                pay = 40 * hourRate
                overtimePay = (numberOfW - 40) * hourRate * 1.5
            }
            totalPay = pay + overtimePay

            tax = pay * taxRate
        })
        { Text("Calculate") }



        Text("Pay: $pay")
        Text("Overtime Pay: $overtimePay")
        Text("Total Pay: $totalPay")
        Text("Tax: $tax")

        // Opens AboutActivity to show the student's name and ID
        Button(onClick = {
            context.startActivity(Intent(context, AboutActivity::class.java))
        }){
            Text("About")
        }
    }
}