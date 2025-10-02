<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file ="header.jsp" %>

<jsp:useBean id="auto" class="org.progI.entities.Auto" scope="request" />
<jsp:useBean id="autoImpl" class="org.progI.dao.AutoImpl" scope="page" />

<c:if test = "${param.operacion == 'editar'}">
    <c:set var = "idAuto" value = "${Integer.parseInt(param.id)}" />
    <c:set var = "autoEditar" value = "${autoImpl.getById(idAuto)}" />
    <c:set var="listaAutos" value="${autoImpl.getAll()}" />
</c:if>

<h2>
<c:choose>
    <c:when test = "${param.operacion == 'editar'}"> Editar Auto </c:when>
    <c:when test = "${param.operacion == 'eliminar'}"> Eliminar Auto </c:when>
    <c:otherwise> Nuevo Auto </c:otherwise>
</c:choose>
</h2>

<form action = "AutoServlet" method = "GET">

    <label for = "selectAuto"> Seleccionar Auto </label>
    <select name = "listAuto" id = "listAuto" tabindex = "1">
        <c:forEach var = "a" items = "${listaAutos}">
            <option value = "${a.idAuto}">
                <c:if test = "${autoEditar.idAuto == a.idAuto}">selected</c:if>
                ${a.marca} ${a.modelo}
            </option>
        </c:forEach>
    </select>

    <br>

    <input type = "hidden" name = "txtId"
        value = "${not empty autoEditar.idAuto ? autoEditar.idAuto : -1}"
    />

    <input type = "hidden" name = "operacion" id = "operacion"
        value = "${param.operacion == 'editar' ? 'editar' : 'nuevo'}"
    />

    <label for ="txtPatente"> Patente </label>
    <input type = "text" name = "txtPatente" id = "txtPatente" placeholder = "Patente"
        value = "${not empty autoEditar.patente ? autoEditar.patente : ''}"
    required />

    <br>

    <label for = "txtColor"> Color </label>
    <input type = "text" name = "txtColor" id = "txtColor" placeholder = "Color"
        value = "${not empty autoEditar.color ? autoEditar.color : ''}"
    required />

    <br>

    <label for ="txtAnio"> Año </label>
    <input type = "text" name = "txtAnio" id = "txtAnio" placeholder = "Año"
        value = "${not empty autoEditar.anio ? autoEditar.anio : ''}"
    required />

    <br>

    <label for ="txtKilometraje"> Kilometraje </label>
    <input type = "text" name = "txtKilometraje" id = "txtKilometraje" placeholder = "Kilometraje"
        value = "${not empty autoEditar.kilometraje ? autoEditar.kilometraje : ''}"
    required />

    <br>

    <label for = "txtMarca"> Marca </label>
    <input type = "text" name = "txtMarca" id = "txtMarca" placeholder = "Marca"
        value = "${not empty autoEditar.marca ? autoEditar.marca : ''}"
    required />

    <br>

    <label for = "txtModelo"> Modelo </label>
    <input type = "text" name = "txtModelo" id = "txtModelo" placeholder = "Modelo"
        value = "${not empty autoEditar.modelo ? autoEditar.modelo : ''}"
    required />

    <input type = "submit" value = "Enviar" />
</form>

<a href="index.jsp"> Ir a Inicio </a>

<%@ include file ="footer.jsp" %>
</body>
</html>