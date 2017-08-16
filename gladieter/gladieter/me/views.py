from .models import UserConsumption
from .serializers import (
	GetUserProfileSerializer,
	GetUserProductsSerializer,
	ConsumeUserProductSerializer
	)
from rest_framework import generics
from rest_framework.permissions import IsAuthenticated
from rest_framework.exceptions import ParseError
from django.utils import timezone
from datetime import timedelta


class GetUserProfile(generics.RetrieveAPIView):
	"""
	Retrieves information of the currently authenticated user.
	"""
	permission_classes = (IsAuthenticated,)

	def get_object(self):
		return self.request.user
	serializer_class = GetUserProfileSerializer

class GetUserProducts(generics.ListAPIView):
	"""
	Retrieves product consumption information of user since specified minutes.
	"""
	permission_classes = (IsAuthenticated,)
	def get_queryset(self):
		try:
			time_diff = timezone.now() - timedelta(minutes=int(self.kwargs['since']))
			return UserConsumption.objects.filter(
				user__id=self.request.user.id).filter(
				consumption_datetime__gte=time_diff)
		except:
			raise ParseError
	serializer_class = GetUserProductsSerializer

class ConsumeUserProduct(generics.CreateAPIView):
	"""
	Consumes specified GTIN for currently authenticated user.
	"""
	permission_classes = (IsAuthenticated,)
	serializer_class = ConsumeUserProductSerializer