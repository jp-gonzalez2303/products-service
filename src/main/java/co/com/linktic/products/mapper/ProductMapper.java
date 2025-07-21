package co.com.linktic.products.mapper;

import co.com.linktic.products.entity.ProductEntity;
import co.com.linktic.products.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "price", target = "precio")
    @Mapping(source = "description", target = "descripcion")
    Product toDTO(ProductEntity product);

    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "precio", target = "price")
    @Mapping(source = "descripcion", target = "description")
    ProductEntity toEntity(Product productoDTO);
}