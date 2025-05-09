1.- Abrir Proyecto con Intellij
2.- Adjunto en el correo la colleción POSTMAN.
     - Tiene dos endpoint:
        http://localhost:8080/add  (crea usuario)
          2.1.- valida email que no se encuentre en la base de datos.
          
        http://localhost:8080/user/1 (buscar usuario por ID)
     


Importante: Archivo application.properties
      jwt.secret=..... 
      jwt.expiration=3600000
      #1 Hora expiracion

      y ademas estan las variables de la base de datos en memoria.


