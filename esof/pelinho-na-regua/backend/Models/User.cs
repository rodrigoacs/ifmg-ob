namespace backend.Models
{
  public class User
  {
    public int Id { get; set; }
    public string? Username { get; set; }
    public string? Password { get; set; }
    public UserProfile? UserProfile { get; set; }  // Remova qualquer restrição obrigatória aqui
  }
}
