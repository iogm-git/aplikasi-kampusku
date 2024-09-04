package ilhamrhmtkbr.app;

// Mengimpor kelas-kelas yang diperlukan
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ilhamrhmtkbr.app.activities.LoginActivity;
import ilhamrhmtkbr.app.components.InputText;

// Kelas Helper menyediakan berbagai metode utilitas
public class Helper {

    // Mengatur ikon pada view yang berupa EditText atau TextView
    public static void setIcon(Context context, int icon, View view){
        // Mendapatkan drawable dari sumber daya
        Drawable drawable = ContextCompat.getDrawable(context, icon);

        // Mengecek jika view adalah EditText atau TextView
        if (view instanceof EditText || view instanceof TextView) {
            if (drawable != null) {
                // Mengatur ukuran drawable
                drawable.setBounds(0, 0, 68, 68);
                // Mengatur drawable di awal view
                ((TextView) view).setCompoundDrawables(
                        drawable, // drawable di awal (start)
                        null,     // drawable di atas
                        null,     // drawable di akhir (end)
                        null      // drawable di bawah
                );
            }
        } else if (view instanceof InputText) {
            if (drawable != null) {
                // Mengatur ukuran drawable
                drawable.setBounds(0, 0, 68, 68);
                // Mengatur drawable di awal view InputText
                ((InputText) view).getComponent().setCompoundDrawables(
                        drawable, // drawable di awal (start)
                        null,     // drawable di atas
                        null,     // drawable di akhir (end)
                        null      // drawable di bawah
                );
            }
        }
    }

    // Melakukan logout dengan menghapus status login dari file dan mengalihkan ke LoginActivity
    public static void performLogout(Context context) {
        // Path ke file userdata.txt di direktori eksternal aplikasi
        File file = new File(context.getExternalFilesDir(null), "userdata.txt");

        // Mengecek jika file ada
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] data = new byte[(int) file.length()];
                fis.read(data);
                String fileContent = new String(data);

                // Mengubah konten file menjadi baris-baris
                String[] lines = fileContent.split("\n");

                // Membuat StringBuilder untuk menyusun konten baru
                StringBuilder updatedContent = new StringBuilder();

                for (String line : lines) {
                    if (line.startsWith("status login: ")) {
                        // Menghapus status login dengan tidak menambahkan baris ini ke konten baru
                        continue;
                    }
                    updatedContent.append(line).append("\n");
                }

                // Menulis konten yang telah diperbarui ke file
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(updatedContent.toString().getBytes());
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Menampilkan pesan kesalahan jika gagal menulis file
                    Toast.makeText(context, "Gagal memperbarui file", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mengalihkan ke LoginActivity
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            } catch (IOException e) {
                e.printStackTrace();
                // Menampilkan pesan kesalahan jika gagal membaca file
                Toast.makeText(context, "Gagal membaca file", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Menampilkan pesan kesalahan jika file tidak ditemukan
            Toast.makeText(context, "File userdata.txt tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper class untuk manajemen database SQLite
    public static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "ilhamrhmtkbr.db"; // Nama database
        private static final int DATABASE_VERSION = 1; // Versi database

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Membuat tabel saat pertama kali database dibuat
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE mahasiswa" +
                    " (nomor TEXT PRIMARY KEY, " +
                    "nama TEXT, " +
                    "tanggal_lahir TEXT," +
                    "jenis_kelamin TEXT," +
                    "alamat TEXT);");
        }

        // Menghapus tabel lama dan membuat tabel baru saat versi database diperbarui
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS mahasiswa");
            onCreate(db);
        }
    }

    // Validasi form dengan memeriksa apakah field kosong
    public static boolean validasiForm(Context context, String[] fieldValues, String[] fieldNames){
        boolean isDataValid = false;

        for (int i = 0; i < fieldValues.length; i++) {
            if (fieldValues[i].isEmpty()) {
                // Menampilkan pesan error jika field kosong
                Toast.makeText(context, fieldNames[i] + " tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return false; // Mengembalikan false jika ada field kosong
            } else {
                isDataValid = true;
            }
        }

        return isDataValid; // Mengembalikan true jika semua field valid
    }
}
