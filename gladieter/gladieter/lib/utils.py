from defaults import ONS_PEER_ROOT
from error import GladieterLibError
from defaults import ONS_SUPPORTED_REGEX
from xml.dom.minidom import parse
import urllib
import ons

def gtin_to_fqdn(gtin, peer_root=ONS_PEER_ROOT):
	aus = gtin[0:1] + "." + gtin[12:13] + "." + gtin[11:12] + "." \
			+ gtin[10:11] + "." + gtin[9:10] + "." + gtin[8:9] + "." \
			+ gtin[7:8] + "." + gtin[6:7] + "." + gtin[5:6] + "." \
			+ gtin[4:5] + "." + gtin[3:4] + "." + gtin[2:3] + "." \
			+ gtin[1:2] + "."
	return aus + "gtin.gs1.id." + peer_root

def regexp_to_uri(regexp):
	s = regexp.split('!')
	# be picky!
	if s[1] in ONS_SUPPORTED_REGEX:
		return regexp.split('!')[2] # url
	raise GladieterLibError("Regexp not supported")

def is_product_recalled(gtin):
	fqdn = gtin_to_fqdn(str(gtin))

	o = ons.ONSServer()
	epcis = o.query_epcis(fqdn)
	uri = regexp_to_uri(epcis['regexp'])

	query_url = uri + "Service/Poll/SimpleEventQuery?" +\
		"EQ_bizStep=urn:epcglobal:cbv:bizstep:holding&EQ_disposition=urn:epcglobal:cbv:disp:recalled" +\
		"&MATCH_epc=urn:epc:id:gtin:" + str(gtin) + "&"
	try:
		xml = urllib.urlopen(query_url)
		dom = parse(xml)
		if len(dom.getElementsByTagName('action')) == 0:
			return False
		return True # recalled
	except:
		return False

def get_gs1source_query_url(gtin):
	fqdn = gtin_to_fqdn(str(gtin))

	o = ons.ONSServer()
	gs1_source = o.query_gs1source(fqdn)
	uri = regexp_to_uri(gs1_source['regexp'])

	return uri