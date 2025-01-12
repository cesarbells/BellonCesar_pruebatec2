<%@ page import="com.mycompany.gestionturnos.logica.Estado" %>
<%@ page import="com.mycompany.gestionturnos.logica.Turno" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Turnos</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .btn-group-custom {
            display: flex;
            justify-content: center;
            gap: 10px;
        }
        .table-responsive {
            margin-top: 20px;
        }
    </style>
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
    <!-- Formulario de Filtrado -->
    <form action="SvCrearFiltro" method="GET" class="mb-4">
        <div class="row">
            <div class="col-md-6">
                <label for="fecha" class="form-label">Filtrar por Fecha:</label>
                <input type="date" class="form-control" id="fecha" name="fecha">
            </div>
            <div class="col-md-6 d-flex align-items-end">
                <div class="btn-group-custom">
                    <button type="submit" class="btn btn-primary">Filtrar</button>
             
                </div>
                
            </div>
        </div>
    </form>
    <!-- Botón para mostrar todos los turnos -->
<form action="SvCrearFiltro" method="get">
    <input type="submit" value="Ver todos" />
</form>

    <!-- Mensajes de error o éxito -->
    <div>
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
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Número de Turno</th>
                    <th>Fecha</th>
                    <th>Descripción</th>
                    <th>Estado</th>
                    <th>Ciudadano</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Turno> turnos = (List<Turno>) request.getAttribute("turnos"); // Recuperar la lista de turnos
                    if (turnos != null && !turnos.isEmpty()) {
                        for (Turno turno : turnos) {
                %>
                <tr>
                    <td><%= turno.getId() %></td>
                    <td><%= turno.getNumeroTurno() %></td>
                    <td><%= turno.getFecha() %></td>
                    <td><%= turno.getDescripcion() %></td>
                    <td><%= turno.getEstadoTurno().toString() %></td>
                    <td><%= turno.getCiudadano() != null ? turno.getCiudadano().getNombre() : "N/A" %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="text-center">No hay turnos disponibles.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<!-- Bootstrap 5 JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

