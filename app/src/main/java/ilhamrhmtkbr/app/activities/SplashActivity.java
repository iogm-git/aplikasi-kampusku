package ilhamrhmtkbr.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import ilhamrhmtkbr.app.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Mengatur layout untuk activity ini dengan file XML activity_splash

        int splashScreenDuration = 3000; // Durasi splash screen diatur selama 3 detik (3000 milidetik)

        // Handler untuk menunggu sebelum beralih ke LoginActivity
        new Handler().postDelayed(new Runnable() { // Menunda eksekusi perintah di dalam run() selama splashScreenDuration
            @Override
            public void run() {
                // Beralih ke LoginActivity setelah durasi splash selesai
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class); // Membuat intent untuk pindah ke LoginActivity
                startActivity(intent); // Memulai LoginActivity
                finish(); // Tutup SplashActivity agar tidak bisa kembali dengan tombol back
            }
        }, splashScreenDuration);
    }
}
