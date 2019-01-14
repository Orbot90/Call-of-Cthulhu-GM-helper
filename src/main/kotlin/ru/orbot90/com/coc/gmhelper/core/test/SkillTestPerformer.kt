package ru.orbot90.com.coc.gmhelper.core.test

import ru.orbot90.com.coc.gmhelper.core.model.OpposedTestResult
import ru.orbot90.com.coc.gmhelper.core.model.TestRequest

interface SkillTestPerformer {

    fun performSkillTest(skillValue: Int,
                         bonusDiceCount: Int = 0, penaltyDiceCount: Int = 0): TestResultType

    fun performOpposedTest(firstCharacterTest: TestRequest, secondCharacterTest: TestRequest): OpposedTestResult
}