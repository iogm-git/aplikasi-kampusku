package ilhamrhmtkbr.app.activities.mahasiswa;

import android.content.ContentValues; // Untuk menyimpan pasangan kunci-nilai ke dalam database
import android.content.Intent; // Untuk berpindah antar activity
import android.database.Cursor; // Untuk mengelola hasil query dari database
import android.os.Bundle; // Untuk menyimpan dan memulihkan status activity
import android.view.MenuItem; // Untuk menangani item menu
import android.view.View; // Untuk menangani event klik
import android.widget.Toast; // Untuk menampilkan pesan singkat

import androidx.appcompat.app.AppCompatActivity; // Activity dasar untuk semua activity dengan AppCompat
import androidx.appcompat.widget.AppCompatButton; // Button yang kompatibel dengan AppCompat
import androidx.appcompat.widget.Toolbar; // Toolbar kustom dengan fitur-fitur tambahan

import ilhamrhmtkbr.app.Helper; // Kelas Helper untuk fungsi-fungsi umum
import ilhamrhmtkbr.app.R; // Referensi ke sumber daya dalam aplikasi
import ilhamrhmtkbr.app.components.InputDate; // Komponen input tanggal khusus
import ilhamrhmtkbr.app.components.InputSelect; // Komponen input dropdown khusus
import ilhamrhmtkbr.app.components.InputText; // Komponen input teks khusus

public class EditActivity extends AppCompatActivity {

    Toolbar toolbar; // Deklarasi Toolbar
    InputText inputNomer, inputNama, inputAlamat; // Deklarasi komponen input teks
    InputSelect inputJenisKelamin; // Deklarasi komponen input dropdown untuk jenis kelamin
    InputDate inputTanggalLahir; // Deklarasi komponen input tanggal

    AppCompatButton buttonSubmit; // Deklarasi button submit
    Helper.DatabaseHelper dbHelper; // Helper untuk manajemen database
    String intentNomor; // Menyimpan nomor yang diterima dari intent

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_mahasiswa_create); // Mengatur layout activity

        toolbar = findViewById(R.id.toolbar); // Inisialisasi Toolbar
        toolbar.setTitle("Update Data"); // Set judul Toolbar

        setSupportActionBar(toolbar); // Menggunakan Toolbar sebagai action bar

        // Menambahkan tombol kembali pada action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        inputNomer = findViewById(R.id.input_nomer); // Inisialisasi input nomor
        inputNomer.getComponent().setEnabled(false); // Menonaktifkan input nomor agar tidak bisa diubah
        inputNama = findViewById(R.id.input_nama); // Inisialisasi input nama
        inputTanggalLahir = findViewById(R.id.input_tanggal_lahir); // Inisialisasi input tanggal lahir
        inputJenisKelamin = findViewById(R.id.input_jenis_kelamin); // Inisialisasi input jenis kelamin
        inputAlamat = findViewById(R.id.input_alamat); // Inisialisasi input alamat
        buttonSubmit = findViewById(R.id.button_submit); // Inisialisasi button submit

        // Mengatur ikon pada input menggunakan Helper
        Helper.setIcon(this, R.drawable.ic_pencil, inputNomer);
        Helper.setIcon(this, R.drawable.ic_name, inputNama);
        Helper.setIcon(this, R.drawable.ic_location, inputAlamat);

        // Menambahkan event listener untuk button submit
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(); // Memanggil metode update saat button ditekan
            }
        });

        dbHelper = new Helper.DatabaseHelper(this); // Inisialisasi helper untuk database
        intentNomor = getIntent().getStringExtra("nomor"); // Mengambil nomor dari intent

    }

    // Metode untuk memperbarui data
    private void update() {
        String nomor = inputNomer.getValue(); // Mendapatkan nilai dari input nomor
        String[] fieldValues = { // Mengambil semua nilai input
                nomor,
                inputNama.getValue(),
                inputTanggalLahir.getValue(),
                inputJenisKelamin.getValue(),
                inputAlamat.getValue()
        };
        String[] fieldNames = {"nomor", "nama", "tanggal_lahir", "jenis_kelamin", "alamat"}; // Menentukan nama-nama kolom di database

        // Validasi semua input
        if (Helper.validasiForm(this, fieldValues, fieldNames)) {
            ContentValues values = new ContentValues(); // Untuk menyimpan nilai ke database
            for (int i = 0; i < fieldValues.length; i++) {
                values.put(fieldNames[i], fieldValues[i]); // Memasukkan nilai ke dalam ContentValues
            }

            // Menyimpan data ke dalam database
            long result = dbHelper.getWritableDatabase().update("mahasiswa", values, "nomor = ?", new String[]{intentNomor});

            // Menampilkan pesan jika update berhasil atau gagal
            if (result != -1) {
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                // Setelah sukses, kembali ke activity IndexActivity
                startActivity(new Intent(EditActivity.this, IndexActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Terjadi kesalahan saat memperbarui data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData(); // Memperbarui data saat aktivitas kembali aktif
    }

    // Metode untuk mengambil data dari database dan menampilkan di form
    private void refreshData() {
        // Query untuk mengambil data dari database menggunakan parameter binding
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT nomor, nama, tanggal_lahir, jenis_kelamin, alamat FROM mahasiswa WHERE nomor = ?",
                new String[]{intentNomor}
        );

        // Iterasi melalui cursor dan set data ke input field
        if (cursor.moveToFirst()) {
            String nomor = cursor.getString(cursor.getColumnIndexOrThrow("nomor"));
            String nama = cursor.getString(cursor.getColumnIndexOrThrow("nama"));
            String tanggalLahir = cursor.getString(cursor.getColumnIndexOrThrow("tanggal_lahir"));
            String jenisKelamin = cursor.getString(cursor.getColumnIndexOrThrow("jenis_kelamin"));
            String alamat = cursor.getString(cursor.getColumnIndexOrThrow("alamat"));

            // Set nilai ke input field
            inputNomer.setValue(nomor);
            inputNama.setValue(nama);
            inputTanggalLahir.setValue(tanggalLahir);
            inputJenisKelamin.setValue(jenisKelamin);
            inputAlamat.setValue(alamat);
        }

        // Jangan lupa menutup cursor setelah digunakan
        cursor.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Menangani tombol kembali di action bar
        if (item.getItemId() == android.R.id.home) {
            finish(); // Tutup activity saat tombol back ditekan
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
