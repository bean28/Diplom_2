package model;

import java.util.Arrays;
import java.util.List;

public class Order {
    List<String> ingredients;

    public Order(String... s) {
        this.ingredients = Arrays.asList(s);
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
