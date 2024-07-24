for j in {1..10}; do
  echo "Teste $j"
  for i in {1..16}; do
    java -jar target/senha-1.0-SNAPSHOT.jar $i
  done
  echo
done
