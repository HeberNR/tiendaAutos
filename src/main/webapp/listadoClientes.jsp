<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file ="header.jsp" %>
<%@ page import = "org.progI.dao.ClienteDAO" %>
<%@ page import = "org.progI.entities.Cliente" %>

<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.List" %>

<!-- abro bloque de declaracion java -->
<%!
    ClienteDAO clienteDAO = new ClienteDAO();
    Cliente cliente = new Cliente();
    List<Cliente> listaClientes = clienteDAO.getAll();
%>



<h2> Listado de Clientes </h2>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Apellido</th>
        <th>Telefono</th>
        <th>Editar</th>
        <th>Borrar</th>
    </tr>
    </thead>
    <tbody>
    <% for(Cliente cli : listaClientes) { %>
           <td> <%=cli.getIdCliente() %> </td>
           <td> <%=cli.getNombre() %> </td>
           <td> <%=cli.getApellido() %> </td>
           <td> <%=cli.getTelefono() %> </td>
           <td> </tr>
           <td> </tr>
    <% } %>
    </tbody>
</table>