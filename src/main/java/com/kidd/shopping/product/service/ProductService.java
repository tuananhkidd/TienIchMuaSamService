package com.kidd.shopping.product.service;

import com.kidd.shopping.base.entity.PageDto;
import com.kidd.shopping.base.response.NotFoundResponse;
import com.kidd.shopping.base.response.OkResponse;
import com.kidd.shopping.base.response.Response;
import com.kidd.shopping.base.tools.HttpPostRequestBuilder;
import com.kidd.shopping.base.tools.PageAndSortRequestBuilder;
import com.kidd.shopping.product.entity.*;
import com.kidd.shopping.product.entity.request.ProductFilter;
import com.kidd.shopping.product.entity.response.ProductDto;
import com.kidd.shopping.product.repository.*;
import com.kidd.shopping.utils.Constant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProductService {
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private StyleRepository styleRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private ProductPropertyRepository productPropertyRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProductPropertyColorRepository productPropertyColorRepository;

    @Transactional(rollbackFor = Exception.class)
    public Response crawlProductProperty(long categoryID, String path) throws IOException {
        Document doc = Jsoup.connect(path).get();
        doc.getElementById("filters");
        Elements filters = doc.select("ul#filters");
        Elements properties = filters.first().getElementsByClass("filter");
        ProductCategory productCategory = productCategoryRepository.findOne(categoryID);
        for (Element e : properties) {
            int type = Integer.parseInt(e.attr("data-type"));
            Elements propertyElements = e.getElementsByTag("a");
            switch (type) {
                case 1: {
                    //color
                    for (Element colorE : propertyElements) {
                        Color color = new Color();
                        color.setName(colorE.text());
                        color.setProductCategory(productCategory);
                        colorRepository.save(color);
                    }
                    break;
                }

                case 2: {
                    //Size
                    for (Element colorE : propertyElements) {
                        Size size = new Size();
                        size.setName(colorE.text());
                        size.setProductCategory(productCategory);
                        sizeRepository.save(size);
                    }
                    break;
                }


                case 3: {
                    //Material
                    for (Element colorE : propertyElements) {
                        Material material = new Material();
                        material.setName(colorE.text());
                        material.setProductCategory(productCategory);
                        materialRepository.save(material);
                    }
                    break;
                }


                case 4: {
                    //Style
                    for (Element colorE : propertyElements) {
                        Style style = new Style();
                        style.setName(colorE.text());
                        style.setProductCategory(productCategory);
                        styleRepository.save(style);
                    }
                    break;
                }

                default: {
                    break;
                }
            }

        }

        //get data : filters.first().getElementsByClass("filter").first().getElementsByTag("a").first().text()
        return new OkResponse();
    }

    @Transactional(rollbackFor = Exception.class)
    public Response crawlProduct(long categoryID, String catID) throws IOException {
        ProductCategory productCategory = productCategoryRepository.findOne(categoryID);
        if (productCategory == null) {
            return new NotFoundResponse("Category not exist");
        }
        String contentHtml = new HttpPostRequestBuilder(restTemplate)
                .withUrl("tienichmuasam.com/san_pham/searchProductPaging?categoryId=" + (catID) + "&attribute&keySearch=&currentPage=1&pageSize=50&sortType=47")
                .withProtocol(HttpPostRequestBuilder.HTTPS)
                .setFormDataBody(new LinkedMultiValueMap<>())
                .execute(String.class);
        Document doc = Jsoup.parse(contentHtml);
        Elements listItemElements = doc.getElementsByClass("each_item");
        for (Element item : listItemElements) {
            Elements aElement = item.getElementsByTag("a");
            String productID = aElement.first().attr("data-goodsid");
            int colorDataFilter = Integer.parseInt(aElement.first().attr("data-colorid"));

            //get size
            Elements avatarElement = item.getElementsByClass("item_top");
            Document docDetail = Jsoup.connect("https://tienichmuasam.com" + avatarElement.first().attr("href")).get();

            Elements sizeElement = docDetail.getElementsByClass("optionsbox").first()
                    .getElementsByClass("add_bag_size").first().getElementsByTag("li");
            List<Size> sizes = new ArrayList<>();
            List<ProductPropertyColor> productPropertyColorList = new ArrayList<>();
            for (Element size : sizeElement) {
                sizes.add(sizeRepository.getSizeByName(size.text().trim(), categoryID));
            }

            if (!productRepository.exists(productID)) {
                Product product = new Product();
                product.setId(productID);
                Elements inforElement = item.getElementsByClass("item_bottom");
                Elements priceElement = inforElement.first().getElementsByClass("price");
                long sellerPrice = Long.valueOf(priceElement.get(0).text().replaceAll("đ", "").replaceAll("[.]", "").trim());
                product.setSellerPrice(new BigDecimal(sellerPrice));
                if (priceElement.size() > 1) {
                    long regularPrice = Long.valueOf(priceElement.get(1).text().replaceAll("đ", "").replaceAll("[.]", "").trim());
                    product.setRegularPrice(new BigDecimal(regularPrice));
                } else {
                    product.setRegularPrice(new BigDecimal(sellerPrice));
                }
                product.setAvatarUrl(avatarElement.first().getElementsByTag("img").first().attr("src"));

                Elements content = docDetail.getElementsByClass("dt-right");
                String name = content.first().getElementsByTag("h2").first().text();
                product.setName(name);

                Elements imageUrlElement = docDetail.getElementsByClass("optionsbox").first().getElementsByClass("add_bag_color");
                List<String> lsImage = new ArrayList<>();
                for (Element image : imageUrlElement.first().getElementsByTag("img")) {
                    lsImage.add(image.attr("src"));
                }
                product.setImageUrls(lsImage);
                Element description = docDetail.getElementsByClass("description-main").first();

                product.setDescription(description.toString());
                product.setProductCategory(productCategory);

                productRepository.save(product);


                ProductProperty productProperty = new ProductProperty();
                List<Material> materialList = materialRepository.findAll();
                if (!materialList.isEmpty()) {
                    Random materialRandom = new Random();
                    productProperty.setMaterial(materialList.get(materialRandom.nextInt(materialList.size())));
                }

                List<Style> styleList = styleRepository.findAll();
                if (!styleList.isEmpty()) {
                    Random styleRandom = new Random();
                    productProperty.setStyle(styleList.get(styleRandom.nextInt(styleList.size())));
                }
                productProperty.setProduct(product);

                productPropertyRepository.save(productProperty);
                for (Size size : sizes) {
                    ProductPropertyColor productPropertyColor = new ProductPropertyColor();
                    productPropertyColor.setProductProperty(productProperty);
                    productPropertyColor.setColor(colorRepository.getColorByByDataFilter(colorDataFilter, categoryID));
                    productPropertyColor.setSize(size);
                    productPropertyColorList.add(productPropertyColor);
                }
            } else {
                ProductProperty productProperty = productPropertyRepository.findFirstByProduct_Id(productID);
                for (Size size : sizes) {
                    ProductPropertyColor productPropertyColor = new ProductPropertyColor();
                    productPropertyColor.setProductProperty(productProperty);
                    productPropertyColor.setColor(colorRepository.getColorByByDataFilter(colorDataFilter, categoryID));
                    productPropertyColor.setSize(size);
                    productPropertyColorList.add(productPropertyColor);
                }
            }
            productPropertyColorRepository.save(productPropertyColorList);
            productPropertyColorList.clear();
        }
        return new OkResponse();
    }

    public Response getListProduct(Long categoryId, String sortBy, String sortType, int pageIndex, int pageSize) {
        Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
        if (categoryId != null) {
            return new OkResponse(new PageDto<>(productRepository.getListProductsByCategory(pageable, categoryId)));
        }
        return new OkResponse(new PageDto<>(productRepository.getListProducts(pageable)));
    }

    public Response searchProducts(ProductFilter productFilter,
                                   String sortBy,
                                   String sortType,
                                   Integer pageIndex,
                                   Integer pageSize) {
        StringBuilder conditionQuery = new StringBuilder();
        StringBuilder joinQuery = new StringBuilder();
        String query = (" select new com.kidd.shopping.product.entity.response.ProductDto" +
                "(p.id,p.name,p.regularPrice,p.sellerPrice,p.avatarUrl,p.favouriteCount) from Product p , ProductProperty pp");
        String countQuery = "select count(p.id)  from Product p , ProductProperty pp ";
        conditionQuery.append(" where p.id = pp.product.id ");
        if (productFilter != null) {
            if (productFilter.getCategoryId() != null) {
                joinQuery.append(" join p.productCategory pc ");
                conditionQuery.append("and pc.id = ").append(productFilter.getCategoryId());
            }
            if (productFilter.getColorID() != null) {
                joinQuery.append(",ProductPropertyColor ppc ");
                conditionQuery.append(" and ppc.productProperty.id = pp.id ");
                conditionQuery.append(" and ppc.color.id = ").append(productFilter.getColorID());
            }
            if (productFilter.getSizeID() != null) {
                if (productFilter.getColorID() == null) {
                    joinQuery.append(",ProductPropertyColor ppc ");
                    conditionQuery.append(" and ppc.productProperty.id = pp.id ");
                }
                conditionQuery.append(" and ppc.size.id = ").append(productFilter.getSizeID());
            }
            if (productFilter.getMaterialID() != null) {
                joinQuery.append("left join pp.material m ");
                conditionQuery.append(" and m.id = ").append(productFilter.getMaterialID());
            }

            if (productFilter.getStyleID() != null) {
                joinQuery.append("left join pp.style s ");
                conditionQuery.append(" and s.id = ").append(productFilter.getStyleID());
            }

            if (productFilter.getKeyword() != null) {
                conditionQuery.append(" and p.name like '%").append(productFilter.getKeyword()).append("%'");
            }

        }

        conditionQuery.append(" group by p.id");

        Pageable pageable
                = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
        query = query.concat(joinQuery.toString()).concat(conditionQuery.toString());
        countQuery = countQuery.concat(joinQuery.toString()).concat(conditionQuery.toString());
        List<ProductDto> results = entityManager.createQuery(query, ProductDto.class).setMaxResults(pageable.getPageSize())
                .setFirstResult(pageIndex * pageable.getPageSize()).getResultList();
        Long totalItemFound;
        try {
            totalItemFound = entityManager.createQuery(countQuery, Long.class).getSingleResult();
        } catch (Exception e) {
            totalItemFound = 0L;
        }
        return new OkResponse(new PageDto<>(results, pageIndex, pageSize, totalItemFound));
    }
}
