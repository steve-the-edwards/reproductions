package com.example.android_lib.other_module

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

abstract class AndroidLibAbstractClass<I1, I2, T> {
  @Composable
  abstract fun AComposableWithLambda(
    input1: I1,
    input2: I2,
    hoistState: @Composable (T) -> Unit
  ): Unit

  @Composable
  abstract fun AComposableWithoutLambda(
    input1: I1,
    input2: I2,
  ): Unit
}

@Composable fun <I1, I2, T> AndroidLibComposableWithLambda(
  i1: I1,
  i2: I2,
  objectWithComposables: AndroidLibAbstractClass<I1, I2, T>
): T? {
  val payload: MutableState<T?> = remember { mutableStateOf(null) }
  objectWithComposables.AComposableWithLambda(i1, i2) @Composable {
    payload.value = it
  }
  return payload.value
}

@Composable fun <I1, I2, T> AndroidLibComposableWithoutLambda(
  i1: I1,
  i2: I2,
  objectWithComposables: AndroidLibAbstractClass<I1, I2, T>
): Unit {
  objectWithComposables.AComposableWithoutLambda(i1, i2)
}
