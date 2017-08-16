from xml.dom.minidom import parse
import urllib
from django.utils import timezone
import utils
from dateutil import parser as date_parser

class GS1SourceParser(object):
	def __init__(self, gtin):
		url = utils.get_gs1source_query_url(gtin)
		if gtin[0] != '0':
			gtin = "0" + gtin
		url = url + gtin + "?targetMarket=410"
		self.xmldoc = parse(urllib.urlopen(url))

	def get_product_name(self):
		return self.xmldoc.getElementsByTagName('productName')[0].firstChild.nodeValue

	def get_fat_value(self):
		return self.xmldoc.getElementsByTagName('nutrientDetail')[0].childNodes[5].firstChild.nodeValue

	def get_cholesterol_value(self):
		return self.xmldoc.getElementsByTagName('nutrientDetail')[1].childNodes[5].firstChild.nodeValue

	def get_sodium_value(self):
		return self.xmldoc.getElementsByTagName('nutrientDetail')[2].childNodes[5].firstChild.nodeValue

	def get_protein_value(self):
		return self.xmldoc.getElementsByTagName('nutrientDetail')[3].childNodes[5].firstChild.nodeValue

	def get_carbohydrate_value(self):
		return "0"

	def get_cooking_time(self):
		return self.xmldoc.getElementsByTagName('servingSuggestion')[0].firstChild.nodeValue

	def get_manufacturing_datetime(self):
		val = self.xmldoc.getElementsByTagName('productActivityTypeCode')[0].firstChild.nodeValue
		if val == "0000-00-00T00:00:00.000000Z":
			return timezone.now()
		else:
			return date_parser.parse(val)

	def get_expiry_date(self):
		val = self.xmldoc.getElementsByTagName('stringAVP')[0].firstChild.nodeValue
		if val == "0000-00-00T00:00:00.000000Z":
			return timezone.now()
		else:
			return date_parser.parse(val).date()