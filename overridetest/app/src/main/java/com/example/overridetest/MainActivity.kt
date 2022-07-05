package com.example.overridetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android_lib.other_module.AndroidLibAbstractClass
import com.example.android_lib.other_module.AndroidLibComposableWithLambda
import com.example.android_lib.other_module.AndroidLibComposableWithoutLambda
// import com.example.common.module.CommonCallingComposableWithLambda
// import com.example.common.module.CommonCallingComposableWithoutLambda
// import com.example.common.module.CommonKotlinAbstractClass
import com.example.overridetest.ui.theme.OverrideTestTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      OverrideTestTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          Column {
            Greeting("Android")
            // val myLocalConcreteInstance = AndroidConcrete("Test Callsite")
            // myLocalConcreteInstance.AComposableWithLambda(
            //   input1 = Unit, input2 = " Android"
            // ) @Composable {
            //   Text(text = it)
            // }
            // CommonCallingComposableWithoutLambda(
            //   i1 = Unit, i2 = "No op", objectWithComposables = myLocalConcreteInstance
            // )
            // Text(
            //   text = CommonCallingComposableWithLambda(
            //     i1 = Unit, i2 = " via Common Kotlin",
            //     objectWithComposables = myLocalConcreteInstance
            //   )!!
            // )
            val myLocalInstance = AndroidAppConcreteClass("Test Callsite")
            myLocalInstance.AComposableWithLambda(
              input1 = Unit, input2 = " Android"
            ) @Composable {
              Text(text = it)
            }
            AndroidLibComposableWithoutLambda(
              i1 = Unit, i2 = "No op", objectWithComposables = myLocalInstance
            )
            Text(
              text = AndroidLibComposableWithLambda(
                i1 = Unit, i2 = " via Common Kotlin",
                objectWithComposables = myLocalInstance
              )!!
            )
          }
        }
      }
    }
  }
}

// private class AndroidConcrete(
//   private val payload: String
// ) : CommonKotlinAbstractClass<Unit, String, String>() {
//   @Composable
//   override fun AComposableWithLambda(
//     input1: Unit,
//     input2: String,
//     hoistState: @Composable (String) -> Unit
//   ) {
//     hoistState(payload + input2)
//   }
//
//   @Composable
//   override fun AComposableWithoutLambda(
//     input1: Unit,
//     input2: String
//   ) {
//   }
// }

private class AndroidAppConcreteClass(
  private val payload: String
) : AndroidLibAbstractClass<Unit, String, String>() {
  @Composable
  public override fun AComposableWithLambda(
    input1: Unit,
    input2: String,
    hoistState: @Composable (s: String) -> Unit
  ) {
    println("Can you hear me now? $payload")
    hoistState(payload + input2)
  }

  @Composable
  override fun AComposableWithoutLambda(
    input1: Unit,
    input2: String
  ) {
  }
}

@Composable
fun Greeting(name: String) {
  Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  OverrideTestTheme {
    Greeting("Android")
  }
}