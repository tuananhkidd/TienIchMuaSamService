package com.kidd.shopping.product.entity;


import com.kidd.shopping.base.converter.SizeConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_property")
public class ProductProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Convert(converter = SizeConverter.class)
    private List<ColorSize> colorSize;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_id")
    private Style style;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<ColorSize> getColorSize() {
        return colorSize;
    }

    public void setColorSize(List<ColorSize> colorSize) {
        this.colorSize = colorSize;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
