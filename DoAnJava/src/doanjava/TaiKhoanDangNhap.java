package doanjava;


public class TaiKhoanDangNhap {

    public String tendangnhap;
    public String matkhau;

    public TaiKhoanDangNhap() {
    }

    public TaiKhoanDangNhap(String tendangnhap, String matkhau) {
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
