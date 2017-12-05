#ifndef SAFE_QUEUE
#define SAFE_QUEUE

#include <queue>
#include <mutex>
#include <condition_variable>

template <class T>
class SafeQueue
{
private:
	std::queue<T> safeQueue;
	mutable std::mutex mtx;
	std::condition_variable cond;
public:
	void enqueue(T t) {
		std::lock_guard<std::mutex> lock(mtx);
		safeQueue.push(t);
		cond.notify_one();
	}

	T dequeue(void)
	{
		std::unique_lock<std::mutex> lock(mtx);
		while (safeQueue.empty())
		{
			cond.wait(lock);
		}
		T val = safeQueue.front();
		safeQueue.pop();
		return val;
	}

};
#endif