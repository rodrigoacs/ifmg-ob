using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using backend.Data;
using backend.Models;

namespace backend.Controllers
{
  [Route("api/[controller]")]
  [ApiController]
  public class EventsController(ApplicationDbContext context) : ControllerBase
  {

    // GET: api/Events/user/{userId}
    [HttpGet("user/{userId}")]
    public async Task<ActionResult<IEnumerable<Event>>> GetEventsByUser(int userId)
    {
      var events = await context.Events.Where(e => e.UserId == userId).ToListAsync();
      return Ok(events);
    }

    // GET: api/Events
    [HttpGet]
    public async Task<ActionResult<IEnumerable<Event>>> GetAllEvents()
    {
      var events = await context.Events.ToListAsync();
      return Ok(events);
    }

    // POST: api/Events
    [HttpPost]
    public async Task<IActionResult> CreateEvent(Event newEvent)
    {
      newEvent.ConvertDatesToUtc();

      context.Events.Add(newEvent);
      await context.SaveChangesAsync();

      return Ok(newEvent);
    }

    // PUT: api/Events/{id}
    [HttpPut("{id}")]
    public async Task<IActionResult> UpdateEvent(int id, Event updatedEvent)
    {
      if (id != updatedEvent.Id)
      {
        return BadRequest();
      }

      context.Entry(updatedEvent).State = EntityState.Modified;

      try
      {
        await context.SaveChangesAsync();
      }
      catch (DbUpdateConcurrencyException)
      {
        if (!EventExists(id))
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

    // DELETE: api/Events/{id}
    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteEvent(int id)
    {
      var eventItem = await context.Events.FindAsync(id);
      if (eventItem == null)
      {
        return NotFound();
      }

      context.Events.Remove(eventItem);
      await context.SaveChangesAsync();

      return NoContent();
    }

    private bool EventExists(int id)
    {
      return context.Events.Any(e => e.Id == id);
    }
  }
}
