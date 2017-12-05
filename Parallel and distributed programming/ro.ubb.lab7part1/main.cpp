//1.Given a sequence of n numbers, compute the sums of the first 
//k numbers, for each k between 1 and n.
//Parallelize the computations, to optimize for 
//low latency on a large number of processors.
//Use at most 2 * n additions, but no more 
//than 2 * log(n) additions on each computation path
//from inputs to an output.


//Partial Sums

//I used dynamic programming to solve this problem.Each thread can compute the sum of every subsequence of the array(not neccessarry starting on the first position).Let's denote a - the initial array and let the following discriminant:

//dp[k][i] = sum(a[j]) where i <= j <= i + 2 ^ k - 1
//It follows that
//dp[0][i] = a[i]
//dp[k][i] = dp[k - 1][i] + dp[k - 1][i - 2 ^ (k - 1)]
//Having this table precomputed, it's easy to compute the sum of the first k elements. Just start at position 0 and break the interval in other log(k) intervals of size a power of two.

//Let T be the number of threads.Thread number i where 0 <= i < T will compute every partial sum k such that k mod T = i.

#include <iostream>
#include <fstream>
#include <vector>
#include <thread>
#include <assert.h>

using namespace std;

const int maxlg = 22;
const int maxn = 500005;

int n;
long long dp[maxlg][maxn], sum[maxn], psum[maxn];

const int T = 5;

//#define debug

inline void doIt(int idx) {
	for (int i = idx; i < n; i += T) {
		int act = 0;
		int now = i + 1;
		for (int bit = 0; (1 << bit) <= now; ++bit) {
			if (now & (1 << bit)) {
				sum[i] += dp[bit][act];
				act += (1 << bit);
			}
		}
	}
}

int main() {
	clock_t t;
	t = clock();

	ifstream fin("sums.in");

	fin >> n;
	for (int i = 0; i < n; ++i) {
		fin >> dp[0][i];
		psum[i] = psum[i - 1] + dp[0][i];
	}

	// dp[k][i] =
	for (int k = 1; (1 << k) < maxn; ++k) {
		for (int i = 0; i < n; ++i) {
			dp[k][i] = dp[k - 1][i] + dp[k - 1][i + (1 << (k - 1))];
		}
	}

	vector <thread> th;
	int last = n;
	if (T < n) {
		last = T;
	}
	for (int i = 0; i < last; ++i) {
		th.push_back(thread(doIt, i));
	}
	for (int i = 0; i < th.size(); ++i) {
		th[i].join();
	}
	ofstream fout("sums.out");
	for (int i = 0; i < n; ++i) {
		fout << sum[i] << '\n';
		assert(sum[i] == psum[i]);
	}

	t = clock() - t;
	cout << "Computing partial sums of an array with " << n
		<< " elements took me " << t << " cycles ("
		<< static_cast<float> (t) / CLOCKS_PER_SEC << " seconds)\n";

	return 0;
}