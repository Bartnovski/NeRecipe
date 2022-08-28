package ru.netology.nerecipe.demo

import ru.netology.nerecipe.models.RecipeModel
import ru.netology.nerecipe.models.Step

val recipeEv = RecipeModel(
    recipeId = 1L,
    recipeName = "Цветаевский яблочный пирог",
    author = "Неизвестен",
    category = "Европейская кухня",
    recipeImagePath = "https://eda.ru/img/eda/c434x295/s1.eda.ru/StaticContent/Photos/110810174633/1703132035211/p_O.png",
    isFavorite = false
)

val listOfLinksEv = listOf(
    "Ингридиенты",

    "https://eda.ru/img/eda/c434x295/s1.eda.ru/StaticContent/Photos/110810174633/170313203520/p_O.png",

    "https://eda.ru/img/eda/c434x295/s1.eda.ru/StaticContent/Photos/110810174633/1703132035200/p_O.png",

    "https://eda.ru/img/eda/c434x295/s1.eda.ru/StaticContent/Photos/110810174633/170313203521/p_O.png",

    "https://eda.ru/img/eda/c434x295/s1.eda.ru/StaticContent/Photos/110810174633/1703132035210/p_O.png",

    "https://eda.ru/img/eda/c434x295/s1.eda.ru/StaticContent/Photos/110810174633/1703132035211/p_O.png"
)

val listOfStepsEv = listOf(
    "Яблоко 1кг,Пшеничная мука 2 стакана,Сметана 1,5 банки,Сливочное масло 150 г,Уксус 1 чайная ложка,Сахар 1 стакан" +
            "Сода 0,5 чайные ложки,Куринное яйцо 1шт",

    "Сливочное масло комнатной температуры натрите на терке и смешайте с 1,5 стаканами муки. " +
            "Затем влейте в тесто полстакана сметаны и как следует перемешайте. В конце добавьте соду гашеную уксусом, " +
            "перемешайте и немного помесите тесто руками. Тесто должно получиться мягкое и не прилипать к рукам.",

    "Яблоки очистите от кожуры, удалите сердцевину и с помощью картофелечистки нарежьте на тонкие пластиночки, похожие на лепестки.",

    "Раскатайте тесто и придайте ему форму той емкости, в которой вы будете выпекать пирог. " +
            "Уложите на дно формы тесто, сверху разложите яблочные лепестки.",

    "В отдельной миске смешайте 1 стакан сметаны, яйцо, сахар и 2 столовые ложки муки и слегка взбейте. " +
            "Крем по консистенции должен получиться достаточно жидким. Залейте форму с тестом и яблоками кремом.",

    "Разогрейте духовку до 200 градусов, поместите туда форму и выпекайте минут 50."
)



