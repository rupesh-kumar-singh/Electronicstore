package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.*;
import com.lcwd.electronic.store.services.FileService;
import com.lcwd.electronic.store.services.ProductService;
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
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("products")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
   private ProductService productService;
    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagepath;


    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDto){

        ProductDto createproduct = productService.createproduct(productDto);
        return new ResponseEntity<>(createproduct, HttpStatus.CREATED);

    }
    @PutMapping("/{productid}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto productDto,@PathVariable("productid") String productid){
        ProductDto updateproduct = productService.updateproduct(productDto, productid);
        return new ResponseEntity<>(updateproduct,HttpStatus.OK);

    }
    @DeleteMapping("/{productid}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiresponceMessage> delete(@PathVariable("productid") String productid) throws IOException {
        productService.deleteproduct(productid);
        ApiresponceMessage build = ApiresponceMessage.builder().success(true).status(HttpStatus.OK).massage("deleted successfully").build();

        return new ResponseEntity<>(build,HttpStatus.OK);
    }

    @GetMapping("/{productid}")

    public ResponseEntity<ProductDto> getsingle(@PathVariable("productid") String productid){
        ProductDto getsingleproduct = productService.getsingleproduct(productid);

        return new ResponseEntity<>(getsingleproduct,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponce<ProductDto>> getall(@RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,

                                                               @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
                                                               @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,

                                                               @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){

        PageableResponce<ProductDto> getall = productService.getall(pagenumber, pagesize, sortBy, sortDir);
        return new ResponseEntity<>(getall,HttpStatus.OK);

    }
    @GetMapping("/live")
    public ResponseEntity<PageableResponce<ProductDto>> getalllive(@RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,

                                                               @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
                                                               @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,

                                                               @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){

        PageableResponce<ProductDto> getall = productService.getAllive(pagenumber,pagesize,sortBy,sortDir);
        return new ResponseEntity<>(getall,HttpStatus.OK);

    }

    @GetMapping("/search/{subtitle}")
    public ResponseEntity<PageableResponce<ProductDto>> searchbytitle( @PathVariable("subtitle") String subtitle ,@RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,

                                                               @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
                                                               @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,

                                                               @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){

        PageableResponce<ProductDto> getall = productService.searchbytitle(subtitle,pagenumber,pagesize,sortBy,sortDir);
        return new ResponseEntity<>(getall,HttpStatus.OK);

    }

    @PostMapping("/image/{productid}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImageResponce> uploadproduct(@PathVariable("productid") String productid,@RequestParam("productimage") MultipartFile image) throws IOException {

        String filename = fileService.uploadFile(image, imagepath);

        ProductDto getsingleproduct = productService.getsingleproduct(productid);
        getsingleproduct.setProductimagename(filename);
        ProductDto updateproduct = productService.updateproduct(getsingleproduct, productid);
        ImageResponce responce = ImageResponce.builder().imageName(updateproduct.getProductimagename()).massage("product image is fully uploded").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(responce,HttpStatus.CREATED);
    }

    // serving the image

    @GetMapping("image/{productid}")
    public void getcategorycoverimage(@PathVariable("productid") String productid, HttpServletResponse response) throws IOException {

        ProductDto productDto = productService.getsingleproduct(productid);
        logger.info("categoryimageupload{}",productDto.getProductimagename());
        InputStream getresources = fileService.getresources(imagepath, productDto.getProductimagename());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(getresources,response.getOutputStream());
    }

}
