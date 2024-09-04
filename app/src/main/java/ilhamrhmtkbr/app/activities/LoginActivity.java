package ilhamrhmtkbr.app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ilhamrhmtkbr.app.Helper;

import ilhamrhmtkbr.app.R;
import ilhamrhmtkbr.app.components.InputText;

public class LoginActivity extends AppCompatActivity {

    // Deklarasi variabel untuk input email dan password serta tombol submit
    InputText inputEmail, inputPassword;
    AppCompatButton buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Mengatur layout untuk activity ini dengan file XML activity_login

        // Menginisialisasi inputEmail, inputPassword, dan buttonSubmit dengan ID dari layout
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        buttonSubmit = findViewById(R.id.button_submit);

        // Mengatur icon pada inputEmail dan inputPassword menggunakan Helper
        Helper.setIcon(this, R.drawable.ic_email, inputEmail);
        Helper.setIcon(this, R.drawable.ic_password, inputPassword);

        // Mengatur aksi klik untuk buttonSubmit
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin(); // Memanggil fungsi untuk melakukan login
            }
        });

        // Cek apakah file userdata sudah dibuat dengan menggunakan SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isFileCreated = sharedPreferences.getBoolean("isFileCreated", false);

        if (!isFileCreated) {
            // Jika file belum ada, buat file userdata.txt
            createUserdataFile();
            // Simpan status bahwa file sudah dibuat
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFileCreated", true);
            editor.apply();
        }
    }

    private void performLogin() {
        // Mengambil nilai email dan password dari input
        String email = inputEmail.getValue();
        String password = inputPassword.getValue();

        // Path ke file userdata.txt di direktori khusus aplikasi
        File file = new File(getExternalFilesDir(null), "userdata.txt");

        if (file.exists()) { // Cek apakah file userdata.txt ada
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] data = new byte[(int) file.length()];
                fis.read(data); // Membaca konten file
                String fileContent = new String(data);

                // Cek apakah email dan password cocok
                if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Mohon lengkapi formulir", Toast.LENGTH_SHORT).show();
                } else if (email.equals("ilhamrhmtkbr@gmail.com") && password.equals("ilham123")) {
                    // Pisahkan konten file menjadi baris
                    String[] lines = fileContent.split("\n");

                    // Buat StringBuilder untuk menyusun konten baru tanpa status login yang lama
                    StringBuilder updatedContent = new StringBuilder();

                    for (String line : lines) {
                        if (line.startsWith("status login: ")) {
                            continue; // Lewati baris yang mengandung status login lama
                        }
                        updatedContent.append(line).append("\n"); // Tambahkan baris lainnya ke StringBuilder
                    }

                    // Tambahkan status login yang baru
                    updatedContent.append("status login: true");

                    // Tulis konten yang telah diperbarui ke file
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(updatedContent.toString().getBytes());
                        fos.flush(); // Pastikan semua data tertulis ke file
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Gagal memperbarui file", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Redirect ke DashboardActivity setelah login berhasil
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish(); // Tutup LoginActivity agar tidak bisa kembali dengan tombol back
                } else {
                    Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show(); // Jika email atau password tidak cocok
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Gagal membaca file", Toast.LENGTH_SHORT).show(); // Jika terjadi error saat membaca file
            }
        } else {
            Toast.makeText(this, "File userdata.txt tidak ditemukan", Toast.LENGTH_SHORT).show(); // Jika file userdata.txt tidak ditemukan
        }
    }

    private void createUserdataFile() {
        // Nama file yang akan dibuat
        String fileName = "userdata.txt";
        // Data default yang akan disimpan dalam file
        String data = "ilhamrhmtkbr@gmail.com, " +
                "ilham123\n";

        // Dapatkan direktori khusus aplikasi di penyimpanan eksternal
        File file = new File(getExternalFilesDir(null), fileName);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes()); // Tulis data ke file
            fos.flush(); // Pastikan semua data tertulis ke file
            Toast.makeText(this, "File userdata.txt berhasil dibuat", Toast.LENGTH_SHORT).show(); // Informasi kepada pengguna bahwa file telah berhasil dibuat
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal membuat file", Toast.LENGTH_SHORT).show(); // Jika terjadi kesalahan dalam pembuatan file
        }
    }
}
