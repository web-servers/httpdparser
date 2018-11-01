#!/bin/bash

for d in LocationMatch \
Location \
FilesMatch \
RequireAny \
RequireAll \
RequireNone \
Proxy \
ProxyMatch;do 
echo processing $d;

cp template.java_ $d.java

sed -i "s/@template@/$d/g" $d.java
done