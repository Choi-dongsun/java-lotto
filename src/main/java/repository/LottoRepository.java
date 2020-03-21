package repository;

import domain.Lotto;
import vo.LottoNumber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LottoRepository implements Repository {

    public void save(Lotto lotto) {
        String sql = "INSERT INTO lotto VALUES (?,?,?,?,?,?,?)";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, 0);
            pstmt.setInt(2, lotto.getNumbers().get(0).getNumber());
            pstmt.setInt(3, lotto.getNumbers().get(1).getNumber());
            pstmt.setInt(4, lotto.getNumbers().get(2).getNumber());
            pstmt.setInt(5, lotto.getNumbers().get(3).getNumber());
            pstmt.setInt(6, lotto.getNumbers().get(4).getNumber());
            pstmt.setInt(7, lotto.getNumbers().get(5).getNumber());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(pstmt);
            closeConnection(conn);
        }

    }

    public void saveAll(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            save(lotto);
        }
    }

    public List<Lotto> findAll() {
        String sql = "SELECT * FROM lotto";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<Lotto> lottos = new ArrayList<>();
            while (rs.next()) {
                List<LottoNumber> lottoNumbers = new ArrayList<>();
                lottoNumbers.add(new LottoNumber(rs.getInt("one")));
                lottoNumbers.add(new LottoNumber(rs.getInt("two")));
                lottoNumbers.add(new LottoNumber(rs.getInt("three")));
                lottoNumbers.add(new LottoNumber(rs.getInt("four")));
                lottoNumbers.add(new LottoNumber(rs.getInt("five")));
                lottoNumbers.add(new LottoNumber(rs.getInt("six")));

                Lotto lotto = new Lotto(lottoNumbers);
                lottos.add(lotto);
            }

            return lottos;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(rs);
            closeConnection(pstmt);
            closeConnection(conn);
        }
        return null;
    }
}
