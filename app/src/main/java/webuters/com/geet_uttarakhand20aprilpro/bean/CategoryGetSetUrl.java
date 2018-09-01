package webuters.com.geet_uttarakhand20aprilpro.bean;

/**
 * Created by Admin on 2/18/2016.
 */
public class CategoryGetSetUrl {

    @Override
    public String toString() {
        return "CategoryGetSetUrl{" +
                "id='" + id + '\'' +
                ", CatagoryName='" + CatagoryName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatagoryName() {
        return CatagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        CatagoryName = catagoryName;
    }

    String id,CatagoryName;

}
