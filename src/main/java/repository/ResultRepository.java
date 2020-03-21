package repository;

import vo.Rank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import static vo.Rank.*;

public class ResultRepository implements Repository {

    public void save(Map<Rank, Integer> rankCount) {
        String sql = "INSERT INTO result VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, 0);
            pstmt.setInt(2, rankCount.get(FIRST));
            pstmt.setInt(3, rankCount.get(SECOND));
            pstmt.setInt(4, rankCount.get(THIRD));
            pstmt.setInt(5, rankCount.get(FOURTH));
            pstmt.setInt(6, rankCount.get(FIFTH));
            pstmt.setInt(7, rankCount.get(FAIL));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(pstmt);
            closeConnection(conn);
        }
    }

}
