package com.example.jetfilms.Network

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}