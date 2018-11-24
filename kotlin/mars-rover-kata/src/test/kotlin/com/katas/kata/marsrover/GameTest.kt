package com.katas.kata.marsrover

import junit.framework.Assert.assertEquals
import main.kotlin.com.katas.kata.marsrover.Game
import main.kotlin.com.katas.kata.marsrover.io.Inputs
import main.kotlin.com.katas.kata.marsrover.io.Output
import org.junit.Test

class GameTest {

    @Test
    fun forwardTest() {
        val result = Game.execute(Inputs("5x5", "3,3", "0,0", "f"))
        val expected = Output("Nord:0,1")
        assertEquals(expected, result.get())
    }

    @Test
    fun noObstacles() {
        val result = Game.execute(Inputs("5x5", "", "0,0", "f"))
        val expected = Output("Nord:0,1")
        assertEquals(expected, result.get())
    }

    @Test
    fun backwardTest() {
        val result = Game.execute(Inputs("5x5", "3,3", "0,1", "b"))
        val expected = Output("Nord:0,0")
        assertEquals(expected, result.get())
    }

    @Test
    fun turnRight() {
        val result = Game.execute(Inputs("5x5", "3,3", "0,0", "r"))
        val expected = Output("Est:0,0")
        assertEquals(expected, result.get())
    }

    @Test
    fun turnLeft() {
        val result = Game.execute(Inputs("5x5", "3,3", "0,0", "l"))
        val expected = Output("Ovest:0,0")
        assertEquals(expected, result.get())
    }

    @Test(expected = HittingBlockException::class)
    fun hitObstacle() {
        Game.execute(Inputs("5x5", "1,1", "0,0", "frf"))
    }

    @Test
    fun surpassPlanetEdgeForward() {
        val result = Game.execute(Inputs("2x2", "1,0", "0,0", "ff"))
        val expected = Output("Nord:0,0")
        assertEquals(expected, result.get())
    }

    @Test
    fun surpassPlanetEdgeBackward() {
        val result = Game.execute(Inputs("2x2", "1,0", "0,0", "bb"))
        val expected = Output("Nord:0,0")
        assertEquals(expected, result.get())
    }

    @Test
    fun firstTest() {
        val result = Game.execute(Inputs("5x5", "", "0,0", "rrffflbb"))

        val expected = Output("Est:3,2")

        assertEquals(expected, result.get())
    }

    @Test(expected = CommandNotRecognizedException::class)
    fun secondTest() {
        Game.execute(Inputs("5x5", "", "2,3", "frzffxrbbylbll"))
    }

    @Test(expected = HittingBlockException::class)
    fun thirdTest() {
        Game.execute(Inputs("5x5", "0,0/2,2", "0,2", "lfff"))
    }

    @Test
    fun fourthTest() {
        val result = Game.execute(Inputs("10x10", "", "5,5", "ff"))

        val expected = Output("Nord:5,7")

        assertEquals(expected, result.get())
    }

    @Test
    fun fifthTest() {
        val result = Game.execute(Inputs("10x10", "", "5,5", "bb"))

        val expected = Output("Nord:5,3")

        assertEquals(expected, result.get())
    }

    @Test
    fun sixthTest() {
        val result = Game.execute(Inputs("2x2", "", "0,0", "r"))

        val expected = Output("Est:0,0")

        assertEquals(expected, result.get())
    }

    @Test
    fun seventhTest() {
        val result = Game.execute(Inputs("2x2", "", "0,0", "rr"))

        val expected = Output("Sud:0,0")

        assertEquals(expected, result.get())
    }

    @Test
    fun eighthTest() {
        val result = Game.execute(Inputs("2x2", "", "0,0", "rrr"))

        val expected = Output("Ovest:0,0")

        assertEquals(expected, result.get())
    }

    @Test
    fun ninthTest() {
        val result = Game.execute(Inputs("2x2", "", "0,0", "rrrr"))

        val expected = Output("Nord:0,0")

        assertEquals(expected, result.get())
    }

    @Test
    fun tenthTest() {
        val result = Game.execute(Inputs("2x2", "", "0,0", "l"))

        val expected = Output("Ovest:0,0")

        assertEquals(expected, result.get())
    }

    @Test
    fun eleventhTest() {
        val result = Game.execute(Inputs("2x2", "", "0,0", "ll"))

        val expected = Output("Sud:0,0")

        assertEquals(expected, result.get())
    }

    @Test
    fun twelveTest() {
        val result = Game.execute(Inputs("2x2", "", "0,0", "lll"))

        val expected = Output("Est:0,0")

        assertEquals(expected, result.get())
    }

    @Test
    fun thirteenthTest() {
        val result = Game.execute(Inputs("2x2", "", "0,0", "llll"))

        val expected = Output("Nord:0,0")

        assertEquals(expected, result.get())
    }

}