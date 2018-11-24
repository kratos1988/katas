package main.kotlin.com.katas.kata.marsrover

import com.katas.kata.marsrover.HittingBlockException
import com.katas.kata.marsrover.Planet
import com.katas.kata.marsrover.Position
import main.kotlin.com.katas.kata.marsrover.Direction.Nord

class Rover {
    var dir: Direction = Nord
    var pos: Position = Position(0,0)
    var pl: Planet = Planet(0,0, emptyList())

    fun retrieveOutput(): String {
        return printDirection() + ":" + printPosition()
    }

    private fun printPosition(): String {
        return pos?.x.toString() + "," + pos?.y.toString()
    }

    private fun printDirection(): String {
        return dir.toString()
    }

    fun copy(): Rover {
        val rover = Rover()
        rover.dir = dir
        rover.pos = pos
        rover.pl = pl
        return rover
    }

    fun checkIfHitObstacle(p: Position) {
        pl.obstacles.filter { o -> o.x == p.x && o.y == p.y}.map { throw HittingBlockException() }
    }

}