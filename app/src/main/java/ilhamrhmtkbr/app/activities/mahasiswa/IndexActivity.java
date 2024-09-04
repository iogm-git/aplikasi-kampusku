package ilhamrhmtkbr.app.activities.mahasiswa;

import android.content.Intent; // Digunakan untuk memulai activity lain
import android.database.Cursor; // Digunakan untuk membaca data dari database
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity; // Kelas dasar untuk Activity di Android
import androidx.appcompat.widget.Toolbar; // Toolbar untuk menampilkan judul dan navigasi
import androidx.recyclerview.widget.LinearLayoutManager; // Layout manager untuk RecyclerView
import androidx.recyclerview.widget.RecyclerView; // Komponen untuk menampilkan data dalam bentuk list atau grid

import java.util.ArrayList; // List dinamis untuk menyimpan data
import java.util.List; // Interface untuk list

import ilhamrhmtkbr.app.Helper; // Helper class yang berisi fungsi-fungsi umum
import ilhamrhmtkbr.app.R; // Referensi ke sumber daya seperti layout dan drawable
import ilhamrhmtkbr.app.components.FloatingButton; // Komponen custom untuk tombol floating action button (FAB)
import ilhamrhmtkbr.app.components.RecyclerViewMahasiswa; // Komponen custom untuk RecyclerView

public class IndexActivity extends AppCompatActivity {

    Toolbar toolbar; // Deklarasi toolbar
    RecyclerView dataMahasiswa; // RecyclerView untuk menampilkan data mahasiswa
    Helper.DatabaseHelper dbHelper; // Helper untuk akses database
    RecyclerViewMahasiswa.ItemAdapter adapter; // Adapter untuk menghubungkan data dengan RecyclerView

    FloatingButton fab; // Floating Action Button untuk menambah data

    List<RecyclerViewMahasiswa.MahasiswaModel> mahasiswaList; // List untuk menyimpan data mahasiswa

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mahasiswa_index); // Menetapkan layout untuk activity ini

        // Inisialisasi toolbar dan menetapkan judulnya
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Data Mahasiswa"); // Judul toolbar "Data Mahasiswa"
        setSupportActionBar(toolbar); // Menetapkan toolbar sebagai action bar

        // Mengaktifkan tombol kembali di toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Inisialisasi list mahasiswa
        mahasiswaList = new ArrayList<>();

        // Inisialisasi RecyclerView dan menetapkan layout manager untuk mengatur tata letak item
        dataMahasiswa = findViewById(R.id.list_mahasiswa);
        dataMahasiswa.setLayoutManager(new LinearLayoutManager(this)); // LinearLayoutManager untuk list vertikal

        // Inisialisasi adapter dan menghubungkannya dengan RecyclerView
        adapter = new RecyclerViewMahasiswa.ItemAdapter(this, mahasiswaList);
        dataMahasiswa.setAdapter(adapter);

        // Inisialisasi Floating Action Button dan menetapkan onClickListener
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Memulai CreateActivity untuk menambah data mahasiswa baru
                Intent intent = new Intent(IndexActivity.this, CreateActivity.class);
                startActivityForResult(intent, 1); // Request code 1 untuk hasil activity
            }
        });

        // Inisialisasi dbHelper untuk akses database
        dbHelper = new Helper.DatabaseHelper(this);

        // Set listener to refresh data on change
        adapter.setOnDataChangedListener(this::refreshData); // Menetapkan listener untuk adapter
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData(); // Memperbarui data saat aktivitas kembali aktif
    }

    private void refreshData() {
        mahasiswaList.clear(); // Menghapus data lama dari list mahasiswa
        // Query untuk mengambil data dari database
        Cursor cursor = dbHelper.getReadableDatabase()
                .rawQuery("SELECT nomor, nama, tanggal_lahir, jenis_kelamin, alamat FROM mahasiswa;", null);

        // Iterasi melalui cursor dan tambahkan data ke list
        if (cursor.moveToFirst()) {
            do {
                String nomor = cursor.getString(cursor.getColumnIndexOrThrow("nomor"));
                String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
                // Tambahkan data mahasiswa ke list
                mahasiswaList.add(new RecyclerViewMahasiswa.MahasiswaModel(nomor, nama));
            } while (cursor.moveToNext());
        }

        // Jangan lupa menutup cursor setelah digunakan untuk menghindari memory leaks
        cursor.close();
        // Notifikasi adapter bahwa data telah diperbarui
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Tutup activity saat tombol back ditekan
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
