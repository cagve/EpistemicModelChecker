<p align="center">
  <img src="readme/banner.png" alt="EMC">
</p>

## Index 

* [Introduction](#introduction)
* [First Steps](#primeros)
	* [Dependencies](#dependencias)
    * [Installation](#install)
 * [Use](#manual)
    * [Interface](#interfaz)
    * [Model input](#modelo)
    * [Formula input](#formula)
    * [Example](#ejemplo)
* [Developers](#community)
* [Licence](#license)

## Introduction <a name="introduction"></a>
**EpistemicModelChecker (EMC)** is a software tool for determining the truth value of modal formulas in Kripke-style multi-agent models.
The chosen evaluation process is the one that the *model checking algorithm describe*. During the development of the program, we have use two libraries.;
[Tweety project](https://tweetyproject.org/) y [GraphStream](http://graphstream-project.org/). However, **EMC** is the first step of a biggest project in development which pretends to improve and add new tools to **EMC**. 
Any suggestion, bug detection, etc will be appreciate. You can open an issue in github (https://github.com/cagve/EpistemicModelChecker/issues.) or mail me.

## First steps <a name="primeros"></a>
### Dependencies <a name="dependencias"></a>
To execute **EMC** yo need to haven an older version than JAVA 11.
However if you want to download the code and compile it, you will need the las version of [JavaFX](https://gluonhq.com/products/javafx/)


### Instalation <a name="install"></a>

Download the **EMC** installer for your operating system in: [Downloads](https://github.com/cagve/EpistemicModelChecker/releases/tag/v1).


## Use <a name="manual"></a>
### Interface <a name="interfaz"></a>
<p align="center">
  <img src="readme/interfaz.png" alt="EMC">
</p>

 1. Introduce a new model
 2. Reload the model
 3. Help
 4. Formula field text
 5. Run the checker
 6. Clean the interface
 7. Return area
 8. Graph area 

### Model input <a name="modelo"></a>
To introduce the model you need to create a text file (txt) in which the model is written following the mathematical notation. We provide you an example just as some considerations to be aware of.
```
W={w0,w1,w2}
Ra={<w0,w1>,<w2,w2>}
Rc={<w1,w1>,<w2,w1>}
Rb={<w2,w1>,<w0,w0>}
V(p)={w0,w1,w2}
V(q)={w2,w1}
V(r)={w0}
```
* The maximum worlds that you can create is 10: {w0,w1,w2,w3,w4,w5,w6,w7,w8,w9}. Is important to follow that order.
* You can use only 4 agents: a, b, c y d.
* The domain of the atom set is: {p,q,r,s,t,u,v,w,x,y,z}
* You can download some models example from: [ejemplo de modelos](https://github.com/cagve/EpistemicModelChecker/releases/tag/v1) 

### Formula input <a name="formula"></a>
In the next table you can check the formula's syntaxis.

| Operato | Classic notation  | EMC notation
 ------------- |:-------------:| :-------------:|
| Negation* | ¬p | \lnot( p )    
| Conjunction |  p ∧ q | p \land q |
| Disjunction | p v q       |   p \lor q |
| Implication | p → q     |  p \to q |
| Biconditional | p ↔ q | p \eq q |
| Knowledge* | K<sub>a</sub>p | Ka( p )|
| Dual of knowledge*|  M<sub>a</sub>p | Ma( p ) |

*The monadic operator must be written with the parenthesis 

#### Example
<p align="center">
  <img src="readme/corte.gif" alt="EMC">
</p>

## Developers <a name="community"></a>
Thisn projet has been developed by;
* Carlos Aguilera Ventura (carlos.aguilera13416@gmail.com)<a name="mail"></a>
* Alfredo Burrieza Muñiz
* Antonio Yuste Ginel
## Licencia <a name="licence"></a>
EMC is licensed by GNUv3 : [LICENCE](https://github.com/CaAgVe/EpistemicModelChecker/blob/Release_1.01/licence) 
