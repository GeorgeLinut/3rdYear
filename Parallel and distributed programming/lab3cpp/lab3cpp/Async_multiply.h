#pragma once
#ifndef C_ASYNC_ASYNC_MULTIPLY_H
#define C_ASYNC_ASYNC_MULTIPLY_H
#include <future>
#include "Matrix.h"
#include "Controller.h"
#include "chrono"
#include "Windows.h"

using namespace chrono;
using namespace std;

class Async_Multiply
{
private:
	int max_nr_threads{};
	Matrix matrixA{};
	Matrix matrixB{};
	Matrix matrix_res{};
	Controller controller{};
	SYSTEMTIME time{};
	SYSTEMTIME most_efficient_time{};
	int most_efficient_nr_threads{};

public:
	Async_Multiply();

	~Async_Multiply();

	Async_Multiply(int max_nr_threads, Matrix matrixA, Matrix matrixB, Matrix matrix_res, Controller controller,
		SYSTEMTIME time, SYSTEMTIME most_efficient_time, int most_efficient_nr_threads);
	void run();
};

Async_Multiply::Async_Multiply(int max_nr_threads, Matrix matrixA, Matrix matrixB, Matrix matrix_res, Controller controller,
	SYSTEMTIME time, SYSTEMTIME most_efficient_time, int most_efficient_nr_threads) : max_nr_threads(
		max_nr_threads), matrixA(matrixA), matrixB(matrixB),
	matrix_res(matrix_res),
	controller(controller),
	time(time),
	most_efficient_time(
		most_efficient_time),
	most_efficient_nr_threads(
		most_efficient_nr_threads)
{
	this->most_efficient_nr_threads = most_efficient_nr_threads;
	this->most_efficient_time = most_efficient_time;
	this->max_nr_threads = max_nr_threads;
	this->matrixB = matrixB;
	this->matrixA = matrixA;
	this->matrix_res = matrix_res;
	this->controller = controller;
	this->time = time;
}

Async_Multiply::Async_Multiply()
= default;

Async_Multiply::~Async_Multiply()
= default;

void Async_Multiply::run()
{
	SYSTEMTIME totalTime{};
	GetSystemTime(&totalTime);

	SYSTEMTIME currentTime{};
	for (int i = 0; i < max_nr_threads; ++i)
	{
		GetSystemTime(&time);

		controller.multiply(matrixA, matrixB, matrix_res, i);

		GetSystemTime(&currentTime);
		if (most_efficient_time.wMilliseconds > currentTime.wMilliseconds - time.wMilliseconds)
		{
			most_efficient_time.wMilliseconds = currentTime.wMilliseconds - time.wMilliseconds;
			most_efficient_nr_threads = i;
		}
	}

	GetSystemTime(&time);
	cout << "\n";
	cout << "Total execution time: " << abs(time.wMilliseconds - totalTime.wMilliseconds) << endl;
	cout << "Multiply statistics: " << endl;
	cout << "Fastest time: " << most_efficient_time.wMilliseconds << "millis" << endl;
	cout << "Number of threads: " << most_efficient_nr_threads << endl;
}
#endif // C_ASYNC_ASYNC_MULTIPLY_H