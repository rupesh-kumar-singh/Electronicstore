package com.lcwd.electronic.store.dtos;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponce {
    private String jwtToken;
    private UserDto user;
}
