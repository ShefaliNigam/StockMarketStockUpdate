package stockMarketChartingApp.Controller;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import stockMarketChartingApp.Entity.StockEntity;
import stockMarketChartingApp.Service.StockService;

@WebMvcTest(StockController.class)
public class StockControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StockService stockService;

	private StockEntity stockEntity1;
	private StockEntity stockEntity2;

	@BeforeEach
	void setUp() {
		stockEntity1 = new StockEntity(1L, "Stock1", 100.5, "NYSE", "S1", 10, 101);
		stockEntity2 = new StockEntity(2L, "Stock2", 150.75, "NASDAQ", "S2", 20, 102);
	}

	@Test
	void addStockTest() throws Exception {
		List<StockEntity> stockList = Arrays.asList(stockEntity1, stockEntity2);

		when(stockService.addStock(any())).thenReturn(stockList);

		mockMvc.perform(post("/api/stocks/addNewStock").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(stockList))).andExpect(status().isOk());
//				.andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[0].name").value("Stock1"))
//				.andExpect(jsonPath("$[1].name").value("Stock2"));
	}

	private @Valid List<StockEntity> any() {
		// TODO Auto-generated method stub
		return null;
	}

	@Test
    void updateStockTest() throws Exception {
        when(stockService.updateStock(anyLong(), (@Valid StockEntity) any())).thenReturn(stockEntity1);

        mockMvc.perform(put("/api/stocks/updateStockById/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(stockEntity1)))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.name").value("Stock1"))
//                .andExpect(jsonPath("$.price").value(100.5));
    }

	private @NotNull @Min(1) Long anyLong() {
		// TODO Auto-generated method stub
		return null;
	}

	@Test
	void deleteStockTest() throws Exception {
		mockMvc.perform(delete("/api/stocks/deleteStockById/1")).andExpect(status().isNoContent());
	}

	@Test
    void getStockTest() throws Exception {
        when(stockService.getStock(anyLong())).thenReturn(stockEntity1);

//        mockMvc.perform(get("/api/stocks/getStockById/1"))
//                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.name").value("Stock1"))
//                .andExpect(jsonPath("$.price").value(100.5));
    }

	@Test
	void getAllStocksTest() throws Exception {
		List<StockEntity> stockList = Arrays.asList(stockEntity1, stockEntity2);

		when(stockService.getAllStocks()).thenReturn(stockList);

		mockMvc.perform(get("/api/stocks/")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].name").value("Stock1")).andExpect(jsonPath("$[1].name").value("Stock2"));
	}
}

/*
 * @WebMvcTest(StockController.class) public class StockControllerTest {
 * 
 * @Autowired private MockMvc mockMvc;
 * 
 * @MockBean private StockService stockService;
 * 
 * @Autowired private ObjectMapper objectMapper;
 * 
 * private StockEntity stockEntity;
 * 
 * @BeforeEach void setUp() { stockEntity = new StockEntity();
 * stockEntity.setId(1L); stockEntity.setName("Tesla");
 * stockEntity.setPrice(750.0); }
 * 
 * // @Test // public void testAddStock() throws Exception { //
 * when(stockService.addStock(any(StockEntity.class))).thenReturn(stockEntity);
 * // // mockMvc.perform(post("/api/stocks/add") //
 * .contentType(MediaType.APPLICATION_JSON) //
 * .content(objectMapper.writeValueAsString(stockEntity))) //
 * .andExpect(status().isOk()) //
 * .andExpect(jsonPath("$.id").value(stockEntity.getId())) //
 * .andExpect(jsonPath("$.name").value(stockEntity.getName())) //
 * .andExpect(jsonPath("$.price").value(stockEntity.getPrice())) //
 * .andDo(print()); // // verify(stockService,
 * times(1)).addStock(any(StockEntity.class)); // }
 * 
 * @Test public void testUpdateStock() throws Exception {
 * when(stockService.updateStock(eq(1L),
 * any(StockEntity.class))).thenReturn(stockEntity);
 * 
 * mockMvc.perform(put("/api/stocks/updateById/1")
 * .contentType(MediaType.APPLICATION_JSON)
 * .content(objectMapper.writeValueAsString(stockEntity)))
 * .andExpect(status().isOk())
 * .andExpect(jsonPath("$.id").value(stockEntity.getId()))
 * .andExpect(jsonPath("$.name").value(stockEntity.getName()))
 * .andExpect(jsonPath("$.price").value(stockEntity.getPrice()))
 * .andDo(print());
 * 
 * verify(stockService, times(1)).updateStock(eq(1L), any(StockEntity.class)); }
 * 
 * @Test public void testDeleteStock() throws Exception {
 * doNothing().when(stockService).deleteStock(1L);
 * 
 * mockMvc.perform(delete("/api/stocks/deleteById/1"))
 * .andExpect(status().isNoContent()) .andDo(print());
 * 
 * verify(stockService, times(1)).deleteStock(1L); }
 * 
 * @Test public void testGetStock() throws Exception {
 * when(stockService.getStock(1L)).thenReturn(stockEntity);
 * 
 * mockMvc.perform(get("/api/stocks/getById/1")) .andExpect(status().isOk())
 * .andExpect(jsonPath("$.id").value(stockEntity.getId()))
 * .andExpect(jsonPath("$.name").value(stockEntity.getName()))
 * .andExpect(jsonPath("$.price").value(stockEntity.getPrice()))
 * .andDo(print());
 * 
 * verify(stockService, times(1)).getStock(1L); } }
 * 
 */