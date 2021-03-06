package org.thoughtcrime.securesms.espresso;

import org.thoughtcrime.securesms.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PreferencesHelper extends BaseHelper<PreferencesHelper>{
    public PreferencesHelper(HelperSecret s) {}

    public PreferencesHelper resetNickname() {
        onView(withId(android.R.id.content))
            .perform(swipeUp());
        onView(withText(R.string.RecipientPreferenceActivity_resetnickname))
            .perform(click());

        return new PreferencesHelper(new HelperSecret());
    }

    public PreferencesHelper setNickname(String message) {
        onView(withId(android.R.id.content))
            .perform(swipeUp());
        // TODO fix error where this does nothing
        onView(withText(R.string.RecipientPreferenceActivity_setnickname))
            .perform(click());
        onView(withId(android.R.id.edit))
            .perform(replaceText(message));
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
