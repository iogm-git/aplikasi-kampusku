package ilhamrhmtkbr.app.components;

import android.annotation.SuppressLint; // Untuk menghindari peringatan lint pada penggunaan setText()
import android.app.DatePickerDialog; // Untuk menampilkan dialog pemilih tanggal
import android.content.Context; // Untuk konteks aplikasi
import android.content.res.TypedArray; // Untuk mengakses atribut yang didefinisikan dalam XML
import android.util.AttributeSet; // Untuk membaca atribut dari XML
import android.view.LayoutInflater; // Untuk menginflate layout XML
import android.view.View; // Untuk menangani tampilan
import android.widget.DatePicker; // Untuk memilih tanggal
import android.widget.EditText; // Untuk input teks
import android.widget.LinearLayout; // Layout dasar yang digunakan
import android.widget.TextView; // Untuk menampilkan teks

import java.util.Calendar; // Untuk mengelola tanggal dan waktu

import ilhamrhmtkbr.app.Helper; // Helper class yang digunakan untuk fungsi tambahan
import ilhamrhmtkbr.app.R; // Resource ID

// Kelas custom view untuk input tanggal
public class InputDate extends LinearLayout {

    private TextView tvLabel; // TextView untuk label
    private EditText etInput; // EditText untuk input tanggal

    // Konstruktor dengan konteks dan atribut
    public InputDate(Context context, AttributeSet attrs) {
        super(context, attrs); // Memanggil konstruktor superclass
        init(context, attrs); // Inisialisasi custom view
    }

    // Metode untuk menginisialisasi view
    private void init(Context context, AttributeSet attrs) {
        // Inflate layout XML untuk komponen ini
        LayoutInflater.from(context).inflate(R.layout.comp_input_date, this, true);

        // Menghubungkan TextView dan EditText dengan ID dari layout XML
        tvLabel = findViewById(R.id.tvLabel);
        etInput = findViewById(R.id.etInput);

        // Menetapkan ikon kalender pada EditText menggunakan helper
        Helper.setIcon(getContext(), R.drawable.ic_calendar, etInput);

        // Mengambil atribut custom dari XML
        // TypedArray digunakan untuk mengambil atribut custom yang didefinisikan di XML dan mengatur label jika atribut labelText tersedia.
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonAttributes);
        String labelText = a.getString(R.styleable.CommonAttributes_labelText);

        // Mengatur teks label jika ada
        if (labelText != null) {
            tvLabel.setText(labelText);
        }

        // Membersihkan TypedArray setelah digunakan
        a.recycle();

        // Menambahkan OnClickListener pada EditText
        etInput.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mendapatkan tanggal saat ini
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Membuat DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context, // Menggunakan konteks yang benar
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                                // Mengatur tanggal yang dipilih ke EditText
                                etInput.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                            }
                        },
                        year, month, day);
                // Menampilkan dialog pemilih tanggal
                datePickerDialog.show();
            }
        });
    }

    // Mengembalikan nilai dari EditText
    public String getValue() {
        return etInput.getText().toString(); // Mengembalikan teks input
    }

    // Mengatur nilai pada EditText
    public void setValue(String value) {
        this.etInput.setText(value); // Mengatur teks input
    }

    // Mengatur teks label
    public void setLabelText(String text) {
        tvLabel.setText(text); // Mengatur teks pada TextView label
    }
}
