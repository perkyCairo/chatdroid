package com.example.chatdroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatdroid.MessageActivity;
import com.example.chatdroid.R;
import com.example.chatdroid.model.User;

import java.util.List;

public class Useradapter extends RecyclerView.Adapter<Useradapter.ViewHolder>
{
  private Context ucontext;
  private List<User> uUsers;

  public Useradapter(Context ucontext,List<User> uUsers)
  {
      this.uUsers=uUsers;
      this.ucontext=ucontext;

  }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(ucontext).inflate(R.layout.user_item,parent,false);
        return new Useradapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final User user=uUsers.get(position);
    holder.username.setText(user.getUsername());
       // holder.profile.setImageResource(R.mipmap.ic_launcher);
        String str=user.getImageURL();
        System.out.println(str);
    if(str==null)
    {
        holder.profile.setImageResource(R.mipmap.ic_launcher);
    }
    else
    {
        Glide.with(ucontext).load(user.getImageURL()).into(holder.profile);
    }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(ucontext, MessageActivity.class);
            intent.putExtra("userid",user.getId());
            ucontext.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return uUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

      public TextView username;
      public ImageView profile;
      public ViewHolder(View itemView)
      {
          super(itemView);
          username=itemView.findViewById(R.id.username);
          profile=itemView.findViewById(R.id.profile);
      }
  }
}
