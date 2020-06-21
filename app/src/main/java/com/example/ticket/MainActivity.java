package com.example.ticket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    ListView listView;
    FloatingActionButton floatingActionButton;
    DBTicket dbTicket = new DBTicket(this);
    ArrayList<Ticket> songs;
    TicketAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("App đặt vé tàu");
        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.ListView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        registerForContextMenu(listView);
        dbTicket.createDefaultNotesIfNeed();
        songs = dbTicket.getAllSongs();
        customAdapter = new TicketAdapter(this, songs);
        listView.setAdapter(customAdapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        listView.setAdapter(customAdapter);
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int pos = info.position;
        Intent myActivity;
        switch (item.getItemId()) {
            case R.id.sua:
                myActivity = new Intent(MainActivity.this, Main4Activity.class);
                Ticket ticket = (Ticket) listView.getAdapter().getItem(pos);
                String id = String.valueOf(ticket.getId());
                myActivity.putExtra("id", id);
                myActivity.putExtra("GaDen", ticket.getGaDen());
                myActivity.putExtra("GaDi", ticket.getGaDi());
                myActivity.putExtra("DonGia", String.valueOf(ticket.getDonGia()));
                myActivity.putExtra("TheLoai", String.valueOf(ticket.getTheLoai()));
                startActivityForResult(myActivity, 200);
                break;
            case R.id.xoa:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure to delete?");
                builder.setCancelable(false);
                builder.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Ticket song = (Ticket) listView.getAdapter().getItem(pos);
                        dbTicket.deleteWord(song);
                        songs = dbTicket.getAllSongs();
                        customAdapter = new TicketAdapter(MainActivity.this, songs);
                        listView.setAdapter(customAdapter);
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            songs = dbTicket.getAllSongs();
            customAdapter = new TicketAdapter(this, songs);
            listView.setAdapter(customAdapter);
        }
        if (requestCode == 200 && resultCode == RESULT_OK) {
            songs = dbTicket.getAllSongs();
            customAdapter = new TicketAdapter(this, songs);
            listView.setAdapter(customAdapter);
        }
    }
}
