package Model;

/**
 *
 * @author mismu
 */
public class Transaksi {
    private final String idTransaksi;
    private final String namaPenyewa;
    private final String namaAlat;
    private final int lamaSewaHari;
    private final double totalBiaya;

    public Transaksi(String idTransaksi, String namaPenyewa, String namaAlat,
                     int lamaSewaHari, double totalBiaya) {
        this.idTransaksi = idTransaksi;
        this.namaPenyewa = namaPenyewa;
        this.namaAlat = namaAlat;
        this.lamaSewaHari = lamaSewaHari;
        this.totalBiaya = totalBiaya;
    }

    public String getInfo() {
        return String.format("%-10s | %-15s | %-15s | %-10d | Rp%.0f",
                idTransaksi, namaPenyewa, namaAlat, lamaSewaHari, totalBiaya);
    }
}