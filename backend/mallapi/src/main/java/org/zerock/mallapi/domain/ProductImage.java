package org.zerock.mallapi.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 파일 업로드의 특징은 파일 자체가 부수적인 요소라는 점이다. 
// 예를 들어 상품 등록의 경우 핵심은 상품 자체이고 파일들은 이를 설명하는 부수적인 데이터이다. 
// 이럴 때 상품은 고유한 pk를 가지는 하나의 온전한 엔티티로 봐야 하고 파일들은 엔터티에 속해 있는 데이터로 봐야 한다.
// JPA에서는 ‘값 타입 객체’라는 표현을 쓰는데 컬렉션으로 처리할 때는 @ElementCollection
// 을 활용한다. ‘값 타입 객체’는 엔티티와 달리 PK가 없는 데이터이다. 
// 예제에서 하나의 상품 데이터는 여러 개의 상품 이미지를 갖도록 구성된다

// ProductImage 클래스는 @Embeddable을 이용해서 해당 클래스의 인스턴스가 ‘값 타입 객체＇임을 명시한다.
@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    
    private String fileName;

    // ord 필드는 하나의 상품에 여러 이미지를 갖을 때 각 이미지마다 번호를 지정하고, 
    // 번호가 0일 경우만 대표 이미지로 지정하고 상품 목록을 출력할 때 번호 0만 보이게 하기 위함이다
    private int ord;

    public void setOrd(int ord){
        this.ord = ord;
    }
}
