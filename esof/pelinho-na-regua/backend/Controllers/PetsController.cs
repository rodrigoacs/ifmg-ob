using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using backend.Data;
using backend.Models;

namespace backend.Controllers
{
  [Route("api/[controller]")]
  [ApiController]
  public class PetsController(ApplicationDbContext context) : ControllerBase
  {

    // GET: api/Pets/user/{userId}
    [HttpGet("user/{userId}")]
    public async Task<ActionResult<IEnumerable<Pet>>> GetPetsByUser(int userId)
    {
      var pets = await context.Pets.Where(p => p.UserId == userId).ToListAsync();
      return Ok(pets);
    }

    // POST: api/Pets
    [HttpPost]
    public async Task<IActionResult> AddPet(Pet pet)
    {
      if (pet.UserId == 0)
      {
        return BadRequest("UserId is required.");
      }

      pet.ConvertDatesToUtc();

      context.Pets.Add(pet);
      await context.SaveChangesAsync();

      return Ok(pet);
    }

    // PUT: api/Pets/{id}
    [HttpPut("{id}")]
    public async Task<IActionResult> UpdatePet(int id, Pet pet)
    {
      if (id != pet.Id)
      {
        return BadRequest();
      }

      context.Entry(pet).State = EntityState.Modified;

      try
      {
        await context.SaveChangesAsync();
      }
      catch (DbUpdateConcurrencyException)
      {
        if (!PetExists(id))
        {
          return NotFound();
        }
        else
        {
          throw;
        }
      }

      return NoContent();
    }

    // DELETE: api/Pets/{id}
    [HttpDelete("{id}")]
    public async Task<IActionResult> DeletePet(int id)
    {
      var pet = await context.Pets.FindAsync(id);
      if (pet == null)
      {
        return NotFound();
      }

      context.Pets.Remove(pet);
      await context.SaveChangesAsync();

      return NoContent();
    }

    private bool PetExists(int id)
    {
      return context.Pets.Any(e => e.Id == id);
    }
  }
}
