from .models import ProductInformation
from rest_framework import serializers

class ProductInformationSerializer(serializers.ModelSerializer):
    nutritional_information = serializers.ReadOnlyField()

    class Meta:
        model = ProductInformation
        fields = ('gtin', 'display_name', 'manufacturing_datetime',
        	'cooking_time', 'expiry_date', 'recalled', 'nutritional_information')