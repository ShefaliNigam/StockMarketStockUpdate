package stockMarketChartingApp.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import stockMarketChartingApp.Entity.StockEntity;
import stockMarketChartingApp.Repository.StockRepository;
import stockMarketChartingApp.exception.ResourceNotFoundException;

@Service
@Validated  // Enables validation in the service layer
public class StockService {
	 private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    @Autowired
    private StockRepository stockRepository;

    
    public List<StockEntity> addStock(@Valid List<StockEntity> stockEntity) {
    	
    	List<StockEntity> stocks = new ArrayList<>();
    	for(StockEntity s: stockEntity) {
       	 logger.info("Adding a new stock: {} with symbol: {}", s.getName(), s.getSymbol());
    		StockEntity stock = stockRepository.save(s);
    		stocks.add(stock);
    	}
        return stocks;
    }

    
    public StockEntity updateStock(@NotNull @Min(1)Long id, @Valid StockEntity stockEntity) {
    	logger.info("Updating stock with id: {}", id);
        // Check if the stock entity exists
        if (!stockRepository.existsById(id)) {
        	logger.error("Stock with id {} not found", id);
            throw new ResourceNotFoundException("Stock not found with id: " + id);
        }
        // Set the ID to ensure it's updated properly
        stockEntity.setId(id);
        return stockRepository.save(stockEntity);
    }

   
    public void deleteStock(@NotNull @Min(1)Long id) {
    	 logger.info("Deleting stock with id: {}", id);
        // Check if the stock entity exists
        if (!stockRepository.existsById(id)) {
        	logger.error("Stock with id {} not found", id);
            throw new ResourceNotFoundException("Stock not found with id: " + id);
        }
        stockRepository.deleteById(id);
    }

    
    public StockEntity getStock(@NotNull @Min(1)Long id) {
    	logger.info("Fetching stock with id: {}", id);
        return stockRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found with id: " + id));
    }
    
    public List<StockEntity> getAllStocks(){
    	return stockRepository.findAll();
    }
}
