using Microsoft.AspNetCore.Mvc;
using backend.Data;
using backend.Models;
using Microsoft.EntityFrameworkCore;

namespace backend.Controllers
{
  [ApiController]
  [Route("api/[controller]")]
  public class ProductsController(ApplicationDbContext context) : ControllerBase
  {
    [HttpGet]
    public async Task<IActionResult> GetProducts()
    {
      var products = await context.Products.ToListAsync();
      return Ok(products);
    }

    [HttpGet("{id}")]
    public async Task<IActionResult> GetProduct(int id)
    {
      var product = await context.Products.FindAsync(id);
      if (product == null)
      {
        return NotFound();
      }
      return Ok(product);
    }

    [HttpPost]
    public async Task<IActionResult> AddProduct(Product product)
    {
      if (product.DiscountPercentage.HasValue)
      {
        product.DiscountedPrice = CalculateDiscountedPrice(product.OriginalPrice, product.DiscountPercentage.Value);
      }
      context.Products.Add(product);
      await context.SaveChangesAsync();
      return CreatedAtAction(nameof(GetProduct), new { id = product.Id }, product);
    }

    [HttpPut("{id}")]
    public async Task<IActionResult> UpdateProduct(int id, Product product)
    {
      if (id != product.Id)
      {
        return BadRequest();
      }

      if (product.DiscountPercentage.HasValue)
      {
        product.DiscountedPrice = CalculateDiscountedPrice(product.OriginalPrice, product.DiscountPercentage.Value);
      }

      context.Entry(product).State = EntityState.Modified;

      try
      {
        await context.SaveChangesAsync();
      }
      catch (DbUpdateConcurrencyException)
      {
        if (!ProductExists(id))
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

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteProduct(int id)
    {
      var product = await context.Products.FindAsync(id);
      if (product == null)
      {
        return NotFound();
      }

      context.Products.Remove(product);
      await context.SaveChangesAsync();

      return NoContent();
    }

    private bool ProductExists(int id)
    {
      return context.Products.Any(e => e.Id == id);
    }

    private static decimal CalculateDiscountedPrice(decimal originalPrice, decimal discountPercentage)
    {
      return originalPrice - (originalPrice * discountPercentage / 100);
    }
  }
}
