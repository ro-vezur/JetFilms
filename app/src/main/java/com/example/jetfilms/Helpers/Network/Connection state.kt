package com.example.jetfilms.Helpers.Network

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}