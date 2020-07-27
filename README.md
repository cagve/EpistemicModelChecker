<p align="center">
  <img src="readme/banner.png" alt="EMC">
</p>

## Indice

* [Introducción](#introduction)
* [Primeros Pasos](#primeros)
	* [Dependencias](#dependencias)
	 * [Instalación](#install)
 * [Manual de uso](#manual)
	 * [Interfaz](#interfaz)
	  * [Introducción de modelos](#modelo)
	  * [Introducción de fórmulas](#formula)
* [Desarrolladores](#community)
* [Licencia](#license)

## Introducción <a name="introduction"></a>
**EpistemicModelChecker (EMC)** un programa capaz de evaluar el
estatus semántico de una fórmula epistémica en modelos multiagentes. El proceso de evaluación utilizado es el que describe el algoritmo *model checking*. Para el desarrollo del 
programa ha sido esencial el uso de dos librerías; [Tweety project](https://tweetyproject.org/) y [GraphStream](http://graphstream-project.org/). Sin embargo, **EMC** no es sino el
primer paso de un proyecto en desarrollo que pretende mejorar las funcionalidades de dicha herramienta. Es por ello por lo que toda sugerencia, advertencia de bug, etc. Es más que 
bienvenida. Para ello bien se puede contactar a través del [email](#mail) aportado más abajo, así como abriendo una entrada a través del siguiente enlace https://github.com/cagve/EpistemicModelChecker/issues.


## Primeros pasos <a name="primeros"></a>
### Dependencias <a name="dependencias"></a>
Para poder ejecutar **EMC** basta con tener instalado una versión java superior a JAVA 11. La última versión de puede ser descargada en el siguente enlace: [Java 14](https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html). 

Para descargar y compilar el código se requiere además de la última versión de [JavaFX](https://gluonhq.com/products/javafx/)

### Instalación<a name="install"></a>

Para instalar **EMC** basta con descargar el archivo correspondiente para su sistema operativo en el siguiente enlace; [Descargar instaladores](https://github.com/cagve/EpistemicModelChecker/releases/tag/v1).


## Manual de uso <a name="manual"></a>
### Interfaz <a name="interfaz"></a>
<p align="center">
  <img src="readme/interfaz.png" alt="EMC">
</p>

 1. Introducir nuevo modelo
 2. Recargar modelo
 3. Ayuda
 4. Campo de texto donde introducir la fórmula
 5. Ejecutar el verificador
 6. Limpiar interfaz
 7. Área de texto donde se muestra el resultado
 8. Área gráfica donde se muestra el grafo del modelo

### Introducción de modelos <a name="modelo"></a>
La introducción del modelo consiste en la creación de un archivo de texto (txt) en donde se escribe el modelo siguiendo la notación matemática. Aportamos ahora un ejemplo, así como algunas consideraciones a tener en cuenta. 
```
W={w0,w1,w2}
Ra={<w0,w1>,<w2,w2>}
Rc={<w1,w1>,<w2,w1>}
Rb={<w2,w1>,<w0,w0>}
V(p)={w0,w1,w2}
V(q)={w2,w1}
V(r)={w0}
```
* Se pueden crear hasta un máximo de 10 mundos, empezando siempre por w0.
* El número de agentes está restringido a 4; a, b, c y d.
* El dominio de los átomos interpretables por **EMC** es: {p,q,r,s,t,u,v,w,x,y,z}
* Se pueden descargar ejemplos en el siguiente enlace: [ejemplo de modelos](https://github.com/cagve/EpistemicModelChecker/releases/tag/v1) 

### Introducción de fórmulas <a name="formula"></a>
La sintaxis para la introducción de fórmulas es la siguiente. 

| Operador     | Ejemplo notación  clásica  | Ejemplo notación EMC 
 ------------- |:-------------:| :-------------:|
 |Negación* | ¬p | \lnot( p )    
| Conjunción   |  p ∧ q | p \land q |
| Disyunción     | p v q       |   p \lor q |
| Implicación | p → q     |  p \to q |
| Equivalencia | p ↔ q | p \eq q |
| Conocimiento* | K<sub>a</sub>p | Ka( p )|
| Dual del conocimeinto*|  M<sub>a</sub>p | Ma( p ) |

*Es importante recalcar que los operadores monádicos deben escribirse siempre manteniendo los paréntesis que delimiten la fórmula a la que afectan

## Desarrolladores <a name="community"></a>
El proyecto ha sido desarrollado por 
* Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)<a name="mail"></a>
* Alfredo Burrieza Muñiz
* Antonio Yuste Ginel
## Licencia <a name="licence"></a>
EMC está licenciado bajo GNUv3, véase: [LICENCE](https://github.com/CaAgVe/EpistemicModelChecker/blob/Release_1.01/licence) 
