package com.example.ca2_mobileappdev_luarafroes_78337

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // This opens the app and decides which screen the user should see
            // AppPage handles showing either the login or counter screen
            AppPage(onRefresh = { recreate() })
        }
    }
}

// This checks if the user is logged in and shows the correct screen
@Composable
fun AppPage(onRefresh: () -> Unit) {
    // Getting Firebase Authentication so the app can handle user accounts
    val auth = FirebaseAuth.getInstance()

    // Checking if someone is already logged in
    var loggedIn by remember { mutableStateOf(auth.currentUser != null) }

    if (loggedIn) {
        // If the user is logged in, they can see the counter screen
        Box(modifier = Modifier.fillMaxSize()) {
            CounterPage(onRefresh)

            OutlinedButton(
                onClick = {
                    // This logs the user out and takes them back to the login screen
                    auth.signOut()
                    loggedIn = false
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Text("Log out")
            }
        }
    } else {
        // If nobody is logged in, the login screen is shown instead
        LoginScreen(onLoginSuccess = { loggedIn = true })
    }
}

// This function is used for logging in or creating a new account
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val auth = FirebaseAuth.getInstance()

    // These variables store the email, password and any messages shown to the user
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // The user enters their email here
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // The user enters their password here and the characters are hidden
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password (min 6 characters)") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // This button tries to log the user into their Firebase account
            Button(onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        // Checking if the login was successful
                        if (task.isSuccessful) {
                            onLoginSuccess()
                        } else {
                            message = "Login failed: ${task.exception?.message}"
                        }
                    }
            }) {
                Text("Login")
            }

            // This button lets the user create a new account
            OutlinedButton(onClick = {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        // Checking if the account was created successfully
                        if (task.isSuccessful) {
                            onLoginSuccess()
                        } else {
                            message = "Register failed: ${task.exception?.message}"
                        }
                    }
            }) {
                Text("Register")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Any errors from logging in or registering will show here
        Text(text = message)
    }
}

@Composable
fun CounterPage(onRefresh: () -> Unit) {
    // This counter resets back to 0 when the activity is refreshed
    var counter1 by remember { mutableStateOf(0) }

    // This counter keeps its value even after refreshing the activity
    var counter2 by rememberSaveable { mutableStateOf(0) }

    // Passing both counter values and the button actions to CounterScreen
    CounterScreen(
        counter1 = counter1,
        counter2 = counter2,
        onAdd = {
            // Every time the button is pressed, both counters increase by 1
            counter1++
            counter2++
        },
        onRefresh = onRefresh
    )
}

// This function displays the counters and buttons on the screen
// The counter values are stored in CounterPage
@Composable
fun CounterScreen(
    counter1: Int,
    counter2: Int,
    onAdd: () -> Unit,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Counters",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // This card displays the first counter using remember
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("remember (lost on refresh)")
                Text(
                    text = "$counter1",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // This card displays the second counter using rememberSaveable
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("rememberSaveable (kept on refresh)")
                Text(
                    text = "$counter2",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Pressing this button adds 1 to both counters
        Button(onClick = onAdd) {
            Text("Add 1 to both")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // This button refreshes the screen so you can see which counter keeps its value
        OutlinedButton(onClick = onRefresh) {
            Text("Refresh screen")
        }
    }
}