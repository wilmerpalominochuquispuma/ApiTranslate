package pe.edu.vallegrande.apitraslate.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("translations")
public class Translation {
    @Id
    private Long id;
    private String originalText;
    private String translatedText;
    private String fromLanguage;
    private String toLanguage;
}
