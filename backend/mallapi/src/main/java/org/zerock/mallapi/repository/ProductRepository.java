package org.zerock.mallapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.mallapi.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
    // @EntityGraph 애플리케이션 개발에서 가능하면 데이터베이스의 접근은 항상 많은 리소스와 
    // 시간을 잡아먹기 때문에 쿼리 실행 횟수는 가능하면 줄이는 것이 좋다. JPA에서는 쿼리를 작성할 때 
    // @EntityGraph를 이용해서 해당 속성을 조인 처리하도록 설정해 줄 수 있다. ProductRepository 내에 @Query로 메서드를 추가한다.
    @EntityGraph(attributePaths = "imageList")
    @Query("select p from Product p where p.pno = :pno")
    Optional<Product> selectOne(@Param("pno") Long pno);

    @Modifying
    @Query("update Product p set p.delFlag = :flag where p.pno = :pno")
    void updateToDelete(@Param("pno") Long pno, @Param("flag") boolean flag);

    // 이미지가 포함된 목록
    @Query( "select p, pi from Product p left join p.imageList pi where pi.ord = 0 and p.delFlag = false " )
    Page<Object[]> selectList(Pageable pageable);
}
