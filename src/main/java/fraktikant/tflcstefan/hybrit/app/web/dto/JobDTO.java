package fraktikant.tflcstefan.hybrit.app.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobDTO {

    private String id;
    private String type;
    private String url;
    private String created_at;
    private String company;
    private String company_url;
    private String location;
    private String title;
    private String description;
    private String how_to_apply;
    private String company_logo;


    public String toTableRow(){
        return "<tr>" +
                    "<td>"+ this.getTitle() + "</td>" +
                    "<td>"+ this.getType() + "</td>" +
                    "<td>"+ this.getCompany() + "</td>" +
                    "<td>"+ this.getLocation() + "</td>" +
                    "<td>"+ this.getHow_to_apply() + "</td>" +
                "</tr>";
    }

}
