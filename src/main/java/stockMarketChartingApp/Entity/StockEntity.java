package stockMarketChartingApp.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
@Entity
@Table(name="stocks")
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotBlank(message = "Exchange cannot be blank")
    private String exchange;

    @NotBlank(message = "Symbol cannot be blank")
    private String symbol;
    
    private int quantity;
    
    private Integer userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public StockEntity(Long id, @NotBlank(message = "Name cannot be blank") String name,
			@NotNull(message = "Price cannot be null") @Positive(message = "Price must be positive") Double price,
			@NotBlank(message = "Exchange cannot be blank") String exchange,
			@NotBlank(message = "Symbol cannot be blank") String symbol, int quantity, Integer userId) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.exchange = exchange;
		this.symbol = symbol;
		this.quantity = quantity;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "StockEntity [id=" + id + ", name=" + name + ", price=" + price + ", exchange=" + exchange + ", symbol="
				+ symbol + ", quantity=" + quantity + ", userId=" + userId + "]";
	}

	public StockEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
 
   
}