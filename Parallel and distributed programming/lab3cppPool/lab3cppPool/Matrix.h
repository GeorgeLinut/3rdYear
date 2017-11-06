#pragma once
#include <random>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <iostream>
using namespace std;
#ifndef  C_ASYNC_MATRIX_H
#define C_ASYNC_MATRIX_H

class Matrix
{
public:
	Matrix();
	~Matrix();
	Matrix(int rows, int colums);

	void set_value(int row, int column, int value);
	int get_value(int row, int column);
	int** get_matrix();
	int get_rows();
	int get_columns();
	void print();

private:
	int rows;
	int columns;
	int **matrix;

	void allocateSpace()
	{
		matrix = new int*[rows];
		for (int i = 0;i < rows;i++) {
			matrix[i] = new int[columns];
		}
	}
};

Matrix::Matrix()
{
}

Matrix::~Matrix()
{
}

Matrix::Matrix(int rows, int columns) : rows(rows), columns(columns)
{
	allocateSpace();
	for (int i = 0;i < rows;i++) {
		for (int j = 0;j < columns;++j)
		{
			matrix[i][j] = rand() % 100 + 1;
		}
	}
}

int Matrix::get_value(int row, int column) {
	return matrix[row][column];
}

void Matrix::set_value(int row, int column, int value) {
	matrix[row][column] = value;
}

int Matrix::get_rows() {
	return rows;
}

int Matrix::get_columns() {
	return columns;
}

int** Matrix::get_matrix() {
	return matrix;
}

void Matrix::print() {
	for (int i = 0;i < rows;++i) {
		for (int j = 0;j < columns;++j) {
			std::cout << matrix[i][j] << " ";
		}
		cout << endl;
	}
}

#endif // ! C_ASYNC_MATRIX_H
