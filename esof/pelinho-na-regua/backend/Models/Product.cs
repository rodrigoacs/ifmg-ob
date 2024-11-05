namespace backend.Models
{
  public class Product
  {
    public int Id { get; set; }
    public string? Name { get; set; }
    public string? Description { get; set; }
    public decimal OriginalPrice { get; set; }
    public decimal? DiscountedPrice { get; set; }
    public decimal? DiscountPercentage { get; set; }
    public string? ImageUrl { get; set; }
  }
}
