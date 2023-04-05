package com.snackman.datnud11.dto;

import com.snackman.datnud11.entity.Colors;
import com.snackman.datnud11.entity.Size;
import lombok.Data;

import java.util.List;

@Data
public class PaymentDTO {

    private Voucherzz voucher;
    private Info info;
    private List<ProductOrder> productsOrder;

    @Data
    public static class Voucherzz{
        private Long id;
        private String code;
        private Integer value;
        private Integer quantity;
    }

    @Data
    public static class Info{
        private Long customerId;
        private String fullName;
        private String email;
        private String phone;
        private String address;
    }

    @Data
    public static class ProductOrder{
        private Long id;
        private String productName;
        private Double price;
        private Integer quantity;
        private Colors color;
        private Size size;

        public String getSku(){
            return "P" + this.id + "C" + this.color.getId() + "S" + this.size.getId();
        }
    }
}
