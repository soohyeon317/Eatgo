package kr.co.fastcampus.eatgo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    private Long id;
    private String name;
    private String address;

    public String getInformation() {
        return this.name + " in " + address;
    }
}
