package com.lcwd.electronic.store.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponce<T>{
    private List<T> content;
    private int pageNumber;
    private  int pagesize;
    private long totalelement;
    private int totalpages;
    private boolean lastpage;

}
