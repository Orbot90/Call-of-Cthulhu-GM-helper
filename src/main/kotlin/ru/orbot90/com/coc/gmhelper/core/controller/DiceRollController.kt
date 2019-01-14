package ru.orbot90.com.coc.gmhelper.core.controller

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import ru.orbot90.com.coc.gmhelper.core.dice.DiceRoller
import ru.orbot90.com.coc.gmhelper.core.model.RollRequest


fun Route.diceRollController(diceRoller: DiceRoller){
    route("/diceroll"){
        post("/"){
            val request = call.receive<RollRequest>()
            call.respond(diceRoller.rollDice(request.diceCount,request.diceFacesCount))
        }
    }
}
