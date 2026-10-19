package ca.gbc.comp3074.onem_mehmet.lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.gbc.comp3074.onem_mehmet.lab04.data.AppDatabase
import ca.gbc.comp3074.onem_mehmet.lab04.ui.CustomerApp
import ca.gbc.comp3074.onem_mehmet.lab04.ui.CustomerViewModel
import ca.gbc.comp3074.onem_mehmet.lab04.ui.CustomerViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)

        setContent {
            MaterialTheme {
                Surface {
                    val viewModel: CustomerViewModel = viewModel(
                        factory = CustomerViewModelFactory(database.customerDao())
                    )
                    CustomerApp(viewModel)
                }
            }
        }
    }
}