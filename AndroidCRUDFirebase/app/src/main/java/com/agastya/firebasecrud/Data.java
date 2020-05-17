package com.agastya.firebasecrud;

import java.io.Serializable;

public class Data implements Serializable {
    private String key;
    private String kode;
    private String nama;
    private String harga;

    public Data() {
    }

    public Data(String kode, String nama, String harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return "Data{" +
                "kode='" + kode + '\'' +
                ", nama='" + nama + '\'' +
                ", harga='" + harga + '\'' +
                '}';
    }
}
