package org.zerock.mallapi.repository;

import static org.mockito.ArgumentMatchers.isNull;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.zerock.mallapi.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {
    
    @Autowired
    private TodoRepository todoRepository;

    // @Test
    // public void test1(){
    //     log.info("----------------------------------------");
    //     log.info(todoRepository);

    // }

    // 생성 테스트
    // @Test
    // public void testInsert() {
    //     for(int i = 1; i <= 100; i++){
    //         Todo todo = Todo.builder()
    //         .title("Title..." + i)
    //         .dueDate(LocalDate.of(2023, 12, 31))
    //         .writer("user00")
    //         .build();

    //         todoRepository.save(todo);
    //     }
    // }

    // 조회 테스트
    // @Test
    // public void testRead(){

    //     // 존재하지 않는 번호로도 확인
    //     Long tno = 33L;
    //     // Long tno = 200L;
    //     java.util.Optional<Todo> result = todoRepository.findById(tno);
    //     Todo todo = result.orElseThrow();
    //     log.info(todo);
    // }

    // 수정 테스트
    // @Test
    // public void testModify(){
    //     Long tno = 33L;
    //     Optional<Todo> result = todoRepository.findById(tno);
    //     Todo todo = result.orElseThrow();
    //     todo.changeTitle("Modified 33...");
    //     todo.changeComplete(true);
    //     todo.changeDueDate(LocalDate.of(2023, 10, 10));
    //     todoRepository.save(todo);
    // }

    // 삭제 테스트
    // @Test
    // public void testDelete() {
    //     Long tno = 1L;
    //     todoRepository.deleteById(tno);
    // }

    // 페이징처리 테스트
    @Test
    public void testPaging() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("tno").descending());
        Page<Todo> result = todoRepository.findAll(pageable); 
        log.info(result.getTotalElements()); 
        result.getContent().stream().forEach(todo -> log.info(todo));

    }

}
