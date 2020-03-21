package repository;

import org.junit.Before;
import org.junit.Test;
import vo.Rank;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static vo.Rank.*;

public class ResultRepositoryTest {

    private ResultRepository resultRepository;

    @Before
    public void setUp() throws SQLException {
        resultRepository = new ResultRepository();

    }

    @Test
    public void DB연결() {
        Connection conn = resultRepository.getConnection();

        assertThat(conn).isNotNull();
    }

    @Test
    public void 결과저장() {
        Map<Rank, Integer> rankCount = new HashMap<>();
        rankCount.put(FIRST, 1);
        rankCount.put(SECOND, 3);
        rankCount.put(THIRD, 5);
        rankCount.put(FOURTH, 7);
        rankCount.put(FIFTH, 9);
        rankCount.put(FAIL, 11);

        resultRepository.save(rankCount);
    }
}