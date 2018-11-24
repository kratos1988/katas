package main.kotlin.com.katas.kata.marsrover

import arrow.core.*
import com.katas.kata.marsrover.*
import com.katas.kata.marsrover.Command.*
import main.kotlin.com.katas.kata.marsrover.Direction.*
import main.kotlin.com.katas.kata.marsrover.io.Inputs
import main.kotlin.com.katas.kata.marsrover.io.Output
import java.lang.Integer.parseInt


object Game {

    fun execute(inputs: Inputs): Option<Output> {
        return Some(inputs).flatMap { readPlanetSize(inputs) }
                .flatMap({ pl -> readObstacles(inputs, pl) })
                .flatMap({ pl -> readRoverPosition(inputs, pl) })
                .flatMap({r -> readCommands(inputs).map { cs -> executeCommands(r, cs) } })
                .flatMap({ x -> extractOutput(x) })
                .or(None)

    }

    private fun extractOutput(rover: Rover): Option<Output> {
        return Some(Output(rover.retrieveOutput()))
    }

    private fun executeCommands(r: Rover, cs: List<Command>): Rover {
        return cs.fold(r, { r, c -> executeCommand(r, c) })
    }

    private fun executeCommand(r: Rover, c: Command) : Rover {
        return when(c) {
            F -> forward(r)
            B -> backward(r)
            R -> turnRight(r)
            L -> turnLeft(r)
        }
    }

    private fun turnLeft(r: Rover): Rover {
        val newRover = r.copy()
        newRover.dir = calcLeftTurn(r.dir)
        return newRover
    }

    private fun calcLeftTurn(direction: Direction): Direction {
        return when(direction) {
            Nord -> Ovest
            Ovest -> Sud
            Sud -> Est
            Est -> Nord
        }
    }

    private fun turnRight(r: Rover): Rover {
        val newRover = r.copy()
        newRover.dir = calcRightTurn(r.dir)
        return newRover
    }

    private fun calcRightTurn(direction: Direction): Direction {
        return when(direction) {
            Nord -> Est
            Est -> Sud
            Sud -> Ovest
            Ovest -> Nord
        }
    }

    private fun backward(r: Rover): Rover {
        val newRover = r.copy()
        newRover.pos = moveBackward(r)
        return newRover
    }

    private fun moveBackward(r: Rover): Position {
        return when(r.dir) {
            Nord -> Position(r.pos.x, calcNewPos(r.pos.y - 1, r.pl.height))
            Sud -> Position(r.pos.x, calcNewPos(r.pos.y + 1, r.pl.height))
            Est -> Position(calcNewPos(r.pos.x - 1, r.pl.length), r.pos.y)
            Ovest -> Position(calcNewPos(r.pos.x + 1, r.pl.length), r.pos.y)
        }
    }

    private fun forward(r: Rover): Rover {
        val newRover = r.copy()
        newRover.pos = moveForward(r)
        return newRover
    }

    private fun moveForward(rover: Rover): Position {
        val newPos = when (rover.dir) {
            Nord -> Position(rover.pos.x, calcNewPos(rover.pos.y + 1, rover.pl.height))
            Sud -> Position(rover.pos.x, calcNewPos(rover.pos.y - 1, rover.pl.height))
            Est -> Position(calcNewPos(rover.pos.x + 1, rover.pl.length), rover.pos.y)
            Ovest -> Position(calcNewPos(rover.pos.x - 1, rover.pl.length), rover.pos.y)
        }
        rover.checkIfHitObstacle(newPos)
        return newPos
    }

    private fun calcNewPos(ax: Int, plSize: Int) = (ax + plSize) % plSize

    private fun readCommands(inputs: Inputs): Option<List<Command>> {
        return Some(inputs.commands.map { x -> toCommand(x) })
    }

    private fun toCommand(x: Char): Command {
       return when(x) {
           'f' -> F
           'b' -> B
           'r' -> R
           'l' -> L
           else -> throw CommandNotRecognizedException()
       }
    }

    private fun readRoverPosition(inputs: Inputs, pl: Planet): Option<Rover> {
        val rover = Rover()
        rover.pos = extractPosition(inputs)
        rover.pl = pl
        return Some(rover)
    }

    private fun extractPosition(inputs: Inputs): Position {
        val position = inputs.roverPosition.split(",")
        return Position(parseInt(position[0]), parseInt(position[1]))
    }

    private fun readObstacles(inputs: Inputs, planet: Planet): Option<Planet> {
        val obstacles = inputs.obstacles.split("/")
        return readObstacleList(obstacles).flatMap { x -> planetWithObstacles(planet, x) }
    }

    private fun planetWithObstacles(planet: Planet, obstacles: List<Obstacle>): Option<Planet> {
        val newPlanet = Planet(planet.length, planet.height, obstacles);
        return Some(newPlanet)
    }

    private fun readObstacleList(obstacles: List<String>): Option<List<Obstacle>> {
        return Some(obstacles
                .filter { x -> !x.isNullOrEmpty() }
                .map { x -> readObstacle(x) })
    }

    private fun readObstacle(obstacle: String): Obstacle {
        val coordinates = obstacle.split(",")
        return Obstacle(parseInt(coordinates[0]), parseInt(coordinates[1]))
    }

    private fun readPlanetSize(inputs: Inputs): Option<Planet> {
        val sizes = inputs.size.split("x")
        return Some(Planet(parseInt(sizes[0]), parseInt(sizes[1]), emptyList()))
    }

}
