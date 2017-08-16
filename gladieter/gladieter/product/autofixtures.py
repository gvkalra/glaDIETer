from models import ProductInformation
from autofixture import AutoFixture, register
from decimal import Decimal
from random import randint


class ProductInformationAutoFixture(AutoFixture):
	class Values:
		gtin = staticmethod(lambda: randint(1, 8650623461676147281))
		cooking_time = staticmethod(lambda: randint(30, 500))
		recalled = False
		recalled_datetime = None
		fat = staticmethod(lambda: Decimal(str(randint(10, 40)) + '.' + str(randint(10, 40))))
		cholesterol = staticmethod(lambda: Decimal(str(randint(10, 40)) + '.' + str(randint(10, 40))))
		protein = staticmethod(lambda: Decimal(str(randint(10, 40)) + '.' + str(randint(10, 40))))
		carbohydrate = staticmethod(lambda: Decimal(str(randint(10, 40)) + '.' + str(randint(10, 40))))
		sodium = staticmethod(lambda: Decimal(str(randint(10, 40)) + '.' + str(randint(10, 40))))

register(ProductInformation, ProductInformationAutoFixture)