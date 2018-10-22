#!/bin/bash

for d in \


dirs....

;do 
echo processing $d;

cp template.java_ $d.java

sed -i "s/@template@/$d/g" $d.java
done