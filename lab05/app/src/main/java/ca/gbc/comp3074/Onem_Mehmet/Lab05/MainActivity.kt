package ca.gbc.comp3074.Onem_Mehmet.Lab05

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import java.text.DateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LocationScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}


private val LOCATION_PERMISSIONS = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

private fun hasFinePermission(context: Context): Boolean =
    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

@SuppressLint("MissingPermission") // only called after FINE permission is confirmed
private fun startGpsUpdates(
    locationManager: LocationManager,
    listener: LocationListener,
    onLastKnown: (Location) -> Unit
) {
    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let(onLastKnown)
    locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER,
        2000L,  // min time between updates (ms)
        0f,     // min distance (m)
        listener,
        Looper.getMainLooper()
    )
}

@Composable
fun LocationScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val locationManager = remember {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    var hasPermission by remember { mutableStateOf(hasFinePermission(context)) }
    var permissionDenied by remember { mutableStateOf(false) }
    var gpsEnabled by remember {
        mutableStateOf(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    }
    var location by remember { mutableStateOf<Location?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        // GPS needs precise location; approximate-only counts as denied
        hasPermission = result[Manifest.permission.ACCESS_FINE_LOCATION] == true
        permissionDenied = !hasPermission
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) permissionLauncher.launch(LOCATION_PERMISSIONS)
    }

    // Restarts when permission changes; removes updates when the screen is disposed
    DisposableEffect(hasPermission) {
        val listener = object : LocationListener {
            override fun onLocationChanged(loc: Location) {
                location = loc
            }

            override fun onProviderEnabled(provider: String) {
                gpsEnabled = true
            }

            override fun onProviderDisabled(provider: String) {
                gpsEnabled = false
            }

            // Needed on API < 30 where this method has no default implementation
            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }
        }
        if (hasPermission) {
            startGpsUpdates(locationManager, listener) { location = it }
        }
        onDispose { locationManager.removeUpdates(listener) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Current GPS Location",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(24.dp))

        when {
            !hasPermission -> {
                Text(
                    text = if (permissionDenied)
                        "Precise location permission was denied. The app needs it to read GPS coordinates."
                    else
                        "This app needs location permission to read your GPS coordinates.",
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = { permissionLauncher.launch(LOCATION_PERMISSIONS) }) {
                    Text("Grant Permission")
                }
                if (permissionDenied) {
                    TextButton(onClick = {
                        context.startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                        )
                    }) {
                        Text("Open App Settings")
                    }
                }
            }

            !gpsEnabled -> {
                Text("GPS is turned off.", textAlign = TextAlign.Center)
                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }) {
                    Text("Turn On GPS")
                }
            }

            location == null -> {
                CircularProgressIndicator()
                Spacer(Modifier.height(16.dp))
                Text("Waiting for GPS signal...")
            }

            else -> {
                val loc = location!!
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        CoordinateRow("Latitude", String.format(Locale.US, "%.6f", loc.latitude))
                        CoordinateRow("Longitude", String.format(Locale.US, "%.6f", loc.longitude))
                        CoordinateRow("Accuracy", String.format(Locale.US, "%.1f m", loc.accuracy))
                        CoordinateRow(
                            "Last update",
                            DateFormat.getTimeInstance().format(Date(loc.time))
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Source: GPS provider (updates every 2 seconds)",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun CoordinateRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
    }
}