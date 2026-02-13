package com.cristobal.gonews.views

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

sealed class BottomNavItem(val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("Home", Icons.Filled.Home)
    object Saved : BottomNavItem("Saved", Icons.Filled.Bookmark)
}

@Composable
fun BottomTabBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {}
) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Saved)
    NavigationBar (
        modifier = modifier,
        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
    ){
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier
                    .background(
                        if (selectedIndex == index) {
                            androidx.compose.material3.MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                        } else {
                            androidx.compose.material3.MaterialTheme.colorScheme.primary
                        }
                    ),
                selected = selectedIndex == index,
                onClick = { onTabSelected(index) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors =
                    androidx.compose.material3.NavigationBarItemDefaults.colors(
                        selectedIconColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
                        selectedTextColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
                        indicatorColor = androidx.compose.material3.MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
                    )

            )
        }
    }
}

@Preview
@Composable
fun BottomTabBarPreview() {
    var selectedIndex by remember { mutableStateOf(0) }
    BottomTabBar(selectedIndex = selectedIndex, onTabSelected = { selectedIndex = it })
}