# Laboratorio 2


## Nombres:
```
Nicolas Cardenas Chaparro

Daniel Felipe Rodriguez Villalba
```
## Compile and run instructions:

Ubicarse sobre el directorio en el cual se va a trabajar
* Compilar: Use el comando `mvn package`
* Ejecutar Pruebas: Use el comando `mvn test`

## Part I - Before finishing class

Thread control with wait/notify. Producer/consumer

1. Check the operation of the program and run it. While this occurs, run jVisualVM and check the CPU consumption of the 		corresponding process. Why is this consumption? Which is the responsible class? 

	
	![Imagenes](https://github.com/danielrodriguezvillalba/ARSW-Laboratorio2/blob/master/Imagenes/LAB2parte1punto1visulavm.PNG)

	`La clase StartProduction() es la responsable del consumo excesivo, ya que crea los threads Consumer 
	y Producer, y estos corren infinitamente sin ningun manejo o control usando un ciclo while(true).`


2. Make the necessary adjustments so that the solution uses the CPU more efficiently, taking into account that - for now - production is slow and consumption is fast. Verify with JVisualVM that the CPU consumption is reduced. 

	![Imagenes](https://github.com/danielrodriguezvillalba/ARSW-Laboratorio2/blob/master/Imagenes/LAB2parteMejorada.PNG)

	`Se hizo uso de varios bloqueos de la queue en los hilos de producer y costumer para poder reducir el desempeño de la CPU, tal como puede verse en la imagen usando el Java Visual VM.`

3. Make the producer now produce very fast, and the consumer consumes slow. Taking into account that the producer knows a Stock limit (how many elements he should have, at most in the queue), make that limit be respected. Review the API of the collection used as a queue to see how to ensure that this limit is not exceeded. Verify that, by setting a small limit for the 'stock', there is no high CPU consumption or errors.
	
	![Imagenes](https://github.com/danielrodriguezvillalba/ARSW-Laboratorio2/blob/master/Imagenes/lab2VvmPart1pt3-stocklimitRunning.png)
	
	`Se ejecuto el programa con el stocklimit original y con produccion rapida, el porcentaje de consumo de CPU fue de 12% a 14%. Inicialmente el consumo de CPU se debe unicamente al productor (en los primeros 5 segundos) `
	
	![Imagenes](https://github.com/danielrodriguezvillalba/ARSW-Laboratorio2/blob/master/Imagenes/lab2VvmPart1pt3-stocklimitthousand.png)
	
	`Se ejecuto el programa con el stocklimit mas chico de 1000 y con produccion rapida, el porcentaje de consumo de CPU fue exactamente el mismo, adicionalemte no se produjo ningun error`
	
	![Imagenes](https://github.com/danielrodriguezvillalba/ARSW-Laboratorio2/blob/master/Imagenes/lab2VvmPart1pt3-stocklimitTen.png)
	
	`Se ejecuto el programa con el stocklimit pequeño de 10 y con produccion rapida, el porcentaje de consumo de CPU fue exactamente el mismo, adicionalemte no se produjo ningun error`
	

## Part II - Synchronization and Dead-Locks

1. Review the “highlander-simulator” program, provided in the edu.eci.arsw.highlandersim package. This is a game in which:
	You have N immortal players. 
	Each player knows the remaining N-1 player.
 	Each player permanently attacks some other immortal. The one who first attacks subtracts M life points from his opponent, and increases his own life points by the same amount. 
	The game could never have a single winner. Most likely, in the end there are only two left, fighting indefinitely by removing and adding life points. 
2. Review the code and identify how the functionality indicated above was implemented. Given the intention of the game, an invariant should be that the sum of the life points of all players is always the same (of course, in an instant of time in which a time increase / reduction operation is not in process ). For this case, for N players, what should this value be?

	`La vida de cada inmortal es de 100 puntos, por lo tanto la vida global es igual 100 multiplicado por la cantidad N de inmortales`

3. Run the application and verify how the ‘pause and check’ option works. Is the invariant fulfilled?

	* Primer pause and check

	![Imagenes](https://github.com/danielrodriguezvillalba/ARSW-Laboratorio2/blob/master/Imagenes/First%20time.PNG)
	
	* Segundo pause and check
	
	![Imagenes](https://github.com/danielrodriguezvillalba/ARSW-Laboratorio2/blob/master/Imagenes/NextTime.PNG)

	`El invariante no se cumple por el momento, la suma de la vida global siempre esta cambiando`

4. A first hypothesis that the race condition for this function (pause and check) is presented is that the program consults the list whose values ​​it will print, while other threads modify their values. To correct this, do whatever is necessary so that, before printing the current results, all other threads are paused. Additionally, implement the ‘resume’ option.
5. Check the operation again (click the button many times). Is the invariant fulfilled or not ?.

	`El invariante no se cumple por el momento, a veces aumenta y a veces disminuye, como se puede ver en las imagenes del punto 3`

6. Identify possible critical regions in regards to the fight of the immortals. Implement a blocking strategy that avoids race conditions. Remember that if you need to use two or more ‘locks’ simultaneously, you can use nested synchronized blocks:

7. After implementing your strategy, start running your program, and pay attention to whether it comes to a halt. If so, use the jps and jstack programs to identify why the program stopped.

8. Consider a strategy to correct the problem identified above (you can review Chapter 15 of Java Concurrency in Practice again).

9. Once the problem is corrected, rectify that the program continues to function consistently when 100, 1000 or 10000 immortals are executed. If in these large cases the invariant begins to be breached again, you must analyze what was done in step 4.

10. An annoying element for the simulation is that at a certain point in it there are few living 'immortals' making failed fights with 'immortals' already dead. It is necessary to suppress the immortal dead of the simulation as they die. 
Analyzing the simulation operation scheme, could this create a race condition? Implement the functionality, run the simulation and see what problem arises when there are many 'immortals' in it. Write your conclusions about it in the file ANSWERS.txt. 
Correct the previous problem WITHOUT using synchronization, since making access to the shared list of immortals sequential would make simulation extremely slow. 

	`Se elimino cada inmortal que moria(o sea tenia vida 0), pero al final el ultimo inmortal peleaba consigo mismo y por ende tambien se removia de la poblacion de inmortales, por esta razon al final en el panel de estadisticas no aparecen inmortales y la vida global es cero [No se soluciono el error]`

11. To finish, implement the STOP option.

	`La implementacion del metodo stop se realiza de esta manera y verificando esa condicion de que el stop sea falso siempre.`

	![Imagenes](https://github.com/danielrodriguezvillalba/ARSW-Laboratorio2/blob/master/Imagenes/BotonStop.PNG)
