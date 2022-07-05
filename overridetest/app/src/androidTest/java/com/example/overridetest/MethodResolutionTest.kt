package com.example.overridetest

import androidx.compose.runtime.BroadcastFrameClock
import androidx.compose.runtime.Composable
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.molecule.launchMolecule
import com.example.android_lib.other_module.AndroidLibAbstractClass
import com.example.android_lib.other_module.AndroidLibComposableWithLambda
import com.example.android_lib.other_module.AndroidLibComposableWithoutLambda
import kotlinx.coroutines.CoroutineScope

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MethodResolutionTest {

  private class AndroidAppConcreteTestClass(
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

  @Test fun testMethodResolution() {
    val objectUnderTest = AndroidAppConcreteTestClass("a test")
    val broadcastFrameClock = BroadcastFrameClock {}
    val testScope = CoroutineScope(broadcastFrameClock)

    val testFlow = testScope.launchMolecule {
      AndroidLibComposableWithoutLambda(Unit, " again", objectUnderTest)
      AndroidLibComposableWithLambda(Unit, " again", objectUnderTest)
    }

    assert(testFlow.value.contentEquals("a test again"))
  }
}