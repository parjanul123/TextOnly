package com.example.qrlogin;

import com.example.textonly.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class ProfileActivity extends Activity {
    private EditText editDisplayName;
    private EditText editPhoneNumber;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editDisplayName = findViewById(R.id.edit_display_name);
        editPhoneNumber = findViewById(R.id.edit_phone_number);
        btnSave = findViewById(R.id.btn_save);

        // TODO: Încarcă datele utilizatorului din storage/local sau backend

        btnSave.setOnClickListener(v -> {
            // TODO: Salvează datele local sau trimite la backend
            Toast.makeText(this, "Profil salvat!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
