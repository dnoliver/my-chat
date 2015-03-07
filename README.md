# my-chat

El siguiente documento tiene como finalidad dejar claramente especificados las condiciones 
con las cuales el alumno puede presentarse a rendir el examen final.

## Respecto al software


Del caso de estudio “Chat” planteado durante el cursado de la materia, 
se requiere desarrollar obligatoriamente los siguientes casos de uso:

### VISITANTE

1.	Registrar usuario

### USUARIO REGISTRADO

1.	Loguear usuario
2.	Validar usuario
3.	Desloguear usuario
4.	Mostrar salas activas
5.	Ingresar a sala para participar
6.	Mostrar usuarios activos de la sala
7.	Invitar usuario a sala privada
8.	Responder invitación
9.	Aceptar invitación
10.	Rechazar invitación
11.	Enviar mensaje
12.	Salir de sala

### ADMINISTRADOR

1.	Loguear usuario
2.	Validar usuario
3.	Desloguear usuario
4.	Mostrar salas activas
5.	Ingresar a sala para administrar
6.	Mostrar usuarios activos de la sala
7.	Eliminar mensaje de sala pública
8.	Expulsar usuario de sala pública

### SISTEMA

1.	Dar de baja usuarios inactivos
2.	Refrescar salas
  1.	Refrescar mensajes
  2.	Refrescar usuarios
  3.	Refrescar lista de salas
4.	Mostrar invitaciones pendientes de respuesta

De la lista anterior, deben incluirse todos los casos de uso asociados para completitud del caso de estudio.

El software deberá implementarse usando el entorno de trabajo desarrollado durante el cursado de la materia. 
No se permitirá el uso de scriptlets y menos utilizar la librería SQL de JSTL para procesar los datos.

El motor de base de datos a utilizar será SQL Server 2008 o superior.
El acceso a los datos deberá realizarse a través de Servicios REST considerando la posibilidad de que la 
aplicación sea implementada también en una plataforma móvil.

## Respecto a la documentación
Se deberá completar con la documentación de los casos de uso no obligatorios y las correcciones realizadas 
durante la revisión para la regularización de la materia. 
A su vez, se deberá entregar los diagramas de implementación del proyecto teniendo en cuenta todos los módulos 
que deberían desarrollarse para la entrega del software final y no solo los solicitados para la aprobación de la materia.

### Antes del examen final
El alumno deberá presentarse 2 semanas antes del examen final para revisar el software.

### Durante el examen final
Se realizará un cruce de información entre la documentación entrega y el software desarrollado para determinar 
la coherencia entre ambos, el caso de uso a tomar será definido en ese momento. A su vez, se realizaran preguntas 
teóricas sobre la arquitectura utilizada.

Posteriormente, una vez finalizada la revisión del software, se asignará una tarea de modificación sobre el 
programa o la definición de un caso a resolver en el momento que puedo o no haber sido contemplado en el caso de 
estudio y puedo o no corresponderse con el proyecto.
