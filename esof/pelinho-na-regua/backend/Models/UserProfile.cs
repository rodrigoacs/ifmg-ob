namespace backend.Models
{
  public class UserProfile
  {
    public int Id { get; set; }
    public string? FullName { get; set; }
    public string? Phone { get; set; }
    public string? Street { get; set; }
    public string? Neighborhood { get; set; }
    public string? City { get; set; }
    public string? Number { get; set; }
    public string? Complement { get; set; }

    public int UserId { get; set; }  // Chave estrangeira
    public User? User { get; set; }   // Navegação para User
  }
}
