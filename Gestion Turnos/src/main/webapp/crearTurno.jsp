<%@page import="com.mycompany.gestionturnos.logica.Estado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.mycompany.gestionturnos.logica.Turno"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
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
        <!-- Formulario de Turnos -->
        <form action="SvCrearTurno" method="POST">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="numeroTurno">Número de Turno:</label>
                        <input type="text" class="form-control" id="numeroTurno" name="numero_turno">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="fecha">Fecha:</label>
                        <input type="date" class="form-control" id="fecha" name="fecha_turno">
                    </div>
                </div>
            </div>
            <div class="form-group mt-3">
                <label for="descripcion">Descripción:</label>
                <input type="text" class="form-control" id="descripcion" name="descripcion_turno">
            </div>
            <div class="form-group mt-3">
                <label for="estadoTurno">Estado del Turno:</label>
              <select id="estadoTurno" name="estado_turno" class="form-control">
    <%
        // Obtener los valores del enum Estado
        Estado[] estados = Estado.values();
        // Iterar sobre los valores del enum para generar las opciones del select
        for (Estado estado : estados) {
    %>
        <option value="<%= estado.toString() %>"><%= estado.toString()%></option>
    <%
        }
    %>
</select>
    
            </div>
            <div class="form-group mt-3">
                <label for="idCiudadano">ID del Ciudadano:</label>
                <input type="number" class="form-control" id="idCiudadano" name="id_ciudadano">
            </div>

            <div class="btn-group-custom mt-4">
                <button type="submit" class="btn btn-primary">Guardar Turno</button>
                <button type="submit" formaction="SvCrearTurno" formmethod="GET" class="btn btn-secondary">Ver Turnos</button>
            </div>
        </form>

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
        <div class="table-responsive">
            <table class="table table-striped table-bordered mt-4">
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
                                <!-- <input type="hidden" name="tipo" value="turno"> -->
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
    </div>

    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

