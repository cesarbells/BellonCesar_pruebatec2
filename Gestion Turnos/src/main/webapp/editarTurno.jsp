<%@page import="com.mycompany.gestionturnos.logica.Estado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.mycompany.gestionturnos.logica.Turno"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Turnos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <nav class="navbar navbar-light fixed-top">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Menu Aplicacion</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
      <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasNavbarLabel">Offcanvas</h5>
        <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <div class="offcanvas-body">
        <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="./CrearCiudadano.jsp">Crear Ciudadano</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./crearTurno.jsp">Crear Turno</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./crearFiltro.jsp">Filtro Básico</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="./crearFiltroAvanzado.jsp">Filtro Avanzado</a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</nav>
        <h2>Gestión de Turnos</h2>

        <!-- Mensajes de error o éxito -->
        <div class="mt-3">
            <% 
                String mensaje = (String) request.getAttribute("mensaje");
                if (mensaje != null) {
            %>
                <div class="alert alert-success">
                    <%= mensaje %>
                </div>
            <% 
                }
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <div class="alert alert-danger">
                    <%= error %>
                </div>
            <% 
                }
            %>
        </div>

        <!-- Tabla de Turnos Registrados -->
        <div class="table-responsive mt-4">
            <h4>Lista de Turnos</h4>
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Número de Turno</th>
                        <th>Fecha</th>
                        <th>Descripción</th>
                        <th>Estado</th>
                        <th>Ciudadano</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <% 
                List<Turno> turnos = (List<Turno>) request.getAttribute("turnos");
                if (turnos != null && !turnos.isEmpty()) {
                    for (Turno turno : turnos) {
                %>
                    <tr>
                        <td><%= turno.getId() %></td>
                        <td><%= turno.getNumeroTurno() %></td>
                        <td><%= turno.getFecha() %></td>
                        <td><%= turno.getDescripcion() %></td>
                        <td><%= turno.getEstadoTurno() %></td>
                        <td>
                            <%= turno.getCiudadano() != null 
                                ? turno.getCiudadano().getNombre() + " " + turno.getCiudadano().getApellidos()
                                : "Sin asignar" %>
                        </td>
                        <td>
                            <form action="SvEditarTurno" method="GET" style="display:inline;">
                                <input type="hidden" name="id" value="<%= turno.getId() %>">
                                <button type="submit" class="btn btn-warning btn-sm">Editar</button>
                            </form>
                            <form action="SvEliminarTurno" method="GET" style="display:inline;">
                                <input type="hidden" name="id" value="<%= turno.getId() %>">
                                <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                <% 
                    }
                } else {
                %>
                    <tr>
                        <td colspan="7" class="text-center">No hay turnos registrados.</td>
                    </tr>
                <% 
                }
                %>
                </tbody>
            </table>
        </div>

        <!-- Formulario de Edición -->
        <div class="mt-5">
            <h4>Editar Turno</h4>
            <%
                Turno turno = (Turno) request.getAttribute("turno");
                if (turno != null) {
                    // Formateo de la fecha para mostrarla correctamente en el campo
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String fechaFormateada = "";
                    if (turno.getFecha() != null) {
                        // Convertir LocalDate a java.util.Date
                        java.util.Date fechaDate = java.sql.Date.valueOf(turno.getFecha());
                        fechaFormateada = sdf.format(fechaDate);
                    }
                    request.setAttribute("fechaFormateada", fechaFormateada);
            %>
            <form action="SvEditarTurno" method="POST">
                <input type="hidden" name="id" value="<%= turno.getId() %>" />

                <div class="mb-3">
                    <label for="fecha_turno" class="form-label">Fecha del Turno:</label>
                    <input type="date" class="form-control" id="fecha_turno" name="fecha_turno" value="<%= request.getAttribute("fechaFormateada") %>" >
                </div>

                <div class="mb-3">
                    <label for="numero_turno" class="form-label">Número de Turno:</label>
                    <input type="text" class="form-control" id="numero_turno" name="numero_turno"
                           value="<%= turno.getNumeroTurno() %>" required>
                </div>

                <div class="mb-3">
                    <label for="descripcion_turno" class="form-label">Descripción del Turno:</label>
                    <input type="text" class="form-control" id="descripcion_turno" name="descripcion_turno"
                           value="<%= turno.getDescripcion() %>" required>
                </div>

                <div class="mb-3">
                    <label for="estado_turno" class="form-label">Estado del Turno:</label>
                    <select class="form-select" id="estado_turno" name="estado_turno">
                        <option value="En espera" <%= "En espera".equals(turno.getEstadoTurno()) ? "selected" : "" %>>En espera</option>
                        <option value="Atendido" <%= "Atendido".equals(turno.getEstadoTurno()) ? "selected" : "" %>>Atendido</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="id_ciudadano" class="form-label">ID del Ciudadano:</label>
                    <input type="text" class="form-control" id="id_ciudadano" name="id_ciudadano"
                           value="<%= turno.getCiudadano() != null ? turno.getCiudadano().getId() : "" %>" required>
                </div>

                <button type="submit" class="btn btn-primary">Actualizar Turno</button>
            </form>
            <% } else { %>
                <p class="text-danger">Selecciona un turno para editar.</p>
            <% } %>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


