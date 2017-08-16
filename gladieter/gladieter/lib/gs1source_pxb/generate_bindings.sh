#!/bin/bash

pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/BasicProductInformationModule.xsd -m BasicProductInformationModule
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/FoodAndBeverageIngredientInformationModule.xsd -m FoodAndBeverageIngredientInformationModule
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/FoodAndBeveragePreparationInformationModule.xsd -m FoodAndBeveragePreparationInformationModule
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/NonfoodIngredientInformationModule.xsd -m NonfoodIngredientInformationModule
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/NutritionalProductInformationModule.xsd -m NutritionalProductInformationModule
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/ProductAllergenInformationModule.xsd -m ProductAllergenInformationModule
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/ProductClaimsAndEndorsementsModule.xsd -m ProductClaimsAndEndorsementsModule
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/ProductData.xsd -m ProductData
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/ProductInstructionsModule.xsd -m ProductInstructionsModule
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/ProductOriginInformationModule.xsd -m ProductOriginInformationModule
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/ProductQuantityInformationModule.xsd -m ProductQuantityInformationModule
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/ProductUsageAndSafetyModule.xsd -m ProductUsageAndSafetyModule
pyxbgen -u https://gladieter.herokuapp.com/static/TSD_1_1-schemas/tsd/QueryByGtinResponse.xsd -m QueryByGtinResponse