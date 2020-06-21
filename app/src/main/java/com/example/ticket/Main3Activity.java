package com.example.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    EditText editTextGaDen;
    EditText editTextGaDi;
    EditText editTextDonGia;
    RadioButton radioButtonMotChieu;
    RadioButton radioButtonKhuHoi;
    Button buttonAdd;
    Button buttonBack;
    DBTicket dbTicket = new DBTicket(this);
    ArrayList<Ticket> tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setTitle("App đặt vé tàu");
        tickets = dbTicket.getAllSongs();
        editTextGaDi = findViewById(R.id.editText2);
        editTextGaDen = findViewById(R.id.editText3);
        editTextDonGia = findViewById(R.id.editText4);
        buttonAdd = findViewById(R.id.button);
        buttonBack = findViewById(R.id.button2);
        radioButtonKhuHoi = findViewById(R.id.radioButton2);
        radioButtonMotChieu = findViewById(R.id.radioButton);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radioButtonMotChieu.setChecked(true);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextGaDi.getText().toString().trim().equals("") && !editTextGaDen.getText().toString().trim().equals("")
                        && !editTextDonGia.getText().toString().trim().equals("")) {
                    int theLoai = 0;
                    long donGia;
                    if (radioButtonKhuHoi.isChecked()) {
                        theLoai = 1;
                        donGia = (long) (Long.parseLong(editTextDonGia.getText().toString()) * 0.95 * 2);
                    } else {
                        donGia = Long.parseLong(editTextDonGia.getText().toString());
                    }
                    dbTicket.addSong(new Ticket(editTextGaDen.getText().toString().trim(), editTextGaDi.getText().toString().trim(), donGia, theLoai));
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(Main3Activity.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        radioButtonKhuHoi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButtonMotChieu.setChecked(false);
                }
            }
        });
        radioButtonMotChieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    radioButtonKhuHoi.setChecked(false);
                }
            }
        });
    }
}
