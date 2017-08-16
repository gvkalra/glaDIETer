from django.contrib.auth import get_user_model
from .models import UserConsumption
from gladieter.product.serializers import ProductInformationSerializer
from rest_framework import serializers


class GetUserProfileSerializer(serializers.ModelSerializer):
	class Meta:
		model = get_user_model()
		fields = ('username', 'email')

class GetUserProductsSerializer(serializers.ModelSerializer):
	product_information = ProductInformationSerializer(source="gtins")
	class Meta:
		model = UserConsumption
		fields = ('consumption_datetime', 'product_information')
		depth = 1

class ConsumeUserProductSerializer(serializers.ModelSerializer):
	user = serializers.PrimaryKeyRelatedField(read_only=True,
		default=serializers.CurrentUserDefault())
	class Meta:
		model = UserConsumption
		fields = ('user', 'gtins')