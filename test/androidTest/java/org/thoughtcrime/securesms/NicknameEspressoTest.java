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
    public ActivityTestRule<ConversationListActivity> mainActivityRule =
            new ActivityTestRule(ConversationListActivity.class, true, false);

    @Test
    public void pageExists(){
        Helper otherHelper = new Helper(mainActivityRule);

        otherHelper
                .goConversations()
                .goConversation()
                .goSettings();

    }

    @Test
    public void settingNickname()
    {
        Helper helper = new Helper(mainActivityRule);

        helper
                .goConversations()
                .goConversation()
                .goSettings()
                .setNickname("Testing");
        helper
                .goConversations()
                .goConversation();

        onView(withId(R.id.action_bar_container));


    }
    @Test
    public void resettingNickname()
    {
        Helper helper = new Helper(mainActivityRule);
        helper
                .goConversations()
                .goConversation()
                .goSettings()
                .setNickname("Testing")
                .resetNickname();

    }




}
