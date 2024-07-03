package courseproject.library.dto;

import courseproject.library.entity.Order;
import courseproject.library.entity.Role;
import courseproject.library.entity.ShoppingCart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private String address;
    private Collection<Role> roles;
    private ShoppingCart cart;
    private List<Order> orders;
}
