package org.thoughtcrime.securesms.espresso;

import org.thoughtcrime.securesms.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.thoughtcrime.securesms.espresso.ViewActions.longClickChildViewWithId;

public class PinnedHelper extends BaseHelper<PinnedHelper> {
    public PinnedHelper(HelperSecret s) {}

    public PinnedHelper unpinMessage(int position) {
        onView(withId(android.R.id.list))
            .perform(actionOnItemAtPosition(position, longClickChildViewWithId(R.id.pinned_message_wrapper)));
        onView(withId(android.R.id.button1))
            .perform(click());

        return this;
    }

    /* NAVIGATION */

    public ConversationHelper goConversation() {
        pressBack();

        return new ConversationHelper(new HelperSecret());
    }
}
