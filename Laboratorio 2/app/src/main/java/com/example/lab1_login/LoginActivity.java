package com.example.lab1_login;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1_login.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        EditText edtUsername = binding.edtUsername;
        EditText edtPassword = binding.edtPassword;
        Button btnLogin = binding.btnLogin;
        Button btnAddAccount = binding.btnAddAccount;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountsData = readFile("cuentas.txt");
                if (!accountsData.isEmpty()) {
                    Gson gson = new Gson();
                    AccountEntity[] accounts = gson.fromJson(accountsData, AccountEntity[].class);

                    boolean found = false;
                    for (AccountEntity account : accounts) {
                        if (account.getUsername().equals(edtUsername.getText().toString()) && account.getPassword().equals(edtPassword.getText().toString())) {
                            Toast.makeText(getApplicationContext(),"Bienvenido " + account.getFirstname(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"Bienvenido " + account.getFirstname());
                            // Mostrar el mensaje en la misma actividad
                            // Puedes actualizar la interfaz aquí, por ejemplo, con un TextView.
                            binding.txtWelcome.setText("Bienvenido " + account.getFirstname());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        Toast.makeText(getApplicationContext(), "Cuenta no encontrada", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"Error en la autenticación");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Archivo vacío", Toast.LENGTH_SHORT).show();
                }
            }
        });



        btnAddAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),AccountActivity.class);
            activityResultLauncher.launch(intent);
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult activityResult) {
                    Integer resultCode = activityResult.getResultCode();

                    if (resultCode == AccountActivity.ACCOUNT_ACEPTAR) {
                        Intent data = activityResult.getData();
                        String accountRecord = data.getStringExtra(AccountActivity.ACCOUNT_RECORD);

                        // Escribir el nuevo usuario en cuentas.txt
                        writeFile("cuentas.txt", accountRecord + "\n");

                        Gson gson = new Gson();
                        AccountEntity accountEntity = gson.fromJson(accountRecord, AccountEntity.class);

                        String firstname = accountEntity.getFirstname();
                        Toast.makeText(getApplicationContext(), "Nombre: " + firstname, Toast.LENGTH_SHORT).show();

                    } else if (resultCode == AccountActivity.ACCOUNT_CANCELAR) {
                        Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    // Leer archivo
    private String readFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    // Escribir archivo
    private void writeFile(String fileName, String data) {
        try {
            FileOutputStream fos = openFileOutput(fileName, MODE_APPEND);
            fos.write(data.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}