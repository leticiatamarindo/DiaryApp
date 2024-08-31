package com.example.diaryapp;

public class Note {
    // Campos da classe Note
    private long id;
    private String title;
    private String content;
    private String timestamp; // Data e hora de criação da nota

    // Construtor que inicializa título, conteúdo e timestamp (sem ID) (criando uma nova nota que ainda não foi salva no banco de dados)
    public Note(String title, String content, String timestamp) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Construtor que inicializa todos os campos, incluindo ID (notas que já existem no banco de dados)
    public Note(long id, String title, String content, String timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
