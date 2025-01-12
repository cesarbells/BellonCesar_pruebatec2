<%@page import="java.util.List"%>
<%@page import="com.mycompany.gestionturnos.logica.Ciudadano"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Ciudadano</title>
    <!-- Bootstrap 5 CSS -->
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
        <h2>Editar Ciudadano</h2>
        
        <% 
            Ciudadano ciudadano = (Ciudadano) request.getAttribute("ciudadano");
            List<Ciudadano> ciudadanos = (List<Ciudadano>) request.getAttribute("ciudadanos");
        %>

        <!-- Formulario de edición -->
        <form action="SvEditarCiudadano" method="POST">
            <input type="hidden" name="id" value="<%= ciudadano.getId() %>" />
            
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="nombre_ciudadano">Nombre:</label>
                        <input type="text" class="form-control" id="nombre_ciudadano" name="nombre_ciudadano" 
                               value="<%= ciudadano.getNombre() %>" required>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="apellidos_ciudadano">Apellidos:</label>
                        <input type="text" class="form-control" id="apellidos_ciudadano" name="apellidos_ciudadano"
                               value="<%= ciudadano.getApellidos() %>" required>
                    </div>
                </div>
            </div>
            
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="dni_ciudadano">DNI:</label>
                        <input type="text" class="form-control" id="dni_ciudadano" name="dni_ciudadano"
                               value="<%= ciudadano.getDni() %>" required>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="telefono_ciudadano">Teléfono:</label>
                        <input type="text" class="form-control" id="telefono_ciudadano" name="telefono_ciudadano"
                               value="<%= ciudadano.getTelefono() %>" required>
                    </div>
                </div>
            </div>
            
            <button type="submit" class="btn btn-primary mt-3">Actualizar Ciudadano</button>
        </form>

       
    <!-- Bootstrap 5 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

