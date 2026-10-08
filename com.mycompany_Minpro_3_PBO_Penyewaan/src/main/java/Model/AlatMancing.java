package Model;

/**
 *
 * @author mismu
 */
public abstract class AlatMancing implements Disewakan {
    private final String idAlat;
    private final String namaAlat;
    private final double hargaSewaPerHari;
    private int stok;
    private String catatanTerakhir = "";

    public AlatMancing(String idAlat, String namaAlat, int stok, double hargaSewaPerHari) {
        this.idAlat = idAlat;
        this.namaAlat = (namaAlat == null || namaAlat.trim().isEmpty()) ? "Tanpa Nama" : namaAlat.trim();
        this.stok = Math.max(stok, 0);
        this.hargaSewaPerHari = Math.max(hargaSewaPerHari, 0);
    }

    public String getIdAlat() { return idAlat; }
    public String getNamaAlat() { return namaAlat; }
    public double getHargaSewaPerHari() { return hargaSewaPerHari; }
    public int getStok() { return stok; }
    public String getCatatanTerakhir() { return catatanTerakhir; }

    public abstract String getInfo();

    @Override
    public boolean sewa() {
        if (stok > 0) {
            stok--;
            return true;
        }
        return false;
    }

    public boolean tambahStok(int tambahan) {
        if (tambahan <= 0) {
            return false;
        }
        stok += tambahan;
        return true;
    }

    public boolean tambahStok(int tambahan, String catatan) {
        if (!tambahStok(tambahan)) {
            return false;
        }
        catatanTerakhir = catatan;
        return true;
    }
}