package org.thoughtcrime.securesms;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thoughtcrime.securesms.espresso.Helper;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class NicknameEspressoTest {
    @Rule
    public ActivityTestRule<RecipientPreferenceActivity> mainActivityRule =
            new ActivityTestRule(RecipientPreferenceActivity.class, true, false);

    public ActivityTestRule<ConversationListActivity> otherActivityRule =
            new ActivityTestRule(ConversationListActivity.class, true, false);

    @Test
    public void pageExists(){
        Helper otherHelper = new Helper(otherActivityRule);

        otherHelper
                .goConversations()
                .goConversation()
                .goSettings();

    }

    public void settingNickname()
    {
        Helper helper = new Helper(mainActivityRule);
        Helper otherHelper = new Helper(otherActivityRule);

        helper
                .setNickname("Testing");
        otherHelper
                .goConversations()
                .goConversation();

        onView(withId(R.id.action_bar_container));


    }

    public void resettingNickname()
    {
        Helper helper = new Helper(mainActivityRule);

        helper
                .resetNickname();

    }




}
