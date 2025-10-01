<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file ="header.jsp" %>

<jsp:useBean id="auto" class="org.progI.entities.Auto" scope="request" />
<jsp:useBean id="AutoImpl" class="org.progI.dao.AutoImpl" scope="page" />

<%
    org.progI.entities.Cliente clienteAsociado = null;
    if (session.getAttribute("clienteTemporal") != null) {
        clienteAsociado = (org.progI.entities.Cliente) session.getAttribute("clienteTemporal");
    }
%>

<%--
    2. LÓGICA DE EDICIÓN (para el Auto, no para el Cliente)
    Aquí necesitarías un 'AutoDAO' para traer el auto a editar, pero lo simplificaremos.
    Asumimos que solo estamos en modo 'nuevo' o 'editar' auto.
--%>
<c:if test = "${param.operacion == 'editar'}">
    <%-- Asumiendo que existe un AutoDAO y Auto, y que pasas el 'idAuto' --%>
    <%-- <jsp:useBean id="autoDAO" class="org.progI.dao.AutoImpl" scope="page" /> --%>
    <%-- <c:set var = "idAuto" value = "${Integer.parseInt(param.id)}" /> --%>
    <%-- <c:set var = "autoEditar" value = "${autoDAO.getById(idAuto)}" /> --%>
</c:if>

        <c:choose>
            <c:when test = "${param.operacion == 'editar'}"> Editar Auto </c:when>
            <c:when test = "${param.operacion == 'eliminar'}"> Eliminar Auto </c:when>
            <c:otherwise> Nuevo Auto </c:otherwise>
        </c:choose>

        <h2>Formulario de Auto</h2>

<%-- 3. MOSTRAR EL CLIENTE ASOCIADO --%>
<% if (clienteAsociado != null) { %>
    <div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 20px;">
        **Cliente Asociado:** <%= clienteAsociado.getNombre() %> <%= clienteAsociado.getApellido() %> (ID: <%= clienteAsociado.getIdCliente() %>)
    </div>
<% } else if ("nuevo".equals(param.operacion)) { %>
    <p style="color: red;">Error: No se encontró el cliente en la sesión. La creación fallará.</p>
<% } %>


        <form action = "AutoServlet" method = "GET">

        <input type = "hidden" name = "txtId" value = "${not empty autoEditar.idAuto ? autoEditar.idAuto : -1}" />
        <input type = "hidden" name = "operacion" id = "operacion" value = "${param.operacion == 'editar' ? 'editar' : 'nuevo'}" />

        <label for ="txtPatente"> Patente </label>
        <input type = "text" name = "txtPatente" id = "txtPatente" placeholder = "Patente"
        value = "${not empty autoEditar.patente ? autoEditar.patente : ''}" required />
        <br>

        <label for ="txtColor"> Color </label>
        <input type = "text" name = "txtColor" id = "txtColor" placeholder = "Color"
        value = "${not empty autoEditar.color ? autoEditar.color : ''}" required />
        <br>

        <label for ="txtAnio"> Año </label>
        <input type = "number" name = "txtAnio" id = "txtAnio" placeholder = "Año"
        value = "${not empty autoEditar.anio ? autoEditar.anio : ''}" required />
        <br>

        <label for ="txtKilometraje"> Kilometraje </label>
        <input type = "number" name = "txtKilometraje" id = "txtKilometraje" placeholder = "Kilometraje"
        value = "${not empty autoEditar.kilometraje ? autoEditar.kilometraje : ''}" required />
        <br>

        <label for ="txtMarca"> Marca </label>
        <select name = "txtMarca" id = "txtMarca" required>
            <option value="FORD">Ford</option>
            <option value="CHEVROLET">Chevrolet</option>
            <option value="TOYOTA">Toyota</option>
            <%-- Asegúrate de que los values coincidan con tus ENUM de Marca --%>
        </select>
        <br>

        <label for ="txtModelo"> Modelo </label>
        <input type = "text" name = "txtModelo" id = "txtModelo" placeholder = "Modelo"
        value = "${not empty autoEditar.modelo ? autoEditar.modelo : ''}" required />
        <br>

        <input type = "submit" value = "Enviar" />
        </form>

<%@ include file ="footer.jsp" %>
    </body>
</html>