package kosiorek.michal.service;

import kosiorek.michal.App;
import kosiorek.michal.dto.TradeDto;
import kosiorek.michal.exceptions.AppException;
import kosiorek.michal.model.Trade;
import kosiorek.michal.repository.TradeRepository;
import kosiorek.michal.validators.TradeDtoValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeDto addTrade(TradeDto tradeDto) {

        if (tradeDto == null) {
            throw new AppException("add trade - trade object null");
        }

        if (tradeDto.getName() == null) {
            throw new AppException("add trade - trade name object null");
        }

        TradeDtoValidator tradeDtoValidator = new TradeDtoValidator();
        Map<String, String> errors = tradeDtoValidator.validate(tradeDto);
        if (tradeDtoValidator.hasErrors()) {
            throw new AppException("add trade - trade validation not correct " + errors.toString());
        }

        if (tradeRepository.findByName(tradeDto.getName()).isPresent()) {
            throw new AppException("add trade - trade with given name already exists");
        }

        Trade trade = ModelMapper.fromTradeDtoToTrade(tradeDto);

        return tradeRepository.addOrUpdate(trade)
                .map(ModelMapper::fromTradeToTradeDto)
                .orElseThrow(() -> new AppException("add trade - error while inserting"));

    }

    public List<TradeDto> getAllTrades() {

        return tradeRepository.findAll().stream()
                .map(ModelMapper::fromTradeToTradeDto)
                .collect(Collectors.toList());

    }

    public TradeDto editTrade(TradeDto tradeDto) {

        if (tradeDto == null) {
            throw new AppException("edit trade - trade object null");
        }

        Trade trade = ModelMapper.fromTradeDtoToTrade(tradeDto);
        return tradeRepository.addOrUpdate(trade).map(ModelMapper::fromTradeToTradeDto)
                .orElseThrow(() -> new AppException("editing trade - exception while editing"));

    }


}
