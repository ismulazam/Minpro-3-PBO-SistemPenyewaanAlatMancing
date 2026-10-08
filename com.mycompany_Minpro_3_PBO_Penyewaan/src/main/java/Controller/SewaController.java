package Controller;

/**
 *
 * @author mismu
 */
import Model.AlatMancing;
import Model.Joran;
import Model.Reel;
import Model.Transaksi;
import View.SewaView;
import java.util.ArrayList;

public class SewaController {
    private final ArrayList<AlatMancing> daftarAlat;
    private final ArrayList<Transaksi> daftarTransaksi;
    private final SewaView view;

    public SewaController(SewaView view) {
        this.view = view;
        this.daftarAlat = new ArrayList<>();
        this.daftarTransaksi = new ArrayList<>();
        inisialisasiDummyData();
    }

    private void inisialisasiDummyData() {
        daftarAlat.add(new Joran("J-001", "Maguro Extreme", 15, 25000, "Medium Heavy"));
        daftarAlat.add(new Reel("R-001", "Shimano Stella", 5, 50000, 12));
    }

    public void mulai() {
        boolean berjalan = true;
        while (berjalan) {
            int pilihan = view.tampilkanMenuUtama();
            switch (pilihan) {
                case 1: view.tampilkanDaftarAlat(daftarAlat); break;
                case 2: tambahData(); break;
                case 3: updateStok(); break;
                case 4: hapusAlat(); break;
                case 5: catatTransaksi(); break;
                case 6: view.tampilkanDaftarTransaksi(daftarTransaksi); break;
                case 7:
                    berjalan = false;
                    view.tampilkanPesan("Sampai jumpa!");
                    break;
                default:
                    view.tampilkanPesan("ERROR: Pilihan menu tidak valid!");
            }
        }
    }

    private void tambahData() {
        view.tampilkanPesan("--- TAMBAH ALAT BARU ---");
        int jenis = view.inputInt("Pilih Jenis (1. Joran / 2. Reel): ");
        if (jenis != 1 && jenis != 2) {
            view.tampilkanPesan("ERROR: Pilihan jenis tidak dikenali.");
            return;
        }

        String id = view.inputString("Masukkan ID Alat    : ");
        if (cariAlat(id) != null) {
            view.tampilkanPesan("ERROR: ID sudah dipakai!");
            return;
        }
        String nama = view.inputString("Masukkan Nama Alat  : ");
        int stok = view.inputInt("Masukkan Stok       : ");
        double harga = view.inputDouble("Harga Sewa/Hari     : ");

        switch (jenis) {
            case 1:
                String kekuatan = view.inputString("Masukkan Kekuatan   : ");
                daftarAlat.add(new Joran(id, nama, stok, harga, kekuatan));
                break;
            case 2:
                int bearing = view.inputInt("Jumlah Bearing      : ");
                daftarAlat.add(new Reel(id, nama, stok, harga, bearing));
                break;
        }
        view.tampilkanPesan("SUCCESS: Data berhasil ditambahkan!");
    }

    private void updateStok() {
        AlatMancing alat = cariAlat(view.inputString("Masukkan ID Alat yang akan diupdate: "));
        if (alat == null) {
            view.tampilkanPesan("ERROR: ID Alat tidak ditemukan!");
            return;
        }
        int tambah = view.inputInt("Masukkan jumlah tambahan stok: ");
        String catatan = view.inputString("Ketik catatan penambahan: ");
        if (alat.tambahStok(tambah, catatan)) {
            view.tampilkanPesan("SUCCESS: Stok diupdate. Catatan: " + alat.getCatatanTerakhir());
        } else {
            view.tampilkanPesan("ERROR: Jumlah tambahan harus lebih dari 0!");
        }
    }

    private void hapusAlat() {
        AlatMancing alat = cariAlat(view.inputString("Masukkan ID Alat yang dihapus: "));
        if (alat != null) {
            daftarAlat.remove(alat);
            view.tampilkanPesan("SUCCESS: Alat berhasil dihapus!");
        } else {
            view.tampilkanPesan("ERROR: ID Alat tidak ditemukan!");
        }
    }

    private void catatTransaksi() {
        view.tampilkanPesan("--- CATAT TRANSAKSI ---");
        String idTrans = view.inputString("Masukkan ID Transaksi : ");
        String namaPenyewa = view.inputString("Masukkan Nama Penyewa : ");
        AlatMancing alat = cariAlat(view.inputString("Masukkan ID Alat Sewa : "));

        if (alat == null) {
            view.tampilkanPesan("ERROR: ID Alat tidak ditemukan!");
            return;
        }
        if (alat.getStok() <= 0) {
            view.tampilkanPesan("ERROR: Stok alat sedang kosong!");
            return;
        }
        int lama = view.inputInt("Masukkan Lama Sewa (hari): ");
        if (lama < 1) {
            view.tampilkanPesan("ERROR: Lama sewa minimal 1 hari!");
            return;
        }

        double total = alat.hitungTotalSewa(lama);
        daftarTransaksi.add(new Transaksi(idTrans, namaPenyewa, alat.getNamaAlat(), lama, total));
        view.tampilkanPesan("SUCCESS: Transaksi dicatat. Total Biaya: Rp" + total);
    }

    private AlatMancing cariAlat(String id) {
        for (AlatMancing alat : daftarAlat) {
            if (alat.getIdAlat().equalsIgnoreCase(id)) {
                return alat;
            }
        }
        return null;
    }
}