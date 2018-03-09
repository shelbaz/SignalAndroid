package org.thoughtcrime.securesms;

import org.thoughtcrime.securesms.database.model.MessageRecord;

import java.util.Iterator;
import java.util.LinkedList;

public class SearchHandler {

    public LinkedList<MessageRecord> messageRecordList;
    public LinkedList<Integer> searchResultList;

    public SearchHandler() {
        messageRecordList = new LinkedList<MessageRecord>();
        searchResultList = new LinkedList<Integer>();
    }

    public void search(String term) {
        //reset searchResultList
        //search messageRecordList and push positions into searchResultList
    }

    //Used to add messageRecords when conversation gets new messages
    public void addMessageRecord(MessageRecord messageRecord) {

    }

    //Used to delete messageRecords when user deleted message records from conversation
    public void deleteMessageRecord(long messageId) {
        Iterator<MessageRecord> iterator = messageRecordList.iterator();
        
        while (iterator.hasNext()) {
            MessageRecord messageRecord = iterator.next();
            if (messageRecord.getId() == messageId) {
                iterator.remove();
                continue;
            }
        }
    }
    //returns the next element in the searchResultList to scrollTo
    public int getNextSearchResult(int index) {
        //not sure if we should increment from this class
        return 0;
    }

    public boolean hasResults() {
        return searchResultList.size() > 0;
    }

    public int getResultNumber() {
        return searchResultList.size();
    }

    public boolean hasMessageRecords() {
        return messageRecordList.size() > 0;
    }
}
