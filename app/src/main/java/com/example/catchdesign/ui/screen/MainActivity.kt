package com.example.catchdesign.ui.screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catchdesign.R
import com.example.catchdesign.model.ResponseModel
import com.example.catchdesign.ui.route.Route
import com.example.catchdesign.ui.state.MainUiState
import com.example.catchdesign.ui.theme.CatchDesignTheme
import com.example.catchdesign.viewModel.MainViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MainScreen(viewModel: MainViewModel = getViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val navController = rememberNavController()

    CatchDesignTheme {

            NavHost(navController = navController, startDestination = Route.ListScreen.route) {
                composable(Route.ListScreen.route) {
                    ListView(
                        state, navController = navController
                    )
                }

                composable(Route.DetailScreen.route) {

                }
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListView(
    state: MainUiState,
    navController: NavController
) {

    Box(modifier = Modifier.fillMaxSize()) {
        when (state){
            is MainUiState.Loading -> {
                CircularProgressRing(modifier = Modifier.align(Alignment.Center))
            }

            is MainUiState.Success -> {
                LazyColumn {
                    items(state.users.size) { item ->
                        ListViewRow (
                            data = state.users[item],
                            onClick = {
                                navController.navigate(Route.DetailScreen.route)
                            }
                        )
                    }
                }
            }

            is MainUiState.Error -> {
                showMessage(state.error.message)
            }
        }


    }


}

@Composable
fun ListViewRow(
    data: ResponseModel,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 20.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = data.title.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = data.subtitle.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
fun showMessage(message: String) {
    val context = LocalContext.current
    LaunchedEffect(message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun CircularProgressRing(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = Modifier.size(40.dp)) {
        val strokeWidth = 40.dp.toPx()

        // Background Circle
        drawCircle(
            color = Color.White.copy(alpha = 1f),
            style = Stroke(width = strokeWidth)
            )

        // Progress Arc with gradient
        drawArc(
            brush = Brush.sweepGradient(
                colors = listOf(Color.White, Color.Red),
                center = center
            ),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)

        )

    }
}


@Composable
fun DetailScreen(){

}

