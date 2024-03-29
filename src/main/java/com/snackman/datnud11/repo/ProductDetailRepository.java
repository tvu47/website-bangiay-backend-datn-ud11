package com.snackman.datnud11.repo;

import com.snackman.datnud11.entity.Images;
import com.snackman.datnud11.entity.ProductDetail;
import com.snackman.datnud11.responses.Count;
import com.snackman.datnud11.responses.ProductDetailThongKeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    @Query(value = "select * from product_detail where product_id = :id", nativeQuery = true)
    List<ProductDetail> findByProductId(@Param("id") Long id);

    @Query(value = "select * from product_detail where sku = :sku", nativeQuery = true)
    ProductDetail findBySku(@Param("sku") String sku);

    @Query(value = "select * from product_detail where product_id in (:ids)", nativeQuery = true)
    List<ProductDetail> getProductDetailByListIdProduct(@Param(value = "ids") List<Long> productList);
    @Query(value = "SELECT new com.snackman.datnud11.responses.ProductDetailThongKeResponse(SUM(b.quatity), SUM(b.price)) FROM ProductDetail b WHERE b.productId in (:ids)")
    ProductDetailThongKeResponse tinhThongKeProduct(@Param(value = "ids") List<Long> productList);

    @Query(value = "select * from product_detail where product_id = :productId and color =:colorId LIMIT 1", nativeQuery = true)
    ProductDetail findProductDetailByProductIdTop1(@Param(value = "productId") Long productId,
                                               @Param(value = "colorId") Long colorId);

}