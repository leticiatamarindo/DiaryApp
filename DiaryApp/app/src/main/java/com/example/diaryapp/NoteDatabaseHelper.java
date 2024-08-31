package com.example.diaryapp;

// banco
import android.content.Context; // Classe para fornecer informações globais sobre o aplicativo
import android.database.sqlite.SQLiteDatabase; // Manipulação de banco de dados SQLite
import android.database.sqlite.SQLiteOpenHelper; // Criação e gerenciamento de banco de dados SQLite

public class NoteDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela e colunas
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIMESTAMP = "timestamp"; // Data e hora

    // Comando SQL para criar a tabela
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Coluna ID com auto incremento
                    COLUMN_TITLE + " TEXT NOT NULL, " + // Coluna título não nula
                    COLUMN_CONTENT + " TEXT, " + // Coluna conteúdo
                    COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"; // Coluna timestamp com valor padrão de timestamp atual

    // Construtor da classe helper
    public NoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // Chama o construtor da classe pai
    }

    // Método chamado quando o banco de dados é criado pela primeira vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    // Método chamado quando o banco de dados precisa ser atualizado
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aqui teria uma lógica de atualização do db
    }
}
