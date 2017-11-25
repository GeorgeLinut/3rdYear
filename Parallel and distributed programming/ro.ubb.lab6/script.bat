
echo "Compiling naive algorithm - classic.cpp'"
g++ -O2 -std=c++11 -pthread -Dhome -Wall classic.cpp -o classic.o
echo "Compiling naive algorithm (threaded) - classicThread.cpp'"
g++ -O2 -std=c++11 -pthread -Dhome -Wall classicThread.cpp -o classicThread.o
echo "Compiling karasuba algorithm - katsubata.cpp'"
g++ -O2 -std=c++11 -pthread -Dhome -Wall katsubata.cpp -o katsubata.o
echo "Compiling karasuba algorithm (threaded) - katsubataThread.cpp'"
g++ -O2 -std=c++11 -pthread -Dhome -Wall katsubataThread.cpp -o katsubataThread.o

echo ""

for i in `ls generateTests/*.in`
do
  echo "Running test $i"
  head -n1 $i
  ./seq_1.o $i
  ./seq_2.o $i
  ./kar_1.o $i
  ./kar_2.o $i
  echo ""
done

rm *.o