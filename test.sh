LIST=`cat instance_list.txt`

for i in $LIST
do
    curl -IL https://$i/about | head -n 1 | cut -d$' ' -f2
done
