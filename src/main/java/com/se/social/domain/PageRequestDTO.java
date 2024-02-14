package com.se.social.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class PageRequestDTO {
    
    private int page; // 요청받은 PageNo
    private int size; // 1페이지당 출력 row 갯수
    
    // ** 기본생성자로 초기화
    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }
    
    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
    
}
