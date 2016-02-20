# Yaes 
The framework of Yaes (pronounced as "yes") - Yet Another Extensible Simulator, has been developed by [Dr Ladislau Boloni](http://www.eecs.ucf.edu/~lboloni/). More closely it is a Java library which offers a number of abstractions which allows its users to quickly and easily assemble simulators for a large number of problems.


## What is YAES?
> YAES (pronounced as "yes") is Yet Another Extensible Simulator. More closely it is a Java library which offers a number of abstractions which allows its users to quickly and easily assemble simulators for a large number of problems. 

## Tell me more about the abstractions!
> Efficient simulation is frequently learning about what not to simulate. If one attempts to simulate everything, one will soon end up like Borges' mythical country, where the geographical sciences had such a formidable evolution, that they built a map of the country which covered the country itself. Thus most of the things offered by YAES are interfaces, abstract classes or very generic implementations. For instance, you will find an abstract implementation of generic algorithms, or a generic A* search. These abstract implementations might not be the most optimal solutions for any particular case, as the very generic assumptions on the data types preclude domain specific optimizations. But they allow a user to try out various algorithms in their simulations to compare their properties - which is, many times, the main reason to simulate.	 	Why doesn't have a scripting language?
The simple answer is, because it would require the user to learn a second language, and a lot of tedious details about interfacing them. We found that, in practice, there is no significant difference between the compilation time of Java vs. the startup times of interpreters. If somebody needs a scripting environment, one can easily run JAES from the Jython prompt, for example. But we would advise against creating new abstractions in an external language, except in cases when there are overwhelming reasons for it: such as, when there is a need to interface with external legacy software. 

## So, what does YAES offer currently?
> As of version 0.0.1 YAES offers the following abstractions
* Two-dimensional abstract maps.
* Newtonian physical model of two dimensional movement.
* Basic search algorithms (depth-, breadth-, best-first, A*).
* Basic path-planning algorithms
* Basic genetic algorithm framework
* Basic visibility models for wireless sensor networks
* Basic energy consumption models for wireless sensor networks
* Collection of vehicle control behaviors
* Genetic algorithm abstraction

[Publications using YAES](http://www.eecs.ucf.edu/~lboloni/Programming/Yaes/publication.html)

## Wireless sensor network example:
> ### Bridge protection algorithm 
> ![bpa](https://cloud.githubusercontent.com/assets/4132171/13195128/af2280ae-d75e-11e5-9a79-fbb6d0a9eb94.jpg)
> ### Dense sensor network example
![phase2](https://cloud.githubusercontent.com/assets/4132171/13195144/02f166fa-d75f-11e5-84c3-647212156fd6.png)

# Feed-Fight-Mate Game:
> Screen-shot of the Feed-Fight-Mate game with different agent implementation. 
> ![cxbr](https://cloud.githubusercontent.com/assets/4132171/13195104/ea958a10-d75d-11e5-8c78-a86f581ced6d.gif)

# Mobile nodes/ Base Station/ Fire application:
Screen shot for a general wireless application with mobile nodes, base station and randomly occuring events.
> ![eel6788_screen1](https://cloud.githubusercontent.com/assets/4132171/13195105/f9199dc4-d75d-11e5-8774-ad29a7a8abe9.jpg)
