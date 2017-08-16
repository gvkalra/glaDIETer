from django.db import models
from django.forms.models import model_to_dict
from django.core.validators import MinValueValidator
from decimal import Decimal


class ProductInformation(models.Model):
	# GTIN of product
	gtin = models.BigIntegerField(primary_key=True, unique=True, validators=[MinValueValidator(0)])

	# Display name of product
	display_name = models.CharField(max_length=50, blank=False)

	# Manufacturing date
	manufacturing_datetime = models.DateTimeField(blank=False)

	# Recommended cooking time of product in seconds.
	cooking_time = models.PositiveIntegerField(blank=False)

	# Expiry date of product
	expiry_date = models.DateField(blank=False)

	# True if product is recalled
	recalled = models.BooleanField(default=False, blank=False)
	# Datetime when we got to know product has been recalled
	recalled_datetime = models.DateTimeField(blank=True, null=True)

	# Nutritional Information
	fat = models.DecimalField(max_digits=5, decimal_places=2, blank=False,
		validators=[MinValueValidator(Decimal('00.00'))])
	cholesterol = models.DecimalField(max_digits=5, decimal_places=2, blank=False,
		validators=[MinValueValidator(Decimal('00.00'))])
	protein = models.DecimalField(max_digits=5, decimal_places=2, blank=False,
		validators=[MinValueValidator(Decimal('00.00'))])
	carbohydrate = models.DecimalField(max_digits=5, decimal_places=2, blank=False,
		validators=[MinValueValidator(Decimal('00.00'))])
	sodium = models.DecimalField(max_digits=5, decimal_places=2, blank=False,
		validators=[MinValueValidator(Decimal('00.00'))])

#	def __unicode__(self):
#		return str(self.gtin)

	def nutritional_information(self):
		return model_to_dict(self, fields=['fat', 'cholesterol', 'protein', 'carbohydrates', 'sodium'])