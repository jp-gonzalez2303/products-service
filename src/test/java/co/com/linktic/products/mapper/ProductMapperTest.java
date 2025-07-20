package co.com.linktic.products.mapper;


import co.com.linktic.products.entity.ProductEntity;
import co.com.linktic.products.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    private ProductMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = ProductMapper.INSTANCE;
    }

    @Test
    void shouldMapEntityToDto() {
        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        entity.setName("Test");
        entity.setDescription("desc");
        entity.setPrice(100.0);

        Product dto = mapper.toDTO(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNombre()).isEqualTo("Test");
        assertThat(dto.getDescripcion()).isEqualTo("desc");
        assertThat(dto.getPrecio()).isEqualTo(100.0);
    }

    @Test
    void shouldMapDtoToEntity() {
        Product dto = new Product();
        dto.setId(2L);
        dto.setNombre("DTO");
        dto.setDescripcion("descripcion");
        dto.setPrecio(200.0);

        ProductEntity entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(2L);
        assertThat(entity.getName()).isEqualTo("DTO");
        assertThat(entity.getDescription()).isEqualTo("descripcion");
        assertThat(entity.getPrice()).isEqualTo(200.0);
    }

    @Test
    void shouldReturnNullIfDtoIsNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void shouldReturnNullIfEntityIsNull() {
        assertThat(mapper.toDTO(null)).isNull();
    }
}
