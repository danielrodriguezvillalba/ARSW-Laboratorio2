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

Part I - Before finishing class

Thread control with wait/notify. Producer/consumer

1. Check the operation of the program and run it. While this occurs, run jVisualVM and check the CPU consumption of the 		corresponding process. Why is this consumption? Which is the responsible class? 

	
	![Imagenes](https://github.com/danielrodriguezvillalba/ARSW-Laboratorio2/blob/master/Imagenes/LAB2parte1punto1visulavm.PNG)

	`La clase StartProduction() es la responsable del consumo excesivo, ya que crea los threads Consumer 
	y Producer, y estos corren infinitamente sin ningun manejo o control usando un ciclo while(true).`


2. Make the necessary adjustments so that the solution uses the CPU more efficiently, taking into account that - for now - production is slow and consumption is fast. Verify with JVisualVM that the CPU consumption is reduced. 

	![Imagenes](https://github.com/danielrodriguezvillalba/ARSW-Laboratorio2/blob/master/Imagenes/LAB2parteMejorada.PNG)

	`Se hizo uso de varios bloqueos de la queue en los hilos de producer y costumer para poder reducir el desempe�o de la CPU, tal como puede verse en la imagen usando el Java Visual VM.`

3. Make the producer now produce very fast, and the consumer consumes slow. Taking into account that the producer knows a Stock limit (how many elements he should have, at most in the queue), make that limit be respected. Review the API of the collection used as a queue to see how to ensure that this limit is not exceeded. Verify that, by setting a small limit for the 'stock', there is no high CPU consumption or errors.
