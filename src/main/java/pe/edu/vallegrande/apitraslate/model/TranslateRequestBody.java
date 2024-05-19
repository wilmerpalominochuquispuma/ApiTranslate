package pe.edu.vallegrande.apitraslate.model;

import lombok.Data;

@Data
public class TranslateRequestBody {
    private String text;
    private String from;
    private String to;
}
