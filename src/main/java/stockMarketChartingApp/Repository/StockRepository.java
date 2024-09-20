package stockMarketChartingApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import stockMarketChartingApp.Entity.StockEntity;

@Repository
public interface StockRepository extends JpaRepository<StockEntity,Long>{

}
