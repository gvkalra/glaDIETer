:warning: **This repository is not maintained anymore.**

glaDIETer-board
============

Consumer protection by bringing GS1 to Every Household

Setting up development environment
--------------------------------------------
**Setup Raspberry Pi with NOOBS**

[![Getting started with NOOBS](http://i.imgur.com/Iu3iwMk.png)](https://vimeo.com/90518800 "Getting started with NOOBS - Click to Watch!")

**Install Dependencies**

```bash
$ sudo apt-get install lighttpd python-flup
```

**Build the Circuit**

![GlaDIETer circuit](http://i.imgur.com/0A4hHIG.png "GlaDIETer circuit")

**Create 'rooted' python**

```bash
$ sudo cp /usr/bin/python2.7 /usr/bin/python_root
$ sudo chmod u+s /usr/bin/python_root
```

**Set static IP address**
```bash
$ sudo nano /etc/dhcpcd.conf
```
Add interface information at the end-of-file:
```
interface eth0

static ip_address=143.248.134.191/24
static routers=143.248.134.1
static domain_name_servers=143.248.1.177 143.248.2.177
```

**Install glaDIETer-board**
```bash
# lighttpd configuration
$ sudo cp ./etc/lighttpd/lighttpd.conf /etc/lighttpd/lighttpd.conf

# command handler
$ sudo cp ./usr/bin/rungpio /usr/bin/rungpio

# CGI scripts
$ sudo cp ./var/www/cgi-bin/fcgiPipe.py /var/www/cgi-bin/fcgiPipe.py
$ sudo cp ./var/www/cgi-bin/start_cooking.py /var/www/cgi-bin/start_cooking.py
```

**Start HTTP server & restart**
```bash
$ sudo service lighttpd start
$ sudo reboot
```

**Listen for cooking requests**
```bash
# rungpio
```

Notes
--------------------------------------------
Navigate to http://143.248.134.191/start_cooking.py?q=6 to cook for **<6>** seconds

Authors
-----------------
- Gaurav Kalra (<gvkalra@kaist.ac.kr>)
- Wladimir Silantiy Leuschner (<wleuschner@kaist.ac.kr>)
- Sunghee Myung (<1992happy@kaist.ac.kr>)
