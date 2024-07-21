/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package prolab.media;

/**
 *
 * @author kaan
 */
public enum ImagePath {

    CHARACTER("/home/kaan/Desktop/karakter.jpeg"),
    SUMMER_TREE("/home/kaan/Desktop/tekagac.png"),
    WINTER_TREE("/home/kaan/Desktop/karliagac.jpg"),
    SUMMER_JUNGLE("/home/kaan/Desktop/orman.jpg"),
    WINTER_JUNGLE("/home/kaan/Desktop/karliorman.jpg"),
    SUMMER_COBBEL_STONE("/home/kaan/Desktop/cakiltasi.jpg"),
    WINTER_COBBEL_STONE("/home/kaan/Desktop/buzlucakiltasi.jpg"),
    SUMMER_MOUNTAIN("/home/kaan/Desktop/yazdag.png"),
    WINTER_MOUNTAIN("/home/kaan/Desktop/kisdag.png"),
    SUMMER_ROW_MOUNTAIN("/home/kaan/Desktop/yazsiradag.jpg"),
    WINTER_ROW_MOUNTAIN("/home/kaan/Desktop/kissiradag.jpg"),
    SUMMER_ROCK("/home/kaan/Desktop/kaya.jpg"),
    WINTER_ROCK("/home/kaan/Desktop/buzlukaya.png"),
    SUMMER_WALL("/home/kaan/Desktop/yazduvar.jpg"),
    WINTER_WALL("/home/kaan/Desktop/kisduvar.jpg"),
    SUMMER_LAKE("/home/kaan/Desktop/yazgol.jpeg"),
    WINTER_LAKE("/home/kaan/Desktop/kisgol.jpeg"),
    DIAMOND_CHESS("/home/kaan/Desktop/elmassandik.jpg"),
    GOLD_CHESS("/home/kaan/Desktop/altinsandik.jpeg"),
    SILVER_CHESS("/home/kaan/Desktop/gumussandik.jpeg"),
    COPPER_CHESS("/home/kaan/Desktop/bakirsandik.jpeg"),
    BEE("/home/kaan/Desktop/ari.jpg"),
    BIRD("/home/kaan/Desktop/kus.jpg"),
    FOG("/home/kaan/Desktop/sis.jpg");

    private String path;

    private ImagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
