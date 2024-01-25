package com.lcwd.electronic.store.helper;

import com.lcwd.electronic.store.dtos.PageableResponce;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Healper {
    public static<U,V> PageableResponce<V> getPeableResponce(Page<U> page, Class<V> type){
        List<U> users = page.getContent();
        List<V> collectdto = users.stream().map(object -> new ModelMapper().map(object,type)).collect(Collectors.toList());
        PageableResponce<V> responce = new PageableResponce<>();
        responce.setContent(collectdto);
        responce.setPageNumber(page.getNumber());
        responce.setPagesize(page.getSize());
        responce.setTotalpages(page.getTotalPages());
        responce.setTotalelement(page.getTotalElements());
        responce.setLastpage(page.isLast());
        return responce;
    }
}
