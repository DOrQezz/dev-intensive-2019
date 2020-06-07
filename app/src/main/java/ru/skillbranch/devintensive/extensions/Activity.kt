package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager

fun Activity.isKeyboardOpen(): Boolean {
    var r = Rect()
    val rootView = window.decorView
    rootView.getWindowVisibleDisplayFrame(r)
    val screenHeight = rootView.height
    var heightDiff = screenHeight - (r.bottom - r.top)
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        heightDiff -= resources.getDimensionPixelSize(resourceId)
    }
    if (heightDiff > 100) {
        return true
    }
    return false
}

fun Activity.isKeyboardClose() = !isKeyboardOpen()

fun Activity.hideKeyboard() {
    if (isKeyboardClose()) return
    val view = currentFocus?: return
    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}