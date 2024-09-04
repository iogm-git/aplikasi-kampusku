package ilhamrhmtkbr.app.components;

import android.content.Context; // Untuk konteks aplikasi
import android.content.res.TypedArray; // Untuk mengakses atribut yang didefinisikan dalam XML
import android.graphics.drawable.Drawable; // Untuk Drawable, digunakan pada ikon
import android.text.InputType; // Untuk tipe input pada EditText
import android.util.AttributeSet; // Untuk membaca atribut dari XML
import android.view.LayoutInflater; // Untuk menginflate layout XML
import android.view.View; // Untuk menangani tampilan
import android.widget.EditText; // Untuk input teks
import android.widget.ImageView; // Untuk menampilkan ikon
import android.widget.LinearLayout; // Layout dasar yang digunakan
import android.widget.TextView; // Untuk menampilkan teks

import androidx.core.content.ContextCompat; // Untuk mendapatkan Drawable dari context

import ilhamrhmtkbr.app.R; // Resource ID

// Kelas custom view untuk input teks dengan label dan toggle visibilitas password
public class InputText extends LinearLayout {

    private TextView tvLabel; // TextView untuk label
    private EditText etInput; // EditText untuk input
    private ImageView ivTogglePassword; // ImageView untuk toggle visibilitas password
    private boolean isPasswordVisible = false; // Status visibilitas password

    // Konstruktor dengan konteks dan atribut
    public InputText(Context context, AttributeSet attrs) {
        super(context, attrs); // Memanggil konstruktor superclass
        init(context, attrs); // Inisialisasi custom view
    }

    // Metode untuk menginisialisasi view
    private void init(Context context, AttributeSet attrs) {
        // Inflate layout XML untuk komponen ini
        LayoutInflater.from(context).inflate(R.layout.comp_input_text, this, true);

        // Menghubungkan TextView, EditText, dan ImageView dengan ID dari layout XML
        tvLabel = findViewById(R.id.tvLabel);
        etInput = findViewById(R.id.etInput);
        ivTogglePassword = findViewById(R.id.ivTogglePassword);

        // Mengambil atribut custom dari XML
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonAttributes);
        String labelText = a.getString(R.styleable.CommonAttributes_labelText);

        // Mengatur teks label jika ada
        if (labelText != null) {
            tvLabel.setText(labelText);
        }

        // Membaca atribut android:inputType dari XML
        int inputType = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "inputType", InputType.TYPE_CLASS_TEXT);
        etInput.setInputType(inputType); // Mengatur jenis input berdasarkan atribut

        // Memeriksa apakah inputType adalah password
        if ((inputType & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD ||
                (inputType & InputType.TYPE_NUMBER_VARIATION_PASSWORD) == InputType.TYPE_NUMBER_VARIATION_PASSWORD) {
            ivTogglePassword.setVisibility(View.VISIBLE); // Menampilkan ikon mata
            setupPasswordToggle(); // Mengatur fungsi toggle password
        } else {
            ivTogglePassword.setVisibility(View.GONE); // Menyembunyikan ikon mata jika bukan password
        }

        a.recycle(); // Membersihkan TypedArray setelah digunakan
    }

    // Metode untuk mengatur aksi saat ikon mata diklik
    private void setupPasswordToggle() {
        ivTogglePassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(); // Memanggil metode untuk toggle visibilitas password
            }
        });
    }

    // Metode untuk toggle visibilitas password
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Jika password terlihat, sembunyikan password
            etInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivTogglePassword.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off)); // Ganti ikon menjadi mata tertutup
        } else {
            // Jika password tersembunyi, tampilkan password
            etInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivTogglePassword.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility)); // Ganti ikon menjadi mata terbuka
        }
        // Pindahkan cursor ke akhir teks
        etInput.setSelection(etInput.getText().length());
        isPasswordVisible = !isPasswordVisible; // Toggle status visibilitas
    }

    // Mengembalikan teks input dari EditText
    public String getValue() {
        return etInput.getText().toString(); // Mengembalikan teks input
    }

    // Mengembalikan EditText komponen
    public EditText getComponent(){
        return etInput; // Mengembalikan EditText
    }

    // Mengatur teks input pada EditText
    public void setValue(String value) {
        this.etInput.setText(value); // Mengatur teks pada EditText
    }

    // Mengatur teks label pada TextView
    public void setLabelText(String text) {
        tvLabel.setText(text); // Mengatur teks pada TextView label
    }

    // Mengatur jenis input pada EditText
    public void setInputType(int inputType) {
        etInput.setInputType(inputType); // Mengatur jenis input
    }

}
