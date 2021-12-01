package nguyen.com.shoplaptopmobile.model;

public class Giohang {
    public int id;
    public String tensp;
    public long gia;
    public String hinhanh;
    public int soluongsp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }

    public Giohang(int id, String tensp, long gia, String hinhanh, int soluongsp) {
        this.id = id;
        this.tensp = tensp;
        this.gia = gia;
        this.hinhanh = hinhanh;
        this.soluongsp = soluongsp;
    }
}
