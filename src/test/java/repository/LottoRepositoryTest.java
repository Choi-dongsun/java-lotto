package repository;

import domain.Lotto;
import org.junit.Before;
import org.junit.Test;
import vo.LottoNumber;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LottoRepositoryTest {

    private LottoRepository lottoRepository;

    @Before
    public void setUp() throws SQLException {
        lottoRepository = new LottoRepository();

    }

    @Test
    public void DB연결() {
        Connection conn = lottoRepository.getConnection();

        assertThat(conn).isNotNull();
    }

    @Test
    public void 로또단수저장() {
        Lotto lotto = new Lotto(Arrays.asList(
                new LottoNumber(1),
                new LottoNumber(2),
                new LottoNumber(3),
                new LottoNumber(4),
                new LottoNumber(5),
                new LottoNumber(6)
        ));

        lottoRepository.save(lotto);
    }

    @Test
    public void 로또뭉치저장() {
        Lotto lotto1 = new Lotto(Arrays.asList(
                new LottoNumber(1),
                new LottoNumber(2),
                new LottoNumber(3),
                new LottoNumber(4),
                new LottoNumber(5),
                new LottoNumber(6)
        ));
        Lotto lotto2 = new Lotto(Arrays.asList(
                new LottoNumber(2),
                new LottoNumber(3),
                new LottoNumber(4),
                new LottoNumber(5),
                new LottoNumber(6),
                new LottoNumber(7)
        ));
        List<Lotto> lottos = Arrays.asList(lotto1, lotto2);

        lottoRepository.saveAll(lottos);
    }

    @Test
    public void 로또전부가져오기() {
        List<Lotto> all = lottoRepository.findAll();

        for (Lotto lotto : all) {
            System.out.println(lotto);
        }
    }
}