package kosiorek.michal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopDto {

    private Long id;
    private String name;

    private CountryDto countryDto;

}
