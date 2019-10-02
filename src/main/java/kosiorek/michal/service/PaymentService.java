package kosiorek.michal.service;

import kosiorek.michal.dto.PaymentDto;
import kosiorek.michal.model.Payment;
import kosiorek.michal.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<PaymentDto> getAllPayments(){
       return paymentRepository.findAll()
               .stream()
               .map(ModelMapper::fromPaymentToPaymentDto)
               .collect(Collectors.toList());
    }

}
