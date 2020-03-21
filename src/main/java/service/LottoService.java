package service;

import domain.Lotto;
import domain.Money;
import domain.Purchase;
import domain.WinningLotto;
import dto.LottoDto;
import dto.LottosResult;
import generator.AutoLottoGenerator;
import generator.IntegratedLottoGenerator;
import generator.LottoGenerator;
import generator.ManualLottoGenerator;
import repository.LottoRepository;
import repository.ResultRepository;
import vo.Rank;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.*;
import static util.ParseUtil.parseArray;

public class LottoService {
    private Money money; // DB 귀찮아서 패스
    private final LottoRepository lottoRepository;
    private final ResultRepository resultRepository;

    public LottoService(LottoRepository lottoRepository, ResultRepository resultRepository) {
        this.lottoRepository = lottoRepository;
        this.resultRepository = resultRepository;
    }

    public List<LottoDto> buyLotto(String inputMoney, String manualNumbers) {
        money = new Money(parseInt(inputMoney));
        List<String> manualLotto = Arrays.asList(parseArray(manualNumbers));

        Purchase purchase = new Purchase(money, manualLotto.size());

        LottoGenerator manualLottoGenerator = new ManualLottoGenerator(manualLotto);
        LottoGenerator autoLottoGenerator = new AutoLottoGenerator(purchase.getAutoSize());
        IntegratedLottoGenerator integratedLottoGenerator =
                new IntegratedLottoGenerator(manualLottoGenerator.generateLotto(), autoLottoGenerator.generateLotto());

        lottoRepository.saveAll(integratedLottoGenerator.getLottos());

        return integratedLottoGenerator.toDto().getLottos();
    }

    public LottosResult matchLotto(String winningNumber, String bonusNumber) {
        WinningLotto winningLotto = LottoGenerator.generateWinningLotto(winningNumber, parseInt(bonusNumber));
        List<Lotto> lottos = lottoRepository.findAll();

        Map<Rank, Integer> rankCount = winningLotto.calculateWinningCount(
                lottos.stream()
                        .map(i -> new Lotto(i.getNumbers()))
                        .collect(Collectors.toList()));
        resultRepository.save(rankCount);

        double totalRateOfReturn = money.calculateTotalRateOfReturn(rankCount);

        return new LottosResult(rankCount, totalRateOfReturn);
    }
}
