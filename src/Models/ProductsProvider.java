package Models;

import Beans.ProductPreviewBean;

import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by alexthor on 14.10.17.
 */
public class ProductsProvider {

    private static ProductPreviewBean getProduct(ResourceBundle resourceBundle){
        String title = resourceBundle.getString("ProductTitle");
        String imageUrl = resourceBundle.getString("ImagesPath") + "1.jpg";
        String overview = (resourceBundle.getString("DescriptionContent").split("[;]"))[0];
        String price = resourceBundle.getString("Price");
        ProductPreviewBean bean = new ProductPreviewBean();
        bean.setTitle(title);
        bean.setOverview(overview);
        bean.setImageUrl(imageUrl);
        bean.setPrice(price);
        return bean;
    }

    public static LinkedList<ProductPreviewBean> getProducts(String category, Locale locale) {
        String[] products = getProductsByCategory(category);
        ResourceBundle productsBundle = ResourceBundle.getBundle("ProductsBundle");
        LinkedList<ProductPreviewBean> beans = new LinkedList<>();
        for(String product: products){
            beans.add(getProductBean(product, locale, productsBundle));
        }
        return beans;
    }

    public static ProductPreviewBean getProductBean(String productName, Locale locale, ResourceBundle productsBundle){
        String bundle = productsBundle.getString(productName);
        ResourceBundle productBundle =  ResourceBundle.getBundle(bundle, locale);
        ProductPreviewBean bean = getProduct(productBundle);
        bean.setProductName(productName);
        return bean;
    }

    public static String[] getProductsByCategory(String category){
        ResourceBundle categories = ResourceBundle.getBundle("CategoryBundle");
        return categories.getString(category.toLowerCase()).split("[;]");
    }

    public static String checkCategory(String category){
        String target = category.toLowerCase();
        ResourceBundle categories = ResourceBundle.getBundle("CategoryBundle");
        if(categories.containsKey(target))
            return target;
        return "all";
    }

    public static String getProductName(String productId){
        ResourceBundle productsBundle = ResourceBundle.getBundle("ProductsBundle");
        String productBundleName = productsBundle.getString(productId);
        ResourceBundle productBundle = ResourceBundle.getBundle(productBundleName);
        String title = productBundle.getString("ProductTitle");
        return title;
    }
}
