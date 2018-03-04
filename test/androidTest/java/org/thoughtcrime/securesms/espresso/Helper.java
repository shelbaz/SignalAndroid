package org.thoughtcrime.securesms.espresso;

import android.support.test.rule.ActivityTestRule;

import org.thoughtcrime.securesms.ConversationListActivity;

public class Helper extends BaseHelper<Helper> {
    public Helper(ActivityTestRule<ConversationListActivity> activityRule) {
        super(activityRule);
    }

    /* NAVIGATION */

    public ConversationsHelper goConversations() {
        return new ConversationsHelper(new HelperSecret());
    }

    public PreferencesHelper resetNickname() {
        return new PreferencesHelper(new HelperSecret());
    }

    public PreferencesHelper setNickname(String message) {
        return new PreferencesHelper(new HelperSecret());
    }

    /* ASSERTIONS */

    public T assertId(int id) {
        try {
            onView(withId(id))
                    .check(matches(isDisplayed()));
        } catch (AmbiguousViewMatcherException e) {}

        return (T)this;
    }

    public T assertText(String text) {
        try {
            onView(withText(text))
                    .check(matches(isDisplayed()));
        } catch (AmbiguousViewMatcherException e) {}

        return (T)this;
    }

    public T assertNoText(String text) {
        try {
            onView(withText(text))
                    .check(matches(isDisplayed()));

            throw new Error("Helper.assertNoText: View found matching \"" + text + "\"");
        } catch (NoMatchingViewException e) {}

        return (T)this;
    }

    /* MISC */

    public String randString() {
        return UUID.randomUUID().toString().substring(0, 16);
    }
}
