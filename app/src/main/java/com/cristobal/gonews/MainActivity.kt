package com.cristobal.gonews

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.cristobal.gonews.api_services.models.RetrofitService
import com.cristobal.gonews.ui.theme.GoNewsTheme
import com.cristobal.gonews.views.BottomTabBar
import com.cristobal.gonews.views.HomeScreen
import com.cristobal.gonews.views.SavedScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            GoNewsTheme (){
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.height(48.dp)
                        )
                    }) { innerPadding ->
                        Greeting(
                            innerPadding
                        )
                }
            }
        }

    }
}

@Composable
fun Greeting(
    innerPadding: PaddingValues = WindowInsets.systemBars.asPaddingValues(),
) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding())
    ) {

        when(selectedTabIndex) {
            0 -> HomeScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            )
            1 -> SavedScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            )
            else -> {

            }
        }

        BottomTabBar(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
                .align(androidx.compose.ui.Alignment.BottomCenter),
            selectedIndex = selectedTabIndex,
            onTabSelected = { index ->
                Log.d("BottomTabBar", "Selected index: $index")
                selectedTabIndex = index
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting()
}