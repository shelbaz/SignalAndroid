package org.thoughtcrime.securesms.espresso;

import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.thoughtcrime.securesms.R;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.v4.util.Preconditions.checkNotNull;

public class ViewMatchers {
    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position);
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    public static Matcher<View> visibleIdAtPosition(final int position, int id) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has visible item at position " + position);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    return false;
                }
                View child = viewHolder.itemView.findViewById(id);
                if (child == null || child.getVisibility() != View.VISIBLE) {
                    throw new VisibleErr("ViewMatchers.visibleIdAt: No view found matching " + id + " at child #" + position);
                }
                return true;
            }
        };
    }
}
