using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace backend.Models
{
  public class Event
  {
    [Key]
    public int Id { get; set; }
    [ForeignKey("User")]
    public int UserId { get; set; }
    public User? User { get; set; }
    [Required]
    [MaxLength(100)]
    public string? Title { get; set; }
    public string? Description { get; set; }
    [Required]
    public DateTime StartTime { get; set; }
    [Required]
    public DateTime EndTime { get; set; }
    public string? Location { get; set; }
    public bool AllDay { get; set; }
    public void ConvertDatesToUtc()
    {
      StartTime = DateTime.SpecifyKind(StartTime, DateTimeKind.Utc);
      EndTime = DateTime.SpecifyKind(EndTime, DateTimeKind.Utc);
    }
  }
}
