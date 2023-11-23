# ConsultasHospitalJava

- Es un proyecto echo en Java que realiza una conexion a una base de datos mysql para hacer consultas del tipo de:
- Ver hospitales.
- Ver los equipos de los hospitales.
- Ver Gfh.
- Podemos cargar datos desde archivos excel a la base de datos.
- Podemos exportar los datos de las tablas de la base de datos a un archivo excel.
- Podemos ejecutar una consulta que escribamos en un campo, ver su resultado y sacar los datos que nos salieron a un archivo excel.

# Preparación

- Para poder ver el proyecto haremos lo siguiente:

1. Desde el repositorio github del proyecto pulsaremos en el boton Code y copiaremos la url que nos da.
2. Descargaremos el visual studio code, el ide que usaremos para este proyecto en la siguiente url:https://code.visualstudio.com/download, daremos al boton de Windows Windows 10,11(si usamos el sistema operativo windows).
3. Iremos a donde descargó el instalador del visual studio code(VSCodeUserSetup-x64-1.84.2.exe) y lo ejecutaremos para instalarlo,seguiremos todos los pasos que nos dice para instalarlo y dejaremos todas las opciones por defecto.
4. Abrimos el ide visual studio code ,dentro del abriremos una terminal y pondremos el comando git clone url(la url que copiamos del github)(con espacio entre clone y la url copiada) para así descargar el proyecto.
5. En el terminal pondremos cd nombre_proyecto_descargado(con espacio entre cd y el nombre del proyecto) para estar en la ruta del proyecto o cerramos el ide y lo abrimos en la carpeta del proyecto descargado.
6. Tenemos que tener instaladas las siguientes extensiones del visual studio code para ver el proyecto:
   - Extensión Pack for Java.
   - Debugger for Java.
   - Test Runner for Java.
   - Marven for Java.
   - Proyect Manager for Java.

- Si no las tenemos instaladas vamos a extensiones y las instalamos(es el icono del cuadrado con una sección del cuadrado hacia fuera).

7. Vamos a la pestaña del Explorador llamada JAVA PROJECTS, si no aparece el proyecto le damos al boton + y buscamos su ruta para que aparecezca.(Si no aparece la pestaña abrimos cualquier archivo .java del projecto para que aparezca)
8. En la Sección Referenced Libraries, le damos a la flecha de la izquierda para abrir la sección,pulsamos el boton más y le añadimos la ruta de todos los archivos del proyecto que contienen las carpetas lib y excel, uno por uno o selecionando varios.

# Visualización

- Para ver este proyecto haremos lo siguiente.

1. Abriremos el archivo Menu1.java.
2. Pulsaremos el boton que es una flecha arriba a la derecha, el que pone Run Java si dejas el raton encima.
3. Ahora podremos ver como funciona el proyecto.

# Base de Datos

- La estructura de la base de datos es la siguiente:

## Nombre: hospital

### Tablas:

#### hospitales:

##### Campos:

- Codigo VARCHAR(5) PRIMARY KEY.
- Nombre VARCHAR(9) NOT NULL.

#### gfh:

##### Campos:

- CodiHospi VARCHAR(5) NOT NULL.
- Codigo VARCHAR(5) NOT NULL.
- Nombre VARCHAR(80) NOT NULL.
- PRIMARY KEYS(CodiHospi,Codigo).
- FOREIGN KEY CodiHospi(tabla Hospitales campo Codigo).

#### equipos:

##### Campos:

- idEquipo INT AUTO_INCREMENT PRIMARY KEY.
- CodiHospi VARCHAR(6) NOT NULL FOREIGN KEY(tabla hospitales campo Codigo).
- Codigo VARCHAR(12) NOT NULL.
- Nombre VARCHAR(150) NOT NULL.
- Marca VARCHAR(50) NOT NULL.
- Modelo VARCHAR(50) NOT NULL.
- Serie VARCHAR(25) NOT NULL.
- PrecioCompra FLOAT NOT NULL.
- FechaHoraAlta datetime NOT NULL.
- CodigoGfh VARCHAR(10) NOT NULL FOREIGH KEY(tabla gfh campo Codigo).
