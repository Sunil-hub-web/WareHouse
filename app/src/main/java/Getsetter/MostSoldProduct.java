package Getsetter;

public class MostSoldProduct {

    String SalesPrice,MrpPrice;
    int images;

    public MostSoldProduct(String salesPrice, String mrpPrice, int images) {
        SalesPrice = salesPrice;
        MrpPrice = mrpPrice;
        this.images = images;
    }

    public MostSoldProduct() {

    }


    public String getSalesPrice() {
        return SalesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        SalesPrice = salesPrice;
    }

    public String getMrpPrice() {
        return MrpPrice;
    }

    public void setMrpPrice(String mrpPrice) {
        MrpPrice = mrpPrice;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }
}
