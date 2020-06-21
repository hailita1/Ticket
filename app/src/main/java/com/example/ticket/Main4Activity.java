package com.example.ticket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main4);
        setTitle("App đặt vé tàu");
        tickets = dbTicket.getAllSongs();
        editTextGaDi = findViewById(R.id.editText2);
        editTextGaDen = findViewById(R.id.editText3);
        editTextDonGia = findViewById(R.id.editText4);
        buttonAdd = findViewById(R.id.button);
        buttonBack = findViewById(R.id.button2);
        radioButtonKhuHoi = findViewById(R.id.radioButton2);
        radioButtonMotChieu = findViewById(R.id.radioButton);
        final Intent intent = getIntent();
        editTextGaDi.setText(intent.getStringExtra("GaDi"));
        editTextGaDen.setText(intent.getStringExtra("GaDen"));
        editTextDonGia.setText(intent.getStringExtra("DonGia"));
        final int check = Integer.parseInt(intent.getStringExtra("TheLoai"));
        if (check == 0) {
            radioButtonMotChieu.setChecked(true);
        } else {
            radioButtonKhuHoi.setChecked(true);
        }
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextGaDi.getText().toString().trim() != "" && editTextGaDen.getText().toString().trim() != "" && editTextDonGia.getText().toString().trim() != "") {
                    int theLoai = 0;
                    long donGia;
                    if (check == 0) {
                        if (radioButtonKhuHoi.isChecked()) {
                            theLoai = 1;
                            donGia = (long) (Long.parseLong(editTextDonGia.getText().toString()) * 0.95 * 2);
                        } else {
                            donGia = Long.parseLong(editTextDonGia.getText().toString());
                        }
                    } else {
                        if (radioButtonKhuHoi.isChecked()) {
                            theLoai = 1;
                            donGia = Long.parseLong(editTextDonGia.getText().toString());
                        } else {
                            donGia = (long) (Long.parseLong(editTextDonGia.getText().toString()) / 0.95 / 2);
                        }
                    }
                    long x = dbTicket.Update(Integer.parseInt(intent.getStringExtra("id")), editTextGaDen.getText().toString(), editTextGaDi.getText().toString(), donGia, theLoai);
                    if (x == 0) {
                        Toast.makeText(Main4Activity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Main4Activity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }
                } else {
                    Toast.makeText(Main4Activity.this, "Hãy nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
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
