package com.elsonreis.tesk_manager.enums;

import java.util.Arrays;

public enum TaskStatus {

    TODO,
    IN_PROGRESS,
    DONE;

    public static TaskStatus fromValue(String value) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Status de tarefa é obrigatório.");
        }

        String normalizedValue = value.trim()
                .replace('-', '_')
                .replace(' ', '_')
                .toUpperCase();

        return Arrays.stream(values())
                .filter(status -> status.name().equals(normalizedValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Status inválido. Use um dos valores: TODO, IN_PROGRESS, DONE"));
    }
}
