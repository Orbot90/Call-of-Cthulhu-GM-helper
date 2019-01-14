package ru.orbot90.com.coc.gmhelper.core.test

import ru.orbot90.com.coc.gmhelper.core.dice.DiceRoller
import ru.orbot90.com.coc.gmhelper.core.model.OpposedTestResult
import ru.orbot90.com.coc.gmhelper.core.model.OpposedTestResultType
import ru.orbot90.com.coc.gmhelper.core.model.TestRequest
import java.lang.RuntimeException

class SkillTestPerformerImpl constructor(private val diceRoller: DiceRoller) : SkillTestPerformer {

    override fun performSkillTest(skillValue: Int,
                         bonusDiceCount: Int, penaltyDiceCount: Int): TestResultType {
        val rollResult = when {
            bonusDiceCount == 0 && penaltyDiceCount == 0 -> this.diceRoller.rollDice(1, 100)[0]
            bonusDiceCount > penaltyDiceCount -> this.rollWithBonusDice(bonusDiceCount - penaltyDiceCount)
            else -> this.rollWithPenaltyDice(penaltyDiceCount - bonusDiceCount)
        }

        val fumbleRange: IntRange = if (skillValue < 50) 96..100 else 100..100

        return when {
            rollResult in fumbleRange -> TestResultType.FUMBLE
            rollResult == 1 -> TestResultType.CRITICAL_SUCCESS
            rollResult <= skillValue/5 -> TestResultType.EXTREME_SUCCESS
            rollResult <= skillValue/2 -> TestResultType.HARD_SUCCESS
            rollResult <= skillValue -> TestResultType.SUCCESS
            else -> TestResultType.FAILURE
        }
    }

    override fun performOpposedTest(firstCharacterTest: TestRequest, secondCharacterTest: TestRequest): OpposedTestResult {
        val firstResult = this.performSkillTest(firstCharacterTest.skillValue, firstCharacterTest.bonusDice, firstCharacterTest.penaltyDice)
        val secondResult = this.performSkillTest(secondCharacterTest.skillValue, secondCharacterTest.bonusDice, secondCharacterTest.penaltyDice)
        val opposedTestResultType: OpposedTestResultType = when {
            firstResult in hashSetOf(TestResultType.FUMBLE, TestResultType.FAILURE) &&
                    secondResult in hashSetOf(TestResultType.FUMBLE, TestResultType.FAILURE) ->
                OpposedTestResultType.BOTH_FAIL
            firstResult == secondResult -> OpposedTestResultType.TIE
            firstResult > secondResult -> OpposedTestResultType.FIRST_WIN
            secondResult > firstResult -> OpposedTestResultType.SECOND_WIN
            else -> {
                throw RuntimeException("Failed comparing test results")
            }
        }
        return OpposedTestResult(opposedTestResultType, firstResult, secondResult)
    }

    private fun rollWithPenaltyDice(penaltyDiceCount: Int): Int {
        val units = this.diceRoller.rollDice(1, 10)[0]
        val tens = this.diceRoller.rollDice(1 + penaltyDiceCount, 10)
        val worstTen: Int = {
            var biggestTen = 0
            for (ten in tens) {
                if (ten > biggestTen) {
                    biggestTen = ten
                }
            }
            biggestTen
        }()

        return 10 * worstTen + units
    }

    private fun rollWithBonusDice(bonusDiceCount: Int): Int {
        val units = this.diceRoller.rollDice(1, 10)[0]
        val tens = this.diceRoller.rollDice(1 + bonusDiceCount, 10)
        val bestTen: Int = {
            var leastTen = 10
            for (ten in tens) {
                if (ten < leastTen) {
                    leastTen = ten
                }
            }
            leastTen
        }()

        return 10 * bestTen + units
    }

}