package com.example.appemptyviewsactivity1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*
        Toast.makeText(this, "Olá usuário...", Toast.LENGTH_SHORT).show();
        Log.i("info", "Botão criado ok...");
        */

        DatabaseHandler db = new DatabaseHandler(this);
        Log.d("Insert: ", "Inserindo clientes...");
        db.addCliente(new Cliente("Bruno"));
        db.addCliente(new Cliente("Marcos"));
        db.addCliente(new Cliente("Miranda"));

        Log.d("Select: ", "Lendo os clientes...");
        List<Cliente> clientes = db.getAllClientes();

        for (Cliente cliente : clientes){
            String log = "Id: " + cliente.getId() + ", Nome: " + cliente.getNome();
            Log.d("Nome >> ", log);
        }
    }

    public void topClick(View view) {
        Toast.makeText(this, "Olá usuário...", Toast.LENGTH_SHORT).show();

        Log.i("info", "Botão criado ok...");
    }
}