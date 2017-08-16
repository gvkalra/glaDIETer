## Install dnspython

```bash
$ pip install dnspython
```

## Query

```python
import dns.resolver

r = dns.resolver.Resolver()
r.nameservers = ['143.248.134.239']

answer = r.query('1.8.0.0.0.0.1.2.3.4.5.8.8.gtin.gs1.id.onsepc.kr', 'NAPTR')
for data in answer:
   print data
```

## Sample output

```
0 0 "U" "http://www.gs1.org/ons/epcis" "!^.*$!http://stis.iotlab.eu:8080/epcis-repository-0.6.4/query!" .
```