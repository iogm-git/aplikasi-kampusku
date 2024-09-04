package ilhamrhmtkbr.app.components;

import android.content.Context; // Untuk konteks aplikasi
import android.content.res.TypedArray; // Untuk mengakses atribut yang didefinisikan dalam XML
import android.util.AttributeSet; // Untuk membaca atribut dari XML
import android.view.LayoutInflater; // Untuk menginflate layout XML
import android.widget.ArrayAdapter; // Untuk adapter array pada Spinner
import android.widget.LinearLayout; // Layout dasar yang digunakan
import android.widget.Spinner; // Widget spinner untuk memilih opsi
import android.widget.TextView; // Untuk menampilkan teks

import androidx.annotation.Nullable; // Untuk nullable attribute

import ilhamrhmtkbr.app.R; // Resource ID

// Kelas custom view untuk memilih opsi dari daftar
public class InputSelect extends LinearLayout {
    private TextView tvLabel; // TextView untuk label
    private Spinner spinner; // Spinner untuk menampilkan opsi
    private ArrayAdapter<String> adapter; // Adapter untuk mengisi data ke Spinner

    // Konstruktor dengan konteks dan atribut
    public InputSelect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs); // Memanggil konstruktor superclass
        init(context, attrs); // Inisialisasi custom view
    }

    // Metode untuk menginisialisasi view
    private void init(Context context, AttributeSet attrs) {
        // Inflate layout XML untuk komponen ini
        LayoutInflater.from(context).inflate(R.layout.comp_input_select, this, true);

        // Menghubungkan TextView dan Spinner dengan ID dari layout XML
        tvLabel = findViewById(R.id.tvLabel);
        spinner = findViewById(R.id.spinnerOptions);

        // Mengambil atribut custom dari XML
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonAttributes);
        String labelText = a.getString(R.styleable.CommonAttributes_labelText);

        // Mengatur teks label jika ada
        if (labelText != null) {
            tvLabel.setText(labelText);
        }
        a.recycle(); // Membersihkan TypedArray setelah digunakan

        // Mengambil opsi dari atribut
        TypedArray b = context.obtainStyledAttributes(attrs, R.styleable.InputSelect);
        String options = b.getString(R.styleable.InputSelect_options);

        // Jika ada opsi, atur adapter untuk Spinner
        if (options != null) {
            // Opsi dipisahkan dengan koma
            String[] optArray = options.split(",");
            adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_item, optArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter); // Mengatur adapter pada Spinner
        }
        b.recycle(); // Membersihkan TypedArray setelah digunakan
    }

    // Mengembalikan nilai yang dipilih dari Spinner
    public String getValue() {
        return spinner.getSelectedItem().toString(); // Mengembalikan teks item yang dipilih
    }

    // Mengatur nilai pada Spinner berdasarkan nilai yang diberikan
    public void setValue(String value) {
        // Temukan posisi item dalam adapter
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                spinner.setSelection(i); // Mengatur posisi item pada Spinner
                return; // Keluar setelah ditemukan
            }
        }
    }

    // Mengatur teks label pada TextView
    public void setLabelText(String text) {
        tvLabel.setText(text); // Mengatur teks pada TextView label
    }
}
