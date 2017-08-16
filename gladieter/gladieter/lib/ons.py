import dns.resolver
from error import GladieterLibError
from defaults import ONS_SERVER_IP, ONS_SUPPORTED_SERVICES

class ONSServer(object):
	def __init__(self, nameservers=[ONS_SERVER_IP]):
		self.r = dns.resolver.Resolver()
		self.r.nameservers = nameservers

	def query_gs1source(self, fqdn):
		resp = {}

		# ONS query
		try:
			answer = self.r.query(fqdn, 'NAPTR')
		except:
			raise GladieterLibError("Failed to query NAPTR record")
		else:
			for data in answer:
				if data.service == 'http://www.gs1.org/ons/gs1source':
					resp['order'] = data.order
					resp['preference'] = data.preference
					resp['flags'] = data.flags
					resp['regexp'] = data.regexp
				else:
					pass
			return resp

	def query_epcis(self, fqdn):
		resp = {}

		# ONS query
		try:
			answer = self.r.query(fqdn, 'NAPTR')
		except:
			raise GladieterLibError("Failed to query NAPTR record")
		else:
			for data in answer:
				if data.service == 'http://www.gs1.org/ons/epcis':
					resp['order'] = data.order
					resp['preference'] = data.preference
					resp['flags'] = data.flags
					resp['regexp'] = data.regexp
				else:
					pass
			return resp