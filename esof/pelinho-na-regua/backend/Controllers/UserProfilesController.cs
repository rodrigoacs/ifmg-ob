using Microsoft.AspNetCore.Mvc;
using backend.Data;
using backend.Models;
using Microsoft.EntityFrameworkCore;

namespace backend.Controllers
{
  [Route("api/[controller]")]
  [ApiController]
  public class UserProfilesController(ApplicationDbContext context) : ControllerBase
  {
    private readonly ApplicationDbContext _context = context;

    [HttpGet("{userId}")]
    public async Task<IActionResult> GetUserProfile(int userId)
    {
      var userProfile = await _context.UserProfiles.FirstOrDefaultAsync(up => up.UserId == userId);
      if (userProfile == null)
      {
        return NotFound(new { error = "Perfil do usuário não encontrado" });
      }
      return Ok(userProfile);
    }

    [HttpPost]
    public async Task<IActionResult> CreateUserProfile([FromBody] UserProfile userProfile)
    {
      if (_context.UserProfiles.Any(up => up.UserId == userProfile.UserId))
      {
        return BadRequest(new { error = "Perfil do usuário já existe" });
      }
      _context.UserProfiles.Add(userProfile);
      await _context.SaveChangesAsync();
      return CreatedAtAction(nameof(GetUserProfile), new { userId = userProfile.UserId }, userProfile);
    }

    [HttpPut("{userId}")]
    public async Task<IActionResult> UpdateUserProfile(int userId, [FromBody] UserProfile userProfile)
    {
      if (userId != userProfile.UserId)
      {
        return BadRequest(new { error = "ID do usuário não corresponde" });
      }
      _context.Entry(userProfile).State = Microsoft.EntityFrameworkCore.EntityState.Modified;
      await _context.SaveChangesAsync();
      return NoContent();
    }
  }
}
