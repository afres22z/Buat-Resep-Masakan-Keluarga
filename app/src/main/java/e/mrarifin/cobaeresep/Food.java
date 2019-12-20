package e.mrarifin.cobaeresep;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class Food {
    private int id;
    private String name;
    private String price;
    private String cara;
    private byte[] image;

    public Food(String name, String price, String cara, byte[] image, int id) {
        this.name = name;
        this.price = price;
        this.cara = cara;
        this.image = image;

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String cara) {
        this.cara = price;
    }

    public String getcara() {
        return cara;
    }

    public void setcara(String cara) {
        this.cara = cara;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
