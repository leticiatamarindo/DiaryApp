package com.example.diaryapp;

//adaptador para a RecyclerView, gerenciando a exibição das notas

// Imports para a criação da interface do usuário e interações
import android.content.Context; // Classe para fornecer informações globais sobre o aplicativo
import android.view.LayoutInflater; // Classe para inflar layouts XML em Views
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

// Imports para a compatibilidade e suporte à anotação
import androidx.annotation.NonNull; // Anotação para indicar que um parâmetro, campo ou retorno não pode ser nulo
import androidx.recyclerview.widget.RecyclerView; // Criação de listas e grids de itens roláveis

import java.util.List; // Interface de Listas

// Adaptador para a RecyclerView, gerenciando a exibição das notas
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context context; // Contexto da aplicação
    private List<Note> noteList; // Lista de notas a serem exibidas
    private OnDeleteClickListener onDeleteClickListener; // Listener para ações de deletar nota

    //Construtor
    public NoteAdapter(Context context, List<Note> noteList, OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        this.noteList = noteList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    // Método chamado quando um novo ViewHolder é criado
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item da nota e cria um ViewHolder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    // Método chamado para vincular dados a um ViewHolder existente
    // ViewHolder: reduz o número de chamadas desnecessárias ao método findViewById
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        // Obter a nota da lista baseada na posição
        Note note = noteList.get(position);
        // Definir os valores dos campos de texto do ViewHolder
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewContent.setText(note.getContent());
        holder.textViewTimestamp.setText(note.getTimestamp());

        // Configurar o botão de deletar para chamar o listener quando clicado
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClickListener.onDeleteClick(note);
            }
        });
    }

    // Método para obter o número total de itens na lista
    @Override
    public int getItemCount() {
        return noteList.size();
    }

    // Classe ViewHolder para armazenar as views dos itens da nota
    public class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle; // Campos de texto
        public TextView textViewContent;
        public TextView textViewTimestamp;
        public Button buttonDelete; // Botão para deletar a nota

        // Construtor para inicializar as views do ViewHolder
        public NoteViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    // Interface para o listener de ações de deletar nota
    public interface OnDeleteClickListener {
        void onDeleteClick(Note note); // Método chamado quando uma nota é deletada
    }
}
