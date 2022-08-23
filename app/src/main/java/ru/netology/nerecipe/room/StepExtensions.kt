package ru.netology.nerecipe.room

import ru.netology.nerecipe.models.Step

internal fun StepEntity.toModel() = Step(
    id = id,
    idToRecipe = idToRecipe,
    positionInRecipe = positionInRecipe,
    stepContent = stepContent,
    stepImagePath = stepImagePath
)

internal fun Step.toEntity() = StepEntity(
    id = id,
    idToRecipe = idToRecipe,
    positionInRecipe = positionInRecipe,
    stepContent = stepContent,
    stepImagePath = stepImagePath
)