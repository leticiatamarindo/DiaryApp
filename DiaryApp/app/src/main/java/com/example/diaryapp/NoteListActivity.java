package com.example.diaryapp;

import android.database.Cursor; // Classe para navegar pelos resultados de uma consulta do db
import android.database.sqlite.SQLiteDatabase; // manipulação de banco de dados SQLite
import android.os.AsyncTask; // realizar operações em segundo plano
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity; // Para atividades que usam a biblioteca de suporte ActionBar
import androidx.recyclerview.widget.LinearLayoutManager; // Gerenciar o layout do RecyclerView
import androidx.recyclerview.widget.RecyclerView; // Classe para exibir listas de itens

import java.util.ArrayList; // Classe para listas redimensionáveis
import java.util.List; // Interface para listas

// Exibir a lista de notas
public class NoteListActivity extends AppCompatActivity implements NoteAdapter.OnDeleteClickListener {

    private RecyclerView recyclerView; // RecyclerView para exibir as notas
    private TextView textViewEmpty; // TextView para exibir mensagem quando a lista estiver vazia
    private NoteAdapter noteAdapter; // Adaptador para o RecyclerView
    private List<Note> noteList; // Lista de notas
    private NoteDatabaseHelper dbHelper; // Helper para o db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        recyclerView = findViewById(R.id.recyclerViewNotes); // Inicializa o RecyclerView
        textViewEmpty = findViewById(R.id.textViewEmpty); // Inicializa o TextView vazio
        noteList = new ArrayList<>(); // Inicializa a lista de notas
        dbHelper = new NoteDatabaseHelper(this); // Inicializa o helper do banco de dados

        // Configura o RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Define o layout do RecyclerView como linear
        noteAdapter = new NoteAdapter(this, noteList, this); // Inicializa o adaptador do RecyclerView
        recyclerView.setAdapter(noteAdapter); // Define o adaptador do RecyclerView

        // Carrega as notas do banco de dados em segundo plano
        new LoadNotesTask().execute();
    }

    @Override
    public void onDeleteClick(Note note) {
        // Chama a tarefa de deletar a nota em segundo plano
        new DeleteNoteTask().execute(note);
    }

    // Classe AsyncTask para carregar notas do banco de dados em segundo plano
    private class LoadNotesTask extends AsyncTask<Void, Void, List<Note>> {

        @Override
        protected List<Note> doInBackground(Void... voids) {
            // Executa a consulta ao banco de dados em segundo plano
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] projection = {
                    NoteDatabaseHelper.COLUMN_ID,
                    NoteDatabaseHelper.COLUMN_TITLE,
                    NoteDatabaseHelper.COLUMN_CONTENT,
                    NoteDatabaseHelper.COLUMN_TIMESTAMP
            };

            // Executa a consulta para obter todas as notas, ordenadas por timestamp desc - (do mais recente para o mais antigo)
            Cursor cursor = db.query(
                    NoteDatabaseHelper.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    NoteDatabaseHelper.COLUMN_TIMESTAMP + " DESC"
            );

            List<Note> notes = new ArrayList<>();
            // Verifica se o cursor não é nulo e move-se para o primeiro resultado
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Extrai os valores das colunas do cursor
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_TITLE));
                    String content = cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_CONTENT));
                    String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabaseHelper.COLUMN_TIMESTAMP));

                    // Cria uma nova nota e adiciona à lista de notas
                    Note note = new Note(id, title, content, timestamp);
                    notes.add(note);
                } while (cursor.moveToNext()); // Move-se para o próximo resultado

                cursor.close(); // Fecha o cursor
            }

            db.close(); // Fecha o banco de dados

            return notes; // Retorna a lista de notas
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            // Atualiza a interface do usuário com a lista de notas carregadas
            noteList.clear(); // Limpa a lista de notas existente
            noteList.addAll(notes); // Adiciona todas as notas carregadas
            updateUI(); // Atualiza a interface do usuário
        }
    }

    // Classe AsyncTask para deletar nota do banco de dados em segundo plano
    private class DeleteNoteTask extends AsyncTask<Note, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Note... notes) {
            // Executa a operação de deletar a nota em segundo plano
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            String selection = NoteDatabaseHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = { String.valueOf(notes[0].getId()) };

            // Executa a exclusão da nota no banco de dados
            int deletedRows = db.delete(NoteDatabaseHelper.TABLE_NAME, selection, selectionArgs);
            db.close(); // Fecha o banco de dados

            return deletedRows > 0; // Retorna verdadeiro se uma ou mais linhas foram deletadas
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // Se a exclusão foi bem-sucedida, recarrega as notas
                new LoadNotesTask().execute();
            }
        }
    }
    // Atualização do layout
    private void updateUI() {
        // Atualiza a interface do usuário com base no estado da lista de notas
        if (noteList.isEmpty()) {
            textViewEmpty.setVisibility(TextView.VISIBLE); // Exibe o TextView vazio se a lista estiver vazia
            recyclerView.setVisibility(RecyclerView.GONE); // Esconde o RecyclerView
        } else {
            textViewEmpty.setVisibility(TextView.GONE); // Esconde o TextView vazio
            recyclerView.setVisibility(RecyclerView.VISIBLE); // Exibe o RecyclerView
            noteAdapter.notifyDataSetChanged(); // Notifica o adaptador que os dados mudaram
        }
    }
}
