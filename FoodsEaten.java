package nutritionproject;

public class FoodsEaten 
{
    String dateEaten;
    String foodName;
    String servingSize;
    String servings;
    
    public FoodsEaten(String dateEaten, String foodName, String servingSize, String servings)
    {
        this.dateEaten = dateEaten;
        this.foodName = foodName;
        this.servingSize = servingSize;
        this.servings = servings;
    }
    
    public String getDateEaten()
    {
        return dateEaten;
    }
    public void setDateEaten(String dateEaten)
    {
        this.dateEaten = dateEaten;
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
   
    public String getServings()
    {
        return servings;
    }
    public void setServings(String servings)
    {
        this.servings = servings;
    }
    
    public String toString()
    {
        return "dateEaten = " + dateEaten + "\nfoodName = " + foodName + "\nservingSize = " + servingSize + "\nservings = " + servings;
    }
}
