#include "..\Common\Image.h"
#include "..\Common\Filter.h"

#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include "cudaUtils.cuh"

#include <chrono>
#include <exception>
#include <functional>
#include <iostream>
#include <memory>

#define RED     0
#define GREEN   1
#define BLUE    2
#define OPACITY 3

const size_t Blocks = 2048;
const size_t ThreadsPerBlock = 512;

__device__ uint8_t interval(int32_t value)
{
	if (value > 255) return 255;
	if (value < 0) return 0;
	return (uint8_t)value;
}

__global__ void applyFilter(
	uint8_t* image, size_t height, size_t width,
	size_t borderedHeight, size_t borderedWidth,
	int32_t* filter, size_t filterSize, uint8_t* result
)
{
	uint32_t x = threadIdx.x + blockIdx.x * blockDim.x;
	uint32_t y = threadIdx.y + blockIdx.y * blockDim.y;
	uint32_t index = x + y * blockDim.x * gridDim.x;
	//uint32_t blockId = blockIdx.x + blockIdx.y * gridDim.x;

	while (index < height * width)
	{
		int32_t red = 0, green = 0, blue = 0;
		size_t h = index / width;
		size_t w = index % width;

		index += blockDim.x * blockDim.y * gridDim.x * gridDim.y;

		for (size_t i = 0; i < filterSize; i++)
			for (size_t j = 0; j < filterSize; j++)
			{
				uint8_t* pixel = image + 4 * (h + i) * borderedWidth + 4 * (w + j);
				red += pixel[RED] * filter[i * filterSize + j];
				green += pixel[GREEN] * filter[i * filterSize + j];
				blue += pixel[BLUE] * filter[i * filterSize + j];
			}

		uint8_t* pixel = result + 4 * h * width + 4 * w;
		pixel[RED] = interval(red);
		pixel[GREEN] = interval(green);
		pixel[BLUE] = interval(blue);
		pixel[OPACITY] = 255;
	}

}

int main()
{
	batman::Image image;
	batman::Image result;

	std::string initialFile = "Polar_Bear_Svalbard_Norwegian_Arctic.png";
	std::string resultFile = "Result.png";

	try
	{
		image.LoadFromFile(initialFile);
		std::cout << "Loaded image " << initialFile << std::endl;

		image.Border(border);

		result.Resize(image.Height(), image.Width());

		size_t gpuFilterSize = filterSize;
		size_t gpuImageHeight = image.Height();
		size_t gpuImageWidth = image.Width();
		size_t gpuImageBorderedHeight = image.BorderedHeight();
		size_t gpuImageBorderedWidth = image.BorderedWidth();

		auto gpuImageBuffer = std::unique_ptr<uint8_t, std::function<void(uint8_t*)>>(
			reinterpret_cast<uint8_t*>(batman::cudaAlloc(image.BufferSize())),
			[](uint8_t* ptr) { cudaFree(ptr); }
		);

		auto gpuFilterBuffer = std::unique_ptr<int32_t, std::function<void(int32_t*)>>(
			reinterpret_cast<int32_t*>(batman::cudaAlloc(filterSize * filterSize * sizeof(int32_t))),
			[](int32_t* ptr) { cudaFree(ptr); }
		);

		auto gpuResultBuffer = std::unique_ptr<uint8_t, std::function<void(uint8_t*)>>(
			reinterpret_cast<uint8_t*>(batman::cudaAlloc(result.BufferSize())),
			[](uint8_t* ptr) { cudaFree(ptr); }
		);

		cudaError error = cudaMemcpy(gpuImageBuffer.get(), image.Buffer(), image.BufferSize(), cudaMemcpyKind::cudaMemcpyHostToDevice);
		if (error != cudaError::cudaSuccess)
		{
			throw new std::runtime_error("cudaMemcpy failed with error code" + std::to_string(error));
		}

		for (int i = 0; i < filterSize; i++)
		{
			error = cudaMemcpy(
				gpuFilterBuffer.get() + i * filterSize,
				filter[i],
				filterSize * sizeof(int32_t),
				cudaMemcpyKind::cudaMemcpyHostToDevice
			);
			if (error != cudaError::cudaSuccess)
			{
				throw new std::runtime_error("cudaMemcpy failed with error code" + std::to_string(error));
			}
		}

		__int64 start = std::chrono::duration_cast<std::chrono::milliseconds>(
			std::chrono::system_clock::now().time_since_epoch()
			).count();

		applyFilter << <Blocks, ThreadsPerBlock >> > (
			gpuImageBuffer.get(), gpuImageHeight, gpuImageWidth,
			gpuImageBorderedHeight, gpuImageBorderedWidth,
			gpuFilterBuffer.get(), gpuFilterSize, gpuResultBuffer.get()
			);

		error = cudaMemcpy(result.Buffer(), gpuResultBuffer.get(), result.BufferSize(), cudaMemcpyKind::cudaMemcpyDeviceToHost);
		if (error != cudaError::cudaSuccess)
		{
			throw new std::runtime_error("cudaMemcpy failed with error code" + std::to_string(error));
		}

		__int64 stop = std::chrono::duration_cast<std::chrono::milliseconds>(
			std::chrono::system_clock::now().time_since_epoch()
			).count();

		std::cout << "Finished aplying filter on " << Blocks << " blocks and "
			<< ThreadsPerBlock << " threads per blocks in "
			<< stop - start << " miliseconds" << std::endl;

		image.Unborder();

		//image.StoreToFile("Initial.png");
		result.StoreToFile(resultFile);
		std::cout << "Stored result in image " << resultFile << std::endl;
	}
	catch (std::exception& ex)
	{
		std::cerr << ex.what() << std::endl;
		return -1;
	}
}
