package ilhamrhmtkbr.app.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ilhamrhmtkbr.app.R;

public class InfoActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_info);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tentang Aplikasi");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

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
