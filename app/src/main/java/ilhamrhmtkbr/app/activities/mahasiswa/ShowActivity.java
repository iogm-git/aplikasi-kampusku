package ilhamrhmtkbr.app.activities.mahasiswa;

import android.database.Cursor; // Digunakan untuk membaca data dari database
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity; // Kelas dasar untuk Activity di Android
import androidx.appcompat.widget.Toolbar; // Toolbar untuk menampilkan judul dan navigasi

import ilhamrhmtkbr.app.Helper; // Helper class yang berisi fungsi-fungsi umum
import ilhamrhmtkbr.app.R; // Referensi ke sumber daya seperti layout dan drawable
import ilhamrhmtkbr.app.components.InputText; // Komponen custom untuk input teks

public class ShowActivity extends AppCompatActivity {

    // Deklarasi toolbar dan komponen input untuk detail mahasiswa
    Toolbar toolbar;
    InputText textNomor, textNama, textTanggalLahir, textJenisKelamin, textAlamat;

    String intentNomor; // Nomor mahasiswa yang akan ditampilkan, diterima dari intent

    Helper.DatabaseHelper dbHelper; // Objek helper untuk mengakses database

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mahasiswa_show); // Menetapkan layout untuk activity ini

        // Inisialisasi toolbar dan menetapkan judul
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Data"); // Judul toolbar "Detail Data"
        setSupportActionBar(toolbar); // Menetapkan toolbar sebagai action bar

        // Menambahkan tombol kembali di toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Inisialisasi komponen input teks dengan mengaitkan ID dari layout XML
        textNomor = findViewById(R.id.text_nomor);
        textNama = findViewById(R.id.text_nama);
        textTanggalLahir = findViewById(R.id.text_tanggal_lahir);
        textJenisKelamin = findViewById(R.id.text_jenis_kelamin);
        textAlamat = findViewById(R.id.text_alamat);

        // Menonaktifkan input teks agar tidak dapat diedit
        textNomor.getComponent().setEnabled(false);
        textNama.getComponent().setEnabled(false);
        textTanggalLahir.getComponent().setEnabled(false);
        textJenisKelamin.getComponent().setEnabled(false);
        textAlamat.getComponent().setEnabled(false);

        // Menambahkan ikon ke masing-masing komponen input menggunakan helper
        Helper.setIcon(this, R.drawable.ic_pencil, textNomor);
        Helper.setIcon(this, R.drawable.ic_name, textNama);
        Helper.setIcon(this, R.drawable.ic_calendar, textTanggalLahir);
        Helper.setIcon(this, R.drawable.ic_gender, textJenisKelamin);
        Helper.setIcon(this, R.drawable.ic_location, textAlamat);

        // Mengambil nomor mahasiswa dari intent
        intentNomor = getIntent().getStringExtra("nomor");

        // Inisialisasi dbHelper untuk akses database
        dbHelper = new Helper.DatabaseHelper(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Menangani aksi tombol kembali di toolbar
        if (item.getItemId() == android.R.id.home) {
            finish(); // Menutup activity dan kembali ke activity sebelumnya
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData(); // Memperbarui data saat aktivitas kembali aktif
    }

    private void refreshData() {
        // Query untuk mengambil data mahasiswa dari database menggunakan nomor mahasiswa
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT nomor, nama, tanggal_lahir, jenis_kelamin, alamat FROM mahasiswa WHERE nomor = ?",
                new String[]{intentNomor}
        );

        // Iterasi melalui cursor dan menetapkan data ke input field
        if (cursor.moveToFirst()) {
            String nomor = cursor.getString(cursor.getColumnIndexOrThrow("nomor"));
            String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
            String tanggalLahir = cursor.getString(cursor.getColumnIndexOrThrow("tanggal_lahir"));
            String jenisKelamin = cursor.getString(cursor.getColumnIndexOrThrow("jenis_kelamin"));
            String alamat = cursor.getString(cursor.getColumnIndexOrThrow("alamat"));

            // Set nilai ke masing-masing input field
            textNomor.setValue(nomor);
            textNama.setValue(nama);
            textTanggalLahir.setValue(tanggalLahir);
            textJenisKelamin.setValue(jenisKelamin);
            textAlamat.setValue(alamat);
        }

        // Menutup cursor setelah digunakan untuk menghindari memory leaks
        cursor.close();
    }
}
