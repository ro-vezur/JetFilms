package com.example.jetfilms.extensions

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavController.navigate(route: String, param: Pair<String, Parcelable>?, builder: NavOptionsBuilder.() -> Unit = {}) {
    param?.let { this.currentBackStackEntry?.arguments?.putParcelable(param.first, param.second)  }

    navigate(route, builder)
}

fun NavController.navigate(route: String, params: List<Pair<String, Parcelable>>?, builder: NavOptionsBuilder.() -> Unit = {}) {
    params?.let {
        val arguments = this.currentBackStackEntry?.arguments
        params.forEach { arguments?.putParcelable(it.first, it.second) }
    }

    navigate(route, builder)
}

fun NavController.navigate(route: Any, params: Bundle?, builder: NavOptionsBuilder.() -> Unit = {}) {
    this.currentBackStackEntry?.arguments?.putAll(params)

    navigate(route, builder)
}

fun NavController.popBackStackOrIgnore() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}