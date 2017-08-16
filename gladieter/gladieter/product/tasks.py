from __future__ import absolute_import

from celery import task
from celery.utils.log import get_task_logger

from gladieter.product.models import ProductInformation
from gladieter.lib import utils
from django.utils import timezone

logger = get_task_logger(__name__)

@task
def update_cache():
    logger.info("Starting to update cache")
    elems = ProductInformation.objects.all()
    for elem in elems:
    	try:
    		if utils.is_product_recalled(elem.gtin) and elem.recalled is False:
	    		print "Recalled: " + str(elem.gtin)
    			elem.recalled = True
    			elem.recalled_datetime = timezone.now()
    			elem.save()
    	except:
    		pass
    logger.info("Cache update finished")