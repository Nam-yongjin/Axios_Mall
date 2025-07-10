package org.zerock.mallapi.repository;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.zerock.mallapi.domain.Product;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {
    
    @Autowired
    ProductRepository productRepository;

    // @Test
    // public void testInsert(){
    //     for(int i = 0; i<10; i++){
    //         Product product = Product.builder()
    //         .pname("상품"+i)
    //         .price(100*i)
    //         .pdesc("상품설명"+i)
    //         .build();

    //         // 2개의 이미지파일 추가
    //         product.addImageString("IMAGE1.jpg");
    //         product.addImageString("IMAGE2.jpg");

    //         productRepository.save(product);
    //         log.info("-----------------------------------");
    //     }
    // }

    // 이미지 불러오기까지 해서 쿼리 2번 실행
    // @Transactional
    // @Test
    // public void testRead(){
    //     Long pno = 1L;

    //     Optional<Product> result = productRepository.findById(pno);
    //     Product product = result.orElseThrow();

    //     log.info(product);
    //     log.info(product.getImageList());
    // }

    // @EntityGraph로 인해 @Transational이없이도 테이블들은 조인해서 한번에 로딩한다. 
    // 아래 실행 결과를 보면 이전과 달리 조인 처리가 된 것을 볼 수 있고, imageList를 출력하기 
    // 위해서 별도의 쿼리가 실행되지 않은 것을 확인할 수 있다. 
    // @Test
    // public void testRead2(){
    //     Long pno = 1L;
    //     Optional<Product> result = productRepository.selectOne(pno);
    //     Product product = result.orElseThrow();

    //     log.info(product);
    //     log.info(product.getImageList());
    // }

    // @Commit
    // @Transactional
    // @Test
    // public void testDelete(){
    //     Long pno = 2L;
    //     productRepository.updateToDelete(pno, true);
    // }

    // @Test
    // public void testUpdate() {
    //     Long pno = 10L;

    //     Product product = productRepository.selectOne(pno).get();
    //     product.changename("10번 상품");
    //     product.changeDesc("10번 상품 설명입니다.");
    //     product.changePrice(5000);

    //     // 첨부파일 수정
    //     product.clearList();
    //     product.addImageString(UUID.randomUUID().toString()+"_"+"NEWIMAGE1.jpg");
    //     product.addImageString(UUID.randomUUID().toString()+"_"+"NEWIMAGE2.jpg");
    //     product.addImageString(UUID.randomUUID().toString()+"_"+"NEWIMAGE3.jpg");

    //     productRepository.save(product);
    // }

    // 이미지 하나만 출력
    @Test
    public void testList(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());
        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }
}
