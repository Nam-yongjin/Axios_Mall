package org.zerock.mallapi.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_product")
@Getter
@ToString(exclude = "imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private int price;

    private String pdesc;

    private boolean delFlag;

    // @ElementationCollection은 기본적으로 lazy loading 방식으로 동작하기 때문에 우선은 tbl_product 테이블만 
    // 접근해서 데이터를 처리하고 첨부파일이 필요할 때 product_image_list 테이블에 접근하게 된다
    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();

    public void changePrice(int price){
        this.price = price;
    }

    public void changeDesc(String desc){
        this.pdesc = desc;
    }

    public void changename(String name){
        this.pname = name;
    }

    public void addImage(ProductImage image){
        image.setOrd(this.imageList.size());
        imageList.add(image);
    }

    public void addImageString(String fileName){
        ProductImage productImage = ProductImage.builder()
        .fileName(fileName)
        .build();
        addImage(productImage);
    }

    public void clearList(){
        this.imageList.clear();
    }

    public void changeDel(boolean delFlag){
        this.delFlag = delFlag;
    }
}
