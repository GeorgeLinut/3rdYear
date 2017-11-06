

#include <iostream>
#include <fstream>
#include <string>
#include <ctime>
#include <thread>
#include <chrono>

class Main {

public:
	void generateMatrixWithElements2(std::string, bool, int);
	void generateMatrixWithElements(std::string, bool, int);
	int randomNumber(bool);
	int** loadMatrix(std::string, int);
	void printMatrix(int**, int);

};

int Main::randomNumber(bool isEmpty) {

	if (isEmpty == true) {
		return 0;
	}
	else {
		return rand() % 9 + 1;
	}

}

void Main::generateMatrixWithElements2(std::string fileName, bool isEmpty, int numberOfElements) {

	srand((unsigned int)time(NULL));
	std::ofstream out(fileName.c_str(), std::ios_base::out);

	for (int i = 0; i < numberOfElements; i++) {
		for (int j = 0; j < numberOfElements; j++) {
			int randomNumber = rand() % 9 + 1;
			out << " " << randomNumber;
		}
		out << '\n';
	}
	out.close();
}

void Main::generateMatrixWithElements(std::string fileName, bool isEmpty, int numberOfElements) {

	Main main;

	std::ofstream outfile(fileName.c_str(), std::ios_base::out);

	for (int i = 0; i < numberOfElements; i++) {
		for (int j = 0; j < numberOfElements; j++) {
			outfile << main.randomNumber(isEmpty) << " ";

		}

		outfile << std::endl;

	}

	outfile.close();

}

int** Main::loadMatrix(std::string fileName, int nr) {
	int x, y;
	std::ifstream in(fileName.c_str());

	if (!in) {
		std::cout << "Cannot open file.\n";
		return 0;
	}

	int** array2D = 0;
	array2D = new int*[nr];

	for (y = 0; y < nr; y++) {

		array2D[y] = new int[nr];

		for (x = 0; x < nr; x++) {
			in >> array2D[y][x];
		}
	}

	in.close();

	return array2D;
}

void Main::printMatrix(int** matrix, int nr) {

	for (int h = 0; h < nr; h++)
	{
		for (int w = 0; w < nr; w++)
		{
			printf("%i,", matrix[h][w]);
		}
		printf("\n");
	}
}

int static getStartLine(int start, int nr) {
	return start / nr;
}

int static getStartColumn(int start, int nr) {
	return start % nr;
}

int static getStopLine(int stop, int nr) {
	return (stop - 1) / nr;
}

int static getStopColumn(int stop, int nr) {
	return (stop - 1) % nr;
}

void sum(int start, int stop, int** m1, int** m2, int** m3, int nr)
{
	for (int line = getStartLine(start, nr); line <= getStopLine(stop, nr); line++) {
		for (int column = getStartColumn(start, nr); column < nr; column++) {
			m3[line][column] = m1[line][column] + m2[line][column];
		}
	}

	for (int column = 0; column <= getStopColumn(stop, nr); column++) {
		int line = getStopLine(stop, nr);
		m3[line][column] = m1[line][column] + m2[line][column];
	}

}

void multiplication(int start, int stop, int** m1, int** m2, int** m3, int nr)
{
	for (int line = getStartLine(start, nr); line <= getStopLine(stop, nr); line++) {
		for (int column = getStartColumn(start, nr); column < nr; column++) {
			m3[line][column] += m1[line][column] * m2[column][line];
		}
	}

	for (int column = 0; column <= getStopColumn(stop, nr); column++) {
		int line = getStopLine(stop, nr);
		m3[line][column] += m1[line][column] * m2[column][line];
	}

}


static const int num_threads = 2;
std::string matrix1 = "matrix1.txt";
std::string matrix2 = "matrix2.txt";
std::string matrix3 = "matrix3.txt";


int main(int argc, const char * argv[]) {

	int numberOfElements = 4;
	int nr = numberOfElements;

	Main main;
	std::thread tt[num_threads];

	main.generateMatrixWithElements2(matrix1, false, numberOfElements);
	main.generateMatrixWithElements(matrix2, false, numberOfElements);
	main.generateMatrixWithElements(matrix3, true, numberOfElements);

	int** m1 = main.loadMatrix(matrix1, nr);
	int** m2 = main.loadMatrix(matrix2, nr);
	int** m3 = main.loadMatrix(matrix3, nr);

	int start = 0;
	int stop = 0;

	long long startTime = std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now().time_since_epoch()).count();

	for (int threadId = 0; threadId < num_threads; threadId++) {

		double elements = (double)nr * nr;
		double perfectElements = elements / num_threads;
		int actualElements = (int)perfectElements;

		if (threadId + 1 == num_threads || start + actualElements > elements) {
			stop = (int)elements;
		}
		else {
			stop = start + actualElements;
		}

		//        tt[threadId] = std::thread(sum, start, stop, m1, m2, m3, nr);
		tt[threadId] = std::thread(multiplication, start, stop, m1, m2, m3, nr);

		start = stop;
	}

	for (int i = 0; i < num_threads; ++i) {
		tt[i].join();
	}

	long long duration = std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now().time_since_epoch()).count() - startTime;

	std::cout << "Time: " << duration << std::endl;

	main.printMatrix(m3, nr);

	return 0;

}
