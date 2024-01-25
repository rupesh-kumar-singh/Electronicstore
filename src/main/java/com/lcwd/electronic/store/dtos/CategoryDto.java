package com.lcwd.electronic.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String categoryId;
     @NotBlank
     @Size(min = 4,message = "minimum 4 carector required")
    private String title;
      @NotBlank(message = "Description required")
    private  String description;

       @NotBlank(message = "cover the image")
    private String coverImage;
}
