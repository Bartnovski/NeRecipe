package ru.netology.nerecipe.room

import ru.netology.nerecipe.models.Step

internal fun StepEntity.toModel() = Step(
    stepId = stepId,
    idToRecipe = idToRecipe,
    positionInRecipe = positionInRecipe,
    stepContent = stepContent,
    stepImagePath = stepImagePath
)

internal fun Step.toEntity() = StepEntity(
    stepId = stepId,
    idToRecipe = idToRecipe,
    positionInRecipe = positionInRecipe,
    stepContent = stepContent,
    stepImagePath = stepImagePath
)