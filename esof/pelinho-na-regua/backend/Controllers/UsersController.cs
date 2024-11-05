using Microsoft.AspNetCore.Mvc;
using backend.Data;
using backend.Models;

namespace backend.Controllers
{
  [Route("api/[controller]")]
  [ApiController]
  public class UsersController(ApplicationDbContext context) : ControllerBase
  {
    private readonly ApplicationDbContext _context = context;

    [HttpPost("register")]
    public async Task<IActionResult> Register(User user)
    {
      if (_context.Users.Any(u => u.Username == user.Username))
      {
        return BadRequest(new { error = "Nome de usuario já cadastrado" });
      }

      // Hash the password before saving
      user.Password = BCrypt.Net.BCrypt.HashPassword(user.Password);

      _context.Users.Add(user);
      await _context.SaveChangesAsync();

      return Ok(new { message = "Usuario cadastrado com sucesso" });
    }

    [HttpPost("login")]
    public IActionResult Login([FromBody] User user)
    {
      var existingUser = _context.Users.FirstOrDefault(u => u.Username == user.Username);
      if (existingUser == null || !BCrypt.Net.BCrypt.Verify(user.Password, existingUser.Password))
      {
        return Unauthorized(new { error = "Usuario ou senha inválidos" });
      }

      return Ok(new { message = "Logado com sucesso", id = existingUser.Id });
    }

  }
}
