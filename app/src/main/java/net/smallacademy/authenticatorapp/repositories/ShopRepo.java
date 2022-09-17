package net.smallacademy.authenticatorapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.smallacademy.authenticatorapp.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopRepo {

    private MutableLiveData<List<Product>> mutableProductList;

    public LiveData<List<Product>> getProducts() {
        if (mutableProductList == null) {
            mutableProductList = new MutableLiveData<>();
            loadProducts();
        }
        return mutableProductList;
    }

    private void loadProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(UUID.randomUUID().toString(), "Hair Oil",  13.00, true, "https://firebasestorage.googleapis.com/v0/b/hairdonatioa-app.appspot.com/o/Shop%20image%2Fhairoil.JPG?alt=media&token=5bdbac3b-0dc1-4b2a-945f-468e7212d024" ));
        productList.add(new Product(UUID.randomUUID().toString(), "Gold Hair Oil", 8.00, true, "https://firebasestorage.googleapis.com/v0/b/hairdonatioa-app.appspot.com/o/Shop%20image%2Fgoldoil.jpg?alt=media&token=65c9e13d-0a1c-482f-a05b-0c337f8b8800"));
        productList.add(new Product(UUID.randomUUID().toString(), "Almond Shampoo", 14.00, true, "https://firebasestorage.googleapis.com/v0/b/hairdonatioa-app.appspot.com/o/Shop%20image%2Falmondshampoo.jpg?alt=media&token=e3dc6386-405e-4c8b-b61f-5b715d499e3f"));
        productList.add(new Product(UUID.randomUUID().toString(), "Amla Serum", 41.00, false, "https://firebasestorage.googleapis.com/v0/b/hairdonatioa-app.appspot.com/o/Shop%20image%2Fserum.jpg?alt=media&token=31f50740-4453-4985-ae12-1c52fe55bc1a"));
        productList.add(new Product(UUID.randomUUID().toString(), "Herb Essential", 50.00, true, "https://firebasestorage.googleapis.com/v0/b/hairdonatioa-app.appspot.com/o/Shop%20image%2Fherb%20essential.jpg?alt=media&token=9c5a9683-347a-4aff-aab0-22874927bf0b"));
        productList.add(new Product(UUID.randomUUID().toString(), "Amla Oil ", 103.00, true, "https://firebasestorage.googleapis.com/v0/b/hairdonatioa-app.appspot.com/o/Shop%20image%2Famla%20oil.jpg?alt=media&token=33781543-14f3-47b6-b44b-551bc6acce75"));
        productList.add(new Product(UUID.randomUUID().toString(), "DABUR", 41.00, false, "https://firebasestorage.googleapis.com/v0/b/hairdonatioa-app.appspot.com/o/Shop%20image%2Fdabur.png?alt=media&token=83d7e8e4-3a55-41b0-9dd5-ffdf37212a79"));
        productList.add(new Product(UUID.randomUUID().toString(), "DABUR", 50.00, false, "https://firebasestorage.googleapis.com/v0/b/hairdonatioa-app.appspot.com/o/Shop%20image%2Fdabur.png?alt=media&token=83d7e8e4-3a55-41b0-9dd5-ffdf37212a79"));
        productList.add(new Product(UUID.randomUUID().toString(), "DABUR", 41.00, false, "https://firebasestorage.googleapis.com/v0/b/hairdonatioa-app.appspot.com/o/Shop%20image%2Fdabur.png?alt=media&token=83d7e8e4-3a55-41b0-9dd5-ffdf37212a79"));
        productList.add(new Product(UUID.randomUUID().toString(), "DABUR", 50.00, false, "https://firebasestorage.googleapis.com/v0/b/hairdonatioa-app.appspot.com/o/Shop%20image%2Fdabur.png?alt=media&token=83d7e8e4-3a55-41b0-9dd5-ffdf37212a79"));



        mutableProductList.setValue(productList);
    }
}
