package org.thoughtcrime.securesms.espresso;

import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.NoMatchingViewException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

class BaseRecyclerHelper<T> extends Helper {

    /* ASSERTIONS */

    public T assertIdAt(int id, int position) {
        try {
            onView(withId(android.R.id.list))
                .perform(scrollToPosition(position))
                .check(matches(ViewMatchers.atPosition(position, hasDescendant(withId(id)))));
        } catch (AmbiguousViewMatcherException e) {}

        return (T)this;
    }

    public T assertTextAt(String text, int position) {
        try {
            onView(withId(android.R.id.list))
                .perform(scrollToPosition(position))
                .check(matches(ViewMatchers.atPosition(position, hasDescendant(withText(text)))));
        } catch (AmbiguousViewMatcherException e) {}

        return (T)this;
    }

    public T assertNoTextAt(String text, int position) {
        try {
            onView(withId(android.R.id.list))
                .perform(scrollToPosition(position))
                .check(matches(ViewMatchers.atPosition(position, hasDescendant(withText(text)))));

            throw new Error("Helper.assertNoText: View found matching \"" + text + "\" at child #" + position);
        } catch (NoMatchingViewException e) {}

        return (T)this;
    }
}
