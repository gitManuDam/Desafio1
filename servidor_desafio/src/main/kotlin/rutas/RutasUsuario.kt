package rutas


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Usuario
import modelo.UsuarioDAO
import modelo.UsuarioDAOImpl
import modelo.UsuarioLogIn


val usuarioDAO: UsuarioDAO = UsuarioDAOImpl()

fun Route.rutasUsuario() {
    route("/listarUsuario") {
        get {
            if (usuarioDAO.obtenerTodos().isNotEmpty()) {
                return@get call.respond(HttpStatusCode.OK, usuarioDAO.obtenerTodos())
            } else {
                return@get call.respond(HttpStatusCode.NotFound, null)
            }

        }
        get {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest, null)

            val usuario =
                usuarioDAO.obtenerUsuarioPorId(id.toInt()) ?: return@get call.respond(HttpStatusCode.NotFound, null)

            call.respond(HttpStatusCode.OK, usuario)
        }
    }
    route("/loginUsuario") {
        post{
            val user = call.receive<UsuarioLogIn>()
            val usuario = usuarioDAO.obtenerUsuarioPorNombre(user.nombre) ?: return@post call.respond(HttpStatusCode.NotFound, null)
            if (usuario.clave != user.clave){
                return@post call.respond(HttpStatusCode.BadRequest, null)
            }
            call.respond(HttpStatusCode.OK, usuario)
        }
    }
    route("/borrarUsuario") {
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, false)

            val usuario = usuarioDAO.obtenerUsuarioPorId(id.toInt()) ?: return@delete call.respond(HttpStatusCode.NotFound, false)

            if (!usuarioDAO.eliminar(id.toInt())){
                return@delete call.respond(HttpStatusCode.Conflict, false)
            }
            call.respond(HttpStatusCode.Accepted, true)

        }
    }
    route("/insertarUsuario"){
        post {
            val user = call.receive<Usuario>()
            val usuario = usuarioDAO.obtenerUsuarioPorNombre(user.nombre)
            if(usuario != null){
                return@post call.respond(HttpStatusCode.Conflict, null)
            }
            if(!usuarioDAO.insertar(user)){
                return@post call.respond(HttpStatusCode.Conflict, null)
            }
            return@post call.respond(HttpStatusCode.Created, usuario)
        }

    }



}

