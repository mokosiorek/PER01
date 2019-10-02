package kosiorek.michal.service;

import kosiorek.michal.dto.CountryDto;
import kosiorek.michal.dto.CustomerDto;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Country;
import kosiorek.michal.model.Customer;
import kosiorek.michal.repository.CountryRepository;
import kosiorek.michal.repository.CustomerRepository;
import kosiorek.michal.validators.CountryDtoValidator;
import kosiorek.michal.validators.CustomerDtoValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;

    public CustomerDto addCustomer(CustomerDto customerDto) {

        if (customerDto == null) {
            throw new AppException("add customer - customer object is null");
        }

        if (customerDto.getName() == null) {
            throw new AppException("add customer - customer name is null");
        }

        CountryDto countryDto = customerDto.getCountryDto();

        if (countryDto == null) {
            throw new AppException("add customer - country object is null");
        }

        if (countryDto.getName() == null) {
            throw new AppException("add customer - country name is null");
        }

        CustomerDtoValidator customerDtoValidator = new CustomerDtoValidator();
        Map<String, String> errors = customerDtoValidator.validate(customerDto);
        if (customerDtoValidator.hasErrors()) {
            throw new AppException("add customer - customer validation not correct " + errors.toString());
        }


        if (customerRepository.findCustomerByNameSurnameAndCountryName(customerDto.getName(), customerDto.getSurname(), countryDto.getName()).isPresent()) {
            throw new AppException("add customer - customer with given name and surname already exists for given country name");
        }


        Country country = countryRepository
                .findByName(countryDto.getName())
                .orElseThrow(() -> new AppException("add customer - no country with name " + countryDto.getName()));

        Customer customer = ModelMapper.fromCustomerDtoToCustomer(customerDto);
        customer.setCountry(country);
        return customerRepository
                .addOrUpdate(customer)
                .map(ModelMapper::fromCustomerToCustomerDto)
                .orElseThrow(() -> new AppException("add customer - exception while inserting customer"));
    }

    public List<CustomerDto> getAllFromCountry(String countryName) {

        if (countryName == null) {
            throw new AppException("find all customers from country - country name is null");
        }

        if (!countryRepository.findByName(countryName).isPresent()) {
            throw new AppException("find all customers from country - no country with given name");
        }

        return customerRepository
                .findAll()
                .stream()
                .filter(customer -> customer.getCountry().getName().equals(countryName))
                .map(ModelMapper::fromCustomerToCustomerDto)
                .collect(Collectors.toList());

    }

    public List<CustomerDto> getAllFromCountry2(String countryName) {

        if (countryName == null) {
            throw new AppException("find all customers from country - country name is null");
        }

        return customerRepository
                .findAllFromCountry(countryName)
                .stream()
                .map(ModelMapper::fromCustomerToCustomerDto)
                .collect(Collectors.toList());

    }

    public List<CustomerDto> getCustomersWhoOrderedAtLeastOneProductFromCountryOfOrigin() {

        return customerRepository.findCustomersWhoOrderedAtLeastOneProductFromCountryOfOrigin().stream()
                .map(ModelMapper::fromCustomerToCustomerDto)
                .collect(Collectors.toList());

    }

    public List<CustomerDto> getAllCustomers(){
       return customerRepository.findAll().stream()
                .map(ModelMapper::fromCustomerToCustomerDto)
                .collect(Collectors.toList());
    }

}
