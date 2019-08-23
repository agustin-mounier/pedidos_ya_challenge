# pedidos_ya_challenge
Challenge de evaluación para PedidosYa

## Overview
Lenguaje: Kotlin <br>
Patrón de diseño: MVVM <br>
Librerias utilizadas: Dagger2, Retrofit + OkHttp, Realm, Glide, Mockito, Robolectric, PowerMock. <br>
Herramienta de análisis estático de códgio: detekt

## Estructura del proyecto
<pre>
* dagger          Clases requeridas para la injeccion de código con dagger.
* models          Pojo's para modelar información.
* repository      Repositorio que actua como Single Source of Truth de la applicación.
  * local         DAO's para acceder a datos almacenados localmente utilizando Realm.
  * remote        Service's para obtener datos remotamente.
* utils           Clases utilitarias.
* viewmodels      Implementaciones de los ViewModels utilizados
* views           Implementaciones de las vistas utilizadas.
</pre>
## Arquitectura
![arquitectura](https://github.com/agustin-mounier/rappi_test/blob/master/arq.png)

El proyecto fue desarrollado en kotlin utilizando el patrón de diseño MVVM. <br>
Cada una de las capas de arquitectura interactua con otra de forma unilateral siguiendo la "regla de las dependencias" de la arquitectura CLEAN, logrando un mayor desacoplamiento de código. <br> 
El repositorio actua como una Single Source of Truth de la applicación buscando la información necesaria de forma remota o local dependiendo la conectividad disponible del dispositivo.
