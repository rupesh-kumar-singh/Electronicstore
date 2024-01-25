package com.lcwd.electronic.store.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiresponceMessage {
    private  String massage;
    private boolean success;
    private HttpStatus status;

}
