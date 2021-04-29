package com.rrain.springdomesticservicesapp.utils;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


public final class PathProvider {
    public static final Path projectRoot;
    public static final Path images;

    static {
        try {
            projectRoot = Path.of(PathProvider.class.getResource("").toURI()).resolve("../../../../");
            images = projectRoot.resolve("images");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("PathProvider CAN'T OBTAIN PATHS");
        }
    }

    /*private static URI add(URI path, String next){
        //Path.of()
        System.out.println("Path: " + Paths.get(next));
        return path.resolve(Paths.get(next).toUri());
    }*/

    public static Path getImage(String name) {
        return images.resolve(name);
    }
    /*public static URL getImage(String name) {
        try {
            System.out.println(images);
            URI uri = add(images, name);
            System.out.println(uri);
            return uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("PathProvider CAN'T OBTAIN PATHS");
        }
    }*/

}
