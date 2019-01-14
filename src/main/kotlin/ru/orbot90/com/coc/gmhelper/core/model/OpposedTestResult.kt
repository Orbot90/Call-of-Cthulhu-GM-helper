package ru.orbot90.com.coc.gmhelper.core.model

import ru.orbot90.com.coc.gmhelper.core.test.TestResultType

class OpposedTestResult(val resultType: OpposedTestResultType,
                        val firstRollResult: TestResultType, val secondRollResult: TestResultType)