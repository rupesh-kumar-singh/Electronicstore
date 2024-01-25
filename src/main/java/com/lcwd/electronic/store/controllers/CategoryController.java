package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.*;
import com.lcwd.electronic.store.repositories.CategoryReposetory;
import com.lcwd.electronic.store.services.CategoryService;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.ProductService;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Value("${category.profile.image.path}")
    private String imageuploadpath;
    @Autowired
    private FileService fileService;
    //create
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createcategory(@Valid @RequestBody CategoryDto categoryDto) {

        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryid}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("categoryid") String categoryid) {

        CategoryDto update = categoryService.update(categoryDto, categoryid);

        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryid}")
    public ResponseEntity<ApiresponceMessage> deletecategory(@PathVariable("categoryid") String categoryid) throws IOException {

        categoryService.delete(categoryid);
        ApiresponceMessage build = ApiresponceMessage.builder().success(true).status(HttpStatus.OK).massage("deleted succesfuly !!").build();
        return new ResponseEntity<>(build, HttpStatus.OK);

    }
    @GetMapping
    public ResponseEntity<PageableResponce<CategoryDto>> getallcategorydata(@RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,

                                                                            @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
                                                                            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponce<CategoryDto> getall = categoryService.getall(pagenumber, pagesize, sortBy, sortDir);
        return new ResponseEntity<>(getall, HttpStatus.OK);
    }

    @GetMapping("/{categoryid}")
    public ResponseEntity<CategoryDto> getsingle(@PathVariable("categoryid") String categoryid) {

        CategoryDto getsingle = categoryService.getcategoryByid(categoryid);
        return new ResponseEntity<>(getsingle, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public  ResponseEntity<List<CategoryDto>> getbyseach(@PathVariable("keyword") String keyword){
        List<CategoryDto> searchby = categoryService.searchby(keyword);
        return new ResponseEntity<>(searchby,HttpStatus.OK);
    }

    // upload category image
    @PostMapping("coverimage/{categoryid}")
    public ResponseEntity<ImageResponce> uploadcoverimage(@PathVariable("categoryid") String categoryid, @RequestParam("coverimage")MultipartFile coverimage) throws IOException {
        String uploadimage = fileService.uploadFile(coverimage, imageuploadpath);
        CategoryDto categoryDto = categoryService.getcategoryByid(categoryid);
        categoryDto.setCoverImage(uploadimage);
        categoryService.update(categoryDto,categoryid);
        ImageResponce uploadSuccesfullly = ImageResponce.builder().imageName(uploadimage).massage("upload succesfullly").status(HttpStatus.CREATED).success(true).build();
        return new ResponseEntity<>(uploadSuccesfullly,HttpStatus.OK);
    }

    @GetMapping("coverimage/{categoryid}")
    public void getcategorycoverimage(@PathVariable("categoryid") String categoryid, HttpServletResponse response) throws IOException {

        CategoryDto categoryDto = categoryService.getcategoryByid(categoryid);
        logger.info("categoryimageupload{}",categoryDto.getCoverImage());
        InputStream getresources = fileService.getresources(imageuploadpath, categoryDto.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(getresources,response.getOutputStream());
    }

    @PostMapping("/{categoryid}/product")
    public ResponseEntity<ProductDto> createproductwithcategories(@PathVariable("categoryid") String categoryid,@RequestBody ProductDto productDto){
        ProductDto productDto1 = productService.createwithCategory(productDto, categoryid);

        return new ResponseEntity<>(productDto1,HttpStatus.CREATED);
    }

    // uppdate category off product

    @PutMapping("/{categoryid}/product/{productid}")
    public ResponseEntity<ProductDto> updatecategoruofproduct(@PathVariable("productid") String productid,@PathVariable("categoryid") String categoryid){
        ProductDto productDto = productService.updateCategory( productid,categoryid);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    @GetMapping("/{categoryid}/product")
    public  ResponseEntity<PageableResponce<ProductDto>> gettallproudctofsinglecategory(@PathVariable("categoryid") String categoryid,
                                                                                        @RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,

                                                                                        @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
                                                                                        @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                                        @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

                                                                                         ){
        PageableResponce<ProductDto> responce = productService.gettingalldatafromsamecateogry(categoryid, pagenumber, pagesize, sortBy, sortDir);

        return new ResponseEntity<>(responce,HttpStatus.OK);
    }
}

