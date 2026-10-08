# Sistem Manajemen Penyewaan Alat Mancing
 
Program berbasis **Java (CLI / Console)** untuk mengelola penyewaan alat mancing, dibuat sebagai tugas **Minpro 3 – Pemrograman Berorientasi Objek (PBO)**. Program dibangun dengan pola **MVC (Model – View – Controller)**.
 
---
 
## Daftar Isi
1. [Deskripsi Singkat Program](#1-deskripsi-singkat-program)
2. [Penjelasan Struktur Package](#2-penjelasan-struktur-package)
3. [Penjelasan Alur Program](#3-penjelasan-alur-program)
4. [Penerapan Encapsulation dan Inheritance](#4-penerapan-encapsulation-dan-inheritance)
5. [Penerapan Polymorphism dan Abstraction](#5-penerapan-polymorphism-dan-abstraction)
6. [Penerapan Nilai Tambah](#6-penerapan-nilai-tambah)
7. [Cara Menjalankan Program](#7-cara-menjalankan-program)
---
 
## 1. Deskripsi Singkat Program
 
Sistem ini membantu pengelola persewaan alat mancing dalam mencatat dan mengelola data alat serta transaksi sewa. Alat yang tersedia terdiri dari dua jenis, yaitu **Joran** dan **Reel**.
 
Fitur utama:
 
| No | Fitur | Keterangan |
|----|-------|------------|
| 1 | Tampilkan Data Alat Mancing | Menampilkan seluruh alat beserta stok dan harga sewa |
| 2 | Tambah Alat Mancing Baru | Menambah Joran atau Reel dengan validasi ID unik |
| 3 | Update Stok Alat Mancing | Menambah stok beserta catatan penambahan |
| 4 | Hapus Alat Mancing | Menghapus alat berdasarkan ID |
| 5 | Catat Transaksi Sewa Baru | Mencatat transaksi dan menghitung total biaya sewa |
| 6 | Tampilkan Riwayat Transaksi | Menampilkan seluruh transaksi yang sudah dicatat |
| 7 | Keluar | Mengakhiri program |
 
Data awal (dummy) yang dimuat saat program berjalan:
 
| ID | Nama | Jenis | Stok | Harga/Hari | Atribut Khusus |
|----|------|-------|------|-----------|----------------|
| J-001 | Maguro Extreme | Joran | 15 | Rp25.000 | Kekuatan: Medium Heavy |
| R-001 | Shimano Stella | Reel | 5 | Rp50.000 | Bearing: 12 |
 
---
 
## 2. Penjelasan Struktur Package
 
```
com.mycompany_Minpro_3_PBO_Penyewaan
├── pom.xml
└── src/main/java
    ├── Main
    │   └── Main.java
    ├── Model
    │   ├── Disewakan.java        (interface)
    │   ├── AlatMancing.java      (abstract class)
    │   ├── Joran.java
    │   ├── Reel.java
    │   ├── Penyewa.java
    │   └── Transaksi.java
    ├── View
    │   └── SewaView.java
    └── Controller
        └── SewaController.java
```
 
| Package | Class | Peran |
|---------|-------|-------|
| `Main` | `Main` | Titik masuk program (`main`). Membuat objek `SewaView` dan `SewaController`, lalu menjalankan `controller.mulai()`. |
| `Model` | `Disewakan` | **Interface** yang mendefinisikan kontrak `hitungTotalSewa()` dan `sewa()`. |
| `Model` | `AlatMancing` | **Abstract class** induk dari semua alat. Menyimpan data umum (id, nama, stok, harga, catatan). |
| `Model` | `Joran` | Turunan `AlatMancing` dengan atribut tambahan `kekuatan`. |
| `Model` | `Reel` | Turunan `AlatMancing` dengan atribut tambahan `jumlahBearing` dan aturan diskon. |
| `Model` | `Penyewa` | Menyimpan data penyewa (id, nama, no. telepon). |
| `Model` | `Transaksi` | Menyimpan data satu transaksi sewa. |
| `View` | `SewaView` | Menangani seluruh tampilan menu, output, serta input dan validasi input dari pengguna. |
| `Controller` | `SewaController` | Menjadi penghubung Model dan View, berisi logika alur program (CRUD alat dan pencatatan transaksi). |
 
**Pemisahan tanggung jawab (MVC):**
- **Model** hanya berisi data dan aturan bisnis (hitung biaya, kelola stok).
- **View** hanya berurusan dengan `System.out` / `Scanner`.
- **Controller** mengatur alur: menerima pilihan dari View, memanggil Model, lalu mengirim hasilnya kembali ke View.
---
 
## 3. Penjelasan Alur Program
 
```
        ┌──────────────┐
        │  Main.main() │
        └──────┬───────┘
               ▼
   Buat SewaView & SewaController
               ▼
   Controller memuat data dummy (1 Joran + 1 Reel)
               ▼
        controller.mulai()
               ▼
   ┌─────────► Tampilkan Menu Utama (1-7) ◄──────────┐
   │                     │                            │
   │   ┌─────┬─────┬─────┼─────┬─────┬─────┐          │
   │   ▼     ▼     ▼     ▼     ▼     ▼     ▼          │
   │  [1]   [2]   [3]   [4]   [5]   [6]   [7]         │
   │ Lihat Tambah Update Hapus Catat Riwayat Keluar   │
   │  alat  alat  stok  alat  trans. trans.   │       │
   │   └─────┴─────┴─────┴─────┴─────┴────────┼───────┘
   │                                           ▼
   └───────────── (ulangi selama berjalan) →  Selesai
```
 
Alur tiap menu:
 
1. **Tampilkan Data Alat** – Controller meneruskan `daftarAlat` ke View. View memanggil `getInfo()` pada setiap alat.
2. **Tambah Alat Baru** – Pengguna memilih jenis (1 = Joran, 2 = Reel) → input ID (dicek agar tidak duplikat) → nama, stok, harga → input atribut khusus (kekuatan atau jumlah bearing) → objek `Joran`/`Reel` dibuat dan disimpan ke `ArrayList`.
3. **Update Stok** – Pengguna memasukkan ID alat → jumlah tambahan stok dan catatan → `tambahStok()` dipanggil. Jika tambahan ≤ 0, muncul pesan error.
4. **Hapus Alat** – Pengguna memasukkan ID → alat dicari dengan `cariAlat()` → dihapus dari list bila ditemukan.
5. **Catat Transaksi** – Pengguna memasukkan ID transaksi, nama penyewa, ID alat, dan lama sewa → sistem memvalidasi alat (ada dan stok tidak kosong) serta lama sewa (minimal 1 hari) → `hitungTotalSewa()` menghitung biaya → objek `Transaksi` disimpan.
6. **Riwayat Transaksi** – Menampilkan tabel transaksi, atau pesan bila belum ada data.
7. **Keluar** – Loop berhenti dan program selesai.
---
 
## 4. Penerapan Encapsulation dan Inheritance
 
### Encapsulation
 
Seluruh atribut pada class Model dideklarasikan **`private`** sehingga tidak dapat diakses langsung dari luar class. Akses dilakukan melalui method **getter** atau method khusus yang menyertakan validasi.
 
**Contoh – `AlatMancing.java`**
```java
private final String idAlat;
private final String namaAlat;
private final double hargaSewaPerHari;
private int stok;
private String catatanTerakhir = "";
 
public AlatMancing(String idAlat, String namaAlat, int stok, double hargaSewaPerHari) {
    this.idAlat = idAlat;
    this.namaAlat = (namaAlat == null || namaAlat.trim().isEmpty()) ? "Tanpa Nama" : namaAlat.trim();
    this.stok = Math.max(stok, 0);                       // stok tidak boleh negatif
    this.hargaSewaPerHari = Math.max(hargaSewaPerHari, 0); // harga tidak boleh negatif
}
```
 
Poin penting:
- Atribut `private`, hanya bisa dibaca lewat `getIdAlat()`, `getNamaAlat()`, `getStok()`, dst.
- **Stok tidak punya setter bebas.** Perubahan stok hanya lewat `sewa()` dan `tambahStok()`, sehingga nilainya selalu terkontrol (tidak bisa diisi angka negatif sembarangan).
- Validasi di constructor: nama kosong diganti `"Tanpa Nama"`, stok dan harga minimal 0.
- Atribut yang tidak boleh berubah dibuat `final` (`idAlat`, `namaAlat`, `hargaSewaPerHari`).
- Penerapan yang sama ada pada `Joran` (`kekuatan`), `Reel` (`jumlahBearing`), `Penyewa`, dan `Transaksi`.
### Inheritance
 
`Joran` dan `Reel` **mewarisi** (`extends`) class `AlatMancing`, sehingga atribut dan method umum tidak perlu ditulis ulang.
 
```java
public class Joran extends AlatMancing { ... }
public class Reel  extends AlatMancing { ... }
```
 
```
        «interface»
        Disewakan
            ▲  implements
            │
   «abstract» AlatMancing
        ▲            ▲
        │ extends    │ extends
      Joran         Reel
```
 
- Class turunan memanggil constructor induk dengan `super(idAlat, namaAlat, stok, hargaSewa)`.
- Method `sewa()`, `tambahStok()`, serta seluruh getter diwarisi dan dipakai langsung oleh `Joran` dan `Reel`.
- Masing-masing class turunan hanya menambah hal yang spesifik: `kekuatan` pada Joran dan `jumlahBearing` pada Reel.
---
 
## 5. Penerapan Polymorphism dan Abstraction
 
### Polymorphism
 
**a) Method Overriding (runtime polymorphism)**
 
Method `getInfo()` dan `hitungTotalSewa()` ditulis ulang di setiap class turunan dengan perilaku berbeda.
 
| Method | `Joran` | `Reel` |
|--------|---------|--------|
| `getInfo()` | Menampilkan data umum + **Kekuatan** | Menampilkan data umum + **Bearing** |
| `hitungTotalSewa(lamaHari)` | `harga × lamaHari` | `harga × lamaHari`, **diskon 10%** bila sewa ≥ 3 hari |
 
```java
// Reel.java
@Override
public double hitungTotalSewa(int lamaHari) {
    double total = getHargaSewaPerHari() * lamaHari;
    if (lamaHari >= 3) {
        total = total - (total * 0.1); // diskon 10%
    }
    return total;
}
```
 
Polymorphism terlihat pada `SewaController` dan `SewaView`: list bertipe `ArrayList<AlatMancing>` menampung objek `Joran` maupun `Reel`, namun saat `alat.getInfo()` atau `alat.hitungTotalSewa(lama)` dipanggil, Java otomatis menjalankan versi milik class aslinya.
 
```java
// SewaView.java
for (AlatMancing alat : daftar) {
    System.out.println(alat.getInfo());   // versi Joran atau Reel, tergantung objeknya
}
 
// SewaController.java
double total = alat.hitungTotalSewa(lama); // Joran: tanpa diskon, Reel: diskon bila ≥ 3 hari
```
 
**b) Method Overloading (compile-time polymorphism)**
 
Class `AlatMancing` memiliki dua versi `tambahStok()` dengan parameter berbeda:
 
```java
public boolean tambahStok(int tambahan) { ... }                  // tambah stok saja
public boolean tambahStok(int tambahan, String catatan) { ... }  // tambah stok + simpan catatan
```
 
### Abstraction
 
**a) Abstract class – `AlatMancing`**
 
```java
public abstract class AlatMancing implements Disewakan {
    public abstract String getInfo();
    ...
}
```
 
- `AlatMancing` tidak dapat diinstansiasi langsung (`new AlatMancing()` tidak diperbolehkan) karena merupakan konsep umum.
- `getInfo()` dideklarasikan **abstract**, sehingga setiap turunan **wajib** mengimplementasikannya.
- Method konkret yang sama untuk semua alat (`sewa()`, `tambahStok()`) ditulis sekali di class abstrak.
**b) Interface – `Disewakan`**
 
```java
public interface Disewakan {
    double hitungTotalSewa(int lamaHari);
    boolean sewa(); // mengurangi stok 1, return false jika stok kosong
}
```
 
- Interface hanya mendefinisikan **apa** yang harus bisa dilakukan oleh sesuatu yang disewakan, tanpa menentukan **bagaimana**.
- `AlatMancing` mengimplementasikan `sewa()`, sedangkan `hitungTotalSewa()` diserahkan ke class turunan (`Joran` dan `Reel`) karena aturan hitungnya berbeda.
---
 
## 6. Penerapan Nilai Tambah
 
> **Catatan:** Bagian ini ditulis berdasarkan fitur yang benar-benar ada di kode. Hapus poin yang tidak kamu klaim sebagai nilai tambah, dan sesuaikan dengan ketentuan tugas.
 
| No | Nilai Tambah | Letak Penerapan | Penjelasan |
|----|--------------|-----------------|------------|
| 1 | **Arsitektur MVC** | Package `Model`, `View`, `Controller` | Kode dipisah berdasarkan tanggung jawab sehingga lebih rapi dan mudah dikembangkan. |
| 2 | **Kombinasi Interface + Abstract Class** | `Disewakan.java`, `AlatMancing.java` | Kontrak perilaku (interface) dan implementasi bersama (abstract class) digunakan bersamaan. |
| 3 | **Method Overloading** | `AlatMancing.tambahStok(int)` dan `tambahStok(int, String)` | Dua versi method untuk kebutuhan berbeda, dengan versi kedua memanfaatkan versi pertama. |
| 4 | **Diskon otomatis** | `Reel.hitungTotalSewa()` | Diskon 10% untuk sewa 3 hari atau lebih, sebagai aturan bisnis khusus pada Reel. |
| 5 | **Validasi input** | `SewaView.inputInt()`, `inputDouble()` | Input diulang terus sampai pengguna memasukkan angka valid (tidak crash saat salah input). |
| 6 | **Validasi data** | `SewaController`, `AlatMancing` | Cek ID duplikat, ID tidak ditemukan, stok kosong, lama sewa minimal 1 hari, tambahan stok harus > 0. |
| 7 | **Pencarian tidak case-sensitive** | `SewaController.cariAlat()` | Menggunakan `equalsIgnoreCase`, jadi `j-001` dan `J-001` dianggap sama. |
| 8 | **Catatan penambahan stok** | `AlatMancing.catatanTerakhir` | Menyimpan alasan/keterangan setiap penambahan stok. |
 
---
 
## 7. Cara Menjalankan Program
 
**Kebutuhan:** JDK (sesuai `pom.xml`, `maven.compiler.release` = 26) dan Maven. Dapat dijalankan dari NetBeans.
 
```bash
# 1. Clone repository
git clone https://github.com/ismulazam/Minpro-3-PBO-SistemPenyewaanAlatMancing.git
cd Minpro-3-PBO-SistemPenyewaanAlatMancing/com.mycompany_Minpro_3_PBO_Penyewaan
 
# 2. Compile
mvn compile
 
# 3. Jalankan
mvn exec:java -Dexec.mainClass="Main.Main"
```
 
Atau buka project di **NetBeans**, lalu klik kanan `Main.java` → **Run File**.
 
---
 
# 📸 Dokumentasi Screenshot
 
> Ganti setiap `path/gambar.png` dengan lokasi file screenshot kamu (misalnya simpan di folder `screenshots/` pada repository).
 
### 1. Menu Utama
<img width="333" height="180" alt="image" src="https://github.com/user-attachments/assets/43906023-badf-485e-ba58-0b1b4507c951" />

 
### 2. Tampilkan Data Alat Mancing
<img width="641" height="248" alt="image" src="https://github.com/user-attachments/assets/1652497e-52d8-4d55-9eb4-6ab16bf37e6a" />

 
### 3. Tambah Alat Mancing – Joran
<img width="337" height="304" alt="image" src="https://github.com/user-attachments/assets/594859de-97b5-467f-b786-e024d257c703" />

 
### 4. Tambah Alat Mancing – Reel
<img width="339" height="302" alt="image" src="https://github.com/user-attachments/assets/e744d83d-58ff-4f46-b4ac-d5f60d73eec9" />

 
### 5. Validasi ID Duplikat saat Tambah Alat
<img width="334" height="240" alt="image" src="https://github.com/user-attachments/assets/cfe76df8-63ff-45b5-bc6d-d2b613f1809e" />

 
### 6. Update Stok Alat Mancing
<img width="638" height="532" alt="image" src="https://github.com/user-attachments/assets/6015b67a-bbb8-4314-9508-ba5680532211" />

 
### 7. Update Stok Gagal (jumlah ≤ 0 / ID tidak ditemukan)
<img width="332" height="242" alt="image" src="https://github.com/user-attachments/assets/37009404-cfcd-403d-ba0b-509c57a2f518" />

 
### 8. Hapus Alat Mancing
<img width="638" height="470" alt="Screenshot 2026-10-08 211717" src="https://github.com/user-attachments/assets/34e81dfd-016e-41cf-8473-7e14cb3f13ff" />

 
### 9. Catat Transaksi – Joran (tanpa diskon)
<img width="384" height="271" alt="image" src="https://github.com/user-attachments/assets/8d9dee39-3a56-4695-970b-67c3c061a101" />

 
### 10. Catat Transaksi – Reel (diskon 10% untuk ≥ 3 hari)
<img width="389" height="276" alt="image" src="https://github.com/user-attachments/assets/5cd7b012-c5bd-4bcb-8ecf-1cc0923d596d" />

 
### 11. Transaksi Gagal (stok kosong / lama sewa < 1)
<img width="358" height="308" alt="image" src="https://github.com/user-attachments/assets/3cb4bdf3-e771-4f02-8160-d706fbfeb7b3" />

<img width="335" height="261" alt="image" src="https://github.com/user-attachments/assets/dceddc4a-221f-4205-a860-d5d87910a9e2" />

<img width="338" height="279" alt="image" src="https://github.com/user-attachments/assets/9799c309-3db7-43a0-823a-1d65105f88c6" />

 
### 12. Riwayat Transaksi
<img width="546" height="281" alt="image" src="https://github.com/user-attachments/assets/d62ca787-a7f9-4f64-99b7-eb33bdb1bd73" />

 
### 13. Validasi Input Angka (input huruf)
<img width="549" height="517" alt="image" src="https://github.com/user-attachments/assets/66655b6f-a561-490d-ba8c-4092d4d84451" />

 
### 14. Keluar dari Program
<img width="507" height="236" alt="image" src="https://github.com/user-attachments/assets/52929cd0-e2f2-41d9-aa2c-7651fc81eb0e" />

 
---
 
## 👤 Identitas
 
| | |
|---|---|
| **Nama** | _Mukhammad Ismukl Azam Atmoko_ |
| **NIM** | _2509116034_ |
| **Kelas** | _A_ |
| **Mata Kuliah** | Pemrograman Berorientasi Objek |
