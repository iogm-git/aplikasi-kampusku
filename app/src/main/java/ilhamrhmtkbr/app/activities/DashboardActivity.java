package ilhamrhmtkbr.app.activities;

import android.content.Intent; // Digunakan untuk berpindah antar activity
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity; // Activity dasar dari Android
import androidx.appcompat.widget.Toolbar; // Toolbar untuk menampilkan judul dan navigasi di bagian atas
import androidx.core.content.ContextCompat; // Utilitas untuk mengakses sumber daya aplikasi

import ilhamrhmtkbr.app.Helper; // Helper class yang digunakan untuk berbagai fungsi utility
import ilhamrhmtkbr.app.R; // Mengakses sumber daya seperti layout dan drawable
import ilhamrhmtkbr.app.activities.mahasiswa.CreateActivity; // Activity untuk membuat data mahasiswa baru
import ilhamrhmtkbr.app.activities.mahasiswa.IndexActivity; // Activity untuk melihat daftar mahasiswa

public class DashboardActivity extends AppCompatActivity {

    // Deklarasi tombol-tombol yang ada di dashboard
    TextView buttonToMahasiswaCreate, buttonToMahasiswaIndex, buttonToInfo, buttonLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // Menetapkan layout untuk activity ini

        // Mengatur toolbar dengan judul "Dashboard"
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard"); // Set judul toolbar menjadi "Dashboard"
        toolbar.setNavigationIcon(null); // Menghilangkan ikon navigasi di toolbar

        // Inisialisasi tombol dengan mengaitkan ID dari layout XML
        buttonToMahasiswaCreate = findViewById(R.id.nav_mahasiswa_create);
        buttonToMahasiswaIndex = findViewById(R.id.nav_mahasiswa_index);
        buttonToInfo = findViewById(R.id.nav_info);
        buttonLogout = findViewById(R.id.nav_logout);

        // Menambahkan ikon ke masing-masing tombol menggunakan helper
        Helper.setIcon(this, R.drawable.ic_input, buttonToMahasiswaCreate);
        Helper.setIcon(this, R.drawable.ic_list, buttonToMahasiswaIndex);
        Helper.setIcon(this, R.drawable.ic_info, buttonToInfo);
        Helper.setIcon(this, R.drawable.ic_logout, buttonLogout);

        // Menambahkan listener ke tombol untuk navigasi ke CreateActivity
        buttonToMahasiswaCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, CreateActivity.class)); // Pindah ke CreateActivity
            }
        });

        // Menambahkan listener ke tombol untuk navigasi ke IndexActivity
        buttonToMahasiswaIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, IndexActivity.class)); // Pindah ke IndexActivity
            }
        });

        // Menambahkan listener ke tombol untuk navigasi ke InfoActivity
        buttonToInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, InfoActivity.class)); // Pindah ke InfoActivity
            }
        });

        // Menambahkan listener ke tombol logout
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.performLogout(DashboardActivity.this); // Melakukan logout dengan bantuan Helper
            }
        });
    }
}
