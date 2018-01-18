#include "cuda_runtime.h"

#include <stdexcept>

namespace batman
{
	class CudaBadAlloc : public std::runtime_error
	{
	public:
		CudaBadAlloc() : runtime_error("cudaMalloc failed") { }
	};

	void* cudaAlloc(size_t size)
	{
		void* pointer;
		cudaError_t error;

		error = cudaMalloc(&pointer, size);
		if (error == cudaError::cudaErrorMemoryAllocation)
		{
			throw CudaBadAlloc();
		}

		return pointer;
	}
}
