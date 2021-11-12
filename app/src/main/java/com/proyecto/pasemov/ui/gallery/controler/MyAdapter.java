package com.proyecto.pasemov.ui.gallery.controler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyecto.pasemov.R;

import java.text.DateFormat;

public class MyAdapter  extends RecyclerView.Adapter {
    Context context;
    RealmResults<Notes> notesList;

    public MyAdapter(Context context, RealmResults<Notes> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    Notes note =notesList.get(position);
    holder.titleOutput.setText(note.getTitle());
        holder.descriptionOutput.setText(note.getTitle());
        String formatedTime= DateFormat.getDateTimeInstance().format(note.createdTime);

        holder.timeOutput.setText(note.getTitle(formatedTime));
        holder.itemView.setOnClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu=   new PopupMenu(context,v);
                menu.getMenu().add("Eliminar");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Eliminar"))
                            Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        note.deleteFromRealm();
                        realm.commitTransaction();
                        Toast.makeText(context,"eliminacion de nota",Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });
                menu.show();

                return  true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleOutput, descriptionOunput, timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timeOutput= itemView.findViewById(R.id.titloutput);
            descriptionOunput= itemView.findViewById(R.id.descriptionoutput);
            timeOutput=itemView.findViewById((R.id.timeoutpu));
        }
    }
}
