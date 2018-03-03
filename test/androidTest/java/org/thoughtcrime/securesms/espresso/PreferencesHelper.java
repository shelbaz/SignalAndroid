package org.thoughtcrime.securesms.espresso;

import org.thoughtcrime.securesms.R;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PreferencesHelper extends Helper<PreferencesHelper>{
    public PreferencesHelper(HelperSecret s) {}

    public PreferencesHelper resetNickname()
    {
        onView(withText("Reset Nickname"))
                .perform(click());

        return new PreferencesHelper(new HelperSecret());
    }

    public PreferencesHelper setNickname(String message)
    {
        onView(withText("Set Nickname"))
                .perform(click());

        onView(withId(R.id.action_bar_root))
                .perform(typeText(message));

        onView(withText("OK"))
                .perform(click());

        return new PreferencesHelper(new HelperSecret());
    }


}
