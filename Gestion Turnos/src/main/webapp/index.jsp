<%@page import="com.mycompany.gestionturnos.logica.Ciudadano"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Ciudadanos</title>
    <!-- Importa los estilos de Bootstrap 5 para una interfaz moderna y responsiva -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Estilos personalizados para la página */
        .btn-group-custom {
            display: flex;
            justify-content: center;
            gap: 10px;
        }
        .btn-group-spacing {
            margin-top: 20px;
        }
        .table-responsive {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <!-- Navbar -->
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

        <!-- Formulario para crear o actualizar un ciudadano -->
        <form action="SvCrearCiudadano" method="POST">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="nombre_ciudadano">Nombre:</label>
                        <!-- Campo para ingresar el nombre, relleno con datos del request si existen -->
                        <input type="text" class="form-control" id="nombre_ciudadano" name="nombre_ciudadano"
                               value="<%= request.getAttribute("ciudadano") != null ? ((Ciudadano) request.getAttribute("ciudadano")).getNombre() : "" %>">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="apellidos_ciudadano">Apellidos:</label>
                        <!-- Campo para ingresar los apellidos -->
                        <input type="text" class="form-control" id="apellidos_ciudadano" name="apellidos_ciudadano"
                               value="<%= request.getAttribute("ciudadano") != null ? ((Ciudadano) request.getAttribute("ciudadano")).getApellidos() : "" %>">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="dni_ciudadano">DNI:</label>
                        <!-- Campo para ingresar el DNI -->
                        <input type="text" class="form-control" id="dni_ciudadano" name="dni_ciudadano"
                               value="<%= request.getAttribute("ciudadano") != null ? ((Ciudadano) request.getAttribute("ciudadano")).getDni() : "" %>">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="telefono_ciudadano">Teléfono:</label>
                        <!-- Campo para ingresar el teléfono -->
                        <input type="text" class="form-control" id="telefono_ciudadano" name="telefono_ciudadano"
                               value="<%= request.getAttribute("ciudadano") != null ? ((Ciudadano) request.getAttribute("ciudadano")).getTelefono() : "" %>">
                    </div>
                </div>
            </div>

            <!-- Botones de acción -->
            <div class="btn-group-custom mt-4">
                <!-- Botón para guardar el ciudadano -->
                <button type="submit" class="btn btn-primary">Guardar Ciudadano</button>
                <!-- Botón para ver la lista de ciudadanos -->
                <button type="submit" formaction="SvCrearCiudadano" formmethod="GET" class="btn btn-secondary">Ver Ciudadanos</button>
            </div>
        </form>

        <!-- Tabla para mostrar la lista de ciudadanos -->
        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Nombre</th>
                        <th>Apellidos</th>
                        <th>DNI</th>
                        <th>Teléfono</th>
                        <th>Acciones</th> <!-- Nueva columna para las acciones -->
                    </tr>
                </thead>
                <tbody>
                   <% 
                   // Obtiene la lista de ciudadanos del request
                   List<Ciudadano> ciudadanos = (List<Ciudadano>) request.getAttribute("ciudadanos");
                   if (ciudadanos != null) {
                       // Itera sobre cada ciudadano y genera una fila en la tabla
                       for (Ciudadano ciudadano : ciudadanos) { %>
                           <tr>
                               <td><%= ciudadano.getId() %></td>
                               <td><%= ciudadano.getNombre() %></td>
                               <td><%= ciudadano.getApellidos() %></td>
                               <td><%= ciudadano.getDni() %></td>
                               <td><%= ciudadano.getTelefono() %></td>
                               <td>
                                   <!-- Botones para editar o eliminar al ciudadano -->
                                   <a href="SvEliminarCiudadano?id=<%= ciudadano.getId() %>" class="btn btn-danger">Eliminar</a>
                                   <a href="SvEditarCiudadano?id=<%= ciudadano.getId() %>" class="btn btn-warning">Editar</a>
                               </td>
                           </tr>
                   <%     }
                   } else { %>
                       <!-- Mensaje si no hay ciudadanos registrados -->
                       <tr>
                           <td colspan="6" class="text-center">No hay ciudadanos registrados.</td>
                       </tr>
                   <% } %>
                </tbody>
            </table>
        </div>

        <!-- Botón para volver al formulario de alta -->
        <div class="btn-group-custom mt-4">
            <a href="crearCiudadano.jsp" class="btn btn-primary">Volver al formulario de alta</a>
        </div>
    </div>

    <!-- Scripts de Bootstrap para funcionalidades interactivas -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

