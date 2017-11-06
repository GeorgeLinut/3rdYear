#pragma once
#ifndef C_ASYNC_CONTROLLER_H
#define C_ASYNC_CONTROLLER_H
#include<future>
#include "Matrix.h"

class Controller
{
public:
	Controller();
	~Controller();
	void sum(Matrix matrixA, Matrix matrixB, Matrix matrix_res, int nr_threads);
	void multiply(Matrix matrixA, Matrix matrixB, Matrix matrix_res, int nr_threads);
	static void run_sum(Matrix matrixA, Matrix matrixB, Matrix matrixResult, int row, int column);
	static void run_multiply(Matrix matrixA, Matrix matrixB, Matrix matrixResult, int row, int column);
};

Controller::Controller()
{
}

Controller::~Controller()
{
}

void Controller::sum(Matrix matrixA, Matrix matrixB, Matrix matrix_res, int nr_threads)
{
	for (int i = 0;i < matrixA.get_rows();i++)
	{
		for (int j = 0;j < matrixA.get_columns();j++)
		{
			if (nr_threads > 0)
			{
				std::future<void> result(std::async(run_sum, matrixA, matrixB, matrix_res, i, j));
				result.get();
			}
			else {
				run_sum(matrixA, matrixB, matrix_res, i, j);
			}
		}
	}
}

void Controller::run_sum(Matrix matrixA, Matrix matrixB, Matrix matrix_res, int row, int column)
{
	matrix_res.set_value(row, column, matrixA.get_value(row, column) + matrixB.get_value(row, column));
}

void Controller::multiply(Matrix matrixA, Matrix matrixB, Matrix matrix_res, int nr_threads)
{
	for (int i = 0;i < matrixA.get_rows();i++)
	{
		for (int j = 0;j < matrixA.get_columns();j++)
		{
			if (nr_threads > 0)
			{
				std::future<void> result(std::async(run_multiply, matrixA, matrixB, matrix_res, i, j));
				result.get();
			}
			else {
				run_multiply(matrixA, matrixB, matrix_res, i, j);
			}
		}
	}
}

void Controller::run_multiply(Matrix matrixA, Matrix matrixB, Matrix matrix_res, int row, int column)
{
	for (int t = 0;t < matrixB.get_columns();t++)
	{
		matrix_res.set_value(row, column, matrixA.get_value(row, column) + matrixB.get_value(row, column));
	}
}
#endif //C_ASYNC_CONTROLLER_H
