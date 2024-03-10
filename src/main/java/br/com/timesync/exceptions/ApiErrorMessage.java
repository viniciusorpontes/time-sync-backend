package br.com.timesync.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiErrorMessage {

    private List<String> errors;

    public ApiErrorMessage(String mensagem) {
        this.errors = Collections.singletonList(mensagem);
    }

}