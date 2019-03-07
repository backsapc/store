package Models;

import Beans.InterfaceBean;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by alexthor on 15.10.17.
 */
public class InterfaceGenerator {

    private static HashMap<Locale, InterfaceBean> cache;

    public static InterfaceBean getInterface(Locale locale){
        if(cache==null)
            cache = new HashMap<>();
        if(cache.containsKey(locale))
            return cache.get(locale);
        ResourceBundle interfaceBundle = ResourceBundle.getBundle("InterfaceBundle", locale);
        String detailsLabel = interfaceBundle.getString("DetailsLabel");
        String cartLabel = interfaceBundle.getString("CartButtonLabel");
        String addToCartLabel = interfaceBundle.getString("AddToCartButtonLabel");
        String loginLabel = interfaceBundle.getString("LoginLabel");
        String langLabel = ResponseGenerator.getLanguageByLocale(locale);
        String lang = interfaceBundle.getString("Lang");
        String productListLabel = interfaceBundle.getString("ProductsListLabel");
        String category = interfaceBundle.getString("Category");
        String allLabel = interfaceBundle.getString("all");
        String desktopLabel = interfaceBundle.getString("desktops");
        String laptops = interfaceBundle.getString("laptops");
        String tablets = interfaceBundle.getString("tablets");
        String itemsLabel = interfaceBundle.getString("Items");
        String totalLabel = interfaceBundle.getString("Total");
        String orderDetailsLabel = interfaceBundle.getString("OrderDetails");
        String emptyCartLabel = interfaceBundle.getString("EmptyCart");
        String checkOutLabel = interfaceBundle.getString("Checkout");
        String logoutLabel = interfaceBundle.getString("Logout");
        String profileLabel = interfaceBundle.getString("Profile");
        String priceLabel = interfaceBundle.getString("Price");
        InterfaceBean iBean = new InterfaceBean();
        iBean.setPriceLabel(priceLabel);
        iBean.setAddToCartLabel(addToCartLabel);
        iBean.setCartLabel(cartLabel);
        iBean.setDetailsLabel(detailsLabel);
        iBean.setLangLabel(langLabel);
        iBean.setLangParam(lang);
        iBean.setLoginLabel(loginLabel);
        iBean.setProductListLabel(productListLabel);
        iBean.setCategoryLabel(category);
        iBean.setAllLabel(allLabel);
        iBean.setDesktopsLabel(desktopLabel);
        iBean.setLaptopsLabel(laptops);
        iBean.setTabletsLabel(tablets);
        iBean.setItemsLabel(itemsLabel);
        iBean.setProfileLabel(profileLabel);
        iBean.setLogoutLabel(logoutLabel);
        iBean.setCheckoutLabel(checkOutLabel);
        iBean.setEmptyCartLabel(emptyCartLabel);
        iBean.setSummaryLabel(orderDetailsLabel);
        iBean.setTotalLabel(totalLabel);
        cache.put(locale, iBean);
        return iBean;
    }
}
