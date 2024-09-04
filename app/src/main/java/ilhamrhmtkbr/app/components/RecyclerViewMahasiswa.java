package ilhamrhmtkbr.app.components;

import android.app.Activity; // Untuk mengelola aktivitas
import android.app.AlertDialog; // Untuk membuat dialog
import android.content.ContentValues; // Untuk menyimpan pasangan key-value
import android.content.Context; // Untuk konteks aplikasi
import android.content.Intent; // Untuk mengelola intents
import android.view.LayoutInflater; // Untuk menginflate layout XML
import android.view.View; // Untuk menangani tampilan
import android.view.ViewGroup; // Untuk mengelola grup tampilan
import android.widget.TextView; // Untuk menampilkan teks
import android.widget.Toast; // Untuk menampilkan pesan singkat

import androidx.annotation.NonNull; // Untuk menandai metode atau parameter tidak boleh null
import androidx.recyclerview.widget.RecyclerView; // Untuk RecyclerView dan Adapter

import java.util.List; // Untuk mengelola daftar data

import ilhamrhmtkbr.app.Helper; // Untuk utilitas tambahan
import ilhamrhmtkbr.app.R; // Resource ID
import ilhamrhmtkbr.app.activities.mahasiswa.EditActivity; // Activity untuk mengedit data mahasiswa
import ilhamrhmtkbr.app.activities.mahasiswa.IndexActivity; // Activity untuk menampilkan daftar mahasiswa
import ilhamrhmtkbr.app.activities.mahasiswa.ShowActivity; // Activity untuk menampilkan detail data mahasiswa

// Kelas utama untuk RecyclerView dengan data mahasiswa
public class RecyclerViewMahasiswa {

    // Model data mahasiswa
    public static class MahasiswaModel {
        String nomor, nama, tanggal_lahir, jenis_kelamin, alamat;

        // Konstruktor untuk nomor dan nama saja
        public MahasiswaModel(String nomor, String nama) {
            this.nomor = nomor;
            this.nama = nama;
        }

        // Konstruktor untuk semua atribut
        public MahasiswaModel(String nomor, String nama, String tanggal_lahir, String jenis_kelamin, String alamat) {
            this.nomor = nomor;
            this.nama = nama;
            this.tanggal_lahir = tanggal_lahir;
            this.jenis_kelamin = jenis_kelamin;
            this.alamat = alamat;
        }

        // Getter dan setter untuk nomor
        public String getNomor() {
            return nomor;
        }

        public void setNomor(String nomor) {
            this.nomor = nomor;
        }

        // Getter dan setter untuk nama
        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        // Getter dan setter untuk tanggal lahir
        public String getTanggal_lahir() {
            return tanggal_lahir;
        }

        public void setTanggal_lahir(String tanggal_lahir) {
            this.tanggal_lahir = tanggal_lahir;
        }

        // Getter dan setter untuk jenis kelamin
        public String getJenis_kelamin() {
            return jenis_kelamin;
        }

        public void setJenis_kelamin(String jenis_kelamin) {
            this.jenis_kelamin = jenis_kelamin;
        }

        // Getter dan setter untuk alamat
        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }
    }

    // Adapter untuk RecyclerView
    public static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        private List<MahasiswaModel> items; // Daftar data mahasiswa
        private Context context; // Konteks aplikasi

        private Helper.DatabaseHelper dbHelper; // Helper untuk database

        // Konstruktor adapter
        public ItemAdapter(Context context, List<MahasiswaModel> items) {
            this.context = context;
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Menginflate layout item_data_mahasiswa untuk setiap item dalam RecyclerView
            View view = LayoutInflater.from(context).inflate(R.layout.item_data_mahasiswa, parent, false);
            return new ViewHolder(view); // Mengembalikan ViewHolder baru
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // Mengikat data mahasiswa ke ViewHolder
            MahasiswaModel item = items.get(position);
            holder.name.setText(item.getNama()); // Mengatur nama mahasiswa di TextView

            dbHelper = new Helper.DatabaseHelper(context);

            // Mengatur aksi klik pada item
            holder.itemView.setOnClickListener(view -> {
                // Membuat dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.alert_dialog_data_mahasiswa, null);

                // Menyambungkan elemen-elemen dialog
                TextView navShow = dialogView.findViewById(R.id.nav_show_mahasiswa);
                TextView navEdit = dialogView.findViewById(R.id.nav_edit_mahasiswa);
                TextView navDestroy = dialogView.findViewById(R.id.nav_destroy_mahasiswa);

                // Mengatur ikon untuk opsi dialog
                Helper.setIcon(context, R.drawable.ic_search, navShow);
                Helper.setIcon(context, R.drawable.ic_edit, navEdit);
                Helper.setIcon(context, R.drawable.ic_trash, navDestroy);

                // Menyusun dialog
                builder.setView(dialogView)
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss()) // Menutup dialog dengan tombol OK
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()); // Menutup dialog dengan tombol Cancel
                AlertDialog dialog = builder.create();

                // Menangani klik tombol di dialog
                navShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(ShowActivity.class, item.getNomor()); // Memulai ShowActivity untuk menampilkan detail mahasiswa
                        dialog.dismiss(); // Menutup dialog
                    }
                });

                navEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(EditActivity.class, item.getNomor()); // Memulai EditActivity untuk mengedit data mahasiswa
                        dialog.dismiss(); // Menutup dialog
                    }
                });

                navDestroy.setOnClickListener(v -> {
                    destroy(item.getNomor()); // Menghapus data mahasiswa dari database
                    dialog.dismiss(); // Menutup dialog
                });

                dialog.show(); // Menampilkan dialog
            });
        }

        // Callback interface
        public interface OnDataChangedListener {
            void onDataChanged();
        }

        private OnDataChangedListener onDataChangedListener;

        // Setter for listener
        public void setOnDataChangedListener(OnDataChangedListener listener) {
            this.onDataChangedListener = listener;
        }

        private void destroy(String nomor) {
            long result = dbHelper.getWritableDatabase().delete("mahasiswa", "nomor = ?", new String[]{nomor});
            if (result != -1) {
                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                if (onDataChangedListener != null) {
                    onDataChangedListener.onDataChanged(); // Notify listener
                }
            } else {
                Toast.makeText(context, "Terjadi kesalahan saat menghapus data", Toast.LENGTH_SHORT).show();
            }
        }

        // Memulai activity dengan data nomor
        private void startActivity(Class<?> activityClass, String nomor) {
            Intent intent = new Intent(context, activityClass);
            intent.putExtra("nomor", nomor); // Menyertakan data nomor dalam intent
            context.startActivity(intent); // Memulai activity
            if (context instanceof Activity) {
                ((Activity) context).finish(); // Menutup activity saat ini jika konteks adalah Activity
            }
        }

        @Override
        public int getItemCount() {
            return items.size(); // Mengembalikan jumlah item dalam daftar
        }

        // ViewHolder untuk item di RecyclerView
        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView name; // TextView untuk nama mahasiswa

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.item_name); // Menghubungkan TextView nama dengan ID dari layout item
            }
        }
    }
}
