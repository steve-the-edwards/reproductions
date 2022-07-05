package com.example.common.module

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

abstract class CommonKotlinAbstractClass<I1, I2, T> {
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

@Composable fun <I1, I2, T> CommonCallingComposableWithLambda(
  i1: I1,
  i2: I2,
  objectWithComposables: CommonKotlinAbstractClass<I1, I2, T>
): T? {
  val payload: MutableState<T?> = remember { mutableStateOf(null) }
  objectWithComposables.AComposableWithoutLambda(i1, i2)
  objectWithComposables.AComposableWithLambda(i1, i2) @Composable {
    payload.value = it
  }
  return payload.value
}

@Composable fun <I1, I2, T> CommonCallingComposableWithoutLambda(
  i1: I1,
  i2: I2,
  objectWithComposables: CommonKotlinAbstractClass<I1, I2, T>
): Unit {
  objectWithComposables.AComposableWithoutLambda(i1, i2)
}
