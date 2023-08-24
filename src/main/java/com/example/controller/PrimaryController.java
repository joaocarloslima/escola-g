package com.example.controller;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.data.AlunoDao;
import com.example.model.Aluno;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class PrimaryController {
    // textField e TextArea são objetos/classes
    @FXML
    ListView<Aluno> listAlunos;
    @FXML
    TextField txtNome;
    @FXML
    TextField txtTurma;
    @FXML
    TextField txtRm;
    @FXML
    RadioButton rdbCrescente;
    @FXML
    RadioButton rdbDecrescente;

    // collections em java - ArrayList
    // <String> - generics
    // inner class x class
    // (classe com metodos de uso interno usaria ele aqui dentro)
    private ArrayList<Aluno> alunos = new ArrayList<>();
    AlunoDao alunoDao = new AlunoDao();

    public void adicionarAluno() {
        var aluno = new Aluno(txtNome.getText(), txtTurma.getText(), Integer.valueOf (txtRm.getText()) );
        try {
            alunoDao.inserir(aluno);
           
            mostrarMensagem(AlertType.INFORMATION, "Sucesso", "aluno cadastrado com sucesso");
    
        } catch (Exception e) {
            mostrarMensagem(
                AlertType.ERROR,
                "Erro ao cadastrar aluno", 
                e.getLocalizedMessage()
            );
        }
        
        txtNome.clear();
        txtRm.clear();
        txtTurma.clear();

        atualizarAluno();
    }

    public void atualizarAluno() {
        listAlunos.getItems().clear();
        try {
            alunoDao
                .listarTodos()
                .forEach(aluno -> listAlunos.getItems().add(aluno));
        } catch (SQLException e) {
            mostrarMensagem(
                AlertType.ERROR,
                "Erro ao buscar dados dos aluno", 
                e.getLocalizedMessage()
            );
        }
    }

    private void ordenar() {
        if (rdbCrescente.isSelected()) {
            alunos.sort((o1, o2) -> o1.nome().compareToIgnoreCase(o2.nome()));
        } else {
            alunos.sort((o1, o2) -> o2.nome().compareToIgnoreCase(o1.nome()));
        }
    }

    public void apagarAluno() {
        try {
            var aluno = listAlunos.getSelectionModel().getSelectedItem();
            alunoDao.apagar(aluno);
            atualizarAluno();
            mostrarMensagem(
                AlertType.INFORMATION, 
                "Sucesso",  
                "Aluno(a) " + aluno.nome() + " apagado(a) com sucesso"
            );
        } catch (SQLException e) {
            mostrarMensagem(AlertType.ERROR, "Erro ao apagar aluno", e.getLocalizedMessage());
        }
    }

    private void mostrarMensagem(AlertType tipo, String titulo, String texto){
        Alert mensagem = new Alert(tipo);
        mensagem.setHeaderText(titulo);
        mensagem.setContentText(texto);
        mensagem.show();
    }
}
