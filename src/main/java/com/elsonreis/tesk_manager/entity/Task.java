package com.elsonreis.tesk_manager.entity;

import com.elsonreis.tesk_manager.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título da tarefa é obrigatório")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String title;

    @NotBlank(message = "A descrição da tarefa é obrigatória")
    @Size(max = 1000)
    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
}
