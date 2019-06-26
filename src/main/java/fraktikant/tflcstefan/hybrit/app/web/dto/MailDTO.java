package fraktikant.tflcstefan.hybrit.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {

    private String recipient;
    private String content;
    private String subject;

}
