package stockMarketChartingApp.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import stockMarketChartingApp.Entity.StockEntity;
import stockMarketChartingApp.Repository.StockRepository;
import stockMarketChartingApp.exception.ResourceNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockServiceTest {

    @InjectMocks
    private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    private StockEntity stockEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stockEntity = new StockEntity();
        stockEntity.setId(1L);
        stockEntity.setName("Tesla");
        stockEntity.setPrice(750.0);
    }

//    @Test
//    public void testAddStock() {
//        when(stockRepository.save(stockEntity)).thenReturn(stockEntity);
//
////        StockEntity createdStock = stockService.addStock(Arrays.asstockEntity);
//        assertNotNull(createdStock);
//        assertEquals(stockEntity.getId(), createdStock.getId());
//        assertEquals(stockEntity.getName(), createdStock.getName());
//
//        verify(stockRepository, times(1)).save(stockEntity);
//    }

    @Test
    public void testUpdateStock() {
        when(stockRepository.existsById(1L)).thenReturn(true);
        when(stockRepository.save(stockEntity)).thenReturn(stockEntity);

        StockEntity updatedStock = stockService.updateStock(1L, stockEntity);
        assertNotNull(updatedStock);
        assertEquals(stockEntity.getId(), updatedStock.getId());
        assertEquals(stockEntity.getName(), updatedStock.getName());

        verify(stockRepository, times(1)).existsById(1L);
        verify(stockRepository, times(1)).save(stockEntity);
    }

    @Test
    public void testUpdateStock_StockNotFound() {
        when(stockRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            stockService.updateStock(1L, stockEntity);
        });

        String expectedMessage = "Stock not found with id: 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(stockRepository, times(1)).existsById(1L);
        verify(stockRepository, times(0)).save(stockEntity);
    }

    @Test
    public void testDeleteStock() {
        when(stockRepository.existsById(1L)).thenReturn(true);
        doNothing().when(stockRepository).deleteById(1L);

        stockService.deleteStock(1L);

        verify(stockRepository, times(1)).existsById(1L);
        verify(stockRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteStock_StockNotFound() {
        when(stockRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            stockService.deleteStock(1L);
        });

        String expectedMessage = "Stock not found with id: 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(stockRepository, times(1)).existsById(1L);
        verify(stockRepository, times(0)).deleteById(1L);
    }

    @Test
    public void testGetStock() {
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stockEntity));

        StockEntity foundStock = stockService.getStock(1L);
        assertNotNull(foundStock);
        assertEquals(stockEntity.getId(), foundStock.getId());
        assertEquals(stockEntity.getName(), foundStock.getName());

        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetStock_StockNotFound() {
        when(stockRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            stockService.getStock(1L);
        });

        String expectedMessage = "Stock not found with id: 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(stockRepository, times(1)).findById(1L);
    }
}
