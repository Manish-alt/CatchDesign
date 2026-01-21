package com.example.catchdesign.ui.screen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                        viewModel,
                        state,
                        navController = navController
                    )
                }

                composable(
                    route = Route.DetailScreen.route,
                    arguments = listOf(
                        navArgument("title") { type = NavType.StringType },
                        navArgument("content") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    // Extract the strings from the navigation arguments
                    val title = backStackEntry.arguments?.getString("title") ?: ""
                    val content = backStackEntry.arguments?.getString("content") ?: ""

                    DetailScreen(
                        title = title,
                        content = content,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListView(
    viewModel: MainViewModel,
    state: MainUiState,
    navController: NavController
) {
    val isRefreshing = (state as? MainUiState.Success)?.isRefreshing ?: false
    Box(modifier = Modifier.fillMaxSize()) {
        when (state){
            is MainUiState.Loading -> {
                CircularProgressRing(modifier = Modifier.align(Alignment.Center))
            }

            is MainUiState.Success -> {
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        // This triggers your ViewModel fetch
                        viewModel.loadUsers()
                    },
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn {
                        items(state.users.size) { item ->
                            ListViewRow(
                                data = state.users[item],
                                onClick = {
                                    navController.navigate(
                                        Route.DetailScreen.createRoute(
                                            title = state.users[item].title.toString(),
                                            content = state.users[item].content.toString()
                                        )
                                    )
                                }
                            )
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
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


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DetailScreen(
        title: String,
        content: String,
        onBackClick: () -> Unit
    ) {
        Scaffold(
            topBar = {
                CustomNavigationBar(
                    title = title,
                    onBackClick = onBackClick
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = content,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp, // Equivalent to lineSpacing(4)
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }

@Composable
fun CustomNavigationBar(
    title: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp) // Standard iOS/Android bar height
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        // Centered Title
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        // Left-aligned Back Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null, // Removes ripple to mimic iOS feel
                        onClick = onBackClick
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color(0xFF070932), // Your custom hex color
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Back",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF070932)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

