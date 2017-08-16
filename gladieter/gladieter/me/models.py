from django.db import models
from django.contrib.auth.models import User
from gladieter.product.models import ProductInformation

class UserConsumption(models.Model):
	# User Information
	user = models.ForeignKey(User)

	# GTIN of consumed product
	gtins = models.ForeignKey(ProductInformation)

	# Consumption date-time
	consumption_datetime = models.DateTimeField(auto_now_add=True)