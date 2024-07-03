package courseproject.library.dto;

import courseproject.library.entity.Product;
import courseproject.library.entity.ShoppingCart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Long id;
    private ShoppingCartDto cart;
    private ProductDto product;
    private int quantity;
    private double unitPrice;
}
