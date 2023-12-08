package nutritionproject;

public class FoodItem 
{
    String foodName;
    String servingSize;
    String calories;
    String protein;
    String carbs;
    String totalFat;
    String saturatedFat;
    String totalSugar;
    String addedSugar;
    String sodium;
    String cholesterol;
    String fiber;
   
    public FoodItem(
            String foodName,
            String servingSize,
            String calories,
            String protein,
            String carbs,
            String totalFat,
            String saturatedFat,
            String totalSugar,
            String addedSugar,
            String sodium,
            String cholesterol,
            String fiber
    )
    {
        this.foodName = foodName;
        this.servingSize = servingSize;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
        this.totalSugar = totalSugar;
        this.addedSugar = addedSugar;
        this.sodium = sodium;
        this.cholesterol = cholesterol;
        this.fiber = fiber;
    }
    
    public String getFoodName()
    {
        return foodName;
    }
    public void setFoodName(String foodName)
    {
        this.foodName = foodName;
    }
    
    public String getServingSize()
    {
        return servingSize;
    }
    public void setServingSize(String servingSize)
    {
        this.servingSize = servingSize;
    }
    
    public String getCalories()
    {
        return calories;
    }
    public void setCalories(String calories)
    {
        this.calories = calories;
    }
    
    public String getProtein()
    {
        return protein;
    }
    public void setProtein(String protein)
    {
        this.protein = protein;
    }
    
    public String getCarbs()
    {
        return carbs;
    }
    public void setCarbs(String carbs)
    {
        this.carbs = carbs;
    }
    
    public String getTotalFat()
    {
        return totalFat;
    }
    public void setTotalFat(String totalFat)
    {
        this.totalFat = totalFat;
    }
    
    public String getSaturatedFat()
    {
        return saturatedFat;
    }
    public void setSaturatedFat(String saturatedFat)
    {
        this.saturatedFat = saturatedFat;
    }
    
    public String getTotalSugar()
    {
        return totalSugar;
    }
    public void setTotalSugar(String totalSugar)
    {
        this.totalSugar = totalSugar;
    }
    
    public String getAddedSugar()
    {
        return addedSugar;
    }
    public void setAddedSugar(String addedSugar)
    {
        this.addedSugar = addedSugar;
    }
    
    public String getSodium()
    {
        return sodium;
    }
    public void setSodium(String sodium)
    {
        this.sodium = sodium;
    }
    
    public String getCholesterol()
    {
        return cholesterol;
    }
    public void setCholesterol(String cholesterol)
    {
        this.cholesterol = cholesterol;
    }
    
    public String getFiber()
    {
        return fiber;
    }
    public void setFiber(String fiber)
    {
        this.fiber = fiber;
    }
    
    public String toString()
    {
        return "foodName = " + foodName
                + "\nservingSize = " + servingSize
                + "\ncalories = " + calories
                + "\nprotein = " + protein
                + "\ncarbs = " + carbs 
                + "\ntotalFat = " + totalFat
                + "\nsaturatedFat = " + saturatedFat
                + "\ntotalSugar = " + totalSugar
                + "\naddedSugar = " + addedSugar
                + "\nsodium = " + sodium
                + "\ncholesterol = " + cholesterol
                + "\nfiber = " + fiber;
    }
}
