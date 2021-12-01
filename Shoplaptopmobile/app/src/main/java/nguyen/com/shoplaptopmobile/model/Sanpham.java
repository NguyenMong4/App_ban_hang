package nguyen.com.shoplaptopmobile.model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    public int Id;
    public String Tensp;
    public int Giasp;
    public  String Hinhanhsp;
    public String Mota;
    public int Idsanpham;

    public Sanpham(int id, String tensp, int giasp, String hinhanhsp, String mota, int idsanpham) {
        Id = id;
        Tensp = tensp;
        Giasp = giasp;
        Hinhanhsp = hinhanhsp;
        Mota = mota;
        Idsanpham = idsanpham;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTensp() {
        return Tensp;
    }

    public void setTensp(String tensp) {
        Tensp = tensp;
    }

    public int getGiasp() {
        return Giasp;
    }

    public void setGiasp(int giasp) {
        Giasp = giasp;
    }

    public String getHinhanhsp() {
        return Hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        Hinhanhsp = hinhanhsp;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public int getIdsanpham() {
        return Idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        Idsanpham = idsanpham;
    }
}
