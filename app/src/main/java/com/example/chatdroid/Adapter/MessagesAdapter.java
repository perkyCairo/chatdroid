package com.example.chatdroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatdroid.MessageActivity;
import com.example.chatdroid.R;
import com.example.chatdroid.model.Chat2;
import com.example.chatdroid.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>
{
    public  static  final int Msg_left=0;
    public  static  final int Msg_right=1;



    private Context ucontext;
    private List<Chat2> uChat;
    private String imageurl;

    FirebaseUser firebaseUser;

    public MessagesAdapter(Context ucontext,List<Chat2> uChat, String imageurl)
    {
        this.uChat=uChat;
        this.ucontext=ucontext;
        this.imageurl=imageurl;

    }

    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if (viewType == Msg_right) {
            View view = LayoutInflater.from(ucontext).inflate(R.layout.chat_right, parent, false);
            return new MessagesAdapter.ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(ucontext).inflate(R.layout.chat_left, parent, false);
            return new MessagesAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.ViewHolder holder, int position) {
        Chat2 chat2=uChat.get(position);

        holder.showmessage.setText(chat2.getMessage());
        holder.profile.setImageResource(R.mipmap.ic_launcher);

     /*   if (imageurl.equals("default"))
        {
            holder.profile.setImageResource(R.mipmap.ic_launcher);
        }
        else
        {
            Glide.with(ucontext).load(imageurl).into(holder.profile);
        }
*/
    }

    @Override
    public int getItemCount() {
        return uChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView showmessage;
        int i=10;
        public ImageView profile;
        public ViewHolder(View itemView)
        {
            super(itemView);
        showmessage=itemView.findViewById(R.id.showmessage);
            profile=itemView.findViewById(R.id.profile);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(uChat.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return  Msg_right;

        }
        else
            return Msg_left;
    }
}

