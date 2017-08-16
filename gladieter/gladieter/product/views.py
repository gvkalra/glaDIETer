from .models import ProductInformation
from .serializers import ProductInformationSerializer
from rest_framework import generics
from rest_framework.permissions import IsAuthenticated
from django.core.exceptions import ObjectDoesNotExist
from gladieter.lib import gs1source, utils
from django.utils import timezone
from decimal import Decimal


class GetProductInformation(generics.RetrieveAPIView):
	"""
	Retrieves information of the product specified by GTIN.
	"""
	permission_classes = (IsAuthenticated,)

	queryset = ProductInformation.objects.all()
	serializer_class = ProductInformationSerializer
	lookup_url_kwarg = 'gtin'

	def get_queryset(self):
		gtin = self.kwargs['gtin']
		try:
			product = ProductInformation.objects.get(gtin=gtin)
		except ObjectDoesNotExist:
			print "Cache Miss"
			try:
				parser = gs1source.GS1SourceParser(str(gtin))
				product = ProductInformation(gtin=gtin, display_name=parser.get_product_name(),
					manufacturing_datetime=parser.get_manufacturing_datetime(),
					cooking_time=parser.get_cooking_time(), expiry_date=parser.get_expiry_date(),
					recalled=utils.is_product_recalled(str(gtin)), recalled_datetime=timezone.now(),
					fat=Decimal(parser.get_fat_value()), cholesterol=Decimal(parser.get_cholesterol_value()),
					protein=Decimal(parser.get_protein_value()), carbohydrate=Decimal(parser.get_carbohydrate_value()),
					sodium=Decimal(parser.get_sodium_value()))
				product.save()
			except:
				pass
		return ProductInformation.objects.all()