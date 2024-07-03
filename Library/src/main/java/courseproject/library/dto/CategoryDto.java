package courseproject.library.dto;

import courseproject.library.entity.Brand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private boolean activated;
    private boolean deleted;
    private Brand brand;
}
