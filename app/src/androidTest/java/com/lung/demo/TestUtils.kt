package com.lung.demo

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import android.widget.EditText


fun recyclerViewAtPositionOnView(
    position: Int,
    itemMatcher: Matcher<View>, @NonNull targetViewId: Int
): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has view id $itemMatcher at position $position")
        }

        public override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            val targetView = viewHolder?.itemView?.findViewById<View>(targetViewId) ?: return false
            return itemMatcher.matches(targetView)
        }
    }
}


fun typeTextOnViewChild(viewId: Int, text: String) = object : ViewAction {
    override fun getConstraints() = null

    override fun getDescription() = "Type text on a child view with specified id."

    override fun perform(uiController: UiController, view: View) =
        ViewActions.typeText(text).perform(uiController, view.findViewById<View>(viewId))
}

fun clearTextOnViewChild(viewId: Int) = object : ViewAction {
    override fun getConstraints() = null

    override fun getDescription() = "Clear text on a child view with specified id."

    override fun perform(uiController: UiController, view: View) =
        ViewActions.clearText().perform(uiController, view.findViewById<View>(viewId))
}

fun editTextWithItemHint(matcherText: String): Matcher<View> {
    return object : BoundedMatcher<View, EditText>(EditText::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("with item hint: $matcherText")
        }

        override fun matchesSafely(editTextField: EditText): Boolean {
            return matcherText.equals(editTextField.hint.toString(), ignoreCase = true)
        }
    }
}

