package org.thoughtcrime.securesms.espresso;

import org.thoughtcrime.securesms.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PreferencesHelper extends Helper<PreferencesHelper>{
    public PreferencesHelper(HelperSecret s) {}

    public PreferencesHelper resetNickname() {
        onView(withText("Reset Nickname"))
                .perform(click());

        return new PreferencesHelper(new HelperSecret());
    }

    public PreferencesHelper setNickname(String message) {
        onView(withText("Set Nickname"))
                .perform(click());
        onView(withId(android.R.id.edit))
                .perform(typeText(message));
        onView(withId(android.R.id.button1))
                .perform(click());

        return new PreferencesHelper(new HelperSecret());
    }

    /* NAVIGATION */

    public ConversationHelper goConversation() {
        pressBack();

        return new ConversationHelper(new HelperSecret());
    }
}
