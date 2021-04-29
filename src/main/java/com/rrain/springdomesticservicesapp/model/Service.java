package com.rrain.springdomesticservicesapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

// TODO: 06.04.2021 Сделать в бд только ссылки на изображения и загружать их отдельным параллельным запросом, чтобы первичный текст уже загрузился и можно было что читать

@Document(collection = "services")
@Data @NoArgsConstructor
public class Service {

    @Id private String id;
    private String title;
    private byte[] image;
    private String description;
    private String price;

    public Service(String title, byte[] image, String description, String price) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", images=" + (image==null ? "null" : ("Byte array with len="+ image.length)) +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
