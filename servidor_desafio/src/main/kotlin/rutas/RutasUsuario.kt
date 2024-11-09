package rutas


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Usuario
import modelo.UsuarioDAO
import modelo.UsuarioDAOImpl


val usuarioDAO: UsuarioDAO = UsuarioDAOImpl()

fun Route.rutasUsuario() {
    route("/listado"){
        get{
            if (usuarioDAO.obtenerTodos().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, usuarioDAO.obtenerTodos())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }

        }
        get{
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val usuario = usuarioDAO.obtener(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, usuario)
        }
        route("/login") {
            post{
                val user = call.receive<Usuario>()
                val usuario = usuarioDAO.obtener(user.id) ?: return@post call.respond(HttpStatusCode.NotFound, null)
                if (usuario.clave != user.clave){
                    return@post call.respond(HttpStatusCode.BadRequest, null)
                }
                call.respond(HttpStatusCode.OK, usuario)
            }
        }
        route("/borrar") {
            delete("{id?}") {
                val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

                val usuario = usuarioDAO.obtener(id.toInt()) ?: return@delete call.respond(HttpStatusCode.NotFound, false)

                if (!usuarioDAO.eliminar(id.toInt())){
                    return@delete call.respond(HttpStatusCode.Conflict, false)
                }
                call.respond(HttpStatusCode.Accepted, true)

            }
        }
    }
}

