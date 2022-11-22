#! /bin/bash

modules=($(grep --include=\pom.xml -riL "<packaging>pom</packaging>" "." | sort | grep -v ^./pom.xml | grep -v ^./workbench/pom.xml | sed -e 's/\/pom.xml//'))
count=${#modules[@]}

json=$'{\n'
json+=$'    "include": [\n'
i=0

for module in ${modules[@]}; do
    ((i=i+1))
    json+=$'    {\n'
    json+=$'        "tck": "'
    json+="$module"
    json+=$'"\n    }'
    if [ $i -lt $count ]; then
    json+=$',\n'
    fi
done
json+=$'\n    ]\n}\n'

echo "$json"