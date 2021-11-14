package com.proyecto.pasemov;

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

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapterMed extends RecyclerView.Adapter<MyAdapterMed.MyViewHolderMed> {

    Context context;
    RealmResults<Notes> notesList;
    Realm realm;

    public MyAdapterMed(Context context, RealmResults<Notes> notesList) {
        this.context = context;
        this.notesList = notesList;
    }
    @NonNull
    @Override
    public MyViewHolderMed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderMed(LayoutInflater.from(context).inflate(R.layout.item_view2, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterMed.MyViewHolderMed holder, int position) {
        Notes note = notesList.get(position);
        holder.titleOutput2.setText(note.getTitle());
        holder.descriptionOutput2.setText(note.getDescription());
        String formatedTime = DateFormat.getDateTimeInstance().format(note.createdTime);

        holder.timeOutput2.setText(formatedTime);//note.getTitle(formatedTime)
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu2 = new PopupMenu(context, v);
                menu2.getMenu().add("Eliminar");
                menu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Eliminar")) {
                            realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "eliminacion de nota", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu2.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolderMed extends RecyclerView.ViewHolder {
        TextView titleOutput2, descriptionOutput2, timeOutput2;
        public MyViewHolderMed(@NonNull View itemView2) {
            super(itemView2);
            titleOutput2 = itemView2.findViewById(R.id.tituloOutput2);
            descriptionOutput2 = itemView2.findViewById(R.id.descripcionOutput2);
            timeOutput2 = itemView2.findViewById(R.id.timeOutput2);
        }
    }
}
