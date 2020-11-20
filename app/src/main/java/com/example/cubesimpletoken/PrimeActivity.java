package com.example.cubesimpletoken;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cubesimpletoken.networking.Retro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PrimeActivity extends AppCompatActivity {
    private final Gson gson = new GsonBuilder().create();
    private final String address = "https://po-skud.herokuapp.com/api/cards/card-validation/";
    Button _openAccess;
    TextView _log, _tagLog;
    private final Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(address)
            .build();
    private final Retro req = retrofit.create(Retro.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime2);
        _openAccess = findViewById(R.id.openButton);
        _openAccess.setOnClickListener(x->sendManually());
        _log = findViewById(R.id.logView);
        _tagLog = findViewById(R.id.tagLog);


        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        }else if(!nfcAdapter.isEnabled()){
            Toast.makeText(this,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
            finish();
        }

    }
    private void sendAuto() {
        Call<JsonObject> s = req.updateUser("ajklsfjlksgd456sg43ag5sd132sdg31a2sgdg","0");
        s.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                if (String.valueOf(Objects.requireNonNull(response.body()).get("result")).equals("true"))
                    _log.setText("Открыт доступ\nАвтоматически\nПользователь\nКузнецов Иван Владимирович!");
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(),"Неполадки с сетью. Попробуйте позже!", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void sendManually() {
        Call<JsonObject> s = req.updateUser("ajklsfjlksgd456sg43ag5sd132sdg31a2sgdg","0");
        s.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                if (String.valueOf(Objects.requireNonNull(response.body()).get("result")).equals("true"))
                    _log.setText("Открыт доступ\nВручную\nПользователь\nКузнецов Иван Владимирович!");
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                Toast.makeText(getApplicationContext(),"Неполадки с сетью. Попробуйте позже!", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {


            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag != null){
                StringBuilder tagInfo = new StringBuilder();
                byte[] tagId = tag.getId();
                for (byte b : tagId) {
                    tagInfo.append(Integer.toHexString(b & 0xFF)).append(" ");
                }
                String s = "Считана карта с ID:\n"+tagInfo+"\n";
                _tagLog.setText(s);
                sendAuto();
            }
        }

    }
}