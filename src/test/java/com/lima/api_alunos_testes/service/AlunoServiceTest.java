package com.lima.api_alunos_testes.service;

import com.lima.api_alunos_testes.dto.AlunoRequest;
import com.lima.api_alunos_testes.dto.AlunoResponse;
import com.lima.api_alunos_testes.model.Aluno;
import com.lima.api_alunos_testes.repository.AlunoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @InjectMocks
    AlunoService service;

    @Mock
    AlunoRepository repository;

    @Test
    void deveSalvarERetornarAluno() {

        //Arrange
        AlunoRequest alunoRequest = new AlunoRequest(
                "Patricia",
                "patricia@gmail.com",
                "123456",
                LocalDate.of(1992, 10, 26),
                8
        );

        //Stub
        Mockito.when(repository.existsByEmailIgnoreCase(alunoRequest.getEmail()))
                .thenReturn(false);

        Aluno aluno = new Aluno(
                1L,
                alunoRequest.getNome(),
                alunoRequest.getEmail(),
                alunoRequest.getSenha(),
                alunoRequest.getDataNascimento(),
                alunoRequest.getMedia()
        );

        Mockito.when(repository.save(Mockito.any(Aluno.class))).thenReturn(aluno);
        //Act

        AlunoResponse alunoSalvo = service.cadastrarAluno(alunoRequest);

        //Assert
        Assertions.assertThat(alunoSalvo).isNotNull();
        Assertions.assertThat(alunoSalvo.getId()).isGreaterThan(0);
    }

    @Test
    void deveRetornarListaDeAlunos() {

        //Arrange
        Aluno aluno1 = new Aluno(
                1L,
                "Patricia",
                "patricia@gmail.com",
                "Senha@123",
                LocalDate.of(1992, 10, 26),
                8
        );
        Aluno aluno2 = new Aluno(
                2L,
                "Gabrielly",
                "gabi@gmail.com",
                "Senha@456",
                LocalDate.of(1998, 3, 15),
                9
        );

        Mockito.when(repository.findAll()).thenReturn(List.of(aluno1, aluno2));

        //Act
        List<AlunoResponse> listaAlunos = service.listarAlunos();

        //Assert
        Assertions.assertThat(listaAlunos).hasSize(2);
    }
}
