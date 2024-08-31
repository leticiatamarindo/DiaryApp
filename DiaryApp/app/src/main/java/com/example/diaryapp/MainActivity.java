package com.example.diaryapp;

import android.content.Intent; // Intents
import android.os.Bundle; // Classe para transportar dados entre atividades
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// compatibilidade com versões mais antigas do Android
import androidx.appcompat.app.AppCompatActivity;

// Imports para manipulação de datas e formatação
import java.text.SimpleDateFormat; // Formatação
import java.util.Calendar; // Manipulaçao de datas
import java.util.Locale; // Especificar a localidade

public class MainActivity extends AppCompatActivity {

    private TextView textViewDate; // TextView para exibir a data atual
    private Button buttonNovaNota; // Botão para criar uma nova nota
    private Button buttonRegistros; // Botão para visualizar as notas registradas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar os componentes da interface
        textViewDate = findViewById(R.id.textViewDate);
        buttonNovaNota = findViewById(R.id.buttonNovaNota);
        buttonRegistros = findViewById(R.id.buttonRegistros);

        // Obter a data atual
        Calendar calendar = Calendar.getInstance(); // Obter instância do calendário
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()); // Definir o formato da data
        String currentDate = dateFormat.format(calendar.getTime()); // Obter a data atual formatada

        // Exibir a data atual no TextView
        textViewDate.setText(currentDate);

        // Configurar clique no botão "Nova nota" para abrir a NewNoteActivity
        buttonNovaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewNoteActivity(); // Método para abrir a atividade de criar nova nota
            }
        });

        // Configurar clique no botão "Registros" para abrir a NoteListActivity
        buttonRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNoteListActivity(); // Método para abrir a atividade de visualizar notas registradas
            }
        });
    }

    // Método para iniciar a NewNoteActivity
    private void openNewNoteActivity() {
        Intent intent = new Intent(MainActivity.this, NewNoteActivity.class); // Criar intenção para iniciar NewNoteActivity
        startActivity(intent);
    }

    // Método para iniciar a NoteListActivity
    private void openNoteListActivity() {
        Intent intent = new Intent(MainActivity.this, NoteListActivity.class); // Criar intenção para iniciar NoteListActivity
        startActivity(intent);
    }
}
