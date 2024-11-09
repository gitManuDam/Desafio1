package modelo

interface UsuarioDAO {
    fun insertar(usuario: Usuario): Boolean
    fun obtener(id: Int): Usuario?
    fun cambiarClave(usuario: Usuario, nuevaClave:String): Boolean
    fun eliminar(id: Int): Boolean
    fun obtenerTodos(): List<Usuario>
}