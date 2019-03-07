package Models;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.Set;

public class SomeDataBase {

    private static HashMap<String, String> hashMap;

    static {
        hashMap = new HashMap<>();
        hashMap.put("saint-p shop lig", "РФ, СПб, Лиговский проспект, д.74");
        hashMap.put("saint-p shop col", "РФ, СПб, Коломяжский проспект, д.55");
        hashMap.put("saint-p shop yak", "РФ, СПб, Приморский проспект, д.72");
    }

    public static Set<String> getShops(){
        return hashMap.keySet();
    }

    public static String getAddressByShop(String shopId) throws NullPointerException {
        return hashMap.get(shopId);
    }
}
