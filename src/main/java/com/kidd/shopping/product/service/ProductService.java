package com.kidd.shopping.product.service;

import com.kidd.shopping.base.response.NotFoundResponse;
import com.kidd.shopping.base.response.OkResponse;
import com.kidd.shopping.base.response.Response;
import com.kidd.shopping.base.tools.HttpPostRequestBuilder;
import com.kidd.shopping.product.entity.*;
import com.kidd.shopping.product.repository.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
                .withUrl("tienichmuasam.com/san_pham/searchProductPaging?categoryId="+(catID)+"&attribute&keySearch=&currentPage=1&pageSize=50&sortType=47")
                .withProtocol(HttpPostRequestBuilder.HTTPS)
                .setFormDataBody(new LinkedMultiValueMap<>())
                .execute(String.class);
        Document doc = Jsoup.parse(contentHtml);
        Elements listItemElements = doc.getElementsByClass("each_item");
        for (Element item : listItemElements) {
            Elements aElement = item.getElementsByTag("a");
            String productID = aElement.first().attr("data-goodsid");
            int colorDataFilter = Integer.parseInt(aElement.first().attr("data-colorid"));
            System.out.println("colorDataFilter : "+colorDataFilter);

            //get size
            Elements avatarElement = item.getElementsByClass("item_top");
            Document docDetail = Jsoup.connect("https://tienichmuasam.com" + avatarElement.first().attr("href")).get();

            Elements sizeElement = docDetail.getElementsByClass("optionsbox").first()
                    .getElementsByClass("add_bag_size").first().getElementsByTag("li");
            List<Integer> sizes = new ArrayList<>();
            for (Element size : sizeElement) {
                System.out.println("size_size :"+ size.text() + "  " + " url : "+ avatarElement.first().attr("href"));
                int sizeID = sizeRepository.getSizeIDByName(size.text().trim(),categoryID);
                sizes.add(sizeID);
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
                }else {
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
                System.out.println("description : "+description.toString());

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
                ColorSize colorSize = new ColorSize();
                colorSize.setColor(colorRepository.getColorIDByByDataFilter(colorDataFilter,categoryID));
                colorSize.setSize(sizes);
                List<ColorSize> colorSizes = new ArrayList<>();
                colorSizes.add(colorSize);
                productProperty.setColorSize(colorSizes);

                productPropertyRepository.save(productProperty);
            } else {
                ProductProperty productProperty = productPropertyRepository.findFirstByProduct_Id(productID);
                ColorSize colorSize = new ColorSize();
                colorSize.setColor(colorRepository.getColorIDByByDataFilter(colorDataFilter,categoryID));
                colorSize.setSize(sizes);
                List<ColorSize> colorSizeList = productProperty.getColorSize();
                colorSizeList.add(colorSize);

                productPropertyRepository.save(productProperty);
            }

        }
        return new OkResponse();
    }
}
