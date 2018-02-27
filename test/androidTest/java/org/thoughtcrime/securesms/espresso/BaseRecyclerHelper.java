package org.thoughtcrime.securesms.espresso;

import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.NoMatchingViewException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.thoughtcrime.securesms.espresso.ViewMatchers.atPosition;

class BaseRecyclerHelper<T> extends Helper {

    /* ASSERTIONS */

    public T assertIdAt(int id, int position, boolean checkVisible) {
        try {
            onView(withId(android.R.id.list))
                .perform(scrollToPosition(position))
                .check(matches(atPosition(position, hasDescendant(withId(id)), id)));
        } catch (AmbiguousViewMatcherException e) {}

        return (T)this;
    }

    public T assertNoIdAt(int id, int position, boolean checkVisible) {
        try {
            onView(withId(android.R.id.list))
                .perform(scrollToPosition(position))
                .check(matches(atPosition(position, hasDescendant(withId(id)), id)));

            throw new Error("Helper.assertNoIdAt: View found matching \"" + id + "\" at child #" + position);
        } catch (Error e) {
            if (e.getMessage().contains("Helper.assertNoIdAt")) {
                throw e;
            }
        }

        return (T)this;
    }

    public T assertTextAt(String text, int position) {
        try {
            onView(withId(android.R.id.list))
                .perform(scrollToPosition(position))
                .check(matches(atPosition(position, hasDescendant(withText(text)), -1)));
        } catch (AmbiguousViewMatcherException e) {}

        return (T)this;
    }

    public T assertNoTextAt(String text, int position) {
        try {
            onView(withId(android.R.id.list))
                .perform(scrollToPosition(position))
                .check(matches(atPosition(position, hasDescendant(withText(text)), -1)));

            throw new Error("Helper.assertNoTextAt: View found matching \"" + text + "\" at child #" + position);
        } catch (NoMatchingViewException e) {}

        return (T)this;
    }
}
