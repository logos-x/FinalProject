package courseproject.library.dto;

import courseproject.library.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String image;
    private List<Role> roles;
}
