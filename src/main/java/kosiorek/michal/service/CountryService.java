package kosiorek.michal.service;

import kosiorek.michal.dto.CountryDto;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Country;
import kosiorek.michal.repository.CountryRepository;
import kosiorek.michal.validators.CountryDtoValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryDto addCountry(CountryDto countryDto){

        if (countryDto == null) {
            throw new AppException("add country - country object is null");
        }

        if (countryDto.getName() == null) {
            throw new AppException("add country - country name is null");
        }

        CountryDtoValidator countryDtoValidator = new CountryDtoValidator();
        Map<String, String> errors = countryDtoValidator.validate(countryDto);
        if(countryDtoValidator.hasErrors()){
            throw new AppException("add country - country validation has errors " + errors.toString());
        }

        if(countryRepository.findByName(countryDto.getName()).isPresent()){
            throw new AppException("add country - country with given name already exists");
        }

        Country country = ModelMapper.fromCountryDtoToCountry(countryDto);
        return countryRepository.addOrUpdate(country)
                .map(ModelMapper::fromCountryToCountryDto)
                .orElseThrow(() -> new AppException("add country - exception while inserting country"));

    }

    public List<CountryDto> getAllCountries(){

        return countryRepository.findAll().stream()
                .map(ModelMapper::fromCountryToCountryDto)
                .collect(Collectors.toList());

    }

}
