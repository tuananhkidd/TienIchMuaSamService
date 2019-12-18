package com.kidd.shopping.product.service;

import com.kidd.shopping.base.response.OkResponse;
import com.kidd.shopping.base.response.Response;
import com.kidd.shopping.product.entity.*;
import com.kidd.shopping.product.repository.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

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

    @Transactional(rollbackFor = Exception.class)
    public Response crawlProductProperty(long categoryID,String path) throws IOException {
        Document doc = Jsoup.connect(path).get();
        doc.getElementById("filters");
        Elements filters = doc.select("ul#filters");
        Elements properties = filters.first().getElementsByClass("filter");
        ProductCategory productCategory = productCategoryRepository.findOne(categoryID);
        for(Element e:properties){
            int type = Integer.parseInt(e.attr("data-type"));
            Elements propertyElements = e.getElementsByTag("a");
            switch (type){
                case 1:{
                    //color
                    for(Element colorE:propertyElements){
                        Color color = new Color();
                        color.setName(colorE.text());
                        color.setProductCategory(productCategory);
                        colorRepository.save(color);
                    }
                    break;
                }

                case 2:{
                    //Size
                    for(Element colorE:propertyElements){
                        Size size = new Size();
                        size.setName(colorE.text());
                        size.setProductCategory(productCategory);
                        sizeRepository.save(size);
                    }
                    break;
                }


                case 3:{
                    //Material
                    for(Element colorE:propertyElements){
                        Material material = new Material();
                        material.setName(colorE.text());
                        material.setProductCategory(productCategory);
                        materialRepository.save(material);
                    }
                    break;
                }


                case 4:{
                    //Style
                    for(Element colorE:propertyElements){
                        Style style = new Style();
                        style.setName(colorE.text());
                        style.setProductCategory(productCategory);
                        styleRepository.save(style);
                    }
                    break;
                }

                default:{
                    break;
                }
            }

        }

        //get data : filters.first().getElementsByClass("filter").first().getElementsByTag("a").first().text()
        return new OkResponse();
    }

    @Transactional(rollbackFor = Exception.class)
    public Response crawlProduct(long categoryID, String path) throws IOException {
        Document doc = Jsoup.connect(path).get();
        Elements listItemElements = doc.getElementsByClass("each_item");
        for(Element item:listItemElements){
            Elements aElement = item.getElementsByTag("a");
            String productID = aElement.first().attr("data-goodsid");
            if(!productRepository.exists(productID)){
                Product product = new Product();
                product.setId(productID);
                Elements inforElement = item.getElementsByClass("item_bottom");
                product.setName(inforElement.first().getElementsByTag("a").first().attr("title"));
                Elements priceElement = inforElement.first().getElementsByClass("price");
                long sellerPrice = Long.valueOf(priceElement.get(0).text().replaceAll("đ","").replaceAll("[.]","").trim());
                product.setSellerPrice(new BigDecimal(sellerPrice));
                if(priceElement.size()>1){
                    long regularPrice = Long.valueOf(priceElement.get(1).text().replaceAll("đ","").replaceAll("[.]","").trim());
                    product.setSellerPrice(new BigDecimal(regularPrice));
                }
                Elements avatarElement = item.getElementsByClass("item_top");
                product.setAvatarUrl(avatarElement.first().getElementsByTag("img").first().attr("src"));



                productRepository.save(product);
                ProductProperty productProperty = new ProductProperty();

                productProperty.setProduct(product);

                productPropertyRepository.save(productProperty);
            }else {
                Product product = productRepository.findOne(productID);


                productRepository.save(product);
            }

        }
        return new OkResponse();
    }
}
