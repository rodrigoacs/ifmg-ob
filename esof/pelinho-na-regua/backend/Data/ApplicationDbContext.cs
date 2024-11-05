using Microsoft.EntityFrameworkCore;
using backend.Models;

namespace backend.Data
{
  public class ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : DbContext(options)
  {
    public DbSet<UserProfile> UserProfiles { get; set; }
    public DbSet<User> Users { get; set; }
    public DbSet<Product> Products { get; set; }
    public DbSet<Pet> Pets { get; set; }
    public DbSet<Event> Events { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
      base.OnModelCreating(modelBuilder);

      modelBuilder.Entity<User>()
          .HasOne(u => u.UserProfile)
          .WithOne(p => p.User)
          .HasForeignKey<UserProfile>(p => p.UserId);
    }
  }
}
