using System;

namespace backend.Models
{
  public class Pet
  {
    public int Id { get; set; }
    public string? Name { get; set; }
    public DateTime LastRabiesVaccine { get; set; }
    public DateTime LastPolyvalentVaccine { get; set; }
    public int? Age { get; set; }
    public string? Species { get; set; }
    public string? Breed { get; set; }

    // Relacionamento com o usuário
    public int UserId { get; set; }
    public User? User { get; set; }

    public void ConvertDatesToUtc()
    {
      LastRabiesVaccine = DateTime.SpecifyKind(LastRabiesVaccine, DateTimeKind.Utc);
      LastPolyvalentVaccine = DateTime.SpecifyKind(LastPolyvalentVaccine, DateTimeKind.Utc);
    }
  }
}
