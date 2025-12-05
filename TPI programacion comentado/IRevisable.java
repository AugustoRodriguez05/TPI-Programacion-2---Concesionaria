// INTERFACE: Define un "Contrato".
// Cualquier clase que implemente esto, PROMETE tener estos métodos.
public interface IRevisable {
    boolean necesitaMantenimiento(); // Método abstracto (sin cuerpo)
    void realizarMantenimiento();    // Método abstracto
}