from django.conf.urls import url
from views import *

product_urlpatterns = [
    url(r'^(?P<gtin>[\w-]+)$', GetProductInformation.as_view()),
]