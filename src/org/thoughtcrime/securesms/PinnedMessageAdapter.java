/*
 * Copyright (C) 2011 Whisper Systems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.thoughtcrime.securesms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import org.thoughtcrime.securesms.crypto.MasterSecret;
import org.thoughtcrime.securesms.database.DatabaseFactory;
import org.thoughtcrime.securesms.database.MmsSmsDatabase;
import org.thoughtcrime.securesms.database.model.MessageRecord;
import org.thoughtcrime.securesms.util.DateUtils;

import java.util.Locale;

import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;

public class PinnedMessageAdapter extends RecyclerView.Adapter<PinnedMessageAdapter.ViewHolder> {
    private Cursor               dataCursor;
    private Context              context;
    private MmsSmsDatabase       db;
    private MasterSecret         masterSecret;
    private RecyclerView.Adapter adapter;
    private View                 view;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView messageContent;
        public TextView recipient;
        public TextView time;
        public View wrapper;

        public ViewHolder(View v) {
            super(v);
            this.messageContent = v.findViewById(R.id.pinned_message_body);
            this.recipient      = v.findViewById(R.id.pinned_message_recipient);
            this.time           = v.findViewById(R.id.conversation_item_date);
            this.wrapper        = v.findViewById(R.id.pinned_message_wrapper);
        }
    }

    public PinnedMessageAdapter(Activity mContext, Cursor cursor, MasterSecret masterSecret) {
        this.dataCursor   = cursor;
        this.context      = mContext;
        this.db           = DatabaseFactory.getMmsSmsDatabase(mContext);
        this.masterSecret = masterSecret;
        this.adapter      = this;

    }

    @Override
    public PinnedMessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("pinFragment", "on create view holder");

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View theInflatedView = inflater.inflate(R.layout.pinned_conversation_item, null);
        this.view = theInflatedView;

        return new ViewHolder(theInflatedView);
    }

    public Cursor swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
        }
        Cursor oldCursor = dataCursor;
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dataCursor.moveToPosition(position);
        Log.v("pinFragment", "onbindViewHolder");

        MmsSmsDatabase.Reader reader = db.readerFor(dataCursor, masterSecret);
        MessageRecord record = reader.getCurrent();

        this.setMessageView(record, holder);

        holder.messageContent.setText(record.getDisplayBody().toString());
        holder.time.setText(DateUtils.getExtendedRelativeTimeSpanString(context, new Locale("en", "CA"),
                record.getTimestamp()));

        holder.wrapper.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.PinnedMessageActivity_unpin_prompt);
            builder.setCancelable(true);

            builder.setPositiveButton(R.string.yes, (dialog, id) -> {
                PinnedMessagesHandler handler = new PinnedMessagesHandler(context);
                handler.handleUnpinMessage(record, DatabaseFactory.getSmsDatabase(context));

                ((ViewGroup)v.getParent().getParent()).removeAllViews();
                dialog.cancel();
            });

            builder.setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
            builder.create().show();
            return true;
        });
    }

    private void setMessageView(MessageRecord record, ViewHolder viewHolder) {
        LayoutParams lp = (LayoutParams) viewHolder.wrapper.getLayoutParams();
        if (record.isOutgoing()) {
            lp.addRule(ALIGN_PARENT_RIGHT);
            viewHolder.wrapper.setLayoutParams(lp);

            viewHolder.recipient.setText(R.string.PinnedMessageActivity_own_name);
            return;
        } else {
            lp.addRule(ALIGN_PARENT_LEFT);
            viewHolder.wrapper.setLayoutParams(lp);
        }

        String messageSenderName = record.getRecipient().getName();

        if (messageSenderName == null) {
            messageSenderName = record.getRecipient().getAddress().toString();
        }

        viewHolder.recipient.setText(messageSenderName);
    }

    @Override
    public int getItemCount() {
        return (dataCursor == null) ? 0 : dataCursor.getCount();
    }
}