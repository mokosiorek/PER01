package kosiorek.michal.service;

import kosiorek.michal.dto.ErrorDto;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Error;
import kosiorek.michal.repository.ErrorRepository;
import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;

@RequiredArgsConstructor
public class ErrorService {

    private final ErrorRepository errorRepository;

    public ErrorDto addError(ErrorDto errorDto) {

        if (errorDto == null) {
            throw new AppException("add error - error object null");
        }

        if (errorDto.getDate() == null) {
            throw new AppException("add error - date null");
        }

        if (errorDto.getMessage() == null) {
            throw new AppException("add error - message is null");
        }

        Error error = ModelMapper.fromErrorDtoToError(errorDto);
        return errorRepository.addOrUpdate(error)
                .map(ModelMapper::fromErrorToErrorDto)
                .orElseThrow(() -> new AppException("add error - error while inserting"));

    }

}
