package org.thoughtcrime.securesms.nickname;

import android.os.Looper;
import android.os.Parcel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.thoughtcrime.securesms.BaseUnitTest;
import org.thoughtcrime.securesms.database.Address;
import org.thoughtcrime.securesms.recipients.Recipient;
import org.thoughtcrime.securesms.database.RecipientDatabase.RecipientSettings;
import org.thoughtcrime.securesms.util.Util;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.thoughtcrime.securesms.recipients.RecipientProvider.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
public class RecipientGetterSetterTests extends BaseUnitTest {

    Parcel            parcel;
    RecipientDetails  details;
    Recipient         recipient;
    RecipientSettings recipientSettings;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.initStaticMocks();

        this.parcel = mock(Parcel.class);
        this.recipientSettings = mock(RecipientSettings.class);

        when(this.parcel.readString()).thenReturn("Jon Snow");
    }

    private void initStaticMocks() {
        PowerMockito.mockStatic(Looper.class);
        BDDMockito.given(Looper.getMainLooper()).willReturn(null);

        PowerMockito.mockStatic(Util.class);
        BDDMockito.given(Util.uri(any())).willReturn(null);
    }

    private void setProfileName(String name) {
        when(this.recipientSettings.getProfileName()).thenReturn(name);
    }

    private void setInitialNickname(String name) {
        when(this.recipientSettings.getNickname()).thenReturn(name);
    }

    private void setInitSystemDisplayName(String name) {
        when(this.recipientSettings.getSystemDisplayName()).thenReturn(name);
    }

    @Test
    public void testUserWithNoNicknameInitially() {
        this.setInitialNickname(null);
        this.setProfileName("Jon snow");
        this.setInitSystemDisplayName("Jon snow");

        this.details = new RecipientDetails(null, 1l, false, this.recipientSettings, null);
        this.recipient = new Recipient(new Address(this.parcel), this.details);

        assertEquals(this.recipient.getName(), "Jon snow");
        verify(this.recipientSettings).getNickname();
        verify(this.recipientSettings).getProfileName();
        verify(this.recipientSettings).getSystemDisplayName();
    }

    @Test
    public void testUserWithNicknameInitially() {
        this.setInitialNickname("Jon Targaryen");
        this.setProfileName("Jon snow");
        this.setInitSystemDisplayName("Jon snow");

        this.details = new RecipientDetails(null, 1l, false, this.recipientSettings, null);
        this.recipient = new Recipient(new Address(this.parcel), this.details);

        assertEquals(this.recipient.getName(), "Jon Targaryen");
        verify(this.recipientSettings).getNickname();
        verify(this.recipientSettings).getProfileName();
        verify(this.recipientSettings).getSystemDisplayName();
    }

    @Test
    public void testSetNewNickname() {
        this.setInitialNickname(null);
        this.setProfileName("Jon snow");
        this.setInitSystemDisplayName("Jon snow");

        this.details = new RecipientDetails(null, 1l, false, this.recipientSettings, null);
        this.recipient = new Recipient(new Address(this.parcel), this.details);

        assertEquals(this.recipient.getName(), "Jon snow");
        this.recipient.setNickname("LOOOOOOOL");
        assertEquals(this.recipient.getName(), "LOOOOOOOL");

        verify(this.recipientSettings).getNickname();
        verify(this.recipientSettings).getProfileName();
        verify(this.recipientSettings).getSystemDisplayName();
    }

    @Test
    public void testOverrideOldNickname() {
        this.setInitialNickname("Jon Targaryen");
        this.setProfileName("Jon snow");
        this.setInitSystemDisplayName("Jon snow");

        this.details = new RecipientDetails(null, 1l, false, this.recipientSettings, null);
        this.recipient = new Recipient(new Address(this.parcel),this.details);

        assertEquals(this.recipient.getName(), "Jon Targaryen");
        this.recipient.setNickname("LOOOOOOOL");
        assertEquals(this.recipient.getName(), "LOOOOOOOL");

        verify(this.recipientSettings).getNickname();
        verify(this.recipientSettings).getProfileName();
        verify(this.recipientSettings).getSystemDisplayName();
    }

    @Test
    public void testRemoveNickname() {
        this.setInitialNickname("Jon Targaryen");
        this.setProfileName("Jon snow");
        this.setInitSystemDisplayName("Jon snow");

        this.details = new RecipientDetails(null, 1l, false, this.recipientSettings, null);
        this.recipient = new Recipient(new Address(this.parcel), this.details);

        assertEquals(this.recipient.getName(), "Jon Targaryen");
        this.recipient.setNickname(null);
        assertEquals(this.recipient.getName(), "Jon snow");

        verify(this.recipientSettings).getNickname();
        verify(this.recipientSettings).getProfileName();
        verify(this.recipientSettings).getSystemDisplayName();
    }
}

