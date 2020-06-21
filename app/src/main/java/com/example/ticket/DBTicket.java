package com.example.ticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBTicket extends SQLiteOpenHelper {
    // Phiên bản
    private static final int DATABASE_VERSION = 1;

    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "DBTicket1";

    // Tên bảng: Word.
    private static final String TABLE_TICKET = "Ticket";

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME_GADEN = "GaDen";
    private static final String COLUMN_NAME_GADI = "GaDi";
    private static final String COLUMN_DONGIA = "DonGia";
    private static final String COLUMN_THELOAI = "TheLoai";

    public DBTicket(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable = "CREATE TABLE " + TABLE_TICKET + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_GADEN + " TEXT NOT NULL, " +
                COLUMN_NAME_GADI + " TEXT NOT NULL, " +
                COLUMN_DONGIA + " LONG NOT NULL, " +
                COLUMN_THELOAI + " INTEGER NOT NULL " + ")";
        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKET);
        // Và tạo lại.
        onCreate(db);
    }

    public void createDefaultNotesIfNeed() {
        int count = this.getSongsCount();
        if (count == 0) {
            Ticket ticket = new Ticket(1, "Hà Nội", "Thái Nguyên", 1, 1);
            Ticket ticket1 = new Ticket(2, "Hà Nội", "Thái Nguyên", 2, 0);
            Ticket ticket2 = new Ticket(3, "Hà Nội", "Thái Nguyên", 3, 0);
            Ticket ticket3 = new Ticket(4, "Hà Nội", "Thái Nguyên", 4, 1);
            Ticket ticket4 = new Ticket(5, "Hà Nội", "Thái Nguyên", 5, 0);
            Ticket ticket5 = new Ticket(6, "Hà Nội", "Thái Nguyên", 6, 1);

            this.addSong(ticket);
            this.addSong(ticket1);
            this.addSong(ticket2);
            this.addSong(ticket3);
            this.addSong(ticket4);
            this.addSong(ticket5);
        }
    }

    public void addSong(Ticket word) {
//        Log.i(TAG, "DBWord.addNote ... " + word.getOriginal_Text());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_GADEN, word.getGaDen());
        values.put(COLUMN_NAME_GADI, word.getGaDi());
        values.put(COLUMN_DONGIA, word.getDonGia());
        values.put(COLUMN_THELOAI, word.getTheLoai());
        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_TICKET, null, values);
        // Đóng kết nối database.
        db.close();
    }

    public int getSongsCount() {
//        Log.i(TAG, "DBWord.getWordsCount ... ");
        String countQuery = "SELECT * FROM " + TABLE_TICKET;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public void deleteWord(Ticket word) {
//        Log.i(TAG, "DBWord.updateWord ... " + word.getOriginal_Text());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TICKET, COLUMN_ID + " = ?",
                new String[]{String.valueOf(word.getId())});
        db.close();
    }

    public ArrayList<Ticket> getAllSongs() {
//        Log.i(TAG, "DBWord.getAllWords ... ");

        ArrayList<Ticket> wordList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TICKET + " ORDER BY " + COLUMN_DONGIA + " DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Ticket word = new Ticket();
                word.setId(cursor.getInt(0));
                word.setGaDen(cursor.getString(1));
                word.setGaDi(cursor.getString(2));
                word.setDonGia(cursor.getLong(3));
                word.setTheLoai(cursor.getInt(4));

                // Thêm vào danh sách.
                wordList.add(word);
            } while (cursor.moveToNext());
        }
        return wordList;
    }

    // Sửa dữ liệu
    public long Update(int id, String gaDen, String gaDi, long donGia, int theLoai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_NAME_GADEN, gaDen);
        contentValues.put(COLUMN_NAME_GADI, gaDi);
        contentValues.put(COLUMN_DONGIA, donGia);
        contentValues.put(COLUMN_THELOAI, theLoai);
        String where = COLUMN_ID + " = " + id;
        return db.update(TABLE_TICKET, contentValues, where, null);
    }
}
