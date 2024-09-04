package ilhamrhmtkbr.app.activities.mahasiswa;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import ilhamrhmtkbr.app.Helper;
import ilhamrhmtkbr.app.R;
import ilhamrhmtkbr.app.components.InputDate;
import ilhamrhmtkbr.app.components.InputSelect;
import ilhamrhmtkbr.app.components.InputText;

public class CreateActivity extends AppCompatActivity {

    // Deklarasi komponen UI dan helper database
    Toolbar toolbar;
    InputText inputNomer, inputNama, inputAlamat;
    InputSelect inputJenisKelamin;
    InputDate inputTanggalLahir;
    AppCompatButton buttonSubmit;

    Helper.DatabaseHelper dbHelper; // Database helper untuk mengelola database

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mahasiswa_create);

        // Inisialisasi toolbar dan set judul
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Input Data");

        // Set toolbar sebagai action bar
        setSupportActionBar(toolbar);

        // Aktifkan tombol kembali pada toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Inisialisasi input field
        inputNomer = findViewById(R.id.input_nomer);
        inputNama = findViewById(R.id.input_nama);
        inputTanggalLahir = findViewById(R.id.input_tanggal_lahir);
        inputJenisKelamin = findViewById(R.id.input_jenis_kelamin);
        inputAlamat = findViewById(R.id.input_alamat);
        buttonSubmit = findViewById(R.id.button_submit);

        // Set ikon pada input field
        Helper.setIcon(this, R.drawable.ic_pencil, inputNomer);
        Helper.setIcon(this, R.drawable.ic_name, inputNama);
        Helper.setIcon(this, R.drawable.ic_location, inputAlamat);

        // Inisialisasi database helper
        dbHelper = new Helper.DatabaseHelper(this);

        // Set listener untuk tombol submit
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                store(); // Panggil metode untuk menyimpan data
            }
        });
    }

    // Metode untuk menyimpan data mahasiswa ke database
    private void store() {
        // Ambil nilai dari input field
        String nomor = inputNomer.getValue();
        String[] fieldValues = {
                nomor,
                inputNama.getValue(),
                inputTanggalLahir.getValue(),
                inputJenisKelamin.getValue(),
                inputAlamat.getValue()
        };
        String[] fieldNames = {"nomor", "nama", "tanggal_lahir", "jenis_kelamin", "alamat"};

        // Validasi input
        if (Helper.validasiForm(this, fieldValues, fieldNames)) {
            // Periksa apakah nomor sudah ada di database
            String selection = "nomor = ?";
            String[] selectionArgs = {nomor};

            Cursor cursor = dbHelper.getReadableDatabase().query(
                    "mahasiswa",   // Nama tabel
                    null,          // Kolom yang akan diambil (null mengambil semua kolom)
                    selection,     // Klausa WHERE
                    selectionArgs, // Argumen untuk klausa WHERE
                    null,          // Grup kolom
                    null,          // Filter grup
                    null           // Urutan hasil
            );

            // Cek apakah ada data dengan nomor yang sama
            if (cursor.getCount() > 0) {
                // Nomor sudah ada di database
                Toast.makeText(this, "Nomor mahasiswa sudah ada", Toast.LENGTH_SHORT).show();
                cursor.close();
            } else {
                cursor.close();
                // Nomor belum ada di database, lanjutkan penyimpanan data
                ContentValues values = new ContentValues();
                for (int i = 0; i < fieldValues.length; i++) {
                    values.put(fieldNames[i], fieldValues[i]);
                }

                // Menyimpan data ke dalam database
                long result = dbHelper.getWritableDatabase().insert("mahasiswa", null, values);
                if (result != -1) {
                    // Jika sukses, tampilkan pesan berhasil dan kembali ke IndexActivity
                    Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateActivity.this, IndexActivity.class));
                    finish(); // Tutup activity ini
                } else {
                    // Jika gagal, tampilkan pesan error
                    Toast.makeText(this, "Terjadi kesalahan saat menambahkan data", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Menangani klik pada tombol di toolbar
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
