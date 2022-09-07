package com.eldorado.eldoradosercice_oms.repository.repo;

import com.eldorado.eldoradosercice_oms.repository.entities.Execution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface ExecutionReportsRepository extends JpaRepository<Execution, UUID> {

  @Query(
      nativeQuery = true,
      value =
          "SELECT SUM(s.buy) - SUM(s.sell) quantity\n"
              + "FROM (SELECT erb.target_comp_id, erb.cum_qty buy, 0 sell\n"
              + "      FROM execution_reports erb\n"
              + "      WHERE erb.target_comp_id = :compId\n"
              + "        AND erb.side = '1'\n"
              + "        AND erb.ord_status = '2'\n"
              + "        AND erb.symbol = :symbol\n"
              + "      UNION ALL\n"
              + "      SELECT ers.target_comp_id, 0 buy, ers.cum_qty sell\n"
              + "      FROM execution_reports ers\n"
              + "      WHERE ers.target_comp_id = :compId\n"
              + "        AND ers.side = '2'\n"
              + "        AND ers.ord_status = '2'\n"
              + "        AND ers.symbol = :symbol) s\n"
              + "GROUP BY target_comp_id;")
  BigDecimal getClientPosition(@Param("compId") String compId, @Param("symbol") String symbol);
}
