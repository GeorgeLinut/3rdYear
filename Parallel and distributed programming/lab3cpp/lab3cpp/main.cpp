#include <iostream>
#include <future>
#include "Matrix.h"
#include "Controller.h"
#include "Async_multiply.h"
#include "Async_sum.h"

using namespace chrono;

using std::cout;
using std::cin;
using std::endl;



int main()
{
	int rows = 10;
	int columns = 10;

	auto matrixA = new Matrix(rows, columns);
	auto matrixB = new Matrix(rows, columns);
	auto matrixResult = new Matrix(rows, columns);

	SYSTEMTIME time{};
	time.wMilliseconds = 9000;

	SYSTEMTIME mostEfficientTime{};
	mostEfficientTime.wMilliseconds = 9000;

	int mostEfficientNumberOfThreads = 900000000;

	int maxNumberOfThreads = 10;

	auto controller = new Controller();
	auto async_sum = new Async_Sum(maxNumberOfThreads, *matrixA, *matrixB, *matrixResult, *controller, time, mostEfficientTime, mostEfficientNumberOfThreads);
	auto async_multiply = new Async_Multiply(maxNumberOfThreads, *matrixA, *matrixB, *matrixResult, *controller, time, mostEfficientTime, mostEfficientNumberOfThreads);


	cout << "Start: \n";
	async_sum->run();
	async_multiply->run();

	cin.get();

	return 0;
}