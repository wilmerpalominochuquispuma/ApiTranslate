package pe.edu.vallegrande.apitraslate.controller;
import pe.edu.vallegrande.apitraslate.model.TranslateRequestBody;
import pe.edu.vallegrande.apitraslate.model.Translation;
import pe.edu.vallegrande.apitraslate.repository.TranslationRepository;
import pe.edu.vallegrande.apitraslate.service.TranslatorService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class TranslatorController {

    private final TranslatorService translatorService;
    private final TranslationRepository translationRepository;

    @Autowired
    public TranslatorController(TranslatorService translatorService, TranslationRepository translationRepository) {
        this.translatorService = translatorService;
        this.translationRepository = translationRepository;
    }

    @PostMapping("/translate")
    public Mono<ResponseEntity<String>> translateText(@RequestBody TranslateRequestBody requestBody) {
        String text = requestBody.getText();
        String from = requestBody.getFrom();
        String to = requestBody.getTo();
        return translatorService.translateText(text, from, to)
                .flatMap(translatedText -> {
                    Translation translation = new Translation();
                    translation.setOriginalText(text);
                    translation.setTranslatedText(translatedText);
                    translation.setFromLanguage(from);
                    translation.setToLanguage(to);
                    return translationRepository.save(translation)
                            .map(savedTranslation -> ResponseEntity.status(HttpStatus.OK)
                                    .body("Translation saved successfully"));
                })
                .onErrorResume(error -> {
                    log.error("Error translating text: {}", error.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error translating text"));
                });
    }
}
