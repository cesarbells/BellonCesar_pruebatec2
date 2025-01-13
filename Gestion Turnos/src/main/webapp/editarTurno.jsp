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
      

       

        <!-- Formulario de Edición -->
        <div class="mt-3">
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


