package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

//fun Activity.isKeyboardOpen(): Boolean {
  //  val rootView = window.decorView
    // val rect = Rect()
  //   rootView.getWindowVisibleDisplayFrame(rect)
    // val screenHeight = rootView.height
    //  var keypadHeight = screenHeight - rect.bottom
    //  return keypadHeight > screenHeight * 0.15
//}

  //  val rootView = window.decorView
   // val rect = Rect()
   // rootView.getWindowVisibleDisplayFrame(rect)
   // val screenHeight = rootView.height
  //  var keypadHeight = screenHeight - rect.bottom
  //  return keypadHeight > screenHeight * 0.15
//}
//fun Activity.isKeyboardOpen(visibleThreshold: Float = 100f): Boolean {
 //   val root = findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
 //   val measureRect = Rect()
  //  root.getWindowVisibleDisplayFrame(measureRect)
 //   return root.rootView.height - measureRect.bottom > ceil((visibleThreshold * Resources.getSystem().displayMetrics.density))
//}

//fun Activity.isKeyboardClosed() = !isKeyboardOpen()


fun Activity.getRootView(): View {
    return findViewById<View>(android.R.id.content)
}
fun Context.convertDpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            this.resources.displayMetrics
    )
}
fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = Math.round(this.convertDpToPx(50F))
    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}

fun Activity.hideKeyboard() {
    if (isKeyboardClosed()) return
    val view = currentFocus ?: return
    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}