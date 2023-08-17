package com.example;

public record Aluno(String nome, String turma, Integer rm) {
    @Override
    public String toString(){
        // 20202 Joao (turma)
        return rm + " " + nome + " (" + turma + ")";
    }
}
