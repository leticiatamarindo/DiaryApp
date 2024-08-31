package com.example.diaryapp;

// banco
import android.content.ContentValues; // Classe para armazenar valores de dados que serão inseridos no banco de dados
import android.database.sqlite.SQLiteDatabase;

// interface e interações
import android.os.Bundle;
import android.widget.Button; // Classe para o botão de interface do usuário
import android.widget.EditText;
import android.widget.Toast; // Classe para exibir mensagens curtas na tela

// compatibilidade com versões mais antigas do Android
import androidx.appcompat.app.AppCompatActivity;

public class NewNoteActivity extends AppCompatActivity {

    private EditText editTextTitle; // EditText para o título da nota
    private EditText editTextContent; // EditText para o conteúdo da nota
    private Button saveButton; // Botão para salvar a nota
    private NoteDatabaseHelper dbHelper; // Helper para o banco de dados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        // Inicializar os componentes da interface
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);
        saveButton = findViewById(R.id.saveButton);
        dbHelper = new NoteDatabaseHelper(this); // Inicializar o helper do banco de dados

        // Configurar clique no botão de salvar para chamar o método saveNote()
        saveButton.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        // Obter o título e o conteúdo da nota
        String title = editTextTitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();

        // Verificar se o título está vazio
        if (title.isEmpty()) {
            editTextTitle.setError("O título não pode estar vazio"); // Mostrar erro no EditText do título
            editTextTitle.requestFocus(); // Focar no EditText do título
            return; // Sair do método se o título estiver vazio
        }

        // Obter uma instância gravável do banco de dados
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Criar um objeto ContentValues para armazenar os valores a serem inseridos
        ContentValues values = new ContentValues();
        values.put(NoteDatabaseHelper.COLUMN_TITLE, title); // Adicionar o título aos valores
        values.put(NoteDatabaseHelper.COLUMN_CONTENT, content); // Adicionar o conteúdo aos valores

        // Inserir os valores no banco de dados e obter o ID da nova linha
        long newRowId = db.insert(NoteDatabaseHelper.TABLE_NAME, null, values);
        db.close(); // Fechar o banco de dados

        // Verificar se a inserção foi bem-sucedida
        if (newRowId == -1) {
            Toast.makeText(this, "Erro ao salvar nota", Toast.LENGTH_SHORT).show(); // Mostrar mensagem de erro
        } else {
            Toast.makeText(this, "Nota salva com sucesso", Toast.LENGTH_SHORT).show(); // Mostrar mensagem de sucesso
            finish(); // Fechar a atividade após salvar
        }
    }
}
