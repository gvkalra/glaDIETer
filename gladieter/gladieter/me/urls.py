from django.conf.urls import url
from views import *

me_urlpatterns = [
    url(r'^$', GetUserProfile.as_view()),
    url(r'^products/(?P<since>[\w-]+)$', GetUserProducts.as_view()),
    url(r'^consume$', ConsumeUserProduct.as_view()),
]