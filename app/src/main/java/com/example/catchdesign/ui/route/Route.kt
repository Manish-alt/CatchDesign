package com.example.catchdesign.ui.route

sealed class Route(val route: String) {
    data object ListScreen: Route("ListScreen")
    data object DetailScreen: Route("DetailScreen")
}