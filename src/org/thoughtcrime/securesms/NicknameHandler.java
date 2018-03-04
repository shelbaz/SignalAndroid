package org.thoughtcrime.securesms;

import android.content.Context;

import org.thoughtcrime.securesms.database.DatabaseFactory;
import org.thoughtcrime.securesms.database.RecipientDatabase;
import org.thoughtcrime.securesms.recipients.Recipient;

public class NicknameHandler {
    private Context           context;
    private RecipientDatabase recipientDatabase;

    public NicknameHandler(Context context) {
        this.context = context;
    }

    public NicknameHandler setContext(Context context) {
        this.context = context;
        return this;
    }

    /**
     * This method is implemented for only testing purposes inorder
     * to pass mocked database object
     *
     * @param recipientDatabase
     * @return
     */
    public NicknameHandler setRecipientDatabase(RecipientDatabase recipientDatabase) {
        this.recipientDatabase = recipientDatabase;
        return this;
    }

    /**
     * If the new nickname is identical to theold one, then
     * it will return false, otherwise, the nickname will be
     * updated and this will return true.
     *
     * @param recipient
     * @param nickname
     * @return boolean indicating whether the nickname was set or not
     */
    public boolean setNickname(Recipient recipient, String nickname) {
        this.setupDatabaseHandler();
        return this.recipientDatabase.setNickname(recipient, nickname);
    }
  
    public boolean removeNickname(Recipient recipient) {
        this.setupDatabaseHandler();
        this.recipientDatabase.setNickname(recipient, null);
        return true;
    }

    /**
     * The method helps the developer to set the proper database
     * without worrying about the details. This method is used
     * in production only.
     *
     * @return NicknameHandler
     */
    public NicknameHandler setupDatabaseHandler() {
        if (this.recipientDatabase == null) {
            this.recipientDatabase = DatabaseFactory.getRecipientDatabase(this.context);
        }
        return this;
    }
}
