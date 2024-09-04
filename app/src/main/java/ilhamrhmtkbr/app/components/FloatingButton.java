package ilhamrhmtkbr.app.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import ilhamrhmtkbr.app.R;

// Kelas FloatingButton yang merupakan custom view berbasis CoordinatorLayout
public class FloatingButton extends CoordinatorLayout {

    private ImageView fabIcon; // ImageView untuk ikon floating button
    private TextView fabText;  // TextView untuk teks floating button

    // Konstruktor untuk FloatingButton
    public FloatingButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs); // Panggil konstruktor superclass dengan konteks dan atribut
        init(context, attrs);  // Inisialisasi custom view
    }

    // Metode untuk inisialisasi view
    private void init(Context context, AttributeSet attrs) {
        // Inflate layout XML untuk FloatingButton
        LayoutInflater.from(context).inflate(R.layout.comp_floating_button, this, true);

        // Inisialisasi ImageView dengan ID fab_icon dari layout
        fabIcon = findViewById(R.id.fab_icon);

        // Inisialisasi TextView dengan ID fab_text dari layout
        fabText = findViewById(R.id.fab_text);
    }

    // Metode untuk mengatur teks pada FloatingButton
    public void setText(String text) {
        fabText.setText(text); // Set teks pada TextView
    }
}
