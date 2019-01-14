package ru.orbot90.com.coc.gmhelper.core.controller

import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import ru.orbot90.com.coc.gmhelper.core.model.OpposedTestRequest
import ru.orbot90.com.coc.gmhelper.core.model.TestRequest
import ru.orbot90.com.coc.gmhelper.core.test.SkillTestPerformer

fun Route.performTestController(skillTestPerformer: SkillTestPerformer){
    post("/performtest"){
        val request = call.receive<TestRequest>()
        val testResult = skillTestPerformer.performSkillTest(request.skillValue, request.bonusDice,
                request.penaltyDice).name
        call.respond("{\"testResult\": \"$testResult\"}")
    }

    post("/performtest/opposed") {
        val request = call.receive<OpposedTestRequest>()
        val opposedTestResult = skillTestPerformer.performOpposedTest(request.firstCharacterTest, request.secondCharacterTest)
        call.respond("\"opposedTestResult\": \"$opposedTestResult\"}")
    }
}