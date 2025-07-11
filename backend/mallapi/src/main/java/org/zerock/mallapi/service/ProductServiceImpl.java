package org.zerock.mallapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.domain.Product;
import org.zerock.mallapi.domain.ProductImage;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.ProductDTO;
import org.zerock.mallapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // 상품정보와 그상품의이미지 하나만 불러옴
    @Override
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO){
        log.info("getList..........");
        Pageable pageable = PageRequest.of(
            pageRequestDTO.getPage() -1, 
            pageRequestDTO.getSize(),
            Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        List<ProductDTO> dtoList = result.get().map(arr -> {
            Product product = (Product)arr[0];
            ProductImage productImage = (ProductImage)arr[1];
            ProductDTO productDTO = ProductDTO.builder()
            .pno(product.getPno())
            .pname(product.getPname())
            .pdesc(product.getPdesc())
            .price(product.getPrice())
            .build();

            // 상품 이미지를 등록하지 않은 것도 페이지로 불러오려면
            if(productImage != null) {
                String imageStr = productImage.getFileName();
                productDTO.setUploadFileNames(List.of(imageStr));
            }

            return productDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();
        return PageResponseDTO.<ProductDTO>withAll()
        .dtoList(dtoList)
        .pageRequestDTO(pageRequestDTO)
        .totalCount(totalCount)
        .build();
    }

    @Override
    public Long register(ProductDTO productDTO) {
        Product product = dtoToEntity(productDTO);
        Product result = productRepository.save(product);
        return result.getPno();
    }

    private Product dtoToEntity(ProductDTO productDTO) {
        Product product = Product.builder()
                .pno(productDTO.getPno())
                .pname(productDTO.getPname())
                .pdesc(productDTO.getPdesc())
                .price(productDTO.getPrice())
                .build();

        // 업로드 처리가 끝난 파일들의 이름 리스트
        List<String> uploadFileNames = productDTO.getUploadFileNames();
        if (uploadFileNames == null) {
            return product;
        }
        uploadFileNames.stream().forEach(uploadName -> {
            product.addImageString(uploadName);
        });
        return product;
    }

    @Override
    public ProductDTO get(Long pno) {
        Optional<Product> result = productRepository.selectOne(pno);
        Product product = result.orElseThrow();
        ProductDTO productDTO = entityToDTO(product);
        return productDTO;
    }

    private ProductDTO entityToDTO(Product product) {
        ProductDTO productDTO = ProductDTO.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .pdesc(product.getPdesc())
                .price(product.getPrice())
                .build();

        List<ProductImage> imageList = product.getImageList();
        if (imageList == null || imageList.size() == 0) {
            return productDTO;
        }

        List<String> fileNameList = imageList.stream().map(productImage -> productImage.getFileName()).toList();
        productDTO.setUploadFileNames(fileNameList);

        return productDTO;
    }

    // 수정 기능의 처리에서는 첨부파일의 처리를 주의해야 한다.
    // ProductDTO에서는 List<MultipartFile>타입으로 선언된 files로 존재하고,
    // List<String> 타입인 uploadFileNames가 존재하는데 uploadFileNames는 기존에 업로드된
    // 파일들의 이름을 의미하고, files는 처리가 필요한 새로운 파일들이다. 실제 DB에 추가되는 것은
    // 문자열로 된 uploadFileNames이므로 업로드 작업이 완료된 후에는 이미 업로드된 uploadFileNames에
    // 업로드된 파일의 이름들을 추가해서 구성해 줘야 한다.
    // DB와 관련된 엔티티에서는 uploadFileNames의 내용이 첨부파일의 이름들이기 때문에 기존의 Product 객체가
    // 가진 모든 파일을 지우고, ProductDTO가 가진 uploadFileNames 내용들로 새롭게 추가해서 저장하는 과정으로 처리된다.
    @Override
    public void modify(ProductDTO productDTO) {

        // read
        Optional<Product> result = productRepository.findById(productDTO.getPno());

        Product product = result.orElseThrow();

        // change pname, pdesc, price
        product.changeName(productDTO.getPname());
        product.changeDesc(productDTO.getPdesc());
        product.changePrice(productDTO.getPrice());

        // upload File - clear First
        product.clearList();
        // 업로드 처리가 끝난 파일들의 이름 리스트
        List<String> uploadFileNames = productDTO.getUploadFileNames();

        if (uploadFileNames != null && uploadFileNames.size() > 0) {
            uploadFileNames.stream().forEach(uploadName -> {
                product.addImageString(uploadName);
            });
        }
        productRepository.save(product);
    }

    @Override
    public void remove(Long pno) {
        productRepository.updateToDelete(pno, true);
    }
}
