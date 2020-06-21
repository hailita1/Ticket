package com.example.ticket;

public class Ticket {
    private int Id;
    private String GaDen;
    private String GaDi;
    private long DonGia;
    private int TheLoai;

    public Ticket() {
    }

    public Ticket(int id, String gaDen, String gaDi, long donGia, int theLoai) {
        Id = id;
        GaDen = gaDen;
        GaDi = gaDi;
        DonGia = donGia;
        TheLoai = theLoai;
    }

    public Ticket(String gaDen, String gaDi, long donGia, int theLoai) {
        GaDen = gaDen;
        GaDi = gaDi;
        DonGia = donGia;
        TheLoai = theLoai;
    }

    public int getId() {
        return Id;
    }

    public Ticket setId(int id) {
        Id = id;
        return this;
    }

    public String getGaDen() {
        return GaDen;
    }

    public Ticket setGaDen(String gaDen) {
        GaDen = gaDen;
        return this;
    }

    public String getGaDi() {
        return GaDi;
    }

    public Ticket setGaDi(String gaDi) {
        GaDi = gaDi;
        return this;
    }

    public long getDonGia() {
        return DonGia;
    }

    public Ticket setDonGia(long donGia) {
        DonGia = donGia;
        return this;
    }

    public int getTheLoai() {
        return TheLoai;
    }

    public Ticket setTheLoai(int theLoai) {
        TheLoai = theLoai;
        return this;
    }
}
