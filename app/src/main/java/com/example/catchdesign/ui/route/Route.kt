package com.example.catchdesign.ui.route

import android.net.Uri

sealed class Route(val route: String) {
    data object ListScreen: Route("ListScreen")
    object DetailScreen : Route("DetailScreen/{title}/{content}") {
        fun createRoute(title: String, content: String): String {
            val encodedTitle = Uri.encode(title)
            val encodedContent = Uri.encode(content)
            return "DetailScreen/$encodedTitle/$encodedContent"
        }
    }
}