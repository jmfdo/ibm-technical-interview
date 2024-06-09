# Prueba Técnica IBM - Desarrollador Fullstack

Actualmente la consultora colombian SAS esta llevando un proceso de préstamo de dispositivos electrónicos para las presentaciones de nuevas propuestas (celulares, computadores, proyectores, tablets, entre otros). 
El servicio está cobrando un auge mayor al esperado y actualmente los prestamos se solicitan por chat y el control de préstamos y devoluciones es llevado en una plantilla de Excel.

La empresa ha tomado la decisión de construir un sistema web, que pueda ser altamente escalable y reusado en un futuro para otros desarrollos tipo aplicaciones donde se pueda gestionar el servicio de préstamo de dispositivos.
Lo que se requiere para el sistema es que se tenga una autenticación integrada con el sistema de autorización de sesión actual de la empresa; una vez el usuario se autentica puede tener un Rol de empleado regular o administrador del sistema.

El administrador del sistema puede gestionar el inventario de dispositivos, validar los préstamos que tienen fechas de entrega vencida, generar reportes de los dispositivos más solicitados y personas frecuentes.
El empleado regular debe seguir el mismo proceso de autenticación, dentro del sistema podrá buscar los dispositivos disponibles, podrá solicitar un préstamo de un dispositivo en una fecha futura no menor a dos días y por un periodo no mayor a 12 días hábiles. Un día antes de cumplir la fecha de entrega el sistema deberá notificar por correo electrónico al empleado un recordatorio de la devolución y en caso de pasada la fecha se debe enviar una notificación con el no cumplimiento y el número de días de retraso.

El sistema debe ser desarrollado para una plataforma de contenedores, con un front en angular, un backend en java springboot 17 y una base de datos relacional postgres. Dado el anterior contexto realizar las siguientes propuestas:

1.	Realizar un diseño de base de datos que permita el manejo completo de la aplicación. Definir del diseño cual es la tabla principal y adjuntar el script SQL para su creación.
2.	Realizar una propuesta del modelo de despliegue que se utilizara para la puesta en producción de la aplicación ( modelado de alto nivel de infraestructura)
3.	Adjuntar el dockerfile propuesto para la liberación del Frontend del sistema.
4.	Crear en la tecnología solicitada el servicio Json Rest de Backend para la función préstamo de dispositivos con sus correspondientes validaciones.

5.	Entrevista de sustentación de las decisiones tecnológicas tomadas
