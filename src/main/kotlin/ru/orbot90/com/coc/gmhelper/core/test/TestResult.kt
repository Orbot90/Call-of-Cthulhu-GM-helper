package ru.orbot90.com.coc.gmhelper.core.test

enum class TestResultType {
    FUMBLE(-1), FAILURE(-1), SUCCESS(0), HARD_SUCCESS(1), EXTREME_SUCCESS(2), CRITICAL_SUCCESS(3);

    val priority: Int

    constructor(priority: Int) {
        this.priority = priority
    }
}