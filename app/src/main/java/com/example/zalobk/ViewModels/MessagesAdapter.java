package com.example.zalobk.ViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zalobk.R;
import com.example.zalobk.models.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Message> messageArrayList;
    int ITEM_SEND = 1;
    int ITEM_RECIEVE = 2;

    public MessagesAdapter(Context context, ArrayList<Message> messageArrayList) {
        this.context = context;
        this.messageArrayList = messageArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == ITEM_SEND){
            View view = LayoutInflater.from(context).inflate(R.layout.sendmessagelayout, parent, false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.recievermessagelayout, parent, false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageArrayList.get(position);
        if(holder.getClass() == SenderViewHolder.class){

            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.textViewMess.setText(message.getMessages());
            viewHolder.timeofMess.setText(message.getCurrenttimme());
        }else
        {
            RecieverViewHolder viewHolder = (RecieverViewHolder) holder;
            viewHolder.textViewMess.setText(message.getMessages());
            viewHolder.timeofMess.setText(message.getCurrenttimme());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(message.getSenderID())){

            return  ITEM_SEND;
        }else
        {
            return ITEM_RECIEVE;
        }
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView textViewMess;
        TextView timeofMess;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMess = itemView.findViewById(R.id.sendermessage);
            timeofMess = itemView.findViewById(R.id.timeofmessage);
        }
    }

    class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView textViewMess;
        TextView timeofMess;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMess = itemView.findViewById(R.id.sendermessage);
            timeofMess = itemView.findViewById(R.id.timeofmessage);
        }
    }


}
