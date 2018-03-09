package org.thoughtcrime.securesms;

import org.thoughtcrime.securesms.database.model.MessageRecord;

import java.util.Iterator;
import java.util.LinkedList;

public class SearchHandler {

    public LinkedList<MessageRecord> messageRecordList;
    public LinkedList<SearchResult> searchResultList;
    private int searchIndex = 0;

    public SearchHandler() {
        messageRecordList = new LinkedList<MessageRecord>();
        searchResultList = new LinkedList<SearchResult>();
    }

    public void search(String term) {
        searchIndex = 0;
        searchResultList.clear();

        //search messageRecordList and push position (which is the index of the list) and    messageRecord into searchResultList
        Iterator<MessageRecord> iterator = messageRecordList.iterator();
        while (iterator.hasNext()) {
            MessageRecord messageRecord = iterator.next();
            if (messageRecord.getBody().getBody().toLowerCase().contains(term)) {
                SearchResult searchResult = new SearchResult(searchIndex, messageRecord);
                searchResultList.add(searchResult);
            }
            searchIndex++;
        }

    }

    //Used to add messageRecords when conversation gets new messages
    public void addMessageRecord(MessageRecord messageRecord) {
        messageRecordList.addFirst(messageRecord);
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
    //returns the next position in the searchResultList to scrollTo
    public SearchResult getNextResultPosition() {
        if (searchIndex < getResultNumber()) return searchResultList.get(searchIndex++);

        return null;
    }

    //returns the previous position in the searchResultList
    public SearchResult getPreviousResultPosition() {
        if (searchIndex-- > -1) return searchResultList.get(searchIndex--);

        return null;
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

    //not sure if should create seperate class file, needs to be accessed outside
    public class SearchResult {
        private int position;
        private MessageRecord messageRecord;

        public SearchResult(int position, MessageRecord messageRecord) {
            this.position = position;
            this.messageRecord = messageRecord;
        }

        public int getPosition() {
            return position;
        }

        public MessageRecord getMessageRecord() {
            return messageRecord;
        }
    }
}
