package com.dcu.demo.Product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FileService fileService;


     List<ProductDTO> productFindAll() {
         return productRepository.findAll().stream().map(product -> {
             ProductDTO productDTO = new ProductDTO();
             productDTO.setId(product.getId());
             productDTO.setImage(product.getImage());
             productDTO.setTitle(product.getTitle());
             productDTO.setPrice(product.getPrice());
             return productDTO;
         }).collect(Collectors.toList());

    }

    void productSave(ProductCreateDto productCreateDto, String imagePath){
         Product product = new Product();
         product.setImage(imagePath);
         product.setTitle(productCreateDto.getTitle());
         product.setPrice(productCreateDto.getPrice());
         product.setCompany(productCreateDto.getCompany());
         product.setManufactureDate(productCreateDto.getManufactureDate());

         productRepository.save(product);
    }


    //상품 등록 메서드
    void productSave(Product product) {
        productRepository.save(product);
    }

    //특정 ID 상태 조회
    Optional<ProductDTO> productFindById(Long id) {
        return productRepository.findById(id).map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setImage(product.getImage());
            productDTO.setTitle(product.getTitle());
            productDTO.setPrice(product.getPrice());
            productDTO.setCompany(product.getCompany());
            productDTO.setManufactureDate(product.getManufactureDate());
            productDTO.setCreateAt(product.getCreatedAt());
            return productDTO;
        });
    }

    //특정 ID 상품 삭제
    void productDelete(ProductDeleteDTO productDeleteDTO)
    { Product product = productRepository.findById(productDeleteDTO.getId())
                    .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."));
        String imagePath = product.getImage();
        if (imagePath != null && imagePath.startsWith("/upload/images")) {
            String fileName = Paths.get(imagePath).getFileName().toString();
            try {
                fileService.fileDelete(fileName);
            }catch (IOException e){
                throw new RuntimeException("이미지 삭제 중 오류 발생",e);
            }
        }
         productRepository.deleteById(product.getId());
    }

    void productUpdate(ProductUpdateDTO productUpdateDTO){
         Product product = productRepository.findById(productUpdateDTO.getId())
                 .orElseThrow(()-> new NoSuchElementException("상품이 존재하지 않습니다."));

         String imagePath = productUpdateDTO.getOriginalImage();
         if(productUpdateDTO.getImage() != null && !productUpdateDTO.getImage().isEmpty()){
             try{
                 fileService.fileDelete(String.valueOf(Paths.get(productUpdateDTO.getOriginalImage()).getFileName()));
                 imagePath = fileService.imageSave(productUpdateDTO.getImage());

             }catch (IOException e){
                 throw new RuntimeException("이미지 처리 중 오류 발생",e);
             }
         }

         product.setImage(imagePath);
         product.setTitle(productUpdateDTO.getTitle());
         product.setPrice(productUpdateDTO.getPrice());
         product.setCompany(productUpdateDTO.getCompany());
         product.setManufactureDate(productUpdateDTO.getManufactureDate());

         productRepository.save(product);
    }

    List<ProductDTO> productSearch(String keyword){
         List<Product> products;
         if (keyword!=null && !keyword.trim().isEmpty()){
             products = productRepository.findByTitleContainingIgnoreCase(keyword);
         }else {
             products = productRepository.findAll();
         }
         return  products.stream().map(product -> {
             ProductDTO productDTO = new ProductDTO();
             productDTO.setId(product.getId());
             productDTO.setImage(product.getImage());
             productDTO.setTitle(product.getTitle());
             productDTO.setPrice(product.getPrice());
             return productDTO;
         }).collect(Collectors.toList());
    }
}
