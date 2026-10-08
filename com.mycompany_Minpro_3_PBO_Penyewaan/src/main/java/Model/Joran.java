package Model;

/**
 *
 * @author mismu
 */
public class Joran extends AlatMancing {
    private final String kekuatan;

    public Joran(String idAlat, String namaAlat, int stok, double hargaSewa, String kekuatan) {
        super(idAlat, namaAlat, stok, hargaSewa);
        this.kekuatan = kekuatan;
    }

    public String getKekuatan() { return kekuatan; }

    @Override
    public String getInfo() {
        return String.format("%-6s | %-15s | %-8s | Stok: %-3d | Sewa: Rp%-7.0f | Kekuatan: %s",
                getIdAlat(), getNamaAlat(), "Joran", getStok(), getHargaSewaPerHari(), kekuatan);
    }

    @Override
    public double hitungTotalSewa(int lamaHari) {
        return getHargaSewaPerHari() * lamaHari;
    }
}