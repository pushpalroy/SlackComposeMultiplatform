package dev

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

sealed class Keyboard {
  data class Opened(var height: Int) : Keyboard()
  object Closed : Keyboard()
  object HardwareKeyboard: Keyboard()
}

@Composable
expect fun keyboardAsState(): State<Keyboard>