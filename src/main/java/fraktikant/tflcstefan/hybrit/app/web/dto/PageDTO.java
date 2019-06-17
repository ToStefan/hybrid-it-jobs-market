package fraktikant.tflcstefan.hybrit.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@Getter
@Setter
@AllArgsConstructor
public class PageDTO {

    private int page = 0;
    private int elementsCount = 3;

    public PageRequest toPageRequest(){
        return  PageRequest.of(this.page, this.elementsCount);
    }
}