for i in {1..16}; do
  echo "Testando com $i threads"
  time java -jar target/senha-1.0-SNAPSHOT.jar $i
done
